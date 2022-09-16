import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
//		System.out.println(DB_Connection.getConnection());


//		Book_DB.add_Book(new Book(0,"JAVA",null));
	}
	
	@Override
	public void start(Stage arg0) throws Exception {
		{
			// TODO Auto-generated method stub

			try {

				Parent root = FXMLLoader.load(getClass().getResource("Table.fxml"));

				Scene scene = new Scene(root);

//			scene.getStylesheets().add(getClass().getResource("/DarkTheme.css").toExternalForm());

				arg0.setScene(scene);
				arg0.centerOnScreen();
				arg0.setResizable(true);
//				arg0.initStyle(StageStyle.UNDECORATED);

				arg0.show();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
