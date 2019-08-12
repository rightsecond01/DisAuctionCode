package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnectionMgr {

	public static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
	//드라이버 연결방식 (thin : 멀티 티어(동시접속)에서 사용하는데 적합, oci)중 thin사용
	public static final String _URL    = "jdbc:oracle:thin:@192.168.0.9:1521:orcl11";
	public static final String _USER   = "scott";
	public static final String _PW     = "tiger";
	static DBConnectionMgr dbMgr = null;
	public static DBConnectionMgr getInstance() {
		if(dbMgr == null) {
			dbMgr = new DBConnectionMgr();
		}
		return dbMgr;
	}
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName(_DRIVER);
			con = DriverManager.getConnection(_URL,_USER,_PW);
		}catch(ClassNotFoundException e) {
			System.out.println("드라이버 클래스를 찾을 수 없습니다.");
		}catch(Exception e) {
			System.out.println("오라클 서버 연결 실패");
		}
		
		return con;
	}
	public void close(Connection con, Statement stmt, ResultSet rs) {
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

	public void close(Connection con, PreparedStatement pstmt) {
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
