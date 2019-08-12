package com.network1;

import java.awt.Color;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TalkClientThread extends Thread {
	TalkClient tc = null;
	
	

	public TalkClientThread(TalkClient tc) {
		this.tc = tc;
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
				msg = (String) tc.ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Protocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
				
				case Protocol.ROOM_IN: {
					String nickName = st.nextToken();
					Vector<String> v_name = new Vector();
					v_name.add(nickName);
					tc.dtm_name.addRow(v_name);
					SimpleAttributeSet sas = new SimpleAttributeSet();
					sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153,000,000));
					tc.sd_display.insertString(tc.sd_display.getLength(), nickName + "님이 입장하였습니다.\n", sas);

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
									tc.sd_display.insertString(tc.sd_display.getLength()
											, "[" + nickName + "] : " + "\n",null);
									tc.sd_display.insertString(tc.sd_display.getLength(), "\n", attr1);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
					} else {
						try {
							tc.sd_display.insertString(tc.sd_display.getLength(), "[" + nickName + "] : " + message + "\n",
									null);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					tc.jtp_display.setCaretPosition(tc.sd_display.getLength());

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
						if(tc.nickName.equals(receiver)&&tc.nickName.equals(sender)) {//내가 나에게 귓속말을 보낸경우
							try {
								tc.sd_display.insertString(tc.sd_display.getLength(),
										"자신에게는 귓속말을 보낼 수 없습니다. \n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else if(tc.nickName.equals(sender)) {//내가 보낸 귓속말 화면에 띄우기
							int i = 0;
							for (i = 0; i < emo.imgFiles.length; i++) {
								if (emo.imgFiles[i].equals(imgChoice)) {
									StyleConstants.setIcon(attr1, new ImageIcon(emo.imgPath + emo.imgFiles[i]));
									try {
										tc.sd_display.insertString(tc.sd_display.getLength()
												,"{" + receiver + "} 님에게 보낸 귓속말 : " + "\n",sas);
										tc.sd_display.insertString(tc.sd_display.getLength(), "\n", attr1);
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
											tc.sd_display.insertString(tc.sd_display.getLength()
													,"{" + sender + "} 님의 귓속말 : " + "\n",sas);
											tc.sd_display.insertString(tc.sd_display.getLength(), "\n", attr1);
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
						if(tc.nickName.equals(receiver)&&tc.nickName.equals(sender)) {//내가 나에게 귓속말을 보낸경우
							try {
								tc.sd_display.insertString(tc.sd_display.getLength(),
										"자신에게는 귓속말을 보낼 수 없습니다. \n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else if(tc.nickName.equals(sender)) {//내가 보낸 귓속말 화면에 띄우기
							try {								
								tc.sd_display.insertString(tc.sd_display.getLength(),
										"{" + receiver + "} 님에게 보낸 귓속말 : " + message + "\n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						else {// 받은 귓속말 화면에 띄우기
							try {
								
								tc.sd_display.insertString(tc.sd_display.getLength(),
										"{" + sender + "} 님의 귓속말 : " + message + "\n", sas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					tc.jtp_display.setCaretPosition(tc.sd_display.getLength());

				}
					break;
					
				case Protocol.CHANGE: {
					String nickName = st.nextToken();//바꾸기전이름
					String afterName = st.nextToken();//바꾼이름
					String notice = st.nextToken();
					
					if(tc.nickName.equals(nickName)) {
						for(int i=0;i<tc.dtm_name.getRowCount();i++) {
							String currentName = (String)tc.dtm_name.getValueAt(i, 0);
							if(currentName.equals(nickName)) {//같은이름 있는경우 한번만 실행됨
								tc.dtm_name.setValueAt(afterName, i, 0);		
								SimpleAttributeSet sas = new SimpleAttributeSet();
								sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153,000,000));
								try {
									tc.sd_display.insertString(tc.sd_display.getLength(), notice+"\n", sas);									
								} catch (Exception e) {
									e.printStackTrace();
								}
								tc.setTitle(afterName+"님의 대화창");
								tc.nickName = afterName;
							}
						}
					}
					else {
						for(int i=0;i<tc.dtm_name.getRowCount();i++) {
							String currentName = (String)tc.dtm_name.getValueAt(i, 0);
							if(currentName.equals(nickName)) {
								tc.dtm_name.setValueAt(afterName, i, 0);
							}
							SimpleAttributeSet sas = new SimpleAttributeSet();
							sas.addAttribute(StyleConstants.ColorConstants.Foreground, new Color(153,000,000));
							try {
								tc.sd_display.insertString(tc.sd_display.getLength(), notice+"\n", sas);									
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}		
					
				} break;
				

				case Protocol.ROOM_OUT: {
					String nickName = st.nextToken();
					if (tc.nickName.equals(nickName)) {
						System.exit(0);
					} else {
						Vector v = tc.dtm_name.getDataVector();
						int deleteRowNum = 0;
						for (int i = 0; i < v.size(); i++) {
							if ((v.get(i).toString()).equals("[" + nickName + "]")) {
								deleteRowNum = i;
							}
						}
						tc.dtm_name.removeRow(deleteRowNum);
						tc.jtb_name.repaint();
						tc.sd_display.insertString(tc.sd_display.getLength()
						                  		, "[[" + nickName + "]]" + "님이 퇴장 하셨습니다.\n"
						                        ,null);					
						tc.jtp_display.setCaretPosition(tc.sd_display.getLength());
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
