package com.discodeaucion_ver03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class UploadImgReceiving extends Thread{
	String token = null;
	String imgFileName = null;
	int port = 0;
	String saveFolderPath = null;

	
	Socket socket = null;
	BufferedInputStream bis = null;
	public UploadImgReceiving(String token, String imgFileName, int port,String saveFolderPath) {
		this.token = token;
		this.imgFileName = imgFileName;
		this.port = port;
		this.saveFolderPath = saveFolderPath;
	}
	
	@Override
	public void run() {
		
		try {
			socket = new Socket("192.168.0.9",port);
			bis = new BufferedInputStream(socket.getInputStream());
			DataInputStream dis = new DataInputStream(bis);
			File imgfile = new File(saveFolderPath + imgFileName);
			FileOutputStream fos = new FileOutputStream(imgfile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			int len;
			byte[] data = new byte[4096];
			while((len = dis.read(data)) != -1) {
				bos.write(data,0,len);
				bos.flush();
			}
			bos.close();
			fos.close();
			dis.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
