package models;

public class Register {
	
	public int rg_id;
	public String rg_name;
	public String rg_nrc;
	public String rg_phone;
	public String rg_address;
	public String rg_password;
	public String rg_image;
	
	
	public Register(int rg_id, String rg_name, String rg_nrc, String rg_phone, String rg_address, String rg_password,
			String rg_image) {
		super();
		this.rg_id = rg_id;
		this.rg_name = rg_name;
		this.rg_nrc = rg_nrc;
		this.rg_phone = rg_phone;
		this.rg_address = rg_address;
		this.rg_password = rg_password;
		this.rg_image = rg_image;
	}


	public int getRg_id() {
		return rg_id;
	}


	public void setRg_id(int rg_id) {
		this.rg_id = rg_id;
	}


	public String getRg_name() {
		return rg_name;
	}


	public void setRg_name(String rg_name) {
		this.rg_name = rg_name;
	}


	public String getRg_nrc() {
		return rg_nrc;
	}


	public void setRg_nrc(String rg_nrc) {
		this.rg_nrc = rg_nrc;
	}


	public String getRg_phone() {
		return rg_phone;
	}


	public void setRg_phone(String rg_phone) {
		this.rg_phone = rg_phone;
	}


	public String getRg_address() {
		return rg_address;
	}


	public void setRg_address(String rg_address) {
		this.rg_address = rg_address;
	}


	public String getRg_password() {
		return rg_password;
	}


	public void setRg_password(String rg_password) {
		this.rg_password = rg_password;
	}


	public String getRg_image() {
		return rg_image;
	}


	public void setRg_image(String rg_image) {
		this.rg_image = rg_image;
	}


}
