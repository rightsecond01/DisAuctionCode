package com.dvd;

import java.util.List;

public class MemLogic {
	
	MemDao mDao = null;
	
	public MemVO memInsert(MemVO pmVO) {
		MemVO rmVO = null;		
		mDao = new MemDao();
		rmVO = mDao.memInsert(pmVO);
		return rmVO;
		
	}
	public MemVO memUpdate(MemVO pmVO) {
		MemVO rmVO = null;
		mDao = new MemDao();
		rmVO = mDao.memUpdate(pmVO);
		return rmVO;
		
	}
	public MemVO memDelete(MemVO pmVO) {
		MemVO rmVO = null;
		mDao = new MemDao();
		rmVO = mDao.memDelete(pmVO);
		return rmVO;
		
	}
	
	
	
	public List<MemVO> getTable(){
		List<MemVO> memList = null;
		mDao = new MemDao();
		memList = mDao.getTable();
		return memList;
	}

}
