package com.ch2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import org.glassfish.grizzly.SocketAcceptor;

public class ServerTest extends JFrame{
	ServerSocket sSocket = null;
	Socket socket = null;
	public void initDisplay() {
		this.setSize(500, 300);
		this.setVisible(true);
	}
	public void serverInit() {
		try {
			sSocket = new ServerSocket(5000);
			System.out.println("접속기다리는중");
			while(true) {
				socket = sSocket.accept();
				System.out.println("클라이언트 접속" + socket);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ServerTest st = new ServerTest();
		st.serverInit();
		st.initDisplay();
	}

}
