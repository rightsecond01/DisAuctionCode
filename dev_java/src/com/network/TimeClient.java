package com.network;

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
	//화면 처리하기
	public void initDisplay() {
		this.add("North",jlb_time);
		this.setTitle("내 단말기");
		this.setSize(500, 400);
		this.setVisible(true);
	}
	//소켓 초기화
	public void init(String ip, int port) {
		try {
			client = new Socket(ip,port);
		} catch (UnknownHostException ue) {
			// TODO: handle exception
		} catch(IOException io) {
			
		}
	}
	//메인메소드
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TimeClient tc = new TimeClient();
		//화면을 처리하는 메소드 호출하기
		tc.initDisplay();
		System.out.println("Socket 정보 확인(before):"+tc.client);
		//소켓 생성 메소드 호출하기
		tc.init("192.168.0.187", 3000);
		Thread thread = new Thread(tc);
		thread.start();//run()호출하기
		System.out.println("Socket 정보 확인(after):"+tc.client);
		
	}
	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		String timeInfo = null;
		try {
			out = new PrintWriter(
					client.getOutputStream(),true);
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
				client.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}












