package com.address;

public class AddressVO {
	  private String id;      //아이디
	  private String name;    //이름
	  private String address; //주소
	  private String hp;      //핸드폰번호
	  private String gender;  //성별
	  private String birthday;//생년월일
	  private String comments;//비고
	  private String regdate; //등록일
	  //테이블에는 없지만 사용자가 지시한 업무에 대한 구분을 하기위해 사용되는 변수 선언
	  private String command;//select, detail, insert, update, delete
	  //오라클서버에서 반환되는 값을 담을 변수 - 1:성공(입력,수정,삭제), 0:실패
	  private int status = -1;
	
	
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getHp() {
		return hp;
	}
	public String getGender() {
		return gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public String getComments() {
		return comments;
	}
	public String getRegdate() {
		return regdate;
	}
	public String getCommand() {
		return command;
	}
	public int getStatus() {
		return status;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setHp(String hp) {
		this.hp = hp;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public void setStatus(int status) {
		this.status = status;
	}

}
