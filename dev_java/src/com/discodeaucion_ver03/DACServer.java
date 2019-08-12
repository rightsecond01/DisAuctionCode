package com.discodeaucion_ver03;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;



public class DACServer extends JFrame implements Runnable {
	
	ServerSocket mainServer = null;
	
	List<DACServerThread> online_userList = null;//main_s_socket과 연결된 상태의 스레드들의 리스트
	List<Room> activeRoomList = null;//연결상태(s_Socket활성화)인 방의 리스트
	
	Socket client = null;
	
	DACServerThread dst = null;
	int roomPort = 20000;	
	
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log);
	
	public DACServer(){
		LoginServerThread lst = new LoginServerThread(this);
		lst.start();
	}
	
	public void initDisplay() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					if(mainServer!=null) mainServer.close();
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.add("Center",jsp_log);
		this.setTitle("서버로그");
		this.setSize(600,500);
		this.setVisible(true);
	}

	@Override
	public void run() {
		
		online_userList = new Vector<DACServerThread>();
		activeRoomList = new Vector<Room>();
		
		boolean isStop = false;
		try {
			mainServer = new ServerSocket(3000);
			while(!isStop) {
				client = mainServer.accept();
				jta_log.append(client.toString()+"\n");
				dst = new DACServerThread(this);
				dst.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DACServer ts = new DACServer();
		ts.initDisplay();
		new Thread(ts).start();
	}

}
