package com.address;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SubBook extends JDialog implements ActionListener {

	private JLabel labelId;
	private JTextField txtId;
	private JLabel labelName;
	private JTextField txtName;
	private JLabel labelAddr;
	private JTextField txtAddress;
	private JLabel labelHP;
	private JTextField txtHP;	
	private JLabel labelGender;
	private JComboBox comboGender;
	private JLabel labelBirth;
	private JTextField txtBirth;
	private JLabel labelComment;
	private JTextArea txtComment;
	private JLabel labelRegDate;
	private JTextField txtRegDate;
	private JScrollPane scrollPane;
	private JScrollPane scrollComment;

	Font font = new Font("돋움체", Font.PLAIN, 16);
	JPanel jp_center = new JPanel();
	JPanel jp_south = new JPanel();
	JButton jbtn_save = new JButton("저장");
	JButton jbtn_cancle = new JButton("취소");

	
	AddressBook aBook = null;
	JScrollPane jsp_line = new JScrollPane(jp_center);
	AddressVO aVO = null;
	public SubBook() {		
	}
	/***********************************************************************
	 * 부모창에서 결정된 값(입력,aVO)
	 * @param aVO aVO 입력버튼 눌렀을 땐 null, 수정일 땐 aVO는 DB에서 가저온 정보를 담은 객체를 가짐
	 * @param title - 부모창에서 선택한 버튼의 라벨값
	 * @param aBook - 부모창의 원본 주소번지 담고 있는 변수
	 * @param e - 자식창을 활성화 시키기 위한 boolean타입 변수
	 */
	
	public void set(AddressVO aVO, String title, AddressBook aBook, boolean e) {		
		this.aVO = aVO;		
		initDisplay();
		this.setTitle(title);
		this.aBook = aBook;
		setEditable(e);
		this.setVisible(true);
	}
	
	public void setEditable(boolean e) {
		txtName.setEditable(e);
		txtAddress.setEditable(e);
		txtHP.setEditable(e);
		txtId.setEditable(e);
		comboGender.setEnabled(e);
		txtBirth.setEditable(e);
		txtComment.setEditable(e);
		
		if(aVO!=null) {
			txtId.setEditable(false);
		}
	}

	

	public void initDisplay() {

		jp_center.setLayout(null);

		// 데이터 칼럼명을 보여줄 레이블을 정의합니다.
		labelName = new JLabel("이름(필수입력) ");
		labelAddr = new JLabel("주소 ");
		labelHP = new JLabel("전화번호 ");
		labelId = new JLabel("아이디 ");
		labelGender = new JLabel("성별 ");
		labelBirth = new JLabel("생일(YYYYMMDD) ");
		labelComment = new JLabel("비고 ");
		labelRegDate = new JLabel("수정일 ");

		labelName.setFont(font);
		labelAddr.setFont(font);
		labelHP.setFont(font);
		labelId.setFont(font);
		labelGender.setFont(font);
		labelBirth.setFont(font);
		labelComment.setFont(font);
		labelRegDate.setFont(font);

		// 데이터를 보여줄 텍스트 필드등을 정의합니다.
		txtName = new JTextField(20);
		txtAddress = new JTextField(20);
		txtHP = new JTextField(20);
		txtId = new JTextField(15);
		txtBirth = new JTextField(20);
		txtComment = new JTextArea(3, 20);
		scrollComment = new JScrollPane(txtComment);
		txtRegDate = new JTextField(20);

		String[] genderList = { "남자", "여자" };
		comboGender = new JComboBox(genderList);

		/////////////////// 화면 객체 배치 시작////////////////////////

		labelName.setBounds(20, 20, 200, 20);
		txtName.setBounds(150, 20, 150, 20);

		labelAddr.setBounds(20, 45, 100, 20);
		txtAddress.setBounds(150, 45, 150, 20);

		labelHP.setBounds(20, 70, 100, 20);
		txtHP.setBounds(150, 70, 150, 20);

		labelId.setBounds(20, 95, 100, 20);
		txtId.setBounds(150, 95, 150, 20);

		labelGender.setBounds(20, 120, 100, 20);
		comboGender.setBounds(150, 120, 150, 20);
		comboGender.setFont(new java.awt.Font("굴림", 0, 12));

		labelBirth.setBounds(20, 145, 250, 20);
		txtBirth.setBounds(150, 145, 150, 20);

		labelComment.setBounds(20, 170, 100, 20);
		scrollComment.setBounds(150, 170, 250, 60);

		labelRegDate.setBounds(20, 235, 100, 20);
		txtRegDate.setBounds(150, 235, 150, 20);
		txtRegDate.setEditable(false);

		// 컴포넌트들을 패널에 붙입니다.
		jp_center.add(labelName);
		jp_center.add(txtName);
		jp_center.add(labelAddr);
		jp_center.add(txtAddress);
		jp_center.add(labelHP);
		jp_center.add(txtHP);
		jp_center.add(labelId);
		jp_center.add(txtId);
		jp_center.add(labelGender);
		jp_center.add(comboGender);
		jp_center.add(labelBirth);
		jp_center.add(txtBirth);
		jp_center.add(labelComment);
		jp_center.add(scrollComment);
		jp_center.add(labelRegDate);
		jp_center.add(txtRegDate);
		/////////////////// 화면 객체 배치 끝/////////////////////////
		
		///////////////////화면에 값 넣어주기/////////////////////////	
		if(aVO!=null) {
			setName(aVO.getName());
			setAddress(aVO.getAddress());
			setHP(aVO.getHp());
			setId(aVO.getId());
			setComboGender(aVO.getGender());
			setBirth(aVO.getBirthday());
			setComment(aVO.getComments());
			setRegDate(aVO.getRegdate());
		}		
		
		///////////////////화면에 값 넣기 끝///////////////////////// 

		jbtn_save.addActionListener(this);
		jbtn_cancle.addActionListener(this);
		this.setLayout(new BorderLayout());
		jp_south.add(jbtn_save);
		jp_south.add(jbtn_cancle);
		this.add("Center",jp_center);
		this.add("South", jp_south);
		// 자식창의 제목은 세가지 중 한 가지가 되어야함.
		// 하나의 화면을 가지고 세가지 기능을 어떻게 처리하지?
		// this.setTitle("입력|수정|상세조회");
		this.setSize(450, 500);
		this.setVisible(false);
		this.setResizable(false);

	}
	////////////////////////////화면에서 입력받은 값 혹은 화면에 출력한 값 처리 getter/setter 정의 ///////////////////////////////	
	public String getName() {		
		return txtName.getText();
	}

	public void setName(String txtName) {
		this.txtName.setText(txtName);
	}

	public String getAddress() {
		return txtAddress.getText();
	}

	public void setAddress(String txtAddress) {
		this.txtAddress.setText(txtAddress);
	}

	public String getHP() {
		return txtHP.getText();
	}

	public void setHP(String txtHP) {
		this.txtHP.setText(txtHP);
	}

	public String getId() {
		return txtId.getText();
	}

	public void setId(String txtId) {
		this.txtId.setText(txtId);;
	}

	public String getComboGender() {
		return comboGender.getSelectedItem().toString();
	}
	//////////////////////////젠더 콤보박스 코드///////////////////////////////
	public String getGender() {
		if(comboGender.getSelectedItem().equals("남자")) return "1";
		else return "0";
	}

	public void setComboGender(String p_Gender) {
		if(p_Gender.equals("1")) comboGender.setSelectedItem("남자");
		else comboGender.setSelectedItem("여자");
	}
    /////////////////////////////////////////////////////////////////////
	public String getBirth() {
		return txtBirth.getText();
	}

	public void setBirth(String txtBirth) {
		this.txtBirth.setText(txtBirth);
	}

	public String getComment() {
		return txtComment.getText();
	}

	public void setComment(String txtComment) {
		this.txtComment.setText(txtComment);
	}

	public String getRegDate() {
		return txtRegDate.getText();
	}

	public void setRegDate(String txtRegDate) {
		this.txtRegDate.setText(txtRegDate);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void actionPerformed(ActionEvent e) {
		String label = e.getActionCommand();
		if ("저장".equals(label)) {
			// dispose는 창만 닫게 해줄 뿐 메모리에 대해서까지 영향력이 없다.
			if(aVO != null) {//수정인 경우				
				try {
					AddressVO paVO = new AddressVO();
					paVO.setCommand("update");
					paVO.setId(getId());
					paVO.setName(getName());
					paVO.setAddress(getAddress());
					paVO.setHp(getHP());
					paVO.setBirthday(getBirth());
					paVO.setComments(getComment());
					paVO.setGender(getGender());
					AddressBookCtrl aCtrl = new AddressBookCtrl();
					AddressVO raVO = aCtrl.send(paVO);
					//System.out.println(raVO.getName());
					if(raVO!=null) {
						if(raVO.getStatus()==1) {//입력성공
							JOptionPane.showMessageDialog(aBook, "수정성공");
							this.dispose();
							aBook.refreshDate();
						}else {
							JOptionPane.showMessageDialog(aBook, "수정실패");
							return;
						}
					}
				} catch (Exception e1) {					
					e1.printStackTrace();
				}
			
			}
			else {
				try {//입력인 경우 
					AddressVO paVO = new AddressVO();
					paVO.setCommand("insert");
					paVO.setId(getId());
					paVO.setName(getName());
					paVO.setHp(getHP());
					paVO.setAddress(getAddress());
					paVO.setGender(getGender());
					paVO.setBirthday(getBirth());
					paVO.setComments(getComment());					
					AddressBookCtrl aCtrl = new AddressBookCtrl();
					AddressVO raVO = aCtrl.send(paVO);
					if(raVO!=null) {
						if(raVO.getStatus()==1) {//입력성공
							JOptionPane.showMessageDialog(aBook, "입력성공");
							this.dispose();
							aBook.refreshDate();
						}else {
							JOptionPane.showMessageDialog(aBook, "입력실패");
							return;
						}
					}
				} catch (Exception e2) {
					System.out.println("버튼누를때 문제발생");
				}
			}
		}
		if("취소".equals(label)) {
			this.dispose();
		}

	}

}

/*
 * A a = new A(); a = null; a = new A(); 이벤트처리할 때 마다 추가 선언부에서 선언만 하고 - 전변으로 선언
 * 생성은 이벤트 처리시 마다 생성 객체가 협업이 필요할 때 같은 메소드를 반복해서 호출하는 것은 피해야한다. - stackoverflow
 *
 * 해결방법 메소드 중심의 코딩을 한다. -> 반복되는 코드를 줄일 수 있다.
 *
 * 입력 수정 상세조회 세가지 메뉴를 하나의 화면에서 처리할 때 - 문제발생
 *
 * 입력 - 새로 입력받아야 함 ,insert 수정 - 기존에 가지고 있는 정보를 변경하는 것 - 기본 정보를 보여주는 경우가 대부분이므로
 * select를 먼저하고 화면을 보여주어야 하지 않을까? - UPDATE 상세조회 - 기존에 가입된 사람의 정보를 조회하는 것 -
 * select
 *
 * 객체를 인스턴스화 할 때 주의사항 무조건 전역변수위치에서 인스턴스화하는 것이 좋은 건 아니다. A a = null; a = new A();
 * 그 객체가 필요할 때 주입되는 것이 효율적이다.
 *
 * set(AddressBook aBook,String title)
 *
 * main() - AddressBook인스턴스화처리 - 메모리상주
 *
 * 버튼클릭(입력,수정,상세조회) - 이벤트감지 actionPerformed호출 - 자식창 호출(set호출)
 *
 * SubBook의 initDisplay()가 호출이 안됨.
 *
 * 생성자에서 호출했다.
 *
 * subBook.initDisplay(); subBook.setTitle(label);
 *
 * 문제제기 실제로 생성된 객체는 하나이지만 메소드 콜이 일어날때마다(initDisplay) 객체가 여러개 복제되는 일이 발생
 *
 */