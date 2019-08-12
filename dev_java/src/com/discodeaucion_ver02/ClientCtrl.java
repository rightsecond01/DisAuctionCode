package com.discodeaucion_ver02;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.util.ImageResize;

public class ClientCtrl extends JFrame implements Runnable {

	JTabbedPane tp = new JTabbedPane();
	MainView mv = null;

	Container con = this.getContentPane();

	Socket mainSocket = null;
	List<Socket> rSocketList = null;

	ObjectOutputStream main_oos = null;
	ObjectInputStream main_ois = null;
	
	Map<String, String> userInfoMap = new HashMap<String, String>();
	String user_id = null;
	String nickName = null;

	////////// 방생성시 사용 변수///////////
	String[] s_roomType = { "AuctionRoom", "VoiceTalkRoom" };

	////// 접속중인(토큰을 입력한) 방관리/////
	List<Map<String, Object>> acceptRoomList = new Vector<Map<String, Object>>();

	String ip = "127.0.0.1";
	
	String temp_directory = "C:\\chat_temp\\" ;
	String r_profileImgPath = null;

	public ClientCtrl(String user_id,String roomInfo, String p_current_Json,String user_Info) {
		this.user_id = user_id;
		this.r_profileImgPath = resizeImage();
		initDisplay();
		setPreInfo(roomInfo, p_current_Json);
		setUserProfile(user_Info);
		nickName = userInfoMap.get("mem_nick");
		connect_process();

	}


	public void setPreInfo(String roomInfo, String p_current_Json) {
		JsonParser parser = new JsonParser();
		JsonElement el_p_current = parser.parse(p_current_Json);

		JsonElement el_roomInfo = parser.parse(roomInfo);
		JsonArray infoArray = el_roomInfo.getAsJsonArray();
		for (int i = 0; i < infoArray.size(); i++) {
			String room_title = infoArray.get(i).getAsJsonObject().get("room_title").getAsString();
			String room_game = infoArray.get(i).getAsJsonObject().get("room_game").getAsString();
			int p_limit = infoArray.get(i).getAsJsonObject().get("p_limit").getAsInt();
			int room_type = infoArray.get(i).getAsJsonObject().get("room_type").getAsInt();
			String token = infoArray.get(i).getAsJsonObject().get("token").getAsString();

			// token으로 현재인원을 뽑아내는 코드
			int p_current = el_p_current.getAsJsonObject().get(token).getAsInt();

			Vector<String> v = new Vector<>();
			v.add(s_roomType[room_type] + " || " + room_game);
			v.add(room_title);
			v.add(p_current + "/" + p_limit);
			v.add(token);
			mv.dtm_roomList.addRow(v);
		}

	}
	public void setUserProfile(String user_Info) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(user_Info);
		
		userInfoMap = new HashMap<String, String>();
		
		String mem_name = element.getAsJsonObject().get("mem_name").getAsString();
		String mem_nick = element.getAsJsonObject().get("mem_nick").getAsString();
		String mem_email = element.getAsJsonObject().get("mem_email").getAsString();
		String mem_hp = element.getAsJsonObject().get("mem_hp").getAsString();
		String mem_birth = element.getAsJsonObject().get("mem_birth").getAsString();
		String reg_date = element.getAsJsonObject().get("reg_date").getAsString();
		String mem_profile = element.getAsJsonObject().get("mem_profile").getAsString();
		
		userInfoMap.put("mem_name", mem_name);
		userInfoMap.put("mem_nick", mem_nick);
		userInfoMap.put("mem_email", mem_email);
		userInfoMap.put("mem_hp", mem_hp);
		userInfoMap.put("mem_birth", mem_birth);
		userInfoMap.put("reg_date", reg_date);
		userInfoMap.put("mem_profile", mem_profile);
	}
	
	public String resizeImage() {
		String newPath = null;
		String myFilePath = temp_directory+ user_id;
		//경로없으면 경로 만드는 코드
		String resizeSaveFolderPath = myFilePath + "\\ImageResize\\";
		File resizeSaveFolder = new File(resizeSaveFolderPath);
		
		if(!resizeSaveFolder.exists()) {
			resizeSaveFolder.mkdir();
		}
		
		/////////////////끝
		
		String filePath = myFilePath + "\\myProfileImg.png";
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
			newPath = tmpPath +"\\ImageResize\\"+newFileName;
			try {
				ImageIO.write(bi, "png", new File(newPath));
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		else {
			newPath = temp_directory+ user_id+ "\\ImageResize\\" + userInfoMap.get("mem_profile") + ".png";
			
			try {
				BufferedImage src = ImageIO.read(resizeFile);
				ImageIO.write(src,"png",new File(newPath));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return newPath;
	}

	public void initDisplay() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(ClientCtrl.this, "이 버튼으로는 종료할 수 없습니다.");
				return;
			}
		});
		mv = new MainView(this);
		con.setLayout(null);
		tp.setBounds(5, 4, 920, 900);
		tp.addTab("메인화면", mv);
		con.add(tp, null);
		this.setSize(950, 950);
		this.setVisible(true);
	}

	public void connect_process() {
		this.setTitle("디스옥션코드");
		int port = 3000;
		try {
			mainSocket = new Socket(ip, port);
			main_oos = new ObjectOutputStream(mainSocket.getOutputStream());
			main_ois = new ObjectInputStream(mainSocket.getInputStream());
			main_oos.writeObject(Dprotocol.LOG_IN + Dprotocol.seperator + user_id + Dprotocol.seperator + nickName);
			rSocketList = new Vector<Socket>();

			ClientCtrlThread cct = new ClientCtrlThread(this);
			cct.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		rSocketList = new Vector<Socket>();

		boolean isStop = false;
		try {
			while (!isStop) {
				String msg = (String) main_ois.readObject();
				StringTokenizer st = null;
				int protocol = 0;
				if (msg != null) {
					st = new StringTokenizer(msg, Dprotocol.seperator);
					protocol = Integer.parseInt(st.nextToken());
				}
				switch (protocol) {

				}
			}

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

	}

}
