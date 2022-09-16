package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class BusBooking {
	
	private int booking_id;
	private BusLine busLine;
	private String bus_seatno;
	private Double total_amount;
	private String customer_name;
	private String customer_phone; 
	private LocalDateTime booking_datetime;
	
	public BusBooking() {
		
	}
	public BusBooking(String bus_seatno) {
		this.bus_seatno=bus_seatno;
	}

	public BusBooking(BusLine busLine, String bus_seatno, Double total_amount, String customer_name,
			String customer_phone, LocalDateTime booking_datetime) {
		super();
		
		this.busLine = busLine;
		this.bus_seatno = bus_seatno;
		this.total_amount = total_amount;
		this.customer_name = customer_name;
		this.customer_phone = customer_phone;
		this.booking_datetime = booking_datetime;
	}

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
	}

	public BusLine getBusLine() {
		return busLine;
	}

	public void setBusLine(BusLine busLine) {
		this.busLine = busLine;
	}

	public String getBus_seatno() {
		return bus_seatno;
	}

	public void setBus_seatno(String bus_seatno) {
		this.bus_seatno = bus_seatno;
	}

	public Double getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(Double total_amount) {
		this.total_amount = total_amount;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_phone() {
		return customer_phone;
	}

	public void setCustomer_phone(String customer_phone) {
		this.customer_phone = customer_phone;
	}

	public LocalDateTime getBooking_datetime() {
		return booking_datetime;
	}

	public void setBooking_datetime(LocalDateTime booking_datetime) {
		this.booking_datetime = booking_datetime;
	}

	public String getBus_name() {
		return busLine.bus_name;
	}
	
	public String getBus_no() {
		return busLine.bus_no;
	}
	
	public String getLoc_from() {
		return busLine.loc_from;
	}
	
	public String getLoc_to() {
		return busLine.loc_to;
	}
	
	public LocalDate getDate() {
		return busLine.date;
	}
	
	public LocalTime getTime() {
		return busLine.time;
	}
	

}
