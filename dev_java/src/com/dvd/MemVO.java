package com.dvd;

public class MemVO {
	
	 private String memid      = null;
	 private String memname    = null;
	 private String tel        = null;
	 private String phonenum   = null;
	 private String zipcode    = null;
	 private String address    = null;
	 private String resistdate = null;
	 private String mempw      = null;
	 private String command    = null;//insert, update, delete
	 private int status = -1;// 오라클 응답 성공 : 1, 오라클 응답 실패 : 0
	public String getMemid() {
		return memid;
	}
	public void setMemid(String memid) {
		this.memid = memid;
	}
	public String getMemname() {
		return memname;
	}
	public void setMemname(String memname) {
		this.memname = memname;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getResistdate() {
		return resistdate;
	}
	public void setResistdate(String resistdate) {
		this.resistdate = resistdate;
	}
	public String getMempw() {
		return mempw;
	}
	public void setMempw(String mempw) {
		this.mempw = mempw;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
