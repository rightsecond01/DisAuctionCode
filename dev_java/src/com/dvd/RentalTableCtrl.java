package com.dvd;

import java.util.List;

public class RentalTableCtrl {
	private static final String _INS = "insert";
	private static final String _UPD = "update";
	private static final String _DEL = "delete";
	private static final String _SEA = "search";	
	
	public RentalVO send(RentalVO prVO) {
		RentalVO rrVO = null;
		RentalLogic rLogic = new RentalLogic();
		String command = prVO.getCommand();
		if(_INS.equals(command)) {//입력
			rrVO = rLogic.rentalInsert(prVO);
		}else if(_UPD.equals(command)) {//수정
			rrVO = rLogic.rentalUpdate(prVO);
		}else if(_DEL.equals(command)) {//삭제
			rrVO = rLogic.rentalDelete(prVO);
		}
		return rrVO;		
	}
	
	public List<RentalVO> send(String command){		
		List<RentalVO> renList = null;
		if(_SEA.equals(command)) {
			RentalLogic rLogic = new RentalLogic();
			renList = rLogic.getTable();
		}
		return renList;
	}
}
