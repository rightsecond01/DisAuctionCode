package com.dvd;

import java.util.List;

public class DVDLogic {
	
	DVDDao dDao = null;
	public DVDVO dvdInsert(DVDVO pdVO) {
		DVDVO rdVO = null;
		dDao = new DVDDao();
		rdVO = dDao.dvdInsert(pdVO);
		return rdVO;
		
	}
	public DVDVO dvdUpdate(DVDVO pdVO) {
		DVDVO rdVO = null;
		dDao = new DVDDao();
		rdVO = dDao.dvdUpdate(pdVO);
		return rdVO;
		
	}
	public DVDVO dvdDelete(DVDVO pdVO) {
		DVDVO rdVO = null;
		dDao = new DVDDao();
		rdVO = dDao.dvdDelete(pdVO);
		return rdVO;
		
	}
	
	
	public List<DVDVO> getTable(){
		List<DVDVO> memList = null;
		dDao = new DVDDao();
		memList = dDao.getTable();
		return memList;
	}
}
