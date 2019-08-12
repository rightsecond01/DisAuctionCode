package com.ch2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.address.AddressVO;
import com.google.gson.Gson;
import com.util.DBConnectionMgr;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class JsonCreate {
	
	Connection con = null;
	CallableStatement cstmt = null;
	OracleCallableStatement ocstmt = null;
	ResultSet rs = null;
	DBConnectionMgr dbMgr = null;
	public JsonCreate() {
		dbMgr = DBConnectionMgr.getInstance();
		try {
			con = dbMgr.getConnection();
			cstmt = con.prepareCall("{call proc_mkaddrtb(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			ocstmt = (OracleCallableStatement)cstmt;
			ResultSet cursor = ocstmt.getCursor(1);
			List<AddressVO> list = new ArrayList<>();
			AddressVO aVO = null;
			while(cursor.next()) {
				aVO = new AddressVO();
				aVO.setId(cursor.getString("id"));
				aVO.setName(cursor.getString("name"));
				aVO.setAddress(cursor.getString("address"));
				aVO.setHp(cursor.getString("hp"));
				list.add(aVO);
			}
			Gson g = new Gson();
			String jsonMember = g.toJson(list);
			System.out.println(jsonMember);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static void main(String[] args) {
		new JsonCreate();
	}

}
