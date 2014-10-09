package news;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Db {

	Connection con;
	Statement st;
	ResultSet rs;

	public Db() {
		openDb();
	}

	public void openDb() {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			// set this to a MS Access DB you have on your machine
			String filename = "data\\db.mdb";
//			String filename = "F:\\bigdata_taobaov1.0_writedb_csv\\data\\db.mdb";			
			String database = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
			database += filename.trim() + ";DriverID=22;READONLY=true}";
			// now we can get the connection from the DriverManager
			con = DriverManager.getConnection(database, "", "");
			// select(con);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
		}
	}

	public void closeDb() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert2db(Page page) {
		Map<String, Shop> data = page.getShopMap();
		Iterator it = data.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Shop> pair = (Entry<String, Shop>) it.next();
			insert(pair.getValue());
		}
		closeDb();
	}

	public void insert(String title, String url, String text) {
		String sql = "insert into news (title, url, news) values (?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, url);
			ps.setString(3, text);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert(Shop shop) {
		String sql = "insert into taobao (sort,shop,home,rank,sales) values (?,?,?,?,?)";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, shop.getId());
			ps.setString(2, shop.getShopName());
			ps.setString(3, shop.getHomePage());
			ps.setString(4, shop.getRank());
			ps.setInt(5, shop.getSales());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void select(Connection con) {
		try {
			st = con.createStatement();
			rs = st.executeQuery("select * from taobao");
			while (rs.next()) {
				System.out.println(rs.getObject(1));
			}
		} catch (SQLException ex) {
			System.out.println(ex.toString());
		}
	}
}
