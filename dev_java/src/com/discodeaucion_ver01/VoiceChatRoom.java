package com.discodeaucion_ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class VoiceChatRoom extends JFrame implements ActionListener {
	JPanel jp_left = new JPanel();
	JPanel jp_left_Center = new JPanel();
	JPanel jp_left_Center_voice = new JPanel();
	JPanel jp_left_Center_main = new JPanel();
	JPanel jp_left_Center_main_Top = new JPanel();
	JPanel jp_left_Center_main_Top_title = new JPanel();
	JPanel jp_left_Center_main_Top_btn = new JPanel();
	JPanel jp_left_Center_main_Talk = new JPanel();
	JPanel jp_left_Bottom = new JPanel();
	JPanel jp_left_Bottom_label = new JPanel();
	JPanel jp_left_Bottom_chat = new JPanel();
	JPanel jp_right = new JPanel();
	JPanel jp_right_Online = new JPanel();
	JPanel jp_right_Offline = new JPanel();
	
	JLabel jlb_title = new JLabel("방 제목");
	
	//roomSet은 마스터의 경우만 활성화
	JButton jbtn_roomSet     = new JButton("방 설정");
	
	JButton jbtn_ProfileSet  = new JButton("설정");
	JButton jbtn_roomInvite  = new JButton("초대");
	JButton jbtn_Friend      = new JButton("친구");
	JButton jbtn_roomExit    = new JButton("나가기");
	JButton jbtn_whisper     = new JButton("귓속말");
	
	JLabel jlb_chat = new JLabel("채팅");
	JTextField tf_chat = new JTextField(); 

	String cols2[] = { "접속중인 친구", "" };
	String data2[][] = new String[0][2];
	String cols3[] = { "오프라인 친구", "" };
	String data3[][] = new String[0][2];

	DefaultTableModel dtm_online = null;
	JTable jtb_online            = null;
	JScrollPane jsp_online       = null;
	DefaultTableModel dtm_offline = null;
	JTable jtb_offline            = null;
	JScrollPane jsp_offline       = null;

	Font font = new Font("돋움",Font.PLAIN,12);
	
	MainView mv = null;
	
	ClientCtrl cc = null;
	int port = 0;
	
	public VoiceChatRoom() {
		
	}
	public VoiceChatRoom(ClientCtrl cc, int port) {
		this.cc = cc;
		this.port = port;
	}

	public VoiceChatRoom(MainView mv) {
		this.mv = mv;
	}

	public void initDisplay() {
		jbtn_roomSet.addActionListener(this);
		jbtn_ProfileSet.addActionListener(this);
		jbtn_roomInvite.addActionListener(this);
		jbtn_Friend.addActionListener(this);
		jbtn_roomExit.addActionListener(this);
		jbtn_whisper.addActionListener(this);

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
		jp_left_Center_main.setLayout(new LinearLayout(Orientation.VERTICAL,5));		
		jp_left_Center_main_Top.setLayout(new LinearLayout(5));
		jp_left_Center_main_Top_title.setLayout(new BorderLayout());
		jlb_title.setBackground(Color.white);
		jlb_title.setOpaque(true);
		jlb_title.setHorizontalAlignment(JLabel.CENTER);
		jp_left_Center_main_Top_title.add("Center",jlb_title);
		jp_left_Center_main_Top_btn.setLayout(new LinearLayout(5));
		jbtn_ProfileSet.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_roomInvite.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_Friend.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_roomExit.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));
		jbtn_whisper.setBorder(BorderFactory.createEmptyBorder(5 , 5 , 5 , 5));		
		jbtn_ProfileSet.setFont(font);
		jbtn_roomInvite.setFont(font);
		jbtn_Friend.setFont(font);
		jbtn_roomExit.setFont(font);
		jbtn_whisper.setFont(font);
		jp_left_Center_main_Top_btn.add(jbtn_ProfileSet
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_roomInvite
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_Friend
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_whisper
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top_btn.add(jbtn_roomExit
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top.add(jp_left_Center_main_Top_title
				, new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main_Top.add(jp_left_Center_main_Top_btn
				, new LinearConstraints().setWeight(3).setLinearSpace(LinearSpace.MATCH_PARENT));		
		jp_left_Center_main_Talk.setLayout(new BorderLayout());
		jp_left_Center_main_Talk.add("Center",new JTextArea());
		jp_left_Center_main.add(jp_left_Center_main_Top
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center_main.add(jp_left_Center_main_Talk
				, new LinearConstraints().setWeight(15).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center.add(jp_left_Center_voice
				, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Center.add(jp_left_Center_main
				, new LinearConstraints().setWeight(4).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Bottom.setLayout(new LinearLayout(5));	
		jp_left_Bottom_label.setLayout(new BorderLayout());
		jlb_chat.setForeground(Color.white);
		jlb_chat.setBackground(Color.DARK_GRAY);
		jlb_chat.setOpaque(true);
		jlb_chat.setHorizontalAlignment(JLabel.CENTER);
		jp_left_Bottom_label.add("Center",jlb_chat);	
		jp_left_Bottom_chat.setLayout(new BorderLayout());
		jp_left_Bottom_chat.add("Center",tf_chat);		
		jp_left_Bottom.add(jp_left_Bottom_label, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Bottom.add(jp_left_Bottom_chat,  new LinearConstraints().setWeight(15).setLinearSpace(LinearSpace.MATCH_PARENT));
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
		
		this.setTitle("보이스챗룸 테스트중입니다.");
		this.setSize(800, 800);
		this.setVisible(true);
		
		// 비율에 따라 컬럼크기 변경하는 코드
		int onoff_JTable_width = 800 * 1/6;
		jtb_online.getColumnModel().getColumn(0).setPreferredWidth(onoff_JTable_width * 7 / 8);
		jtb_online.getColumnModel().getColumn(1).setPreferredWidth(onoff_JTable_width * 1 / 8);
		jtb_offline.getColumnModel().getColumn(0).setPreferredWidth(onoff_JTable_width * 7 / 8);
		jtb_offline.getColumnModel().getColumn(1).setPreferredWidth(onoff_JTable_width * 1 / 8);
		// 테이블 내용수정불가, 이동,크기 조절 불가
		jtb_online.getTableHeader().setReorderingAllowed(false);
		jtb_online.getTableHeader().setResizingAllowed(false);
		jtb_offline.getTableHeader().setReorderingAllowed(false);
		jtb_offline.getTableHeader().setResizingAllowed(false);
	}
	
	public static void main(String[] args) {
		VoiceChatRoom vr = new VoiceChatRoom();
		vr.initDisplay();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}
