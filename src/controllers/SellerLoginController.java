package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import database.DB_Connection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SellerLoginController implements Initializable {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXTextField usernameField;

	@FXML
	private Label label;

	@FXML
	private JFXButton btn_cancel, btn_login;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		btn_cancel.setOnAction(e -> {

			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
				Scene scene = new Scene(root);
				Stage stage = (Stage) btn_cancel.getScene().getWindow();
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.setTitle("Go Go Application");
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});

		btn_login.setOnAction(e -> {

//			try {
//				Boolean cc = checkUser(usernameField.getText(),passwordField.getText());
//				if (cc == true) {
//					new Alert(AlertType.INFORMATION, "Login Successful!").showAndWait();
//					call_sellerFrame();
//				} else
//					new Alert(AlertType.WARNING, "Login Fail!").showAndWait();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}

			call_sellerFrame();

		});

	}// initial

	public Boolean checkUser(String seller_name,String seller_password) throws SQLException{
		
		String username=null;
		String password=null;
		
		Boolean ccBoolean=false;
		String st="select * from seller where seller_name=? and seller_password=?";
		PreparedStatement stmt=DB_Connection.getConnection().prepareStatement(st);
		stmt.setString(1, seller_name);
		stmt.setString(2, seller_password);
		ResultSet set=stmt.executeQuery();
		while(set.next()) {
			username=set.getString(2);
			password=set.getString(6);
			if(usernameField.getText().equals(username) && passwordField.getText().equals(password)) {
				ccBoolean=true;break;
			}
			else ccBoolean=false;
		}
		return ccBoolean;
	}
	
	public void call_sellerFrame() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/sellerFrame.fxml"));
			Scene scene = new Scene(root);
			Stage stage=(Stage) btn_login.getScene().getWindow(); 
			stage.setScene(scene);
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.setTitle("Go Go Application");
			stage.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
