package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class BusTicketFrameController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane ticketPane;

    @FXML
    private Label busname;

    @FXML
    private Label time;

    @FXML
    private Label date;

    @FXML
    private Label seatbooking;

    @FXML
    private Label c_phone;

    @FXML
    private Label c_name;

    @FXML
    private Label from;

    @FXML
    private Label busno;

    @FXML
    private Label to;

    @FXML
    private Label total_amount;

    @FXML
    private Label refund;
    
    @FXML
    private Label payment;
    
    
    public void ticketFrame(String busname,String busno,String from,String to,String date,
    		String time,String seatbooking,String cname,String cphone,String amt,String pay,String repay) {
		
    	this.busname.setText(busname);
    	this.busno.setText(busno);
    	this.from.setText(from);
    	this.to.setText(to);
    	this.date.setText(date);
    	this.time.setText(time);
    	this.seatbooking.setText(seatbooking);
    	this.c_name.setText(cname);
    	this.c_phone.setText(cphone);
    	this.total_amount.setText(amt);
    	this.payment.setText(pay);
    	this.refund.setText(repay);
	}

}

