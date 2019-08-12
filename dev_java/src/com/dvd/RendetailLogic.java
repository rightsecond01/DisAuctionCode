package com.dvd;

import java.util.ArrayList;
import java.util.List;

public class RendetailLogic {
	
	RendetailDao rdDao;
	
	
	public RendetailVO renDetailInsert(List<RendetailVO> prdtVO) {
		rdDao = new RendetailDao();
		RendetailVO rrdtVO = null;
		rrdtVO = rdDao.renDetailInsert(prdtVO);
		return rrdtVO;
	}

	public RendetailVO renDetailUpdate(List<RendetailVO> prdtVO) {
		rdDao = new RendetailDao();
		RendetailVO rrdtVO = null;
		rrdtVO = rdDao.renDetailUpdate(prdtVO);
		return rrdtVO;
	}

	public RendetailVO renDetailDelete(List<RendetailVO> prdtVO) {
		rdDao = new RendetailDao();
		RendetailVO rrdtVO = null;
		rrdtVO = rdDao.renDetailDelete(prdtVO);
		return rrdtVO;
	}

	public List<RendetailVO> getDetailTable(String rnum) {
		List<RendetailVO> list = null;
		rdDao = new RendetailDao();
		list = rdDao.getDetailTable(rnum);
		return list;
	}

	

}
