package com.puzzlegame;

import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PuzzleView extends JFrame {
	public JButton jbtns[][] = new JButton[3][3];
	public String jbtn_label[];

	public PuzzleView() {
		shuffle();
		initDisplay();

	}

	public JButton[][] getJbtns() {
		return jbtns;
	}

	public String[] getJbtn_label() {
		return jbtn_label;
	}

	public void initDisplay() {
		this.setLayout(new GridLayout(3, 3));

		PuzzleEvent pe = new PuzzleEvent(this);

		for (int i = 0; i < jbtns.length; i++) {
			for (int j = 0; j < jbtns[0].length; j++) {
				JButton jbtn = new JButton(jbtn_label[i * 3 + j]);
				jbtns[i][j] = jbtn;
				jbtns[i][j].addActionListener(pe);
				this.add(jbtn);
			}

		}
		this.setSize(500, 400);
		this.setVisible(true);
	}

	public void shuffle() {
		jbtn_label = new String[jbtns.length * jbtns[0].length];// 행의갯수 x (0행의)열의갯수
		for (int i = 0; i < 8; i++) {
			Random random = new Random();
			int randomnumber = random.nextInt(8) + 1;
			jbtn_label[i] = randomnumber + "";
			for (int j = 0; j < i; j++) {
				if (jbtn_label[j].equals(jbtn_label[i])) {
					i--;
					break;
				}
			}
		}
		jbtn_label[8] = "";
	}
}
