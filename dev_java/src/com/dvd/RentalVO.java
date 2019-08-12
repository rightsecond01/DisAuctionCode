package com.dvd;

public class RentalVO {
	
	  private String r_num      = null;
	  private String r_date     = null;
	  private String returndate = null;
	  private int latefee       = 0;
	  private String duedate    = null;
	  private String memid      = null;
	  private String command    = null;//insert, update, delete
      private int status = -1;// 오라클 응답 성공 : 1, 오라클 응답 실패 : 0
	public String getR_num() {
		return r_num;
	}
	public void setR_num(String r_num) {
		this.r_num = r_num;
	}
	public String getR_date() {
		return r_date;
	}
	public void setR_date(String r_date) {
		this.r_date = r_date;
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
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getMemid() {
		return memid;
	}
	public void setMemid(String memid) {
		this.memid = memid;
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
