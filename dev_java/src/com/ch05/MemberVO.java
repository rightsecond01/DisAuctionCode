package com.ch05;

public class MemberVO {
   
	private String mem_id; //회원아이디
	private String mem_pw; //회원비밀번호
	private String mem_name; //회원명
	private String mem_addr; //회원주소
	private String mem_zipcode; //회원우편번호

	public String getMem_id() {
		return mem_id;
	}
	public String getMem_pw() {
		return mem_pw;
	}
	public String getMem_name() {
		return mem_name;
	}
	public String getMem_addr() {
		return mem_addr;
	}
	public String getMem_zipcode() {
		return mem_zipcode;
	}
	
	public void setMem_id(String mem_id) {
		this.mem_id = mem_id;
	}
	public void setMem_pw(String mem_pw) {
		this.mem_pw = mem_pw;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public void setMem_addr(String mem_addr) {
		this.mem_addr = mem_addr;
	}
	public void setMem_zipcode(String mem_zipcode) {
		this.mem_zipcode = mem_zipcode;
	}
	
}
