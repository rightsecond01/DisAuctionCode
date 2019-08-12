package com.network23;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.network1.Protocol;

public class WaitRoom extends JPanel implements ActionListener{

	TalkClientVer2 tc2 = null;
	JPanel jp_first = new JPanel();
	JPanel jp_second = new JPanel();
	JPanel jp_second_south = new JPanel();
	String cols1[] = {"대화명","위치"};
	String data1[][] = new String[0][2];
	DefaultTableModel dtm_wait = new DefaultTableModel(data1,cols1);
	JTable            jtb_wait = new JTable(dtm_wait);
	JScrollPane       jsp_wait = new JScrollPane(jtb_wait
			                  ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                  ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	////////////////////////////////////////////////////////////////////
	String cols2[] = {"단톡명","현재원"};
	String data2[][] = new String[0][2];
	DefaultTableModel dtm_room = new DefaultTableModel(data2,cols2);
	JTable            jtb_room = new JTable(dtm_room);
	JScrollPane       jsp_room = new JScrollPane(jtb_room
			                  ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
			                  ,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JButton jbtn_create = new JButton("단톡생성");
	JButton jbtn_in     = new JButton("입장");
	JButton jbtn_blank  = new JButton("미정");
	JButton jbtn_exit   = new JButton("종료");
	JTableHeader jth_wait = jtb_wait.getTableHeader();
	JTableHeader jth_room = jtb_room.getTableHeader();
	
	String nickName = null;
	String roomTitle = null;
	int currentNum = 0;

	public WaitRoom() {

	}
	public WaitRoom(TalkClientVer2 tc2) {
		this.tc2 = tc2;
		initDisplay();
		
	}

	public void initDisplay() {
		
		jbtn_create.addActionListener(this);
		jbtn_in.addActionListener(this);
		jbtn_blank.addActionListener(this);
		jbtn_exit.addActionListener(this);
		
		this.setLayout(new GridLayout(1,2));
		jp_first.setLayout(new BorderLayout());
		jp_first.add("Center", jsp_wait);		
		this.add(jp_first);
		
		jp_second.setLayout(new BorderLayout());
		jp_second.add("Center",jsp_room);
		
		jp_second_south.setLayout(new GridLayout(2,2));		
		jp_second_south.add(jbtn_create);
		jp_second_south.add(jbtn_in);
		jp_second_south.add(jbtn_blank);
		jp_second_south.add(jbtn_exit);		
		
		jp_second.add("South",jp_second_south);
		this.add(jp_second);
		
	}
	
	public void intro_process() {
		int index = jtb_room.getSelectedRow();
		if(index<0) {//index가 0보다 작으면 선택이 안된 경우 이니깐....
			JOptionPane.showMessageDialog(tc2, "단톡 선택하세요.");
			return;//intro_process탈출
		}else {
			try {
				for(int i=0;i<dtm_room.getRowCount();i++) {
					if(jtb_room.isRowSelected(i)) {
						String roomTitle = (String)dtm_room.getValueAt(i, 0);
						System.out.println(roomTitle);
						tc2.oos.writeObject(Protocol.ROOM_IN
						          + Protocol.seperator+roomTitle
						          + Protocol.seperator+tc2.nickName
						           );
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		}
		jtb_room.clearSelection();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if(obj == jbtn_in) {
			intro_process();
		}
		else if(obj == jbtn_create) {			
			roomTitle = JOptionPane.showInputDialog("단톡명을 입력하세요.");
			if(roomTitle !=null) {
				try {//==> 110|단톡명|0
					tc2.oos.writeObject(Protocol.ROOM_CREATE
					          + Protocol.seperator+roomTitle
					          + Protocol.seperator+0
							);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}


	

}
