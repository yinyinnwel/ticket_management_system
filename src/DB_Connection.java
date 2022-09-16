import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB_Connection {

	public static boolean conncetion_status = true;

	public static void main(String[] args) {
		getConnection();
	}
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// "jdbc:mysql://www.db4free.net:3306/point_of_sale?characterEncoding=utf8",
//			 "pos_root", "posroot123"

			// internet
			// "jdbc:mysql://www.db4free.net:3306/point_of_sale?characterEncoding=utf8","pos_root",
			// "posroot123"
			// local
			// "jdbc:mysql://localhost:3306/point_of_sale?characterEncoding=utf8","root", ""

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?characterEncoding=utf8&useOldAliasMetadataBehavior=true", "root", "");
			if (con != null) {
				System.out.println("Status: DB " + "connected");

			}

		} catch (Exception e) {
			DB_Connection.conncetion_status = false;

			// TODO Auto-generated catch block
//			e.printStackTrace();
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("ERROR Dialog");
			alert.setHeaderText("Check Database Connection !");
			alert.setContentText("Could not find Database Connection !");

			// Create expandable Exception.
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exceptionText = sw.toString();

			Label label = new Label("The exception stacktrace was:");

			TextArea textArea = new TextArea(exceptionText);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			textArea.setMaxWidth(Double.MAX_VALUE);
			textArea.setMaxHeight(Double.MAX_VALUE);
			GridPane.setVgrow(textArea, Priority.ALWAYS);
			GridPane.setHgrow(textArea, Priority.ALWAYS);

			GridPane expContent = new GridPane();
			expContent.setMaxWidth(Double.MAX_VALUE);
			expContent.add(label, 0, 0);
			expContent.add(textArea, 0, 1);

			// Set expandable Exception into the dialog pane.
			alert.getDialogPane().setExpandableContent(expContent);

			alert.showAndWait();
		}
		return con;

	}

	
}
