package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainScreenViewController implements Initializable{
	
	@FXML
	private AnchorPane mainPane,admin,seller;
	
	@FXML
	private Label label;
	
	@FXML
	private JFXButton btn_exit;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		admin.setOnMouseClicked(e->{
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/adminLogin.fxml"));
				Scene scene = new Scene(root);
				Stage stage=(Stage) admin.getScene().getWindow(); 
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.setTitle("Go Go Application");
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		seller.setOnMouseClicked(e->{
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/sellerLogin.fxml"));
				Scene scene = new Scene(root);
				Stage stage=(Stage) seller.getScene().getWindow(); 
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.setTitle("Go Go Application");
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
		btn_exit.setOnAction(e->{
			Alert alert=new Alert(AlertType.INFORMATION,"Are you sure that wanna to exit this application?",ButtonType.YES,ButtonType.NO);
			alert.showAndWait();
			if(alert.getResult().equals(ButtonType.YES)) {
				
				System.exit(0);
			}
		});
	}

}
