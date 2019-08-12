package com.network23;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		
		if(isId) {//isId가 true => 같은아이디가 존재, 입력성공 =1,입력실패=0,중복아이디=2
			result = 2;
			return result;
		}
		
		try {
			sql.append("INSERT INTO chat_member");
			sql.append("(mem_num,mem_id,mem_pw,mem_name,mem_nick,mem_email,mem_hp,mem_birth,reg_date,mem_profile) ");
			sql.append("VALUES(seq_cmember_num.nextval,?,?,?,?,?,?,?,to_char(sysdate,'yyyymmdd'),?)");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			
			pstmt.setString(1, cmVO.getMem_id() );
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
	public String login (String mem_id, String mem_pw) {
		String nick = null;
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
			
			while(rs.next()) {
				count = rs.getInt("isId");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(con,pstmt,rs);
		}
		if(count==1) {
			isId = true;
		}else if(count==0) {
			isId = false;
		}
		
		return isId;
	}

	
	private void close(Connection con, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {

			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {

			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {

			}
		}

	}

	private void close(Connection con, PreparedStatement pstmt) {
		if (pstmt != null) {
			try {
				pstmt.close();
				con.close();

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

}
