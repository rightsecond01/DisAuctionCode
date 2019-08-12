package com.address;

import java.util.List;

public class RetrieveLogic {
	AddressBookInterface aBookInterface = new AddressBookDao();

	public AddressVO getAddressDetail(AddressVO paVO) {
		AddressVO raVO = null;
		raVO = aBookInterface.getAddressDetail(paVO);
		return raVO;
	}
	
	public List<AddressVO> getAddress(){
		List<AddressVO> list = null;
		list = aBookInterface.getAddress();
		return list;
		
	}

}
