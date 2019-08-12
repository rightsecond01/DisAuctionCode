package com.dvd;

import java.util.List;

public class MemTableCtrl {
	private static final String _INS = "insert";
	private static final String _UPD = "update";
	private static final String _DEL = "delete";
	private static final String _SEA = "search";	
	
	public MemVO send(MemVO pmVO) {
		MemVO rmVO = null;
		MemLogic mLogic = new MemLogic();
		String command = pmVO.getCommand();
		if(_INS.equals(command)) {//입력				
			rmVO = mLogic.memInsert(pmVO);
		}else if(_UPD.equals(command)) {//수정
			rmVO = mLogic.memUpdate(pmVO);
		}else if(_DEL.equals(command)) {//삭제
			rmVO = mLogic.memDelete(pmVO);		
		}
		return rmVO;
	}
	
	public List<MemVO> send(String command){		
		List<MemVO> memList = null;
		if(_SEA.equals(command)) {
			MemLogic mLogic = new MemLogic();
			memList = mLogic.getTable();
		}
		return memList;
	}

}
