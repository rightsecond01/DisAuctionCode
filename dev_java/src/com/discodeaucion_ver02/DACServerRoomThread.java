package com.discodeaucion_ver02;

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

public class DACServerRoomThread extends Thread {
	ServerSocket roomServerSocket = null;
	Socket acceptUser = null;
	ServiceThread service = null;
	int port = 0;
	String token = null;

	List<ServiceThread> roomInList = null;

	// 테스트용
	DACServer ds = null;
	Map<String,Object> pRoomMap = null;

	public DACServerRoomThread(int port) {
		this.port = port;
		roomInList = new Vector<DACServerRoomThread.ServiceThread>();
	}

	public DACServerRoomThread(int port, DACServer ds, Map<String,Object> pRoomMap) {
		this.port = port;
		this.ds = ds;
		this.pRoomMap = pRoomMap;
		this.token = pRoomMap.get("token").toString();
		roomInList = new Vector<DACServerRoomThread.ServiceThread>();
		Map<String,Integer> connectPort = new HashMap<String, Integer>();
		connectPort.put(pRoomMap.get("token").toString(), port);

		Room pr = new Room();
		pr.setRoomPort(port);
		pr.setConnectPortMap(connectPort);
		pr.setDsrt(this);
		pr.setRoomInList(roomInList);
		pr.setRoomMap(pRoomMap);
		ds.activeRoomList.add(pr);
	}

	public void run() {
		boolean isStop = false;
		try {
			roomServerSocket = new ServerSocket(port);
			while (!isStop) {
				acceptUser = roomServerSocket.accept();
				service = new ServiceThread(acceptUser);				
				service.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class ServiceThread extends Thread {

		Socket acceptUser = null;
		ObjectOutputStream room_oos = null;
		ObjectInputStream room_ois = null;
		
		String user_id = null;
		String nickName = null;

		public ServiceThread(Socket acceptUser) {
			this.acceptUser = acceptUser;
			try {
				room_oos = new ObjectOutputStream(acceptUser.getOutputStream());
				room_ois = new ObjectInputStream(acceptUser.getInputStream());
				String msg = (String)room_ois.readObject();
				StringTokenizer st = null;
				
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					int protocol = Integer.parseInt(st.nextToken());//Dprotocol.ROOM_IN
				}
				user_id = st.nextToken();
				nickName = st.nextToken();
				//입장한 사람을 제외한 나머지 방에 있는 사람들에게
				broadCasting(Dprotocol.ROOM_IN
						    +Dprotocol.seperator+user_id
						    +Dprotocol.seperator+nickName
						    +Dprotocol.seperator+token
						    );
				roomInList.add(this);
				
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}

		}

		public void send(String msg) {
			try {
				room_oos.writeObject(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void broadCasting(String msg) {
			synchronized (this) {
				for (DACServerRoomThread.ServiceThread st : roomInList) {
					st.send(msg);
				}
			}
		}
		
        /////////DS아래 activeRoomList의 현재인원정보 초기화 하는 코드/////////
		public void roomOutP_current() {
			String token = pRoomMap.get("token").toString();
			
			for(int i=0;i<ds.activeRoomList.size();i++) {
				Room indexRoom = ds.activeRoomList.get(i);
				if(token.equals(indexRoom.getRoomMap().get("token"))){
					Map<String,Object> rRmap = indexRoom.getRoomMap();
					int p_current = (Integer)rRmap.get("p_current");
					p_current = p_current - 1;
					indexRoom.getRoomMap().put("p_current", p_current);
				}
			}
		}

		public void run() {
			String msg = null;
			boolean isStop = false;
			try {
				while (!isStop) {
					msg = (String) room_ois.readObject();
					StringTokenizer st = null;
					int protocol = 0;
					if (msg != null) {
						st = new StringTokenizer(msg, Dprotocol.seperator);
						protocol = Integer.parseInt(st.nextToken());
					}
					switch (protocol) {
					case Dprotocol.ROOM_IN:{
						String user_id = st.nextToken();
						String nickName = st.nextToken();
						this.send(Dprotocol.ROOM_IN
								 +Dprotocol.seperator+user_id
								 +Dprotocol.seperator+nickName
								 );
					}
					    break;
					    
					case Dprotocol.ROOM_OUT:{
						String out_user = st.nextToken();
						String out_user_nick = st.nextToken();
						
						roomOutP_current();//룸아웃 요청을 받았을때, 서버아래 activeRoomList 현재인원 리셋
						
						broadCasting(Dprotocol.ROOM_OUT
								+ Dprotocol.seperator + out_user
								+ Dprotocol.seperator + out_user_nick
								);
						for(int i=0; i<roomInList.size();i++) {
							if(roomInList.get(i).user_id.equals(this.user_id)) {
								roomInList.remove(i);
							}
						}
						isStop=true;
					}
					   break;
					case Dprotocol.ROOM_EXIT:{
						String exit_user = st.nextToken();
						String exit_user_nick = st.nextToken();
						String token = st.nextToken();
						
						//여기서 DB에 접근하여 처리하는 코드 작성 
						RoomDao rDao = new RoomDao();
						int result = rDao.deleteInRoom(token, exit_user);
						
						if(result==1) {
							roomOutP_current();
							
							broadCasting(Dprotocol.ROOM_EXIT
									+ Dprotocol.seperator + exit_user
									+ Dprotocol.seperator + exit_user_nick
									);
							for(int i=0; i<roomInList.size();i++) {
								if(roomInList.get(i).user_id.equals(this.user_id)) {
									roomInList.remove(i);
								}
							}
							isStop=true;
						}
						else if(result==0) {
							this.send(Dprotocol.ROOM_EXIT_FAIL
									 +Dprotocol.seperator+"에러코드201 : 서버입력에 문제가 발생했습니다.");
						}						
						
					}
					case Dprotocol.MESSAGE: {
						String sender = st.nextToken();
						String message = st.nextToken();
						
						broadCasting(Dprotocol.MESSAGE
						     	    +Dprotocol.seperator+sender
						     	    +Dprotocol.seperator+this.user_id
						     	    +Dprotocol.seperator+message
						     	    );
					}
						break;					
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

	}

}
