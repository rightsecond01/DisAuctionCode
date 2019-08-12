package com.address;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.ch2.ClientThread;
import com.ch2.TimeClientVer2;

public class AddressBook extends JFrame implements ActionListener {
	JLabel jlb_time = new JLabel("현재시간",JLabel.CENTER);
	SubBook sBook = new SubBook();
	static AddressBook aBook = null;
	JPanel jp_north = new JPanel();
	JButton jbtn_ins = new JButton("입력");
	JButton jbtn_upd = new JButton("수정");
	JButton jbtn_del = new JButton("삭제");
	JButton jbtn_det = new JButton("상세조회");
	JButton jbtn_ser = new JButton("전체조회");

	Vector<String> userColumn = new Vector<String>();
	Vector<String> userRow;
	// 데이터를 담을 수 있는 클래스 필요.
	// DataSet
	DefaultTableModel dtm_address;
	JTable jt_address;// 화면,그리드만 제공. 데이터는 없다
	JScrollPane jsp_address;
	JTableHeader jth_address;

	public void initDisplay() {
		//TimeClientVer2 인스턴스화 하기
		
		TimeClientVer2 tc = new TimeClientVer2(jlb_time);
		ClientThread ct = new ClientThread(tc);
		ct.start();
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// 컬럼추가
		userColumn.addElement("아이디");
		userColumn.addElement("이름");
		userColumn.addElement("주소");
		userColumn.addElement("HP");
		userColumn.addElement("성별");
		userColumn.addElement("생일");
		userColumn.addElement("비고");
		userColumn.addElement("가입일");
		// 테이블생성
		dtm_address = new DefaultTableModel(userColumn, 0);
		jt_address = new JTable(dtm_address);
		jsp_address = new JScrollPane(jt_address);
		jth_address = jt_address.getTableHeader();
		///////////////////////////////////////////////////

		jbtn_ins.addActionListener(this);
		jbtn_upd.addActionListener(this);
		jbtn_del.addActionListener(this);
		jbtn_det.addActionListener(this);
		jbtn_ser.addActionListener(this);

		// 내안에 있을 때는 this 외부에 있을 떄는 인스턴스 변수
		jp_north.setLayout(new FlowLayout());

		jp_north.add(jbtn_ins);
		jp_north.add(jbtn_upd);
		jp_north.add(jbtn_del);
		jp_north.add(jbtn_det);
		jp_north.add(jbtn_ser);
		this.add("South", jlb_time);
		this.add("North", jp_north);
		this.add("Center", jsp_address);
		this.setSize(1400, 600);
		this.setVisible(true);

		jth_address.setFont(new Font("맑은고딕", Font.BOLD, 18));
		jth_address.setBackground(new Color(22, 22, 100));
		jth_address.setForeground(Color.white);
		jth_address.setReorderingAllowed(false);
		jth_address.setResizingAllowed(false);
		jt_address.getColumnModel().getColumn(0).setPreferredWidth(80);
		jt_address.getColumnModel().getColumn(1).setPreferredWidth(100);
		jt_address.getColumnModel().getColumn(2).setPreferredWidth(390);
		jt_address.getColumnModel().getColumn(3).setPreferredWidth(130);
		jt_address.getColumnModel().getColumn(4).setPreferredWidth(50);
		jt_address.getColumnModel().getColumn(5).setPreferredWidth(130);
		jt_address.getColumnModel().getColumn(6).setPreferredWidth(300);
		jt_address.getColumnModel().getColumn(7).setPreferredWidth(130);
		jt_address.repaint();
	}

	// 새로고침 처리 메소드 구현
	public void refreshDate() {
		while (dtm_address.getRowCount() > 0) {
			dtm_address.removeRow(0);
		}
		AddressBookCtrl aCtrl = new AddressBookCtrl();
		List<AddressVO> list = aCtrl.send("select");
		if ((list == null) || (list.size() == 0)) {
			JOptionPane.showMessageDialog(this, "데이터가 없습니다");
		} else {
			for (int i = 0; i < list.size(); i++) {
				AddressVO raVO = list.get(i);
				Vector rowData = new Vector();
				rowData.add(0, raVO.getId());
				rowData.add(1, raVO.getName());
				rowData.add(2, raVO.getAddress());
				rowData.add(3, raVO.getHp());
				rowData.add(4, raVO.getGender());
				rowData.add(5, raVO.getBirthday());
				rowData.add(6, raVO.getComments());
				rowData.add(7, raVO.getRegdate());
				dtm_address.addRow(rowData);
			}
		}
	}

	public static void main(String[] args) {// 인스턴스화처리 ->메모리상주
		if (aBook == null) {
			aBook = new AddressBook();
		}
		aBook.initDisplay();

	}

	@Override
	public void actionPerformed(ActionEvent ae) {// 이벤트 감지
		String label = ae.getActionCommand();// 버튼의 라벨을 읽어올 수 있음.
		
		if ("입력".equals(label)) {
			sBook = null;
			sBook = new SubBook();
			sBook.set(null, label, aBook, true);

		} else if ("수정".equals(label)) {
			sBook = null;
			sBook = new SubBook();
			//선택한 row의 아이디 값 가져오기.
			if(userRow==null) {
				JOptionPane.showMessageDialog(this, "테이블 조회를 먼저 실행하십시오.");
				return;
			}
			int row = jt_address.getSelectedRow();
			Object value = jt_address.getValueAt(row, 0);
			//paVO만들기
			AddressVO paVO = new AddressVO();
			paVO.setId(value.toString());
			paVO.setCommand("detail");
			AddressBookCtrl aCtrl = new AddressBookCtrl();
			try {
				AddressVO raVO = aCtrl.send(paVO);				
				sBook = null;
				sBook = new SubBook();
				sBook.set(raVO, label, aBook, true);
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}				
			

		}else if ("삭제".equals(label)) {
			if(userRow==null) {
				JOptionPane.showMessageDialog(this, "테이블 조회를 먼저 실행하십시오.");
				return;
			}
			
		}
		else if ("상세조회".equals(label)) {
			
			if(userRow!=null) {
				//선택한 row의 아이디 값 가져오기.
				int row = jt_address.getSelectedRow();
				Object value = jt_address.getValueAt(row, 0);
				//paVO만들기
				AddressVO paVO = new AddressVO();
				paVO.setId(value.toString());
				paVO.setCommand("detail");
				AddressBookCtrl aCtrl = new AddressBookCtrl();
				try {
					AddressVO raVO = aCtrl.send(paVO);
					sBook = null;
					sBook = new SubBook();
					sBook.set(raVO, label, aBook, false);
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}				
				
			}else if(userRow==null){
				JOptionPane.showMessageDialog(this, "테이블 조회를 먼저 실행하십시오.");
				return;
			}
			
		} else if ("전체조회".equals(label)) {
			AddressBookCtrl aCtrl = new AddressBookCtrl();
			List<AddressVO> list = new ArrayList<>();
			list = aCtrl.send("select");
			while (dtm_address.getRowCount() > 0) {
				dtm_address.removeRow(0);
			}
			if ((list == null) || (list.size() == 0)) {
				JOptionPane.showMessageDialog(this, "데이터가 없습니다");
			} else {
				for (int i = 0; i < list.size(); i++) {
					AddressVO aVO = list.get(i);
					userRow = new Vector();
					userRow.add(0, aVO.getId());
					userRow.add(1, aVO.getName());
					userRow.add(2, aVO.getAddress());
					userRow.add(3, aVO.getHp());
					userRow.add(4, aVO.getGender());
					userRow.add(5, aVO.getBirthday());
					userRow.add(6, aVO.getComments());
					userRow.add(7, aVO.getRegdate());
					dtm_address.addRow(userRow);
				}
			}
		}

	}

}
