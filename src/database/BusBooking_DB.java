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

import models.BusBooking;
import models.BusLine;

public class BusBooking_DB {
	
	public static BusBooking getBusBooking(int booking_id) {

			BusBooking bus = null;
			try {
				Connection con = DB_Connection.getConnection();
				String sql = "select * from busbooking where booking_id=?";
				PreparedStatement getstatement = con.prepareStatement(sql);
				getstatement.setInt(1, booking_id);
				ResultSet rs = getstatement.executeQuery();
				while (rs.next()) {
				//	bus = new BusBooking(rs.getInt(1), rs.getInt(2),rs.getString(3), rs.getDouble(4),rs.getString(5),rs.getString(6),LocalDateTime.parse(rs.getString(7)));

				}
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			return bus;

		}

		public static List<BusBooking> getBusBookingLists() {

			List<BusBooking> list = new ArrayList<BusBooking>();
			try {
				Connection con = DB_Connection.getConnection();
				String sql = "SELECT bb.booking_id,bb.bus_seatno,bb.total_amount,bb.customer_name,bb.customer_phone,bb.booking_datetime,bl.bus_name,bl.bus_no,bl.loc_from,bl.loc_to,bl.date,bl.time,bl.bus_id FROM busbooking bb JOIN busline bl ON bb.bus_id=bl.bus_id";

				PreparedStatement getstatement = con.prepareStatement(sql);

				ResultSet rs = getstatement.executeQuery();
				while (rs.next()) {
					BusLine bl=new BusLine();
					BusBooking bb=new BusBooking();
					
					bl.setBus_id(rs.getInt("bl.bus_id"));
					bl.setBus_name(rs.getString("bl.bus_name"));
					bl.setBus_no(rs.getString("bl.bus_no"));
					bl.setLoc_from(rs.getString("bl.loc_from"));
					bl.setLoc_to(rs.getString("bl.loc_to"));
					bl.setDate(LocalDate.parse(rs.getString("bl.date")));
					bl.setTime(LocalTime.parse(rs.getString("bl.time")));
					
					bb.setBusLine(bl);
					bb.setBooking_id(rs.getInt("bb.booking_id"));
					bb.setBus_seatno(rs.getString("bb.bus_seatno"));
					bb.setTotal_amount(rs.getDouble("bb.total_amount"));
					bb.setCustomer_name(rs.getString("bb.customer_name"));
					bb.setCustomer_phone(rs.getString("bb.customer_phone"));
					bb.setBooking_datetime(LocalDateTime.parse(rs.getString("bb.booking_datetime")));
					list.add(bb);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
		}

		public static void addBusBooking(BusBooking bus) {
			try {

				Connection con = DB_Connection.getConnection();
				PreparedStatement ps = con.prepareStatement(
						"insert into busbooking (bus_id,bus_seatno,total_amount,customer_name,customer_phone,booking_datetime) "
						+ "values (?,?,?,?,?,?)");

				
				ps.setInt(1, bus.getBusLine().bus_id);
				ps.setString(2, bus.getBus_seatno());
				ps.setDouble(3, bus.getTotal_amount());
				ps.setString(4, bus.getCustomer_name());
				ps.setString(5, bus.getCustomer_phone());
				ps.setString(6,bus.getBooking_datetime().toString());
				ps.executeUpdate();

				con.close();

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

		public static void delete_BusBooking(int booking_id) {

			try {
				Connection con = DB_Connection.getConnection();
				String sql = "DELETE FROM busbooking WHERE booking_id=?";
				PreparedStatement getstatement = con.prepareStatement(sql);
				getstatement.setInt(1, booking_id);
				getstatement.executeUpdate();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();

			}

		}

		public static void updateBusBooking(BusBooking bus) {

			try {
				Connection con = DB_Connection.getConnection();

				String sql = "UPDATE busbooking SET  bus_id=?,bus_seatno=?,total_amount=?,customer_name=?,customer_phone=?,booking_datetime=?  where booking_id=?";
				PreparedStatement statement = con.prepareStatement(sql);

				//statement.setInt(1, bus.getBus_id());
				statement.setString(2, bus.getBus_seatno());
				statement.setDouble(3, bus.getTotal_amount());
				statement.setString(4, bus.getCustomer_name());
				statement.setString(5, bus.getCustomer_phone());
				statement.setString(6, bus.getBooking_datetime().toString());
				statement.setInt(7, bus.getBooking_id());

				statement.executeUpdate();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		public static List<String> getSeatBooking(int bus_id) {

			List<String> bookingList=new ArrayList<String>();
			BusBooking bus = null;
			try {
				Connection con = DB_Connection.getConnection();
				String sql = "select bus_seatno from busbooking where bus_id=?";
				PreparedStatement getstatement = con.prepareStatement(sql);
				getstatement.setInt(1, bus_id);
				ResultSet rs = getstatement.executeQuery();
				while (rs.next()) {
					
					bookingList.add(rs.getString("bus_seatno"));
				}
				con.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			return bookingList;

		}
		
		
//		public static void main(String[] args) throws SQLException {
//			getBusBookingLists().forEach(e->{
//				System.out.println(e.getBusLine().bus_name+e.getBusLine().bus_no);
//			});
//			
//		}

}
