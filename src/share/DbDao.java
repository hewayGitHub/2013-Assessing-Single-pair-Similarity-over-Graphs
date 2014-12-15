package share;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//only one connection and one Statement, all operations occupant conn,
//but only query uses stmt
//after user, should be cleared
public class DbDao {

	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static String url = "jdbc:oracle:thin:@10.77.30.50:1521:simdb";
	public static String username = "simdb";
	public static String pass = "simdb";

	private Connection conn;
	private Statement stmt;

	public DbDao(String driver, String url, String username, String pass)
			throws Exception {
		Class.forName(driver);
		conn = DriverManager.getConnection(url, username, pass);
		stmt = conn.createStatement();
	}

	// return -1 means failed
	public int insert(String sql) throws Exception {
		Statement stmtTemp = this.conn.createStatement();
		int temp = stmtTemp.executeUpdate(sql);
		stmtTemp.close();
		return temp;
	}

	public int delete(String sql) throws Exception {
		Statement stmtTemp = this.conn.createStatement();
		int temp = stmtTemp.executeUpdate(sql);
		stmtTemp.close();
		return temp;
	}

	public int update(String sql) throws Exception {
		Statement stmtTemp = this.conn.createStatement();
		int temp = stmtTemp.executeUpdate(sql);
		stmtTemp.close();
		return temp;
	}

	public ResultSet query(String sql) throws Exception {
		return stmt.executeQuery(sql);
	}

	public void clear() throws Exception {
		if (this.stmt != null)
			this.stmt.close();
		if (this.conn != null)
			this.conn.close();
	}

	// example
	public static void main(String[] args) throws Exception {
		DbDao dd = null;
		ResultSet rs = null;
		try {
			dd = new DbDao(DbDao.driver, DbDao.url, DbDao.username, DbDao.pass);
			rs = dd.query("sql sentence");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (dd != null)
				dd.clear();
		}
	}
}