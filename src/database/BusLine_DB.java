package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import models.BusLine;

public class BusLine_DB {

	public static void BusTable(BusLine bus) throws SQLException {
	Connection con = DB_Connection.getConnection();
	String sql = "create table busline(bus_id int,bus_name varchar(100),bus_no varchar(100),loc_from varchar(100),"
			+ "loc_to varchar(100),date varchar(100),time varchar(100),seat_qty int,seat_column int,price double)";
	Statement stmt=con.createStatement();
	stmt.executeUpdate(sql);
	con.close();
	}

	public static BusLine getBus(int bus_id) {

//	ResultSet rs = null;
		BusLine bus = null;
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from busline where bus_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, bus_id);
			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {
				bus = new BusLine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						LocalDate.parse(rs.getString(6)), LocalTime.parse(rs.getString(7)), rs.getInt(8), rs.getInt(9), rs.getDouble(10));

			}
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bus;

	}

	public static List<BusLine> getBusLists() {

		List<BusLine> list = new ArrayList<BusLine>();

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from busline ";

			PreparedStatement getstatement = con.prepareStatement(sql);

			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {

				BusLine bus = new BusLine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						LocalDate.parse(rs.getString(6)), LocalTime.parse(rs.getString(7)),  rs.getInt(8), rs.getInt(9), rs.getDouble(10));

				list.add(bus);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public static void addBusLine(BusLine bus) {
		try {
			Connection con = DB_Connection.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"insert into busline (bus_id,bus_name,bus_no,loc_from,loc_to,date,time,seat_qty,seat_col,price) values (?,?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, bus.getBus_id());
			ps.setString(2, bus.getBus_name());
			ps.setString(3, bus.getBus_no());
			ps.setString(4, bus.getLoc_from());
			ps.setString(5, bus.getLoc_to());
			ps.setString(6,bus.getDate().toString());
			ps.setString(7,bus.getTime().toString());
			ps.setInt(8, bus.getSeat_qty());
			ps.setInt(9, bus.getSeat_col());
			ps.setDouble(10, bus.getPrice());

			ps.executeUpdate();

			con.close();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void delete_BusLine(int bus_id) {

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "DELETE FROM busline WHERE bus_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, bus_id);
			getstatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static void updateBusLine(BusLine bus) {

		try {
			Connection con = DB_Connection.getConnection();

			String sql = "UPDATE busline SET  bus_name=?,bus_no=?,loc_from=?,loc_to=?,date=?,time=?,seat_qty=?,seat_col=?,price=?  where bus_id=?";
			PreparedStatement statement = con.prepareStatement(sql);

			statement.setString(1, bus.getBus_name());
			statement.setString(2, bus.getBus_no());
			statement.setString(3, bus.getLoc_from());
			statement.setString(4, bus.getLoc_to());
			statement.setString(5, bus.getDate().toString());
			statement.setString(6, bus.getTime().toString());
			statement.setInt(7, bus.getSeat_qty());
			statement.setInt(8, bus.getSeat_col());
			statement.setDouble(9, bus.getPrice());
			statement.setInt(10, bus.getBus_id());

			statement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		
		
//	ArrayList<BusLine> buses=(ArrayList<BusLine>) getBusLists();
//	for(BusLine bus:buses) {
//		System.out.println(bus.getBus_id());
//		System.out.println(bus.getBus_name());
//	}

//	BusLine newbusBus=getBus(1);
//	System.out.println(newbusBus.getBus_name());

//	delete_BusLine(1);

//	updateBusLine(new BusLine(0, "GI Express", "1I-2090", "MDY", "YGN", 2021-01-05, null,  60, 4, 15000));
//	addBusLine(new BusLine(3, "GI Express", "323", "MGy", "MDY", null, null,  50, 3, 10000));
	}

}
