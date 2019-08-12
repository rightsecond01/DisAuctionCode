package com.dvd;

public class SalesVO {
	
	  private String dmy        = null;
	  private String totalsales = null;
	  private int r_count = 0;
	  private int status = -1;// 오라클 응답 성공 : 1, 오라클 응답 실패 : 0
	public String getDmy() {
		return dmy;
	}
	public void setDmy(String dmy) {
		this.dmy = dmy;
	}
	public String getTotalsales() {
		return totalsales;
	}
	public void setTotalsales(String totalsales) {
		this.totalsales = totalsales;
	}
	public int getR_count() {
		return r_count;
	}
	public void setR_count(int r_count) {
		this.r_count = r_count;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
