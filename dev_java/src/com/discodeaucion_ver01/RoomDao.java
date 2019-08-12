package com.discodeaucion_ver01;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.util.DBConnectionMgr;

import oracle.jdbc.internal.OracleCallableStatement;

public class RoomDao {
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	CallableStatement cstmt = null;
	OracleCallableStatement ocstmt = null;
	
	DBConnectionMgr dbMgr = null;
	
	public int createRoom (Map<String,Object> pRoomMap) {
		int result = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append("INSERT INTO chat_room(token,room_title,room_type,master_id,p_limit,p_current,room_game) ");
			sql.append("VALUES(?,?,?,?,?,?,?)");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, pRoomMap.get("token").toString());
			pstmt.setString(2, pRoomMap.get("room_title").toString());
			pstmt.setInt(3, (Integer)pRoomMap.get("room_type"));
			pstmt.setString(4, pRoomMap.get("master_id").toString());
			pstmt.setInt(5, (Integer)pRoomMap.get("p_limit"));
			pstmt.setInt(6, (Integer)pRoomMap.get("p_current"));
			pstmt.setString(7, pRoomMap.get("room_game").toString());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.close(con, pstmt);
		}
		
		return result;
	}
	public int isExistRoom(String token) {
		int result = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT 1 FROM dual ");
		sql.append("WHERE EXISTS (");
		sql.append("              SELECT room_title FROM chat_room ");
		sql.append("               WHERE token='" + token + "'");
		sql.append("                 AND rownum=1");
		sql.append("              )");
		try {
			con = dbMgr.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				result = rs.getInt("1");
			}
			if(rs==null) {//sql요청이 아예 동작하지 않은 경우
				JOptionPane.showMessageDialog(new JFrame(), "서버에러 202 : 서버에 문제가 발생했습니다. 프로그램을 종료합니다.");
				System.exit(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.close(con, stmt, rs);
		}
		
		return result;
	}
	public Map<String,Object> getRoomInfo(String token) {
		Map<String,Object> rMap = new HashMap<String, Object>();
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT room_title,room_type,room_game,master_id,p_limit,p_current ");
		sql.append("FROM chat_room ");
		sql.append("WHERE token ='" + token + "'");
		try {
			con = dbMgr.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()) {
				rMap.put("token",token);
				rMap.put("room_title", rs.getString("room_title"));
				rMap.put("room_type", rs.getInt("room_type"));
				rMap.put("room_game", rs.getString("room_game"));
				rMap.put("master_id", rs.getString("master_id"));
				rMap.put("p_limit", rs.getInt("p_limit"));
				rMap.put("p_current", rs.getInt("p_current"));			
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbMgr.close(con, stmt, rs);
		}
		return rMap;
	}
	public int isAlreadyInRoom(String token, String user_id) {
		int exist = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		con = dbMgr.getConnection();
		sql.append("{call proc_isAlreadyInRoom(?,?,?)}");
		try {
			cstmt = con.prepareCall(sql.toString());
			cstmt.setString(1,token);
			cstmt.setString(2,user_id);		
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.execute();
			exist = cstmt.getInt(3);
			System.out.println(exist);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exist;
	}
	public int insertRoomIn(String token, String user_id) {
		int result = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		con = dbMgr.getConnection();
		try {
			sql.append("INSERT INTO chat_inroom(mem_id,token) ");
			sql.append("VALUES(?,?)");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, user_id);
			pstmt.setString(2, token);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbMgr.close(con, pstmt);
		}
		return result;
	}

}
