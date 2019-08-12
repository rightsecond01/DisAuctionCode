package com.dvd;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class RentalDetailView implements ActionListener {

	JButton jbtn_ins = new JButton("입력");
	JButton jbtn_upd = new JButton("수정");
	JButton jbtn_del = new JButton("삭제");
	JButton jbtn_add = new JButton("[+]");

	JDialog jdl_rend = new JDialog();
	JPanel jp_btn = new JPanel();

	Vector userColumn = new Vector();
	Vector userRow;
	DefaultTableModel dtm_table;
	JTable jt_table;
	JScrollPane jsp_table;

	List<RendetailVO> rdtList = null;
	RentalVO rVO = null;
	boolean isInsert;
	
	RentalDetailView(List<RendetailVO> list) {
		this.rdtList = list;
	}

	RentalDetailView(boolean isInsert, RentalVO rrVO) {
		this.isInsert = isInsert;
		this.rVO = rrVO;
	}

	public void initDisplay() {
		/////////////////// 버튼 이벤트 등록//////////////////
		jbtn_ins.addActionListener(this);
		jbtn_upd.addActionListener(this);
		jbtn_del.addActionListener(this);
		jbtn_add.addActionListener(this);
		/////////////////// 버튼 화면에 추가//////////////////
		jp_btn.setLayout(new FlowLayout(FlowLayout.LEFT));
		jp_btn.add(jbtn_ins);
		jp_btn.add(jbtn_upd);
		jp_btn.add(jbtn_del);
		jp_btn.add(jbtn_add);
		jdl_rend.add("North", jp_btn);
		//////////////// 테이블 컬럼까지 만들기시작/////////////////
		JTablecolumn jtbc = new JTablecolumn();
		jtbc.setRentalDetailColumn();
		for (int i = 0; i < jtbc.column.size(); i++) {// userColumn에 컬럼값 추가하는 반복문
			userColumn.addElement(jtbc.column.get(i));
		}
		dtm_table = new DefaultTableModel(userColumn, 0);
		jt_table = new JTable(dtm_table);
		jt_table.setRowHeight(25);
		jt_table.getTableHeader().setReorderingAllowed(false);
		jsp_table = new JScrollPane(jt_table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jt_table.getTableHeader().setReorderingAllowed(false);
		jt_table.getTableHeader().setBackground(new Color(130, 160, 160));
		jt_table.getTableHeader().setForeground(Color.white);
		jt_table.getColumnModel().getColumn(0).setPreferredWidth(120);
		jt_table.getColumnModel().getColumn(1).setPreferredWidth(80);
		jt_table.getColumnModel().getColumn(2).setPreferredWidth(120);
		jt_table.getColumnModel().getColumn(3).setPreferredWidth(200);
		jt_table.getColumnModel().getColumn(4).setPreferredWidth(100);
		jt_table.getColumnModel().getColumn(5).setPreferredWidth(120);
		jt_table.getColumnModel().getColumn(6).setPreferredWidth(120);
		jt_table.getColumnModel().getColumn(7).setPreferredWidth(120);
		jt_table.getColumnModel().getColumn(7).setPreferredWidth(120);
		jt_table.setRowSelectionAllowed(true);
		jt_table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jdl_rend.add(jsp_table);
		/////////////////// 테이블 컬럼까지 만들기 끝///////////////////////
		//////////////////////////////////////////////////////////
		/////////////////// 테이블 로우 채우기 시작 ////////////////////////
		if (dtm_table.getRowCount() > 0) {
			dtm_table.setRowCount(0);
		}

		if (isInsert == true) {// 대여입력일경우 상세대여 테이블 그리기
			addInsertRow();
			jbtn_upd.setVisible(false);
			jbtn_del.setVisible(false);
		} else if ((rdtList == null) || (rdtList.size() == 0)) {// 대여조회일경우 상세대여 테이블 그리기
			JOptionPane.showMessageDialog(jdl_rend, "데이터가 없습니다");
			return;
		} else {
			refreshData();			
		}
		/////////////////// 테이블 로우 채우기 끝//////////////////////////

		jdl_rend.setSize(1100, 500);
		jdl_rend.setVisible(true);
		jdl_rend.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	public void addInsertRow() {
		userRow = new Vector();
		for (int i = 0; i < userColumn.size(); i++) {
			if (i == 0) {
				userRow.add(rVO.getR_num());
			}else if(i==5){
				userRow.add(rVO.getMemid());
			}else if(i==3||i==4||i==6||i==8) {
				userRow.add("입력금지");
			}else if (i == 7) {
				userRow.add(rVO.getReturndate());
			}else {
				userRow.add("");
			}
		}
		dtm_table.addRow(userRow);
	}
	
	public void refreshData() {
		for (int i = 0; i < rdtList.size(); i++) {
			RendetailVO rdtVO = rdtList.get(i);
			userRow = new Vector();
			userRow.add(0, rdtVO.getR_num());
			userRow.add(1, rdtVO.getR_detailnum());
			userRow.add(2, rdtVO.getSerialnum());
			userRow.add(3, rdtVO.getMov_title());
			userRow.add(4, rdtVO.getR_fee() + "원");
			userRow.add(5, rdtVO.getMemid());
			userRow.add(6, (rdtVO.getDuedate()).substring(0, 10));// to_date 함수가 시분초까지 출력해서 substring() 이용해서 자름
			userRow.add(7, rdtVO.getReturndate());
			userRow.add(8, rdtVO.getLatefee() + "원");
			dtm_table.addRow(userRow);
		}
		jbtn_ins.setVisible(false);
		jbtn_add.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jbtn_ins) {
			if(jt_table.getSelectedRows().length > -1) {
				int[] selectedrows = jt_table.getSelectedRows();
				
				if(dtm_table.getRowCount()!=selectedrows.length) {
					JOptionPane.showMessageDialog(jdl_rend, "모든 데이터를 선택하여 주십시오.");
					return;
				}else {
					List<RendetailVO> list = new ArrayList<>();
					
					for(int i = 0; i<selectedrows.length;i++) {						
					RendetailVO prdtVO = new RendetailVO();
					prdtVO.setCommand("insert");
					prdtVO.setR_num(jt_table.getValueAt(i, 0).toString());
					prdtVO.setR_detailnum(jt_table.getValueAt(i, 1).toString());
					prdtVO.setSerialnum(jt_table.getValueAt(i, 2).toString());
					list.add(prdtVO);
					}
					
					RendetailTableCtrl rdttCtrl = new RendetailTableCtrl();
					RendetailVO rrdtVO = rdttCtrl.send(list);
					
					if (rrdtVO != null) {
						if (rrdtVO.getStatus() == 1) {
							JOptionPane.showMessageDialog(jdl_rend, "입력성공");
							jdl_rend.dispose();
						} else {
							JOptionPane.showMessageDialog(jdl_rend, "입력실패");
							return;
						}
					}				
				}
				
			}else {
				JOptionPane.showMessageDialog(jdl_rend, "입력할 데이터를 선택하여 주십시오.");
				return;
			}

		} else if (e.getSource() == jbtn_upd) {

		} else if (e.getSource() == jbtn_add) {
			addInsertRow();
			jdl_rend.repaint();
		}
	}

	
	
	  public static void main(String[] args) { RentalVO a = new RentalVO();
	  a.setR_num("대여번호"); a.setDuedate("의무반납일"); a.setReturndate("미반납");
	  a.setMemid("사람");
	  
	  
	  RentalDetailView t = new RentalDetailView(true, a); t.initDisplay();
	  
	  }
	 
	 
 	
  
}

