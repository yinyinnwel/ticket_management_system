package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AdminFrameController implements Initializable{
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	private AnchorPane btn_busLine,btn_airLine,btn_register,btn_logout,btn_seller;

	@FXML
	void btn_busLine(MouseEvent e) {
		loadAdminView("busLineAdmin");
		
	}
	
	@FXML
	void btn_airLine(MouseEvent e) {
		loadAdminView("airLineAdmin");
		
	}
	
	
	@FXML
	void btn_seller(MouseEvent e) {
		loadAdminView("sellerView");
		
	}
	
	@FXML
	void btn_register(MouseEvent e) {
		loadAdminView("RegisterView");
		
	}
	
	@FXML
	void btn_logout(MouseEvent e) {
		Alert alert=new Alert(AlertType.INFORMATION,"Are you sure that wanna to logout?",ButtonType.YES,ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult().equals(ButtonType.YES)) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
				Scene scene = new Scene(root);
				Stage stage=(Stage) btn_logout.getScene().getWindow(); 
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
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		
	}
	
	public void loadAdminView(String viewName) {
		Parent root;
		try {
			root = FXMLLoader.load(AdminFrameController.class.getResource("../view/"+ viewName+".fxml"));
			stackPane.getChildren().clear();
			stackPane.getChildren().add(root);
			Stage stage=(Stage) stackPane.getScene().getWindow(); 
			stage.centerOnScreen();
			stage.setResizable(false);
			stage.setTitle("Go Go Application");
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
