package com.ch2;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.network.TimeServer;

public class TimeServerVer2 extends JFrame implements Runnable{
	ServerSocket sSocket = null;	
	Socket socket = null;//실제로 대화하는데 필요한 소켓
	List<ServerThread> threadList = null;
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log);
	ServerThread sThread = null;
	
	public void initDisplay() {
		this.setTitle("서버측 로그 출력화면");
		this.add("Center",jsp_log);
		this.setSize(500, 400);
		this.setVisible(true);		
	}
	
	
	
	public static void main(String[] args) {
		TimeServerVer2 ts2 = new TimeServerVer2();
		ts2.initDisplay();
		Thread thread = new Thread(ts2);
		thread.start();
		System.out.println("서버 기동 성공.....");
	}
	@Override
	public void run() {
		threadList = new Vector<ServerThread>();
		try {
			sSocket = new ServerSocket(3000);
			while(true) {
			    jta_log.append("접속을 기다립니다....\n");
				socket = sSocket.accept();				
				System.out.println
				("접속한 클라이언트측 정보 출력하기==>"+socket.getInetAddress());
				jta_log.append(socket+"에 연결되었습니다."+"\n");
				sThread = new ServerThread(this);
				sThread.start();
			}
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
