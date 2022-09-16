package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import models.AirBooking;
import models.AirLine;

public class AirBooking_DB {
	
	public static AirBooking getAirBooking(int booking_id) {

		AirBooking air = null;
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select * from airbooking where booking_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, booking_id);
			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {
			//	air = new AirBooking(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getDouble(4),rs.getString(5),rs.getString(6),LocalDateTime.parse(rs.getString(7)));

			}
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return air;

	}

	public static List<AirBooking> getAirBookingLists() {

		List<AirBooking> list = new ArrayList<AirBooking>();
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "SELECT ab.booking_id,ab.air_seatno,ab.total_amount,ab.customer_name,ab.customer_phone,ab.booking_datetime,"
					+ "al.air_name,al.air_no,al.loc_from,al.loc_to,al.date,al.time,al.arrival_time,al.air_id FROM airbooking ab JOIN airline al ON ab.air_id=al.air_id";

			PreparedStatement getstatement = con.prepareStatement(sql);

			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {
				AirLine al=new AirLine();
				AirBooking ab=new AirBooking();
				
				al.setAir_id(rs.getInt("al.air_id"));
				al.setAir_name(rs.getString("al.air_name"));
				al.setAir_no(rs.getString("al.air_no"));
				al.setLoc_from(rs.getString("al.loc_from"));
				al.setLoc_to(rs.getString("al.loc_to"));
				al.setDate(LocalDate.parse(rs.getString("al.date")));
				al.setTime(LocalTime.parse(rs.getString("al.time")));
				al.setArrival_time(LocalTime.parse(rs.getString("al.arrival_time")));
				
				ab.setAirLine(al);
				ab.setBooking_id(rs.getInt("ab.booking_id"));
				ab.setAir_seatno(rs.getString("ab.air_seatno"));
				ab.setTotal_amount(rs.getDouble("ab.total_amount"));
				ab.setCustomer_name(rs.getString("ab.customer_name"));
				ab.setCustomer_phone(rs.getString("ab.customer_phone"));
				ab.setBooking_datetime(LocalDateTime.parse(rs.getString("ab.booking_datetime")));
				list.add(ab);
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void addAirBooking(AirBooking air) {
		try {

			Connection con = DB_Connection.getConnection();
			PreparedStatement ps = con.prepareStatement(
					"insert into airbooking (air_id,air_seatno,total_amount,customer_name,customer_phone,booking_datetime) "
					+ "values (?,?,?,?,?,?)");

			
			ps.setInt(1, air.getAirLine().air_id);
			ps.setString(2, air.getAir_seatno());
			ps.setDouble(3, air.getTotal_amount());
			ps.setString(4, air.getCustomer_name());
			ps.setString(5, air.getCustomer_phone());
			ps.setString(6,air.getBooking_datetime().toString());
			ps.executeUpdate();

			con.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static void delete_AirBooking(int booking_id) {

		try {
			Connection con = DB_Connection.getConnection();
			String sql = "DELETE FROM airbooking WHERE booking_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, booking_id);
			getstatement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static void updateAirBooking(AirBooking air) {

		try {
			Connection con = DB_Connection.getConnection();

			String sql = "UPDATE airbooking SET  air_id=?,air_seatno=?,total_amount=?,customer_name=?,customer_phone=?,booking_datetime=?  where booking_id=?";
			PreparedStatement statement = con.prepareStatement(sql);

			//statement.setInt(1, bus.getBus_id());
			statement.setString(2, air.getAir_seatno());
			statement.setDouble(3, air.getTotal_amount());
			statement.setString(4, air.getCustomer_name());
			statement.setString(5, air.getCustomer_phone());
			statement.setString(6, air.getBooking_datetime().toString());
			statement.setInt(7, air.getBooking_id());

			statement.executeUpdate();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public static List<String> getSeatBooking(int air_id) {

		List<String> bookingList=new ArrayList<String>();
		AirBooking air = null;
		try {
			Connection con = DB_Connection.getConnection();
			String sql = "select air_seatno from airbooking where air_id=?";
			PreparedStatement getstatement = con.prepareStatement(sql);
			getstatement.setInt(1, air_id);
			ResultSet rs = getstatement.executeQuery();
			while (rs.next()) {
				
				bookingList.add(rs.getString("air_seatno"));
			}
			con.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return bookingList;

	}

}
