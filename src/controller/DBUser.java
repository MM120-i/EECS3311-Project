package controller;

import dataObjects.*;

import java.sql.*;
import java.time.LocalDate;

public class DBUser extends DBAccess {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");
    protected User user;


    public void add(User newUser) {
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
                    user= user;
                } else {
                    System.out.println("The name you are trying to set is already taken.");
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update user.
     *
     * @param newUser   the new user
     * @param field     the field
     * @param newObject the new object
     */
    public void updateUser(User newUser, int field, Object newObject) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            System.out.println("FIELD: " + field);

            switch (field) {
                case 1:
                    if (safeToAdd(newUser)) {
                        statement.execute("update person set name='" + (String) newObject + "' where name='" + newUser.getName() + "';");

                    } else {
                        System.out.println("The name you are trying to set is already taken.");
                    }
                    break;
                case 2:
                    newUser.setIsMale((int) newObject);
                    newUser.calculateBMR();
                    statement.execute("update person set isMale='" + (String) newObject + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + (String) newObject + "' where name='" + newUser.getName() + "';");
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
                    System.out.println((Integer) newObject);
                    statement.execute("update person set height='" + newUser.getHeight() + "' where name='" + newUser.getName() + "';");
                    statement.execute("update person set bmr='" + newUser.getBMR() + "' where name='" + newUser.getName() + "';");
                    System.out.println("test");
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
                    statement.execute("update person set bmr='" + (String) newObject + "' where name='" + newUser.getName() + "';");
                    break;
            }
            //CHANGE to delete based on ID NOT name
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete user.
     *
     * @param newUser the new user
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


    private static boolean safeToAdd(User obj) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();
                //CHANGE to delete based on ID NOT name
                ResultSet rs = statement.executeQuery("select name from person;");
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
