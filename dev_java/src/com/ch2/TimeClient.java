package com.ch2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TimeClient extends JFrame implements Runnable{
	//선언부
	
	Socket client = null;
	
	JLabel jlb_time = new JLabel("현재시간",JLabel.CENTER);
	//생성자
	public TimeClient() {
		
	}
	public void initDisplay() {
		this.add("North",jlb_time);
		this.setTitle("내단말기");
		this.setSize(500,400);
		this.setVisible(true);
	}
	public void init(String ip, int port) {
		
		try {
			client = new Socket(ip,port);
		} catch (UnknownHostException ue) {
			System.out.println("알수없는 오류");
		} catch(IOException ioe) {
			System.out.println("포트에 문제가 있습니다.");
		}
	}
	//메인메소드
	public static void main(String[] args) {
		TimeClient tc = new TimeClient();
		Thread thread = new Thread(tc);
		thread.start();
		tc.initDisplay();
		System.out.println("before tc : " + tc.client);
		tc.init("192.168.0.9",3001);
		System.out.println("after tc : " + tc.client);
	}
	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		String timeInfo = null;
		try {
			out = new PrintWriter(client.getOutputStream(),true);
			in = new BufferedReader(
					new InputStreamReader(
							client.getInputStream()));
			while(true) {
				while((timeInfo=in.readLine())!=null) {
					jlb_time.setText(timeInfo);
					Thread.sleep(1000);
				}
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				out.close();
			} catch (Exception e2) {
				e2.printStackTrace();				
			}
		}
		
	}
}
