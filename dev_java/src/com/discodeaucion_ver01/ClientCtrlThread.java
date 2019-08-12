package com.discodeaucion_ver01;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.TableColumnModel;
import javax.swing.text.SimpleAttributeSet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.network1.Protocol;

public class ClientCtrlThread extends Thread {
	ClientCtrl cc = null;

	public ClientCtrlThread(ClientCtrl cc) {
		this.cc = cc;
	}

	public SimpleAttributeSet makeAttribute() {
		SimpleAttributeSet sas = new SimpleAttributeSet();
		return sas;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while (!isStop) {
			try {
				msg = (String) cc.main_ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
				case Dprotocol.TEST: {
					String t = st.nextToken();
					System.out.println("테스트입니다!" + t);
				}
					break;
				case Dprotocol.ROOM_ACCEPT_PERMIT: {
					String roomInfo = st.nextToken();// Json형식으로 받아온 정보
					// Json으로 변환
					JsonParser parser = new JsonParser();
					JsonElement element = parser.parse(roomInfo);
					// 변환후, key값에따라 자르기
					String token = element.getAsJsonObject().get("token").getAsString();
					String room_title = element.getAsJsonObject().get("room_title").getAsString();
					int room_type = Integer.parseInt(element.getAsJsonObject().get("room_type").getAsString());
					String room_game = element.getAsJsonObject().get("room_game").getAsString();
					String master_id = element.getAsJsonObject().get("master_id").getAsString();
					int p_limit = Integer.parseInt(element.getAsJsonObject().get("p_limit").getAsString());
					int p_current = Integer.parseInt(element.getAsJsonObject().get("p_current").getAsString());
					
					for(int i=0;i<cc.acceptRoomList.size();i++) {
						if(cc.acceptRoomList.get(i).get("token").equals(token)) {
							JOptionPane.showMessageDialog(cc, "이미 접속한 방입니다.");
							return;
						}
					}
					Map<String, Object> acceptRoom = new HashMap<String, Object>();
					acceptRoom.put("token", token);
					acceptRoom.put("room_title", room_title);
					acceptRoom.put("room_type", room_type);
					acceptRoom.put("room_game", room_game);
					acceptRoom.put("master_id", master_id);
					acceptRoom.put("p_limit",p_limit);
					acceptRoom.put("p_current", p_current);
					cc.acceptRoomList.add(acceptRoom);
					
					Vector<String> v = new Vector<>();
					v.add(cc.s_roomType[room_type] + " || " + room_game);
					v.add(room_title);
					v.add(token);
					cc.mv.dtm_roomList.addRow(v);

				}
					break;
				case Dprotocol.ROOM_ACCEPT_FAIL: {
					JOptionPane.showMessageDialog(cc, "일치하는 토큰이 없습니다. 다시 확인해주세요.");
				}
					break;
				case Dprotocol.LOG_OUT: {
					String user_id = st.nextToken();
					if ((cc.user_id).equals(user_id)) {
						cc.dispose();
						return;
					}

					// 여기에서 서버에 있는 onlineThreadlist 와 DB에 있는 회원정보들을 불러와서
					// 비교한다음에 테이블을 수정.
					// 서버스레드의 onlineThreadlist에 어떻게 접근하지?
				}
					break;
				case Dprotocol.ROOM_CREATE_PERMIT: {
					String roomInfo = st.nextToken();// Json형식으로 받아온 정보
					// Json으로 변환
					JsonParser parser = new JsonParser();
					JsonElement element = parser.parse(roomInfo);
					// 변환후, key값에따라 자르기
					String token = element.getAsJsonObject().get("token").getAsString();
					String room_title = element.getAsJsonObject().get("room_title").getAsString();
					int room_type = element.getAsJsonObject().get("room_type").getAsInt();
					String room_game = element.getAsJsonObject().get("room_game").getAsString();
					String master_id = element.getAsJsonObject().get("master_id").getAsString();
					int p_limit = element.getAsJsonObject().get("p_limit").getAsInt();
					int p_current = element.getAsJsonObject().get("p_current").getAsInt();
					int port = element.getAsJsonObject().get("port").getAsInt();


					// 받아온 port번호로 소켓여는것까지 완료후 tap에 add함(방타입에 따라 나눔)
					if(room_type==0) {
						AuctionRoom ar = new AuctionRoom(cc, port);
						cc.tp.addTab(room_title, ar);
						cc.con.add(cc.tp, null);						
					}
					else if(room_type==1){
						VoiceChatRoom vr = new VoiceChatRoom(cc,port);
						cc.tp.addTab(room_title, vr);
						cc.con.add(cc.tp, null);
					}
					
					// 내가' 마스터인'방 RoomList테이블에 추가하기
					// 추후 현재인원/최대인원 컬럼 추가

					Vector<String> v = new Vector<>();
					v.add(cc.s_roomType[room_type] + " || " + room_game);
					v.add(room_title);
					v.add(token);
					cc.mv.dtm_roomList.addRow(v);
					

				}
					break;
				case Dprotocol.ROOM_IN: {
					int port = Integer.parseInt(st.nextToken());
					String room_title = st.nextToken();
					int room_type = Integer.parseInt(st.nextToken());
					
					if(room_type==0) {
						AuctionRoom ar = new AuctionRoom(cc, port);
						cc.tp.addTab(room_title, ar);
						cc.con.add(cc.tp, null);
					}
					else if(room_type==1) {
						VoiceChatRoom vr = new VoiceChatRoom(cc, port);
						cc.tp.addTab(room_title, vr);
						cc.con.add(cc.tp, null);
					}
				}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
