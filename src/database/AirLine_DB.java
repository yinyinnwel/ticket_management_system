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

import models.AirLine;



public class AirLine_DB {
	
	public static void AirLineTable(AirLine airLine) throws SQLException {
		Connection con = DB_Connection.getConnection();
		String sql = "create table airline(air_id int,air_name varchar(100),air_no varchar(100),loc_from varchar(100),"
				+ "loc_to varchar(100),date varchar(100),time varchar(100),"
				+ "arrival_time varchar(100),seat_qty int,seat_column int,price double)";
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sql);
		con.close();
	}
	
	
	public static AirLine getAirLline(int air_id) {

		AirLine airLine = null;
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from airline where air_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, air_id);
			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {

				airLine = new AirLine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						LocalDate.parse(rs.getString(6)), LocalTime.parse(rs.getString(7)), LocalTime.parse(rs.getString(8)),rs.getInt(9), rs.getInt(10), rs.getDouble(11));

			}
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return airLine;

	}

	public static List<AirLine> getAirLineLists() {
		List<AirLine> list = new ArrayList<AirLine>();

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from airline ";

			PreparedStatement getstatement = con.prepareStatement(sql);

			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {

				AirLine airLine = new AirLine(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						LocalDate.parse(rs.getString(6)), LocalTime.parse(rs.getString(7)), LocalTime.parse(rs.getString(8)),rs.getInt(9), rs.getInt(10), rs.getDouble(11));

				list.add(airLine);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

	public static void addAirLine(AirLine airLine) {

		try {

			Connection con = DB_Connection.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"insert into airline (air_id,air_name,air_no,loc_from,loc_to,date,time,arrival_time,seat_qty,seat_column,price) "
					+ "values (?,?,?,?,?,?,?,?,?,?,?)");

			ps.setInt(1, airLine.getAir_id());
			ps.setString(2, airLine.getAir_name());
			ps.setString(3, airLine.getAir_no());
			ps.setString(4, airLine.getLoc_from());
			ps.setString(5, airLine.getLoc_to());
			ps.setString(6, airLine.getDate().toString());
			ps.setString(7, airLine.getTime().toString());
			ps.setString(8, airLine.getArrival_time().toString());
			ps.setInt(9, airLine.getSeat_qty());
			ps.setInt(10, airLine.getSeat_col());
			ps.setDouble(11, airLine.getPrice());

			ps.executeUpdate();

			con.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void delete_AirLine(int air_id) {

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "DELETE FROM airline WHERE air_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, air_id);
			getstatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static void updateAirLine(AirLine airLine) {

		try {
			Connection con = DB_Connection.getConnection();

			String sql = "UPDATE airline SET  air_name=?,air_no=?,loc_from=?,loc_to=?,date=?,time=?,"
					+ "arrival_time=?,seat_qty=?,seat_column=?,price=? where air_id=?";
			PreparedStatement statement = con.prepareStatement(sql);

			statement.setString(1, airLine.getAir_name());
			statement.setString(2, airLine.getAir_no());
			statement.setString(3, airLine.getLoc_from());
			statement.setString(4, airLine.getLoc_to());
			statement.setString(5, airLine.getDate().toString());
			statement.setString(6, airLine.getTime().toString());
			statement.setString(7, airLine.getArrival_time().toString());
			statement.setInt(8, airLine.getSeat_qty());
			statement.setInt(9, airLine.getSeat_col());
			statement.setDouble(10, airLine.getPrice());
			statement.setInt(11, airLine.getAir_id());
			statement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
//		ArrayList<AirLine> airs=(ArrayList<AirLine>) getAirLineLists();
//		for(AirLine air:airs) {
//			System.out.println(air.getAir_id());
//			System.out.println(air.getAir_name());
//		}

//		AirLine newair=getAirLline(3);
//		System.out.println(newair.getAir_name());

//		delete_AirLine(4);

//		updateAirLine(new AirLine(5, "GI Express", "1I-2090", "MDY", "YGN", new Date(), new Date(),  new Date(),  40, 50, 35000));
//		addAirLine(new AirLine(5, "SSE", "323", "YGN", "MDY", null, null, null, 28, 20, 18000));
	}


	

}
