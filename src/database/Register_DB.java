package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Register;

public class Register_DB {

	static File path;

	/*
	 * public static void register(Register register) throws SQLException {
	 * Connection con = DB_Connection.getConnection(); String sql =
	 * "create table register(rg_id int,rg_name varchar(100),rg_nrc varchar(100)," +
	 * "rg_phone varchar(100),rg_address varchar(100),rg_password varchar(100),rg_image longblob)"
	 * ; Statement stmt=con.createStatement(); stmt.executeUpdate(sql); con.close();
	 * }
	 */

	public static Register getRegister(int rg_id) {

		Register register = null;
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from register where rg_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, rg_id);
			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {

				Blob blob = rs.getBlob(7);

				register = new Register(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), blob.length() + "");

			}
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return register;

	}

	public static List<Register> getRegisterLists() {

		List<Register> list = new ArrayList<Register>();

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from register ";

			PreparedStatement getstatement = con.prepareStatement(sql);

			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {

				Blob blob = rs.getBlob(7);

				Register register = new Register(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), blob.length() + "");

				list.add(register);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public static void addRegister(Register register) {

		try {
			Connection con = DB_Connection.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"insert into register (rg_id,rg_name,rg_nrc,rg_phone,rg_address,rg_password,rg_image) values (?,?,?,?,?,?,?)");

			ps.setInt(1, register.getRg_id());
			ps.setString(2, register.getRg_name());
			ps.setString(3, register.getRg_nrc());
			ps.setString(4, register.getRg_phone());
			ps.setString(5, register.getRg_address());
			ps.setString(6, register.getRg_password());
			if (path != null) {
				ps.setBlob(7, new FileInputStream(path));
			} else
				ps.setBlob(7, new FileInputStream(new File("D:\\Java_Project\\Photos\\userlogo.png")));

			ps.executeUpdate();

			con.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void delete_Register(int rg_id) {

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "DELETE FROM register WHERE rg_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, rg_id);
			getstatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static void updateRegister(Register register) throws FileNotFoundException {

		try {
			Connection con = DB_Connection.getConnection();

			String sql = "UPDATE register SET  rg_name=?,rg_nrc=?,rg_phone=?,rg_address=?,rg_password=?,rg_image=?  where rg_id=?";
			PreparedStatement statement = con.prepareStatement(sql);

			statement.setString(1, register.getRg_name());
			statement.setString(2, register.getRg_nrc());
			statement.setString(3, register.getRg_phone());
			statement.setString(4, register.getRg_address());
			statement.setString(5, register.getRg_password());
			if (path != null) {
				statement.setBlob(6, new FileInputStream(path));
			} else
				statement.setBlob(6, new FileInputStream(new File("D:\\Java_Project\\Photos\\userlogo.png")));
			statement.setInt(7, register.getRg_id());
			statement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws FileNotFoundException {

//		ArrayList<Register> registers=(ArrayList<Register>) getRegisterLists();
//		for(Register rg:registers) {
//			System.out.println(rg.getRg_id());
//			System.out.println(rg.getRg_name());
//		}

//		Register newrg=getRegister(1);
//		System.out.println(newrg.getName());

//		delete_Register(2);

//		updateRegister(new Register(1, "Admin","D:\\Java_Project\\Photos\\name.png", "1I-2090","09123456", "MDY", "123"));
//		addRegister(new Register(2, "Boss", "D:\\Java_Project\\Photos\\name.png","9/MaHaMa(N)123456","09123456789","mdy","123"));

	}

}
