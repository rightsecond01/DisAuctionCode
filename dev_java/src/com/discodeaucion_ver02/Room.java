package com.discodeaucion_ver02;

import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Room {
	/*
	 * roomMap에는 chat_room의 컬럼이름과 값을 map형태로 담습니다
	 * connectPortMap은 chat_room에 담겨있는 token값과 port번호를 맵핑 시켜주는 코드입니다.
	 * 
	 *  개선 방향 : DACServerRoomThread dsrt아래 전역변수로 port, roomMap 다있는데 굳이 한번더 여기서 전역변수로 한번더 
	 *  저장해야할까? 라는 의문점 해결하기 
	 */
	private Map<String,Object> roomMap = null;//key=chat_room의 컬럼이름, value=해당컬럼의 값
	private int port = 0;//부여된 포트값(map에서 value로 검색할수 없어서 추가한 전역변수)
	private Map<String, Integer> connectPortMap = null;//key=token, value=포트번호
	private DACServerRoomThread dsrt = null;
	private List<DACServerRoomThread.ServiceThread> roomInList = null;
	
	public Map<String,Object> getRoomMap() {
		return roomMap;
	}
	public void setRoomMap(Map<String,Object> roomMap) {
		this.roomMap = roomMap;
	}
	public int getRoomPort() {//'포트번호(connectPort의 value값)'로 검색을 위한 메소드
		return port;
	}
	public void setRoomPort(int port) {//'포트번호(connectPort의 value값)'로 검색을 위한 메소드
		this.port=port;
	}
	public Map<String, Integer> getConnectPortMap(){
		return connectPortMap;
	}
	public void setConnectPortMap(Map<String, Integer> connectPortMap) {
		this.connectPortMap = connectPortMap;
	}
	public int getPortMappingToken() {//해당 토큰으로 port검색
		return connectPortMap.get(roomMap.get("token"));
	}
	public void setPortMappingToken(Map<String, Integer> connectPort) {//해당 방의 토큰으로 세팅
		this.connectPortMap = connectPort;
	}
	public DACServerRoomThread getDsrt() {
		return dsrt;
	}
	public void setDsrt(DACServerRoomThread dsrt) {
		this.dsrt = dsrt;
	}
	public List<DACServerRoomThread.ServiceThread> getRoomInList() {
		return roomInList;
	}
	public void setRoomInList(List<DACServerRoomThread.ServiceThread> roomInList) {
		this.roomInList = roomInList;
	}	
}
