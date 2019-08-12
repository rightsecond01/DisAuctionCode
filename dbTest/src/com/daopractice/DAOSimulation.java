package com.daopractice;

import java.util.ArrayList;

public class DAOSimulation {

	public static void main(String[] args) {
		DAO dao = new DAO();		
		ArrayList<Info> list = dao.select();
		
		TableJFrame tableView = new TableJFrame("SQL-자바 연동테이블 예제");
		tableView.initDisplay();
		
		if(list != null) {
			for(Info i : list) {
				tableView.insertRow(
						i.getEmp_id()
						, i.getEmp_name()
						, i.getSalary()
						, i.getLev());
			
			}
		}
		if(list != null) {
			for(Info i : list) {
				tableView.insertRow(
						i.getEmp_id()
						, i.getEmp_name()
						, i.getSalary()
						, i.getLev());
			
			}
			/*
			 * tableView.userTable.getColumn("Emp_id").setWidth(0);
			 * tableView.userTable.getColumn("Emp_id").setMinWidth(0);
			 * tableView.userTable.getColumn("Emp_id").setMaxWidth(0);
			 * tableView.userTable.getColumn("Emp_name").setWidth(0);
			 * tableView.userTable.getColumn("Emp_name").setMinWidth(0);
			 * tableView.userTable.getColumn("Emp_name").setMaxWidth(0);
			 * tableView.userTable.getColumn("Salary").setWidth(0);
			 * tableView.userTable.getColumn("Salary").setMinWidth(0);
			 * tableView.userTable.getColumn("Salary").setMaxWidth(0);
			 * tableView.userTable.getColumn("Lev").setWidth(0);
			 * tableView.userTable.getColumn("Lev").setMinWidth(0);
			 * tableView.userTable.getColumn("Lev").setMaxWidth(0);
			 */
		}
	}
	
	

}
