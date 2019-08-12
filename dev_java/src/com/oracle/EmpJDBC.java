package com.oracle;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Scanner;

import com.util.DBConnectionMgr;
import com.vo.DeptVO;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EmpJDBC {
	Connection con;
	CallableStatement cstmt;
	OracleCallableStatement ocstmt;
	
	public DeptVO getMy_proc() {
		DeptVO dvo = null;
		DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
		try {
			con = dbMgr.getConnection();
			cstmt = con.prepareCall("{call proc_refcursor1(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			ocstmt = (OracleCallableStatement) cstmt;
			ResultSet rs = ocstmt.getCursor(1);
			while(rs.next()) {
				dvo = new DeptVO();
				dvo.setDeptno(rs.getInt("deptno"));
				dvo.setDname(rs.getString("dname"));
				dvo.setLoc(rs.getString("loc"));
				System.out.println(dvo.getDeptno()+" "+dvo.getDname()+" "+dvo.getLoc());
				dvo = null;
			}
		} catch (Exception e) {
			System.out.println("[[Exception]]" + e.toString());
		} finally {
			try {
				if(cstmt!=null) {
					con.rollback();
					cstmt.close();
				}
				if(ocstmt!=null) {
					ocstmt.close();
				}
				if(con!=null) {
					con.rollback();
					con.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		return dvo;
	}
	
	public String getPrco_empnoUpdate() {
		String msg = null;//프로시저의 OUT속성값 받아서 저장할 변수
		//외부클래스의 객체주입받기위해 작성
		//직접 인스턴스화 하지않음.
		DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
		try {
			//물리적으로 떨어져있는 오라클 서버의 연결통로 확보
			//자바에서는 오토커밋이 디폴트
			con = dbMgr.getConnection();
			
			cstmt = con.prepareCall("{call proc_empnoUpdate(?,?)}");
			System.out.println("사원번호를 입력하세요.");
			Scanner scan = new Scanner(System.in);
			int u_empno = scan.nextInt();
			cstmt.setInt(1, u_empno);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			int result = cstmt.executeUpdate();
			msg = cstmt.getString(2);
			
		} catch(Exception e) {
			//절대로 print 안에 넣지 말것
			//stack 메모리 영역에 쌓여있는 에러메세지를 모두 출력해줌.
			e.printStackTrace();
		} finally {
			try {
				if(cstmt!=null) {
					cstmt.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return msg;
		
	}
	public String getPrco_deptnoUpdate() {
		String msg = null;//프로시저의 OUT속성값 받아서 저장할 변수
		//외부클래스의 객체주입받기위해 작성
		//직접 인스턴스화 하지않음.
		DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
		try {
			//물리적으로 떨어져있는 오라클 서버의 연결통로 확보
			//자바에서는 오토커밋이 디폴트
			con = dbMgr.getConnection();			
			cstmt = con.prepareCall("{call proc_deptnoUpdate(?,?)}");
			System.out.println("부서번호를 입력하세요.");
			Scanner scan = new Scanner(System.in);
			int u_deptno = scan.nextInt();
			cstmt.setInt(1, u_deptno);
			cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
			int result = cstmt.executeUpdate();
			msg = cstmt.getString(2);
			
		} catch(Exception e) {
			//절대로 print 안에 넣지 말것
			//stack 메모리 영역에 쌓여있는 에러메세지를 모두 출력해줌.
			e.printStackTrace();
		} finally {
			try {
				if(cstmt!=null) {
					
					cstmt.close();
				}
				if(con!=null) {
					con.close();
				}
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return msg;
		
	}

	public static void main(String[] args) {
		EmpJDBC inst = new EmpJDBC();		
		/*
		 * String b = inst.getPrco_deptnoUpdate(); System.out.println(b);
		 */
		
		
		  String a = inst.getPrco_empnoUpdate(); 
		  System.out.println(a);
		 
		/* inst.getMy_proc(); */
		
		

	}

}
