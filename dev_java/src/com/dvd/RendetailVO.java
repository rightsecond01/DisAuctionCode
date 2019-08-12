package com.dvd;

public class RendetailVO {

	private String r_num = null;
	private String r_detailnum = null;
	private String serialnum = null;
	private String mov_title = null;
	private int r_fee = 0;
	private String memid = null;
	private String duedate = null;
	private String returndate = null;
	private int latefee = 0;
	private String command = null;// insert, update, delete,detail
	private int status = -1;// 오라클 응답 성공 : 1, 오라클 응답 실패 : 0

	public String getR_detailnum() {
		return r_detailnum;
	}

	public void setR_detailnum(String r_detailnum) {
		this.r_detailnum = r_detailnum;
	}

	public String getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}

	public String getR_num() {
		return r_num;
	}

	public void setR_num(String r_num) {
		this.r_num = r_num;
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

	public String getMov_title() {
		return mov_title;
	}

	public void setMov_title(String mov_title) {
		this.mov_title = mov_title;
	}

	public int getR_fee() {
		return r_fee;
	}

	public void setR_fee(int r_fee) {
		this.r_fee = r_fee;
	}

	public String getMemid() {
		return memid;
	}

	public void setMemid(String memid) {
		this.memid = memid;
	}

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}

	public String getReturndate() {
		return returndate;
	}

	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}

	public int getLatefee() {
		return latefee;
	}

	public void setLatefee(int latefee) {
		this.latefee = latefee;
	}

}
