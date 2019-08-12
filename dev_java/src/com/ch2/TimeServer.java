package com.ch2;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TimeServer extends JFrame implements Runnable {
	JTextArea jta_log = new JTextArea();
	JScrollPane jsp_log = new JScrollPane(jta_log);
	Socket socket = null;
	ServerSocket sSocket = null;

	public TimeServer() {
	}

	public void initDisplay() {
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});
		this.setTitle("서버측 로그 출력화면");
		this.add("Center", jsp_log);
		this.setSize(500, 400);
		this.setVisible(true);
	}

	public String currentTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		return (hour < 10 ? "0" + hour : "" + hour) + ":" + (min < 10 ? "0" + min : "" + min) + ":"
				+ (sec < 10 ? "0" + sec : "+sec");
	}

	public static void main(String[] args) {
		TimeServer ts = new TimeServer();
		ts.initDisplay();
		// 실제로 대화하는 데 필요한 소켓
		try {
			ts.sSocket = new ServerSocket(3001);
			while (true) {
				System.out.println("I wait jupsok");
				ts.socket = ts.sSocket.accept();
				// System.out.println("접속한 클라이언트측 정보 출력하기==>" + socket.getInetAddress());
				// ts.jta_log.append(socket.getInetAddress().getHostName()+"\n");
				ts.jta_log.append(ts.currentTime());
				Thread thread = new Thread(ts);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("서버가 비정상입니다.");
			System.out.println("포트에 문제가 있습니다.");
		} finally {
			try {
				if (ts.socket != null)
					ts.socket.close();
				if (ts.sSocket != null)
					ts.sSocket.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		System.out.println("서버 기동 성공");
	}

	@Override
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			while (true) {
				out.println(currentTime());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					System.out.println("인터셉트를 당해버림");
				}
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
