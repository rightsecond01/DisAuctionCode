package com.ch2;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import com.mommoo.flat.layout.linear.LinearLayout;
import com.mommoo.flat.layout.linear.Orientation;



public class Test extends JFrame{
	
	JPanel jp_first = new JPanel();
	JPanel jp_second = new JPanel();
	JPanel jp_display = new JPanel();

	StyledDocument sd_display = new DefaultStyledDocument(new StyleContext());
	JTextPane jtp_display = new JTextPane(sd_display);
	JScrollPane jsp_display = new JScrollPane(jtp_display
			                ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	JButton jbtn_whisper = new JButton("1:1대화");
	JButton jbtn_change = new JButton("대화명변경");
	JButton jbtn_icon = new JButton("이모티콘");
	JButton jbtn_exit = new JButton("종료");
	
	JPanel test = new JPanel();
	JButton jf = new JButton("메세지를 입력하세요 ");
	
	public Test() {
		initDisplay();
	}
	public void initDisplay() {
		jp_first.setPreferredSize(new Dimension(200,200));
		jp_second.setPreferredSize(new Dimension(200,200));
		
		GroupLayout gLayout = new GroupLayout(jp_first);
		jp_first.setLayout(gLayout);
		gLayout.setAutoCreateGaps(true);  
		gLayout.setAutoCreateContainerGaps(true);  
		
		gLayout.setHorizontalGroup(
			gLayout.createSequentialGroup()
			   .addGroup (gLayout.createParallelGroup (GroupLayout.Alignment.LEADING)
			       .addComponent(jbtn_whisper)			    
			       .addComponent(jbtn_change))			   
	    );
		gLayout.setVerticalGroup(
			gLayout.createSequentialGroup()			    
			    .addComponent(jbtn_whisper)
			    .addComponent(jbtn_change)
		);
		GroupLayout gLayout2 = new GroupLayout(jp_second);
		jp_second.setLayout(gLayout2);
		gLayout2.setAutoCreateGaps(true);  
		gLayout2.setAutoCreateContainerGaps(true);  
		
		gLayout2.setHorizontalGroup(
			gLayout2.createSequentialGroup()
			   .addGroup (gLayout2.createParallelGroup (GroupLayout.Alignment.LEADING)
			       .addComponent(jbtn_icon)			    
			       .addComponent(jbtn_exit))
	    );
		gLayout2.setVerticalGroup(
			gLayout2.createSequentialGroup()			 
			   .addComponent(jbtn_icon)
			   .addComponent(jbtn_exit)
		);
		
		
		jtp_display.setLayout(new LinearLayout(Orientation.VERTICAL, 15));
		jtp_display.add(jp_first);
		jtp_display.add(jp_second);
		jp_display.setLayout(new BorderLayout());
		jp_display.add(jsp_display);
		
		test.add(jf);
		jtp_display.insertComponent(test);
		
		
		this.add(jp_display);
		this.add("East", jbtn_change);
		this.setSize(1500,1500);
		this.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		Test test = new Test();	
	}

}
