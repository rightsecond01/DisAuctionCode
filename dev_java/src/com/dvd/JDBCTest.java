package com.dvd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCTest {

	public static void main(String[] args) {
		StringBuilder sb_sql = new StringBuilder();
		try {
			sb_sql.append("SELECT mov_title,maker,genre");
			sb_sql.append("FROM dvd");
			Class.forName(RightSecondServer._DRIVER);
			Connection con = DriverManager.getConnection(RightSecondServer._URL, 
					                                     RightSecondServer._USER,                           
					                                     RightSecondServer._PW);
			PreparedStatement pstmt = con.prepareStatement(sb_sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
