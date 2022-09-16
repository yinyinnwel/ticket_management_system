package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connection {
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketmanagementsystem", "root", "");
			if (con != null) {
				//System.out.println("Status: DB " + "connected");
			}

		} catch (Exception e) {

		}
		return con;

	}

	public static void main(String[] args) {
		getConnection();
	}

}
