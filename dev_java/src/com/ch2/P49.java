package com.ch2;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextPane;

public class P49 {

	public static void main(String[] args) {
		 final JTextPane pane = new JTextPane();

		    pane.replaceSelection("text");
		    pane.insertIcon(new ImageIcon("imageName.gif"));
		    pane.insertComponent(new JButton("Click Me"));

		    JFrame frame = new JFrame();
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    frame.getContentPane().add(pane, BorderLayout.CENTER);
		    frame.setSize(360, 180);
		    frame.setVisible(true);
	}

}
