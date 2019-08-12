package com.ch05;

public class MemberSimulation2 {

	public static void main(String[] args) {
		MemberVO mem1 = new MemberVO();
		MemberVO mem2 = new MemberVO();
		MemberVO mem3 = new MemberVO();
		
		mem1.setMem_id("이순신");
		mem2.setMem_id("김유신");
		mem3.setMem_id("강감찬");
		
		String id1 = mem1.getMem_id();
		String id2 = mem2.getMem_id();
		String id3 = mem3.getMem_id();
		
		System.out.println(id1);
		System.out.println(id2);
		System.out.println(id3);		
	
	}

}
