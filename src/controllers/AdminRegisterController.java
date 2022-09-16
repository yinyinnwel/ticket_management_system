package controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import database.Register_DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Register;

public class AdminRegisterController implements Initializable {

	@FXML
	private AnchorPane mainPane;

	@FXML
	private JFXTextField usernameField;

	@FXML
	private JFXTextField nrcField;

	@FXML
	private JFXTextField phoneField;

	@FXML
	private JFXTextField addressField;

	@FXML
	private JFXPasswordField passwordField;

	@FXML
	private JFXPasswordField con_passwordField;

	@FXML
	private JFXButton btn_cancel;

	@FXML
	private JFXButton btn_register;

	@FXML
	private ImageView uploadimg;

	@FXML
	private Label lbl_img=new Label("Upload Image");
	
	String filepath = null;
	FileChooser filechooser = new FileChooser();
	ArrayList<Register> registrArrayList;

	@FXML
	void openFileChooser(MouseEvent event) {
		File file = filechooser.showOpenDialog(null);
		uploadimg.setImage(new Image(file.toURI().toString()));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btn_register.setOnAction(e -> {
			TextField[] fields = { usernameField, phoneField, nrcField, passwordField, addressField,
					con_passwordField };
			if (checkTextfield_Empty(fields)) {

				if (phoneField.getText().length() == 11 && phoneField.getText().startsWith("09")) {

					if (passwordField.getText().equals(con_passwordField.getText())) {

						if (nrcField.getText().split("N\\)")[1].length() >= 6) {

							Register register = new Register(0, usernameField.getText(),
									nrcField.getText(), phoneField.getText(), addressField.getText().toLowerCase(),
									passwordField.getText(), filepath);
							Register_DB.addRegister(register);

							new Alert(AlertType.CONFIRMATION, "Save Your Data!").showAndWait();

							@SuppressWarnings("unused")
							Stage stage = (Stage) this.btn_register.getScene().getWindow();
							// stage.close();
							usernameField.clear();
							addressField.clear();
							nrcField.clear();
							phoneField.clear();
							passwordField.clear();
							con_passwordField.clear();
						} else {
							new Alert(AlertType.CONFIRMATION, "Please Check Your NRC").showAndWait();
						}

					} else {
						new Alert(AlertType.WARNING, "Do not match with pre_password!").showAndWait();
					}

				} else {
					new Alert(AlertType.WARNING, "Please check your phone numbers!").showAndWait();
				}

			} else {
				new Alert(Alert.AlertType.ERROR, "Please fill your data").showAndWait();
			}
		});

		btn_cancel.setOnAction(e -> {
			usernameField.clear();
			addressField.clear();
			nrcField.clear();
			phoneField.clear();
			passwordField.clear();
			con_passwordField.clear();

			Parent root;
			try {
				root = FXMLLoader.load(getClass().getResource("../view/adminLogin.fxml"));
				Scene scene = new Scene(root);
				Stage stage = (Stage) btn_cancel.getScene().getWindow();
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.setResizable(false);
				stage.show();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		});

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

}
