package com.discodeaucion_ver02;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.NumberFormatter;

import com.google.gson.Gson;
import com.util.Token;



public class RoomCreate extends JDialog implements ActionListener{
	private JLabel labelTitle;
	private JTextField txtTitle;
	private JLabel labelGame;
	private JComboBox comboGame;
	private JLabel labelRtype;
	private JComboBox comboRtype;
	private JLabel labelLimit;
	private JLabel labelExplain;
	private JSpinner jspLimit;
	private SpinnerNumberModel limit_Model;
	
	private String[] gameList = {"League of Legend","배틀그라운드"};
	private String[] roomType = {"AuctionRoom","VoiceTalkRoom"};
	
	Font font = new Font("돋움체", Font.PLAIN, 20);
	JPanel jp_center = new JPanel();
	JPanel jp_south  = new JPanel();
	JButton jbtn_create   = new JButton("생성");
	JButton jbtn_cancle   = new JButton("취소");
	

	
	MainView mv = null;
	
	public RoomCreate(MainView mv) {
		this.mv = mv;
	}

	public String getTitle() {
		return txtTitle.getText();
	}

	public String getGame() {
		return comboGame.getSelectedItem().toString();
	}

	public int getRoomtype() {
		
		if(comboRtype.getSelectedItem().equals("AuctionRoom")) return 0;
		else return 1;
	}


	public String getLimit() {
		return jspLimit.getValue().toString();
	}


	public void initDisplay() {
		jbtn_create.addActionListener(this);
		jbtn_cancle.addActionListener(this);
		
		jp_center.setLayout(null);
		
		labelTitle   = new JLabel("방 이름 ");
		labelGame    = new JLabel("게임 ");
		labelRtype   = new JLabel("기능 ");
		labelLimit   = new JLabel("최대 인원 ");
		labelExplain = new JLabel("(최대 1,000명)");
		
		labelTitle.setFont(font);
		labelGame.setFont(font);
		labelRtype.setFont(font);
		labelLimit.setFont(font);
		
		txtTitle = new JTextField(20);
		
		comboGame = new JComboBox(gameList);		
		comboRtype = new JComboBox(roomType);	
		
		limit_Model = new SpinnerNumberModel(1,1,1000,1);
		jspLimit = new JSpinner(limit_Model);
	
		
		////////////////////////start of setBounds///////////////////////////
		labelTitle.setBounds(20, 20, 200, 25);
		txtTitle.setBounds(150, 20, 250, 25);

		labelGame.setBounds(20, 90, 100, 25);
		comboGame.setBounds(150, 90, 250, 25);

		labelRtype.setBounds(20, 160, 100, 25);
		comboRtype.setBounds(150, 160, 250, 25);

		labelLimit.setBounds(20, 250, 100, 25);
		jspLimit.setBounds(150, 250, 250, 25);
		labelExplain.setBounds(310, 278, 100, 25);
		////////////////////////end of setBounds/////////////////////////////
		jp_center.add(labelTitle);
		jp_center.add(txtTitle);
		jp_center.add(labelGame);
		jp_center.add(comboGame);
		jp_center.add(labelRtype);
		jp_center.add(comboRtype);
		jp_center.add(labelLimit);
		jp_center.add(jspLimit);
		jp_center.add(labelExplain);
		
		jp_south.add(jbtn_create);
		jp_south.add(jbtn_cancle);
		
		
		this.setTitle("새로운 방 생성");
		this.setLayout(new BorderLayout());
		this.add("Center",jp_center);
		this.add("South", jp_south);
		this.setSize(450,400);
		this.setVisible(true);
	}		

	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		
		if(obj==jbtn_create) {
			String master_id = mv.cc.user_id;
			String room_title = getTitle();
			String room_game = getGame();
			int room_type = getRoomtype();
			int p_limit = Integer.parseInt(getLimit());
			
			Map<String,Object> pRmap = new HashMap<String, Object>();
			pRmap.put("master_id", master_id);
			pRmap.put("room_title", room_title);
			pRmap.put("room_game", room_game);
			pRmap.put("room_type", room_type);
			pRmap.put("p_limit", p_limit);
			
			Gson g = new Gson();
			String roomInfo = g.toJson(pRmap);
			
			try {
				mv.cc.main_oos.writeObject(Dprotocol.ROOM_CREATE
						                  +Dprotocol.seperator+roomInfo						
						);	
				
				this.dispose();
			} catch (IOException e1) {					
				e1.printStackTrace();
			}
			
			
			
		}
		
		else if(obj==jbtn_cancle) {
			this.dispose();
			
		}
		
	}

}
