package com.daopractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DAO {
	private Connection con = null;
	private Statement stmt = null;
	private ResultSet rs = null;

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String url = "jdbc:oracle:thin:@192.168.0.9:1521:orcl11";
	private static final String user = "scott";
	private static final String pw = "tiger";

	public DAO() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Info> select() {
		ArrayList<Info> list = new ArrayList<>();
		String sql = "select * from temp";
		try {
			con = DriverManager.getConnection(url, user, pw);
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Info info = new Info();
				info.setEmp_id(rs.getString("EMP_ID"));
				info.setEmp_name(rs.getString("EMP_NAME"));
				info.setSalary(rs.getString("SALARY") + "");
				info.setLev(rs.getString("LEV"));
				list.add(info);
			}

			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, stmt, rs);
		}
		return null;

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

}
