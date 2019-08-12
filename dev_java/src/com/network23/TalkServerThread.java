package com.network23;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.network1.Protocol;

public class TalkServerThread extends Thread {
	
	TalkServer ts = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	String nickName = null;
	String g_title = null;//대기 이거나 톡방명
	int g_current = 0;//톡방에 참여자 수
	
	public TalkServerThread(TalkServer ts) {
		this.ts = ts;
		try {
			oos = new ObjectOutputStream(ts.client.getOutputStream());
			ois = new ObjectInputStream(ts.client.getInputStream());
			String msg = (String)ois.readObject();
			ts.jta_log.append(msg+"\n");
			ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
			
			StringTokenizer st = null;
			if(msg!=null) {
				st = new StringTokenizer(msg, Protocol.seperator);
			}
			if(st.hasMoreTokens()) {
				st.nextToken();//Protocol.WAIT
				nickName = st.nextToken();
				g_title = st.nextToken();	
			}
			for(TalkServerThread tst:ts.globalList) {//그전정보 띄우기
				String currentName = tst.nickName;
				String currentState = tst.g_title;
				this.send(Protocol.WAIT
						 +Protocol.seperator+currentName
				         +Protocol.seperator+currentState);
			}
			
			for(int i=0;i<ts.roomList.size();i++) {
				Room room =  ts.roomList.get(i);
				String title = room.title;
				g_title = title;
				int current =0;
				if(room.userList !=null && room.userList.size()!=0 ) {
					current = room.userList.size();
				}
				g_current = current;
				this.send(Protocol.ROOM_LIST
						+ Protocol.seperator+g_title
						+ Protocol.seperator+g_current
						);
			}
			ts.globalList.add(this);
			this.broadCasting(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadCasting(String msg) {
		//동기화 처리 추가하기 - 절대 인터셉트 당하지 않음
		synchronized(this) {
			for(TalkServerThread tst:ts.globalList) {
				tst.send(msg);
			}
		}
	}
	//서버에 접속한 사용자 중에서 톡방에 참여한 사용자들에게 메시지를 전송 처리함.
	public void roomCasting(String msg) {
		for(int i=0;i<ts.roomList.size();i++) {
			Room room = ts.roomList.get(i);
			if(g_title.equals(room.title)) {
				for(int j=0;j<room.userList.size();j++) {
					TalkServerThread tst = room.userList.get(j);
					try {
						tst.send(msg);//샌드 한다.
					} catch (Exception e) {
						room.userList.remove(j--);
					}
				}
			}
			break;
		}
	}
	public void send(String msg) {
		try {
			oos.writeObject(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void run() {
		boolean isStop = false;		
		try {
			run_start:
			while(!isStop) {
				String msg = (String)ois.readObject();
				ts.jta_log.append(msg+"\n");
				ts.jta_log.setCaretPosition(ts.jta_log.getDocument().getLength());
				StringTokenizer st = null;
				int protocol = 0;
				if(msg!=null) {
					st = new StringTokenizer(msg, Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				if(protocol==Protocol.ROOM_CREATE) {
					String roomTitle = st.nextToken();
					String currentNum = st.nextToken();
					Room room = new Room(roomTitle, Integer.parseInt(currentNum));
					ts.roomList.add(room);
					this.broadCasting(Protocol.ROOM_CREATE
						       +  Protocol.seperator+roomTitle
						       +  Protocol.seperator+currentNum
						         );
				}
				else if(protocol==Protocol.ROOM_IN) {
					String roomTitle = st.nextToken();
					String nickName = st.nextToken();
					for(int i=0;i<ts.roomList.size();i++) {
						Room room = ts.roomList.get(i);
						if(roomTitle.equals(room.title)) {
							g_title = roomTitle;
							g_current = room.current+1;
							room.setCurrent(g_current);
							room.userList.add(this);//this는 사용자 스레드
							room.nameList.add(nickName);						
						}
					}
					//접속한 this 스레드의 정보도 해당 방의 roomList에 담긴후,
					for(int i=0;i<ts.roomList.size();i++) {
						Room room = ts.roomList.get(i);
						String title = room.title;
						g_title = title;
						int current = 0;
						if(room.userList!=null && room.userList.size()!=0) {
							current = room.userList.size();
							
						}						
						for(int j=0;j<room.nameList.size();j++) {//'그'방에있는 모든 유저 스레드에 INLIST보냄 
							if(!nickName.equals(room.nameList.get(j))) {//나빼고
								if(roomTitle.equals(room.title)) {
									TalkServerThread tst = room.userList.get(j);
									tst.send(Protocol.ROOM_INLIST
											  +  Protocol.seperator+g_title
											  +  Protocol.seperator+g_current
											  +  Protocol.seperator+nickName
											    );
								}
							}
						}
					}
					broadCasting(Protocol.ROOM_IN
							   + Protocol.seperator+g_title
							   + Protocol.seperator+g_current
							   + Protocol.seperator+this.nickName
							    );
				}
				else if(protocol==Protocol.MESSAGE) {
					String roomTitle = st.nextToken();
					String nickName = st.nextToken();
					String message = st.nextToken();
					String imgChoice = "";
					while(st.hasMoreTokens()) {
						imgChoice = st.nextToken();
					}
					this.broadCasting(Protocol.MESSAGE
							        + Protocol.seperator+nickName
							        + Protocol.seperator+message
							        + Protocol.seperator+imgChoice);
					 
				}
				else if(protocol==Protocol.WHISHER) {
					String sender = st.nextToken();
					String message = st.nextToken();
					String emoticon = st.nextToken();
					String receiver = st.nextToken();
					
					for(TalkServerThread tst : ts.globalList) {
						if((tst.nickName.equals(receiver))&&!(tst.nickName.equals(this.nickName))) {//받는이가 일치하는 쓰레드중, 내 쓰레드를 제외하고							
							tst.send(Protocol.WHISHER
									 +Protocol.seperator+sender
									 +Protocol.seperator+message
									 +Protocol.seperator+emoticon
									 +Protocol.seperator+receiver);
							this.send(Protocol.WHISHER
									 +Protocol.seperator+sender
									 +Protocol.seperator+message
									 +Protocol.seperator+emoticon
									 +Protocol.seperator+receiver);
						}
						else if((tst.nickName.equals(receiver))&&(tst.nickName.equals(sender))) {//보낸이와 받는이가 같을때 즉, 나한테 귓말을 보냈을때 내쓰레드에 접근
							
							this.send(Protocol.WHISHER
									 +Protocol.seperator+sender
									 +Protocol.seperator+message
									 +Protocol.seperator+emoticon
									 +Protocol.seperator+receiver);
						}
					}
					
					
				}
				else if(protocol==Protocol.CHANGE) {
					String nickName = st.nextToken();
					String afterName = st.nextToken();
					String notice = st.nextToken();
					this.nickName = afterName;
					broadCasting(Protocol.CHANGE
							    +Protocol.seperator+nickName
							    +Protocol.seperator+afterName
							    +Protocol.seperator+notice);					
				}
				else if(protocol==Protocol.ROOM_OUT) {
					broadCasting(msg);
					String nickName = st.nextToken();
					for(int i=0;i<ts.globalList.size();i++) {
						if((ts.globalList.get(i).nickName).equals(nickName)) {							
							ts.globalList.remove(i);
						}
					}
					break run_start;
				}
			}
			
			
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

}
