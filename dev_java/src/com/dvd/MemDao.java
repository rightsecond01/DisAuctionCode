package com.dvd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgrRS;

public class MemDao {
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	DBConnectionMgrRS dbMgrRS;

	public MemVO memInsert(MemVO pmVO) {
		MemVO rmVO = new MemVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		try {
			sql.append("insert into MEM(memid,memname,tel,phonenum,zipcode,address,resistdate,mempw) ");
			sql.append("values(?,?,?,?,?,?,SYSDATE,?) ");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, pmVO.getMemid());
			pstmt.setString(2, pmVO.getMemname());
			pstmt.setString(3, pmVO.getTel());
			pstmt.setString(4, pmVO.getPhonenum());
			pstmt.setString(5, pmVO.getZipcode());
			pstmt.setString(6, pmVO.getAddress());
			pstmt.setString(7, pmVO.getMempw());

			cnt = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		rmVO.setStatus(cnt);
		return rmVO;

	}

	public MemVO memUpdate(MemVO pmVO) {
		MemVO rmVO = new MemVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;		
		try {			
			sql.append("UPDATE mem SET memname=?, tel=?, phonenum=?, zipcode=?, address=?, mempw=? ");
			sql.append("WHERE memid=? ");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pmVO.getMemname());
			pstmt.setString(2, pmVO.getTel());
			pstmt.setString(3, pmVO.getPhonenum());
			pstmt.setString(4, pmVO.getZipcode());
			pstmt.setString(5, pmVO.getAddress());
			pstmt.setString(6, pmVO.getMempw());
			pstmt.setString(7, pmVO.getMemid());
			
			cnt = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				close(con,pstmt);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		rmVO.setStatus(cnt);
		return rmVO;
	}
	
	public MemVO memDelete(MemVO pmVO) {
		MemVO rmVO = new MemVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		try {
			sql.append("DELETE FROM mem WHERE memid = ? ");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pmVO.getMemid());
			
			cnt = pstmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				close(con,pstmt);
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		rmVO.setStatus(cnt);
		return rmVO;
	}

	public List<MemVO> getTable() {
		List<MemVO> memList = new ArrayList<>();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT memid,memname,tel,phonenum,zipcode,address,resistdate,mempw FROM mem");
			con = dbMgrRS.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());

			while (rs.next()) {
				MemVO rmVO = new MemVO();
				rmVO.setMemid(rs.getString("memid"));
				rmVO.setMemname(rs.getString("memname"));
				rmVO.setTel(rs.getString("tel"));
				rmVO.setPhonenum(rs.getString("phonenum"));
				rmVO.setZipcode(rs.getString("zipcode"));
				rmVO.setAddress(rs.getString("address"));
				rmVO.setResistdate(rs.getString("resistdate"));
				rmVO.setMempw(rs.getString("mempw"));
				memList.add(rmVO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				close(con, stmt, rs);
			} catch (Exception e2) {
				e2.getMessage();
			}
		}
		return memList;

	}

	////////////////////////////// 자원 반납 메소드 정의 //////////////////////////////////////////////
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
