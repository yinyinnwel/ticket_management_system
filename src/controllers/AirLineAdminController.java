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

import database.AirLine_DB;
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
import models.AirLine;

public class AirLineAdminController implements Initializable{

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private JFXDatePicker datepicker;
	
	@FXML
	private JFXTimePicker timepicker=new JFXTimePicker(),arrival_time=new JFXTimePicker();
	
	@FXML
	private JFXTextField searchField=new JFXTextField(),
							airnameField=new JFXTextField(),
							airnoField=new JFXTextField(),
							loc_from=new JFXTextField(),
							loc_to=new JFXTextField(),
							seat_qtyField=new JFXTextField(),
							seat_colField=new JFXTextField(),
							priceField=new JFXTextField();
	
	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private DatePicker searchDate;
	
	@FXML
	private JFXButton btn_add,btn_update,btn_delete;
	
	@FXML
	private TableView<AirLine> tableView;
	
	@FXML
	private TableColumn<AirLine, Integer> col_id=new TableColumn<AirLine, Integer>("ID");
	@FXML
	private TableColumn<AirLine, String> col_name=new TableColumn<AirLine, String>("AirName");
	@FXML
	private TableColumn<AirLine, String> col_no=new TableColumn<AirLine, String>("AirNo");
	@FXML
	private TableColumn<AirLine, String> col_from=new TableColumn<AirLine, String>("From");
	@FXML
	private TableColumn<AirLine, String> col_to=new TableColumn<AirLine, String>("To");
	@FXML
	private TableColumn<AirLine,LocalDate> col_date=new TableColumn<AirLine, LocalDate>("Date");
	@FXML
	private TableColumn<AirLine,LocalTime> col_time=new TableColumn<AirLine, LocalTime>("Time");
	@FXML
	private TableColumn<AirLine,LocalTime> col_arrivaltime=new TableColumn<AirLine, LocalTime>("Arrival_Time");
	@FXML
	private TableColumn<AirLine, Integer> col_seatqty=new TableColumn<AirLine, Integer>("Seat_Qty");
	@FXML
	private TableColumn<AirLine, Integer> col_seatcolumn=new TableColumn<AirLine, Integer>("Seat_Col");
	@FXML
	private TableColumn<AirLine, Double> col_price=new TableColumn<AirLine, Double>("Price");
	
	ArrayList<AirLine> airLineArrayList=(ArrayList<AirLine>) AirLine_DB.getAirLineLists();
	ObservableList<AirLine> oblist = FXCollections.observableArrayList();
	
	AirLine index;
	int delete_index;
	
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
		col_seatqty.setCellValueFactory(new PropertyValueFactory<AirLine, Integer>("seat_qty"));
		col_seatcolumn.setCellValueFactory(new PropertyValueFactory<AirLine, Integer>("seat_col"));
		col_price.setCellValueFactory(new PropertyValueFactory<AirLine, Double>("price"));
		tableView.setItems(oblist);
		
		inserttoTable();

		btn_add.setOnAction(e -> {
			TextField[] fields= {airnameField,airnoField,loc_from,loc_to,seat_colField,seat_qtyField,priceField};
			
			if (checkTextfield_Empty(fields)) {
				tableView.getItems().clear();
				
				if (!loc_from.getText().equals(loc_to.getText())) {
					AirLine airLine = new AirLine(0, airnameField.getText().toLowerCase(),
							airnoField.getText(), loc_from.getText().toLowerCase(), loc_to.getText().toLowerCase(),
							datepicker.getValue(), timepicker.getValue(),arrival_time.getValue(), Integer.parseInt(seat_qtyField.getText()),
							Integer.parseInt(seat_colField.getText()), Double.parseDouble(priceField.getText()));
					AirLine_DB.addAirLine(airLine);
					inserttoTable();
					airnameField.clear();
					airnoField.clear();
					priceField.clear();
					seat_colField.clear();
					seat_qtyField.clear();
					loc_from.clear();
					loc_to.clear();
					datepicker.setValue(null);
					timepicker.setValue(null);
					arrival_time.setValue(null);
				} else {
					new Alert(AlertType.ERROR, "From and To cannot be same!").showAndWait();
				}
				System.out.println("click");
			} else {
				new Alert(AlertType.ERROR, "Please fill your data!").showAndWait();
			}
		});

//-----------------------------------------------
		
		btn_update.setOnAction(e -> {

			if (!loc_from.getText().equals(loc_to.getText())) {
				AirLine airLine = new AirLine(delete_index, airnameField.getText().toLowerCase(), airnoField.getText(),
						loc_from.getText().toLowerCase(), loc_to.getText().toLowerCase(), datepicker.getValue(),
						timepicker.getValue(),arrival_time.getValue(), Integer.parseInt(seat_qtyField.getText()),
						Integer.parseInt(seat_colField.getText()), Double.parseDouble(priceField.getText()));
				AirLine_DB.updateAirLine(airLine);
				inserttoTable();
				airnameField.clear();
				airnoField.clear();
				priceField.clear();
				seat_colField.clear();
				seat_qtyField.clear();
				loc_from.clear();
				loc_to.clear();
				datepicker.setValue(null);
				timepicker.setValue(null);
				arrival_time.setValue(null);
			} else {
				new Alert(AlertType.ERROR, "From and To cannot be same!").showAndWait();
			}
			tableView.refresh();

		});
//-----------------------------------------------------
		
		btn_delete.setOnAction(e -> {
			tableView.getItems().clear();
			AirLine_DB.delete_AirLine(delete_index);
			inserttoTable();
			airnameField.clear();
			airnoField.clear();
			loc_from.clear();
			loc_to.clear();
			seat_colField.clear();
			seat_qtyField.clear();
			priceField.clear();
			datepicker.setValue(null);
			timepicker.setValue(null);
			arrival_time.setValue(null);

			tableView.refresh();

		});
		
//------------------------------------------------------------

		tableView.setRowFactory(tv -> {
			TableRow<AirLine> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					index = tableView.getSelectionModel().getSelectedItem();
					delete_index = index.getAir_id();
					System.out.println(delete_index);
					String st = "select * from airline where air_id=" + index.getAir_id();
					Statement stmt;
					try {
						stmt = DB_Connection.getConnection().createStatement();
						ResultSet set = stmt.executeQuery(st);
						while (set.next()) {
							airnameField.setText(set.getString(2));
							airnoField.setText(set.getString(3));
							loc_from.setText(set.getString(4));
							loc_to.setText(set.getString(5));
							datepicker.setValue(LocalDate.parse(set.getString(6)));
							System.out.println(datepicker.getValue());
							timepicker.setValue(LocalTime.parse(set.getString(7)));
							arrival_time.setValue(LocalTime.parse(set.getString(8)));
							seat_qtyField.setText(set.getString(9));
							seat_colField.setText(set.getString(10));
							priceField.setText(set.getString(11));
						}

					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}

			});
			return row;
		});
		
//------------------------------------------------------------------------
		
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
		ArrayList<AirLine> airs = (ArrayList<AirLine>) AirLine_DB.getAirLineLists();
		ObservableList<AirLine> airList = FXCollections.observableArrayList(airs);
		tableView.setItems(airList);

	}
	
	public static boolean checkTextfield_Empty(TextField[] fields) { // check all textfields have text
		boolean flag = true;
		for (TextField field : fields) {
			if (field.getText().trim().isEmpty() == true) {

				flag = false;
			}
		}
		return flag;

	}
	
	public void search(String text) {
		System.out.println(text);

		ObservableList<AirLine> airs = FXCollections.observableArrayList(airLineArrayList);

		airs = airs.filtered(aa -> {

			return aa.getAir_name().toLowerCase().contains(text) || String.valueOf(aa.getAir_id()).contains(text) ||
					aa.getAir_no().toLowerCase().contains(text);
		});

		tableView.setItems(airs);
		tableView.refresh();

	}
	
	
}
