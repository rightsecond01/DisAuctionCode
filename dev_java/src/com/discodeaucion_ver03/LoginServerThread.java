package com.discodeaucion_ver03;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.google.gson.Gson;

public class LoginServerThread extends Thread {
	ServerSocket loginServer = null;
	Socket loginClient = null;
	DACServer ds = null;

	ObjectOutputStream login_oos = null;
	ObjectInputStream login_ois = null;

	MemberDao mDao = new MemberDao();
	
	String profilePath = "C:\\workspace_java\\dev_java\\src\\com\\images\\profile\\";

	public LoginServerThread(DACServer ds) {
		this.ds = ds;
	}

	public String confirmLogin(String mem_id, String mem_pw) {
		String nickName = null;
		for (int i = 0; i < ds.online_userList.size(); i++) {// 온라인유저리스트에 지금 로그인하려는 유저정보가 있는지 확인
			if (mem_id.equals(ds.online_userList.get(i).user_id)) {
				nickName = "Already";
				return nickName;
			}
		}
		nickName = mDao.login(mem_id, mem_pw);
		return nickName;
	}

	public List<Map<String, Object>> getPreInRoomInfo(String mem_id) {
		List<Map<String, Object>> inRoomList = null;
		inRoomList = mDao.getPreInRoomInfo(mem_id);
		return inRoomList;
	}
	
	public Map<String, String> getMemberInfo(String mem_id){
		Map<String, String> mMap = null;
		mMap = mDao.getMemberInfo(mem_id);
		return mMap;
	}

	public Map<String, Integer> confirmIsOpenRoom(List<Map<String, Object>> infoList) {
		Map<String, Integer> p_current_Map = new HashMap<String, Integer>();
		for (int i = 0; i < infoList.size(); i++) {
			int p_current = 0;// 한바퀴 돌면 다시 0으로 초기화
			String token = infoList.get(i).get("token").toString();
			for (int j = 0; j < ds.activeRoomList.size(); j++) {
				if (token.equals(ds.activeRoomList.get(j).getRoomMap().get("token"))) {
					p_current = (int) ds.activeRoomList.get(j).getRoomMap().get("p_current");
				}
			}
			p_current_Map.put(token, p_current);
		}
		return p_current_Map;
	}
	public List<Map<String,String>> getFriendInfo(String mem_id) {
		List<Map<String,String>> friList = null;
		friList = mDao.getFriendInfo(mem_id);
		return friList;
	}
	
	public void sendProfileImg(String filePath) {
		DataOutputStream dos;
		try {
			dos = new DataOutputStream(loginClient.getOutputStream());
			File profileImg = new File(filePath);
			FileInputStream fis = new FileInputStream(profileImg);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			int len;
			byte[] data = new byte[1024];
			int sendData = 0;
			while((len=bis.read(data))!=-1) {
				sendData++;
				if(sendData % 10000 == 0) {
					System.out.println("전송중..." + sendData/10000);
				}
				dos.write(data,0,len);
			}
			dos.flush();
			dos.close();
			bis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
	public void run() {
		boolean isStop = false;
		try {
			loginServer = new ServerSocket(5000);

			while (!isStop) {
				loginClient = loginServer.accept();
				login_oos = new ObjectOutputStream(loginClient.getOutputStream());
				login_ois = new ObjectInputStream(loginClient.getInputStream());
				String msg = login_ois.readObject().toString();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				String mem_id = st.nextToken();
				String mem_pw = st.nextToken();

				String nickName = confirmLogin(mem_id, mem_pw);
				if ("Already".equals(nickName)) {// 이미 접속한 아이디일 경우 로그인 실패 띄우기
					login_oos.writeObject(Dprotocol.LOG_IN 
							            + Dprotocol.seperator + nickName);
				} 
				else if("실패".equals(nickName)) {//입력 정보가 틀릴경우
					login_oos.writeObject(Dprotocol.LOG_IN 
				            + Dprotocol.seperator + nickName);
				}
				else {
					Gson g = new Gson();
					List<Map<String, Object>> inRoomList = getPreInRoomInfo(mem_id);
					String roomInfo = g.toJson(inRoomList);

					Map<String, Integer> p_current_Map = confirmIsOpenRoom(inRoomList);
					String p_current = g.toJson(p_current_Map);
					Map<String, String> mMap = getMemberInfo(mem_id);
					String memberInfo = g.toJson(mMap);
					List<Map<String,String>> friList = getFriendInfo(mem_id);
					String friendInfo = g.toJson(friList);
					login_oos.writeObject(Dprotocol.LOG_IN 
							            + Dprotocol.seperator + nickName 
							            + Dprotocol.seperator + roomInfo 
							            + Dprotocol.seperator + p_current
							            + Dprotocol.seperator + memberInfo
							            + Dprotocol.seperator + friendInfo
							            );
					String profileName = mMap.get("mem_profile");
					String filePath = profilePath + profileName + ".png";
					sendProfileImg(filePath);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}