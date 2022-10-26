package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PersonDAO {
	final String sql_idcheck = "SELECT MID FROM USERDATA";
	final String sql_insert = "INSERT INTO USERDATA VALUES((SELECT NVL(MAX(SID),0)+1 FROM USERDATA),?,?,?)";
	final String sql_update = "UPDATE USERDATA SET MPW = ? WHERE SID = ?";
	final String sql_delete = "DELETE FROM USERDATA WHERE SID = ?";
	final String sql_check = "SELECT COUNT(*) FROM USERDATA";
	final String sql_logincheck = "SELECT * FROM USERDATA WHERE MID = ?";
	final String sql_loginCurret = "SELECT * FROM USERDATA WHERE MID = ?";
	final String sql_buylist ="SELECT E.* FROM EPRODUCT E,BUYLIST B where B.EID = E.EID AND B.SID = ?";
	Connection conn = null;
	PreparedStatement stmt = null;

	public ArrayList<EmartMallVO> buylist_user(PersonVO vo) {
		conn =	JDBCUtill.connect();
		ArrayList<EmartMallVO> data = new ArrayList<EmartMallVO>();
		try {
			stmt = conn.prepareStatement(sql_buylist);
			stmt.setInt(1, vo.getSid()); 
			ResultSet rs = stmt.executeQuery();
//			System.out.println(vo.getSid());
			while(rs.next()) {
				//일단 다담아보았습니다 알아서 꺼내쓰세여
				//지우셔도 됩니다~ :D
				//				CREATE TABLE BUYLIST(
				//						PID INT PRIMARY KEY,
				//						EID INT,
				//						SID INT
				//					);
				EmartMallVO evo2 = new EmartMallVO();
				evo2.seteId(rs.getInt("EID"));
                evo2.seteName(rs.getString("ENAME"));
                evo2.setePrice(rs.getInt("EPRICE"));
                evo2.seteImg(rs.getString("EIMG"));
//                System.out.println(evo2.geteId());
				data.add(evo2);
//				System.out.println("로그1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
				return data;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return null;
	}
	public boolean idcheck_user(PersonVO vo) {
		//아이디 중복 체크
		//중복이면 참 아니면 거짓
		conn =	JDBCUtill.connect();
		try {
			stmt = conn.prepareStatement(sql_idcheck);
			// stmt.executeUpdate();
			ResultSet rs = stmt.executeQuery();
			stmt.executeQuery();
			while(rs.next()) {

				if(rs.getString("MID").equals(vo.getUid())) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
			} catch (Exception e2) {
			}
		}
		return false;
	}

	public boolean insert_user(PersonVO vo) {
		//회원 가입
		conn =	JDBCUtill.connect();
		try {
			stmt = conn.prepareStatement(sql_insert);
			stmt.setString(1, vo.getName());				//유저 이름
			stmt.setString(2, vo.getUid());					//유저 아이디
			stmt.setString(3, vo.getUpw());				//유저 비밀번호

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
				return true;
			} catch (Exception e2) {
			}
		}
		return false;
	}
	public boolean update_user(PersonVO vo) {
		//회원 비밀번호 변경
		conn =	JDBCUtill.connect();
		try {
			stmt = conn.prepareStatement(sql_update);
			stmt.setString(1, vo.getUpw());				//유저 비밀번호
			stmt.setInt(2, vo.getSid());

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
				return true;
			} catch (Exception e2) {
			}
		}
		return false;
	}
	public boolean delete_user(PersonVO vo) {
		//회원 탈퇴
		conn =	JDBCUtill.connect();
		try {
			stmt = conn.prepareStatement(sql_delete);
			stmt.setInt(1, vo.getSid());				//유저 PK

			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
				return true;
			} catch (Exception e2) {
			}
		}
		return false;
	}
	public PersonVO loginCurret_user(PersonVO vo) {
		//회원 로그인 성공
		//id값을 비교하여 해당 정보 전달
		conn =	JDBCUtill.connect();
		PersonVO data = null;
		try {
			stmt = conn.prepareStatement(sql_loginCurret);
			stmt.setString(1, vo.getUid());				//유저 ID
			stmt.executeUpdate();
			ResultSet rs = stmt.executeQuery();
			data = new PersonVO();
			while (rs.next()) {
				if(vo.getUid().equals(rs.getString("MID"))) {
					//System.out.println("DATA값 넣기");
					data.setSid(rs.getInt("SID"));
					data.setName(rs.getString("NAME"));
					data.setUid(rs.getString("MID"));
					data.setUpw(rs.getString("MPW"));
					break;
				}
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
//				return data;
			} catch (Exception e2) {
			}
		}
		return null;
	}


	public boolean logincheck_user(PersonVO vo) {
		conn =    JDBCUtill.connect();
		try {
			stmt = conn.prepareStatement(sql_logincheck);
			stmt.setString(1, vo.getUid());
			stmt.executeUpdate();
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				//중복검사에 없으면 참 아니면 거짓
				if(rs.getString("MPW").equals(vo.getUpw())) {
					//System.out.println("       LOG:로그인 성공");
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				JDBCUtill.disConnect(stmt, conn);
			} catch (Exception e2) {
			}
		}
		return false;
	}
}