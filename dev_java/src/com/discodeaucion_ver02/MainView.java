package com.discodeaucion_ver02;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class MainView extends JPanel implements ActionListener {

	JPanel jp_topBtn                     = new JPanel();
	JPanel jp_horizon                    = new JPanel();
	JPanel jp_hor_roomList               = new JPanel();
	JPanel jp_hor_friend                 = new JPanel();
	JPanel jp_hor_f_online               = new JPanel();
	JPanel jp_hor_f_offline              = new JPanel();
	JPanel jp_bottom                     = new JPanel();
	JPanel jp_bottom_profile             = new JPanel();
	JPanel jp_bottom_profile_pic         = new JPanel();
	JPanel jp_bottom_profile_text         = new JPanel();
	JPanel jp_bottom_profile_text_nick   = new JPanel();
	JPanel jp_bottom_profile_text_status = new JPanel();
	JPanel jp_bottom_btn                 = new JPanel();
	JPanel jp_bottom_btn_blank           = new JPanel();

	String cols1[] = { "게임(타입)", "방 이름","현재/최대","토큰"};
	String data1[][] = new String[0][4];
	String cols2[] = { "접속중인 친구" };
	String data2[][] = new String[0][1];
	String cols3[] = { "오프라인 친구" };
	String data3[][] = new String[0][1];

	DefaultTableModel dtm_roomList = null;
	JTable jtb_roomList            = null;
	JScrollPane jsp_roomList       = null;
	DefaultTableModel dtm_online = null;
	JTable jtb_online            = null;
	JScrollPane jsp_online       = null;
	DefaultTableModel dtm_offline = null;
	JTable jtb_offline            = null;
	JScrollPane jsp_offline       = null;

	JButton jbtn_inputTokens = new JButton("토큰입력");
	JButton jbtn_friend      = new JButton("친구관리");
	JButton jbtn_roomIN      = new JButton("입장");
	JButton jbtn_roomCreate  = new JButton("방 생성");
	JButton jbtn_exit        = new JButton("종료");
	
	JLabel jlb_profileImg= new JLabel();
	JLabel jlb_userInfo = new JLabel();

	ClientCtrl cc = null;
	public MainView(ClientCtrl cc) {
		this.cc = cc;
		initDisplay();
		preSetProfile();
		
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

	public void initDisplay() {		
		
		jbtn_inputTokens.addActionListener(this);
		jbtn_friend.addActionListener(this);
		jbtn_roomIN.addActionListener(this);
		jbtn_roomCreate.addActionListener(this);
		jbtn_exit.addActionListener(this);
		
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
		//////////////// 테이블 설정 시작 ////////////////////////////////////////////
		//////////////////////// RoomList 설정 시작////////////////////////////////
		dtm_roomList = new DefaultTableModel(data1, cols1) {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		jtb_roomList = new JTable(dtm_roomList);
		jsp_roomList = new JScrollPane(jtb_roomList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		///////////////////////// RoomList 설정 끝//////////////////////////////////
		///////////////////////// Online 설정 시/ //////////////////////////////////
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
		

		this.setLayout(new LinearLayout(Orientation.VERTICAL, 5));

		jp_topBtn.add(jbtn_inputTokens);
		jp_topBtn.add(jbtn_friend);
		this.add(jp_topBtn, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));

		jp_horizon.setLayout(new LinearLayout(5));
		jp_hor_roomList.setLayout(new BorderLayout());
		jp_hor_roomList.add("Center", jsp_roomList);
		jp_hor_friend.setLayout(new LinearLayout(Orientation.VERTICAL, 5));
		jp_hor_f_online.setLayout(new BorderLayout());
		jp_hor_f_online.add("Center", jsp_online);
		jp_hor_friend.add(jp_hor_f_online,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_hor_f_offline.setLayout(new BorderLayout());
		jp_hor_f_offline.add("Center", jsp_offline);
		jp_hor_friend.add(jp_hor_f_offline,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_horizon.add(jp_hor_roomList, new LinearConstraints().setWeight(3).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_horizon.add(jp_hor_friend, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		this.add(jp_horizon, new LinearConstraints().setWeight(20).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom.setLayout(new LinearLayout(5));
		jp_bottom_profile.setLayout(new LinearLayout(5));
		jp_bottom_profile.setBorder(new TitledBorder(new LineBorder(Color.lightGray,5),"프로필"));
		jp_bottom_profile_pic.setLayout(new BorderLayout());
		jp_bottom_profile_text_nick.setLayout(new BorderLayout());
		jp_bottom_profile_text_status.setLayout(new BorderLayout());
		jp_bottom_profile_pic.add(jlb_profileImg);
		jp_bottom_profile_text.setLayout(new LinearLayout(Orientation.VERTICAL,5));
		jp_bottom_profile_text_nick.add(jlb_userInfo);
		jp_bottom_profile_text_status.add(new JTextArea());
		jp_bottom_profile_text.add(jp_bottom_profile_text_nick, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom_profile_text.add(jp_bottom_profile_text_status, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom_profile.add(jp_bottom_profile_pic, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom_profile.add(jp_bottom_profile_text, new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom_profile_text.add(jp_bottom_profile_text_nick,new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom_profile_text.add(jp_bottom_profile_text_status,new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom_profile.add("Center",new JTextArea());
		jp_bottom_btn.setLayout(new LinearLayout(5));
		jp_bottom_btn.add(jp_bottom_btn_blank, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
		jp_bottom_btn.add(jbtn_roomIN, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
		jp_bottom_btn.add(jbtn_roomCreate, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
		jp_bottom_btn.add(jbtn_exit, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CENTER_CONTENT));
		jp_bottom.add(jp_bottom_profile, new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_bottom.add(jp_bottom_btn, new LinearConstraints().setWeight(3).setLinearSpace(LinearSpace.MATCH_PARENT));
		this.add(jp_bottom, new LinearConstraints().setWeight(3).setLinearSpace(LinearSpace.MATCH_PARENT));
		
		setColumnSize();
		// 테이블 내용수정불가, 이동,크기 조절 불가
		jtb_roomList.getTableHeader().setReorderingAllowed(false);
		jtb_roomList.getTableHeader().setResizingAllowed(false);
		jtb_online.getTableHeader().setReorderingAllowed(false);
		jtb_online.getTableHeader().setResizingAllowed(false);
		jtb_offline.getTableHeader().setReorderingAllowed(false);
		jtb_offline.getTableHeader().setResizingAllowed(false);
		
		TableColumnModel tcm = jtb_roomList.getColumnModel();
		tcm.removeColumn(tcm.getColumn(3));
	}
	
	public void setColumnSize() {
		int jtb_width = 800 * 3 / 4;
		jtb_roomList.getColumnModel().getColumn(0).setPreferredWidth(jtb_width * 2 / 8);
		jtb_roomList.getColumnModel().getColumn(1).setPreferredWidth(jtb_width * 5 / 8);
		jtb_roomList.getColumnModel().getColumn(2).setPreferredWidth(jtb_width * 1 / 8);
	}
	
	int i = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		

		if (obj == jbtn_inputTokens) {
			String inputToken = JOptionPane.showInputDialog("입장할 방의 토큰을 입력하세요");
			try {
				cc.main_oos.writeObject(Dprotocol.ROOM_ACCEPT
						               +Dprotocol.seperator+inputToken);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} 
		else if (obj == jbtn_friend) {			
			
		} 
		else if (obj == jbtn_roomIN) {
			int row = jtb_roomList.getSelectedRow();
			if(row<0) {
				JOptionPane.showMessageDialog(cc,"방을 선택하세요");
				return;
			}
			else {
				try {
					String token = (String)jtb_roomList.getModel().getValueAt(row, 3);	
					cc.main_oos.writeObject(Dprotocol.ROOM_IN
							               +Dprotocol.seperator+token);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		} 
		else if (obj == jbtn_roomCreate) {
			RoomCreate  rc = new RoomCreate(this);
			rc.initDisplay();
			rc.setVisible(true);
		}
		else if (obj == jbtn_exit) {
			try {
				cc.main_oos.writeObject(Dprotocol.LOG_OUT
						          +Dprotocol.seperator+cc.user_id
						          );
			} catch (IOException e1) {				
				e1.printStackTrace();
			}
		}
	}
}