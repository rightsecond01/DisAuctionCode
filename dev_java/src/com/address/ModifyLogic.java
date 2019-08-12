package com.address;

public class ModifyLogic {
	AddressBookInterface aBookInterface = new AddressBookDao();
	
	public AddressVO addressUpdate(AddressVO paVO) {
		AddressVO raVO = null;
		raVO = aBookInterface.addressUpdate(paVO);
		
		return raVO;
	}

}
