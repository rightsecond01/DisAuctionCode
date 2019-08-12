package com.discodeaucion_ver03;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





//개선방향 : cc참조
public class Login extends JFrame implements ActionListener {
	String imgPath = "C:\\workspace_java\\dev_java\\src\\com\\images\\";
	ImageIcon ig = new ImageIcon(imgPath + "main.png");
	Font font = new Font("HY목각파임B",Font.BOLD,25);
	JLabel jlb_id = new JLabel("아이디");
	JTextField jtf_id = new JTextField("test");
	JLabel jlb_pw = new JLabel("패스워드");
	JPasswordField jpf_pw = new JPasswordField("123");
	JButton jbtn_login = new JButton(new ImageIcon(imgPath+"login.png"));
	JButton jbtn_join = new JButton(new ImageIcon(imgPath+"confirm.png"));
	
	
	String ip = "192.168.0.9";
	int port = 5000;
	Socket loginSocket = null;
	ObjectOutputStream login_oos = null;
	ObjectInputStream login_ois = null;
	
	String temp_directory = "C:\\chat_temp\\";
	
	class MyPanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.drawImage(ig.getImage(), 0, 0,null);
			setOpaque(false);
			super.paintComponent(g);
			
		}
	}
	
	
	
	public void initDisplay() {
		setContentPane(new MyPanel());
		jbtn_join.addActionListener(this);
		jbtn_login.addActionListener(this);
		
		this.setLayout(null);
		jlb_id.setBounds(25,270,100,40);
		jtf_id.setBounds(150,270,155,40);
		jlb_id.setFont(font);
		
		jlb_pw.setBounds(25,310,120,40);
		jpf_pw.setBounds(150,310,155,40);
		jlb_pw.setFont(font);
		
		jbtn_login.setBounds(185,365,120,40);
		jbtn_join.setBounds(35,365,135,40);
		
		this.add(jbtn_login);
		this.add(jbtn_join);
		this.add(jlb_id);
		this.add(jtf_id);
		this.add(jlb_pw);
		this.add(jpf_pw);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(350,600);
		this.setVisible(true);
		this.setLocation(800, 250);
	}
	
	public static void main(String[] args) {
		Login login = new Login();
		login.initDisplay();
		
	}
	
	public String requestServer(String mem_id, String mem_pw) {
		String msg = null;
		try {
			loginSocket = new Socket(ip,port);
			login_oos = new ObjectOutputStream(loginSocket.getOutputStream());
			login_ois = new ObjectInputStream(loginSocket.getInputStream());
			login_oos.writeObject(Dprotocol.LOG_IN
					             +Dprotocol.seperator+mem_id
					             +Dprotocol.seperator+mem_pw
					            );
			msg = login_ois.readObject().toString();
			return msg;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return msg;
	}	
	
	public void receiveImgFile(String mem_id) {
		
		String saveFolderPath = temp_directory + mem_id + "\\";
		File saveFolder = new File(saveFolderPath);
		if(!saveFolder.exists()) {
			saveFolder.mkdir();
		}
	
		String filePath = saveFolderPath + "myProfileImg.png";
		File profileImg = new File(filePath);
		try {
			DataInputStream dis = new DataInputStream(loginSocket.getInputStream());
			
			FileOutputStream fos = new FileOutputStream(profileImg);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			int len;
			byte[] data = new byte[1024];
			while((len = dis.read(data)) != -1) {			
				bos.write(data,0,len);
				bos.flush();
			}
			bos.close();
			fos.close();
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void login() {
		String mem_id = jtf_id.getText();
		String mem_pw = jpf_pw.getText();
		if(mem_id==null || mem_id.length()==0) {
			JOptionPane.showMessageDialog(this, "아이디를 입력하세요.");
			return;
		}
		else if(mem_pw==null || mem_pw.length()==0) {
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.");
			return;
		}
		
		String msg = requestServer(mem_id, mem_pw);
	
		StringTokenizer st = null;
		int protocol = 0;
		if (msg != null) {
			st = new StringTokenizer(msg, Dprotocol.seperator);
			protocol = Integer.parseInt(st.nextToken());
		}
		String nickName = st.nextToken();
		if("Already".equals(nickName)) {
			JOptionPane.showMessageDialog(this, "이미 접속한 유저입니다.");
			return;
		}
		
		if("실패".equals(nickName)) {
			JOptionPane.showMessageDialog(this, "아이디와 비밀번호가 일치하지 않습니다.");
			return;
		}
		else {
			receiveImgFile(mem_id);
			String roomInfo = st.nextToken();
			String p_current_Json = st.nextToken();
			String user_Info = st.nextToken();
			String friendInfo = st.nextToken();
			JOptionPane.showMessageDialog(this, nickName + "님, 환영합니다.");
			String user_id=mem_id;
			this.setVisible(false);
			jtf_id.setText("");
			jpf_pw.setText("");
			ClientCtrl cc = new ClientCtrl(user_id,roomInfo,p_current_Json,user_Info,friendInfo);
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj==jbtn_login) {
			login();
		}else if(obj==jbtn_join) {
			Register reg = new Register();
			reg.initDisplay();
		}
	}
	
	

}