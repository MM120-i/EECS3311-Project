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

    public DBMeal(UIController uic) {
        super();
    }

    public void add(User newUser, Meal obj) {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
                Meal meal = obj;
                java.sql.Date sqlDate = java.sql.Date.valueOf(meal.getDate());
                if (safeToAdd(meal, newUser)) {
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

    private static boolean safeToAdd(Object obj, User u) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

                if (((Meal) obj).getMealType() == 4) {
                    return true;
                }

                //CHANGE to delete based on ID NOT name
                ResultSet rs = statement.executeQuery("select mealType,date from meals where person = '" +  u.getName() + "';");
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
                    nutrients = this.findNutrients(
                            (rs.getInt("ingredient")),
                            rs.getDouble("amount"));
                    for (Nutrient n : nutrients) {



                    }
                }
                return nutrients;
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return nutrients;

    }


    /**
     * Find nutrients.
     *
     * @param foodID the food id
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

    public ArrayList<Double> getTotals(String name) {
        int currentIndex = 0;
        ArrayList<Double> list = new ArrayList<>();
        list.add((double) 0);
        list.add((double) 0);
        list.add((double) 0);
        list.add((double) 0);
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            ResultSet r = statement.executeQuery("SELECT fn.foodgroupID,\n" +
                    "       SUM(ft.total_amount) AS total_group_amount,\n" +
                    "       SUM(ft.total_amount) * 100.0 / SUM(SUM(ft.total_amount)) OVER () AS percentage\n" +
                    "FROM (\n" +
                    "    SELECT ingredient, SUM(amount) AS total_amount\n" +
                    "    FROM meals\n" +
                    "    WHERE person = '" + name + "'\n" +
                    "    GROUP BY ingredient\n" +
                    ") ft\n" +
                    "JOIN foodname fn ON ft.ingredient = fn.foodID\n" +
                    "GROUP BY fn.foodgroupID\n");
            while (r.next()) {

                double value = r.getDouble(1);

                if (r.getDouble(1) == (9) || r.getDouble(1) == (16) || r.getDouble(1) == (11)) { //veg and fruit
                    list.set(0, list.get(0) + Double.valueOf(r.getString(3)));

                } else if (r.getDouble(1) == (8) || r.getDouble(1) == (18) || r.getDouble(1) == (19)
                        || r.getDouble(1) == (20) || r.getDouble(1) == (12)) {//grain
                    list.set(1, list.get(1) + Double.valueOf(r.getString(3)));

                } else if (r.getDouble(1) == (1)) {// milk and alternatives
                    list.set(2, list.get(2) + Double.valueOf(r.getString(3)));

                } else {

                    list.set(3, list.get(3) + Double.valueOf(r.getString(3)));

                }

            }



        return list;
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
