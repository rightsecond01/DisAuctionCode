package com.ch2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Test3 implements ActionListener{
	JSpinner jsp = null;
	JButton btn = new JButton("버튼!!");
	public static void main(String[] args) {
		Test3 t = new Test3();
		JFrame test = new JFrame();
		test.setLayout(new GridLayout(2,2));
		SpinnerNumberModel model = new SpinnerNumberModel(1,1,1000,1);
		t.jsp =  new JSpinner(model);
		t.btn.addActionListener(t);
		test.add(t.jsp);
		test.add(t.btn);
		test.setVisible(true);
		test.setSize(500,500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jsp) {
		}
		else if ( e.getSource()==btn) {
			System.out.println(jsp.getValue());			
		}
	}

}
