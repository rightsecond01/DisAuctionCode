package com.network23;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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
	String nickName = null;
	TalkClientVer2 tc2 = null;
	
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
		jlb_id.setBounds(25,200,100,40);
		jtf_id.setBounds(150,200,155,40);
		jlb_id.setFont(font);
		
		jlb_pw.setBounds(25,240,120,40);
		jpf_pw.setBounds(150,240,155,40);
		jlb_pw.setFont(font);
		
		jbtn_login.setBounds(175,285,120,40);
		jbtn_join.setBounds(45,285,120,40);
		
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
		MemberDao cDao = new MemberDao();
		nickName = cDao.login(mem_id, mem_pw);
		if("실패".equals(nickName)) {
			JOptionPane.showMessageDialog(this, "아이디와 비밀번호가 일치하지 않습니다.");
			return;
		}
		else {
			JOptionPane.showMessageDialog(this, nickName + "님, 환영합니다.");
			this.setVisible(false);
			jtf_id.setText("");
			jpf_pw.setText("");
			tc2 = new TalkClientVer2(this);
			
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
