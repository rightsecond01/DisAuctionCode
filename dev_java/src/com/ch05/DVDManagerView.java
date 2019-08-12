package com.ch05;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DVDManagerView {
	
	JMenuBar jmb_dvd = new JMenuBar();
	
	JMenu jm_member = new JMenu("회원관리");
	JMenuItem jmi_mins = new JMenu("회원등록");
	JMenuItem jmi_mupd = new JMenu("회원정보수정");
	JMenuItem jmi_mdel = new JMenu("회원탈퇴");
	
	JMenu jm_rent = new JMenu("대여관리");
	JMenuItem jmi_rins = new JMenu("대여등록");
	JMenuItem jmi_rupd = new JMenu("대여수정");
	JMenuItem jmi_rdel = new JMenu("대여삭제");
	
	JMenu jm_dvd = new JMenu("DVD관리");
	JMenuItem jmi_dins = new JMenu("DVD등록");
	JMenuItem jmi_dupd = new JMenu("DVD수정");
	JMenuItem jmi_ddel = new JMenu("DVD삭제");
	
	JMenu jm_point = new JMenu("POINT관리");
	JMenuItem jmi_pupd = new JMenu("POINT수정");
	
	JMenu jm_sales = new JMenu("매출관리");
	JMenuItem jmi_daySal = new JMenu("일병매출");
	JMenuItem jmi_monSal = new JMenu("월별매출");
	JMenuItem jmi_yrSal = new JMenu("연도별매출");
	
	JFrame jf_dvd = new JFrame();
	
	String[] cols = {"대여날짜","이름","DVD명","전화번호","주소","반납일자"};	

	String[][] data = new String[5][5];
	
	
	DefaultTableModel dtm_rent = new DefaultTableModel(data,cols);
	
	JTable jt_rent = new JTable(dtm_rent);
	
	JScrollPane jsp_rent 
	= new JScrollPane(jt_rent
			,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public DVDManagerView() {
		
	}
	
	public void initDisplay() {
		jf_dvd.setTitle("대여관리시스템 Ver1.0");
		
		jm_member.add(jmi_mins);
		jm_member.add(jmi_mupd);
		jm_member.add(jmi_mdel);
		jmb_dvd.add(jm_member);
		
		jm_rent.add(jmi_rins);
		jm_rent.add(jmi_rupd);
		jm_rent.add(jmi_rdel);
		jmb_dvd.add(jm_rent);
		
		jm_dvd.add(jmi_dins);
		jm_dvd.add(jmi_dupd);
		jm_dvd.add(jmi_ddel);
		jmb_dvd.add(jm_dvd);
		
		jm_point.add(jmi_pupd);
		jmb_dvd.add(jm_point);
		
		jm_sales.add(jmi_daySal);
		jm_sales.add(jmi_monSal);
		jm_sales.add(jmi_yrSal);
		jmb_dvd.add(jm_sales);
		
		jf_dvd.setJMenuBar(jmb_dvd);
		
		jf_dvd.add("Center",jsp_rent);
		jf_dvd.setSize(700,550);
		jf_dvd.setVisible(true);
	}

	public static void main(String[] args) {
		DVDManagerView dvd = new DVDManagerView();		
		dvd.initDisplay();
		
		

	}

}
