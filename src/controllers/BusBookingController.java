package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import database.BusBooking_DB;
import database.BusLine_DB;
import database.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.BusBooking;
import models.BusLine;

public class BusBookingController implements Initializable {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private HBox searchBox;

	@FXML
	private SplitPane splitPane;

	@FXML
	private ScrollPane scrollPane, seat_scrollPane;

	@FXML
	private TableView<BusLine> tableView;

	@FXML
	private TableColumn<BusLine, Integer> col_id = new TableColumn<BusLine, Integer>("ID");

	@FXML
	private TableColumn<BusLine, String> col_name = new TableColumn<BusLine, String>("BusName");

	@FXML
	private TableColumn<BusLine, String> col_no = new TableColumn<BusLine, String>("BusNo");

	@FXML
	private TableColumn<BusLine, String> col_from = new TableColumn<BusLine, String>("From");

	@FXML
	private TableColumn<BusLine, String> col_to = new TableColumn<BusLine, String>("To");

	@FXML
	private TableColumn<BusLine, LocalDate> col_date = new TableColumn<BusLine, LocalDate>("Date");

	@FXML
	private TableColumn<BusLine, LocalTime> col_time = new TableColumn<BusLine, LocalTime>("Time");

	@FXML
	private TableColumn<BusLine, Double> col_price = new TableColumn<BusLine, Double>("Price");

	@FXML
	private HBox hbox;
	
	@FXML
	private JFXTextField searchField;

	@FXML
	private AnchorPane ticketPane;

	@FXML
	private JFXTextField busnameField = new JFXTextField();

	@FXML
	private JFXTextField toField = new JFXTextField();

	@FXML
	private JFXTextField fromField = new JFXTextField();

	@FXML
	private JFXTextField busnoField = new JFXTextField();

	@FXML
	private javafx.scene.control.Label seatbooking;

	@FXML
	private JFXTextField totalamount = new JFXTextField();

	@FXML
	private JFXTextField dateField = new JFXTextField();

	@FXML
	private JFXTextField timeField = new JFXTextField();

	@FXML
	private JFXTextField customer_name = new JFXTextField(), customer_phno = new JFXTextField();

	@FXML
	private JFXButton btn_booking, btn_cancel;
	
	@FXML
	private DatePicker searchDate;

	ArrayList<VBox> mainArrayList;
	int i;
	int count = 0;
	BusLine bus_seat;
	Button button;
	Button[] seatButton;
	double amt = 0.0;
	
	BusLine busLine;
	
	
	ArrayList<BusLine> busLineArrayList = (ArrayList<BusLine>) BusLine_DB.getBusLists();
	ObservableList<BusLine> oblist = FXCollections.observableArrayList();

	ArrayList<Button> selectbtn = new ArrayList<Button>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_id.setCellValueFactory(new PropertyValueFactory<BusLine, Integer>("bus_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<BusLine, String>("bus_name"));
		col_no.setCellValueFactory(new PropertyValueFactory<BusLine, String>("bus_no"));
		col_from.setCellValueFactory(new PropertyValueFactory<BusLine, String>("loc_from"));
		col_to.setCellValueFactory(new PropertyValueFactory<BusLine, String>("loc_to"));
		col_date.setCellValueFactory(new PropertyValueFactory<BusLine, LocalDate>("date"));
		col_time.setCellValueFactory(new PropertyValueFactory<BusLine, LocalTime>("time"));
		col_price.setCellValueFactory(new PropertyValueFactory<BusLine, Double>("price"));
		tableView.setItems(oblist);

		inserttoTable();

		tableView.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				 busLine= tableView.getSelectionModel().getSelectedItem();
				busnameField.setText(busLine.getBus_name());
				busnoField.setText(busLine.getBus_no());
				fromField.setText(busLine.getLoc_from());
				toField.setText(busLine.getLoc_to());
				dateField.setText(busLine.getDate().toString());
				timeField.setText(busLine.getTime().toString());
				System.out.println(busLine.getBus_id()+" "+busLine.getBus_name());
				try {
					bus_seat = selectSeat_Qty_Col(SelectSeatId());
					System.out.println(bus_seat.getSeat_qty());
					System.out.println(bus_seat.getSeat_col());
					System.out.println(bus_seat.getPrice());
					// System.out.println(SelectSeatId());
					busSeatUi(bus_seat.getSeat_qty(), bus_seat.getSeat_col());

				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});

		// -----------------------------------

		// System.out.println(BusBooking_DB.getSeatBooking().toString());

		// ----------------------------------

		btn_booking.setOnAction(e -> {
			
				if(!customer_name.getText().equals("") && !customer_phno.getText().equals("")) {
					if(customer_phno.getText().length()==11 && customer_phno.getText().startsWith("09")) {
						
						System.out.println(busLine.getBus_id());
						
						BusBooking booking = new BusBooking(busLine,seatbooking.getText(),Double.parseDouble(totalamount.getText()),
						customer_name.getText().toLowerCase(),customer_phno.getText(),LocalDateTime.now());
						BusBooking_DB.addBusBooking(booking);
						
						
						new Alert(AlertType.INFORMATION,"Booking!..").showAndWait();
					}else {
						new Alert(AlertType.WARNING,"please check your phone no!").showAndWait();
					}
				}else {
					new Alert(AlertType.WARNING,"please fill customer's information!").showAndWait();
				}
				
						
		});

		// ---------------------------------
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
		
//---------------------------
		searchDate.setOnAction(e->{
			ArrayList<BusLine> buses=null;
			buses=(ArrayList<BusLine>) BusLine_DB.getBusLists();
			ObservableList<BusLine> bl=FXCollections.observableArrayList(buses);
			
			bl = bl.filtered(bb -> {
				if(bb.getDate()!=null) {
					return bb.getDate().toString().equals(searchDate.getValue().toString());
				}

				return false;
				
			});
			tableView.setItems(bl);
			tableView.refresh();
			
		});

	}// inital

	public void inserttoTable() {
		ArrayList<BusLine> buses = (ArrayList<BusLine>) BusLine_DB.getBusLists();
		ObservableList<BusLine> busList = FXCollections.observableArrayList(buses);
		tableView.setItems(busList);

	}

	public BusLine selectSeat_Qty_Col(int bus_id) throws SQLException {
		Connection con = DB_Connection.getConnection();
		String sql = "select seat_qty, seat_col,price from busline where bus_id=?";
		PreparedStatement getstatement = con.prepareStatement(sql);
		getstatement.setInt(1, bus_id);
		ResultSet rs = getstatement.executeQuery();
		BusLine bus_line = null;
		while (rs.next()) {
			bus_line = new BusLine(rs.getInt("seat_qty"), rs.getInt("seat_col"), rs.getDouble("price"));
		}
		return bus_line;
	}

	/*
	 public int SelectSeatId() { 
	 	int id = 0; 
	 	for (BusLine busLine :tableView.getSelectionModel().getSelectedItems()) { 
	 	for (int i = 1; i <= 1; i++) { 
	 		id = busLine.getBus_id(); 
 		// for(Node remove:hbox.getChildren()){
   				hbox.getChildren().remove(remove); 
   		}
	  		hbox.getChildren().clear();
	  		} 
	  } 
	  return id; 
	  }
	 */

	public int SelectSeatId() {
		int id = 0;
		BusLine busLine = tableView.getSelectionModel().getSelectedItem();
		id = busLine.getBus_id();
		hbox.getChildren().clear();
		return id;
	}

	public void busSeatUi(int seat_qty, int seat_col) {
		mainArrayList = new ArrayList<>();
		int column = seat_col;
		int seatnum = seat_qty;
		seatButton = new Button[seat_qty];
		for (int i = 0; i < column; i++) {
			VBox vbox = new VBox();
			mainArrayList.add(vbox);
			vbox.setStyle("-fx-padding:5;-fx-spacing:5;");
		}
		int j = 0;
		for (i = 0; i < seatnum; i++) {
			seatButton[i] = new Button();
			seatButton[i].setPrefSize(50, 50);
			seatButton[i].setStyle("-fx-background-color:#0aac00");
			seatButton[i].setText(i + 1 + "");

			//System.out.println(BusBooking_DB.getSeatBooking(SelectSeatId()));
			for (String st : BusBooking_DB.getSeatBooking(SelectSeatId())) {
				String[] splitString = st.split("B,");
				//System.out.println(Arrays.toString(splitString));
				int book = 0;
				for (String sp : splitString) {
					book = Integer.parseInt(sp);
					if (i == book) {
						seatButton[i - 1].setStyle("-fx-background-color:red");
						seatButton[i - 1].setDisable(true);
						// System.out.println(book);
					}
				}

			}

			seatButton[i].setOnAction(e -> {

				button = (Button) e.getTarget();
				// System.out.println(button.getText());
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						++count;
						amt = count * bus_seat.getPrice();
						totalamount.setText("" + amt);
						// System.out.println(count);
					}
				});
				try {
					seatbooking.setText(seatbooking.getText() + "" + button.getText() + "B,");
					button.setStyle("-fx-background-color:yellow");
					selectbtn.add(button);
				} catch (Exception e2) {

				}

			});

			mainArrayList.get(j++).getChildren().addAll(seatButton[i]);// get(j++).

			if (j == column)
				j = 0;
		}
		hbox.getChildren().addAll(mainArrayList);
	}

	@FXML
	void onCancel() {
		seatbooking.setText("");
		totalamount.clear();
		amt = 0;
		count = 0;
		customer_name.setText("");
		customer_phno.setText("");
		for (int k = 0; k < selectbtn.size(); k++) {
			selectbtn.get(k).setStyle("-fx-background-color:#0aac00");
		}
		selectbtn.clear();
	}
	
	public void search(String text) {
		System.out.println(text);

		ObservableList<BusLine> buss = FXCollections.observableArrayList(busLineArrayList);

		buss = buss.filtered(bb -> {

			return  bb.getBus_name().toLowerCase().contains(text) || String.valueOf(bb.getBus_id()).contains(text) ||
					bb.getBus_no().toLowerCase().contains(text);
		});

		tableView.setItems(buss);
		tableView.refresh();

	}

}
