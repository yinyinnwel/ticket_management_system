package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AirBooking {
	
	private int booking_id;
	private AirLine airLine;
	private String air_seatno;
	private Double total_amount;
	private String customer_name;
	private String customer_phone; 
	private LocalDateTime booking_datetime;
	
	public AirBooking() {
		
	}
	
	public AirBooking(String air_seatno) {
		this.air_seatno=air_seatno;
	}

	public AirBooking(int booking_id, AirLine airLine, String air_seatno, Double total_amount, String customer_name,
			String customer_phone, LocalDateTime booking_datetime) {
		super();
		this.booking_id = booking_id;
		this.airLine = airLine;
		this.air_seatno = air_seatno;
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

	public AirLine getAirLine() {
		return airLine;
	}

	public void setAirLine(AirLine airLine) {
		this.airLine = airLine;
	}

	public String getAir_seatno() {
		return air_seatno;
	}

	public void setAir_seatno(String air_seatno) {
		this.air_seatno = air_seatno;
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

	public String getAir_name() {
		return airLine.air_name;
	}

	public String getAir_no() {
		return airLine.air_no;
	}

	public String getLoc_from() {
		return airLine.loc_from;
	}

	public String getLoc_to() {
		return airLine.loc_to;
	}

	public LocalDate getDate() {
		return airLine.date;
	}

	public LocalTime getTime() {
		return airLine.time;
	}

	public LocalTime getArrival_time() {
		return airLine.arrival_time;
	}


}
