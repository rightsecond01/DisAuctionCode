package com.dvd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class RightSecondServer {
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	static final String _DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String _URL = "jdbc:oracle:thin:@192.168.0.20:1521:orcl11";
	static final String _USER = "rightsecond";
	static final String _PW= "rstiger";
	
	

}
