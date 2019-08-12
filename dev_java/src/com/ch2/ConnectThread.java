package com.ch2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectThread {
	static String[] mem_id = {"test","test2","test3"};
	static String path = "C:\\roomtitle\\";
	
	public static void main(String[] args) {
		try {
			
			for(int i=0;i<3;i++) {
				Socket a = new Socket("127.0.0.1",11002);
				BufferedInputStream bis = new BufferedInputStream(a.getInputStream());
				DataInputStream dis = new DataInputStream(bis);
				String fileName = dis.readUTF();
				///////디렉토리 생성//////////
				File saveFolder = new File(path + mem_id[i]);
				if(!saveFolder.exists()) {
					saveFolder.mkdir();
				}
				/////////끝///////////////
				
				File imgfile = new File(path + mem_id[i]+"\\"+fileName);
				FileOutputStream fos = new FileOutputStream(imgfile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int len;
				int r_data = 0;
				
				byte[] data = new byte[4096];
				while((len = dis.read(data)) != -1) {	
					bos.write(data,0,len);
					bos.flush();
					System.out.println("["+(r_data++)+"]" + len);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
