package com.othello;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.puzzlegame.PuzzleEvent;

public class OthelloView extends JFrame {
	public JButton jbtns[][] = new JButton[8][8];
	public String jbtn_label[];
	
	OthelloView(){
		initDisplay();
	}
	
	public void initDisplay() {
		this.setLayout(new GridLayout(8, 8));
		for (int i = 0; i < jbtns.length; i++) {
			for (int j = 0; j < jbtns[0].length; j++) {
				JButton jbtn = new JButton();
				jbtns[i][j] = jbtn;				
				this.add(jbtn);
			}

		}
		this.setSize(600, 600);
		this.setVisible(true);
	}

}
