package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class BusLine {

	public int bus_id;
	public String bus_name;
	public String bus_no;
	public String loc_from;
	public String loc_to;
	public LocalDate date;
	public LocalTime time;
	public int seat_qty;
	public int seat_col;
	public double price;
	
	public BusLine() {
		
	};

	public BusLine(int seat_qty, int seat_col, double price) {
		this.seat_qty = seat_qty;
		this.seat_col = seat_col;
		this.price = price;
	};

	public BusLine(int bus_id, String bus_name, String bus_no, String loc_from, String loc_to, LocalDate date,
			LocalTime time, int seat_qty, int seat_col, double price) {
		super();
		this.bus_id = bus_id;
		this.bus_name = bus_name;
		this.bus_no = bus_no;
		this.loc_from = loc_from;
		this.loc_to = loc_to;
		this.date = date;
		this.time = time;
		this.seat_qty = seat_qty;
		this.seat_col = seat_col;
		this.price = price;
	}

	public int getBus_id() {
		return bus_id;
	}

	public void setBus_id(int bus_id) {
		this.bus_id = bus_id;
	}

	public String getBus_name() {
		return bus_name;
	}

	public void setBus_name(String bus_name) {
		this.bus_name = bus_name;
	}

	public String getBus_no() {
		return bus_no;
	}

	public void setBus_no(String bus_no) {
		this.bus_no = bus_no;
	}

	public String getLoc_from() {
		return loc_from;
	}

	public void setLoc_from(String loc_from) {
		this.loc_from = loc_from;
	}

	public String getLoc_to() {
		return loc_to;
	}

	public void setLoc_to(String loc_to) {
		this.loc_to = loc_to;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public int getSeat_qty() {
		return seat_qty;
	}

	public void setSeat_qty(int seat_qty) {
		this.seat_qty = seat_qty;
	}

	public int getSeat_col() {
		return seat_col;
	}

	public void setSeat_col(int seat_col) {
		this.seat_col = seat_col;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
