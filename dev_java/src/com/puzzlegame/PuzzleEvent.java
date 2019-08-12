package com.puzzlegame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class PuzzleEvent implements ActionListener {
	PuzzleView puzzleView;

	public PuzzleEvent(PuzzleView pv) {
		this.puzzleView = pv;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton[][] jbtns = new JButton[3][3];
		String[] labels = new String[9];
		jbtns = puzzleView.getJbtns();// ((0,0)~(2,2)번까지의 버튼정보)
		labels = puzzleView.getJbtn_label();// (0~8번까지의 버튼 라벨정보)

		PuzzleLogic pl = new PuzzleLogic(jbtns, labels);
		// 주석아래 for문 수정요망
		for (int selectedBtnRowNum = 0; selectedBtnRowNum < jbtns.length; selectedBtnRowNum++) {
			for (int selectedBtnColumnNum = 0; selectedBtnColumnNum < jbtns.length; selectedBtnColumnNum++) {
				if (e.getSource() == puzzleView.getJbtns()[selectedBtnRowNum][selectedBtnColumnNum]) {
					pl.swap(selectedBtnRowNum, selectedBtnColumnNum);
					btnEnabled(pl.answerLogic() == 9);
				}
			}

		}
	}

	public void btnEnabled(boolean answer) {
		if (answer) {
			for (int i = 0; i < puzzleView.getJbtns().length; i++) {
				for (int j = 0; j < puzzleView.getJbtns().length; j++)
					puzzleView.getJbtns()[i][j].setEnabled(false);
			}
			puzzleView.getJbtns()[2][2].setEnabled(true);

		}
	}
}