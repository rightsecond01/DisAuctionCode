package com.network1;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

public class EmoticonMessage extends JDialog implements ActionListener {
	String imgPath = "C:\\workspace_java\\dev_java\\src\\com\\images\\";
	TalkClient tc = null;
	JPanel jp_emo = new JPanel();
	JButton jbtn_emo0 = new JButton();
	JButton jbtn_emo1 = new JButton();
	JButton jbtn_emo2 = new JButton();
	JButton jbtn_emo3 = new JButton();
	JButton jbtn_emo4 = new JButton();
	String imgFiles[] = { "icon11.gif", "icon22.gif", "icon33.gif", "icon44.gif", "icon55.gif" };
	JButton imgButton[] = { jbtn_emo0, jbtn_emo1, jbtn_emo2, jbtn_emo3, jbtn_emo4 };
	ImageIcon img[] = new ImageIcon[5];
	String imgChoice = "default";

	public EmoticonMessage() {

	}

	public EmoticonMessage(TalkClient tc) {
		this.tc = tc;
	}

	public void initDisplay() {
		jp_emo.setBackground(Color.white);
		jp_emo.setBorder(BorderFactory.createEtchedBorder());
		jp_emo.setBounds(new Rectangle(6, 6, 500, 150));
		jp_emo.setLayout(new GridLayout(1, 5));
		for (int i = 0; i < img.length; i++) {
			img[i] = new ImageIcon(imgPath + imgFiles[i]);
			imgButton[i].setIcon(img[i]);
			imgButton[i].setBorderPainted(false);
			imgButton[i].setFocusPainted(false);
			imgButton[i].setContentAreaFilled(false);
		}
		jp_emo.add(jbtn_emo0);
		jp_emo.add(jbtn_emo1);
		jp_emo.add(jbtn_emo2);
		jp_emo.add(jbtn_emo3);
		jp_emo.add(jbtn_emo4);

		jbtn_emo0.addActionListener(this);
		jbtn_emo1.addActionListener(this);
		jbtn_emo2.addActionListener(this);
		jbtn_emo3.addActionListener(this);
		jbtn_emo4.addActionListener(this);

		this.getContentPane().setBackground(new Color(125, 144, 177));
		this.getContentPane().add(jp_emo);

		this.add(jp_emo);
		this.setLocation(50, 50);
		this.setResizable(false);
		this.setSize(500, 205);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		EmoticonMessage em = new EmoticonMessage();
		em.initDisplay();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String writingText = tc.jtf_msg.getText();
		if (e.getSource() == jbtn_emo0) {
			imgChoice = "icon11.gif";
			if(writingText==null||writingText.length()==0) {				
				tc.message_process(null);				
			}
			else if(writingText!=null) {
				if(writingText.substring(0, 2).equals("/w")) {	
					tc.whisper_process(null);
				}else {
					tc.jtf_msg.setText("");
					tc.message_process(null);
				}
			}			
			this.setVisible(false);
		} 
		else if (e.getSource() == jbtn_emo1) {
			imgChoice = "icon22.gif";
			if(writingText==null||writingText.length()==0) {				
				tc.message_process(null);				
			}
			else if(writingText!=null) {
				if(writingText.substring(0, 2).equals("/w")) {	
					tc.whisper_process(null);
				}else {
					tc.jtf_msg.setText("");
					tc.message_process(null);
				}
			}			
			this.setVisible(false);
		} 
		else if (e.getSource() == jbtn_emo2) {
			imgChoice = "icon33.gif";
			if(writingText==null||writingText.length()==0) {				
				tc.message_process(null);				
			}
			else if(writingText!=null) {
				if(writingText.substring(0, 2).equals("/w")) {	
					tc.whisper_process(null);
				}else {
					tc.jtf_msg.setText("");
					tc.message_process(null);
				}
			}			
			this.setVisible(false);

		} 
		else if (e.getSource() == jbtn_emo3) {
			imgChoice = "icon44.gif";
			if(writingText==null||writingText.length()==0) {				
				tc.message_process(null);				
			}
			else if(writingText!=null) {
				if(writingText.substring(0, 2).equals("/w")) {	
					tc.whisper_process(null);
				}else {
					tc.jtf_msg.setText("");
					tc.message_process(null);
				}
			}			
			this.setVisible(false);

		} 
		else if (e.getSource() == jbtn_emo4) {
			imgChoice = "icon55.gif";
			if(writingText==null||writingText.length()==0) {				
				tc.message_process(null);				
			}
			else if(writingText!=null) {
				if(writingText.substring(0, 2).equals("/w")) {	
					tc.whisper_process(null);
				}else {
					tc.jtf_msg.setText("");
					tc.message_process(null);
				}
			}			
			this.setVisible(false);

		}

	}

}
