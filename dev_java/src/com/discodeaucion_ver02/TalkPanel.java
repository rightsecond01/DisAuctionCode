package com.discodeaucion_ver02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class TalkPanel {
	final public static String AuctionRoom = "au";
	final public static String VoiceChatRoom = "vc";
	
	String my_id = null;
	String room_title = null;
	
	public TalkPanel(String my_id, String room_title) {
		this.my_id = my_id;
		this.room_title = room_title;
	}

	public JPanel textPanel(String roomType, String msg, String user_id) {
		int gap = 10;
		int oneLine_height = 18;
		int count = countChangeLine(msg);// 몇번 개행처리가 되는가에 대한 변수
		int profile_width = 75;
		int profile_height = 75;
		int otr_width = 800 - profile_width - gap;
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
		jp_others.setLayout(new FlowLayout());
		jp_others_info.setLayout(new LinearLayout(10));
		jp_others_info_nick.setLayout(new BorderLayout());
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_msg.setLayout(new BorderLayout());
		////////// add,setPreferredSize,기타/////////
		//// jp_img처리부분////
		if (roomType.equals(AuctionRoom)) {
			jp_profile.setPreferredSize(new Dimension(profile_width, profile_height));
			jp_profile.add("Center", jlb_profile);
		} else if (roomType.equals(VoiceChatRoom)) {
			JPanel jp_profile_profile = new JPanel();//// VoiceChat에서만 사용
			JPanel jp_profile_gameInfo = new JPanel();//// VoiceChat에서만 사용
			jp_profile.setLayout(new LinearLayout(Orientation.VERTICAL, 5));
			jp_profile_profile.setLayout(new BorderLayout());
			jp_profile_gameInfo.setLayout(new BorderLayout());
			jp_profile_profile.setPreferredSize(new Dimension(profile_width, profile_height));
			jp_profile_gameInfo.setPreferredSize(new Dimension(profile_width, 20));
			jp_profile_profile.add(jlb_profile);
			jp_profile_gameInfo.add(new JTextArea());
			jp_profile.setPreferredSize(new Dimension(profile_width, profile_height + 30));
			jp_profile.add(jp_profile_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
			jp_profile.add(jp_profile_gameInfo, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		}
		//// jp_others 처리부분////
		// jp_others_info//
		jp_others_info.setPreferredSize(new Dimension(otr_width, oneLine_height));
		jp_others_info_nick.add("Center", jlb_nickName);
		jp_others_info_time.add("Center", jlb_cTime);
		jp_others_info.add(jp_others_info_nick,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));

		// jp_others_msg//
		jsp_TalkBox.setPreferredSize(new Dimension(otr_width, oneLine_height * count));
		jta_TalkBox.setLineWrap(true);
		jta_TalkBox.append(msg);
		jp_others_msg.add("Center", jsp_TalkBox);

		// jp_others//
		jp_others.setPreferredSize(new Dimension(otr_width, otr_height));
		jp_others.add(jp_others_info);
		jp_others.add(jp_others_msg);

		////// jp_text add//////
		jp_text.add(jp_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_text.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		
		setProfile(jlb_profile,user_id);
		jlb_nickName.setText(user_id);
		setCurrentTime(jlb_cTime);
		
		return jp_text;
	}

	public JPanel imagePanel(String imgPath, String user_id) {
		int gap = 10;
		int oneLine_height = 18;
		int profile_width = 75;
		int profile_height = 75;
		int otr_width = 500 - profile_width - gap;

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
		jp_others.setLayout(new FlowLayout());
		jp_others_info.setLayout(new LinearLayout(10));
		jp_others_info_nick.setLayout(new BorderLayout());
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_img.setLayout(new BorderLayout());

		////////// add,setPreferredSize,기타/////////
		//// jp_profile처리부분////
		jp_profile.setPreferredSize(new Dimension(profile_width, profile_height));
		jp_profile.add("Center", jlb_profile);
		//// jp_others 처리부분////
		// jp_others_info//
		jp_others_info.setPreferredSize(new Dimension(otr_width, oneLine_height));
		jp_others_info_nick.add("Center", jlb_nickName);
		jp_others_info_time.add("Center", jlb_cTime);
		jp_others_info.add(jp_others_info_nick,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.setBackground(Color.GRAY);

		// jp_others_img//
		/*여기에 이미지 사이즈 계산하는 코드와, 최대크기를 넘었을때 처리하는 코드 작성*/
		File imgFile = new File("C:\\Workspace_java\\dev_java\\src\\com\\images\\profile\\1563969682551.png");
		BufferedImage b_img = null;
		try {
			b_img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		int img_width = b_img.getWidth();
		int img_height = b_img.getHeight();
		jlb_img.setIcon(new ImageIcon(b_img));
		jp_others_img.add("Center", jlb_img);
		

		// jp_others//
		int otr_height = 500;/*여기에  이미지의 크기에 따른 jp_others의 크기 입력*/
		jp_others.setPreferredSize(new Dimension(otr_width, otr_height));
		jp_others.add(jp_others_info);
		jp_others.add(jp_others_img);
		jp_others.setBackground(Color.GRAY);

		////// jp_image add//////
		jp_image.add(jp_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_image.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));

		setProfile(jlb_profile,user_id);
		jlb_nickName.setText(user_id);
		setCurrentTime(jlb_cTime);
		
		return jp_image;
	}

	public JPanel tradePanel() {
		
		int gap = 10;
		int oneLine_height = 18;
		int img_width = 75;
		int img_height = 75;
		int otr_width = 500 - img_width - gap;

		/////////// JPanel 선언부///////////////
		JPanel jp_trade = new JPanel();
		JPanel jp_profile = new JPanel();// 좌
		JPanel jp_others = new JPanel();// 우
		JPanel jp_others_info = new JPanel();
		JPanel jp_others_info_nick = new JPanel();
		JPanel jp_others_info_time = new JPanel();
		JPanel jp_others_itemInfo = new JPanel();

		///////////// setLayout////////////////
		jp_trade.setLayout(new LinearLayout(10));
		jp_profile.setLayout(new BorderLayout());
		jp_others.setLayout(new FlowLayout());
		jp_others_info.setLayout(new LinearLayout(10));
		jp_others_info_nick.setLayout(new BorderLayout());
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_itemInfo.setLayout(new BorderLayout());

		////////// add,setPreferredSize,기타/////////
		//// jp_profile처리부분////
		jp_profile.setPreferredSize(new Dimension(img_width, img_height));
		jp_profile.add("Center", new JTextArea());
		//// jp_others 처리부분////
		// jp_others_info//
		jp_others_info.setPreferredSize(new Dimension(otr_width, oneLine_height));
		jp_others_info_nick.add("Center", new JTextArea());
		jp_others_info_time.add("Center", new JTextArea());
		jp_others_info.add(jp_others_info_nick,
				new LinearConstraints().setWeight(5).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,
				new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));

		// jp_others_itemInfo//

		// jp_others//
		jp_others.setPreferredSize(new Dimension(otr_width, 0));
		jp_others.add(jp_others_info);
		jp_others.add(jp_others_itemInfo);

		////// jp_trade add//////
		jp_trade.add(jp_profile, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		jp_trade.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));

		
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
		if(my_id.equals(user_id)) {
			imgPath = "c:\\chat_temp\\" + my_id + "\\ImageResize\\myProfileImg_resize.png";
		}
		else {
			imgPath = "c:\\chat_temp\\" + room_title + "\\ImageResize\\" + user_id + "\\_profileImg.png";
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

}