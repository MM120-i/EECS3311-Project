package model;

import model.dataObjects.Nutrient;
import model.dataObjects.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Database access.
 */
public class DBAccess {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");
    /**
     * The User.
     */
    protected User user;

    /**
     * Getting all the list of users
     *
     * @return List of the names
     */
    public ArrayList<String> getUsers() {
        ArrayList<String> names = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

            //CHANGE to delete based on ID NOT name
            ResultSet rs = statement.executeQuery("select name from person;");
            while (rs.next()) {
                names.add(rs.getString(1));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return names;
    }


    /**
     * Find nutrients.
     *
     * @param foodID the food id
     * @param amount the amount
     * @return the list
     */
    List findNutrients(int foodID, double amount) {
        List<Nutrient> nutrients = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT B.nutrientunit, B.name, B.nutrientunit, A.nutrientvalue * " + amount + " AS modified_value FROM nutrientname B JOIN nutrientamounts A ON B.NutrientID = A.NutrientID WHERE A.nutrientvalue > 0 AND A.FOODID = '" + foodID + "';");

            while (rs.next()) {
                nutrients.add(new Nutrient(rs.getString("name"), rs.getDouble("modified_value"), rs.getString("nutrientunit")));
            }

        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return nutrients;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
    }
}
