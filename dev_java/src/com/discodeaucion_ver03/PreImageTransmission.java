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

public class PreImageTransmission extends Thread {
	
	String gamesImageFolder = "C:\\workspace_java\\dev_java\\src\\com\\images\\games\\";
	ServerSocket imgSendServer = null;
	Socket receiver = null;
	
	String room_game = null;
	public PreImageTransmission(String room_game) {
		this.room_game = room_game;
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
	public void run() {
		File[] files = new File(gamesImageFolder + room_game).listFiles();
		try {
			imgSendServer = new ServerSocket(15000);
			for(int i=0;i<files.length;i++) {
				receiver = imgSendServer.accept();
				BufferedOutputStream bos = new BufferedOutputStream(receiver.getOutputStream());
				DataOutputStream dos = new DataOutputStream(bos);
				String fileName = files[i].getName();
				dos.writeUTF(fileName);
				FileInputStream fis = new FileInputStream(files[i]);
				BufferedInputStream bis = new BufferedInputStream(fis);
				byte[] buf = new byte[4096];
				int theByte = 0;
				while ((theByte = bis.read(buf)) > 0) 
				{
					bos.write(buf, 0, theByte);
				}
				dos.flush();
				dos.close();
				bos.close();
				receiver.close();
			}
			imgSendServer.close();
			int pid = getProcessID(15000);
			Runtime.getRuntime().exec("taskkill /F /PID " + pid);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
