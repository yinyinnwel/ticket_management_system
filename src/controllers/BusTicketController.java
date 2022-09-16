package controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import database.BusBooking_DB;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.BusBooking;

public class BusTicketController implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField searchField;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<BusBooking> tableView;

    @FXML
    private TableColumn<BusBooking, Integer> col_id;

    @FXML
    private TableColumn<BusBooking, String> col_name;

    @FXML
    private TableColumn<BusBooking, String> col_no;

    @FXML
    private TableColumn<BusBooking, String> col_from;

    @FXML
    private TableColumn<BusBooking, String> col_to;

    @FXML
    private TableColumn<BusBooking, LocalDate> col_date;

    @FXML
    private TableColumn<BusBooking, LocalTime> col_time;

    @FXML
    private TableColumn<BusBooking, String> col_seatbooking;

    @FXML
    private TableColumn<BusBooking, Double> col_amt;

    @FXML
    private TableColumn<BusBooking, String> col_cname;

    @FXML
    private TableColumn<BusBooking, String> col_cphone;

    @FXML
    private TableColumn<BusBooking, LocalDateTime> col_bookingdateTime;

    @FXML
    private AnchorPane ticketPane;

    @FXML
    private Label clock;

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
    private JFXTextField payment;

    @FXML
    private Label refund;

    @FXML
    private Button btn_printticket;

    @FXML
    private Button btn_cancel;
    
    BusBooking bb;
    
    ArrayList<BusBooking> busLineArrayList = (ArrayList<BusBooking>) BusBooking_DB.getBusBookingLists();
	ObservableList<BusBooking> oblist = FXCollections.observableArrayList();

    @FXML
    void onCancel(ActionEvent event) {
    	busname.setText("");
    	busno.setText("");
    	from.setText("");
    	to.setText("");
    	date.setText("");
    	time.setText("");
    	seatbooking.setText("");
    	total_amount.setText("");
    	c_name.setText("");
    	c_phone.setText("");
    	payment.setText("");
    	refund.setText("");

    }

    @FXML
    void onPrintTicket(ActionEvent event) {
   	/*FXMLLoader root;
		try {
			//FXMLLoader.load(getClass().getResource("../view/busTicket_Pdf.fxml"));
			root = new FXMLLoader();
			root.setLocation(getClass().getResource("../view/busTicket_Pdf.fxml"));
			root.load();
			BusTicketFrameController bb=root.getController();
			bb.ticketFrame(busname.getText(), busno.getText(), from.getText(), to.getText(), date.getText(), time.getText(),
					seatbooking.getText(), c_name.getText(), c_phone.getText(), total_amount.getText(), payment.getText(), refund.getText());
			Scene parent=root.getController();
			Stage stage = new Stage();
			stage.setScene(parent);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.setTitle("Go Go Application");
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}*/
    }
    
    
    public void search(String text) {
		System.out.println(text);

		ObservableList<BusBooking> buss = FXCollections.observableArrayList(busLineArrayList);

		buss = buss.filtered(bb -> {

			return  bb.getCustomer_name().contains(text) || String.valueOf(bb.getBooking_id()).contains(text)||
					bb.getBus_name().contains(text);
		});

		tableView.setItems(buss);
		tableView.refresh();

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		col_id.setCellValueFactory(new PropertyValueFactory<>("booking_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<>("bus_name"));
		col_no.setCellValueFactory(new PropertyValueFactory<>("bus_no"));
		col_from.setCellValueFactory(new PropertyValueFactory<>("loc_from"));
		col_to.setCellValueFactory(new PropertyValueFactory<>("loc_to"));
		col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
		col_time.setCellValueFactory(new PropertyValueFactory<>("time"));
		col_seatbooking.setCellValueFactory(new PropertyValueFactory<>("bus_seatno"));
		col_amt.setCellValueFactory(new PropertyValueFactory<>("total_amount"));
		col_cname.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
		col_cphone.setCellValueFactory(new PropertyValueFactory<>("customer_phone"));
		col_bookingdateTime.setCellValueFactory(new PropertyValueFactory<>("booking_datetime"));
		
	//	tableView.setItems(oblist);
		
		inserttoTable();
		
		tableView.setOnMouseClicked(e->{
			if(e.getClickCount()==2) {
				bb=tableView.getSelectionModel().getSelectedItem();
				busname.setText(bb.getBus_name());
				busno.setText(bb.getBus_no());
				from.setText(bb.getLoc_from());
				to.setText(bb.getLoc_to());
				date.setText(bb.getDate().toString());
				time.setText(bb.getTime().toString());
				seatbooking.setText(bb.getBus_seatno());
				c_name.setText(bb.getCustomer_name());
				c_phone.setText(bb.getCustomer_phone());
				total_amount.setText(bb.getTotal_amount().toString());
				
			}
		});
		
		
		Timeline line=new Timeline(new KeyFrame(Duration.ZERO,e->{
			DateTimeFormatter format=DateTimeFormatter.ofPattern("HH:mm:ss");
			clock.setText(LocalDateTime.now().format(format));
			
		}),new KeyFrame(Duration.seconds(1)));
		
		line.setCycleCount(Animation.INDEFINITE);
		line.play();
		
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
	}//inital
	
	public void inserttoTable() {
		ArrayList<BusBooking> buses = (ArrayList<BusBooking>) BusBooking_DB.getBusBookingLists();
		ObservableList<BusBooking> busList = FXCollections.observableArrayList(buses);
		tableView.getItems().addAll(busList);
		
		
		BusBooking_DB.getBusBookingLists().forEach(e->{
			System.out.println(e.getBusLine().bus_name+" "+e.getBusLine().bus_no);
		});
		
	}
	@FXML
	public void refund() {
		double amt=Double.parseDouble(total_amount.getText());
		double pay=Double.parseDouble(payment.getText());
		double repay=pay-amt;
		refund.setText(repay+"");
	}
	
	

}


