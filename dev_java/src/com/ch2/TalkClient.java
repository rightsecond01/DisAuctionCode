package com.ch2;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class TalkClient extends JFrame{
	JPanel jp_right = new JPanel(); // 이모티콘/1:1채팅/나가기 버튼 
	JPanel jp_left = new JPanel(); 	// 채팅을치는곳
	JPanel jp_right_up = new JPanel(); // 접속자를 보여주는곳 
	JPanel jp_left_small = new JPanel(); // text옆에 전송버튼 
	
	JTextField jtf = new JTextField("채팅입력하라구~"); // 채팅을 입력하는곳 
	JTable jt = new JTable();								
	JScrollPane jsp = new JScrollPane(jt);
	
	
	JButton jb_imo = new JButton("이모티콘");
	JButton jb_chat = new JButton("1:1챗팅");
	JButton jb_exit = new JButton("나가기");
	JButton jb_send = new JButton("전송");
	String comeon = null;							// 타이틀의 들어온사람의 닉네임을 보여줌 
	StyledDocument sd_display = 
			new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	JScrollPane jsp_display		= new JScrollPane(jtp_display
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	String cols[] = {"접속자"};
	String data[][] = new String[0][1];
	DefaultTableModel dtm_name	= new DefaultTableModel(data,cols);
	
	
 	public void initDisplay() {  // 화면처리하는곳 
 		jp_left.setLayout(new BorderLayout());
 		jp_left.add("Center",jsp_display);
 		jp_left_small.setLayout(new BorderLayout());
 		jp_left_small.add(jb_send);
 		jp_left_small.add(jb_send);
 		jp_left_small.add(jtf);
 		jp_left.add("South",jp_left_small);
 		//jp_left.add("South",jtf);// 메시지 입력 
 		
 		jp_right_up.setLayout(new BorderLayout());
 		jp_right_up.add("Center",jsp);
 		jp_right.setLayout(new GridLayout(3,0));	// 3로우
		jp_right.add(jb_imo);				
		jp_right.add(jb_chat);
		jp_right.add(jb_exit);
		jp_right_up.add("South",jp_right);
		
		
		this.add("Center",jp_left);
		this.add("East",jp_right_up);
		
		//jp_left_small.add("East",jb_send);
		
		
		
//		//this.add("Center",jp_down);
//		jp_down.add("East",jp_right);
//		this.add("Center",jp_left);
//		
	
		
		setSize(1000, 800);
		setVisible(true);
		setTitle(comeon+"님의 채팅방");
	}
	

	public static void main(String[] args) {
		TalkClient tc = new TalkClient();
		tc.initDisplay();
	}

}
