package com.network23;

import java.awt.Container;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import com.network1.Protocol;



public class TalkClientVer2 extends JFrame {
	Login login = null;
	JTabbedPane tp = new JTabbedPane();
	WaitRoom    wr = new WaitRoom();
	MessageRoom mr = new MessageRoom(this);
	MessageRoom mr2 = new MessageRoom(this);
	
	Socket mySocket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	
	String ip = "127.0.0.1";
	int port = 5000;
	String nickName = null;
	
	Container con = this.getContentPane();
	
	public TalkClientVer2() {
		
	}

	public TalkClientVer2(Login login) {
		this.login = login;
		nickName = login.nickName;
		initDisplay();
		connect_process();
		
	}
	public void initDisplay() {
		con.setLayout(null);
		tp.addTab("대기실", wr);		
		tp.setBounds(5, 4, 627, 530);
		con.add(tp,null);		
		this.setSize(655,585);
		this.setVisible(true);
	}
	public void connect_process() {
		this.setTitle(nickName+"님의 대화창");
		try {
			mySocket = new Socket(ip,port);
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
			oos.writeObject(Protocol.WAIT
					       +Protocol.seperator+nickName
					       +Protocol.seperator+"대기"
					       );
			TalkClientThread tct = new TalkClientThread(this);
			tct.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

}
