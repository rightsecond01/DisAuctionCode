package com.address;

import java.util.List;

public interface AddressBookInterface {
	//상세조회 처리를 위한 추상 메소드 선언
	public AddressVO getAddressDetail(AddressVO paVO);
	//입력처리를 위한 추상 메소드 선언
	public AddressVO addressInsert(AddressVO paVO);
	//수정처리를 위한 추상 메소드 선언
	public AddressVO addressUpdate(AddressVO paVO);
	//삭제처리를 위한 추상 메소드 선언
	public AddressVO addressDelete(AddressVO paVO);
	//전체 조회 처리를 위한 추상 메소드 선언
	public List<AddressVO> getAddress();

}
