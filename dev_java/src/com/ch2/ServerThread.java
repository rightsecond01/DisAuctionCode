package com.ch2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

public class ServerThread extends Thread {	
	TimeServerVer2 ts2 = null;
	
	ServerThread(TimeServerVer2 ts2){
		this.ts2 = ts2;
		ts2.threadList.add(this);
	}
	
	public String currentTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);
		return (hour < 10 ? "0"+hour:""+hour)
			  +":"+
               (min < 10 ? "0"+min:""+min)
               +":"+
               (sec < 10 ? "0"+sec:""+sec);
	}
	
	@Override
	public void run() {
		boolean isOk = false;
		PrintWriter out = null;
		try {
			out = new PrintWriter(ts2.socket.getOutputStream(),true);
			while(!isOk) {
				out.println(currentTime());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					System.out.println("인터셉트를 당한 경우...");
				}
			}					
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
