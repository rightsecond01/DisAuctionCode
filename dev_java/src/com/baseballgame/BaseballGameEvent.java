package com.baseballgame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class BaseballGameEvent extends AbstractAction implements ActionListener {
	BaseballGameView baseballGameView;
	int count = 0;
	
	

	public BaseballGameEvent(BaseballGameView bgv) {
		this.baseballGameView = bgv;
	}

	@Override
	public void actionPerformed(ActionEvent ae) { // 각 버튼 클릭시 이벤트 내용
		int[] answer = baseballGameView.getAnswer();	
		
		

		if (ae.getSource() == baseballGameView.getWritingAnswer()) {
			// 여기에 JTextfield에서 엔터시 userEnterAnswer[] 초기화 후 BaseballGameLogic아래
			// strikeBall() 메소드 실행.			
			String userEnterAnswer = baseballGameView.getWritingAnswer().getText();		
			for(int i=0;i<userEnterAnswer.length()-1;i++) {
				for(int j=i+1;j<userEnterAnswer.length();j++) {
					if(userEnterAnswer.substring(i,i+1).equals(userEnterAnswer.substring(j,j+1))) {
						JOptionPane.showMessageDialog(baseballGameView, "중복된 숫자는 입력 불가합니다.");
						return;
					}
				}
			}//증복된 숫자 제출 방지.			
			
			if (userEnterAnswer.length() < 3) {
				if (baseballGameView.getAnswer() == null) {
					JOptionPane.showMessageDialog(baseballGameView, "게임을 시작하시려면 '새게임버튼'을 눌러주세요");
					return;
				}
				JOptionPane.showMessageDialog(baseballGameView, "3자리 숫자를 입력해주세요");
				return;
			}
			if (baseballGameView.getAnswer() == null) {
				JOptionPane.showMessageDialog(baseballGameView, "게임을 시작하시려면 '새게임버튼'을 눌러주세요");
				return;
			}
			try {
				int userEnterNumber = Integer.parseInt(userEnterAnswer);
				JTextArea intentJTextArea = baseballGameView.getContent();
				JTextField intentJTextField = baseballGameView.getWritingAnswer();// 넘겨받은 정보 변수에 담기				

				int[] userEnterNumbers = new int[3];
				for (int i = 0; i < userEnterNumbers.length; i++) {
					userEnterNumbers[userEnterNumbers.length - (1 + i)] = (userEnterNumber
							/ (int) (Math.pow(10, userEnterNumbers.length - (1 + i))));
					userEnterNumber = userEnterNumber % (int) (Math.pow(10, userEnterNumbers.length - (1 + i)));
				} // userEnterNumbers[]에 담기 1의자리숫자는 userEnterNumbers[0]에 담기고 100의자리 숫자는
					// userEnterNumbers[2]에 담긴다.

				BaseballGameLogic bgl = new BaseballGameLogic(answer, userEnterNumbers);
				String result = bgl.strikeBall();

				if (result.equals("3S0B")) {
					intentJTextArea.append("\n" + "3S0B");
					intentJTextArea.append("\n" + "\n" + "축하합니다! 정답입니다!");
					baseballGameView.answer = null;
					intentJTextField.setEnabled(false);
					return;
				}
				count++;
				intentJTextArea.append("\n" + "\n" + userEnterAnswer);
				intentJTextArea.append("\n" + result + "\n");
				intentJTextArea.append("시도횟수 : "+ count+"번");
				intentJTextField.setText("");
				intentJTextArea.setCaretPosition(baseballGameView.getContent().getDocument().getLength());
				
				if(count>=9) {
					String answerNumber = "";
					for (int i = 0; i < answer.length; i++) {
						answerNumber += answer[answer.length - (1 + i)];
					}
					baseballGameView.getContent().append("\n" +"\n" +"기회를 모두 소진하였습니다! 게임종료!" + "\n");
					baseballGameView.getContent().append("정답은" + answerNumber + "입니다!"+ "\n");
					baseballGameView.getContent().append("새 게임을 하고 싶다면, 새 게임 버튼을 눌러주세요"+ "\n");
					baseballGameView.answer = null;
					intentJTextField.setEnabled(false);
					return;					
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(baseballGameView, "숫자를 입력해주세요(3자리,중복x)");
			}

		}
		if (ae.getSource() == baseballGameView.getNewGameBtn()) {
			// 여기에 JTextArea 클리어 시키는 코드 입력.
			baseballGameView.newGame();
			baseballGameView.getWritingAnswer().setEnabled(true);
		}
		if (ae.getSource() == baseballGameView.getTextDeleteBtn()) {
			// 여기에 JTextArea 클리어 코드 입력
			baseballGameView.getContent().setText("");

		}
		if (ae.getSource() == baseballGameView.getShowAnswerBtn()) {
			if (baseballGameView.getAnswer() == null) {
				JOptionPane.showMessageDialog(baseballGameView, "게임을 시작하시려면 '새게임버튼'을 눌러주세요");
				return;
			}
			String answerNumber = "";
			for (int i = 0; i < answer.length; i++) {
				answerNumber += answer[answer.length - (1 + i)];
			}
			baseballGameView.getContent().append(
					"\n" + "\n" + "정답은" + "[" + answerNumber + "] 이었습니다!" + "\n" + "새 게임을 하고싶으시면 [새게임]버튼을 눌러주세요");
			baseballGameView.answer = null;
			baseballGameView.getWritingAnswer().setEnabled(false);
			return;
		}
		if (ae.getSource() == baseballGameView.getExitBtn()) {
			System.exit(0);
		}
	}
}
