package com.daopractice;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

public class TableJFrame extends JFrame {

	JTable userTable;
	JScrollPane listJs;
	JPanel listPanel;

	Vector<String> userColumn = new Vector<String>();
	Vector<String> userRow;

	DefaultTableModel model;
	
	public TableJFrame(String title) {
		super(title);
	}

	public void initDisplay() {

		userColumn.addElement("Emp_id");
		userColumn.addElement("Emp_name");
		userColumn.addElement("Salary");
		userColumn.addElement("Lev");

		model = new DefaultTableModel(userColumn, 0);
		userTable = new JTable(model);
		
		setBounds(500,500,200,200);
		setResizable(false);

		listPanel = new JPanel();
		listPanel.setLayout(new BorderLayout());		
		listJs = new JScrollPane(userTable);
		listPanel.add(listJs, BorderLayout.CENTER);		

		add(listPanel);
		pack();

		setVisible(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		
	}
	
	public void insertRow(String rEmp_id,String rEmp_name, String rSalary, String rLev) {
		userRow = new Vector<String>();
		userRow.addElement(rEmp_id);
		userRow.addElement(rEmp_name);
		userRow.addElement(rSalary);
		userRow.addElement(rLev);
		model.addRow(userRow);
		Vector v = model.getDataVector();
		for(int i=0;i<v.size();i++) {
			System.out.println(v.get(i).toString());
		}
	}
}
