package com.discodeaucion_ver02;

import java.awt.Color;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class AuctionRoomThread extends Thread {
//클라이언트쪽에서 접속한 옥션룸에게 서버가 보내는 명령을 처리하는 쓰레드입니다.
	AuctionRoom ar = null;

	public AuctionRoomThread(AuctionRoom ar) {
		this.ar = ar;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while (!isStop) {
			try {
				msg = (String) ar.room_ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
				case Dprotocol.ROOM_IN: {// 시점때문에 프로토콜을 보낸 '나'에겐 프로토콜이 전송되지않음.
					String user_id = st.nextToken();
					String nickName = st.nextToken();
					String token = st.nextToken();
					DefaultTableModel target_dtm_roomList = ar.cc.mv.dtm_roomList;
					int rowCount = target_dtm_roomList.getRowCount();
					int changeRow = -1;
					for (int i = 0; i < rowCount; i++) {
						if (token.equals(ar.cc.mv.jtb_roomList.getModel().getValueAt(i, 3))) {
							changeRow = i;
						}
					}

					SimpleAttributeSet sas = new SimpleAttributeSet();
					sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153, 000, 000));
					ar.sd_display.insertString(ar.sd_display.getLength(),
							"[" + nickName + "(" + user_id + ")] 님이 입장하셨습니다" + "\n", sas);

					if (changeRow >= 0) {
						String personnelInfo = (String) ar.cc.mv.jtb_roomList.getModel().getValueAt(changeRow, 2);
						StringTokenizer pi_cut = new StringTokenizer(personnelInfo, "/");
						int p_current = Integer.parseInt(pi_cut.nextToken());
						p_current = p_current + 1;
						int p_limit = Integer.parseInt(pi_cut.nextToken());
						target_dtm_roomList.setValueAt(p_current + "/" + p_limit, changeRow, 2);

						for (int i = 0; i < ar.dtm_offline.getRowCount(); i++) {
							if (user_id.equals(ar.dtm_offline.getValueAt(i, 2))) {
								ar.dtm_offline.removeRow(i);
							}
						}

						Vector v = new Vector<>();
						v.add(nickName+ "(" + user_id + ")");
						v.add("");
						v.add(user_id);
						ar.dtm_online.addRow(v);
					} else if (changeRow < 0) {
						JOptionPane.showMessageDialog(ar, "에러코드203: 치명적인 에러발생");
						System.exit(0);
					}
				}
					break;
				case Dprotocol.MESSAGE: {
					// 개선사항 : talkPanel에서 패널 받아와 sd_display에 붙이는 형식으로 수정
					String sender_nick = st.nextToken();
					String sender_id = st.nextToken();
					String message = st.nextToken();
					ar.sd_display.insertString(ar.sd_display.getLength(),
							"[" + sender_nick + "(" + sender_id + ")] : " + message + "\n", null);
				}
					break;
				case Dprotocol.ROOM_OUT: {
					String out_user = st.nextToken();
					String out_user_nick = st.nextToken();
					
					// 개선사항 : 현재인원정보를 테이블에서 받아오는것이 아닌 ds.activiRoomlist에서 받아오는걸로 변경
					////////////////// RoomList현재인원, OnOff테이블 최신화 하는 코드////////////////////
					DefaultTableModel target_dtm_roomList = ar.cc.mv.dtm_roomList;
					int rowCount = target_dtm_roomList.getRowCount();
					int changeRow = -1;
					for (int i = 0; i < rowCount; i++) {
						if (ar.token.equals(ar.cc.mv.jtb_roomList.getModel().getValueAt(i, 3))) {
							changeRow = i;
						}
					}
					if (changeRow >= 0) {
						String personnelInfo = (String) ar.cc.mv.jtb_roomList.getModel().getValueAt(changeRow, 2);
						StringTokenizer pi_cut = new StringTokenizer(personnelInfo, "/");
						int p_current = Integer.parseInt(pi_cut.nextToken());
						p_current = p_current - 1;
						int p_limit = Integer.parseInt(pi_cut.nextToken());
						target_dtm_roomList.setValueAt(p_current + "/" + p_limit, changeRow, 2);

						for (int i = 0; i < ar.dtm_online.getRowCount(); i++) {
							if (out_user.equals(ar.dtm_online.getValueAt(i, 2))) {
								ar.dtm_online.removeRow(i);
							}
						}

						Vector v = new Vector<>();
						v.add(out_user_nick + "(" + out_user + ")");
						v.add("");
						v.add(out_user);
						ar.dtm_offline.addRow(v);
					} else {
						JOptionPane.showMessageDialog(ar, "에러코드203: 치명적인 에러발생");
						System.exit(0);
					}
					////////////////// RoomList현재인원, OnOff테이블 최신화 하는 코드 끝 //////////////////
					if (out_user.equals(ar.cc.user_id)) {// 나가는 사람이 '나'인경우
						for (int i = 0; i < ar.cc.tp.getTabCount(); i++) {
							if ((ar.jlb_title.getText()).equals(ar.cc.tp.getTitleAt(i))) {
								ar.cc.tp.remove(i);
							}
						}
						ar.roomSocket.close();
						isStop = true;
					} else {
						SimpleAttributeSet sas = new SimpleAttributeSet();
						sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153, 000, 000));
						ar.sd_display.insertString(ar.sd_display.getLength(),
								"[" + out_user_nick + "(" + out_user + ")] 님이 퇴장하셨습니다" + "\n", sas);
					}
				}
					break;
				case Dprotocol.ROOM_EXIT:{ 
					String out_user = st.nextToken();
					String out_user_nick = st.nextToken();
					
					// 개선사항 : 현재인원정보를 테이블에서 받아오는것이 아닌 ds.activiRoomlist에서 받아오는걸로 변경
					////////////////// RoomList현재인원, OnOff테이블 최신화 하는 코드////////////////////
					DefaultTableModel target_dtm_roomList = ar.cc.mv.dtm_roomList;
					int rowCount = target_dtm_roomList.getRowCount();
					int changeRow = -1;
					for (int i = 0; i < rowCount; i++) {
						if (ar.token.equals(ar.cc.mv.jtb_roomList.getModel().getValueAt(i, 3))) {
							changeRow = i;
						}
					}
					if (changeRow >= 0) {
						String personnelInfo = (String) ar.cc.mv.jtb_roomList.getModel().getValueAt(changeRow, 2);
						StringTokenizer pi_cut = new StringTokenizer(personnelInfo, "/");
						int p_current = Integer.parseInt(pi_cut.nextToken());
						p_current = p_current - 1;
						int p_limit = Integer.parseInt(pi_cut.nextToken());
						target_dtm_roomList.setValueAt(p_current + "/" + p_limit, changeRow, 2);

						for (int i = 0; i < ar.dtm_online.getRowCount(); i++) {
							if (out_user.equals(ar.dtm_online.getValueAt(i, 2))) {
								ar.dtm_online.removeRow(i);
							}
						}

						Vector v = new Vector<>();
						v.add(out_user_nick + "(" + out_user + ")");
						v.add("");
						v.add(out_user);
						ar.dtm_offline.addRow(v);
					} else {
						JOptionPane.showMessageDialog(ar, "에러코드203: 치명적인 에러발생");
						System.exit(0);
					}
					////////////////// RoomList현재인원, OnOff테이블 최신화 하는 코드 끝 //////////////////
					if (out_user.equals(ar.cc.user_id)) {// 나가는 사람이 '나'인경우
						for (int i = 0; i < ar.cc.tp.getTabCount(); i++) {
							if ((ar.jlb_title.getText()).equals(ar.cc.tp.getTitleAt(i))) {
								ar.cc.tp.remove(i);
							}
						}
						for (int i=0; i<ar.cc.mv.dtm_roomList.getRowCount();i++) {
							if((ar.token).equals(ar.cc.mv.dtm_roomList.getValueAt(i, 3))) {
								ar.cc.mv.dtm_roomList.removeRow(i);
							}
						}
						ar.roomSocket.close();
						isStop = true;
					} else {
						SimpleAttributeSet sas = new SimpleAttributeSet();
						sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153, 000, 000));
						ar.sd_display.insertString(ar.sd_display.getLength(),
								"[" + out_user_nick + "(" + out_user + ")] 님이 방과의 연결을 끊으셨습니다." + "\n", sas);
					}
				}
				break;
				case Dprotocol.ROOM_EXIT_FAIL:{
					String cause_of_failure = st.nextToken();
					JOptionPane.showMessageDialog(ar, cause_of_failure);
				}
				break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
