package com.discodeaucion_ver01;

import java.awt.Container;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class ClientCtrl extends JFrame implements Runnable {

	JTabbedPane tp = new JTabbedPane();
	MainView mv = new MainView(this);

	Container con = this.getContentPane();

	Socket mainSocket = null;
	List<Socket> rSocketList = null;
	

	ObjectOutputStream main_oos = null;
	ObjectInputStream main_ois = null;	

	String user_id = null;
	String nickName = null;

	////////// 방생성시 사용 변수///////////
	String master_id    = null;
	String token        = null;	
	String room_title   = null;
	String room_game    = null;
	int room_type = 0;
	int p_limit   = 0;
	String[] s_roomType = {"AuctionRoom","VoiceTalkRoom"};
	////////////////////////////////
	//////접속중인(토큰을 입력한) 방관리/////
	List<Map<String,Object>> acceptRoomList = new Vector<Map<String,Object>>();

	String ip = "127.0.0.1";
	
	Login login = null;
	public ClientCtrl(Login login) {
		this.login = login;
		user_id = login.user_id;
		nickName = login.nickName;
		initDisplay();
		connect_process();
	}

	public void initDisplay() {
		con.setLayout(null);
		tp.setBounds(5, 4, 800, 800);
		tp.addTab("메인화면", mv);
		con.add(tp, null);
		this.setSize(860, 860);
		this.setVisible(true);
	}

	public void connect_process() {
		this.setTitle("디스옥션코드");
		int port = 3000;
		try {
			mainSocket = new Socket(ip, port);
			main_oos = new ObjectOutputStream(mainSocket.getOutputStream());
			main_ois = new ObjectInputStream(mainSocket.getInputStream());
			main_oos.writeObject(Dprotocol.LOG_IN 
					           + Dprotocol.seperator + user_id
					           + Dprotocol.seperator + nickName
					           );
			rSocketList = new Vector<Socket>();
			
			ClientCtrlThread cct = new ClientCtrlThread(this);
			cct.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		rSocketList = new Vector<Socket>();
		
		
		boolean isStop = false;
		try {
			while (!isStop) {
				String msg = (String) main_ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {
				
				}
			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
