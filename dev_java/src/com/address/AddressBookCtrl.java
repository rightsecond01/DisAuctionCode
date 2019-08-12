package com.address;

import java.util.List;

/* view계층과 model 계층 사이에서 인터페이스 역할
 * 조회 - 상세조회(1row-AddressVO), 전체 조회(n row-ArrayList<AddressVO>)
 * 입력 - 추가
 * 수정 - 수정처리
 * 삭제 - 삭제처리
 * 
 *  
 */
public class AddressBookCtrl {
	
	private static final String _SEL = "select";
	private static final String _DET = "detail";
	private static final String _INS = "insert";
	private static final String _UPD = "update";
	private static final String _DEL = "delete";
	
	/************************************************************************
	 * 
	 * @param paVO
	 * @return raVO - 오라클 서버에서 반환값을 담은 변수
	 * @throws Exception - 이 메소드를 호출하는 메소드에서는 반드시 try..catch 
	 **********************************************************************/
	
	public AddressVO send(AddressVO paVO) throws Exception {
		AddressVO raVO = null;
		String command = paVO.getCommand();
		if(_INS.equals(command)) {//입력
			RegisterLogic regLogic = new RegisterLogic();
			raVO = regLogic.addressInsert(paVO);
		}else if(_UPD.equals(command)) {//수정
			ModifyLogic modLogic = new ModifyLogic();
			raVO = modLogic.addressUpdate(paVO);
		}else if(_DEL.equals(command)) {//삭제
			DeleteLogic delLogic = new DeleteLogic();
			raVO = delLogic.addressDelete(paVO);
		}else if(_DET.equals(command)) {//상세조회
			RetrieveLogic retLogic = new RetrieveLogic();
			raVO = retLogic.getAddressDetail(paVO);
		}else {
			throw new Exception("잘못된 command 입니다");
		}
		return raVO;		
	}
	
	/*********************************************************************
	 * 전체 조회 구현
	 * @param command select
	 * @return ArrayList<AddressVO>
	 *********************************************************************/
	
	public List<AddressVO> send(String command){
		List<AddressVO> addrList = null;
		if(_SEL.equals(command)) {
			RetrieveLogic retLogic = new RetrieveLogic();
			addrList = retLogic.getAddress();
		}
		return addrList;
	}

}
