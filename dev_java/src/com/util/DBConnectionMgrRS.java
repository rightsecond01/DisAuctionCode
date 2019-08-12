package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionMgrRS {
	public static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String _URL    = "jdbc:oracle:thin:@192.168.0.211:1521:orcl11";
	public static final String _USER   = "scott";
	public static final String _PW     = "tiger";
	static DBConnectionMgrRS dbMgr = null;
	public static DBConnectionMgrRS getInstance() {
		if(dbMgr == null) {
			dbMgr = new DBConnectionMgrRS();
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

}
