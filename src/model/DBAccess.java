package model;

import model.dataObjects.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

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
    

    private static boolean safeToAdd(Object obj) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

            if (obj instanceof User) {
                //CHANGE to delete based on ID NOT name
                ResultSet rs = statement.executeQuery("select name from person;");
                while (rs.next()) {
                    if (rs.getString("name").equals(((User) obj).getName())) {
                        return false;
                    }
                }
            } else if (obj instanceof Meal) {
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
     * @param d1   the d 1
     * @param d2   the d 2
     * @param obj  the obj
     * @return the list
     */
    public List findBetween(User user, LocalDate d1, LocalDate d2, Object obj) {
        Date date1 = Date.valueOf(d1);
        Date date2 = Date.valueOf(d2);
        String mealCall = "select * from meals where date between '" + date1 + "' AND '" + date2 + "' AND person='" + user.getName() + "';";

        String exerciseCall = "select * from exercise where date between '" + date1 + "' AND '" + date2 + "' AND person='" + user.getName() + "';";
        List<Nutrient> nutrients = new ArrayList<>();
        List<Exercise> exercises = new ArrayList<>();
        double calSum = 0;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            if (obj instanceof Meal) {
                ResultSet rs = statement.executeQuery(mealCall);

                while (rs.next()) { //for each foodID
                    List<Nutrient> temp = findNutrients(
                            (rs.getInt("ingredient")),
                            rs.getDouble("amount"));

                    for (Nutrient n : temp) {
                        nutrients.add(n);
                    }


                    for (Nutrient n : nutrients) {
                        calSum += n.getAmount() * rs.getDouble("amount");

                    }
                }
                return nutrients;
            } else if (obj instanceof Exercise) {
                ResultSet rs = statement.executeQuery(exerciseCall);
                while (rs.next()) {
                    exercises.add(new Exercise(
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("duration"),
                            rs.getString("type"),
                            rs.getInt("intensity"),
                            rs.getInt("calburned")));
                }
                return exercises;
            }


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
