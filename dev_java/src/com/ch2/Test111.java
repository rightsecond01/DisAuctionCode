package com.ch2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.discodeaucion_ver03.TalkPanel;


public class Test111 extends JFrame implements ActionListener {
	
	StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	JScrollPane jsp_display = new JScrollPane(jtp_display
			                ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JPanel jp_display = new JPanel();
	
	String imgPath = "C:\\workspace_java\\dev_java\\src\\com\\images\\";
	
	public Test111() {
		this.setLayout(new BorderLayout());
		jp_display.add(jsp_display);
		
		this.add(jsp_display);
		this.setSize(800,800);
		this.setVisible(true);
		
		TalkPanel test = new TalkPanel("test","493a25d620",TalkPanel.AuctionRoom);
		String a = "류현진(LA 다저스)이 놀라운 활약을 펼치고 있는 LA 다저스는 올해 선발진에서는 메이저리그 최정상급이다. "
				+"다저스 관련 소식을 다루는 '트루 블루 LA'는 22일(이하 한국시간) 다저스 선발진의 QS와 이닝 소화력을 집중 조명했다. QS에서 류현진은 메이저리그 전체 공동 1위다."
                +"출처 : http://news.chosun.com/site/data/html_dir/2019/07/22/2019072200213.html";
		JPanel jPanel3 = test.textPanel(a+a+a,"test","무사");
		//jPanel3.setBorder(new TitledBorder(new LineBorder(Color.WHITE,5)));
		//insertComponent(jPanel);
		//insertComponent(jPanel2);
		//insertComponent(jPanel3);
		
		JPanel jPanel4 = test.imagePanel("C:\\roomtitle\\1563952379783.png","test","무사");
		Map<String,String> asdf = new HashMap<String, String>();
		asdf.put("room_game", "리니지");
		asdf.put("itemName", "진명황의 집행검");
		JPanel jPanel5 = test.tradePanel("test","무사",asdf);
		insertComponent(jPanel4);
		
	}
	
	
	private void insertComponent(JComponent comp) {
		  try {
		    jtp_display.getDocument().insertString(jtp_display.getDocument().getLength(), " ", null);
		    jtp_display.getDocument().insertString(jtp_display.getDocument().getLength(), "\n\n\n\n", null);
		  } catch (BadLocationException ex1) {
		    // Ignore
		  }
		  try {
		    jtp_display.setCaretPosition(jtp_display.getDocument().getLength() - 1);
		  } catch (Exception ex) {
		    jtp_display.setCaretPosition(0);
		  }
		  jtp_display.insertComponent(comp);
		}


	
	public static void main(String[] args) {
		new Test111();
	}

	@Override
	public void actionPerformed(ActionEvent e) {		

	}

}