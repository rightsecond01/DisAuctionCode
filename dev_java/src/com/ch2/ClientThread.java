package com.ch2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	TimeClientVer2 tc2 = null;
	
	public ClientThread(TimeClientVer2 tc2) {
		this.tc2=tc2;
	}

	@Override
	public void run() {
		PrintWriter out = null;
		BufferedReader in = null;
		String timeInfo = null;
		try {
			tc2.client = new Socket("192.168.0.9",3000);
			out = new PrintWriter(
					tc2.client.getOutputStream(),true);
			in = new BufferedReader(
					new InputStreamReader(
							tc2.client.getInputStream()));
			while(true) {
				while((timeInfo=in.readLine())!=null) {
					tc2.jlb_time.setText(timeInfo);
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
				tc2.client.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

}
