package com.dvd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgrRS;

public class DVDDao {
	
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;	
	DBConnectionMgrRS dbMgrRS = null;
	
	public DVDVO dvdInsert(DVDVO pdVO) {
		dbMgrRS = DBConnectionMgrRS.getInstance();
		DVDVO rdVO = new DVDVO();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		
		try {
			int i = 0;
			sql.append("INSERT INTO dvd(serialnum, genre, mov_class, mov_title, maker, nation, leadingactor, director, mov_date, v_date, damage, r_check, r_fee) ");
			sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(++i,pdVO.getSerialnum());
			pstmt.setString(++i,pdVO.getGenre());
			pstmt.setString(++i,pdVO.getMov_class());
			pstmt.setString(++i,pdVO.getMov_title());
			pstmt.setString(++i,pdVO.getMaker());
			pstmt.setString(++i,pdVO.getNation());
			pstmt.setString(++i,pdVO.getLeadingactor());
			pstmt.setString(++i,pdVO.getDirector());
			pstmt.setString(++i,pdVO.getMov_date());
			pstmt.setString(++i,pdVO.getV_date());
			pstmt.setString(++i,pdVO.getDamage());
			pstmt.setString(++i,pdVO.getR_check());
			pstmt.setInt(++i,pdVO.getR_fee());
			
			cnt = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			System.out.print("오라클문법오류 : ");
			System.out.println(e.getMessage());
		} finally {
			try {
				close(con,pstmt);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		rdVO.setStatus(cnt);
		return rdVO;
		
	}
	public DVDVO dvdUpdate(DVDVO pdVO) {
		dbMgrRS = DBConnectionMgrRS.getInstance();
		DVDVO rdVO = new DVDVO();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		try {
			int i = 0;
			sql.append("UPDATE dvd SET genre = ?, mov_class = ?, mov_title = ?, maker = ?, nation = ?, ");
			sql.append("leadingactor = ?, director = ?, mov_date = ?, v_date = ?, damage = ?, r_check = ?, r_fee = ? ");
			sql.append("WHERE serialnum = ?");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(++i,pdVO.getGenre());
			pstmt.setString(++i,pdVO.getMov_class());
			pstmt.setString(++i,pdVO.getMov_title());
			pstmt.setString(++i,pdVO.getMaker());
			pstmt.setString(++i,pdVO.getNation());
			pstmt.setString(++i,pdVO.getLeadingactor());
			pstmt.setString(++i,pdVO.getDirector());
			pstmt.setString(++i,pdVO.getMov_date());
			pstmt.setString(++i,pdVO.getV_date());
			pstmt.setString(++i,pdVO.getDamage());
			pstmt.setString(++i,pdVO.getR_check());
			pstmt.setInt(++i,5000);
			pstmt.setString(++i,pdVO.getSerialnum());
			
			cnt = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.print("오라클문법오류 : ");
			System.out.println(e.getMessage());
		} catch (Exception e2) {
			System.out.print("기타오류 : ");
			System.out.println(e2.getMessage());
		} finally {
			try {
				close(con,pstmt);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}
		rdVO.setStatus(cnt);
		return rdVO;
		
	}
	public DVDVO dvdDelete(DVDVO pdVO) {
		DVDVO rdVO = new DVDVO();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		
		try {
			sql.append("DELETE FROM dvd WHERE serialnum = ? ");
			con = dbMgrRS.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pdVO.getSerialnum());
			
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
		rdVO.setStatus(cnt);
		return rdVO;
		
	}
	
	
	public List<DVDVO> getTable() {
		List<DVDVO> dvdList = new ArrayList<>();
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuffer sql = new StringBuffer();
		try {
			sql.append("SELECT serialnum, genre, mov_class, mov_title, ");
			sql.append("maker, nation, leadingactor, director, mov_date, v_date, damage, r_check, r_fee ");
			sql.append("FROM dvd");
			con = dbMgrRS.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
			while(rs.next()) {
				DVDVO rdVO = new DVDVO();
				rdVO.setSerialnum(rs.getString("serialnum"));
				rdVO.setGenre(rs.getString("genre"));
				rdVO.setMov_class(rs.getString("mov_class"));
				rdVO.setMov_title(rs.getString("mov_title"));
				rdVO.setMaker(rs.getString("maker"));
				rdVO.setNation(rs.getString("nation"));
				rdVO.setLeadingactor(rs.getString("leadingactor"));
				rdVO.setDirector(rs.getString("director"));
				rdVO.setMov_date(rs.getString("mov_date"));
				rdVO.setV_date(rs.getString("v_date"));
				rdVO.setDamage(rs.getString("damage"));
				rdVO.setR_check(rs.getString("r_check"));
				rdVO.setR_fee(rs.getInt("r_fee"));				
				dvdList.add(rdVO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				close(con,stmt,rs);
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return dvdList;
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
			}catch(SQLException e) {
				
			}
		}
		if (con != null) {
			try {
				con.close();
			}catch(SQLException e) {
				
			}
		}

	}
	
	private void close(Connection con, PreparedStatement pstmt) {
		if(pstmt!=null) {
			try {
				pstmt.close();
				con.close();
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}

}
