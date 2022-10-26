package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
*/

public class JDBCUtill {
	
	static final String name = "oracle.jdbc.driver.OracleDriver";
	static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static final String userID = "son";
	static final String userPW = "1234";
	
	public static Connection connect() {
		Connection conn = null;
		try {
			Class.forName(name);
			conn=DriverManager.getConnection(url,userID,userPW);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static void disConnect(PreparedStatement stmt,Connection conn) {
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	public static Document croling(String str)
	{
		final String url = str;
		final String useragent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(0).ignoreHttpErrors(true).userAgent(useragent).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	*/

}
