package com.address;

public class DeleteLogic {
	AddressBookInterface aBookInterface = new AddressBookDao();

	public AddressVO addressDelete(AddressVO paVO) {
		AddressVO raVO = null;
		raVO = aBookInterface.addressDelete(paVO);		
		return raVO;
	}

}
