package com.discodeaucion_ver03;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.util.ImageResize;

public class ImagesReceiving extends Thread{
	
	int memberSize;
	String token;
	String user_id;
	
	public ImagesReceiving(int memberSize,String token, String user_id) {
		this.memberSize = memberSize;
		this.token = token;
		this.user_id = user_id;
	}
	

	@Override
	public void run() {
		String parentFolderPath = "C:\\chat_temp\\" + user_id + "\\" + token.substring(0,10) + "\\";
	    ///////부모디렉토리 생성//////////
		File parentFolder = new File(parentFolderPath);
		if(!parentFolder.exists()) {	
			parentFolder.mkdir();
			}
		////끝////
		String saveFolderPath = parentFolderPath + "\\profiles\\";		
		Socket socket = null;
			try {
				///////디렉토리 생성//////////
				File saveFolder = new File(saveFolderPath);
				if(!saveFolder.exists()) {	
					saveFolder.mkdir();
				}
				/////////끝///////////////	
				for(int i=0;i<memberSize;i++) {
					socket = new Socket("192.168.0.9",60000);
					BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
					DataInputStream dis = new DataInputStream(bis);
					String receiveFileName = dis.readUTF();
					String mem_id = receiveFileName.substring(0,receiveFileName.indexOf("_"));
					if(!mem_id.equals(user_id)) {
						File imgfile = new File(saveFolderPath +receiveFileName);
						FileOutputStream fos = new FileOutputStream(imgfile);
						BufferedOutputStream bos = new BufferedOutputStream(fos);
						int len;
						byte[] data = new byte[4096];
						while((len = dis.read(data)) != -1) {
							bos.write(data,0,len);
							bos.flush();
						}					
						socket.close();
						//////저장한 사진 리사이징//////
						resizeImage(saveFolderPath,receiveFileName);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
	}

	private void resizeImage(String saveFolderPath,String receiveFileName) {
		//////폴더 없으면 폴더를 만드는 코드
		String resizeSaveFolderPath = saveFolderPath + "\\ImageResize\\";
		File resizeSaveFolder = new File(resizeSaveFolderPath);
		if(!resizeSaveFolder.exists()) {
			resizeSaveFolder.mkdir();
		}
		//////////끝//////////
		String filePath = saveFolderPath + receiveFileName;
		File resizeFile = new File(filePath);
		String tmpPath = resizeFile.getParent();
		String tmpFileName = resizeFile.getName();
		
		ImageIcon ic = ImageResize.resizeImage(filePath, 100, 100);
		if(ic != null) {
			Image i = ic.getImage();
			BufferedImage bi = new BufferedImage(i.getWidth(null), i.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = bi.createGraphics();
			g2.drawImage(i, 0, 0, null);
			g2.dispose();
			String newFileName = tmpFileName.replaceFirst(".png", "_resize.png");
			String newPath = tmpPath +"\\ImageResize\\"+newFileName;
			try {
				ImageIO.write(bi, "png", new File(newPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			String newPath = resizeSaveFolderPath;
			try {
				BufferedImage src = ImageIO.read(resizeFile);
				ImageIO.write(src,"png",new File(newPath));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}