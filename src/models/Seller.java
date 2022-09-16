package models;

public class Seller {
	
	public int seller_id;
	public String seller_name;
	public String seller_nrc;
	public String seller_phone;
	public String seller_address;
	public String seller_password;
	public String seller_image;
	
	
	public Seller(int seller_id, String seller_name, String seller_nrc, String seller_phone, String seller_address,
			String seller_password, String seller_image) {
		super();
		this.seller_id = seller_id;
		this.seller_name = seller_name;
		this.seller_nrc = seller_nrc;
		this.seller_phone = seller_phone;
		this.seller_address = seller_address;
		this.seller_password = seller_password;
		this.seller_image = seller_image;
	}

	public int getSeller_id() {
		return seller_id;
	}


	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}


	public String getSeller_name() {
		return seller_name;
	}


	public void setSeller_name(String seller_name) {
		this.seller_name = seller_name;
	}


	public String getSeller_nrc() {
		return seller_nrc;
	}


	public void setSeller_nrc(String seller_nrc) {
		this.seller_nrc = seller_nrc;
	}


	public String getSeller_phone() {
		return seller_phone;
	}


	public void setSeller_phone(String seller_phone) {
		this.seller_phone = seller_phone;
	}


	public String getSeller_address() {
		return seller_address;
	}


	public void setSeller_address(String seller_address) {
		this.seller_address = seller_address;
	}


	public String getSeller_password() {
		return seller_password;
	}


	public void setSeller_password(String seller_password) {
		this.seller_password = seller_password;
	}


	public String getSeller_image() {
		return seller_image;
	}


	public void setSeller_image(String seller_image) {
		this.seller_image = seller_image;
	}


	


	

	
	
	

}
