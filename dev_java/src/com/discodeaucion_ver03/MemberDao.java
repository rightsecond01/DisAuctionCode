package com.discodeaucion_ver03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import com.util.DBConnectionMgr;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.internal.OracleCallableStatement;

public class MemberDao {
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	CallableStatement cstmt = null;
	OracleCallableStatement ocstmt = null;

	DBConnectionMgr dbMgr = null;

	public int insertMemver(ChatMemberVO cmVO) {
		int result = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		boolean isId = duplicateInspection(cmVO);

		if (isId) {// isId가 true => 같은아이디가 존재, 입력성공 =1,입력실패=0,중복아이디=2
			result = 2;
			return result;
		}

		try {
			sql.append("INSERT INTO chat_member");
			sql.append("(mem_num,mem_id,mem_pw,mem_name,mem_nick,mem_email,mem_hp,mem_birth,reg_date,mem_profile) ");
			sql.append("VALUES(seq_cmember_num.nextval,?,?,?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?)");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, cmVO.getMem_id());
			pstmt.setString(2, cmVO.getMem_pw());
			pstmt.setString(3, cmVO.getMem_name());
			pstmt.setString(4, cmVO.getMem_nick());
			pstmt.setString(5, cmVO.getMem_email());
			pstmt.setString(6, cmVO.getMem_hp());
			pstmt.setString(7, cmVO.getMem_birth());
			pstmt.setString(8, cmVO.getMem_profile());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return result;

	}

////////가입시 중복검사 요청 메소드/////////////////
	public boolean duplicateInspection(ChatMemberVO cmVO) {
		boolean isId = false;
		int count = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT NVL(count(mem_name),0) as \"isId\" FROM chat_member ");
			sql.append(" WHERE mem_id = ?");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, cmVO.getMem_id());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt("isId");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbMgr.close(con, pstmt, rs);
		}
		if (count == 1) {
			isId = true;
		} else if (count == 0) {
			isId = false;
		}

		return isId;
	}
	///////////////////로그인처리 메소드////////////////////
	public String login (String mem_id, String mem_pw) {
		String nick = "실패";
		try {
			con = DBConnectionMgr.getConnection();
			cstmt = con.prepareCall("{call proc_chatlogin(?,?,?)}");
			cstmt.setString(1, mem_id);
			cstmt.setString(2, mem_pw);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.execute();
			nick = cstmt.getString(3);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nick;
	}
///////////////로그인한 시간, 로그아웃 한 시간 기록하는 JDBC
	public int inOutLog(String msg) {
		String user_id  = null;
		String protocol = null;
		StringTokenizer st = null;
		if(msg!=null) {
			st = new StringTokenizer(msg, Dprotocol.seperator);
		}
		if(st.hasMoreTokens()) {
			protocol = st.nextToken();
			user_id = st.nextToken();				
		}
		int result = 0;
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append("INSERT INTO chat_inoutlog(protocol,user_id, c_time) ");
			sql.append("VALUES(?,?,TO_CHAR(SYSDATE,'YY/MM/DD HH24:MI:SS')) ");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());	
			
			pstmt.setString(1, protocol);
			pstmt.setString(2, user_id);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public List<Map<String,Object>> getPreInRoomInfo(String user_id){
		List<Map<String, Object>> inRoomList = new Vector<Map<String,Object>>();
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		con = dbMgr.getConnection();
		sql.append("SELECT room_title, room_type, master_id, p_limit, p_limit, room_game, t2.token ");
		sql.append("  FROM chat_room t1, chat_inroom t2 ");
		sql.append(" WHERE t1.token = t2.token ");
		sql.append("   AND t2.mem_id='" + user_id + "'");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				Map<String, Object> rMap = new HashMap<String, Object>();
				rMap.put("token", rs.getString("token"));
				rMap.put("room_title", rs.getString("room_title"));
				rMap.put("room_type", rs.getInt("room_type"));
				rMap.put("room_game", rs.getString("room_game"));
				rMap.put("p_limit", rs.getInt("p_limit"));
				rMap.put("master_id", rs.getString("master_id"));
				inRoomList.add(rMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return inRoomList;
	}

	public Map<String, String> getMemberInfo(String mem_id) {
		Map<String, String> mMap = new HashMap<String, String>();
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		con = dbMgr.getConnection();
		sql.append("SELECT mem_name, mem_nick, mem_email, mem_hp, mem_birth, reg_date, mem_profile ");
		sql.append("FROM chat_member ");
		sql.append("WHERE mem_id='" + mem_id + "'");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()) {
				mMap.put("mem_name", rs.getString("mem_name"));
				mMap.put("mem_nick", rs.getString("mem_nick"));
				mMap.put("mem_email", rs.getString("mem_email"));
				mMap.put("mem_hp", rs.getString("mem_hp"));
				mMap.put("mem_birth", rs.getString("mem_birth"));
				mMap.put("reg_date", rs.getString("reg_date"));
				mMap.put("mem_profile", rs.getString("mem_profile"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return mMap;
	}
	

	public int addFriend(String addFriendUser, String addedFriendUser) {
		int result = 0;
		boolean isAdded = isAlreadyAddFriend(addFriendUser,addedFriendUser);
		if(isAdded) {
			result = 2;
			return result;
		}
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO chat_friend(mem_id,fri_id,fri_regdate) ");
		sql.append("VALUES(?,?,to_char(sysdate,'yyyymmdd'))");
		con = dbMgr.getConnection();
		try {
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, addFriendUser);
			pstmt.setString(2, addedFriendUser);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public boolean isAlreadyAddFriend(String addFriendUser, String addedFriendUser) {
		boolean isAlreadyAdded = false;
		int result = 0;
		
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT count(mem_id) FROM chat_friend ");
		sql.append("WHERE mem_id='" + addFriendUser + "' ");
		sql.append("AND fri_id='" + addedFriendUser + "'");
		con = dbMgr.getConnection();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()) {
				result = rs.getInt("count(mem_id)");
			}		
			
		if(result==1) {
			isAlreadyAdded = true;
		}
		else if(result==0) {
			isAlreadyAdded = false;
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isAlreadyAdded;
	}

	public List<Map<String,String>> getFriendInfo(String mem_id) {
		List<Map<String,String>> friList = new Vector<Map<String,String>>();
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT t1.fri_id as \"fri_id\", t2.mem_name as \"fri_name\" ");
		sql.append("FROM chat_friend t1, chat_member t2 ");
		sql.append("WHERE t1.mem_id = '" + mem_id + "'");
		sql.append("AND t1.fri_id = t2.mem_id");
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while(rs.next()) {
				Map<String,String> fMap = new HashMap<String, String>();
				fMap.put("fri_id", rs.getString("fri_id"));
				fMap.put("fri_name", rs.getString("fri_name"));
				friList.add(fMap);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return friList;
	}
}
