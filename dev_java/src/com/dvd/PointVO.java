package com.dvd;

public class PointVO {
	
	  private int savepoint      = 0;
	  private int usecount       = 0;
	  private String r_num       = null; 
	  private String r_detailnum = null; 
	  private int status = -1;// 오라클 응답 성공 : 1, 오라클 응답 실패 : 0
	public int getSavepoint() {
		return savepoint;
	}
	public void setSavepoint(int savepoint) {
		this.savepoint = savepoint;
	}
	public int getUsecount() {
		return usecount;
	}
	public void setUsecount(int usecount) {
		this.usecount = usecount;
	}
	public String getR_num() {
		return r_num;
	}
	public void setR_num(String r_num) {
		this.r_num = r_num;
	}
	public String getR_detailnum() {
		return r_detailnum;
	}
	public void setR_detailnum(String r_detailnum) {
		this.r_detailnum = r_detailnum;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
