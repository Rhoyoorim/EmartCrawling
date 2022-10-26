package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmartMallDAO {
	Connection conn;
	PreparedStatement pstmt;
	final String sql_insert="INSERT INTO EPRODUCT VALUES((SELECT NVL(MAX(EID),0)+1 FROM EMARTMALL),?,?,?,?,?)";
	final String sql_selectAll="SELECT * FROM EPRODUCT,ECATEGORY WHERE EPRODUCT.ECATEGORY=ECATEGORY.CID";
	final String sql_condition="SELECT * FROM EPRODUCT,ECATEGORY WHERE EPRICE > ? AND EPRICE < ? AND EPRODUCT.ECATEGORY=ECATEGORY.CID ORDER BY ENAME ASC";
	final String sql_selectOne="SELECT * FROM EPRODUCT,ECATEGORY WHERE EID=? AND EPRODUCT.ECATEGORY=ECATEGORY.CID";
	final String sql_delete="DELETE FROM EPRODUCT WHERE EID=?";
	final String sql_update="UPDATE EPRODUCT SET REVIEW=? WHERE EID=?";
	final String sql_img="SELECT EIMG FORM EPRODUCT WHERE EID=?";
	final String sql_buyproduct="SELECT EPRODUCT.* ,CATEGORY FROM EPRODUCT,ECATEGORY WHERE EID=? AND EPRODUCT.ECATEGORY=ECATEGORY.CID";
	final String sql_binsert="INSERT INTO BUYLIST VALUES((SELECT NVL(MAX(PID),0)+1 FROM BUYLIST),?,?)";
	
	
	///////////////////////////////////////////////////////////////////////////////
	// 카퉼고리
	
	public EmartMallVO category(EmartMallVO vo) {
		conn=model.JDBCUtill.connect(); // JDBCUtil을 연결
		ResultSet rs=null; // 캡슐화를 위해 의미없는 값을 넣어줌
		try {
			pstmt=conn.prepareStatement(sql_buyproduct);
			pstmt.setInt(1, vo.geteId()); // 쿼리내용의 첫번째 ?에 vo의 eid를 가져오겠다
			rs=pstmt.executeQuery(); // 데이터의 자동적인 변화
			if(rs.next()) { // 만약에 값이 있다면
				EmartMallVO data=new EmartMallVO();
				data.seteCategory(rs.getInt("eCategory"));
				return data; // 보여주기위해 값을 돌려줄것이다
			}
			else {
				return null; // 그게 아니라면 null값 돌려주기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.JDBCUtill.disConnect(pstmt, conn);
		}		
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 이미지
	
	public EmartMallVO imageLoad(EmartMallVO vo) {
		conn=model.JDBCUtill.connect(); // JDBCUtil을 연결
		ResultSet rs=null; // 캡슐화를 위해 의미없는 값을 넣어줌
		try {
			pstmt=conn.prepareStatement(sql_img);
			pstmt.setInt(1, vo.geteId());
			rs=pstmt.executeQuery(); 
			if(rs.next()) { 
				EmartMallVO data=new EmartMallVO();
			
				data.seteId(rs.getInt("eId")); // PK값
				data.seteCategory(rs.getInt("eCategory"));
				data.seteName(rs.getString("eName")); // 출력값으로 이름
				data.setePrice(rs.getInt("sPrice"));
				data.seteReview(rs.getInt("eReview"));
				return data; // 보여주기위해 값을 돌려줄것이다
			}
			else {
				return null; // 그게 아니라면 null값 돌려주기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.JDBCUtill.disConnect(pstmt, conn);
		}		
	}
	

	///////////////////////////////////////////////////////////////////////////////
	// 상품 추가
	
	public boolean insert(EmartMallVO vo) {
		conn = model.JDBCUtill.connect();
		try {
			pstmt = conn.prepareStatement(sql_insert);
			pstmt.setInt(1, vo.geteCategory());
			pstmt.setString(2, vo.geteName());
			pstmt.setInt(3, vo.getePrice());
			pstmt.setInt(5, vo.geteReview());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			model.JDBCUtill.disConnect(pstmt, conn);
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 전체상품 목록
	
	public ArrayList<EmartMallVO> selectAll(EmartMallVO vo) {
		ArrayList<EmartMallVO> datas = new ArrayList<EmartMallVO>();
		conn = model.JDBCUtill.connect();
		ResultSet rs;
		
		try {
				pstmt = conn.prepareStatement(sql_selectAll);
				rs = pstmt.executeQuery();
				while(rs.next()) {					
					EmartMallVO data = new EmartMallVO();
					data.seteId(rs.getInt("eId"));
					data.seteCategory(rs.getInt("eCategory"));
					data.seteName(rs.getString("eName"));
					data.setePrice(rs.getInt("ePrice"));
					data.seteReview(rs.getInt("eReview"));
					data.setCategory(rs.getString("Category"));
					datas.add(data);
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			model.JDBCUtill.disConnect(pstmt, conn);
		}
		return datas;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 상품가격 조건출력
	
	public ArrayList<EmartMallVO> selectCon(EmartMallVO vo) {
		ArrayList<EmartMallVO> datas = new ArrayList<EmartMallVO>();
		conn = model.JDBCUtill.connect();
		ResultSet rs;
		
		try {	
				pstmt = conn.prepareStatement(sql_condition);
				pstmt.setInt(1, vo.getMinPrice());
				pstmt.setInt(2, vo.getMaxPrice());
				rs = pstmt.executeQuery();
				while(rs.next()) {			
					EmartMallVO data = new EmartMallVO();
					data.seteId(rs.getInt("eId"));
					data.seteCategory(rs.getInt("eCategory"));
					data.seteName(rs.getString("eName"));
					data.setePrice(rs.getInt("ePrice"));
					data.seteReview(rs.getInt("eReview"));
					data.setCategory(rs.getString("Category"));
					datas.add(data);
				}
				rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			model.JDBCUtill.disConnect(pstmt, conn);
		}
		return datas;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 상품 검색
	
	public EmartMallVO selectOne(EmartMallVO vo) {
		conn=model.JDBCUtill.connect(); // JDBCUtil을 연결
		ResultSet rs=null; // 캡슐화를 위해 의미없는 값을 넣어줌
		try {
			pstmt=conn.prepareStatement(sql_selectOne);
			pstmt.setInt(1, vo.geteId()); // 쿼리내용의 첫번째 ?에 vo의 eid를 가져오겠다
			rs=pstmt.executeQuery(); // 데이터의 자동적인 변화
			if(rs.next()) { // 만약에 값이 있다면
				EmartMallVO data=new EmartMallVO();
				
				data.seteId(rs.getInt("eId")); // PK값
				data.seteCategory(rs.getInt("eCategory"));
				data.seteName(rs.getString("eName")); // 출력값으로 이름
				data.setePrice(rs.getInt("ePrice"));
				data.seteReview(rs.getInt("eReview"));
				data.seteImg(rs.getString("Eimg"));
				return data; // 보여주기위해 값을 돌려줄것이다
			}
			else {
				return null; // 그게 아니라면 null값 돌려주기
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			model.JDBCUtill.disConnect(pstmt, conn);
		}		
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 상품 삭제
	
	public boolean deleteEmartMall(EmartMallVO vo) {
		conn=model.JDBCUtill.connect();
		try {
			pstmt=conn.prepareStatement(sql_delete);
			pstmt.setInt(1, vo.geteId()); // 1번째 물음표에 eid를 가져오겠다
			int res=pstmt.executeUpdate(); // 데이터의 자동적인 변화
			if(res==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			model.JDBCUtill.disConnect(pstmt, conn);
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 상품 갱신
	
	public boolean updateEmartMall(EmartMallVO vo) {
		conn=model.JDBCUtill.connect();
		try {
			pstmt=conn.prepareStatement(sql_update);
			pstmt.setFloat(1, vo.geteReview()); // 쿼리내용을 실행하기 위해 첫번째 ?에 vo의 review을 가져오겠다
			pstmt.setInt(2, vo.geteId()); // 2번째 ?에 vo의 eid를 가져오겠다
			int res=pstmt.executeUpdate(); // 데이터의 자동적인 변화
			if(res==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			model.JDBCUtill.disConnect(pstmt, conn);
		}
		return true;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	// 구매 목록 출력
	public boolean binsert(PersonVO pvo, EmartMallVO evo) {
        conn = model.JDBCUtill.connect();
        try {
            pstmt = conn.prepareStatement(sql_binsert);
            pstmt.setInt(1, evo.geteId());
            pstmt.setInt(2, pvo.getSid());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
            model.JDBCUtill.disConnect(pstmt, conn);
        }
        return true;
    }

}