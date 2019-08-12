package com.puzzlegame;

import javax.swing.JButton;

public class PuzzleLogic {

	JButton[][] jbtns = new JButton[3][3];
	String[] labels = new String[9];	

	public PuzzleLogic(JButton[][] jbtns, String[] labels) {
		this.jbtns = jbtns;
		this.labels = labels;
	}

	public void swap(int selectedRowNum, int selectedColumnNum) {
		/*
		 * selectedRowNum = 선택한 버튼 (2차원 배열의) 행 
		 * selectedColumnNum = 선택한 버튼 (2차원 배열에서의) 열
		 */
		if ((selectedRowNum - 1 >= 0) && (labels[(selectedRowNum - 1) * 3 + selectedColumnNum].equals(""))) {// 위로 이동 조건

			int closedRowNum = selectedRowNum - 1;// 행을 한칸 위로 이동
			int closedColumnNum = selectedColumnNum;// 열은 그대로
			swapLogic(selectedRowNum, selectedColumnNum, closedRowNum, closedColumnNum);

		} else if ((selectedRowNum + 1 <= 2) && (labels[(selectedRowNum + 1) * 3 + selectedColumnNum].equals(""))) {// 아래로
																													// 이동조건

			int closedRowNum = selectedRowNum + 1;// 행을 한칸 아래로 이동
			int closedColumnNum = selectedColumnNum;// 열은 그대로
			swapLogic(selectedRowNum, selectedColumnNum, closedRowNum, closedColumnNum);

		} else if ((selectedColumnNum - 1 >= 0) && (labels[selectedRowNum * 3 + (selectedColumnNum - 1)].equals(""))) {// 왼쪽
																														// 이동조건

			int closedRowNum = selectedRowNum;// 행은 그대로
			int closedColumnNum = selectedColumnNum - 1;// 열을 왼쪽으로 한칸 이동
			swapLogic(selectedRowNum, selectedColumnNum, closedRowNum, closedColumnNum);

		} else if ((selectedColumnNum + 1 <= 2) && (labels[selectedRowNum * 3 + (selectedColumnNum + 1)].equals(""))) {// 오른쪽
																														// 이동조건

			int closedRowNum = selectedRowNum;// 행은 그대로
			int closedColumnNum = selectedColumnNum + 1;// 열을 오른쪽으로 한칸 이동
			swapLogic(selectedRowNum, selectedColumnNum, closedRowNum, closedColumnNum);
		}

	}

	public void swapLogic(int selectedRowNum, int selectedColumnNum, int closedRowNum, int closedColumnNum) {
		jbtns[closedRowNum][closedColumnNum].setText(labels[(selectedRowNum * 3) + selectedColumnNum]);
		jbtns[selectedRowNum][selectedColumnNum].setText("");

		String temp = "";
		labels[(closedRowNum * 3) + closedColumnNum] = labels[(selectedRowNum * 3) + selectedColumnNum];
		labels[(selectedRowNum * 3) + selectedColumnNum] = temp;

	}

	public int answerLogic() {
		String[] answer = {"1", "2", "3", "4", "5", "6", "7", "8", ""};
		int count = 0;
		for (int i = 0; i < answer.length; i++) {
			if (answer[i].equals(labels[i])) {
				count++;
			}
		}
		return count;
	}

}
