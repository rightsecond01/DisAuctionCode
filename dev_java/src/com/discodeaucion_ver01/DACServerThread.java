package com.discodeaucion_ver01;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.google.gson.Gson;
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

			/*
			 * DB에서 이전정보 받아와서 clientThread에 뿌리는 코드 작성
			 * 
			 * 이전정보 : 접속한 방, 프로필사진, 친구목록 등등
			 * 
			 * 
			 */

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
					String master_id = st.nextToken();
					String room_title = st.nextToken();
					String room_game = st.nextToken();
					int room_type = Integer.parseInt(st.nextToken());
					int p_limit = Integer.parseInt(st.nextToken());

					Token tkn = new Token("");
					String token = tkn.hash;
					// DB에 방 정보 등록
					Map<String,Object> pRoomMap = new HashMap<String, Object>();
					pRoomMap.put("master_id", master_id);
					pRoomMap.put("room_title", room_title);
					pRoomMap.put("room_game", room_game);
					pRoomMap.put("room_type", room_type);
					pRoomMap.put("p_limit", p_limit);
					pRoomMap.put("p_current", 1);
					pRoomMap.put("token", token);

					RoomDao rDao = new RoomDao();
					int result = rDao.createRoom(pRoomMap);
					
					

					if (result == 1) {// 입력성공
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
					int result = rDao.isExistRoom(token);
					if (result == 1) {
						//방에 이미 접속 되있는경우, insertRoomIn을 실행할 필요 없으므로 
						//exist = 0 : 그 토큰을 가지는 방에 user_id가 accept 되어있지않음
						//exist = 1 : 그 토큰을 가지는 방에 user_id가 accept 되어있음
						int exist = rDao.isAlreadyInRoom(token, user_id);
						if(exist==0) {
							rDao.insertRoomIn(token, user_id);
						}
						Map<String, Object> rMap = rDao.getRoomInfo(token);
						Gson g = new Gson();
						String roomInfo = g.toJson(rMap);
						
						this.send(Dprotocol.ROOM_ACCEPT_PERMIT 
								+ Dprotocol.seperator + roomInfo);							
					} else if (result == 0) {
						this.send(Dprotocol.ROOM_ACCEPT_FAIL 
								+ Dprotocol.seperator);
					}

				}
					break;
				case Dprotocol.ROOM_IN: {
					int port = -65534;
					String token = st.nextToken();
					RoomDao rDao = new RoomDao();
					Map<String,Object> pRoomMap = rDao.getRoomInfo(token);
					//접속하려는 방의 토큰으로, [현재 그 토큰을 가지는 방]이 포트가 열려있는지 확인하는 코드
					for(int i=0;i<ds.activeRoomList.size();i++) {
						if(token.equals(ds.activeRoomList.get(i).getRoomMap().get("token"))) {
							port = ds.activeRoomList.get(i).getPortMappingToken();
							int p_current = (Integer)ds.activeRoomList.get(i).getRoomMap().get("p_current");
							ds.activeRoomList.get(i).getRoomMap().put("p_current", p_current+1);						
							
							this.send(Dprotocol.ROOM_IN
									+Dprotocol.seperator+port
									+Dprotocol.seperator+pRoomMap.get("room_title")
									+Dprotocol.seperator+pRoomMap.get("room_type"));
							return;
							//port가있는경우 새로 스레드를 열 필요가 없으니 여기서 리턴
						}
					}
					//if true==> 해당 토큰을 가지는 방이 포트가 안열려있음. 빈 포트번호를 초기화해줌
					if(port<0) {
						port=portNum();
					}
					//해당 포트 번호를 서버소캣으로 여는 스레드 실행
					DACServerRoomThread dsrt = new DACServerRoomThread(port, ds, pRoomMap);
					dsrt.start();
					//위의 스레드에서 ds.activeRoomList에 저장을 하므로 해당
					for(int i=0;i<ds.activeRoomList.size();i++) {
						if(token.equals(ds.activeRoomList.get(i).getRoomMap().get("token"))) {
							int p_current = (Integer)ds.activeRoomList.get(i).getRoomMap().get("p_current");
							ds.activeRoomList.get(i).getRoomMap().put("p_current", p_current+1);
							this.send(Dprotocol.ROOM_IN
									+Dprotocol.seperator+port
									+Dprotocol.seperator+pRoomMap.get("room_title")
									+Dprotocol.seperator+pRoomMap.get("room_type"));
						}
					}
				}
					break;

				case Dprotocol.LOG_OUT: {
					String user_id = st.nextToken();
					MemberDao mDao = new MemberDao();
					int result = mDao.inOutLog(msg);
					if (result == 1) {
						JOptionPane.showMessageDialog(new JDialog(), "정상적으로 처리 되었습니다.");
						for (int i = 0; i < ds.online_userList.size(); i++) {
							if ((ds.online_userList.get(i).user_id).equals(user_id)) {
								ds.online_userList.remove(i);
							}
						}
						this.send(Dprotocol.LOG_OUT + Dprotocol.seperator + user_id);
						broadCasting(Dprotocol.LOG_OUT + Dprotocol.seperator + user_id);
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
