package com.baseballgame;

import javax.print.attribute.AttributeSet;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class JTextFieldLimit extends PlainDocument {
	private int limit;
	BaseballGameView baseballGameView;

	public JTextFieldLimit(int limit, BaseballGameView baseballGameView) {
		super();
		this.limit = limit;
		this.baseballGameView = baseballGameView;
	}

	public void insertString(int offset, String str, javax.swing.text.AttributeSet attr) throws BadLocationException {
		if (str == null) {
			JOptionPane.showMessageDialog(baseballGameView, "숫자를 입력해주세요,중복X)");
		}

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}