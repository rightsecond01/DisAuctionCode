package com.network23;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.network1.Protocol;

public class TalkClient extends JFrame implements ActionListener {
	JPanel jp_first = new JPanel();
	JPanel jp_second = new JPanel();
	JPanel jp_second_south = new JPanel();

	StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	JScrollPane jsp_display = new JScrollPane(jtp_display, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JTextField jtf_msg = new JTextField("메세지를 입력하세요");
	String cols[] = { "닉네임" };
	String data[][] = new String[0][1];
	DefaultTableModel dtm_name = new DefaultTableModel(data, cols);
	JTable jtb_name = new JTable(dtm_name);
	JScrollPane jsp_name = new JScrollPane(jtb_name);
	
	JButton jbtn_whisper = new JButton("1:1대화");
	JButton jbtn_change = new JButton("대화명변경");
	JButton jbtn_icon = new JButton("이모티콘");
	JButton jbtn_exit = new JButton("종료");
	
	String nickName = null;
	String receiver = null;
	
	

	Socket mySocket = null;
	String ip = "127.0.0.1";
	int port = 5000;

	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	
	EmoticonMessage emo = null;

	public TalkClient() {
		nickName = JOptionPane.showInputDialog("대화명을 입력하세요");		
		initDisplay();
		try {
			mySocket = new Socket(ip, port);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());

			oos.writeObject(Protocol.ROOM_IN + Protocol.seperator + nickName);
			TalkClientThread tct = new TalkClientThread(this);
			tct.start();//여기까지 실행되면, dtm은 채워진다.
			
			
			// oos,ois 언제든 준비완료
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initDisplay() {	
		
		GridLayout gl = new GridLayout(1,2);		
		this.setLayout(new GridLayout(1, 2));

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_display);
		jp_first.add("South", jtf_msg);

		jp_second.setLayout(new BorderLayout());
		jsp_name.setPreferredSize(new Dimension(200, 600));
		jp_second.add("Center", jsp_name);

		jp_second_south.setLayout(new GridLayout(2, 2));
		jp_second_south.add(jbtn_whisper);
		jp_second_south.add(jbtn_change);
		jp_second_south.add(jbtn_icon);
		jp_second_south.add(jbtn_exit);
		jp_second.add("South", jp_second_south);

		jtf_msg.addActionListener(this);
		jbtn_whisper.addActionListener(this);
		jbtn_change.addActionListener(this);
		jbtn_icon.addActionListener(this);
		jbtn_exit.addActionListener(this);

		this.add("Center", jp_first);
		this.add("East", jp_second);
		this.setTitle(nickName + "님의 대화창");
		this.setSize(800, 600);
		this.setVisible(true);
	}

	public void exitChat() {
		try {
			oos.writeObject(Protocol.ROOM_OUT + Protocol.seperator + nickName);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void change_process() {
		String afterName = JOptionPane.showInputDialog("변경할 대화명을 입력하세요.");
		
		if(afterName==null||afterName.length()<=1) {
			return;
		}
		try {
			oos.writeObject(Protocol.CHANGE
				       +Protocol.seperator+nickName
				       +Protocol.seperator+afterName
				       +Protocol.seperator+nickName + "님의 대화명이 "+afterName+"으로 변경 되었습니다.");			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void message_process(String msg) {
	
			if(msg==null||msg.length()==0) {//이모티콘일때
				msg="이모티콘";
				try {
					oos.writeObject(Protocol.MESSAGE
						       +Protocol.seperator+nickName
						       +Protocol.seperator+msg
						       +Protocol.seperator+emo.imgChoice);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {//이모티콘 아닐때
				try {
				oos.writeObject(Protocol.MESSAGE
						       +Protocol.seperator+nickName
						       +Protocol.seperator+msg
						       +Protocol.seperator+"default"//emo.imgChoice의 기본값
						       );
			} catch (IOException e) {
				e.printStackTrace();
			         }
			}			
		}		
	
	
	public void whisper_process(String msg) {
		if(msg==null||msg.length()==0) {//이모티콘일때
			msg="이모티콘";
			try {
				oos.writeObject(Protocol.WHISHER
					       +Protocol.seperator+nickName
					       +Protocol.seperator+msg
					       +Protocol.seperator+emo.imgChoice
					       +Protocol.seperator+receiver
					       );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {//이모티콘 아닐때
			try {				
				oos.writeObject(Protocol.WHISHER
						       +Protocol.seperator+nickName
						       +Protocol.seperator+msg
						       +Protocol.seperator+"default"//emo.imgChoice의 기본값
						       +Protocol.seperator+receiver
						       );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}

	public static void main(String[] args) {
		TalkClient tc = new TalkClient();// oos, ois 초기화 됨.		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String msg = jtf_msg.getText();
		Object obj = e.getSource();
		if (obj == jtf_msg) {
			if (msg.substring(0, 2).equals("/w")) {
				receiver = msg.substring(3, msg.indexOf(":"));
				int effective_nickName = 0;
				
				Vector v = dtm_name.getDataVector();
				for (int i = 0; i < v.size(); i++) {
					if ((v.get(i).toString()).equals("[" + receiver + "]")) {
						effective_nickName = 1;
					}
				}
				
				if(effective_nickName==1) {
					String whisher = msg.substring(msg.indexOf(":") + 1);
					whisper_process(whisher);
					jtf_msg.setText("/w " + receiver + ":");
				}
				else {
					JOptionPane.showMessageDialog(this, "유효한 닉네임을 입력하세요.");
					return;
				}				
			} else {
				message_process(msg);
				jtf_msg.setText("");
			}

		}
		else if(obj==jbtn_icon) {
			emo = new EmoticonMessage(this);
			emo.initDisplay();
			
		}
		else if (obj == jbtn_exit) {
			exitChat();
		}
		else if (obj == jbtn_whisper) {
			try {
				int row = jtb_name.getSelectedRow();
				receiver = jtb_name.getValueAt(row, 0).toString();// 귓속말을 보낼 대상
				jtf_msg.setText("/w " + receiver + ":");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "메세지를 보낼 대상을 선택하세요.");
				e2.printStackTrace();
			}

		}
		else if(obj == jbtn_change) {
			change_process();
		}

	}

}
