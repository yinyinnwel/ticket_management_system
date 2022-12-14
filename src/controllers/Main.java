package controllers;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application{
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../view/main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.setResizable(true);
		primaryStage.show();
		primaryStage.setTitle("Go Go Application");
		//primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("../view/images/logo.jpg")));
		primaryStage.setResizable(false);
		
	}
	

}
