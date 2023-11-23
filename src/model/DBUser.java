package model;

import model.dataObjects.User;

import java.sql.*;
import java.time.LocalDate;

/**
 * Database access class for user-related operations.
 */
public class DBUser extends DBAccess {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");


    /**
     * Adds a new user to the database.
     *
     * @param newUser The new user to be added.
     * @return True if the user is added successfully, false otherwise.
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
        return true;
    }

    /**
     * Loads a user from the database based on the given name.
     *
     * @param name The name of the user to be loaded.
     * @return The loaded User object.
     */
    public User loadUser(String name) {
        User u = new User();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();
            //CHANGE to delete based on ID NOT name
            ResultSet rs = statement.executeQuery("select * from person where name = '" + name + "';");
            while (rs.next()) {
                u.setName(rs.getString("name"));
                u.setIsMale(rs.getInt("isMale"));
                u.setDob(LocalDate.parse(rs.getString("dob")));
                u.setHeight(rs.getInt("height"));
                u.setWeight(rs.getInt("weight"));
                u.setPrefersMetric(rs.getInt("prefermetric"));
                u.setBMR(rs.getDouble("bmr"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    /**
     * Updates user information in the database.
     *
     * @param newUser   The user object with updated information.
     * @param field     The field to be updated.
     * @param newObject The new value for the specified field.
     */
    public void updateUser(User newUser, int field, Object newObject) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();


            switch (field) {
                case 1:
                    if (safeToAdd(newUser)) {
                        statement.execute("update person set name='" + newObject + "' where name='" + newUser.getName() + "';");

                    } else {
                        System.out.println("The name you are trying to set is already taken.");
                    }
                    break;
                case 2:
                    newUser.setIsMale((int) newObject);
                    newUser.calculateBMR();
                    statement.execute("update person set isMale='" + newObject + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + newObject + "' where name='" + newUser.getName() + "';");
                    break;
                case 3:
                    newUser.setDob((LocalDate) newObject);
                    newUser.calculateBMR();
                    java.sql.Date sqlDate = new java.sql.Date(((Date) newObject).getTime());
                    statement.execute("update person set dob='" + sqlDate + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + newUser.getBMR() + "' where name='" + newUser.getName() + "';");
                    break;
                case 4:
                    newUser.setHeight((int) newObject);
                    newUser.calculateBMR();

                    statement.execute("update person set height='" + newUser.getHeight() + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + newUser.getBMR() + "' where name='" + newUser.getName() + "';");

                    break;
                case 5:
                    newUser.setWeight((int) newObject);
                    newUser.calculateBMR();
                    statement.execute("update person set weight='" + newUser.getWeight() + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + newUser.getBMR() + "' where name='" + newUser.getName() + "';");
                    break;
                case 6:
                    newUser.setPrefersMetric((Integer) newObject);
                    newUser.calculateBMR();
                    statement.execute("update person set prefermetric='" + newObject + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + newUser.getBMR() + "' where name='" + newUser.getName() + "';");

                    break;
                case 7:
                    statement.execute("update person set bmr='" + newObject + "' where name='" + newUser.getName() + "';");
                    break;
            }
            //CHANGE to delete based on ID NOT name
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param newUser The user to be deleted.
     */
    public void deleteUser(User newUser) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

            //CHANGE to delete based on ID NOT name
            statement.execute("delete from person where name='" + newUser.getName() + "';");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/**
 * Checks if it is safe to add a new user by verifying that the provided username
 * is not already present in the database.
 *
 * @param obj The User object to be checked for safe addition.
 * @return True if it is safe to add the user, false if the username already exists.
 */
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

    /**
     * Gets the currently set user.
     *
     * @return The current user.
     */
    public User getUser() {
        return user;
    }

        /**
     * Sets the current user.
     *
     * @param user The user to be set.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
