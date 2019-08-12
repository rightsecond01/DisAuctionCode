package com.ch2;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class TimeClientVer2 extends JFrame {
	Socket client = null;
	JLabel jlb_time = null;
	
	public TimeClientVer2(JLabel jlb_time) {
		this.jlb_time=jlb_time;
	}
	
	public TimeClientVer2() {
		// TODO Auto-generated constructor stub
	}
	public void initDisplay() {
		this.add("North",jlb_time);
		this.setTitle("내 단말기");
		this.setSize(500, 400);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		TimeClientVer2 tc2 = new TimeClientVer2();
		tc2.initDisplay();
		try {			
			ClientThread ct = new ClientThread(tc2);
			ct.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
