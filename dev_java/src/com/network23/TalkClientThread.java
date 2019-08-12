package com.network23;

import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JScrollBar;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.network1.Protocol;

public class TalkClientThread extends Thread {
	
	TalkClientVer2 tc2 = null;	

	public TalkClientThread(TalkClientVer2 tc2) {
		this.tc2 = tc2;
	}

	public SimpleAttributeSet makeAttribute() {
		SimpleAttributeSet sas = new SimpleAttributeSet();
		sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(0,051,153));
		return sas;
	}

	public void run() {
		String msg = null;
		boolean isStop = false;
		while (!isStop) {
			try {
				msg = (String) tc2.ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
				case Protocol.WAIT:{
					String nickName = st.nextToken();
					String state = st.nextToken();
					Vector<String> v_nick = new Vector<>();
					v_nick.add(nickName);
					v_nick.add(state);
					tc2.wr.dtm_wait.addRow(v_nick);
					//테이블 스크롤바를 자동위치 변경
					tc2.wr.jsp_wait.getVerticalScrollBar()
					.addAdjustmentListener(
							new AdjustmentListener() {
								@Override
								public void adjustmentValueChanged(AdjustmentEvent e) {
									JScrollBar jsb = (JScrollBar)e.getSource();
									jsb.setValue(jsb.getMaximum());
								}
							});
				}
				break;
				
				case Protocol.ROOM_CREATE:{
					String roomTitle = st.nextToken();
			    	String currentNum = st.nextToken();
			    	Vector<String> v_room = new Vector<>();
			    	v_room.add(roomTitle);
			    	v_room.add(currentNum);
			    	tc2.wr.dtm_room.addRow(v_room);
			    	tc2.wr.jsp_room.getVerticalScrollBar()
			    	.addAdjustmentListener(
			    		new AdjustmentListener() {
							@Override
							public void adjustmentValueChanged(AdjustmentEvent e) {
								JScrollBar jsb = (JScrollBar)e.getSource();
								jsb.setValue(jsb.getMaximum());
							}
			    	});				    	
				}
			    	break;
				case Protocol.ROOM_LIST:{
					String roomTitle = st.nextToken();
					String currentNum = st.nextToken();
					Vector<String> v_room = new Vector<>();
					v_room.add(roomTitle);
					v_room.add(currentNum);
					tc2.wr.dtm_room.addRow(v_room);
					tc2.wr.jsp_room.getVerticalScrollBar()
			    	.addAdjustmentListener(
			    			new AdjustmentListener() {
			    				@Override
			    				public void adjustmentValueChanged(AdjustmentEvent e) {
			    					JScrollBar jsb = (JScrollBar)e.getSource();
			    					jsb.setValue(jsb.getMaximum());
			    				}
			    			});				    
				}    
				
				break;
				
				case Protocol.ROOM_IN: {
					String roomTitle = st.nextToken();
					String current = st.nextToken();
					String nickName = st.nextToken();
					
					for(int i=0;i<tc2.wr.jtb_room.getRowCount();i++) {
						if(roomTitle.equals(tc2.wr.dtm_room.getValueAt(i, 0))) {
							tc2.wr.dtm_room.setValueAt(current, i, 1);							
							break;
						}
					}
					for(int i=0;i<tc2.wr.jtb_wait.getRowCount();i++) {
						if(nickName.equals((String)tc2.wr.dtm_wait.getValueAt(i, 0))) {
							tc2.wr.dtm_wait.setValueAt(roomTitle, i, 1);
						}
					}
					if(tc2.nickName.equals(nickName)) {
						tc2.tp.setSelectedIndex(1);
						for(int x=0;x<tc2.wr.jtb_wait.getRowCount();x++) {
							if(roomTitle.equals(tc2.wr.dtm_wait.getValueAt(x, 1))) {
								String imsi[] = {(String)tc2.wr.dtm_wait.getValueAt(x, 0)};
								tc2.mr.dtm_name.addRow(imsi);
							}
						}
					}		
					
					SimpleAttributeSet sas = new SimpleAttributeSet();
					sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153,000,000));
					try {
						tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), nickName + "님이 입장하였습니다.\n", sas);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
					break;
					
				case Protocol.ROOM_INLIST:{
					String roomTitle = st.nextToken();
					String currentNum = st.nextToken();
					String nickName = st.nextToken();
					Vector<String> v_room = new Vector<>();
					v_room.add(nickName);
					tc2.mr.dtm_name.addRow(v_room);
				}
				break;
				

				case Protocol.MESSAGE: {
					EmoticonMessage emo = new EmoticonMessage();
					String nickName = st.nextToken();
					String message = st.nextToken();
					String imgChoice = st.nextToken();
					MutableAttributeSet attr1 = new SimpleAttributeSet();
					if (!imgChoice.equals("default")) {
						int i = 0;
						for (i = 0; i < emo.imgFiles.length; i++) {
							if (emo.imgFiles[i].equals(imgChoice)) {
								StyleConstants.setIcon(attr1, new ImageIcon(emo.imgPath + emo.imgFiles[i]));
								try {									
									tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength()
											, "[" + nickName + "] : " + "\n",null);
									tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), "\n", attr1);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} else {
						try {
							tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), "[" + nickName + "] : " + message + "\n",
									null);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					tc2.mr.jtp_display.setCaretPosition(tc2.mr.sd_display.getLength());

				}
					break;				

				case Protocol.WHISHER: {
					EmoticonMessage emo = new EmoticonMessage();
					String sender = st.nextToken();
					String message = st.nextToken();
					String imgChoice = st.nextToken();
					String receiver = st.nextToken();
					SimpleAttributeSet sas = makeAttribute();//귓속말일때, 색을 바꿔주는 속성
					
					MutableAttributeSet attr1 = new SimpleAttributeSet();
					if (!imgChoice.equals("default")) {//이모티콘일때
						if(tc2.nickName.equals(receiver)&&tc2.nickName.equals(sender)) {//내가 나에게 귓속말을 보낸경우
							try {
								tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(),
										"자신에게는 귓속말을 보낼 수 없습니다. \n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else if(tc2.nickName.equals(sender)) {//내가 보낸 귓속말 화면에 띄우기
							int i = 0;
							for (i = 0; i < emo.imgFiles.length; i++) {
								if (emo.imgFiles[i].equals(imgChoice)) {
									StyleConstants.setIcon(attr1, new ImageIcon(emo.imgPath + emo.imgFiles[i]));
									try {
										tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength()
												,"{" + receiver + "} 님에게 보낸 귓속말 : " + "\n",sas);
										tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), "\n", attr1);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
						else {// 받은 귓속말 화면에 띄우기
							try {
								int i = 0;
								for (i = 0; i < emo.imgFiles.length; i++) {
									if (emo.imgFiles[i].equals(imgChoice)) {
										StyleConstants.setIcon(attr1, new ImageIcon(emo.imgPath + emo.imgFiles[i]));
										try {
											tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength()
													,"{" + sender + "} 님의 귓속말 : " + "\n",sas);
											tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), "\n", attr1);
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						
						
					} else {//이모티콘 아닐때
						if(tc2.nickName.equals(receiver)&&tc2.nickName.equals(sender)) {//내가 나에게 귓속말을 보낸경우
							try {
								tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(),
										"자신에게는 귓속말을 보낼 수 없습니다. \n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else if(tc2.nickName.equals(sender)) {//내가 보낸 귓속말 화면에 띄우기
							try {								
								tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(),
										"{" + receiver + "} 님에게 보낸 귓속말 : " + message + "\n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else {// 받은 귓속말 화면에 띄우기
							try {
								
								tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(),
										"{" + sender + "} 님의 귓속말 : " + message + "\n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					tc2.mr.jtp_display.setCaretPosition(tc2.mr.sd_display.getLength());

				}
					break;
					
				case Protocol.CHANGE: {
					String nickName = st.nextToken();//바꾸기전이름
					String afterName = st.nextToken();//바꾼이름
					String notice = st.nextToken();
					
					if(tc2.nickName.equals(nickName)) {
						for(int i=0;i<tc2.mr.dtm_name.getRowCount();i++) {
							String currentName = (String)tc2.mr.dtm_name.getValueAt(i, 0);
							if(currentName.equals(nickName)) {//같은이름 있는경우 한번만 실행됨
								tc2.mr.dtm_name.setValueAt(afterName, i, 0);		
								SimpleAttributeSet sas = new SimpleAttributeSet();
								sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153,000,000));
								try {
									tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), notice+"\n", sas);									
								} catch (Exception e) {
									e.printStackTrace();
								}
								tc2.setTitle(afterName+"님의 대화창");
								tc2.nickName = afterName;
							}
						}
					}
					else {
						for(int i=0;i<tc2.mr.dtm_name.getRowCount();i++) {
							String currentName = (String)tc2.mr.dtm_name.getValueAt(i, 0);
							if(currentName.equals(nickName)) {
								tc2.mr.dtm_name.setValueAt(afterName, i, 0);
							}
							SimpleAttributeSet sas = new SimpleAttributeSet();
							sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153,000,000));
							try {
								tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength(), notice+"\n", sas);									
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}		
					
				} break;
				

				case Protocol.ROOM_OUT: {
					String nickName = st.nextToken();
					if (tc2.nickName.equals(nickName)) {
						System.exit(0);
					} else {
						Vector v = tc2.mr.dtm_name.getDataVector();
						int deleteRowNum = 0;
						for (int i = 0; i < v.size(); i++) {
							if ((v.get(i).toString()).equals("[" + nickName + "]")) {
								deleteRowNum = i;
							}
						}
						tc2.mr.dtm_name.removeRow(deleteRowNum);
						tc2.mr.jtb_name.repaint();
						tc2.mr.sd_display.insertString(tc2.mr.sd_display.getLength()
						                  		, "[[" + nickName + "]]" + "님이 퇴장 하셨습니다.\n"
						                        ,null);					
						tc2.mr.jtp_display.setCaretPosition(tc2.mr.sd_display.getLength());
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
