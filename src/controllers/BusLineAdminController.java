package controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;

import database.BusLine_DB;
import database.DB_Connection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import models.BusLine;

public class BusLineAdminController implements Initializable {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private JFXDatePicker datepicker = new JFXDatePicker();

	@FXML
	private JFXTimePicker timepicker = new JFXTimePicker();

	@FXML
	private JFXTextField searchField = new JFXTextField(), busnameField = new JFXTextField(),
			busnoField = new JFXTextField(), loc_from = new JFXTextField(), loc_to = new JFXTextField(),
			seat_qtyField = new JFXTextField(), seat_colField = new JFXTextField(), priceField = new JFXTextField();

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private JFXButton btn_add, btn_update, btn_delete;
	
	@FXML
	private DatePicker searchDate;

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
	private TableColumn<BusLine, Integer> col_seatqty = new TableColumn<BusLine, Integer>("Seat_Qty");
	@FXML
	private TableColumn<BusLine, Integer> col_seatcolumn = new TableColumn<BusLine, Integer>("Seat_Col");
	@FXML
	private TableColumn<BusLine, Double> col_price = new TableColumn<BusLine, Double>("Price");

	ArrayList<BusLine> busLineArrayList = (ArrayList<BusLine>) BusLine_DB.getBusLists();
	ObservableList<BusLine> oblist = FXCollections.observableArrayList();
	
	JFXTextField[] fields= {busnameField,busnoField};

	BusLine index;
	int delete_index;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_id.setCellValueFactory(new PropertyValueFactory<BusLine, Integer>("bus_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<BusLine, String>("bus_name"));
		col_no.setCellValueFactory(new PropertyValueFactory<BusLine, String>("bus_no"));
		col_from.setCellValueFactory(new PropertyValueFactory<BusLine, String>("loc_from"));
		col_to.setCellValueFactory(new PropertyValueFactory<BusLine, String>("loc_to"));
		col_date.setCellValueFactory(new PropertyValueFactory<BusLine, LocalDate>("date"));
		col_time.setCellValueFactory(new PropertyValueFactory<BusLine, LocalTime>("time"));
		col_seatqty.setCellValueFactory(new PropertyValueFactory<BusLine, Integer>("seat_qty"));
		col_seatcolumn.setCellValueFactory(new PropertyValueFactory<BusLine, Integer>("seat_col"));
		col_price.setCellValueFactory(new PropertyValueFactory<BusLine, Double>("price"));
		tableView.setItems(oblist);
		
	
		

		inserttoTable();

		btn_add.setOnAction(e -> {

		/*	!busnameField.getText().isEmpty() && !busnoField.getText().isEmpty() && !priceField.getText().isEmpty()
			&& !loc_from.getText().isEmpty() && !loc_to.getText().isEmpty() && datepicker.getValue() != null
			&& timepicker.getValue() != null && !seat_colField.getText().isEmpty()
			&& !seat_qtyField.getText().isEmpty()*/
		
			TextField[] fields = { busnameField, busnoField, loc_from, loc_to,seat_colField,seat_qtyField,priceField };
			if (checkTextfield_Empty(fields)) {
				tableView.getItems().clear();
				if (!loc_from.getText().equals(loc_to.getText())) {
					BusLine busLine = new BusLine(delete_index, busnameField.getText().toLowerCase(),
							busnoField.getText(), loc_from.getText().toLowerCase(), loc_to.getText().toLowerCase(),
							datepicker.getValue(), timepicker.getValue(), Integer.parseInt(seat_qtyField.getText()),
							Integer.parseInt(seat_colField.getText()), Double.parseDouble(priceField.getText()));
					BusLine_DB.addBusLine(busLine);
					inserttoTable();
					busnameField.clear();
					busnoField.clear();
					priceField.clear();
					seat_colField.clear();
					seat_qtyField.clear();
					loc_from.clear();
					loc_to.clear();
					datepicker.setValue(null);
					timepicker.setValue(null);
				} else {
					new Alert(AlertType.ERROR, "From and To cannot be same!").showAndWait();
				}

			} else {
				new Alert(AlertType.ERROR, "Please fill your data!").showAndWait();
			}
		});

		btn_update.setOnAction(e -> {

			if (!loc_from.getText().equals(loc_to.getText())) {
				BusLine busLine = new BusLine(delete_index, busnameField.getText().toLowerCase(), busnoField.getText(),
						loc_from.getText().toLowerCase(), loc_to.getText().toLowerCase(), datepicker.getValue(),
						timepicker.getValue(), Integer.parseInt(seat_qtyField.getText()),
						Integer.parseInt(seat_colField.getText()), Double.parseDouble(priceField.getText()));
				BusLine_DB.updateBusLine(busLine);
				inserttoTable();
				busnameField.clear();
				busnoField.clear();
				priceField.clear();
				seat_colField.clear();
				seat_qtyField.clear();
				loc_from.clear();
				loc_to.clear();
				datepicker.setValue(null);
				timepicker.setValue(null);
			} else {
				new Alert(AlertType.ERROR, "From and To cannot be same!").showAndWait();
			}
			tableView.refresh();

		});

		btn_delete.setOnAction(e -> {
			tableView.getItems().clear();
			BusLine_DB.delete_BusLine(delete_index);
			inserttoTable();
			busnameField.clear();
			busnoField.clear();
			loc_from.clear();
			loc_to.clear();
			seat_colField.clear();
			seat_qtyField.clear();
			priceField.clear();
			datepicker.setValue(null);
			timepicker.setValue(null);
			tableView.refresh();
			
		});

		tableView.setRowFactory(tv -> {
			TableRow<BusLine> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					index = tableView.getSelectionModel().getSelectedItem();
					delete_index = index.getBus_id();
					System.out.println(delete_index);
					String st = "select * from busline where bus_id=" + index.getBus_id();
					Statement stmt;
					try {
						stmt = DB_Connection.getConnection().createStatement();
						ResultSet set = stmt.executeQuery(st);
						while (set.next()) {
							busnameField.setText(set.getString(2));
							busnoField.setText(set.getString(3));
							loc_from.setText(set.getString(4));
							loc_to.setText(set.getString(5));
							datepicker.setValue(LocalDate.parse(set.getString(6)));
							timepicker.setValue(LocalTime.parse(set.getString(7)));
							seat_qtyField.setText(set.getString(8));
							seat_colField.setText(set.getString(9));
							priceField.setText(set.getString(10));
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}

			});
			return row;
		});
		
		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
		
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

	}// initial

	public void inserttoTable() {
		ArrayList<BusLine> buses = (ArrayList<BusLine>) BusLine_DB.getBusLists();
		ObservableList<BusLine> busList = FXCollections.observableArrayList(buses);
		tableView.setItems(busList);

	}
	
	public static boolean checkTextfield_Empty(TextField[] fields2) { // check all textfields have text
		boolean flag = true;
		for (TextField field : fields2) {
			if (field.getText().trim().isEmpty() == true) {

				flag = false;
			}
		}
		return flag;

	}
	
	public void search(String text) {
		System.out.println(text);

		ObservableList<BusLine> buss = FXCollections.observableArrayList(busLineArrayList);

		buss = buss.filtered(bb -> {

			return  bb.getBus_name().toLowerCase().contains(text) || String.valueOf(bb.getBus_id()).contains(text) ||
					bb.getBus_no().toLowerCase().contains(text) ;
			
		});
		
		tableView.setItems(buss);
		tableView.refresh();

	}
	
	
}
