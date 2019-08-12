package com.network23;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.address.AddressBook;
import com.address.AddressVO;

public class Register extends JFrame {
	String profilePath = "C:\\workspace_java\\dev_java\\src\\com\\images\\profile\\";

	private JLabel labelId;
	private JTextField txtId;
	private JLabel labelPw;
	private JTextField txtPw;
	private JLabel labelPw_check;
	private JTextField txtPw_check;
	private JLabel labelName;
	private JTextField txtName;
	private JLabel labelNickName;
	private JTextField txtNickName;
	private JLabel labelEmail;
	private JTextField txtEmail;
	private JLabel labelHP;
	private JTextField txtHP;
	private JLabel labelBirth;
	private JTextField txtBirth;
	private JLabel labelImg;
	private JLabel labelImg2;
	private JLabel labelImg3;
	private JTextField txtImgURL;
	private JPanel ImagePanel;

	private JLabel imageLabel;

	Font font = new Font("돋움체", Font.PLAIN, 16);
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JButton jbtn_imgSubmit = new JButton("제출");
	JButton jbtn_save = new JButton("저장");
	JButton jbtn_cancle = new JButton("취소");

	JScrollPane jsp_line = new JScrollPane(jp_center);

	Container con = this.getContentPane();
	Image img = null;
	String profileName = null;
	
	public Register() {
		profileName = profilePath + "person.png";
		File defaultProfile = new File(profileName);
		try {
			img = ImageIO.read(defaultProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageLabel = new JLabel(new ImageIcon(img));
	}
	

	public void initDisplay() {
		

		this.setTitle("회원가입");
		jp_center.setLayout(null);
		labelId = new JLabel("아이디(필수입력) ");
		labelPw = new JLabel("패스워드(필수입력)");
		labelPw_check = new JLabel("패스워드확인");
		labelName = new JLabel("이름(필수입력) ");
		labelNickName = new JLabel("닉네임 ");
		labelEmail = new JLabel("이메일 ");
		labelHP = new JLabel("전화번호 ");
		labelBirth = new JLabel("생일(YYYYMMDD) ");
		labelImg = new JLabel("프로필 사진");
		labelImg2 = new JLabel("이미지주소(외부URL)");
		labelImg3 = new JLabel("(100x100사이즈)");
		
		txtId = new JTextField(20);
		txtPw = new JTextField(20);
		txtPw_check = new JTextField(20);
		txtName = new JTextField(20);
		txtNickName = new JTextField(20);
		txtEmail = new JTextField(20);
		txtHP = new JTextField(20);
		txtBirth = new JTextField(20);
		txtImgURL = new JTextField(20);
		
		txtImgURL.setText("입력하지 않을시, 기본사진으로 지정됩니다.");
		jp_center();
		
		jp_south.add(jbtn_save);
		jp_south.add(jbtn_cancle);
		
		//////////////////버튼 이벤트 처리/////////////////////
		jbtn_imgSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				jp_center.removeAll();
				String imgURL = txtImgURL.getText();
				profileName = Long.valueOf(new Date().getTime()).toString();
				
				eventProfile(imgURL,profileName);

			}
		});
		jbtn_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventSave();
			}
		});
		jbtn_cancle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventCancle();

			}
		});

		this.setLayout(new BorderLayout());
		this.add("Center", jp_center);
		this.add("South", jp_south);
		this.setLocation(200, 200);
		this.setSize(400, 600);
		this.setVisible(true);
		this.setResizable(false);
	}

	public void jp_center() {
		labelId.setBounds(20, 20, 120, 20);
		txtId.setBounds(150, 20, 150, 20);
		labelPw.setBounds(20, 45, 120, 20);
		txtPw.setBounds(150, 45, 150, 20);
		labelPw_check.setBounds(20, 70, 100, 20);
		txtPw_check.setBounds(150, 70, 150, 20);
		labelName.setBounds(20, 95, 100, 20);
		txtName.setBounds(150, 95, 150, 20);
		labelNickName.setBounds(20, 120, 100, 20);
		txtNickName.setBounds(150, 120, 150, 20);
		labelEmail.setBounds(20, 145, 100, 20);
		txtEmail.setBounds(150, 145, 150, 20);
		labelHP.setBounds(20, 170, 100, 20);
		txtHP.setBounds(150, 170, 150, 20);
		labelBirth.setBounds(20, 195, 120, 20);
		txtBirth.setBounds(150, 195, 150, 20);
		labelImg.setBounds(20, 230, 120, 20);
		labelImg2.setBounds(150, 230, 150, 20);
		labelImg3.setBounds(20, 245, 150, 20);
		txtImgURL.setBounds(150, 255, 220, 20);
		jbtn_imgSubmit.setBounds(150, 275, 60, 40);
		imageLabel.setBounds(20, 270, 100, 100);

		jp_center.add(labelId);
		jp_center.add(txtId);
		jp_center.add(labelPw);
		jp_center.add(txtPw);
		jp_center.add(labelPw_check);
		jp_center.add(txtPw_check);
		jp_center.add(labelName);
		jp_center.add(txtName);
		jp_center.add(labelNickName);
		jp_center.add(txtNickName);
		jp_center.add(labelEmail);
		jp_center.add(txtEmail);
		jp_center.add(labelHP);
		jp_center.add(txtHP);
		jp_center.add(labelBirth);
		jp_center.add(txtBirth);
		jp_center.add(labelImg);
		jp_center.add(labelImg2);
		jp_center.add(labelImg3);
		jp_center.add(txtImgURL);
		jp_center.add(jbtn_imgSubmit);
		jp_center.add(imageLabel);		
	}

	public static void main(String[] args) {
		Register rs = new Register();
		rs.initDisplay();
	}

	public void eventProfile(String imgURL, String fileName) {
		
		String saveFileName = fileName + ".png";
		String fileFormat = "png";
		String filePath = profilePath + saveFileName;
		File saveFile = new File(filePath);
		saveImage(imgURL, saveFile, fileFormat);//내 드라이브에 사진 저장
		Image img = null;
		try {
			img = ImageIO.read(saveFile);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		imageLabel = new JLabel(new ImageIcon(img));
		jp_center();
		this.repaint();

	}

	public void saveImage(String imageUrl, File saveFile, String fileFormat) {
		URL url = null;
		BufferedImage bi = null;

		try {
			url = new URL(imageUrl); // 다운로드 할 이미지 URL
			bi = ImageIO.read(url);
			ImageIO.write(bi, fileFormat, saveFile); // 저장할 파일 형식, 저장할 파일명

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void eventSave() {
		if (!txtPw.getText().equals(txtPw_check.getText())) {
			JOptionPane.showMessageDialog(this, "두 비밀번호가 일치하지 않습니다.");
			return;
		}		

		ChatMemberVO cmVO = new ChatMemberVO();
		cmVO.setMem_id(txtId.getText());
		cmVO.setMem_pw(txtPw.getText());
		cmVO.setMem_name(txtName.getText());
		cmVO.setMem_nick(txtNickName.getText());
		cmVO.setMem_email(txtEmail.getText());
		cmVO.setMem_hp(txtHP.getText());
		cmVO.setMem_birth(txtBirth.getText());
		cmVO.setMem_profile(profileName);

		MemberDao mDao = new MemberDao();
		int result = mDao.insertMemver(cmVO);

		if (result == 2) {
			JOptionPane.showMessageDialog(this, "같은 아이디가 존재합니다");
			return;
		} else if (result == 1) {
			JOptionPane.showMessageDialog(this, "성공적으로 가입되었습니다.");
			this.dispose();
		} else if (result == 0) {
			JOptionPane.showMessageDialog(this, "가입에 실패하였습니다.");
			return;
		}

	}

	public void eventCancle() {
		this.dispose();
	}

}
