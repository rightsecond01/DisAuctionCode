package com.discodeaucion_ver03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

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
				case Dprotocol.LOG_IN: {
					String loginUserId = st.nextToken();
					String loginUserNick = st.nextToken();
					
					DefaultTableModel target_dtm = cc.mv.dtm_offline;
					int rowCount = target_dtm.getRowCount();
					int changeRow = -1;
					for (int i = 0; i < rowCount; i++) {
						if (loginUserId.equals(cc.mv.jtb_offline.getModel().getValueAt(i, 2))) {
							changeRow = i;
						}
					}
					
					if (changeRow >= 0) {
						cc.mv.dtm_offline.removeRow(changeRow);
						Vector<String> v = new Vector<>();
						v.add(loginUserNick + "(" + loginUserId + ")");
						v.add("");
						v.add(loginUserId);
						cc.mv.dtm_online.addRow(v);
					}
					
				}
				    break;
				case Dprotocol.F_ONLINE: {
					String fri_id = st.nextToken();
					String fri_name = st.nextToken();
					
					Vector<String> v = new Vector<>();
					v.add(fri_name + "(" + fri_id + ")");
					v.add("");
					v.add(fri_id);
					cc.mv.dtm_online.addRow(v);
				}
				    break;
				case Dprotocol.F_OFFLINE: {
					String fri_id = st.nextToken();
					String fri_name = st.nextToken();
					
					Vector<String> v = new Vector<>();
					v.add(fri_name + "(" + fri_id + ")");
					v.add("");
					v.add(fri_id);
					cc.mv.dtm_offline.addRow(v);
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

					// 개선방향 : 이 코드의 존재의미 고민
					Map<String, Object> acceptRoom = new HashMap<String, Object>();
					acceptRoom.put("token", token);
					acceptRoom.put("room_title", room_title);
					acceptRoom.put("room_type", room_type);
					acceptRoom.put("room_game", room_game);
					acceptRoom.put("master_id", master_id);
					acceptRoom.put("p_limit", p_limit);
					acceptRoom.put("p_current", p_current);
					cc.acceptRoomList.add(acceptRoom);

					Vector<String> v = new Vector<>();
					v.add(cc.s_roomType[room_type] + " || " + room_game);
					v.add(room_title);
					v.add(p_current + "/" + p_limit);
					v.add(token);
					cc.mv.dtm_roomList.addRow(v);

				}
					break;
				case Dprotocol.ROOM_ACCEPT_FAIL: {
					String cause_of_failure = st.nextToken();
					JOptionPane.showMessageDialog(cc, cause_of_failure);
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
					if (room_type == 0) {
						AuctionRoom ar = new AuctionRoom(cc, port, room_title, token);
						cc.tp.addTab(room_title, ar);
						cc.con.add(cc.tp, null);
						cc.tp.setSelectedIndex(cc.tp.getTabCount()-1);
					} else if (room_type == 1) {
						VoiceChatRoom vr = new VoiceChatRoom(cc, port, room_title, token);
						cc.tp.addTab(room_title, vr);
						cc.con.add(cc.tp, null);
						cc.tp.setSelectedIndex(cc.tp.getTabCount()-1);
					}

					// 내가' 마스터인'방 RoomList테이블에 추가하기
					// 추후 현재인원/최대인원 컬럼 추가

					Vector<String> v = new Vector<>();
					v.add(cc.s_roomType[room_type] + " || " + room_game);
					v.add(room_title);
					v.add(p_current + "/" + p_limit);
					v.add(token);
					cc.mv.dtm_roomList.addRow(v);

				}
					break;
				case Dprotocol.ROOM_IN: {
					String room_type = st.nextToken();
					int fileNum = Integer.parseInt(st.nextToken());
					String room_game = st.nextToken();
					int port = Integer.parseInt(st.nextToken());
					int p_current = Integer.parseInt(st.nextToken());
					String room_status = st.nextToken();
					String token = st.nextToken();
					List<Map<String, String>> onlineList = new Vector<Map<String, String>>();
					List<Map<String, String>> offlineList = new Vector<Map<String, String>>();
					JsonArray onlineUserList = null;
					JsonArray offlineUserList = null;
					

					if ("open".equals(room_status)) {
						String onlineUserList_String = st.nextToken();
						String offlineUserList_String = st.nextToken();
						JsonParser parser = new JsonParser();
						onlineUserList = (JsonArray) parser.parse(onlineUserList_String);
						offlineUserList = (JsonArray) parser.parse(offlineUserList_String);

					} else if ("close".equals(room_status)) {
						String offlineUserList_String = st.nextToken();
						JsonParser parser = new JsonParser();
						offlineUserList = (JsonArray) parser.parse(offlineUserList_String);
					}
					/// online상태인 방인원 onlineList에 담기
					if (onlineUserList != null) {
						for (int i = 0; i < onlineUserList.size(); i++) {
							Map<String, String> userMap = new HashMap<String, String>();
							String onlineUser_id = onlineUserList.get(i).getAsJsonObject().get("mem_id").getAsString();
							String onlineUser_nick = onlineUserList.get(i).getAsJsonObject().get("mem_nick")
									.getAsString();
							userMap.put("mem_id", onlineUser_id);
							userMap.put("mem_nick", onlineUser_nick);
							onlineList.add(userMap);
						}
					}

					/// offline상태인 방인원 offlineList에 담기
					for (int i = 0; i < offlineUserList.size(); i++) {
						Map<String, String> userMap = new HashMap<String, String>();
						String offlineUser_id = offlineUserList.get(i).getAsJsonObject().get("mem_id").getAsString();
						String offlineUser_nick = offlineUserList.get(i).getAsJsonObject().get("mem_nick")
								.getAsString();
						userMap.put("mem_id", offlineUser_id);
						userMap.put("mem_nick", offlineUser_nick);
						offlineList.add(userMap);
					}
					////////////위에서 저장한 멤버들의 프로필사진을 저장하는 코드/////////////
					////////////////////(on,off 관계없이)//////////////////////
					int memberSize = onlineList.size() + offlineList.size();
					ImagesReceiving ir = new ImagesReceiving(memberSize,token,cc.user_id);
					ir.start();
					////////////////////////////////////////////////////////
					////////////옥션룸일경우 아이템정보 미리 다운//////////////////////
					if(room_type.equals("AuctionRoom")) {
						PreImageReceiving pir = new PreImageReceiving(cc.user_id, token, room_game, fileNum);
						pir.start();
					}
					///////////////////////////////////////////////////////
					
					int row = cc.mv.jtb_roomList.getSelectedRow();
					if (row < 0) {
						JOptionPane.showMessageDialog(cc, "방을 선택하세요");						
					} else {
						try {
							// 개선사항 : 현재인원정보를 테이블에서 받아오는것이 아닌 ds.activiRoomlist에서 받아오는걸로 변경
							
							String roomInfo = (String) cc.mv.jtb_roomList.getModel().getValueAt(row, 0);
							String room_title = (String) cc.mv.jtb_roomList.getModel().getValueAt(row, 1);
							String personnelInfo = (String) cc.mv.jtb_roomList.getModel().getValueAt(row, 2);
							// 방정보 컬럼에서 "||"기준으로 게임타이틀/룸타입 두개의 정보로 나눔
							StringTokenizer ri_cut = new StringTokenizer(roomInfo, "||");
							String room_type_String = ri_cut.nextToken();
							String room_type_String_sub = room_type_String.substring(0, room_type_String.length() - 1);

							// 인원정보 컬럼에서 "/"기준으로 현재인원/최대인원 두개의 정보로 나눔
							StringTokenizer pi_cut = new StringTokenizer(personnelInfo, "/");
							String p_current_String = pi_cut.nextToken();
							int p_limit = Integer.parseInt(pi_cut.nextToken());

							// p_current_String을 방금 넘겨받은 최신정보인 p_current로 변경
							cc.mv.dtm_roomList.setValueAt(p_current + "/" + p_limit, row, 2);

							// 넘겨받은 선택한 테이블row의 정보==>String 형태의 room_type으로 비교하여 방의 형태 나눔
							if (room_type_String_sub.equals(cc.s_roomType[0])) {
								AuctionRoom ar = new AuctionRoom(cc, port, room_title, token, onlineList, offlineList);
								cc.tp.addTab(room_title, ar);
								cc.con.add(cc.tp, null);
								cc.tp.setSelectedIndex(cc.tp.getTabCount()-1);

							} else if (room_type_String_sub.equals(cc.s_roomType[1])) {
								VoiceChatRoom vr = new VoiceChatRoom(cc, port, room_title, token, onlineList, offlineList);
								cc.tp.addTab(room_title, vr);
								cc.con.add(cc.tp, null);
								cc.tp.setSelectedIndex(cc.tp.getTabCount()-1);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
					break;
				case Dprotocol.ROOM_IN_FAIL: {
					String cause_of_failure = st.nextToken();
					JOptionPane.showMessageDialog(cc, cause_of_failure);
				}
					break;
				case Dprotocol.LOG_OUT: {
					String logoutUserId = st.nextToken();
					String logoutUserNick = st.nextToken();
					if ((cc.user_id).equals(logoutUserId)) {
						cc.dispose();						
					}
					else {
						DefaultTableModel target_dtm = cc.mv.dtm_online;
						int rowCount = target_dtm.getRowCount();
						int changeRow = -1;
						for (int i = 0; i < rowCount; i++) {
							if (logoutUserId.equals(cc.mv.jtb_online.getModel().getValueAt(i, 2))) {
								changeRow = i;
							}
						}
						
						if (changeRow >= 0) {
							cc.mv.dtm_online.removeRow(changeRow);
							Vector<String> v = new Vector<>();
							v.add(logoutUserNick + "(" + logoutUserId + ")");
							v.add("");
							v.add(logoutUserId);
							cc.mv.dtm_offline.addRow(v);
						}
					}
				}
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}