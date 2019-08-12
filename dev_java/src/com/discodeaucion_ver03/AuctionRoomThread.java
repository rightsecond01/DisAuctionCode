package com.discodeaucion_ver03;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.util.ImageResize;

public class AuctionRoomThread extends Thread {
//클라이언트쪽에서 접속한 옥션룸에게 서버가 보내는 명령을 처리하는 쓰레드입니다.
	AuctionRoom ar = null;

	public AuctionRoomThread(AuctionRoom ar) {
		this.ar = ar;
	}
	
	private void insertComponent(JComponent comp) {
		  try {
		    ar.jtp_display.getDocument().insertString(ar.jtp_display.getDocument().getLength(), " ", null);
		    ar.jtp_display.getDocument().insertString(ar.jtp_display.getDocument().getLength(), "\n\n\n\n", null);
		  } catch (BadLocationException ex1) {
		    // Ignore
		  }
		  try {
			  ar.jtp_display.setCaretPosition(ar.jtp_display.getDocument().getLength() - 1);
		  } catch (Exception ex) {
			  ar.jtp_display.setCaretPosition(0);
		  }
		  ar.jtp_display.insertComponent(comp);
		}
	
	public String resizeImage(String filePath) {
		String newPath = null;
		///////////////////////////////////
		/////////디렉토리 생성 코드//////////////
		String resizeSaveFolderPath = ar.uploadImgMyFolderSavePath + "ImageResize\\";
		File resizeSaveFolder = new File(resizeSaveFolderPath);
		if(!resizeSaveFolder.exists()) {
			resizeSaveFolder.mkdir();
		}
		////////끝//////////
		///////////////////		
		File resizeFile = new File(filePath);		
		String tmpPath = resizeFile.getParent();
		String tmpFileName = resizeFile.getName();
		int extPosition = tmpFileName.indexOf("png");
		
		if(extPosition != -1){
			ImageIcon ic = ImageResize.resizeImage(filePath, 700, 700);
			if(ic!=null) {
				Image i = ic.getImage();
				BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D g2 = bi.createGraphics();
				g2.drawImage(i, 0, 0, null);
				g2.dispose();
				
				String newFileName = tmpFileName.replaceFirst(".png", "_resize.png");
				newPath = tmpPath +"\\ImageResize\\"+newFileName;
				try {
					ImageIO.write(bi, "png", new File(newPath));
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}
		return newPath;
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
				case Dprotocol.MESSAGE: {
					String sender_nick = st.nextToken();
					String sender_id = st.nextToken();
					String token = st.nextToken();
					String roomType = st.nextToken();
					String message = st.nextToken();
					
					
					TalkPanel tp = new TalkPanel(ar.cc.user_id, token, roomType);					
					JPanel jp_msg = tp.textPanel(message, sender_id,sender_nick);
					
					insertComponent(jp_msg);
					
				}
					break;
				case Dprotocol.IMAGE_UPLOAD: {
					int portNum = Integer.parseInt(st.nextToken());
					String uploadImgFileName = st.nextToken();
					String sender_id = st.nextToken();
					String sender_nick = st.nextToken();
					
					if(!sender_id.equals(ar.cc.user_id)) {						
						UploadImgReceiving uir = new UploadImgReceiving(ar.token,uploadImgFileName,portNum
								,ar.uploadImgMyFolderSavePath);
						uir.start();
						uir.join();
					}
					
				
					String imgPath = ar.uploadImgMyFolderSavePath + uploadImgFileName;
					String resizePath = resizeImage(imgPath);
					
					TalkPanel tp = new TalkPanel(ar.cc.user_id, ar.token,TalkPanel.AuctionRoom);
					JPanel jp_image = tp.imagePanel(resizePath, sender_id, sender_nick);
					
					insertComponent(jp_image);
				}
				    break;
				case Dprotocol.REG_TRADE:{
					String sender_id = st.nextToken();
					String sender_nick = st.nextToken();
					String itemName = st.nextToken();
					int itemPrice = Integer.parseInt(st.nextToken());
					String itemNotes = st.nextToken(); 
					
					TalkPanel tp = new TalkPanel(ar.cc.user_id,ar.token,TalkPanel.AuctionRoom);
					
					Map<String,String> sellInfo = new HashMap<String, String>();
					sellInfo.put("itemName", itemName);
					sellInfo.put("itemPrice", itemPrice+"");
					sellInfo.put("itemNotes", itemNotes);
					JPanel jp_trade = tp.tradePanel(sender_id, sender_nick, sellInfo);
					
					insertComponent(jp_trade);
				}

				    break;
				case Dprotocol.ADD_FRIEND:{
					String addFriendUser = st.nextToken();
					String addUser_nickname = st.nextToken();
					String addedFriendUser = st.nextToken();
					String addedUser = st.nextToken();
					
					if(ar.cc.user_id.equals(addFriendUser)) {
						
						JOptionPane.showMessageDialog(ar, addedUser + "님이 친구로 등록되었습니다.");
					}
					else if(ar.cc.user_id.equals(addedFriendUser)) {
						int result = JOptionPane.showConfirmDialog(ar
								   , addUser_nickname +"("+addFriendUser+")" + "님이 나를 친구로 등록하였습니다.\n"
						           + addUser_nickname +"("+addFriendUser+")" + "를 친구로 등록하시겠습니까?"
								   , "알림",JOptionPane.YES_NO_OPTION	   
								);
						
						if(result == JOptionPane.CLOSED_OPTION) {
							JOptionPane.showMessageDialog(ar, "친구로 등록하지 않았습니다.");
						}
						else if(result == JOptionPane.YES_OPTION) {
							ar.room_oos.writeObject(Dprotocol.ADD_FRIEND
		          					   + Dprotocol.seperator + ar.cc.user_id
		          					   + Dprotocol.seperator + ar.cc.nickName
							           + Dprotocol.seperator + addFriendUser
							           + Dprotocol.seperator + addUser_nickname
							           );
						}
						else if(result == JOptionPane.NO_OPTION) {
							JOptionPane.showMessageDialog(ar, "친구로 등록하지 않았습니다.");
						}
					}
				}
				    break;
				case Dprotocol.ADD_FRIEND_FAIL:{
					String error_msg = st.nextToken();
					JOptionPane.showMessageDialog(ar,error_msg);
					
				}
				   break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}