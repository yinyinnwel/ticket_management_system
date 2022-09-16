import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Book_DB {

    public static List<Book> getBookList(){

        System.out.println("Performing getCustomerList...");
        List<Book> list = new ArrayList<Book>();

        try {
            Connection con = DB_Connection.getConnection();
            String sql = "select * from books ";
            PreparedStatement getstatement = con.prepareStatement(sql);

            ResultSet rs = getstatement.executeQuery();
            while (rs.next()) {

                byte[] b = null;
                Blob ablob = rs.getBlob(3);

                if (ablob != null) {
                    b = ablob.getBytes(1, (int) ablob.length());
                }
                Book book=new Book(rs.getInt(1),rs.getString(2),b);

                list.add(book);
            }
            con.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println("no results");

        }
        return list;


    }

    public static void add_Book(Book book){

        Connection connection=DB_Connection.getConnection();
        try {
            PreparedStatement statement=connection.prepareStatement("insert into books(name,image) values (?,?) ");

            statement.setString(1,book.getName());

            if(book.getImage()==null){
                statement.setBinaryStream(2,null);
            }else {
                ByteArrayInputStream out = new ByteArrayInputStream(book.getImage());
                statement.setBinaryStream(2,out);
            }


            statement.executeUpdate();
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) {

        ArrayList<Book> books= (ArrayList<Book>) getBookList();

        books.forEach(e->{
            System.err.println(e.getName());
        });
    }

}
