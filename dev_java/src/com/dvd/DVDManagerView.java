package com.dvd;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.table.DefaultTableModel;

public class DVDManagerView implements ActionListener, MouseListener, KeyListener {
	// 선언부 - 선언부 안에서 메소드 호출 불가
	//////////////////////////// 메뉴바 추가하기 시작 /////////////////////////////
	JMenuBar jmb_dvd = new JMenuBar();
	JMenu jm_member = new JMenu("회원관리");
	JMenuItem jmi_mins = new JMenuItem("회원가입");
	JMenuItem jmi_mupd = new JMenuItem("회원수정");
	JMenuItem jmi_mdel = new JMenuItem("회원탈퇴");
	JMenu jm_rent = new JMenu("대여관리");
	JMenuItem jmi_rins = new JMenuItem("대여등록");
	JMenuItem jmi_rupd = new JMenuItem("대여수정");
	JMenuItem jmi_rdel = new JMenuItem("대여삭제");
	JMenu jm_dvd = new JMenu("DVD관리");
	JMenuItem jmi_dins = new JMenuItem("DVD등록");
	JMenuItem jmi_dupd = new JMenuItem("DVD수정");
	JMenuItem jmi_ddel = new JMenuItem("DVD삭제");
	JMenu jm_point = new JMenu("POINT관리");
	JMenuItem jmi_pupd = new JMenuItem("POINT수정");
	JMenu jm_sales = new JMenu("매출관리");
	JMenuItem jmi_ss = new JMenuItem("매출검색");

	//////////////////////////// 메뉴바 추가하기 끝 /////////////////////////////

	//////////////////////////// 검색기 추가 시작 /////////////////////////////
	String searchLabel[] = { "DVD명", "이름", "대여날짜" };
	JComboBox jcb_search = new JComboBox(searchLabel);
	JTextField jtf_keyword = new JTextField("검색할 키워드를 입력하세요.", 50);
	//////////////////////////// 검색기 추가 끝 /////////////////////////////
	//////////////////////////// 테이블 선택 ///////////////////////////////
	JLabel labelTable = new JLabel("조회 테이블 선택 : ");
	String tableLabel[] = { "회원", "대여", "DVD", "POINT", "매출" };
	JComboBox jcb_table = new JComboBox(tableLabel);
	///////////////////////////////////////////////////////////////////
	// 이 속지에 조회,입력,수정,삭제 버튼 추가하기 -FlowLayout
	JPanel jp_north = new JPanel();// GridLayout써서 두개 영역으로 쪼갬.
	JPanel jp_north_selectTable = new JPanel();// 테이블선택기 추가
	JPanel jp_north_search = new JPanel();// 검색기 추가
	JPanel jp_north_btn = new JPanel();// 기존 버튼 4개 이관
	JButton jbtn_sel = new JButton("조회");
	JButton jbtn_ins = new JButton("입력");
	JButton jbtn_upd = new JButton("수정");
	JButton jbtn_del = new JButton("삭제");
	JButton jbtn_det = new JButton("상세");
	JFrame jf_dvd = new JFrame();
	// 생성자 - 생성자는 절대로 리턴타입을 가질 수 없다.

	Vector userColumn = new Vector();
	Vector userRow;
	DefaultTableModel dtm_table;
	JTable jt_table;
	JScrollPane jsp_table;

	String s_table;

	boolean isVisible_detailBtn;
	boolean isVisible_insertBtn;
	boolean isInsert = false;

	RentalDetailView renDetailView = null;
	////////////////////////// 테이블커맨드 정의/////////////////////////////////
	private static final String _MEM = "회원";
	private static final String _DVD = "DVD";
	private static final String _REN = "대여";
	private static final String _POI = "POINT";
	private static final String _SAL = "매출";

	// s_table과 isVisible_detailBtn,isVisible_insertBtn 설정논리메소드
	public void selectedMenuLogic(String label) {
		int len_crud = label.length();
		String crud = label.substring(len_crud - 2, len_crud);

		String selectedTable = label.replace(crud, "");

		if (crud.equals("가입")||crud.equals("등록")) {
			this.isVisible_insertBtn = true;
			this.isInsert = true;
		} else {
			this.isVisible_insertBtn = false;
			this.isInsert = false;
		}

		if (selectedTable.equals(_MEM)) {
			this.s_table = _MEM;
			this.isVisible_detailBtn = false;
		} else if (selectedTable.equals(_DVD)) {
			this.s_table = _DVD;
			this.isVisible_detailBtn = false;
		} else if (selectedTable.equals(_REN)) {
			this.s_table = _REN;
			this.isVisible_detailBtn = true;
		} else if (selectedTable.equals(_POI)) {
			this.s_table = _POI;
			this.isVisible_detailBtn = false;
		} else if (selectedTable.equals(_SAL)) {
			this.s_table = _SAL;
			this.isVisible_detailBtn = false;
		}
	}

	public void selectedTableLogic(String table) {
		if (table.equals(_MEM)) {
			this.s_table = _MEM;
			this.isVisible_detailBtn = false;
			this.isVisible_insertBtn = false;
			this.isInsert = false;
		} else if (table.equals(_DVD)) {
			this.s_table = _DVD;
			this.isVisible_detailBtn = false;
			this.isVisible_insertBtn = false;
			this.isInsert = false;
		} else if (table.equals(_REN)) {
			this.s_table = _REN;
			this.isVisible_detailBtn = true;
			this.isVisible_insertBtn = false;
			this.isInsert = false;
		} else if (table.equals(_POI)) {
			this.s_table = _POI;
			this.isVisible_detailBtn = false;
			this.isVisible_insertBtn = false;
			this.isInsert = false;
		} else if (table.equals(_SAL)) {
			this.s_table = _SAL;
			this.isVisible_detailBtn = false;
			this.isVisible_insertBtn = false;
			this.isInsert = false;
		}
	}

	public DVDManagerView() {
	}

	// 선택된 메뉴를 Stirng타입의 파라미터로 넘겨받아 해당 메뉴의 컬럼으로 userColumn을 채워넣는 메소드
	public void setUserColumn(String p_selectedMenu) {
		JTablecolumn jtc = new JTablecolumn();// 선택된 메뉴에 따른 컬럼을 결정하는 클래스
		// selectedMenu에 따라 컬럼을 변경.
		if (p_selectedMenu.equals(_MEM)) {
			jtc.setMemColumn();
		} else if (p_selectedMenu.equals(_DVD)) {
			jtc.setDVDColumn();
		} else if (p_selectedMenu.equals(_REN)) {
			jtc.setRentalColumn();
		} else if (p_selectedMenu.equals(_POI)) {
			jtc.setPointColumn();
		} else if (p_selectedMenu.equals(_SAL)) {
			jtc.setSalesColumn();
		}

		for (int i = 0; i < jtc.column.size(); i++) {// userColumn에 컬럼값 추가하는 반복문
			userColumn.addElement(jtc.column.get(i));
		}
	}

	// 각종 JTable관련 설정메소드
	public void makeJTable() {
		if (jsp_table != null) {
			jf_dvd.remove(jsp_table);
		}
		if (s_table != null) {
			userColumn.clear();
			setUserColumn(s_table);// userCoulmn에 addElement하는 메소드 호출
			dtm_table = new DefaultTableModel(userColumn, 0);
			jt_table = new JTable(dtm_table);
			jt_table.setRowHeight(25);
			jt_table.getTableHeader().setReorderingAllowed(false);
			jsp_table = new JScrollPane(jt_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			jt_table.getTableHeader().setReorderingAllowed(false);
			jt_table.getTableHeader().setBackground(new Color(130, 160, 160));
			jt_table.getTableHeader().setForeground(Color.white);
			jf_dvd.add("Center", jsp_table);
		}

	}

	// 화면처리구현
	public void initDisplay() {

		jcb_table.setRenderer(new MyComboBoxRenderer("선택"));
		jcb_table.setSelectedIndex(-1);
		//////////////////////////// 화면단 구성/////////////////////////////////////
		jp_north.setLayout(new GridLayout(3, 1));
		jp_north_search.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_north_search.add(jcb_search);
		jp_north_search.add(jtf_keyword);
		jp_north_selectTable.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_north_selectTable.add(labelTable);
		jp_north_selectTable.add(jcb_table);
		jp_north_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_north_btn.add(jbtn_sel);
		jp_north_btn.add(jbtn_ins);
		jp_north_btn.add(jbtn_upd);
		jp_north_btn.add(jbtn_del);
		jp_north_btn.add(jbtn_det);
		jbtn_sel.setBackground(new Color(210, 240, 120));
		jbtn_sel.setForeground(new Color(0, 0, 0));
		jbtn_ins.setBackground(new Color(115, 240, 200));
		jbtn_ins.setForeground(new Color(0, 0, 0));
		jbtn_upd.setBackground(new Color(255, 200, 210));
		jbtn_upd.setForeground(new Color(0, 0, 0));
		jbtn_del.setBackground(new Color(255, 240, 200));
		jbtn_del.setForeground(new Color(0, 0, 0));

		jf_dvd.setTitle("DVD대여관리시스템 Ver1.0");
		///////////////////////////// 메뉴바 등록////////////////////////////////
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

		jm_sales.add(jmi_ss);
		jmb_dvd.add(jm_sales);

		jf_dvd.setJMenuBar(jmb_dvd);
		////////////////////// 메뉴바 액션등록///////////////////////////////
		jmi_mins.addActionListener(this);
		jmi_mupd.addActionListener(this);
		jmi_mdel.addActionListener(this);

		jmi_rins.addActionListener(this);
		jmi_rupd.addActionListener(this);
		jmi_rdel.addActionListener(this);

		jmi_dins.addActionListener(this);
		jmi_dupd.addActionListener(this);
		jmi_ddel.addActionListener(this);

		jmi_pupd.addActionListener(this);

		jmi_ss.addActionListener(this);
		////////////////////////// 메뉴바 액션등록 끝////////////////////////////
		////////////////////////// 버튼 및 기타 액션등록//////////////////////////
		jbtn_sel.addActionListener(this);
		jbtn_ins.addActionListener(this);
		jbtn_upd.addActionListener(this);
		jbtn_del.addActionListener(this);
		jbtn_det.addActionListener(this);

		jcb_table.addActionListener(this);

		jtf_keyword.addMouseListener(this);
		jtf_keyword.addActionListener(this);

		jp_north.add(jp_north_search);
		jp_north.add(jp_north_selectTable);
		jp_north.add(jp_north_btn);
		/////////////////////////////////////////////////////////////////////
		jf_dvd.add("North", jp_north);
		jbtn_det.setVisible(isVisible_detailBtn);
		jf_dvd.setSize(1700, 800);
		jf_dvd.setVisible(true);
	}

	int cnt = 0;

	// 메인메소드
	public static void main(String[] args) {
		DVDManagerView dvd = new DVDManagerView();
		dvd.initDisplay();

	}
	
	public void menuInsert(String label) {
		selectedMenuLogic(label);
		jbtn_ins.setVisible(isVisible_insertBtn);
		jbtn_det.setVisible(isVisible_detailBtn);
		makeJTable();
		
		// 로우추가
		userRow = new Vector();
		for (int i = 0; i < userColumn.size(); i++) {
			if(i==6) {
				userRow.add("작성금지");
			}else {
				userRow.add("");
			}
		}
		dtm_table.addRow(userRow);
		//
		jf_dvd.invalidate();
		jf_dvd.validate();
		jf_dvd.repaint();
	}
	public void menuOther(String label) {
		selectedMenuLogic(label);
		jbtn_ins.setVisible(isVisible_insertBtn);
		jbtn_det.setVisible(isVisible_detailBtn);
		makeJTable();			
		jf_dvd.invalidate();
		jf_dvd.validate();
		jf_dvd.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 라벨뽑아내기
		String label = e.getActionCommand();

		// 테이블선택 콤보박스 선택시 이벤트
		if (e.getSource() == jcb_table) {
			String selectedTable = jcb_table.getSelectedItem().toString();
			selectedTableLogic(selectedTable);
		}

		////////////////////////// 메뉴바 이벤트 처리 시작////////////////////////////////////
		if (e.getSource() == jmi_mins) {			
			menuInsert(label);
		} else if (e.getSource() == jmi_mupd) {
			menuOther(label);
		} else if (e.getSource() == jmi_mdel) {
			menuOther(label);
		} else if (e.getSource() == jmi_rins) {
			menuInsert(label);
		} else if (e.getSource() == jmi_rupd) {
			menuOther(label);
		} else if (e.getSource() == jmi_rdel) {
			menuOther(label);
		} else if (e.getSource() == jmi_dins) {
			menuInsert(label);
		} else if (e.getSource() == jmi_dupd) {
			menuOther(label);
		} else if (e.getSource() == jmi_ddel) {
			menuOther(label);
		} else if (e.getSource() == jmi_pupd) {
			menuOther(label);
		} else if (e.getSource() == jmi_ss) {
			menuOther(label);
		} 
		///////////////////////////////// 메뉴바 클릭시 이벤트 정의 끝//////////////////////
		///////////////////////////////// 버튼클릭시 이벤트 정의/////////////////////////
		if (e.getSource() == jbtn_sel) {// 조회버튼
			if (s_table == null) {
				JOptionPane.showMessageDialog(jf_dvd, "조회할 테이블을 선택하세요.");
				return;
			}
			jbtn_det.setVisible(isVisible_detailBtn);
			jbtn_ins.setVisible(isVisible_insertBtn);
			makeJTable();// 여기까지 선텍한 테이블의 컬럼생성
			/////////////////////////// 선택한 테이블 로우 생성
			/////////////////////////// /////////////////////////////////////////
			if (s_table.equals(_MEM)) {// 회원테이블 선택시

				MemTableCtrl mtCtrl = new MemTableCtrl();
				List<MemVO> list = new ArrayList<>();
				list = mtCtrl.send("search");
				if ((list == null) || (list.size() == 0)) {
					JOptionPane.showMessageDialog(jf_dvd, "데이터가 없습니다");
					return;
				} else {
					for (int i = 0; i < list.size(); i++) {
						MemVO mVO = list.get(i);
						userRow = new Vector();
						userRow.add(0, mVO.getMemid());
						userRow.add(1, mVO.getMemname());
						userRow.add(2, mVO.getTel());
						userRow.add(3, mVO.getPhonenum());
						userRow.add(4, mVO.getZipcode());
						userRow.add(5, mVO.getAddress());
						userRow.add(6, mVO.getResistdate());
						userRow.add(7, mVO.getMempw());
						dtm_table.addRow(userRow);
					}
				}
			} else if (s_table.equals(_REN)) {// 대여테이블 선택시

				RentalTableCtrl rtCtrl = new RentalTableCtrl();
				List<RentalVO> list = new ArrayList<>();
				list = rtCtrl.send("search");
				if ((list == null) || (list.size() == 0)) {
					JOptionPane.showMessageDialog(jf_dvd, "데이터가 없습니다");
					return;
				} else {
					for (int i = 0; i < list.size(); i++) {
						RentalVO rVO = list.get(i);
						userRow = new Vector();
						userRow.add(0, rVO.getR_num());
						userRow.add(1, rVO.getR_date());
						userRow.add(2, rVO.getReturndate());
						userRow.add(3, rVO.getLatefee() + "원");
						userRow.add(4, rVO.getDuedate());
						userRow.add(5, rVO.getMemid());
						dtm_table.addRow(userRow);
					}
				}
			} else if (s_table.equals(_DVD)) {// dvd테이블 선택시

				DVDTableCtrl dtCtrl = new DVDTableCtrl();
				List<DVDVO> list = new ArrayList<>();
				list = dtCtrl.send("search");
				if ((list == null) || (list.size() == 0)) {
					JOptionPane.showMessageDialog(jf_dvd, "데이터가 없습니다");
					return;
				} else {
					for (int i = 0; i < list.size(); i++) {
						DVDVO mVO = list.get(i);
						userRow = new Vector();
						userRow.add(0, mVO.getSerialnum());
						userRow.add(1, mVO.getGenre());
						userRow.add(2, mVO.getMov_class());
						userRow.add(3, mVO.getMov_title());
						userRow.add(4, mVO.getMaker());
						userRow.add(5, mVO.getNation());
						userRow.add(6, mVO.getLeadingactor());
						userRow.add(7, mVO.getDirector());
						userRow.add(8, mVO.getMov_date());
						userRow.add(9, mVO.getV_date());
						userRow.add(10, mVO.getDamage());
						userRow.add(11, mVO.getR_check());
						userRow.add(12, mVO.getR_fee());
						dtm_table.addRow(userRow);
					}
				}
			}
			//////////////////////// 선택한 테이블 로우 생성
			//////////////////////// 끝//////////////////////////////////////////

			jf_dvd.invalidate();
			jf_dvd.validate();
			jf_dvd.repaint();

		} else if (e.getSource() == jbtn_ins) {// 입력버튼
			int i = 0;
			if (s_table == null) {
				JOptionPane.showMessageDialog(jf_dvd, "입력하실 메뉴를 먼저 선택하세요.");
				return;
			} else if (s_table.equals(_MEM)) {// 회원입력시
				MemVO pmVO = new MemVO();
				pmVO.setCommand("insert");
				pmVO.setMemid(jt_table.getValueAt(0, i++).toString());
				pmVO.setMemname(jt_table.getValueAt(0, i++).toString());
				pmVO.setTel(jt_table.getValueAt(0, i++).toString());
				pmVO.setPhonenum(jt_table.getValueAt(0, i++).toString());
				pmVO.setZipcode(jt_table.getValueAt(0, i++).toString());
				pmVO.setAddress(jt_table.getValueAt(0, i++).toString());
				pmVO.setMempw(jt_table.getValueAt(0, i + 1).toString());

				MemTableCtrl mtCtrl = new MemTableCtrl();
				MemVO rmVO = mtCtrl.send(pmVO);
				i = 0;
				if (rmVO != null) {
					if (rmVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "입력성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "입력실패");
						return;
					}
				}
			} else if (s_table.equals(_REN)) {// 대여입력시
				RentalVO prVO = new RentalVO();
				prVO.setCommand("insert");
				prVO.setR_num(jt_table.getValueAt(0, i++).toString());
				i++;//1 입력금지
				prVO.setReturndate(jt_table.getValueAt(0, i++).toString());
				i++;//3 입력금지
				i++;//4 입력금지
				prVO.setMemid(jt_table.getValueAt(0, i).toString());
				
				RentalTableCtrl rtCtrl = new RentalTableCtrl();
				RentalVO rrVO = rtCtrl.send(prVO);
				i = 0;
				if (rrVO != null) {
					if (rrVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "입력성공, 상세입력 페이지로 넘어갑니다.");
						renDetailView = new RentalDetailView(isInsert,prVO);
						renDetailView.initDisplay();
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "입력실패");
						return;
					}
				}
				

			} else if (s_table.equals(_DVD)) {// dvd입력시
				DVDVO pdVO = new DVDVO();
				pdVO.setCommand("insert");
				pdVO.setSerialnum(jt_table.getValueAt(0, i++).toString());
				pdVO.setGenre(jt_table.getValueAt(0, i++).toString());
				pdVO.setMov_class(jt_table.getValueAt(0, i++).toString());
				pdVO.setMov_title(jt_table.getValueAt(0, i++).toString());
				pdVO.setMaker(jt_table.getValueAt(0, i++).toString());
				pdVO.setNation(jt_table.getValueAt(0, i++).toString());
				pdVO.setLeadingactor(jt_table.getValueAt(0, i++).toString());
				pdVO.setDirector(jt_table.getValueAt(0, i++).toString());
				pdVO.setMov_date(jt_table.getValueAt(0, i++).toString());
				pdVO.setV_date(jt_table.getValueAt(0, i++).toString());
				pdVO.setDamage(jt_table.getValueAt(0, i++).toString());
				pdVO.setR_check(jt_table.getValueAt(0, i++).toString());
				// 여기 대여료부분 생략함.

				DVDTableCtrl dtCtrl = new DVDTableCtrl();
				DVDVO rdVO = dtCtrl.send(pdVO);
				i = 0;
				
				if (rdVO != null) {
					if (rdVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "입력성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "입력실패");
						return;
					}
				}

			} else {
				JOptionPane.showMessageDialog(jf_dvd, "입력하실 메뉴를 선택하세요.");
				return;
			}
		} else if (e.getSource() == jbtn_upd) {// 수정버튼

			int row = jt_table.getSelectedRow();
			int i = 0;

			if (jsp_table == null) {
				JOptionPane.showMessageDialog(jf_dvd, "수정할 테이블을 먼저 조회하세요.");
				return;
			} else if (s_table.equals(_MEM)) {// 회원
				MemVO pmVO = new MemVO();
				pmVO.setCommand("update");
				pmVO.setMemid(jt_table.getValueAt(row, i++).toString());
				pmVO.setMemname(jt_table.getValueAt(row, i++).toString());
				pmVO.setTel(jt_table.getValueAt(row, i++).toString());
				pmVO.setPhonenum(jt_table.getValueAt(row, i++).toString());
				pmVO.setZipcode(jt_table.getValueAt(row, i++).toString());
				pmVO.setAddress(jt_table.getValueAt(row, i++).toString());
				pmVO.setMempw(jt_table.getValueAt(row, i + 1).toString());

				MemTableCtrl mtCtrl = new MemTableCtrl();
				MemVO rmVO = mtCtrl.send(pmVO);
				i = 0;
				if (rmVO != null) {
					if (rmVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "수정성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "수정실패");
						return;
					}
				}

			} else if (s_table.equals(_REN)) {// 대여
				RentalVO prVO = new RentalVO();
				prVO.setCommand("update");
				prVO.setR_num(jt_table.getValueAt(row,0).toString());
				prVO.setReturndate(jt_table.getValueAt(row,2).toString());
				
				RentalTableCtrl rtCtrl = new RentalTableCtrl();
				RentalVO rrVO = rtCtrl.send(prVO);
				if (rrVO != null) {
					if (rrVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "수정성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "수정실패");
						return;
					}
				}

			} else if (s_table.equals(_DVD)) {// dvd
				DVDVO pdVO = new DVDVO();
				pdVO.setCommand("update");
				pdVO.setSerialnum(jt_table.getValueAt(row, i++).toString());
				pdVO.setGenre(jt_table.getValueAt(row, i++).toString());
				pdVO.setMov_class(jt_table.getValueAt(row, i++).toString());
				pdVO.setMov_title(jt_table.getValueAt(row, i++).toString());
				pdVO.setMaker(jt_table.getValueAt(row, i++).toString());
				pdVO.setNation(jt_table.getValueAt(row, i++).toString());
				pdVO.setLeadingactor(jt_table.getValueAt(row, i++).toString());
				pdVO.setDirector(jt_table.getValueAt(row, i++).toString());
				pdVO.setMov_date(jt_table.getValueAt(row, i++).toString());
				pdVO.setV_date(jt_table.getValueAt(row, i++).toString());
				pdVO.setDamage(jt_table.getValueAt(row, i++).toString());
				pdVO.setR_check(jt_table.getValueAt(row, i++).toString());
				// 대여료는 생략함.
				///////////////////////////////////////////////////////
				DVDTableCtrl dtCtrl = new DVDTableCtrl();
				DVDVO rdVO = dtCtrl.send(pdVO);
				i = 0;
				if (rdVO != null) {
					if (rdVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "수정성공");
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "수정실패");
						return;
					}
				}
			}

		} else if (e.getSource() == jbtn_del) {// 삭제버튼

			int row = jt_table.getSelectedRow();

			if (jsp_table == null) {
				JOptionPane.showMessageDialog(jf_dvd, "삭제할 테이블을 먼저 조회하세요.");
				return;
			} else if (s_table.equals(_MEM)) {// 회원
				MemVO pmVO = new MemVO();
				pmVO.setCommand("delete");
				pmVO.setMemid(jt_table.getValueAt(row, 0).toString());
				MemTableCtrl mtCtrl = new MemTableCtrl();
				MemVO rmVO = mtCtrl.send(pmVO);

				if (rmVO != null) {
					if (rmVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "삭제성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "삭제실패");
						return;
					}
				}

			} else if (s_table.equals(_REN)) {// 대여
				RentalVO prVO = new RentalVO();
				prVO.setCommand("delete");
				prVO.setR_num(jt_table.getValueAt(row, 0).toString());
				RentalTableCtrl rtCtrl = new RentalTableCtrl();
				RentalVO rrVO = rtCtrl.send(prVO);				
				if (rrVO != null) {
					if (rrVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "삭제성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "삭제실패");
						return;
					}
				}

			} else if (s_table.equals(_DVD)) {// dvd
				DVDVO pdVO = new DVDVO();
				pdVO.setCommand("delete");
				pdVO.setSerialnum(jt_table.getValueAt(row, 0).toString());
				DVDTableCtrl dtCtrl = new DVDTableCtrl();
				DVDVO rdVO = dtCtrl.send(pdVO);

				if (rdVO != null) {
					if (rdVO.getStatus() == 1) {
						JOptionPane.showMessageDialog(jf_dvd, "삭제성공");
						return;
					} else {
						JOptionPane.showMessageDialog(jf_dvd, "삭제실패");
						return;
					}
				}
			}
		} else if (e.getSource() == jbtn_det) {			
			if(this.isInsert == true) {//대여입력일경우 상세버튼 동작				
				JOptionPane.showMessageDialog(jf_dvd, "이 버튼은 사용할 수 없습니다.");
				return;
			}else { //대여조회일경우 상세버튼 동작 
				int row = jt_table.getSelectedRow();
				try {
					String index_rnum = null;
					Object value = jt_table.getValueAt(row, 0);
					index_rnum = value.toString();

					RendetailTableCtrl rdCtrl = new RendetailTableCtrl();
					List<RendetailVO> list = new ArrayList<>();
					list = rdCtrl.send(index_rnum, "detail");
					renDetailView = new RentalDetailView(list);
					renDetailView.initDisplay();

				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(jf_dvd, "상세조회할 행을 선택하세요.");
					return;
				}
			}
			

		} else if (e.getSource() == jtf_keyword) {

		}

	}

	@Override
	public void mouseClicked(MouseEvent me) {

		if (cnt == 0) {
			jtf_keyword.setText("");
			cnt++;
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {

		if (e.getKeyChar() == KeyEvent.VK_DELETE) {
			System.out.println("delete 버튼 이벤트");
		}
	}

}


// 테이블을 선택하는 콤보박스의 기본값을 선택으로 만드는 클래스
class MyComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
	
	private static final long serialVersionUID = 1L;
	private String _title;

	public MyComboBoxRenderer(String title) {
		_title = title;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean hasFocus) {
		if (index == -1 && value == null)
			setText(_title);
		else
			setText(value.toString());
		return this;
	}
}
