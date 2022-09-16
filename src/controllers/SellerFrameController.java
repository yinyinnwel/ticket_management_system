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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SellerFrameController implements Initializable{

    @FXML
    private AnchorPane mainPane;

    @FXML
    private AnchorPane busbooking;

    @FXML
    private AnchorPane airbooking;

    @FXML
    private AnchorPane busticket;

    @FXML
    private AnchorPane airticket;

    @FXML
    private AnchorPane logout;

    @FXML
    private StackPane stackPane;

    @FXML
    void airbooking(MouseEvent event) {
    	loadSellerView("airLineBooking");
    }

    @FXML
    void airticket(MouseEvent event) {
    	loadSellerView("airLineTicket");
    }

    @FXML
    void busbooking(MouseEvent event) {
    	loadSellerView("busbooking");
    }

    @FXML
    void busticket(MouseEvent event) {
    	loadSellerView("busTicket");
    }

    @FXML
    void logout(MouseEvent event) {
    	Alert alert=new Alert(AlertType.INFORMATION,"Are you sure that wanna to logout?",ButtonType.YES,ButtonType.NO);
		alert.showAndWait();
		if(alert.getResult().equals(ButtonType.YES)) {
			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
				Scene scene = new Scene(root);
				Stage stage=(Stage) logout.getScene().getWindow(); 
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
    
    public void loadSellerView(String viewName) {
		Parent root;
		try {
			root = FXMLLoader.load(SellerFrameController.class.getResource("../view/"+ viewName+".fxml"));
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
	}

}




