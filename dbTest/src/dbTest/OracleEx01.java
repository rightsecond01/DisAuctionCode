package dbTest;

import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleEx01 {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.0.9:1521:orcl11";
		String user = "scott";
		String password = "tiger";
		try {
			Class.forName(driver);
			System.out.println("jabc driver 로딩 성공");
			DriverManager.getConnection(url, user, password);
			System.out.println("오라클 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("jabc driver 로딩 실패");
		} catch(SQLException e) {
			System.out.println("오라클 연결 실패");
		}

	}//이클립스-오라클 연결테스트

}
