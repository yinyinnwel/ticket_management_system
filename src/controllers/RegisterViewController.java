package controllers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import database.DB_Connection;
import database.Register_DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import models.Register;

public class RegisterViewController implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField searchField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<Register> tableView;

    @FXML
    private TableColumn<Register,Integer> col_id=new TableColumn<Register, Integer>("ID");

    @FXML
    private TableColumn<Register, String> col_name=new TableColumn<Register, String>("Name");

    @FXML
    private TableColumn<Register, String> col_nrc=new TableColumn<Register, String>("NRC");

    @FXML
    private TableColumn<Register, String> col_phone=new TableColumn<Register, String>("Phone");

    @FXML
    private TableColumn<Register, String> col_address=new TableColumn<Register, String>("Address");

    @FXML
    private TableColumn<Register, String> col_password=new TableColumn<Register, String>("Password");

    @FXML
    private TableColumn<Register, String> col_image=new TableColumn<Register, String>("Image");

    @FXML
    private JFXTextField usernameField;

    @FXML
    private JFXTextField phoneField;

    @FXML
    private JFXTextField nrcField;

    @FXML
    private JFXTextField addressField;

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private ImageView uploadImage;

    @FXML
    private JFXPasswordField passwordField;
    
    ArrayList<Register> rgArrayList = (ArrayList<Register>) Register_DB.getRegisterLists();
	ObservableList<Register> oblist = FXCollections.observableArrayList(rgArrayList);

	Register index;
	int delete_index;
	BufferedImage bufImg = null;

	String filepath = null;
	FileChooser filechooser = new FileChooser();

    @FXML
    void openFileChooser(MouseEvent event) {
    	
    	File file = filechooser.showOpenDialog(null);
    	uploadImage.setImage(new Image(file.toURI().toString()));
    }
    

	public void inserttoTable() {
		ArrayList<Register> registers = (ArrayList<Register>) Register_DB.getRegisterLists();
		ObservableList<Register> rgList = FXCollections.observableArrayList(registers);
		tableView.setItems(rgList);

	}

	public void search(String text) {
		System.out.println(text);

		ObservableList<Register> registers = FXCollections.observableArrayList(rgArrayList);

		registers = registers.filtered(rg -> {

			return rg.getRg_name().toLowerCase().contains(text) || String.valueOf(rg.getRg_id()).contains(text);

		});

		tableView.setItems(registers);
		tableView.refresh();

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


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_id.setCellValueFactory(new PropertyValueFactory<Register, Integer>("rg_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<Register, String>("rg_name"));
		col_nrc.setCellValueFactory(new PropertyValueFactory<Register, String>("rg_nrc"));
		col_phone.setCellValueFactory(new PropertyValueFactory<Register, String>("rg_phone"));
		col_address.setCellValueFactory(new PropertyValueFactory<Register, String>("rg_address"));
		col_password.setCellValueFactory(new PropertyValueFactory<Register, String>("rg_password"));
		col_image.setCellValueFactory(new PropertyValueFactory<Register, String>("rg_image"));

		tableView.setItems(oblist);

		inserttoTable();


		tableView.setRowFactory(tv -> {
			TableRow<Register> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					index = tableView.getSelectionModel().getSelectedItem();
					delete_index = index.getRg_id();
					System.out.println(delete_index);
					String st = "select * from register where rg_id=" + index.getRg_id();
					Statement stmt;
					try {
						stmt = DB_Connection.getConnection().createStatement();
						ResultSet set = stmt.executeQuery(st);
						while (set.next()) {
							Blob blobimg = set.getBlob(7);
							usernameField.setText(set.getString(2));
							nrcField.setText(set.getString(3));
							phoneField.setText(set.getString(4));
							addressField.setText(set.getString(5));
							passwordField.setText(set.getString(6));
							InputStream is = blobimg.getBinaryStream(1, blobimg.length());
							bufImg = ImageIO.read(is);
							WritableImage wr = null;
							if (bufImg != null) {
								wr = new WritableImage(bufImg.getWidth(), bufImg.getHeight());
								PixelWriter pw = wr.getPixelWriter();
								for (int x = 0; x < bufImg.getWidth(); x++) {
									for (int y = 0; y < bufImg.getHeight(); y++) {
										pw.setArgb(x, y, bufImg.getRGB(x, y));
									}
								}
							}

							uploadImage.setImage(wr);
						}
					} catch (SQLException | IOException e) {
						e.printStackTrace();
					}

				}
			});
			return row;
		});

		btn_update.setOnAction(e -> {
			TextField[] fields = { usernameField, phoneField, nrcField, passwordField, addressField };
			if (checkTextfield_Empty(fields)) {

				if (phoneField.getText().length() == 11 && phoneField.getText().startsWith("09")) {
					if (nrcField.getText().split("N\\)")[1].length() >= 6) {
						tableView.getItems().clear();
						try {
							Register register = new Register(delete_index, usernameField.getText(),
									nrcField.getText(), phoneField.getText(), addressField.getText().toLowerCase(),
									passwordField.getText(), filepath);
							Register_DB.updateRegister(register);
							inserttoTable();
							usernameField.clear();
							nrcField.clear();
							phoneField.clear();
							addressField.clear();
							passwordField.clear();
							filepath = null;
							uploadImage.setImage(new Image("file:///D:\\Java_Project\\Photos\\uselogo.png"));

						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
						}
						tableView.refresh();
					} else {
						new Alert(AlertType.WARNING, "Please check your nrc number!").showAndWait();
					}
				}

				else {
					new Alert(AlertType.WARNING, "Please check your phone number!").showAndWait();
				}

			} else {
				new Alert(Alert.AlertType.ERROR, "Please fill your data").showAndWait();
			}

		});

		btn_delete.setOnAction(e -> {

			tableView.getItems().clear();
			Register_DB.delete_Register(delete_index);
			inserttoTable();
			usernameField.clear();
			nrcField.clear();
			phoneField.clear();
			addressField.clear();
			passwordField.clear();
			filepath = null;
			uploadImage.setImage(new Image("file:///D:\\Java_Project\\Photos\\userlogo.png"));

			tableView.refresh();

		});

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
	}

}
