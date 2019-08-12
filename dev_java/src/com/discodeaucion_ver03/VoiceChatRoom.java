package com.discodeaucion_ver03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;
import com.util.ImageResize;

public class VoiceChatRoom extends JPanel implements ActionListener {
	JPanel jp_left                                  = new JPanel();
	JPanel jp_left_Center                           = new JPanel();
	JPanel jp_left_Center_voice                     = new JPanel();
	JPanel jp_left_Center_voice_Group               = new JPanel();
	JPanel jp_left_Center_voice_Profile             = new JPanel();
	JPanel jp_left_Center_voice_Profile_img         = new JPanel();
	JPanel jp_left_Center_voice_Profile_text        = new JPanel();
	JPanel jp_left_Center_voice_Profile_text_Info   = new JPanel();
	JPanel jp_left_Center_voice_Profile_text_blank  = new JPanel();
	JPanel jp_left_Center_main                      = new JPanel();
	JPanel jp_left_Center_main_Top                  = new JPanel();
	JPanel jp_left_Center_main_Top_title            = new JPanel();
	JPanel jp_left_Center_main_Top_btn              = new JPanel();
	JPanel jp_left_Center_main_Talk                 = new JPanel();
	JPanel jp_left_Bottom                           = new JPanel();
	JPanel jp_left_Bottom_label                     = new JPanel();
	JPanel jp_left_Bottom_chat                      = new JPanel();
	JPanel jp_left_Bottom_imgUpload                 = new JPanel();
	JPanel jp_right                                 = new JPanel();
	JPanel jp_right_Online                          = new JPanel();
	JPanel jp_right_Offline                         = new JPanel();
	
	JLabel jlb_title = new JLabel("방 제목");
	JLabel jlb_profileImg= new JLabel();
	JLabel jlb_userInfo = new JLabel();
	
	//roomSet은 마스터의 경우만 활성화
	JButton jbtn_roomSet     = new JButton("방 설정");
	
	JButton jbtn_ProfileSet  = new JButton("설정");
	JButton jbtn_roomInvite  = new JButton("초대");
	JButton jbtn_Friend      = new JButton("친구");
	JButton jbtn_roomOut     = new JButton("나가기");
	JButton jbtn_roomExit    = new JButton("연결끊기");
	JButton jbtn_ImgUpload   = new JButton("Image");
	
	StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	JScrollPane jsp_display = new JScrollPane(jtp_display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JLabel jlb_chat = new JLabel("채팅");
	JTextField jtf_chat = new JTextField(); 

	String cols2[] = { "접속중인 친구", "", "아이디" };
	String data2[][] = new String[0][3];
	String cols3[] = { "오프라인 친구", "", "아이디" };
	String data3[][] = new String[0][3];

	DefaultTableModel dtm_online = null;
	JTable jtb_online            = null;
	JScrollPane jsp_online       = null;
	DefaultTableModel dtm_offline = null;
	JTable jtb_offline            = null;
	JScrollPane jsp_offline       = null;

	Font font = new Font("돋움",Font.PLAIN,12);
	
	Socket roomSocket = null;
	ObjectOutputStream room_oos = null;
	ObjectInputStream room_ois = null;
	
	ClientCtrl cc = null;
	int port = 0;
	String token = null;
	
	String inRoomUserProfilePath = null;
	String uploadImgMyFolderSavePath = null;

	
	public VoiceChatRoom() {
		
	}
	public VoiceChatRoom(ClientCtrl cc, int port, String room_title, String token) {
		this.cc = cc;
		this.port = port;
		jlb_title.setText(room_title);
		this.token = token;
		initDisplay();
		preSetProfile();

		Vector v = new Vector();
		v.add(cc.nickName);
		v.add("");
		v.add(cc.user_id);
		dtm_online.addRow(v);
		connect_process();
		
		String roomdivision = token.substring(0,10);
		inRoomUserProfilePath = "C:\\chat_temp\\" + cc.user_id + "\\" + roomdivision + "\\profiles\\";
		uploadImgMyFolderSavePath = "C:\\chat_temp\\" + cc.user_id + "\\" + roomdivision + "\\upload_images\\";
		
		///////////////////////////////////
		/////////디렉토리 생성 코드//////////////
		File folder = new File(uploadImgMyFolderSavePath);
		if(!folder.exists()) {
			folder.mkdir();
		}
		////////끝//////////
		///////////////////
	}
	public VoiceChatRoom(ClientCtrl cc, int port, String room_title, String token, List<Map<String, String>> onlineList, List<Map<String, String>> offlineList) {
		this.cc = cc;
		this.port = port;
		jlb_title.setText(room_title);
		this.token = token;
		initDisplay();
		preSetProfile();
		preSetOnTable(onlineList);
		preSetOffTable(offlineList);

		Vector v = new Vector();
		v.add(this.cc.nickName + "(" + this.cc.user_id + ")");
		v.add("");
		v.add(this.cc.user_id);
		dtm_online.addRow(v);
		connect_process();
		
		String roomdivision = token.substring(0,10);
		inRoomUserProfilePath = "C:\\chat_temp\\" + cc.user_id + "\\" + roomdivision + "\\profiles\\";
		uploadImgMyFolderSavePath = "C:\\chat_temp\\" + cc.user_id + "\\" + roomdivision + "\\upload_images\\";
		
		///////////////////////////////////
		/////////디렉토리 생성 코드//////////////
		File folder = new File(uploadImgMyFolderSavePath);
		if(!folder.exists()) {
			folder.mkdir();
		}
		////////끝//////////
		///////////////////
	}
	public void preSetProfile() {
		String filePath = cc.r_profileImgPath;
		File imgFile = new File(filePath);
		Image profile_img = null;
		try {
			profile_img = ImageIO.read(imgFile);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		jlb_profileImg.setIcon(new ImageIcon(profile_img));
		jlb_userInfo.setText(cc.nickName + " #" + cc.user_id);
	}
	
	public void preSetOnTable(List<Map<String, String>> userList) {
		for (int i = 0; i < userList.size(); i++) {
			Map<String, String> userMap = userList.get(i);
			String user_nick = userMap.get("mem_nick");
			String user_id = userMap.get("mem_id");

			Vector v = new Vector();
			v.add(user_nick + "(" + user_id + ")");
			v.add("");
			v.add(user_id);
			dtm_online.addRow(v);
		}
	}

	public void preSetOffTable(List<Map<String, String>> userList) {
		for (int i = 0; i < userList.size(); i++) {
			Map<String, String> userMap = userList.get(i);
			String user_nick = userMap.get("mem_nick");
			String user_id = userMap.get("mem_id");

			Vector v = new Vector();
			v.add(user_nick + "(" + user_id + ")");
			v.add("");
			v.add(user_id);
			if (!user_id.equals(cc.user_id)) {
				dtm_offline.addRow(v);
			}
		}
	}
	

	public void initDisplay() {
		jtp_display.setEditable(false);
		
		jtf_chat.addActionListener(this);
		jbtn_roomSet.addActionListener(this);
		jbtn_ProfileSet.addActionListener(this);
		jbtn_roomInvite.addActionListener(this);
		jbtn_Friend.addActionListener(this);
		jbtn_roomExit.addActionListener(this);
		jbtn_roomOut.addActionListener(this);
		jbtn_ImgUpload.addActionListener(this);

		jlb_profileImg.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) { }
			@Override
			public void mousePressed(MouseEvent arg0) { }
			@Override
			public void mouseExited(MouseEvent arg0) { }
			@Override
			public void mouseEntered(MouseEvent arg0) {	}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JDialog jdl_originImg = new JDialog();
				JLabel imageLabel = new JLabel();
				String myFilePath = cc.temp_directory+ cc.user_id;
				String filePath = myFilePath + "\\myProfileImg.png";
				File originFile = new File(filePath);
				Image img = null;
				try {
					img = ImageIO.read(originFile);
				} catch (Exception e) {
					e.printStackTrace();
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
////////////////테이블 설정 시작 /////////////////////////////////////////////
///////////////////////// Online 설정 시/ /////////////////////////////////
		dtm_online = new DefaultTableModel(data2, cols2) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		jtb_online = new JTable(dtm_online);
		jsp_online = new JScrollPane(jtb_online, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
///////////////////////// Online 설정 끝 ///////////////////////////////////
///////////////////////// Offline 설정 시작/////////////////////////////////
		dtm_offline = new DefaultTableModel(data3, cols3) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		jtb_offline = new JTable(dtm_offline);
		jsp_offline = new JScrollPane(jtb_offline, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
///////////////////////// Offline 설정 끝//////////////////////////////////
//////////////// 테이블 설정 끝 //////////////////////////////////////////////
		this.setLayout(new LinearLayout(5));
		
		jp_left.setLayout(new LinearLayout(Orientation.VERTICAL,5));		
		jp_left_Center.setLayout(new LinearLayout(5));		
		jp_left_Center_voice.setLayout(new LinearLayout(Orientation.VERTICAL,5));
		jp_left_Center_voice_Group.setLayout(new BorderLayout());
		jp_left_Center_voice_Profile.setLayout(new LinearLayout(5));
		jp_left_Center_voice_Profile.setBorder(new TitledBorder(new LineBorder(Color.lightGray,5),"프로필"));
		jp_left_Center_voice_Profile_img.setLayout(new BorderLayout());
		jp_left_Center_voice_Profile_img.add(jlb_profileImg);		
		jp_left_Center_voice_Profile_text.setLayout(new LinearLayout(Orientation.VERTICAL,5));
		jp_left_Center_voice_Profile_text_Info.setLayout(new BorderLayout());
		jp_left_Center_voice_Profile_text_Info.add(jlb_userInfo);
		jp_left_Center_voice_Profile_text_blank.setLayout(new BorderLayout());
		jp_left_Center_voice_Profile_text_blank.add(new JTextArea());
		jp_left_Center_voice_Profile_text.add(jp_left_Center_voice_Profile_text_Info,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_voice_Profile_text.add(jp_left_Center_voice_Profile_text_blank,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_voice_Profile.add(jp_left_Center_voice_Profile_img,
				new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_voice_Profile.add(jp_left_Center_voice_Profile_text,
				new LinearConstraints().setWeight(7).setLinearSpace(LinearSpace.MATCH_PARENT));		
		jp_left_Center_voice.add(jp_left_Center_voice_Group,
				new LinearConstraints().setWeight(6).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_voice.add(jp_left_Center_voice_Profile,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main.setLayout(new LinearLayout(Orientation.VERTICAL,5));		
		jp_left_Center_main_Top.setLayout(new LinearLayout(5));
		jp_left_Center_main_Top_title.setLayout(new BorderLayout());
		/////라벨세팅/////
		jlb_title.setBackground(Color.white);
		jlb_title.setOpaque(true);
		jlb_title.setHorizontalAlignment(JLabel.CENTER);
		////라벨 세팅 끝///
		jp_left_Center_main_Top_title.add("Center",jlb_title);
		jp_left_Center_main_Top_btn.setLayout(new LinearLayout(5));
		jbtn_ProfileSet.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_roomInvite.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_Friend.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_roomExit.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_roomOut.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));		
		jbtn_ProfileSet.setFont(font);
		jbtn_roomInvite.setFont(font);
		jbtn_Friend.setFont(font);
		jbtn_roomExit.setFont(font);
		jbtn_roomOut.setFont(font);
		jp_left_Center_main_Top_btn.add(jbtn_ProfileSet
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_roomInvite
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_Friend
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_roomOut
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_roomExit
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top.add(jp_left_Center_main_Top_title
				, new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top.add(jp_left_Center_main_Top_btn
				, new LinearConstraints().setWeight(3).setLinearSpace(LinearSpace.MATCH_PARENT));		
		jp_left_Center_main_Talk.setLayout(new BorderLayout());
		jp_left_Center_main_Talk.add("Center",jsp_display);
		jp_left_Center_main.add(jp_left_Center_main_Top
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main.add(jp_left_Center_main_Talk
				, new LinearConstraints().setWeight(20).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center.add(jp_left_Center_voice
				, new LinearConstraints().setWeight(4).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center.add(jp_left_Center_main
				, new LinearConstraints().setWeight(11).setLinearSpace(LinearSpace.MATCH_PARENT));
		
		jp_left_Bottom.setLayout(new LinearLayout(5));	
		jp_left_Bottom_label.setLayout(new BorderLayout());
		////////라벨 세팅////////
		jlb_chat.setForeground(Color.white);
		jlb_chat.setBackground(Color.DARK_GRAY);
		jlb_chat.setOpaque(true);
		jlb_chat.setHorizontalAlignment(JLabel.CENTER);
		////////라벨 세팅 끝 /////
		jp_left_Bottom_label.add("Center",jlb_chat);	
		jp_left_Bottom_chat.setLayout(new BorderLayout());
		jp_left_Bottom_chat.add("Center",jtf_chat);		
     	///////버튼세팅////////
		jbtn_ImgUpload.setPreferredSize(new Dimension(50,30));
		jbtn_ImgUpload.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		Font f = new Font("HY목각파임B",Font.PLAIN,11);
		jbtn_ImgUpload.setFont(f);
		jbtn_ImgUpload.setBackground(new Color(153,255,255));
		jbtn_ImgUpload.setBorder(new LineBorder(Color.LIGHT_GRAY,5));
		///////버튼세팅끝//////
		jp_left_Bottom_imgUpload.setLayout(new FlowLayout());
		jp_left_Bottom_imgUpload.add(jbtn_ImgUpload);		
		jp_left_Bottom.add(jp_left_Bottom_label, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Bottom.add(jp_left_Bottom_chat,  new LinearConstraints().setWeight(14).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Bottom.add(jp_left_Bottom_imgUpload,  new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left.add(jp_left_Center,  new LinearConstraints().setWeight(20).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left.add(jp_left_Bottom,  new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		
		
		jp_right.setLayout(new LinearLayout(Orientation.VERTICAL,5));
		jp_right_Online.setLayout(new BorderLayout());
		jp_right_Online.add("Center", jsp_online);
		jp_right_Offline.setLayout(new BorderLayout());
		jp_right_Offline.add("Center", jsp_offline);
		jp_right.add(jp_right_Online, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_right.add(jp_right_Offline, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		
		this.add(jp_left, new LinearConstraints().setWeight(5).setLinearSpace(LinearSpace.MATCH_PARENT));
		this.add(jp_right, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		
		
		// 비율에 따라 컬럼크기 변경하는 코드
		int onoff_JTable_width = 800 * 1 / 5;
		jtb_online.getColumnModel().getColumn(0).setPreferredWidth(onoff_JTable_width * 7 / 8);
		jtb_online.getColumnModel().getColumn(1).setPreferredWidth(onoff_JTable_width * 1 / 8);
		jtb_offline.getColumnModel().getColumn(0).setPreferredWidth(onoff_JTable_width * 7 / 8);
		jtb_offline.getColumnModel().getColumn(1).setPreferredWidth(onoff_JTable_width * 1 / 8);
		// 테이블 내용수정불가, 이동,크기 조절 불가
		jtb_online.getTableHeader().setReorderingAllowed(false);
		jtb_online.getTableHeader().setResizingAllowed(false);
		jtb_offline.getTableHeader().setReorderingAllowed(false);
		jtb_offline.getTableHeader().setResizingAllowed(false);
		
		// 유저의 아이디값을 히든으로 설정하는 코드
		TableColumnModel tcm = jtb_online.getColumnModel();
		tcm.removeColumn(tcm.getColumn(2));
		TableColumnModel tcm2 = jtb_offline.getColumnModel();
		tcm2.removeColumn(tcm2.getColumn(2));
		
///////////////테이블 더블클릭 이벤트////////////////////
	jtb_online.addMouseListener(new MouseListener() {
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
			 if(e.getClickCount()==2) {
				 int row = jtb_online.getSelectedRow();
				 String user = (String)jtb_online.getModel().getValueAt(row, 2);
				 JDialog jdl_originImg = new JDialog();
				 JLabel imageLabel = new JLabel();
				 String filePath = null;
				 if(user.equals(cc.user_id)) {
					 filePath = cc.temp_directory+ cc.user_id + "\\myProfileImg.png";
				 }
				 else {
					 filePath = inRoomUserProfilePath + user + "_Profile.png";
				 }
				 
				 File originFile = new File(filePath);
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
		}
	});
	
	jtb_offline.addMouseListener(new MouseListener() {
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
			 if(e.getClickCount()==2) {
				 int row = jtb_offline.getSelectedRow();
				 String user = (String)jtb_offline.getModel().getValueAt(row, 2);
				 JDialog jdl_originImg = new JDialog();
				 JLabel imageLabel = new JLabel();
				 String filePath = null;
				 if(user.equals(cc.user_id)) {
					 filePath = cc.temp_directory+ cc.user_id + "\\myProfileImg.png";
				 }
				 else {
					 filePath = inRoomUserProfilePath + user + "_Profile.png";
				 }
				 File originFile = new File(filePath);
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
		}
	});
	}
	
	public void connect_process() {
		try {
			roomSocket = new Socket(cc.ip, port);
			cc.rSocketList.add(roomSocket);
			room_oos = new ObjectOutputStream(roomSocket.getOutputStream());
			room_ois = new ObjectInputStream(roomSocket.getInputStream());
			room_oos.writeObject(
					Dprotocol.ROOM_IN + Dprotocol.seperator + cc.user_id + Dprotocol.seperator + cc.nickName);
			VoiceChatRoomThread vrt = new VoiceChatRoomThread(this);
			vrt.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////actionLister 메소드/////////////////////////////////////
	public void message_process(String message) {

		try {
			room_oos.writeObject(Dprotocol.MESSAGE 
					           + Dprotocol.seperator + cc.nickName 
					           + Dprotocol.seperator + token
					           + Dprotocol.seperator + TalkPanel.VoiceChatRoom
					           + Dprotocol.seperator + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void inviteFriend() {
		JDialog jdl_showToken = new JDialog();
		JTextField jf_copyToken = new JTextField();
		JPanel jp_jbutton = new JPanel();
		JButton jbtn_copy = new JButton("복사");
		JButton jbtn_exit = new JButton("닫기");
		jdl_showToken.setTitle("초대코드를 복사해서 친구에게 전달하세요.");
		jf_copyToken.setText(token);
		jp_jbutton.add(jbtn_copy);
		jp_jbutton.add(jbtn_exit);
		jbtn_copy.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				StringSelection data = new StringSelection(jf_copyToken.getText());
			    Clipboard clipboard = getToolkit().getDefaultToolkit().getSystemClipboard();
			    clipboard.setContents(data, data);
			    JOptionPane.showMessageDialog(jdl_showToken, "초대 코드가 복사되었습니다. Ctrl + v를 이용하여 친구를 초대하세요!");
			    jdl_showToken.dispose();
				
			}
		});
		jbtn_exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jdl_showToken.dispose();
			}
		});
		jf_copyToken.setEditable(false);
		jdl_showToken.add(jf_copyToken);
		jdl_showToken.add("South",jp_jbutton);			
		jdl_showToken.setBounds(750, 450, 400, 100);
		jdl_showToken.setVisible(true);	
	}
	
	public void addFriend() {		
		String addedUser = null; 
		String addedfriendUser = null; 
		int row = jtb_online.getSelectedRow();
		if(row>=0) {
			addedUser = (String)jtb_online.getModel().getValueAt(row, 0);
			addedfriendUser = (String)jtb_online.getModel().getValueAt(row, 2);			
		}
		 if(row<0) {
			 row = jtb_offline.getSelectedRow();
			 addedUser = (String)jtb_offline.getModel().getValueAt(row, 0);
			 addedfriendUser = (String)jtb_offline.getModel().getValueAt(row, 2);
			 if(row<0) {
				 JOptionPane.showMessageDialog(this, "친구로 등록할 사람을 선택하세요.");
				 return;
			 }
		 }
		 
		 if(addedfriendUser.equals(cc.user_id)) {
			 JOptionPane.showMessageDialog(this, "나를 친구로 등록할 수 없습니다.");
			 return;
		 }
		 try {
			room_oos.writeObject(Dprotocol.ADD_FRIEND
          					   + Dprotocol.seperator + cc.user_id
          					   + Dprotocol.seperator + cc.nickName
					           + Dprotocol.seperator + addedfriendUser
					           + Dprotocol.seperator + addedUser
					           );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void roomExit() {
		JDialog jdl_check  = new JDialog();
		JPanel jp_message  = new JPanel();
		JLabel jlb_message  = new JLabel("연결 끊기를 하시면 다시 초대받기 전까진 이 방으로 들어올 수 없습니다.");
		JLabel jlb_message2 = new JLabel("정말 끊으시겠습니까?");
		JPanel jp_jbutton  = new JPanel();
		JButton jbtn_yes  = new JButton("예");
		JButton jbtn_no  = new JButton("아니오");			
		
		jp_message.setLayout(new GridLayout(2,1));
		jp_message.add(jlb_message);
		jp_message.add(jlb_message2);
		
		jp_jbutton.add(jbtn_yes);
		jp_jbutton.add(jbtn_no);
		jbtn_yes.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					room_oos.writeObject(Dprotocol.ROOM_EXIT
							            +Dprotocol.seperator + cc.user_id
							            +Dprotocol.seperator + cc.nickName  
							            +Dprotocol.seperator + token
							            );
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					jdl_check.dispose();
				}
				
			}
		});
		jbtn_no.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jdl_check.dispose();
				return;					
			}
		});
		jdl_check.setTitle("※경고※");
		jdl_check.add(jp_message);
		jdl_check.add("South",jp_jbutton);
		jdl_check.setBounds(750, 450, 430, 150);
		jdl_check.setVisible(true);
	}
	
	//////////////////////////////////////Util 메소드//////////////////////////////////////////
	public String fileChooser() {
		String src = null;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG&GIF Images","jpg","gif","png");
		chooser.setFileFilter(filter);
		int ret=chooser.showOpenDialog(null);
		if(ret!=JFileChooser.APPROVE_OPTION){
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
			return src;
		}
		src=chooser.getSelectedFile().getPath();
		return src;
	}
	
	public void saveMyImage(String filePath, File saveFile) {//filePath = 원본파일주소, saveFile 저장될 파일
		if(filePath!=null) {
			File myImgFile = new File(filePath);
			try {
				BufferedImage src = ImageIO.read(myImgFile);			
				ImageIO.write(src,"png",saveFile);
			} catch (IOException e) {
				e.printStackTrace();
			}					
		}
		
	}
	
	public void resizeImage(String filePath) { // filePath = 원본파일주소, newPath = 리사이징 파일주소
		String newPath = null;
		///////////////////////////////////
		/////////디렉토리 생성 코드//////////////
		String resizeSaveFolderPath = uploadImgMyFolderSavePath + "ImageResize\\";
		File resizeSaveFolder = new File(resizeSaveFolderPath);
		if(!resizeSaveFolder.exists()) {
			resizeSaveFolder.mkdir();
		}
		////////끝//////////
		///////////////////		
		if(filePath!=null) {
			File resizeFile = new File(filePath);		
			String tmpPath = resizeFile.getParent();
			String tmpFileName = resizeFile.getName();
			int extPosition = tmpFileName.indexOf("png");
			
			if(extPosition != -1){
				ImageIcon ic = ImageResize.resizeImage(filePath, 700, 700);
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
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = jtf_chat.getText();
		Object obj = e.getSource();
		
		if (obj == jtf_chat) {
			if(msg==null||msg.length()<1) {
				JOptionPane.showMessageDialog(this, "빈 메세지를 보낼 수 없습니다.");
				return;
			}
			message_process(msg);
			jtf_chat.setText("");

		} 
		else if(obj==jbtn_roomInvite) {
			inviteFriend();
		}
		else if(obj==jbtn_Friend) {
			addFriend();
		}
		else if (obj == jbtn_roomOut) {
			try {
				room_oos.writeObject(
						  Dprotocol.ROOM_OUT 
						+ Dprotocol.seperator + cc.user_id 
						+ Dprotocol.seperator + cc.nickName);
				

				// 개선사항 : 여기서 this에 대한 자원반납을 할 방법이 뭐가 있을까 고민

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(obj==jbtn_roomExit) {
			roomExit();		
		
		}
		else if(obj==jbtn_ImgUpload) {
			/*
			 * 순서
			 * 원본파일 myFolder에 저장 => 그 후 서버에 이미지 전송
			 */
			String filePath =  fileChooser();
			String myFileName = Long.valueOf(new Date().getTime()).toString()  
					+ "_" + cc.user_id + ".png";

			String myFolderPath = uploadImgMyFolderSavePath + myFileName;
			File saveFile = new File(myFolderPath);
			saveMyImage(filePath, saveFile);//내 폴더에 저장
			resizeImage(myFolderPath);//가로길이 700에 맞춰 리사이징
			
			int imagePort = port + 3000;

			try {
				room_oos.writeObject(Dprotocol.IMAGE_UPLOAD
						           + Dprotocol.seperator + imagePort
						           + Dprotocol.seperator + myFileName
						           + Dprotocol.seperator + cc.user_id
						           + Dprotocol.seperator + cc.nickName
						           );
				UploadImgTransmission uit = new UploadImgTransmission(imagePort,filePath);
				uit.start();//서버로 이미지 전송하는 스레드 
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

}
