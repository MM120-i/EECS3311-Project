package controller;

import dataObjects.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * The type Database access.
 */
public class DBMeal extends DBAccess{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");
    protected User user;

    public void add(User newUser, Meal obj) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
                Meal meal = (Meal) obj;
                java.sql.Date sqlDate = java.sql.Date.valueOf(meal.getDate());
                if (safeToAdd(meal)) {
                    int mid = findNextMealID() + 1;
                    for (Ingredient i : meal.getIngredients()) {
                        statement.execute("insert into meals (person," +
                                "mealid, date, ingredient, amount, mealtype) values ('" + newUser.getName()
                                + "',' " + mid + "',' " + sqlDate + "',' "
                                + i.getIngredientNum() + "',' " + i.getAmount() + "',' " + meal.getMealType() + "');");
                    }
                } else {
                    System.out.println("You've already added this meal for this day");
                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean safeToAdd(Object obj) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

                if (((Meal) obj).getMealType() == 4) {
                    return true;
                }

                //CHANGE to delete based on ID NOT name
                ResultSet rs = statement.executeQuery("select mealType,date from meals;");
                while (rs.next()) {
                    if (rs.getInt("mealtype") == (((Meal) obj).getMealType()) && rs.getString("date").equals
                            (String.valueOf(java.sql.Date.valueOf(((Meal) obj).getDate())))) {
                        System.out.println("This meal has already been entered for this date");
                        return false;

                    }
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * Finds the next available meal ID in the database.
     *
     * @return the next available meal ID
     */
    private static int findNextMealID() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            ResultSet r = statement.executeQuery("select max(mealid) from meals");
            if (r.next()) {
                return r.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Breakdown meal.
     *
     * @param user the user
     */
    public List findBetween(User user, LocalDate d1, LocalDate d2, Object obj) {
        Date date1 = Date.valueOf(d1);
        Date date2 = Date.valueOf(d2);
        String mealCall = "select * from meals where date between '" + date1 + "' AND '" + date2 + "' AND person='" + user.getName() + "';";
        String exerciseCall = "select * from exercise where date between '" + date1 + "' AND '" + date2 + "' AND person='" + user.getName() + "';";
        List<Nutrient> nutrients = null;
        List<Exercise> exercises = new ArrayList<>();
        double calSum = 0;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ResultSet rs = statement.executeQuery(mealCall);

                while (rs.next()) { //for each foodID
                    DBAccess dba = new DBAccess();
                    nutrients = dba.findNutrients(
                            (rs.getInt("ingredient")),
                            rs.getDouble("amount"));
                    for (Nutrient n : nutrients) {
                        //System.out.println(n.getName());
                        //System.out.println("calories");
                        //System.out.println(n.getAmount() + n.getUnit());


                    }
                }
                return nutrients;
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return nutrients;

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
