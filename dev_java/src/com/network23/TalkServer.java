package com.network23;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TalkServer extends JFrame implements Runnable {
	
	ServerSocket server = null;
	
	Socket client = null;
	
	TalkServerThread tst = null;
	List<TalkServerThread> globalList = null;
	List<Room> roomList = null;
	
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log);
	public TalkServer(){
		
	}
	
	public void initDisplay() {
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					if(server!=null) server.close();
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
		globalList = new Vector<TalkServerThread>();
		roomList = new Vector<>();
		boolean isStop = false;
		try {
			server = new ServerSocket(5000);
			while(!isStop) {
				client = server.accept();
				jta_log.append(client.toString()+"\n");
				tst = new TalkServerThread(this);
				tst.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TalkServer ts = new TalkServer();
		ts.initDisplay();
		new Thread(ts).start();
	}

}
