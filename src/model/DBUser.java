package model;

import model.dataObjects.User;

import java.sql.*;
import java.time.LocalDate;

/**
 * The type Db user.
 */
public class DBUser extends DBAccess {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");


    /**
     * Add user
     *
     * @param newUser the new user
     * @return boolean
     */
    public boolean add(User newUser) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            newUser.calculateBMR();
            if (safeToAdd(newUser)) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(newUser.getDob());
                statement.execute("insert into person (name," +
                        "isMale, dob, height, weight, prefermetric, bmr) values ('" + newUser.getName()
                        + "',' " + newUser.getIsMale() + "',' " + sqlDate + "',' "
                        + newUser.getHeight() + "',' " + newUser.getWeight() + "',' " + newUser.getPrefersMetric()
                        + "',' " + newUser.getBMR() + "');");
                user = user;
            } else {
                System.out.println("The name you are trying to set is already taken.");
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("returned true");
        return true;
    }

    /**
     * Load user user.
     *
     * @param name the name
     * @return User user
     */
    public User loadUser(String name) {
        User u = new User();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();
            //CHANGE to delete based on ID NOT name
            ResultSet rs = statement.executeQuery("select * from person where name = '" + name + "';");
            while (rs.next()) {
                u = new User(rs.getString("name"), rs.getInt("isMale"), LocalDate.parse(rs.getString("dob")), rs.getInt("height"),
                        rs.getInt("weight"), rs.getInt("prefermetric"), rs.getInt("bmr"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }


    private static boolean safeToAdd(User obj) {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();
            //CHANGE to delete based on ID NOT name
            ResultSet rs = statement.executeQuery("select * from person where name = '" + obj.getName() + "';");
            while (rs.next()) {
                if (rs.getString("name").equals((obj.getName()))) {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
