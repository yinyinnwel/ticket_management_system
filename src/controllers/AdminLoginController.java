package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

public class AdminLoginController implements Initializable{
	
	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Label label;
	
	@FXML
	private JFXButton btn_register,btn_login;
	
	@FXML
	private JFXTextField usernameField;
	
	@FXML
	private JFXPasswordField passwordField;
	
	@FXML
	private Label error;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		btn_register.setOnAction(e->{
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/adminRegister.fxml"));
				Scene scene = new Scene(root);
				Stage stage=(Stage) btn_register.getScene().getWindow(); 
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.setTitle("Go Go Application");
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		});
		
		btn_login.setOnAction(e->{
//			try {
//				Boolean cc=checkUser();
//				if(cc==true) {
//					new Alert(AlertType.INFORMATION,"Login Successful!").showAndWait();
//					call_adminFrame();
//				}
//				else new Alert(AlertType.WARNING,"Login Fail!").showAndWait();
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
			call_adminFrame();

		});
		
	}//initial
	
	
	public Boolean checkUser() throws Exception{
		
		String username=usernameField.getText();
		String password=passwordField.getText();
		try {
			if(username.isEmpty()) {
				throw new Exception("username is empty");
			}
			if(password.isEmpty()) {
				throw new Exception("password is empty");
			}
		} catch (Exception e) {
			error.setText(e.getMessage());
		}
		
		Boolean ccBoolean=false;
		String st="select * from register";
		Statement stmt=DB_Connection.getConnection().createStatement();
		ResultSet set=stmt.executeQuery(st);
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
	
	public void call_adminFrame() {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("../view/adminFrame.fxml"));
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
