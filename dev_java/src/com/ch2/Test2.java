package com.ch2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;
import com.mommoo.flat.layout.linear.constraints.LinearConstraints;
import com.mommoo.flat.layout.linear.constraints.LinearSpace;

public class Test2 extends JFrame{
	
	JPanel textPanel = new JPanel();
	JPanel jp_img = new JPanel();
	JPanel jp_others = new JPanel();
	JPanel jp_others_info = new JPanel();
	JPanel jp_others_info_nick = new JPanel();
	JPanel jp_others_info_time = new JPanel();
	JPanel jp_others_talk = new JPanel();
	
	int width = 800;
	int height = 900;
	int others_width = 600-15-100;
	int others_height = 300;
	String changeLine = "\n";
	public void initDisplay() {
		textPanel.setLayout(new LinearLayout(15));
		jp_img.setLayout(new BorderLayout());//좌
		jp_img.setPreferredSize(new Dimension(100,100));
		jp_img.add("Center", new JTextArea());
		
		jp_others.setLayout(new FlowLayout());//우
		jp_others_info.setLayout(new LinearLayout(15));
		jp_others_info.setPreferredSize(new Dimension(others_width,20));
		jp_others_info_nick.setLayout(new BorderLayout());		
		jp_others_info_time.setLayout(new BorderLayout());
		jp_others_talk.setLayout(new BorderLayout());
		jp_others_info_nick.add("Center", new JTextArea());
		jp_others_info_time.add("Center", new JTextArea());
		jp_others_info.add(jp_others_info_nick,new LinearConstraints().setWeight(5).setLinearSpace(LinearSpace.MATCH_PARENT));
		jp_others_info.add(jp_others_info_time,new LinearConstraints().setWeight(2).setLinearSpace(LinearSpace.MATCH_PARENT));
		JTextArea testArea = new JTextArea();
		String msg = "류현진(LA 다저스)이 놀라운 활약을 펼치고 있는 LA 다저스는 올해 선발진에서는 메이저리그 최정상급이다. "
				+"다저스 관련 소식을 다루는 '트루 블루 LA'는 22일(이하 한국시간) 다저스 선발진의 QS와 이닝 소화력을 집중 조명했다. QS에서 류현진은 메이저리그 전체 공동 1위다."
                +"출처 : http://news.chosun.com/site/data/html_dir/2019/07/22/2019072200213.html";
		int count = countChangeLine(msg);
		testArea.append(msg);
		testArea.setLineWrap(true);
		JScrollPane jsp_test = new JScrollPane(testArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp_test.setPreferredSize(new Dimension(others_width,16*count));
		others_height = 20 * count;
		
		jp_others.setPreferredSize(new Dimension(others_width,others_height));
		jp_others_talk.add("Center", jsp_test);
		jp_others.add(jp_others_info);
		jp_others.add(jp_others_talk);
		
		textPanel.add(jp_img, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		textPanel.add(jp_others, new LinearConstraints().setLinearSpace(LinearSpace.WRAP_CONTENT));
		
		this.add(textPanel);
		this.setSize(width,height);
		this.setVisible(true);
	}
	public int countChangeLine(String msg) {
		int count = 0;
		int length = msg.length();
		count = length/25;
		return count;
	}
	
	public static void main(String[] args) {
		Test2 t2 = new Test2();
		t2.initDisplay();
	}

}
