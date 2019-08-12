package com.network1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.StringTokenizer;

public class TalkServerThread extends Thread {
	TalkServer ts = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	String nickName = null;
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
			st.nextToken();//100
			nickName = st.nextToken();
			for(TalkServerThread tst:ts.chatList) {//내방 채팅창 그전정보 띄우기
				String currentName = tst.nickName;
				this.send(Protocol.ROOM_IN+Protocol.seperator+currentName);
			}
			ts.chatList.add(this);
			this.broadCasting(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void broadCasting(String msg) {
		for(TalkServerThread tst:ts.chatList) {//이전 채팅방에 내가 들어가므로써 업데이트된 정보 띄우기
			tst.send(msg);
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
				
				if(protocol==Protocol.MESSAGE) {
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
					
					for(TalkServerThread tst : ts.chatList) {
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
					for(int i=0;i<ts.chatList.size();i++) {
						if((ts.chatList.get(i).nickName).equals(nickName)) {							
							ts.chatList.remove(i);
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
