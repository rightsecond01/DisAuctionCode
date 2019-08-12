package com.discodeaucion_ver03;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImagesTransmission extends Thread {
	List<Map<String, String>> inRoomUserList;
	String profilePath = "C:\\workspace_java\\dev_java\\src\\com\\images\\profile\\";
	Socket receiver = null;

	public ImagesTransmission(List<Map<String, String>> inRoomUserList) {
		this.inRoomUserList = inRoomUserList;
	}

	@Override
	public void run() {
		MemberDao mDao = new MemberDao();
		Map<String, File> sendfiles = new HashMap<String, File>();
		ServerSocket imgSendServer = null;
		File[] files = new File(profilePath).listFiles();

		/////////////// 아이디-프로필사진파일 맵핑하는 코드///////////////
		for (int i = 0; i < inRoomUserList.size(); i++) {
			String mem_id = inRoomUserList.get(i).get("mem_id");
			String imgfileName = mDao.getMemberInfo(mem_id).get("mem_profile");
			for (int j = 0; j < files.length; j++) {
				int end_fileName = files[j].getName().indexOf(".");
				String serverFileName = null;
				if (end_fileName != -1) {
					serverFileName = (files[j].getName()).substring(0, end_fileName);// .png를 제거한 순수 파일 이름
					if (serverFileName.equals(imgfileName)) {
						sendfiles.put(mem_id, files[j]);
					}
				}
			}
		}
		///////////////////////// 끝///////////////////////////
		try {
			imgSendServer = new ServerSocket(60000);
			for (int i = 0; i < inRoomUserList.size(); i++) {

				try {
					receiver = imgSendServer.accept();
					String mem_id = inRoomUserList.get(i).get("mem_id");
					BufferedOutputStream bos = new BufferedOutputStream(receiver.getOutputStream());
					DataOutputStream dos = new DataOutputStream(bos);
					File file = sendfiles.get(mem_id);
					dos.writeUTF(mem_id + "_Profile.png");
					FileInputStream fis = new FileInputStream(file);
					BufferedInputStream bis = new BufferedInputStream(fis);
					byte[] buf = new byte[4096];
					int theByte = 0;
					while ((theByte = bis.read(buf)) != -1) {
						bos.write(buf, 0, theByte);
					}
					dos.flush();
					dos.close();
					bos.close();
					receiver.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			imgSendServer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
