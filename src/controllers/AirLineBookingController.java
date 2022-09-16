
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

import database.AirBooking_DB;
import database.AirLine_DB;
import database.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.AirBooking;
import models.AirLine;

public class AirLineBookingController implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private HBox searchBox;

    @FXML
    private SplitPane splitPane;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<AirLine> tableView;

    @FXML
    private TableColumn<AirLine, Integer> col_id;

    @FXML
    private TableColumn<AirLine, String> col_name;

    @FXML
    private TableColumn<AirLine, String> col_no;

    @FXML
    private TableColumn<AirLine, String> col_from;

    @FXML
    private TableColumn<AirLine, String> col_to;

    @FXML
    private TableColumn<AirLine, LocalDate> col_date;

    @FXML
    private TableColumn<AirLine, LocalTime> col_time;

    @FXML
    private TableColumn<AirLine, LocalTime> col_arrivaltime;

    @FXML
    private TableColumn<AirLine, Double> col_price;

    @FXML
    private AnchorPane ticketPane;

    @FXML
    private JFXTextField airnameField=new JFXTextField();

    @FXML
    private JFXTextField toField;

    @FXML
    private JFXTextField fromField;

    @FXML
    private JFXTextField airnoField;

    @FXML
    private JFXTextField seatbookingField;

    @FXML
    private JFXTextField totalamount;

    @FXML
    private JFXTextField dateField;

    @FXML
    private JFXTextField timeField;

    @FXML
    private JFXButton btn_booking;

    @FXML
    private JFXTextField customer_name;

    @FXML
    private JFXTextField customer_phno;

    @FXML
    private JFXTextField arrival_time;

    @FXML
    private JFXButton cancel;

    @FXML
    private ScrollPane seat_scrollPane;

    @FXML
    private HBox hbox;
    
    @FXML
    private DatePicker searchDate;
    
    @FXML
    private JFXTextField searchField;
    
    
    ArrayList<VBox> mainArrayList;
	int i;
	int count = 0;
	AirLine air_seat;
	Button button;
	Button[] seatButton;
	double amt = 0.0;
	
	AirLine airLine;
	
	
	ArrayList<AirLine> airLineArrayList = (ArrayList<AirLine>) AirLine_DB.getAirLineLists();
	ObservableList<AirLine> oblist = FXCollections.observableArrayList();

	ArrayList<Button> selectbtn = new ArrayList<Button>();

    @FXML
    void onBooking(ActionEvent event) {
    	
    	if(!customer_name.getText().equals("") && !customer_phno.getText().equals("")) {
			if(customer_phno.getText().length()==11 && customer_phno.getText().startsWith("09")) {
				
				System.out.println(airLine.getAir_id());
				
				AirBooking booking=new AirBooking(0,airLine,seatbookingField.getText(),Double.parseDouble(totalamount.getText()),
						customer_name.getText().toLowerCase(),customer_phno.getText(),LocalDateTime.now());
				System.out.println(booking);
				AirBooking_DB.addAirBooking(booking);
				
				
				new Alert(AlertType.INFORMATION,"Booking!..").showAndWait();
			}else {
				new Alert(AlertType.WARNING,"please check your phone no!").showAndWait();
			}
		}else {
			new Alert(AlertType.WARNING,"please fill customer's information!").showAndWait();
		}

    }

    @FXML
    void onCancel(ActionEvent event) {
    	seatbookingField.setText("");
		totalamount.clear();
		amt = 0;
		count = 0;
		customer_name.setText("");
		customer_phno.setText("");
		for (int k = 0; k < selectbtn.size(); k++) {
			selectbtn.get(k).setStyle("-fx-background-color:#4ef037");
		}
		selectbtn.clear();

    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_id.setCellValueFactory(new PropertyValueFactory<AirLine, Integer>("air_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<AirLine, String>("air_name"));
		col_no.setCellValueFactory(new PropertyValueFactory<AirLine, String>("air_no"));
		col_from.setCellValueFactory(new PropertyValueFactory<AirLine, String>("loc_from"));
		col_to.setCellValueFactory(new PropertyValueFactory<AirLine, String>("loc_to"));
		col_date.setCellValueFactory(new PropertyValueFactory<AirLine, LocalDate>("date"));
		col_time.setCellValueFactory(new PropertyValueFactory<AirLine, LocalTime>("time"));
		col_arrivaltime.setCellValueFactory(new PropertyValueFactory<AirLine, LocalTime>("arrival_time"));
		col_price.setCellValueFactory(new PropertyValueFactory<AirLine, Double>("price"));
		tableView.setItems(oblist);

		inserttoTable();

		tableView.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {
				 airLine= tableView.getSelectionModel().getSelectedItem();
				airnameField.setText(airLine.getAir_name());
				airnoField.setText(airLine.getAir_no());
				fromField.setText(airLine.getLoc_from());
				toField.setText(airLine.getLoc_to());
				dateField.setText(airLine.getDate().toString());
				timeField.setText(airLine.getTime().toString());
				arrival_time.setText(airLine.getArrival_time().toString());
				System.out.println(airLine.getAir_id()+" "+airLine.getAir_name());
				try {
					air_seat = selectSeat_Qty_Col(SelectSeatId());
					System.out.println(air_seat.getSeat_qty());
					System.out.println(air_seat.getSeat_col());
					System.out.println(air_seat.getPrice());
					// System.out.println(SelectSeatId());
					airSeatUi(air_seat.getSeat_qty(), air_seat.getSeat_col());

				} catch (SQLException e1) {

					e1.printStackTrace();
				}
			}
		});
	//----------------------------------------
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
		
	//-----------------------------------------
		
		searchDate.setOnAction(e->{
			ArrayList<AirLine> airs=null;
			airs=(ArrayList<AirLine>) AirLine_DB.getAirLineLists();
			ObservableList<AirLine> al=FXCollections.observableArrayList(airs);
			
			al = al.filtered(ab -> {
				if(ab.getDate()!=null) {
					return ab.getDate().toString().equals(searchDate.getValue().toString());
				}

				return false;
				
			});
			tableView.setItems(al);
			tableView.refresh();
			
		});
		
	}//initial
	
	public void inserttoTable() {
		ArrayList<AirLine> airs = (ArrayList<AirLine>) AirLine_DB.getAirLineLists();
		ObservableList<AirLine> airList = FXCollections.observableArrayList(airs);
		tableView.setItems(airList);

	}

	public AirLine selectSeat_Qty_Col(int air_id) throws SQLException {
		Connection con = DB_Connection.getConnection();
		String sql = "select seat_qty, seat_col,price from airline where air_id=?";
		PreparedStatement getstatement = con.prepareStatement(sql);
		getstatement.setInt(1, air_id);
		ResultSet rs = getstatement.executeQuery();
		AirLine air_line = null;
		while (rs.next()) {
			air_line = new AirLine(rs.getInt("seat_qty"), rs.getInt("seat_col"), rs.getDouble("price"));
		}
		return air_line;
	}


	public int SelectSeatId() {
		int id = 0;
		AirLine airLine = tableView.getSelectionModel().getSelectedItem();
		id = airLine.getAir_id();
		hbox.getChildren().clear();
		return id;
	}

	public void airSeatUi(int seat_qty, int seat_col) {
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
			seatButton[i].setStyle("-fx-background-color:#4ef037");
			seatButton[i].setText(i + 1 + "");

			//System.out.println(BusBooking_DB.getSeatBooking(SelectSeatId()));
			for (String st : AirBooking_DB.getSeatBooking(SelectSeatId())) {
				String[] splitString = st.split("A,");
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
						amt = count * air_seat.getPrice();
						totalamount.setText("" + amt);
						// System.out.println(count);
					}
				});
				try {
					seatbookingField.setText(seatbookingField.getText() + "" + button.getText() + "A,");
					button.setStyle("-fx-background-color:#033fff");
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

	
	public void search(String text) {
		System.out.println(text);

		ObservableList<AirLine> buss = FXCollections.observableArrayList(airLineArrayList);

		buss = buss.filtered(bb -> {

			return  bb.getAir_name().toLowerCase().contains(text) || String.valueOf(bb.getAir_id()).contains(text) ||
					bb.getAir_no().toLowerCase().contains(text);
		});

		tableView.setItems(buss);
		tableView.refresh();

	}

}

