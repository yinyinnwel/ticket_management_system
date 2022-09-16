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
import database.Seller_DB;
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
import models.Seller;

public class SellerAdminController implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField searchField=new JFXTextField();

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<Seller> tableView;

    @FXML
    private TableColumn<Seller, Integer> col_id=new TableColumn<Seller, Integer>("ID");

    @FXML
    private TableColumn<Seller, String> col_name=new TableColumn<Seller, String>("Name");

    @FXML
    private TableColumn<Seller, String> col_nrc=new TableColumn<Seller, String>("NRC");

    @FXML
    private TableColumn<Seller, String> col_phone=new TableColumn<Seller, String>("Phone");

    @FXML
    private TableColumn<Seller, String> col_address=new TableColumn<Seller, String>("Address");

    @FXML
    private TableColumn<Seller, String> col_password=new TableColumn<Seller, String>("Password");

    @FXML
    private TableColumn<Seller, String> col_image=new TableColumn<Seller, String>("Image");

    @FXML
    private JFXTextField usernameField=new JFXTextField();

    @FXML
    private JFXTextField phoneField=new JFXTextField();

    @FXML
    private JFXTextField nrcField=new JFXTextField();

    @FXML
    private JFXTextField addressField=new JFXTextField();

    @FXML
    private JFXButton btn_update;

    @FXML
    private JFXButton btn_delete;

    @FXML
    private ImageView uploadImage;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXPasswordField passwordField=new JFXPasswordField();
    
   	
   	Seller index;
	int delete_index;
	BufferedImage bufImg = null;
	
	String filepath = null;
	FileChooser filechooser = new FileChooser();
	
	ArrayList<Seller> sellerArrayList=(ArrayList<Seller>) Seller_DB.getSellerLists();
   	ObservableList<Seller> oblist = FXCollections.observableArrayList(sellerArrayList);

    @FXML
    void onFileChooser(MouseEvent event) {
    	
    	File file = filechooser.showOpenDialog(null);
    	uploadImage.setImage(new Image(file.toURI().toString()));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col_id.setCellValueFactory(new PropertyValueFactory<Seller, Integer>("seller_id"));
		col_name.setCellValueFactory(new PropertyValueFactory<Seller, String>("seller_name"));
		col_nrc.setCellValueFactory(new PropertyValueFactory<Seller, String>("seller_nrc"));
		col_phone.setCellValueFactory(new PropertyValueFactory<Seller, String>("seller_phone"));
		col_address.setCellValueFactory(new PropertyValueFactory<Seller, String>("seller_address"));
		col_password.setCellValueFactory(new PropertyValueFactory<Seller, String>("seller_password"));
		col_image.setCellValueFactory(new PropertyValueFactory<Seller, String>("seller_image"));

		tableView.setItems(oblist);

		inserttoTable();

		// -----------------------------------------

		btn_add.setOnAction(e -> {
			TextField[] fields = { usernameField, phoneField, passwordField, addressField, nrcField };
			if (checkTextfield_Empty(fields)) {
				tableView.getItems().clear();
				if (phoneField.getText().length() == 11 && phoneField.getText().startsWith("09")) {

					if (nrcField.getText().split("N\\)")[1].length() >= 6) {

						Seller seller = new Seller(0, usernameField.getText(), nrcField.getText(),
								phoneField.getText(), addressField.getText().toLowerCase(), passwordField.getText(),
								filepath);
						Seller_DB.addSeller(seller);
						inserttoTable();

						usernameField.clear();
						addressField.clear();
						nrcField.clear();
						phoneField.clear();
						passwordField.clear();
						filepath = null;
						uploadImage.setImage(new Image("file:///D:\\Java_Project\\Photos\\userlogo.png"));
					} else {
						new Alert(AlertType.CONFIRMATION, "Please Check Your NRC").showAndWait();
					}

				} else {
					new Alert(AlertType.WARNING, "Please check your phone numbers!").showAndWait();
				}

			} else {
				new Alert(Alert.AlertType.ERROR, "Please fill your data").showAndWait();
			}
		});

//-------------------------------------------------

		btn_update.setOnAction(e -> {
			TextField[] fields = { usernameField, phoneField, nrcField, passwordField, addressField };
			if (checkTextfield_Empty(fields)) {

				if (phoneField.getText().length() == 11 && phoneField.getText().startsWith("09")) {
					if (nrcField.getText().split("N\\)")[1].length() >= 6) {
						tableView.getItems().clear();
						try {
							Seller seller = new Seller(delete_index, usernameField.getText(),
									nrcField.getText(), phoneField.getText(), addressField.getText().toLowerCase(),
									passwordField.getText(), filepath);
							Seller_DB.updateSeller(seller);
							inserttoTable();
							usernameField.clear();
							nrcField.clear();
							phoneField.clear();
							addressField.clear();
							passwordField.clear();
							filepath = null;
							uploadImage.setImage(new Image("file:///D:\\Java_Project\\Photos\\userlogo.png"));

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

//------------------------------------------

		btn_delete.setOnAction(e -> {

			tableView.getItems().clear();
			Seller_DB.delete_Seller(delete_index);
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
	
//-----------------------------------------------

		tableView.setRowFactory(tv -> {
			TableRow<Seller> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					index = tableView.getSelectionModel().getSelectedItem();
					delete_index = index.getSeller_id();
					System.out.println(delete_index);
					String st = "select * from seller where seller_id=" + index.getSeller_id();
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

	// ----------------------------------------

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {

			if (newValue != null && !newValue.isEmpty()) {

				String text = searchField.getText().trim().toLowerCase();

				search(text);

			} else {
				search("");
			}
		});
		
	}//initial
	
	public void search(String text) {
		System.out.println(text);

		ObservableList<Seller> sellerss = FXCollections.observableArrayList(sellerArrayList);

		sellerss = sellerss.filtered(se -> {

			return  se.getSeller_name().toLowerCase().contains(text) || String.valueOf(se.getSeller_id()).contains(text);
		});

		tableView.setItems(sellerss);
		tableView.refresh();

	}
   

	public void inserttoTable() {
		ArrayList<Seller> sellers = (ArrayList<Seller>) Seller_DB.getSellerLists();
		ObservableList<Seller> seList = FXCollections.observableArrayList(sellers);
		tableView.setItems(seList);

	}

	public static boolean checkTextfield_Empty(TextField[] fields) {
		boolean flag = true;
		for (TextField field : fields) {
			if (field.getText().trim().isEmpty() == true) {
				flag = false;
			}
		}
		return flag;

	}

}
