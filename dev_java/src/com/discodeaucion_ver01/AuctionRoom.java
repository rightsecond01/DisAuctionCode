package com.discodeaucion_ver01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import com.network1.Protocol;

public class AuctionRoom extends JPanel implements ActionListener {
	JPanel jp_left = new JPanel();
	JPanel jp_left_Top = new JPanel();
	JPanel jp_left_Top_roomTitle = new JPanel();
	JPanel jp_left_Top_btn = new JPanel();
	JPanel jp_left_Main = new JPanel();
	JPanel jp_left_Main_Talk = new JPanel();
	JPanel jp_left_Main_Search = new JPanel();
	JPanel jp_left_Text = new JPanel();
	JPanel jp_left_Text_label = new JPanel();
	JPanel jp_left_Text_chat = new JPanel();
	JPanel jp_right = new JPanel();
	JPanel jp_right_online = new JPanel();
	JPanel jp_right_offline = new JPanel();

	JLabel jlb_title = new JLabel("방 제목");

	// roomSet은 마스터의 경우만 활성화
	JButton jbtn_roomSet = new JButton("방 설정");

	JButton jbtn_ProfileSet = new JButton("설정");
	JButton jbtn_roomInvite = new JButton("초대");
	JButton jbtn_Friend = new JButton("친구");
	JButton jbtn_roomExit = new JButton("나가기");
	JButton jbtn_whisper = new JButton("귓속말");

	JLabel jlb_chat = new JLabel("채팅");
	JTextField jtf_chat = new JTextField();

	String cols2[] = { "접속중인 친구", "" };
	String data2[][] = new String[0][2];
	String cols3[] = { "오프라인 친구", "" };
	String data3[][] = new String[0][2];

	DefaultTableModel dtm_online = null;
	JTable jtb_online = null;
	JScrollPane jsp_online = null;
	DefaultTableModel dtm_offline = null;
	JTable jtb_offline = null;
	JScrollPane jsp_offline = null;

	Font font = new Font("돋움", Font.PLAIN, 12);

	Socket roomSocket = null;
	ObjectOutputStream room_oos = null;
	ObjectInputStream room_ois = null;
	ClientCtrl cc = null;
	int port = 0;

	String receiver = null;

	public AuctionRoom() {

	}

	public AuctionRoom(ClientCtrl cc, int port) {
		this.cc = cc;
		this.port = port;
		initDisplay();
		connect_process();
	}

	public void initDisplay() {
		jtf_chat.addActionListener(this);
		jbtn_roomSet.addActionListener(this);
		jbtn_ProfileSet.addActionListener(this);
		jbtn_roomInvite.addActionListener(this);
		jbtn_Friend.addActionListener(this);
		jbtn_roomExit.addActionListener(this);
		jbtn_whisper.addActionListener(this);
		//////////////// 테이블 설정 시작 /////////////////////////////////////////////
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

		jp_left.setLayout(new LinearLayout(Orientation.VERTICAL, 5));
		jp_left_Top.setLayout(new LinearLayout(5));
		jp_left_Top_roomTitle.setLayout(new BorderLayout());
		jlb_title.setBackground(Color.white);
		jlb_title.setOpaque(true);
		jlb_title.setHorizontalAlignment(JLabel.CENTER);
		jp_left_Top_roomTitle.add("Center", jlb_title);
		jp_left_Top_btn.setLayout(new LinearLayout(5));
		jbtn_ProfileSet.setFont(font);
		jbtn_roomInvite.setFont(font);
		jbtn_Friend.setFont(font);
		jbtn_roomExit.setFont(font);
		jbtn_whisper.setFont(font);
		jbtn_ProfileSet.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jbtn_roomInvite.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jbtn_Friend.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jbtn_roomExit.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		jbtn_whisper.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		jp_left_Top_btn.add(jbtn_ProfileSet,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Top_btn.add(jbtn_roomInvite,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Top_btn.add(jbtn_Friend, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Top_btn.add(jbtn_whisper,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Top_btn.add(jbtn_roomExit,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Top.add(jp_left_Top_roomTitle,
				new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Top.add(jp_left_Top_btn, new LinearConstraints().setWeight(3).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Main.setLayout(new LinearLayout(Orientation.VERTICAL, 5));
		jp_left_Main_Talk.setLayout(new BorderLayout());
		jp_left_Main_Talk.add("Center", new JTextArea());
		jp_left_Main_Search.setLayout(new BorderLayout());
		jp_left_Main_Search.setLayout(new BorderLayout());
		jp_left_Main_Search.add("Center", new JTextArea());
		jp_left_Main.add(jp_left_Main_Talk,
				new LinearConstraints().setWeight(15).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Main.add(jp_left_Main_Search,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Text.setLayout(new LinearLayout(5));
		jp_left_Text_label.setLayout(new BorderLayout());
		jlb_chat.setForeground(Color.white);
		jlb_chat.setBackground(Color.DARK_GRAY);
		jlb_chat.setOpaque(true);
		jlb_chat.setHorizontalAlignment(JLabel.CENTER);
		jp_left_Text_label.add("Center", jlb_chat);
		jp_left_Text_chat.setLayout(new BorderLayout());
		jp_left_Text_chat.add("Center", jtf_chat);
		jp_left_Text.add(jp_left_Text_label,
				new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left_Text.add(jp_left_Text_chat,
				new LinearConstraints().setWeight(15).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left.add(jp_left_Top, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left.add(jp_left_Main, new LinearConstraints().setWeight(20).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_left.add(jp_left_Text, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));

		jp_right.setLayout(new LinearLayout(Orientation.VERTICAL, 5));
		jp_right_online.setLayout(new BorderLayout());
		jp_right_online.add("Center", jsp_online);
		jp_right_offline.setLayout(new BorderLayout());
		jp_right_offline.add("Center", jsp_offline);
		jp_right.add(jp_right_online, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_right.add(jp_right_offline, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));

		this.add(jp_left, new LinearConstraints().setWeight(4).setLinearSpace(LinearSpace.MATCH_PARENT));
		this.add(jp_right, new LinearConstraints().setWeight(1).setLinearSpace(LinearSpace.MATCH_PARENT));

		// 단위 테스트후 삭제할 코드
		/*
		 * this.setTitle("옥션룸 테스트중입니다."); this.setSize(800, 800); this.setVisible(true);
		 */

		// 비율에 따라 컬럼크기 변경하는 코드
		int jtb_width = 800 * 1 / 5;
		jtb_online.getColumnModel().getColumn(0).setPreferredWidth(jtb_width * 7 / 8);
		jtb_online.getColumnModel().getColumn(1).setPreferredWidth(jtb_width * 1 / 8);
		jtb_offline.getColumnModel().getColumn(0).setPreferredWidth(jtb_width * 7 / 8);
		jtb_offline.getColumnModel().getColumn(1).setPreferredWidth(jtb_width * 1 / 8);

		// 테이블 내용수정불가, 이동,크기 조절 불가
		jtb_online.getTableHeader().setReorderingAllowed(false);
		jtb_online.getTableHeader().setResizingAllowed(false);
		jtb_offline.getTableHeader().setReorderingAllowed(false);
		jtb_offline.getTableHeader().setResizingAllowed(false);
	}

	/*
	 * public static void main(String[] args) { AuctionRoom ar = new AuctionRoom();
	 * ar.initDisplay(); }
	 */
	public void connect_process() {
		try {
			roomSocket = new Socket(cc.ip, port);			
			cc.rSocketList.add(roomSocket);
			room_oos = new ObjectOutputStream(roomSocket.getOutputStream());
			room_ois = new ObjectInputStream(roomSocket.getInputStream());

			AuctionRoomThread art = new AuctionRoomThread(this);
			art.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void message_process(String msg) {

		try {
			room_oos.writeObject(Dprotocol.MESSAGE 
					           + Dprotocol.seperator + cc.user_id 
					           + Dprotocol.seperator + msg
					           );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void whisper_process(String msg) {

		try {
			room_oos.writeObject(Dprotocol.WHISHER 
					           + Dprotocol.seperator + cc.user_id 
					           + Dprotocol.seperator + msg
					           + Dprotocol.seperator + receiver
					           );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = jtf_chat.getText();
		Object obj = e.getSource();
		if (obj == jtf_chat) {
			if ((msg.length() > 1) && (msg.substring(0, 2).equals("/w"))) {
				receiver = msg.substring(3, msg.indexOf(":"));

				boolean isEnableNick = false;
				Vector v = dtm_online.getDataVector();
				for (int i = 0; i < v.size(); i++) {
					if ((v.get(i).toString()).equals("[" + receiver + "]")) {
						isEnableNick = true;
					}
				}
				if (isEnableNick) {
					String whisher = msg.substring(msg.indexOf(":") + 1);
					whisper_process(whisher);
					jtf_chat.setText("/w " + receiver + ":");
				} else {
					JOptionPane.showMessageDialog(cc, "유효한 닉네임을 입력하세요.");
					return;
				}
			} else {
				message_process(msg);
				jtf_chat.setText("");
			}
		} else if (obj == jbtn_ProfileSet) {
			try {
				room_oos.writeObject(Dprotocol.TEST + Dprotocol.seperator + "테스트!!!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
