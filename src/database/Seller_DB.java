package database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Seller;

public class Seller_DB {
	
	static File path;
	

	public static void seller(Seller seller) throws SQLException {
		Connection con = DB_Connection.getConnection();
		String sql = "create table seller(seller_id int,seller_name varchar(100),seller_nrc varchar(100),"
				+ "seller_phone varchar(100),seller_address varchar(100),seller_password varchar(100),seller_image longblob)";
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sql);
		con.close();
	}

	public static Seller getSeller(int seller_id) {

		Seller seller = null;
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from seller where seller_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, seller_id);
			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {
				
                Blob blob = rs.getBlob(7);
               
				seller = new Seller(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), blob.length()+"");

			}
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return seller;

	}

	public static List<Seller> getSellerLists() {

		List<Seller> list = new ArrayList<Seller>();

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from seller ";

			PreparedStatement getstatement = con.prepareStatement(sql);

			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {
				
                Blob blob = rs.getBlob(7);
  
				Seller seller=new Seller(rs.getInt(1),rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), blob.length()+"");

				list.add(seller);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public static void addSeller(Seller seller) {

		try {

			Connection con = DB_Connection.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"insert into seller (seller_id,seller_name,seller_nrc,seller_phone,seller_address,seller_password,seller_image) values (?,?,?,?,?,?,?)");

			ps.setInt(1, seller.getSeller_id());
			ps.setString(2, seller.getSeller_name());
			ps.setString(3, seller.getSeller_nrc());
			ps.setString(4, seller.getSeller_phone());
			ps.setString(5, seller.getSeller_address());
			ps.setString(6, seller.getSeller_password());
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

	public static void delete_Seller(int seller_id) {

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "DELETE FROM seller WHERE seller_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, seller_id);
			getstatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static void updateSeller(Seller seller) throws FileNotFoundException {

		try {
			Connection con = DB_Connection.getConnection();

			String sql = "UPDATE seller SET  seller_name=?,seller_nrc=?,seller_phone=?,seller_address=?,seller_password=?,seller_image=?  where seller_id=?";
			PreparedStatement statement = con.prepareStatement(sql);

			statement.setString(1, seller.getSeller_name());
			statement.setString(2, seller.getSeller_nrc());
			statement.setString(3, seller.getSeller_phone());
			statement.setString(4, seller.getSeller_address());
			statement.setString(5, seller.getSeller_password());
			if (path != null) {
				statement.setBlob(6, new FileInputStream(path));
			} else
				statement.setBlob(6, new FileInputStream(new File("D:\\Java_Project\\Photos\\userlogo.png")));
			
			statement.setInt(7, seller.getSeller_id());
			statement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	

	public static void main(String[] args) throws FileNotFoundException {

//		ArrayList<Seller> sellers=(ArrayList<Seller>) getSellerLists();
//		for(Seller se:sellers) {
//			System.out.println(se.getSeller_id());
//			System.out.println(se.getSeller_name());
//		}

//		Seller newse=getSeller(1);
//		System.out.println(newse.getSeller_name());

//		delete_Seller(2);

//		updateSeller(new Seller(1, "Kyaw Kyaw", "9/MaHaMa(N)123456","091234", "MDY", "123",,"D:\\Java_Project\\Photos\\userlogo.png"));
//		addSeller(new Seller(2, "Aye Aye","9/MaHaMa(N)123456","09123456789","YGN","123",, "D:\\Java_Project\\Photos\\userlogo.png"));

	}


}
