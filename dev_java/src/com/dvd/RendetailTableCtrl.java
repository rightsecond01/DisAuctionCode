package com.dvd;

import java.util.List;

public class RendetailTableCtrl {
	private static final String _INS = "insert";
	private static final String _UPD = "update";
	private static final String _DEL = "delete";
	private static final String _DET = "detail";
	private static final String _SEA = "search";

	public RendetailVO send(List<RendetailVO> prdtVO) {
		RendetailVO rrdtVO = null;
		RendetailLogic rdtLogic = new RendetailLogic();		
		String command = prdtVO.get(0).getCommand();
		
		if (_INS.equals(command)) {// 입력
			rrdtVO = rdtLogic.renDetailInsert(prdtVO);
		} else if (_UPD.equals(command)) {// 수정
			rrdtVO = rdtLogic.renDetailUpdate(prdtVO);
		} else if (_DEL.equals(command)) {// 삭제
			rrdtVO = rdtLogic.renDetailDelete(prdtVO);		} 
		return rrdtVO;
	}
	
	public List<RendetailVO> send(String rnum, String command){
		List<RendetailVO> list = null;
		if (_DET.equals(command)) {//상세조회
			RendetailLogic rdLogic = new RendetailLogic();
			list = rdLogic.getDetailTable(rnum);
		}
		
		
		return list;
	}

}
