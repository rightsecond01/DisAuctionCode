package com.discodeaucion_ver03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.PageAttributes.OriginType;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class TalkPanel {
	final public static String AuctionRoom = "au";
	final public static String VoiceChatRoom = "vc";
	
	String my_id = null;
	String token = null;
	String room_type = null;
	
	public TalkPanel(String my_id, String token, String room_type) {
		this.my_id = my_id;
		this.token = token;
		this.room_type = room_type;
	}

	public JPanel textPanel(String msg, String user_id,String sender_nick) {
		int gap = 10;
		int oneLine_height = 18;
		int count = countChangeLine(msg);// 몇번 개행처리가 되는가에 대한 변수
		int profile_width = 75;
		int profile_height = 75;
		int otr_width = 700 - profile_width - gap;
		int otr_height = 20 + oneLine_height * count + 20;
		/////////// JPanel 선언부///////////////
		JPanel jp_text = new JPanel();
		JPanel jp_profile = new JPanel();// 좌
		JPanel jp_others = new JPanel();// 우
		JPanel jp_others_info = new JPanel();
		JPanel jp_others_info_nick = new JPanel();
		JPanel jp_others_info_time = new JPanel();
		JPanel jp_others_msg = new JPanel();
		///////// 기타 Component 선언부////////////
		JTextArea jta_TalkBox = new JTextArea();
		JScrollPane jsp_TalkBox = new JScrollPane(jta_TalkBox, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JLabel jlb_profile = new JLabel();
		JLabel jlb_nickName = new JLabel();
		JLabel jlb_cTime = new JLabel();
		
		//voiceChat인경우
		
		

		///////////// setLayout////////////////
		jp_text.setLayout(new LinearLayout(10));
		jp_profile.setLayout(new BorderLayout());
		jp_others.setLayout(new LinearLayout(Orientation.VERTICAL,10));
		jp_others_info.setLayout(new LinearLayout(10));
		jp_others_info_nick.setLayout(new BorderLayout());
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_msg.setLayout(new BorderLayout());
		////////// add,setPreferredSize,기타/////////
		//// jp_profile처리부분////
		JLabel jlb_gameinfo = new JLabel();//// VoiceChat에서만 사용
		if (room_type.equals(AuctionRoom)) {
			jp_profile.setPreferredSize(new Dimension(profile_width, profile_height));
			jp_profile.add("Center", jlb_profile);
		} else if (room_type.equals(VoiceChatRoom)) {
			JPanel jp_profile_profile = new JPanel();//// VoiceChat에서만 사용
			JPanel jp_profile_gameInfo = new JPanel();//// VoiceChat에서만 사용
			//////////////////////////////////////////////////////////
			jp_profile.setLayout(new LinearLayout(Orientation.VERTICAL, 10));
			jp_profile_profile.setLayout(new BorderLayout());
			jp_profile_gameInfo.setLayout(new BorderLayout());
			jp_profile_profile.setPreferredSize(new Dimension(profile_width, profile_height));
			jp_profile_gameInfo.setPreferredSize(new Dimension(profile_width, 20));
			jp_profile_profile.add(jlb_profile);
			jp_profile_gameInfo.add("Center",jlb_gameinfo);
			jp_profile.setPreferredSize(new Dimension(profile_width, profile_height + 30));
			jp_profile.add(jp_profile_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
			jp_profile.add(jp_profile_gameInfo, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
			
			otr_width = otr_width - 200;
		}
		//// jp_others 처리부분////
		// jp_others_info//
		jp_others_info.setPreferredSize(new Dimension(otr_width, oneLine_height));
		jp_others_info_nick.add("Center", jlb_nickName);
		jp_others_info_time.add("Center", jlb_cTime);
		jp_others_info.add(jp_others_info_nick,
				new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,
				new LinearConstraints().setWeight(5).setLinearSpace(LinearSpace.MATCH_PARENT));

		// jp_others_msg//
		jsp_TalkBox.setPreferredSize(new Dimension(otr_width, oneLine_height * count));
		jta_TalkBox.setLineWrap(true);
		jta_TalkBox.append(msg);
		jp_others_msg.add("Center", jsp_TalkBox);

		// jp_others//
		jp_others.setPreferredSize(new Dimension(otr_width, otr_height));
		jp_others.add(jp_others_info, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_others.add(jp_others_msg, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));

		////// jp_text add//////
		jp_text.add(jp_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_text.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		
		setProfile(jlb_profile,user_id);
		jlb_nickName.setText(sender_nick + "("+user_id+")");
		setCurrentTime(jlb_cTime);
		
		if(user_id.equals("test")) {
			jlb_gameinfo.setText("티어 : 골드");
		}
		else if(user_id.equals("test1")) {
			jlb_gameinfo.setText("티어 : 실버");
		}
		else if(user_id.equals("test2")) {
			jlb_gameinfo.setText("티어 : 골드");
		}
		else if(user_id.equals("test3")) {
			jlb_gameinfo.setText("티어 : 플레");
		}
		else {
			jlb_gameinfo.setText("티어 : 브론즈");
		}
		jp_others.setBackground(new Color(105,110,138));
		jp_others_info.setBackground(new Color(105,110,138));
		jp_text.setBackground(new Color(105,110,138));
		
		return jp_text;
	}

	public JPanel imagePanel(String imgPath, String user_id, String sender_nick) {
		int gap = 10;
		int oneLine_height = 18;
		int profile_width = 75;
		int profile_height = 75;
		int otr_width = 700 - profile_width - gap;

		/////////// JPanel 선언부///////////////
		JPanel jp_image = new JPanel();
		JPanel jp_profile = new JPanel();// 좌
		JPanel jp_others = new JPanel();// 우
		JPanel jp_others_info = new JPanel();
		JPanel jp_others_info_nick = new JPanel();
		JPanel jp_others_info_time = new JPanel();
		JPanel jp_others_img = new JPanel();
		///////// 기타 Component 선언부////////////
		JLabel jlb_profile = new JLabel();
		JLabel jlb_nickName = new JLabel();
		JLabel jlb_cTime = new JLabel();
		JLabel jlb_img = new JLabel();

		///////////// setLayout////////////////
		jp_image.setLayout(new LinearLayout(10));
		jp_profile.setLayout(new BorderLayout());
		jp_others.setLayout(new LinearLayout(Orientation.VERTICAL,10));
		jp_others_info.setLayout(new LinearLayout(10));
		jp_others_info_nick.setLayout(new BorderLayout());
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_img.setLayout(new BorderLayout());

		////////// add,setPreferredSize,기타/////////
	    //// jp_profile처리부분////
		JLabel jlb_gameinfo = new JLabel();//// VoiceChat에서만 사용
			if (room_type.equals(AuctionRoom)) {
				jp_profile.setPreferredSize(new Dimension(profile_width, profile_height));
				jp_profile.add("Center", jlb_profile);
			} else if (room_type.equals(VoiceChatRoom)) {
				JPanel jp_profile_profile = new JPanel();//// VoiceChat에서만 사용
				JPanel jp_profile_gameInfo = new JPanel();//// VoiceChat에서만 사용
				jp_profile.setLayout(new LinearLayout(Orientation.VERTICAL, 10));
				jp_profile_profile.setLayout(new BorderLayout());
				jp_profile_gameInfo.setLayout(new BorderLayout());
				jp_profile_profile.setPreferredSize(new Dimension(profile_width, profile_height));
				jp_profile_gameInfo.setPreferredSize(new Dimension(profile_width, 20));
				jp_profile_profile.add(jlb_profile);
				jp_profile_gameInfo.add("Center",jlb_gameinfo);
				jp_profile.setPreferredSize(new Dimension(profile_width, profile_height + 30));
				jp_profile.add(jp_profile_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
				jp_profile.add(jp_profile_gameInfo, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
				
				otr_width = otr_width - 200;
			}
		//// jp_others 처리부분////
		// jp_others_info//
		jp_others_info.setPreferredSize(new Dimension(otr_width, oneLine_height));
		jp_others_info_nick.add("Center", jlb_nickName);
		jp_others_info_time.add("Center", jlb_cTime);
		jp_others_info.add(jp_others_info_nick,
				new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,
				new LinearConstraints().setWeight(5).setLinearSpace(LinearSpace.MATCH_PARENT));

		// jp_others_img//
		File imgFile = new File(imgPath);//imgPath
		BufferedImage b_img = null;
		try {
			b_img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		int img_height = b_img.getHeight();
		jlb_img.setIcon(new ImageIcon(b_img));
		jp_others_img.add("Center", jlb_img);

		// jp_others//
		int otr_height = img_height + 50;/* 이미지의 크기에 따라 jp_others의 크기 변함*/
		jp_others.setPreferredSize(new Dimension(otr_width, otr_height));
		jp_others.add(jp_others_info, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others.add(jp_others_img, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));

		////// jp_image add//////
		jp_image.add(jp_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_image.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));

		setProfile(jlb_profile,user_id);
		jlb_nickName.setText(sender_nick + "("+user_id+")");
		setCurrentTime(jlb_cTime);
		
		jlb_img.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				File f = new File(imgPath);
				String originFilePath = f.getParent().substring(0,f.getParent().indexOf("ImageResize"))
						+ f.getName().replaceFirst("_resize.png", ".png");
				
				JDialog jdl_originImg = new JDialog();
				JLabel imageLabel = new JLabel();
				File originFile = new File(originFilePath);
				Image img = null;
				try {
					img = ImageIO.read(originFile);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				imageLabel.setIcon(new ImageIcon(img));
				imageLabel.addMouseListener(new MouseListener() {
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mousePressed(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseClicked(MouseEvent e) {
						jdl_originImg.dispose();						
					}
				});
				jdl_originImg.add(imageLabel);
				jdl_originImg.pack();
				jdl_originImg.setVisible(true);
				jdl_originImg.setLocation(350,0);
			}
		});
		if(user_id.equals("test")) {
			jlb_gameinfo.setText("티어 : 골드");
		}
		else if(user_id.equals("test1")) {
			jlb_gameinfo.setText("티어 : 실버");
		}
		else if(user_id.equals("test2")) {
			jlb_gameinfo.setText("티어 : 골드");
		}
		else if(user_id.equals("test3")) {
			jlb_gameinfo.setText("티어 : 플레");
		}
		else {
			jlb_gameinfo.setText("티어 : 브론즈");
		}
		
		jp_others.setBackground(new Color(105,110,138));
		jp_others_img.setBackground(new Color(105,110,138));
		jp_others_info.setBackground(new Color(105,110,138));
		jp_image.setBackground(new Color(105,110,138));
		return jp_image;
	}

	public JPanel tradePanel(String user_id,String sender_nick,Map<String,String> sellInfo) {
		
		int gap = 10;
		int oneLine_height = 25;
		int img_width = 75;
		int img_height = 75;
		int otr_width = 700 - img_width - gap;

		/////////// JPanel 선언부///////////////
		JPanel jp_trade = new JPanel();
		JPanel jp_profile = new JPanel();// 좌
		JPanel jp_others = new JPanel();// 우
		JPanel jp_others_info = new JPanel();
		JPanel jp_others_info_nick = new JPanel();
		JPanel jp_others_info_time = new JPanel();
		JPanel jp_others_itemInfo = new JPanel();
		JPanel jp_others_itemInfoOut = new JPanel();
		JPanel jp_others_itemInfo_ItemImg = new JPanel();
		JPanel jp_others_itemInfo_TextInfo = new JPanel();
		
        ///////// 기타 Component 선언부////////////
		JLabel jlb_profile = new JLabel();
		JLabel jlb_nickName = new JLabel();
		JLabel jlb_cTime = new JLabel();
		
		JLabel jlb_itemImg = new JLabel();
		JLabel jlb_itemName = new JLabel();
		JLabel jlb_itemPrice = new JLabel();
		JLabel jlb_itemNotes = new JLabel();

		///////////// setLayout////////////////
		jp_trade.setLayout(new LinearLayout(10));
		jp_profile.setLayout(new BorderLayout());
		jp_others.setLayout(new LinearLayout(Orientation.VERTICAL,10));
		jp_others_info.setLayout(new LinearLayout(10));
		jp_others_info_nick.setLayout(new BorderLayout());
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_itemInfo.setLayout(new LinearLayout(10));
		jp_others_itemInfoOut.setLayout(new FlowLayout());
		jp_others_itemInfo_ItemImg.setLayout(new FlowLayout());
		jp_others_itemInfo_TextInfo.setLayout(new LinearLayout(Orientation.VERTICAL,5));
		

		////////// add,setPreferredSize,기타/////////
		//// jp_profile처리부분////
		jp_profile.setPreferredSize(new Dimension(img_width, img_height));
		jp_profile.add("Center", jlb_profile);
		//// jp_others 처리부분////
		// jp_others_info//
		jp_others_info.setPreferredSize(new Dimension(otr_width, oneLine_height));
		jp_others_info_nick.add("Center", jlb_nickName);
		jp_others_info_time.add("Center", jlb_cTime);
		jp_others_info_nick.setBorder(new LineBorder(Color.lightGray,5));
		jp_others_info_time.setBorder(new LineBorder(Color.lightGray,5));
		jp_others_info.add(jp_others_info_nick,
				new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,
				new LinearConstraints().setWeight(5).setLinearSpace(LinearSpace.MATCH_PARENT));

		// jp_others_itemInfo//
		setItemImg(jlb_itemImg,sellInfo.get("room_game") ,sellInfo.get("itemName"));
		jp_others_itemInfo_ItemImg.add(jlb_itemImg);
		jlb_itemName.setText(" 아이템 이름 : " + sellInfo.get("itemName"));
		jlb_itemPrice.setText("   가격  :   " + sellInfo.get("itemPrice"));
		jlb_itemNotes.setText("   비고  :   " + sellInfo.get("itemNotes"));
		jp_others_itemInfo_TextInfo.add(jlb_itemName
				,new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_itemInfo_TextInfo.add(jlb_itemPrice
				,new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_itemInfo_TextInfo.add(jlb_itemNotes
				,new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_itemInfo_TextInfo.setPreferredSize(new Dimension(otr_width, 200));
		jp_others_itemInfo.add(jp_others_itemInfo_ItemImg
				,new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_others_itemInfo.add(jp_others_itemInfo_TextInfo
				,new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_others_itemInfo.setPreferredSize(new Dimension(otr_width, 300));
		jp_others_itemInfo.setBorder(new LineBorder(Color.lightGray,5));
		jp_others_itemInfoOut.add(jp_others_itemInfo);
		jp_others_itemInfoOut.setPreferredSize(new Dimension(otr_width, 380));

		// jp_others//
		jp_others.setPreferredSize(new Dimension(otr_width, 400+oneLine_height+gap));
		jp_others.add(jp_others_info, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others.add(jp_others_itemInfoOut, new LinearConstraints().setLinearSpace(LinearSpace.MATCH_PARENT));

		////// jp_trade add//////
		jp_trade.add(jp_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_trade.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));

		setProfile(jlb_profile,user_id);
		jlb_nickName.setText(sender_nick + "("+user_id+")");
		setCurrentTime(jlb_cTime);
		
		jp_others.setBackground(new Color(105,110,138));
		jp_others_info.setBackground(new Color(105,110,138));
		jp_others_itemInfoOut.setBackground(new Color(105,110,138));
		jp_trade.setBackground(new Color(105,110,138));
		
		return jp_trade;
	}
	
	

	//////////////////////////// 화면 구성에 필요한 로직 메소드 선언부 ///////////////////////////
	public int countChangeLine(String msg) {
		int count = 0;
		int length = msg.length();
		count = (int) (length / (36));
		if (count < 3) {
			count = 3;
		}
		return count;
	}
	
	public void setCurrentTime(JLabel jLabel) {
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy년 MM월dd일 HH시mm분ss초");
		Date time = new Date();
		String currentTime = format.format(time);
		jLabel.setText(currentTime);
	}
	
	public void setProfile(JLabel jLabel,String user_id) {
		String imgPath = null;
		String folder = token.substring(0,10);
		if(my_id.equals(user_id)) {
			imgPath = "c:\\chat_temp\\" + my_id + "\\ImageResize\\myProfileImg_resize.png";
		}
		else {
			imgPath = "c:\\chat_temp\\" + my_id + "\\" + folder + "\\profiles\\ImageResize\\" + user_id + "_Profile_resize.png";
		}
		File imgFile = new File(imgPath);
		Image profile_img = null;
		try {
			profile_img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		jLabel.setIcon(new ImageIcon(profile_img));
	}
	public void setItemImg(JLabel jLabel, String room_game, String itemName) {
		String folder = token.substring(0,10);
		String imgPath = "c:\\chat_temp\\" + my_id + "\\" + folder + "\\리니지\\" + itemName + ".png";
		File imgFile = new File(imgPath);
		Image itemImg = null;
		try {
			itemImg = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		jLabel.setIcon(new ImageIcon(itemImg));
	}

}
