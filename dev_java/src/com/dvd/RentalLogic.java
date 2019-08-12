package com.dvd;

import java.util.List;

public class RentalLogic {
	
	RentalDao rDao = null;
	
	public RentalVO rentalInsert(RentalVO prVO) {
		RentalVO rrVO = null;
		rDao = new RentalDao();
		rrVO = rDao.rentalInsert(prVO);
		return rrVO;
		
	}
	public RentalVO rentalUpdate(RentalVO prVO) {
		RentalVO rrVO = null;
		rDao = new RentalDao();
		rrVO = rDao.rentalUpdate(prVO);
		return rrVO;
		
	}
	public RentalVO rentalDelete(RentalVO prVO) {
		RentalVO rrVO = null;
		rDao = new RentalDao();
		rrVO = rDao.rentalDelete(prVO);
		return rrVO;
		
	}
	
	
	public List<RentalVO> getTable() {
		List<RentalVO> renList = null;
		rDao = new RentalDao();
		renList = rDao.getTable();		
		return renList;
	}

}
