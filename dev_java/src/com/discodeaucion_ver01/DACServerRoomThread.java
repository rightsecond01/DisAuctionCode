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

public class DACServerRoomThread extends Thread {
	ServerSocket roomServerSocket = null;
	Socket acceptUser = null;
	ServiceThread service = null;
	int port = 0;

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
				roomInList.add(this);
				
			} catch (IOException e) {
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
					case Dprotocol.TEST: {
						this.send(msg);
					}
						break;
					case Dprotocol.ROOM_IN: {

					}
						break;
					case Dprotocol.MESSAGE: {
						String roomTitle = st.nextToken();
						String nickName = st.nextToken();
					}
						break;
					case Dprotocol.WHISHER: {

					}
						break;
					case Dprotocol.CHANGE: {

					}
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			while (!isStop) {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

}
