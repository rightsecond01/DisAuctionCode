package com.dvd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgrRS;

public class RentalDao {
	
	private Connection con = null;
	private Statement stmt = null;
	private CallableStatement cstmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	DBConnectionMgrRS dbMgrRS;

	public RentalVO rentalInsert(RentalVO prVO) {
		RentalVO rrVO = new RentalVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		try {
			
			sql.append("INSERT INTO rental(r_num, r_date, returndate, duedate, memid) ");
			sql.append("values(?, to_char(sysdate,'yyyymmdd'), ?, to_char(sysdate+2,'yyyymmdd'), ?) ");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, prVO.getR_num());
			pstmt.setString(2, prVO.getReturndate());
			pstmt.setString(3, prVO.getMemid());
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(con,pstmt);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		rrVO.setStatus(cnt);
		return rrVO;

	}

	public RentalVO rentalUpdate(RentalVO prVO) {
		RentalVO rrVO = new RentalVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		try {
			sql.append("UPDATE rental SET returndate = ? ");
			sql.append("WHERE r_num = ? ");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, prVO.getReturndate());
			pstmt.setString(2, prVO.getR_num());
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		rrVO.setStatus(cnt);
		return rrVO;

	}

	public RentalVO rentalDelete(RentalVO prVO) {
		RentalVO rrVO = new RentalVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();		
		try {
			con = dbMgrRS.getConnection();
			cstmt = con.prepareCall("call proc_rendelete(?)");
			cstmt.setString(1,prVO.getR_num());
			boolean result = cstmt.execute();
			System.out.println(result);
			if(result==false) {
				rrVO.setStatus(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rrVO;

	}

	public List<RentalVO> getTable() {
		List<RentalVO> renList = new ArrayList<>();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT t1.r_num as r_num, max(t1.r_date) as r_date, max(t1.returndate) as returndate, max(t1.duedate) as duedate, max(t1.memid) as memid ");
			////////////////////////대여건의 연체료 계산 sql문 시작 //////////////////////////
			sql.append(",sum(DECODE(returndate,'미반납', DECODE(sign(TO_DATE(TO_CHAR(sysdate,'YYYYMMDD')) - TO_DATE(t1.duedate,'YYYYMMDD')),1 ");
			sql.append("                                     ,(TO_DATE(TO_CHAR(sysdate,'YYYYMMDD')) - TO_DATE(t1.duedate,'YYYYMMDD')) * 100 ");
			sql.append("                                ,0) ");
			sql.append("       ,DECODE(sign(TO_DATE(t1.returndate,'YYYYMMDD')-TO_DATE(t1.duedate,'YYYYMMDD')),1 ");
			sql.append("                         ,(TO_DATE(t1.returndate,'YYYYMMDD')-TO_DATE(t1.duedate,'YYYYMMDD'))*100 ");
			sql.append("       ,0) ");
			sql.append("      ) ");
			sql.append("    ) as latefee ");
			//////////////////////연체료 계산 끝///////////////////////////////////////
			sql.append("FROM rental t1, rendetail t2 ");
			sql.append("WHERE t1.r_num = t2.r_num ");
			sql.append("group by t1.r_num ");
			
			con = dbMgrRS.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				RentalVO rrVO = new RentalVO();
				rrVO.setR_num(rs.getString("r_num"));
				rrVO.setR_date(rs.getString("r_date"));
				rrVO.setReturndate(rs.getString("returndate"));
				rrVO.setLatefee(rs.getInt("latefee"));
				rrVO.setDuedate(rs.getString("duedate"));
				rrVO.setMemid(rs.getString("memid"));				
				renList.add(rrVO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				close(con,stmt,rs);
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
		return renList;
	}

//////////////////////////////자원 반납 메소드 정의//////////////////////////////////////////////
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
