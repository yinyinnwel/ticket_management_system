import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Table_Controller implements Initializable {

    @FXML
    TextField search;
    @FXML
    TableView<Book> tableView;
    @FXML
    TableColumn<Book,Integer> col_id;
    @FXML
    TableColumn<Book,String > col_name;
    @FXML
    TableColumn<Book,byte[]> col_image;

    @FXML
    TextField name;
    @FXML
    Button browse;
    @FXML
    Button add;


    byte[] byteimage;

    ArrayList<Book> bookArrayList;

    @FXML
    public void Browse_Action(){
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg"));

        File selectedFile = fileChooser.showOpenDialog(browse.getScene().getWindow());

        if (selectedFile != null) {
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", bos);
                byteimage = bos.toByteArray();
                Image img2 = new Image(selectedFile.toURI().toString());
                ImageView view=new ImageView(img2);
                view.setFitWidth(50);
                view.setFitHeight(50);
                System.out.println(img2);
                browse.setGraphic(view);
            } catch (IOException e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
            }

        }
    }
    @FXML
    public void add_action(){
        TextField[] fields={name};
        if(checkTextfield_Empty(fields)) {

            String nameText = name.getText();

            Book_DB.add_Book(new Book(0, nameText, byteimage));
            getData();
        }else{
            new Alert(Alert.AlertType.ERROR).showAndWait();
        }
    }

    public void getData(){      //This function takes Data from database
       bookArrayList= (ArrayList<Book>) Book_DB.getBookList();
       search("");
    }

    public void connect_Column(){
        col_id.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Book,String>("name"));
        col_image.setCellFactory(new Callback<TableColumn<Book, byte[]>, TableCell<Book, byte[]>>() {
            @Override
            public TableCell<Book, byte[]> call(TableColumn<Book, byte[]> param) {
                ImageView imageView = new ImageView();
                TableCell<Book, byte[]> cell = new TableCell<Book, byte[]>() {
                    public void updateItem(byte[] item, boolean empty) {
                        if (item != null) {
                            Image img = new Image(new ByteArrayInputStream(item));
                            imageView.setImage(img);
                            imageView.setFitWidth(148);
                            imageView.setFitHeight(110);
                        }
                    }
                };
                // Attach the imageview to the cell

                cell.setGraphic(imageView);
                return cell;

            }
        });
        col_image.setCellValueFactory(new PropertyValueFactory<Book, byte[]>("image"));
        col_image.setPrefWidth(150);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
        connect_Column();

        search.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && !newValue.isEmpty()) {

                String text = search.getText().trim().toLowerCase();

                search(text);

            } else {
                search("");
            }
        });



    }

    public void search(String text){


        System.out.println(text);

        ObservableList<Book> books = FXCollections.observableArrayList(bookArrayList);

        books = books.filtered(book -> {

            return book.getName().toLowerCase().contains(text);

        });

        tableView.setItems(books);
        tableView.refresh();


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
