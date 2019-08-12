package com.address;

public class RegisterLogic {
	AddressBookInterface aBookInterface = new AddressBookDao();

	public AddressVO addressInsert(AddressVO paVO) {
		System.out.println("가입성공");
		AddressVO raVO = null;
		raVO = aBookInterface.addressInsert(paVO);
		return raVO;
		
	}

}
