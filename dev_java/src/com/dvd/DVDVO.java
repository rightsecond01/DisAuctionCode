package com.dvd;

public class DVDVO {
	
	  private String serialnum    = null;
	  private String genre        = null;
	  private String mov_class    = null;
	  private String mov_title    = null;
	  private String maker        = null;
	  private String nation       = null;
	  private String leadingactor = null;
	  private String director     = null;
	  private String mov_date     = null;
	  private String v_date       = null;
	  private String damage       = null;
	  private String r_check      = null;
	  private int r_fee           = 0;
	  private String command    = null;//insert, update, delete
	  private int status = -1;// 오라클 응답 성공 : 1, 오라클 응답 실패 : 0
	public String getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getMov_class() {
		return mov_class;
	}
	public void setMov_class(String mov_class) {
		this.mov_class = mov_class;
	}
	public String getMov_title() {
		return mov_title;
	}
	public void setMov_title(String mov_title) {
		this.mov_title = mov_title;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getLeadingactor() {
		return leadingactor;
	}
	public void setLeadingactor(String leadingactor) {
		this.leadingactor = leadingactor;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getMov_date() {
		return mov_date;
	}
	public void setMov_date(String mov_date) {
		this.mov_date = mov_date;
	}
	public String getV_date() {
		return v_date;
	}
	public void setV_date(String v_date) {
		this.v_date = v_date;
	}
	public String getDamage() {
		return damage;
	}
	public void setDamage(String damage) {
		this.damage = damage;
	}
	public String getR_check() {
		return r_check;
	}
	public void setR_check(String r_check) {
		this.r_check = r_check;
	}
	public int getR_fee() {
		return r_fee;
	}
	public void setR_fee(int r_fee) {
		this.r_fee = r_fee;
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
