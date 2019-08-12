package com.network23;

import java.util.List;
import java.util.Vector;

public class Room {
	List<TalkServerThread> userList = new Vector<>();
	List<String> nameList = new Vector<>();
	String title = null;
	String state = null;
	int max = 0;
	int current = 0;

	public Room() {

	}
	public Room(String title, int current) {
		this.title = title;
		this.current = current;
	}
	public Room(String title, String state, int current) {
		this.title = title;
		this.state = state;
		this.current = current;
	}

	public List<TalkServerThread> getUserList() {
		return userList;
	}
	public void setUserList(List<TalkServerThread> userList) {
		this.userList = userList;
	}
	public List<String> getNameList() {
		return nameList;
	}
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

}
