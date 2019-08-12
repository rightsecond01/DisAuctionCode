package com.discodeaucion_ver01;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.StringTokenizer;

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
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.execute();
			ocstmt = (OracleCallableStatement) cstmt;
			rs = ocstmt.getCursor(3);
			while(rs.next()) {
				nick = rs.getString("mem_nick");
			}
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

}
