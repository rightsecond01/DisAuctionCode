package com.ch05;

public class MemverSimulation {

	public static void main(String[] args) {
		MemberVO mem = new MemberVO();
		
		mem.setMem_id("이순신"); // mem_id = "이순신";
		String id = mem.getMem_id();
		System.out.println(id);

	}

}
