package com.address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgr;

public class AddressBookDao implements AddressBookInterface {
	
	Connection con = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	DBConnectionMgr dbMgr;

	public AddressBookDao() {
		
	}

	@Override
	public AddressVO getAddressDetail(AddressVO paVO) {
		dbMgr = DBConnectionMgr.getInstance();
		AddressVO raVO = null;
		
		String selected_id = paVO.getId();
		StringBuffer sql = new StringBuffer();
		
				
		try {
			sql.append("SELECT id,name,address,hp,gender,birthday,comments,regdate FROM MKADDRTB where id = '" + selected_id + "'");
			con = dbMgr.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
						
			if(rs.next()) {
				raVO = new AddressVO();
				raVO.setId(rs.getString("id"));
				raVO.setName(rs.getString("name"));
				raVO.setAddress(rs.getString("address"));
				raVO.setHp(rs.getString("hp"));
				raVO.setGender(rs.getString("gender"));
				raVO.setBirthday(rs.getString("birthday"));
				raVO.setComments(rs.getString("comments"));
				raVO.setRegdate(rs.getString("regdate"));				
			}
			return raVO;
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			close(con, stmt, rs);
		}
		return null;
	}

	@Override
	public AddressVO addressInsert(AddressVO paVO) {
		dbMgr = DBConnectionMgr.getInstance();
		AddressVO raVO = new AddressVO();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		
		try {
			sql.append("INSERT INTO MKADDRTB(id,name,address,hp,gender,birthday,comments,regdate)");
			sql.append(" values(?,?,?,?,?,?,?,SYSDATE)");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());			
			pstmt.setString(1, paVO.getId());
			pstmt.setString(2, paVO.getName());
			pstmt.setString(3, paVO.getAddress());
			pstmt.setString(4, paVO.getHp());
			pstmt.setString(5, paVO.getGender());
			pstmt.setString(6, paVO.getBirthday());
			pstmt.setString(7, paVO.getComments());
			
			cnt = pstmt.executeUpdate();			
							
			
		} catch(SQLException se) {
			System.out.println(se.getMessage());
		}finally {
			close(con,pstmt);
		}
		raVO = getAddressDetail(paVO);
		raVO.setStatus(cnt);
		return raVO;
	}

	@Override
	public AddressVO addressUpdate(AddressVO paVO) {
		dbMgr = DBConnectionMgr.getInstance();
		AddressVO raVO = new AddressVO();
		String sql = "UPDATE mkaddrtb SET name=?, address=?, hp=?, gender=?,birthday=?, comments=? WHERE id=? ";
		try {			
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, paVO.getName());
			pstmt.setString(2, paVO.getAddress());
			pstmt.setString(3, paVO.getHp());
			pstmt.setString(4, paVO.getGender());
			pstmt.setString(5, paVO.getBirthday());
			pstmt.setString(6, paVO.getComments());
			pstmt.setString(7, paVO.getId());			
			int cnt = pstmt.executeUpdate();			
			System.out.println(cnt);
			raVO.setStatus(cnt);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close(con,pstmt);
		}
		return raVO;
		
		
	}

	@Override
	public AddressVO addressDelete(AddressVO paVO) {
		dbMgr = DBConnectionMgr.getInstance();
		return null;
	}	
	
	@Override
	public List<AddressVO> getAddress() {
		List<AddressVO> list = new ArrayList<>();
		dbMgr = DBConnectionMgr.getInstance();
		StringBuffer sql = new StringBuffer();
		
		try {
			sql.append("SELECT id,name,address,hp,gender,birthday,comments,regdate FROM MKADDRTB");
			con = dbMgr.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			
						
			while(rs.next()) {
				AddressVO raVO = new AddressVO();
				raVO.setId(rs.getString("id"));
				raVO.setName(rs.getString("name"));
				raVO.setAddress(rs.getString("address"));
				raVO.setHp(rs.getString("hp"));
				raVO.setGender(rs.getString("gender"));
				raVO.setBirthday(rs.getString("birthday"));
				raVO.setComments(rs.getString("comments"));
				raVO.setRegdate(rs.getString("regdate"));
				list.add(raVO);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
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
