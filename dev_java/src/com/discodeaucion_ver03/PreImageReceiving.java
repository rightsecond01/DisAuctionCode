package com.discodeaucion_ver03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class PreImageReceiving extends Thread {

	String token;
	String user_id;
	String room_game;
	String downloadFolder;

	int fileNum;

	Socket socket = null;

	public PreImageReceiving(String user_id, String token, String room_game, int fileNum) {
		this.user_id = user_id;
		this.token = token;
		this.room_game = room_game;
		this.fileNum = fileNum;
		this.downloadFolder = "C:\\chat_temp\\" + user_id + "\\" + token.substring(0, 10) + "\\" + room_game + "\\";
	}

	@Override
	public void run() {
		/////// 디렉토리 생성//////////
		File folder = new File(downloadFolder);
		if (!folder.exists()) {
			folder.mkdir();
		}
		//// 끝////

		try {
			for (int i = 0; i < fileNum; i++) {
				socket = new Socket("192.168.0.9", 15000);
				BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
				DataInputStream dis = new DataInputStream(bis);
				String receiveFileName = dis.readUTF();
				File imgfile = new File(downloadFolder + receiveFileName);
				FileOutputStream fos = new FileOutputStream(imgfile);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
				int len;
				byte[] data = new byte[4096];
				while ((len = dis.read(data)) != -1) {
					bos.write(data, 0, len);
					bos.flush();
				}
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
