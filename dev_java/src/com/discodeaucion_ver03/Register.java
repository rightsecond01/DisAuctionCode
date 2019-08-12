package com.discodeaucion_ver03;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.util.ImageResize;

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
	private JTextField txtImgURL;
	private JLabel imageLabel;
	private JLabel labelMyImg;

	
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JButton jbtn_imgSubmit = new JButton("제출");
	JButton jbtn_myImg = new JButton("업로드");
	JButton jbtn_save = new JButton("저장");
	JButton jbtn_cancle = new JButton("취소");
	JScrollPane jsp_line = new JScrollPane(jp_center);

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
		labelImg2 = new JLabel("이미지주소");
		labelMyImg = new JLabel("직접 파일");

		txtId = new JTextField(20);
		txtPw = new JTextField(20);
		txtPw_check = new JTextField(20);
		txtName = new JTextField(20);
		txtNickName = new JTextField(20);
		txtEmail = new JTextField(20);
		txtHP = new JTextField(20);
		txtBirth = new JTextField(20);
		txtImgURL = new JTextField(20);
		
		Font font_submit = new Font("HY견고딕", Font.PLAIN, 15);
		Font font_upload = new Font("돋움", Font.PLAIN, 11);
		jbtn_imgSubmit.setFont(font_submit);
		jbtn_imgSubmit.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		jbtn_imgSubmit.setBackground(Color.lightGray);
		jbtn_myImg.setFont(font_upload);
		jbtn_myImg.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		Font notice = new Font("돋움", Font.BOLD, 11);
		labelMyImg.setFont(notice);
		labelMyImg.setForeground(Color.BLUE);

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
		txtImgURL.setBounds(150, 255, 220, 20);
		labelMyImg.setBounds(150,280,60,20);
		jbtn_myImg.setBounds(210,277,45,25);
		jbtn_imgSubmit.setBounds(320, 277, 50, 35);
		imageLabel.setBounds(20, 270, 100, 100);

		txtImgURL.setText("입력하지 않을시, 기본사진으로 지정됩니다.");
		jp_center();

		
		////////////////// 버튼 이벤트 처리/////////////////////
		jbtn_imgSubmit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(txtImgURL.getText().equals("입력하지 않을시, 기본사진으로 지정됩니다.")) {
					JOptionPane.showMessageDialog(Register.this, "이미지 주소를 입력 후 제출해 주세요.");
					return;
				}
			
				jp_center.removeAll();				
				
				String imgURL = txtImgURL.getText();
				eventSetUrlImg(imgURL);

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
		
		jbtn_myImg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				eventSetMyImg();
			}
		});
		
		
		jp_south.add(jbtn_save);
		jp_south.add(jbtn_cancle);
		this.setLayout(new BorderLayout());
		this.add("Center", jp_center);
		this.add("South", jp_south);
		this.setLocation(200, 200);
		this.setSize(400, 600);
		this.setVisible(true);
		this.setResizable(false);
	}

	public void jp_center() {

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
		jp_center.add(txtImgURL);
		jp_center.add(jbtn_imgSubmit);
		jp_center.add(imageLabel);
		jp_center.add(labelMyImg);
		jp_center.add(jbtn_myImg);		
		
	}

	public static void main(String[] args) {
		Register rs = new Register();
		rs.initDisplay();
	}
	
	///////////////////////////저장하고, 리사이징하는 메소드///////////////////
	public void saveUrlImage(String imageUrl, File saveFile) {
		URL url = null;
		BufferedImage bi = null;

		try {
			url = new URL(imageUrl); // 다운로드 할 이미지 URL
			bi = ImageIO.read(url);
			ImageIO.write(bi, "png", saveFile); // 저장할 파일 형식, 저장할 파일명

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveMyImage(String filePath, File saveFile) {
		File myImgFile = new File(filePath);
		try {
			BufferedImage src = ImageIO.read(myImgFile);			
			
			ImageIO.write(src,"png",saveFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String resizeImage(String filePath) {
		String newPath = null;
		File resizeFile = new File(filePath);
		
		String tmpPath = resizeFile.getParent();
		String tmpFileName = resizeFile.getName();
		int extPosition = tmpFileName.indexOf("png");
		
		if(extPosition != -1){
			ImageIcon ic = ImageResize.resizeImage(filePath, 100, 100);
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
		return newPath;
	}

//////////////////////이벤트 처리 메소드///////////////////////////////
	
	public void eventSetUrlImg(String imgURL) {
		profileName = Long.valueOf(new Date().getTime()).toString();		
		String saveFileName = profileName + ".png";
		String filePath = profilePath + saveFileName;
		File saveFile = new File(filePath);
		saveUrlImage(imgURL, saveFile);
		// 내 드라이브에 사진 저장
		
		String resizePath = resizeImage(filePath);//리사이징후 저장
		File resizeFile = new File(resizePath);
		
		Image img = null;
		try {
			img = ImageIO.read(resizeFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageLabel.setIcon(new ImageIcon(img));
		
		jp_center();
		this.repaint();

	}

	
	public void eventSetMyImg() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG&GIF Images","jpg","gif","png");
		chooser.setFileFilter(filter);
		int ret=chooser.showOpenDialog(null);
		if(ret!=JFileChooser.APPROVE_OPTION){
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.","경고",JOptionPane.WARNING_MESSAGE);
			return;
		}
		String src=chooser.getSelectedFile().getPath();
		String saveFileName = Long.valueOf(new Date().getTime()).toString() + ".png";
		String filePath = profilePath + saveFileName;
		File saveFile = new File(filePath);
		saveMyImage(src, saveFile);
		
		String resizePath = resizeImage(filePath);
		File resize_chooseFile = new File(resizePath);
		
		Image img = null;
		try {
			img = ImageIO.read(resize_chooseFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		imageLabel.setIcon(new ImageIcon(img));
		jp_center();
		this.repaint();
		
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