package com.discodeaucion_ver03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class UploadImgTransmission extends Thread{
	
	int port = 0;
	String filePath = null;
	
	ServerSocket imgSendServer = null;
	Socket receiver = null;
	DataOutputStream dos = null;
	
	public UploadImgTransmission(int port, String filePath) { //filePath : 전송할 파일의 원본주소
		this.port = port;
		this.filePath = filePath;
	}
	
	 public int getProcessID(int port) throws IOException {
	        Process ps = new ProcessBuilder("cmd", "/c", "netstat -a -o").start();
	        BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
	        String line;
	        while ((line = br.readLine()) != null) {
	            if (line.contains(":" + port)) {
	                while (line.contains("  ")) {
	                    line = line.replaceAll("  ", " ");
	                }
	                int pid = Integer.valueOf(line.split(" ")[5]);
	                ps.destroy();
	                return pid;
	            }
	        }
	        return -1;
	    }

	
	@Override
	public void run(){
		try {
			imgSendServer = new ServerSocket(port);
			receiver = imgSendServer.accept();			
			dos = new DataOutputStream(receiver.getOutputStream());
			File profileImg = new File(filePath);
			FileInputStream fis = new FileInputStream(profileImg);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] data = new byte[4096];
			int len = 0;
			while((len=bis.read(data))!=-1) {
				dos.write(data,0,len);
			}
			dos.flush();
			dos.close();
			receiver.close();
			imgSendServer.close();
			int pid = getProcessID(port);
			Runtime.getRuntime().exec("taskkill /F /PID " + pid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
