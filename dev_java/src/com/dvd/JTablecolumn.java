package com.dvd;

import java.util.HashMap;

public class JTablecolumn {
	public HashMap<Integer,String> column = new HashMap<>();
	
	
	public void setMemColumn() {
		column.put(0,"회원아이디");
		column.put(1,"회원이름");
		column.put(2,"전화번호");
		column.put(3,"핸드폰번호");
		column.put(4,"우편번호");
		column.put(5,"주소");
		column.put(6,"등록일");
		column.put(7,"회원비밀번호");
	}
	
	public void setDVDColumn() {
		column.put(0,"시리얼번호");
		column.put(1,"장르");
		column.put(2,"등급");
		column.put(3,"영화제목");
		column.put(4,"제작사");
		column.put(5,"국가");
		column.put(6,"주연배우");
		column.put(7,"감독");
		column.put(8,"개봉일");
		column.put(9,"dvd출시일");
		column.put(10,"손상여부");
		column.put(11,"대여여부");
		column.put(12,"대여료");				
	}
	
	public void setRentalColumn() {//렌탈디테일과 조인
		column.put(0,"대여번호");				
		column.put(1,"대여일");				
		column.put(2,"반납일");				
		column.put(3,"연체료");//대여번호로 group by, sum(대여상세연체료)		
		column.put(4,"반납예정일");				
		column.put(5,"회원아이디");					
	}
	
	public void setRentalDetailColumn() {
		column.put(0,"대여번호");
		column.put(1,"대여상세번호");
		column.put(2,"시리얼번호");
		column.put(3,"영화제목");
		column.put(4,"대여료");
		column.put(5,"회원아이디");
		column.put(6,"반납예정일");
		column.put(7,"반납일");
		column.put(8,"연체료");//DECODE(SIGN(현재날짜 - 반납예정일),1,(현재날짜 - 반납예정일)*100원)
		
	}
	
	public void setPointColumn() {//포인트 추후 다시 설계
		column.put(0,"누적포인트");					
		column.put(1,"사용포인트");					
		column.put(2,"잔여포인트");					
		column.put(3,"대여번호");
		column.put(4,"대여상세번호");		
	}
	
	public void setSalesColumn() {
		column.put(0,"기간");
		column.put(1,"총계");
		column.put(2,"대여횟수");		
	}
	
	

}
