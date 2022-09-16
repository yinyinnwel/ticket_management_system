package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import database.AirBooking_DB;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import models.AirBooking;

public class AirLineTicketController implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField searchField;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<AirBooking> tableView;

    @FXML
    private TableColumn<AirBooking, Integer> col_id;

    @FXML
    private TableColumn<AirBooking, String> col_name;

    @FXML
    private TableColumn<AirBooking, String> col_no;

    @FXML
    private TableColumn<AirBooking, String> col_from;

    @FXML
    private TableColumn<AirBooking, String> col_to;

    @FXML
    private TableColumn<AirBooking, LocalDate> col_date;

    @FXML
    private TableColumn<AirBooking, LocalTime> col_time;

    @FXML
    private TableColumn<AirBooking, LocalTime> col_arrivaltime;

    @FXML
    private TableColumn<AirBooking, String> col_seatbooking;

    @FXML
    private TableColumn<AirBooking, Double> col_totalamt;

    @FXML
    private TableColumn<AirBooking, String> col_cname;

    @FXML
    private TableColumn<AirBooking, String> col_cphone;

    @FXML
    private TableColumn<AirBooking, LocalDateTime> col_bookingdateTime1;

    @FXML
    private AnchorPane ticketPane;

    @FXML
    private Label clock;

    @FXML
    private Label airname;

    @FXML
    private Label time;

    @FXML
    private Label date;

    @FXML
    private Label arrivaltime;

    @FXML
    private Label c_phone;

    @FXML
    private Label c_name;

    @FXML
    private Label from;

    @FXML
    private Label airno;

    @FXML
    private Label to;

    @FXML
    private Label total_amount;

    @FXML
    private Label refund;

    @FXML
    private Button btn_printticket;

    @FXML
    private Button btn_cancel;

    @FXML
    private JFXTextField payment;

    @FXML
    private Label seatbooking;
    
    AirBooking ab;
    
   ArrayList<AirBooking> airLineArrayList = (ArrayList<AirBooking>) AirBooking_DB.getAirBookingLists();
   	ObservableList<AirBooking> oblist = FXCollections.observableArrayList();

    @FXML
    void onCancel(ActionEvent event) {
    	airname.setText("");
    	airno.setText("");
    	from.setText("");
    	to.setText("");
    	date.setText("");
    	time.setText("");
    	arrivaltime.setText("");
    	seatbooking.setText("");
    	total_amount.setText("");
    	c_name.setText("");
    	c_phone.setText("");
    	payment.setText("");
    	refund.setText("");
    }

    @FXML
    void onPrintTicket(ActionEvent event) {

    }
    
    @FXML
	public void refund() {
		double amt=Double.parseDouble(total_amount.getText());
		double pay=Double.parseDouble(payment.getText());
		double repay=pay-amt;
		refund.setText(repay+"");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_id.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<>("air_name"));
		col_no.setCellValueFactory(new PropertyValueFactory<>("air_no"));
		col_from.setCellValueFactory(new PropertyValueFactory<>("loc_from"));
		col_to.setCellValueFactory(new PropertyValueFactory<>("loc_to"));
		col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
		col_arrivaltime.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
		col_seatbooking.setCellValueFactory(new PropertyValueFactory<>("air_seatno"));
		col_totalamt.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
		col_cname.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
		col_cphone.setCellValueFactory(new PropertyValueFactory<>("customer_phone"));
		col_bookingdateTime1.setCellValueFactory(new PropertyValueFactory<>("booking_datetime"));
		
	//	tableView.setItems(oblist);
		
		inserttoTable();
		
		tableView.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				ab=tableView.getSelectionModel().getSelectedItem();
				airname.setText(ab.getAir_name());
				airno.setText(ab.getAir_no());
				from.setText(ab.getLoc_from());
				to.setText(ab.getLoc_to());
				date.setText(ab.getDate().toString());
				time.setText(ab.getTime().toString());
				arrivaltime.setText(ab.getTime().toString());
				seatbooking.setText(ab.getAir_seatno());
				c_name.setText(ab.getCustomer_name());
				c_phone.setText(ab.getCustomer_phone());
				total_amount.setText(ab.getTotal_amount().toString());
				
			}
		});
		
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
	
		
		Timeline line=new Timeline(new KeyFrame(Duration.ZERO,e->{
			DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm:ss");
			clock.setText(LocalDateTime.now().format(format));
			
		}),new KeyFrame(Duration.seconds(1)));
		
		line.setCycleCount(Animation.INDEFINITE);
		line.play();
		
	}//inital
	
	
	public void inserttoTable() {
		ArrayList<AirBooking> airs = (ArrayList<AirBooking>) AirBooking_DB.getAirBookingLists();
		ObservableList<AirBooking> airList = FXCollections.observableArrayList(airs);
		tableView.getItems().addAll(airList);
		
		
		AirBooking_DB.getAirBookingLists().forEach(e->{
			System.out.println(e.getAirLine().air_name+" "+e.getAirLine().air_no);
		});
		
	}
	
	
	public void search(String text) {
		System.out.println(text);

		ObservableList<AirBooking> buss = FXCollections.observableArrayList(airLineArrayList);

		buss = buss.filtered(bb -> {

			return  bb.getCustomer_name().contains(text) || String.valueOf(bb.getBooking_id()).contains(text)||
					bb.getAir_name().contains(text);
		});

		tableView.setItems(buss);
		tableView.refresh();
	}	
	

}

