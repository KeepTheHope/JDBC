package ghy.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	public static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String DBURL = "jdbc:oracle:thin:@localhost:1521:mldn";
	public static final String USER = "scott";
	public static final String PASSWORD = "tiger";
//	private static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
//	private static final String DBURL = "jdbc:mysql://localhost:3306/mldn";
//	private static final String USER = "root";
//	private static final String PASSWORD = "mysqladmin";
	private static ThreadLocal<Connection> threadlocal = new ThreadLocal<Connection> ();
	private static Connection connectionDatabase() {
		Connection conn = null;
		try {
			Class.forName(DBDRIVER);
			conn = DriverManager.getConnection(DBURL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	public static Connection getConnection() {
		Connection conn = threadlocal.get();
		if(conn == null) {
			conn = connectionDatabase();
			threadlocal.set(conn);
		}
		return conn;
		
	}
	public static void close() {
		Connection conn = threadlocal.get();
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			threadlocal.remove();
		}
	}

}

