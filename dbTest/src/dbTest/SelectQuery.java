package dbTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectQuery {
	public static void main(String[] args) {
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@192.168.0.9:1521:orcl11";
		String user = "scott";
		String pw = "tiger";
		String SQL = "SELECT*FROM temp";
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,pw);
			stmt = con.createStatement();
			rs = stmt.executeQuery(SQL);
			while(rs.next()) {
				String emp_id = rs.getString("EMP_ID");
				String emp_name = rs.getString("EMP_NAME");
				String salary = rs.getString("SALARY");
				
				System.out.println("id : " + emp_id +", name : " + emp_name + ", 연봉 : " + salary);
				System.out.println("");
			}
		}catch (SQLException e) {
			System.out.println("SQL Error:" + e.getMessage());
		}catch(ClassNotFoundException e1) {
			System.out.println("[JDBC Connector Driver 오류:" + e1.getMessage() + "]");
		} finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
