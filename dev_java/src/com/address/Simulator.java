package com.address;

public class Simulator {

	public static void main(String[] args) {
		AddressVO paVO = new AddressVO();
		AddressVO raVO = new AddressVO();
		
		paVO.setId("aaa");
		paVO.setGender("남성");
		paVO.setHp("01012345678");
		paVO.setCommand("insert");
		
		AddressBookCtrl ctrl = new AddressBookCtrl();
		try {
			ctrl.send(paVO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
