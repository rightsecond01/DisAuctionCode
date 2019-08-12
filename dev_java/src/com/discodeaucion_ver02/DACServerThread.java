package com.discodeaucion_ver02;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.util.Token;

public class DACServerThread extends Thread {
	DACServer ds = null;
	ObjectOutputStream main_oos = null;
	ObjectInputStream main_ois = null;
	ObjectOutputStream room_oos = null;
	ObjectInputStream room_ois = null;
	String user_id = null;
	String nickName = null;
	String status = null;

	List<ServerSocket> testList = null;
	
	Socket roomclient = null;

	public DACServerThread(DACServer ds) {
		this.ds = ds;
		testList = new Vector<ServerSocket>();
		try {
			main_oos = new ObjectOutputStream(ds.client.getOutputStream());
			main_ois = new ObjectInputStream(ds.client.getInputStream());
			String msg = (String) main_ois.readObject();
			// 여기서 jta_log에 뿌리는대신, DB에 저장하는 코드 작성.
			MemberDao mDao = new MemberDao();
			int result = mDao.inOutLog(msg);
			if (result == 0) {
				JOptionPane.showMessageDialog(new JDialog(), "에러코드 201 :서버에 문제가 생겼습니다");
				System.exit(0);
			}
			ds.jta_log.append(msg + "\n");
			ds.jta_log.setCaretPosition(ds.jta_log.getDocument().getLength());
			StringTokenizer st = null;
			int protocol = 0;
			if (msg != null) {
				st = new StringTokenizer(msg, Dprotocol.seperator);
				protocol = Integer.parseInt(st.nextToken());
			}
			user_id = st.nextToken();
			nickName = st.nextToken();
			ds.online_userList.add(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void broadCasting(String msg) {
		synchronized (this) {
			for (DACServerThread dst : ds.online_userList) {
				dst.send(msg);
			}
		}
	}

	public void send(String msg) {
		try {
			main_oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int portNum() {
		int port=ds.roomPort;
		if(ds.activeRoomList==null||ds.activeRoomList.size()==0) {
			return port;
		}
		else {
			for(int i=0;i<ds.activeRoomList.size();i++) {
				if(port!=ds.activeRoomList.get(i).getRoomPort()) {
					return port;
					
				}
				else if(port==ds.activeRoomList.get(i).getRoomPort()) {
					port++;
				}
			}
			return port;
			//추후, 마지막으로 주어진 포트번호부터 시작이 가능하도록 코드변경
			
		}
	}

	public void run() {
		boolean isStop = false;
		try {
			 run_start: while (!isStop) {
				String msg = (String) main_ois.readObject();
				ds.jta_log.append(msg + "\n");
				ds.jta_log.setCaretPosition(ds.jta_log.getDocument().getLength());

				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}

				switch (protocol) {
				case Dprotocol.ROOM_CREATE: {
					String createRoomInfo = st.nextToken();
					// Json으로 변환
					JsonParser parser = new JsonParser();
					JsonElement element = parser.parse(createRoomInfo);
					// 변환후, key값에따라 자르기
					String master_id = element.getAsJsonObject().get("master_id").getAsString();
					String room_title = element.getAsJsonObject().get("room_title").getAsString();
					String room_game = element.getAsJsonObject().get("room_game").getAsString();
					int room_type = element.getAsJsonObject().get("room_type").getAsInt();
					int p_limit = element.getAsJsonObject().get("p_limit").getAsInt();
					
					//토큰생성
					Token tkn = new Token("token");
					String token = tkn.hash;
					// DB에 방 정보 등록
					Map<String,Object> pRoomMap = new HashMap<String, Object>();
					pRoomMap.put("master_id", master_id);
					pRoomMap.put("room_title", room_title);
					pRoomMap.put("room_game", room_game);
					pRoomMap.put("room_type", room_type);
					pRoomMap.put("p_limit", p_limit);
					pRoomMap.put("token", token);

					RoomDao rDao = new RoomDao();
					int result = rDao.createRoom(pRoomMap);
					rDao.insertInRoom(token, user_id);
					
					

					if (result == 1) {// 입력성공
						pRoomMap.put("p_current",1);//p_current는 ServerSocket이 열려있어야만 의미가 있으므로 DB단계에선 관리되지않고, List<Room>단계에서만 관리
						int port = portNum();
						pRoomMap.put("port", port);
						Gson g = new Gson();
						String roomInfo = g.toJson(pRoomMap);

						// 각 방마다 일처리를 담당할 쓰레드 생성
						DACServerRoomThread dsrt = new DACServerRoomThread(port, ds, pRoomMap);
						dsrt.start();
						
						this.send(Dprotocol.ROOM_CREATE_PERMIT 
								+ Dprotocol.seperator + roomInfo
								);

						/*
						 * 방 생성 순서.
						 * 
						 * 클라이언트에서 룸생성요청=>DB에 추가=>DB추가 성공하면, 해당 포트 번호를 가지는 dsrt만들고 룸스레드 실행 =>
						 * RoomCreatePermit 클라이언트에 보냄
						 *
						 */

					} else {
						JOptionPane.showMessageDialog(new JFrame(), "에러코드 201 : 서버에 문제가 생겼습니다.");
						System.exit(0);
					}

				}
					break;
				case Dprotocol.ROOM_ACCEPT: {
					String token = st.nextToken();
					RoomDao rDao = new RoomDao();
					//accept하려는 방이 DB에 존재하는가를 검사하는 코드
					int result = rDao.isExistRoom(token);
					if (result == 1) {
						/*
						 * 방에 이미 접속 되있는경우, insertRoomIn을 실행할 필요 없으므로 
						 *exist = 0 : 그 토큰을 가지는 방에 user_id가 accept 되어있지않음
						 *exist = 1 : 그 토큰을 가지는 방에 user_id가 accept 되어있음
						 */
						int exist = rDao.isAlreadyInRoom(token, user_id);
						if(exist==0) {
							rDao.insertInRoom(token, user_id);
							Map<String, Object> rMap = rDao.getRoomInfo(token);
							
							//현재 accept하려는 방의 ServerSocket이 
							//열려있는경우 : p_current 를 받아와서 초기
							//닫혀있는경우 : p_current = 0;
							int p_current = 0;
							for(int i=0;i<ds.activeRoomList.size();i++) {
								if(token.equals(ds.activeRoomList.get(i).getRoomMap().get("token"))) {
									p_current = (Integer)ds.activeRoomList.get(i).getRoomMap().get("p_current");
								}
							}
							rMap.put("p_current", p_current);
							Gson g = new Gson();
							String roomInfo = g.toJson(rMap);
							
							this.send(Dprotocol.ROOM_ACCEPT_PERMIT 
									+ Dprotocol.seperator + roomInfo);							
						}
						else if(exist==1) {
							this.send(Dprotocol.ROOM_ACCEPT_FAIL 
									+ Dprotocol.seperator+"이미 접속한 방입니다."
									);
						}
					} else if (result == 0) {
						this.send(Dprotocol.ROOM_ACCEPT_FAIL 
								+ Dprotocol.seperator+"일치하는 토큰이 없습니다. 다시 확인해주세요."
								);
					}

				}
					break;
				case Dprotocol.ROOM_IN: {
					int port = -65534;
					String token = st.nextToken();
					RoomDao rDao = new RoomDao();
					List<Map<String,String>> inRoomUserList = rDao.getInRoomUser(token);//inRoomUserList ==> DB에서 가져온 해당 토큰을 가지는 방에 접속한 사람 List
					
					/////////////////////////////////////판별코드 시작 ///////////////////////////////////
					
					/////////////접속하려는 방의 토큰으로, [현재 그 토큰을 가지는 방]이 포트가 열려있는지 확인하는 코드
					//열려 있을 경우 : isOpenedRoom = true, 닫혀 있을 경우 : isOpenedRoom = false
					boolean isOpenedRoom = false;
					Room indexRoom = null;
					
					boolean isInRoom = false;
					boolean isRoomFull = false;
					for(int i=0;i<ds.activeRoomList.size();i++) {						
						if(token.equals(ds.activeRoomList.get(i).getRoomMap().get("token"))) {
							isOpenedRoom = true;
							indexRoom = ds.activeRoomList.get(i);														
						}
					}
					if(indexRoom!=null) {//indexRoom이 null인경우 
						                 //방 자체가 열려있지 않기때문에 중복검사,현재인원검사를 진행할 필요 없음
						
						/////방에 이미 접속되어 있는지 확인.(접속되어있는경우 : isInRoom=true, 안되어있는 경우 : isInRoom=false)
						List<DACServerRoomThread.ServiceThread> userThreadList = indexRoom.getRoomInList();
						for(int j=0;j<userThreadList.size();j++) {
							if(this.user_id.equals(userThreadList.get(j).user_id)) {					
								isInRoom = true;
							}								
						}
						
						/////방이 꽉 찼는지 알아보는 코드
						int pCurrent = (int)indexRoom.getRoomMap().get("p_current");
						int pLimit = (int)indexRoom.getRoomMap().get("p_limit");
						if(pCurrent==pLimit) {
							isRoomFull = true;
						}
						else {
							isRoomFull = false;
						}
					}
					///////////////////////판별코드 끝/////////////////////////////////////
					
					if(isRoomFull) {
						this.send(Dprotocol.ROOM_IN_FAIL
								 +Dprotocol.seperator+"방이 꽉 차서 입장할 수 없습니다.");
					}
					else {
						if(isInRoom) {//isInRoom이 true => 이미 roomin한 상태.
							this.send(Dprotocol.ROOM_IN_FAIL
									 +Dprotocol.seperator+"이미 접속한 방입니다.");							
						}
						else {//isInRoom이 false => roomin 처리 해야됨.
							if(isOpenedRoom&&(indexRoom!=null)) {//방의 ServerSocket이 열려있을때
								//List<Room>에서 방정보 가져오기						
								Map<String,Object> rRmap = indexRoom.getRoomMap();
								
								port = indexRoom.getPortMappingToken();//getRoomport로 가져올수도 있지만 한번더 토큰과의 확인과정을 거치는 코드
								int p_current = (Integer)rRmap.get("p_current");
								p_current = p_current + 1;//내가 들어가므로 현재인원이 1이 늘어남
								indexRoom.getRoomMap().put("p_current", p_current);//서버의 List<Room>아래 저장된 방정보에서 현재인원수 업데이트
								
								List<DACServerRoomThread.ServiceThread> inRoomThreadList = indexRoom.getRoomInList();
								
								/////DB에서 가져온 userList와 서버에 저장된 userList를 비교해서 on-off구분////START
								List<Map<String,String>> onlineUserList = new Vector<Map<String,String>>();
								List<Map<String,String>> offlineUserList = new Vector<Map<String,String>>();
								for(int j=0;j<inRoomUserList.size();j++) {
									Map<String,String> dbUserMap = inRoomUserList.get(j);
									String dbUser_id = dbUserMap.get("mem_id");
									String dbUser_nickName = dbUserMap.get("mem_nick");
									for(int k=0;k<inRoomThreadList.size();k++) {
										Map<String,String> userMap = new HashMap<String, String>();
										userMap.put("mem_id", dbUser_id);
										userMap.put("mem_nick", dbUser_nickName);
										if(dbUser_nickName.equals(inRoomThreadList.get(k).nickName)) {
											onlineUserList.add(userMap);
											break;
										}
										offlineUserList.add(userMap);									
									}
								}
								Gson g = new Gson();
								String onUserList = g.toJson(onlineUserList);
								String offUserList = g.toJson(offlineUserList);

								this.send(Dprotocol.ROOM_IN
								+Dprotocol.seperator+port
								+Dprotocol.seperator+p_current
								+Dprotocol.seperator+"open"
								+Dprotocol.seperator+token
								+Dprotocol.seperator+onUserList
								+Dprotocol.seperator+offUserList
								);
							}
							else {//같은 토큰으로 열려있는 방이 없을때(포트번호가 부여되지않았을때)
								port=portNum();
								
								//DB에서 방정보 가져오기(방의 ServerSocket이 닫혀있을때)
								Map<String,Object> pRoomMap = rDao.getRoomInfo(token);
								pRoomMap.put("p_current", 1);//내가 들어가므로 p_current는 1(그전엔 ServerSocket이 닫혀있었으므로  0이였음)
								
								//해당 포트 번호를 서버소캣으로 여는 스레드 실행
								DACServerRoomThread dsrt = new DACServerRoomThread(port, ds, pRoomMap);
								dsrt.start();
								
								Gson g = new Gson();
								String offlineUserList = g.toJson(inRoomUserList);
								
								//위의 스레드에서 ds.activeRoomList에 저장을 하므로 activeRoomList(List<Room>)에서 정보 뽑아옴.
								//개선방향 : 굳이 또 List<Room>에 접근해서 정보를 가져와야 할까? 라는 생각부분 고민
								for(int i=0;i<ds.activeRoomList.size();i++) {
									if(token.equals(ds.activeRoomList.get(i).getRoomMap().get("token"))) {
										int p_current = (Integer)ds.activeRoomList.get(i).getRoomMap().get("p_current");
										ds.activeRoomList.get(i).getRoomMap().put("p_current", p_current);
										this.send(Dprotocol.ROOM_IN
												+Dprotocol.seperator+port
												+Dprotocol.seperator+p_current									
												+Dprotocol.seperator+"close"									
												+Dprotocol.seperator+token									
												+Dprotocol.seperator+offlineUserList									
												);
									}
								}
							}
						}
					}
					
					
				}
					break;

				case Dprotocol.LOG_OUT: {
					String user_id = st.nextToken();
					MemberDao mDao = new MemberDao();
					int result = mDao.inOutLog(msg);
					if (result == 1) {
						for (int i = 0; i < ds.online_userList.size(); i++) {
							if ((ds.online_userList.get(i).user_id).equals(user_id)) {
								ds.online_userList.remove(i);
							}
						}
						this.send(Dprotocol.LOG_OUT 
								+ Dprotocol.seperator + user_id);						
						break run_start;
					}
				}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
