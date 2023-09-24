import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCTest {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", System.getenv("SQLPASS"));

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from person");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("idperson") + resultSet.getString("firstName") + resultSet.getString("lastName"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
