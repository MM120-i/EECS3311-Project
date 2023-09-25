import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class JDBCTest {
    public static void main(String[] args) {

        User newUser = new User();
        newUser.setFirstName("marios");
        newUser.setLastName("soccer");
        newUser.setDob(new Date( ));
        newUser.setIsMale(1);
        newUser.setHeight(180);
        newUser.setWeight(50);
        newUser.setPrefersMetric(1);
        newUser.setBMR(1);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", System.getenv("SQLPASS"));
            Statement statement = connection.createStatement();

            deleteUser(newUser);

            ResultSet resultSet = statement.executeQuery("select * from person");

            while (resultSet.next()) {
                System.out.println(resultSet.getString("idperson") + resultSet.getString("firstName") + resultSet.getString("lastName") + resultSet.getString("isMale"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addUser(User newUser) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", System.getenv("SQLPASS"));

            java.sql.Date sqlDate = new java.sql.Date(newUser.getDob().getTime());
            Statement statement = connection.createStatement();
            statement.execute("insert into person (firstName, lastName," +
                    "isMale, dob, height, weight, prefermetric, bmr) values ('" + newUser.getFirstName() + "',' " +
                    newUser.getLastName() + "',' " + newUser.getIsMale() + "',' " + sqlDate + "',' "
                    + newUser.getHeight() + "',' " + newUser.getWeight() + "',' " + newUser.getPrefersMetric()
                    + "',' " + newUser.getBMR()  + "');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(User newUser) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", System.getenv("SQLPASS"));

            Statement statement = connection.createStatement();

            //CHANGE to delete based on ID NOT name
            statement.execute("delete from person where firstName='" + newUser.getFirstName() + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
