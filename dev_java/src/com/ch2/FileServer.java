package com.ch2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends Thread {
	String path = "C:\\workspace_java\\dev_java\\src\\com\\images\\profile\\";
	ServerSocket fileServerSocket = null;
	Socket fileSocket = null;
	int fileportNumber = 11002;

	public void run() {

		try {
			fileServerSocket = new ServerSocket(fileportNumber);
			File[] files = new File(path).listFiles(); // path경로에있는 파일 모두 읽어들임니다.
			try {
				for (int i = 0; i < files.length; i++) { // 파일 개수 만큼 Socket 돕니다.
					if ((files[i].getName()).equals("ImageResize")) {
						System.out.println("!!!");
					} else {
						fileSocket = fileServerSocket.accept();
						BufferedOutputStream bos = new BufferedOutputStream(fileSocket.getOutputStream());
						DataOutputStream dos = new DataOutputStream(bos);
						System.out.println("파일개수?" + files[i].getName() + "," + files[i].length());
						dos.writeUTF("히히" + ".png"); // 파일 이름 받아옵니다.
						FileInputStream fis = new FileInputStream(files[i]);
						BufferedInputStream bis = new BufferedInputStream(fis);
						byte[] buf = new byte[4096]; // buf 생성합니다.
						int theByte = 0;
						while ((theByte = bis.read(buf)) > 0) // BufferedInputStream으로
						// 클라이언트에 보내기 위해 write합니다.
						{
							bos.write(buf, 0, theByte);
						}
						dos.flush();
						System.out.println("마지막값" + theByte);
						System.out.println("송신완료");
						dos.close();
						bos.close();
						fileSocket.close(); // socket 닫아줌
					}

				}
				fileServerSocket.close(); // 파일을 다 전송했으면 ServerSocket 닫아줌니다.
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) {
		FileServer a = new FileServer();
		a.start();
	}

}