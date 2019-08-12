package com.baseballgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

class BaseballGameView extends JFrame {

	private JButton newGameBtn, textDeleteBtn, showAnswerBtn, exitBtn;
	private JTextArea content;
	private JTextField writingAnswer;
	private JScrollPane scroll;
	public int[] answer;

	public int[] getAnswer() {
		return answer;
	}

	// 정답이 담긴 배열에 대한 정보 get
	public JTextField getWritingAnswer() {
		return writingAnswer;
	}
	// JTextField 정보 get

	public JTextArea getContent() {
		return content;
	}
	// JTextArea 정보 get

	public JButton getNewGameBtn() {
		return newGameBtn;
	}

	public JButton getTextDeleteBtn() {
		return textDeleteBtn;
	}

	public JButton getShowAnswerBtn() {
		return showAnswerBtn;
	}

	public JButton getExitBtn() {
		return exitBtn;
	}
	// 눌러진 버튼의 정보 get

	public BaseballGameView() {
		super("숫자야구게임");
		setBounds(100, 100, 900, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 이벤트 처리 담당 객체

		JPanel jTextAreaPanel = new JPanel();
		jTextAreaPanel.setLayout(new BoxLayout(jTextAreaPanel, BoxLayout.Y_AXIS));		
		content = new JTextArea("게임을 시작하시려면, 새게임을 누르세요", 30, 30);
		Font font = new Font("바탕", Font.BOLD, 20);
		content.setFont(font);
		scroll = new JScrollPane(content,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
				,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		content.setEnabled(false);
		jTextAreaPanel.add(scroll);
		// 게임판이 되는 JTextArea 설정과 등록

		writingAnswer = new JTextField("라벨이다!!");
		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
		writingAnswer.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "ENTER");
		Action pressEnter = new BaseballGameEvent(this);
		writingAnswer.getActionMap().put("ENTER", pressEnter);
		writingAnswer.setDocument((new JTextFieldLimit(3, this)));// 입력수 제한
		jTextAreaPanel.add(writingAnswer);
		// JTextfield설정과, 엔터입력시 이벤트 등록

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

		newGameBtn = new JButton("새게임");
		textDeleteBtn = new JButton("지우기");
		showAnswerBtn = new JButton(" 정답   ");
		exitBtn = new JButton("나가기");
		// 버튼초기화

		BaseballGameEvent btnBGE = new BaseballGameEvent(this);
		newGameBtn.addActionListener(btnBGE);
		textDeleteBtn.addActionListener(btnBGE);
		showAnswerBtn.addActionListener(btnBGE);
		exitBtn.addActionListener(btnBGE);
		// 버튼 이벤트 등록

		buttonPanel.add(newGameBtn);
		buttonPanel.add(textDeleteBtn);
		buttonPanel.add(showAnswerBtn);
		buttonPanel.add(exitBtn);
		// 버튼 패널에 추가

		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.X_AXIS));
		backgroundPanel.add(jTextAreaPanel);
		backgroundPanel.add(buttonPanel);
		// 패널 종합

		add(backgroundPanel, BorderLayout.CENTER);
		pack();
		setVisible(true);
	}

	public void newGame() {// answer[] 초기화 메소드, 정답을 설정한다.
		answer = new int[3];
		for (int i = 0; i < answer.length; i++) {
			answer[i] = (int) (Math.random() * 10);
			for (int j = 0; j < i; j++) {
				if (answer[j] == answer[i]) {
					i--;
					break;
				}
			}
		}
		content.setText("");
		content.append("새게임이 시작되었습니다!" + "\n" + "3자리 숫자를 입력해주세요(중복x)");

	}
}
