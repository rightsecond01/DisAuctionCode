package com.dvd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConnectionMgrRS;

public class RendetailDao {

	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	DBConnectionMgrRS dbMgrRS;
	
	public RendetailVO renDetailInsert(List<RendetailVO> prdtVO) {
		dbMgrRS = DBConnectionMgrRS.getInstance();
		RendetailVO rrdtVO = new RendetailVO();
		StringBuffer sql = new StringBuffer();
		int cnt = -1;
		
		for(int i = 0; i<prdtVO.size();i++) {
			try {
				RendetailVO rdtVO = prdtVO.get(i);				
				sql.append("INSERT INTO rendetail(r_num, r_detailnum, serialnum) ");
				sql.append("values(?, ?, ?) ");
				con = dbMgrRS.getConnection();
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, rdtVO.getR_num());
				pstmt.setString(2, rdtVO.getR_detailnum());
				pstmt.setString(3, rdtVO.getSerialnum());
				cnt = pstmt.executeUpdate();
				sql.setLength(0);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					close(con, pstmt);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		
		rrdtVO.setStatus(cnt);
		return rrdtVO;
	}

	public RendetailVO renDetailUpdate(List<RendetailVO> prdtVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public RendetailVO renDetailDelete(List<RendetailVO> prdtVO) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<RendetailVO> getDetailTable(String rnum) {
		List<RendetailVO> rdtList = new ArrayList<>();
		String index_r_num = null;
		index_r_num = rnum;
		dbMgrRS = DBConnectionMgrRS.getInstance();
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT t3.r_num, t3.r_detailnum, t3.serialnum, t1.mov_title, ");
			sql.append("t1.r_fee, t2.memid, TO_DATE(t2.duedate,'YYYYMMDD') as duedate, ");
			//미반납상태인경우 반납일을 미반납으로, 아닌경우 숫자타입을 날짜타입으로 변환.
			sql.append("DECODE(t2.returndate,'미반납','미반납', TO_DATE(t2.returndate,'YYYYMMDD')) as returndate, ");
			//////////////////////////연체료 DECODE문 시작////////////////////////
			sql.append("DECODE(t2.returndate,'미반납', DECODE(sign(TO_DATE(TO_CHAR(sysdate,'YYYYMMDD')) - TO_DATE(t2.duedate,'YYYYMMDD')),1 ");
			sql.append("                                   ,(TO_DATE(TO_CHAR(sysdate,'YYYYMMDD')) - TO_DATE(t2.duedate,'YYYYMMDD')) * 100 ");
			sql.append("                          ,0)");
			sql.append("       ,DECODE(sign(TO_DATE(t2.returndate,'YYYYMMDD')-TO_DATE(t2.duedate,'YYYYMMDD')),1" );
			sql.append("                         ,(TO_DATE(t2.returndate,'YYYYMMDD')-TO_DATE(t2.duedate,'YYYYMMDD'))*100" );
			sql.append("       ,0)");
			sql.append("      ) as \"연체료\" ");
			/////////////////////////연체료 DECODE문 끝///////////////////////////////////////
			sql.append("FROM dvd t1, rental t2, rendetail t3, mem t4 ");
			sql.append("WHERE t2.r_num = t3.r_num ");
			sql.append("AND t3.serialnum = t1.serialnum ");
			sql.append("AND t2.memid = t4.memid ");
			sql.append("AND t2.r_num = ");
			sql.append(index_r_num);
			sql.append(" ORDER BY t3.r_num, t3.r_detailnum");

			con = dbMgrRS.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				RendetailVO rrdVO = new RendetailVO();
				rrdVO.setR_num(rs.getString("r_num"));
				rrdVO.setR_detailnum(rs.getString("r_detailnum"));
				rrdVO.setSerialnum(rs.getString("serialnum"));
				rrdVO.setMov_title(rs.getString("mov_title"));
				rrdVO.setR_fee(rs.getInt("r_fee"));
				rrdVO.setMemid(rs.getString("memid"));
				rrdVO.setDuedate(rs.getString("duedate"));
				rrdVO.setReturndate(rs.getString("returndate"));
				rrdVO.setLatefee(rs.getInt("연체료"));
				rdtList.add(rrdVO);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			close(con, stmt, rs);
		}

		return rdtList;
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

	private void close(Connection con, PreparedStatement pstmt) {
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
