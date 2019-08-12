package com.dvd;

import java.util.List;

public class DVDTableCtrl {
	private static final String _INS = "insert";
	private static final String _UPD = "update";
	private static final String _DEL = "delete";
	private static final String _SEA = "search";
	
	public DVDVO send(DVDVO pdVO) {
		DVDVO rdVO = null;
		DVDLogic dLogic = new DVDLogic();
		String command = pdVO.getCommand();
		
		if(_INS.equals(command)) {//입력
			rdVO = dLogic.dvdInsert(pdVO);
		}else if(_UPD.equals(command)) {//수정
			rdVO = dLogic.dvdUpdate(pdVO);
		}else if(_DEL.equals(command)) {//삭제
			rdVO = dLogic.dvdDelete(pdVO);
		}
		return rdVO;
	}
	
	
	public List<DVDVO> send(String command){
		List<DVDVO> memList = null;
		if(_SEA.equals(command)) {
			DVDLogic dLogic = new DVDLogic();
			memList = dLogic.getTable();
		}
		return memList;
	}

}
