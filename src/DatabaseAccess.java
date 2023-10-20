import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * The type Database access.
 */
public class DatabaseAccess {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");


    /**
     * Add user.
     *
     * @param newUser the new user
     */
    public void addUser(User newUser) {
        newUser.calculateBMR();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            if (!findDup(newUser.getName())) {
                java.sql.Date sqlDate = java.sql.Date.valueOf(newUser.getDob());
                Statement statement = connection.createStatement();
                statement.execute("insert into person (name," +
                        "isMale, dob, height, weight, prefermetric, bmr) values ('" + newUser.getName()
                        + "',' " + newUser.getIsMale() + "',' " + sqlDate + "',' "
                        + newUser.getHeight() + "',' " + newUser.getWeight() + "',' " + newUser.getPrefersMetric()
                        + "',' " + newUser.getBMR() + "');");
            } else {
                System.out.println("The name you are trying to set is already taken.");
            }
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
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
                    if (!findDup(newUser.getName())) {
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

    private static boolean findDup(String name) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

            //CHANGE to delete based on ID NOT name
            ResultSet rs = statement.executeQuery("select name from person;");
            while (rs.next()) {
                if (rs.getString("name").equals(name)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean findDupMeal(Meal m) {
        System.out.println(m.getMealType());
        if (m.getMealType() == 4) {
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

            //CHANGE to delete based on ID NOT name
            ResultSet rs = statement.executeQuery("select mealType,date from meals;");
            while (rs.next()) {
                if (rs.getInt("mealtype") == (m.getMealType()) && rs.getString("date").equals(String.valueOf(java.sql.Date.valueOf(m.getDate())))) {
                    System.out.println("This meal has already been entered for this date");
                    return true;

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

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
     * Add meal.
     *
     * @param newUser the new user
     * @param meal    the meal
     */
    public void addMeal(User newUser, Meal meal) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            java.sql.Date sqlDate = java.sql.Date.valueOf(meal.getDate());
            Statement statement = connection.createStatement();
            if (!findDupMeal(meal)) {
                int mid = findNextMealID() + 1;
                for (Ingredient i : meal.getIngredients()) {
                    statement.execute("insert into meals (person," +
                            "mealid, date, ingredient, amount, mealtype) values ('" + newUser.getName()
                            + "',' " + mid + "',' " + sqlDate + "',' "
                            + i.getIngredientNum() + "',' " + i.getAmount() + "',' " + meal.getMealType() + "');");
                }
            }

        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }

    }

    /**
     * Add exercise.
     *
     * @param newUser  the new user
     * @param exercise the exercise
     */
    public void addExercise(User newUser, Exercise exercise) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            java.sql.Date sqlDate = java.sql.Date.valueOf(exercise.getDate());
            Statement statement = connection.createStatement();
            statement.execute("insert into exercise (person," +
                    "date, duration, type, intensity, calburned) values ('" + newUser.getName()
                    + "',' " + sqlDate + "',' "
                    + exercise.getDuration() + "',' " + exercise.getType() +  "',' " + exercise.getIntensity() + "',' " + exercise.getCalBurned() + "');");

        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }

    }

    /**
     * Breakdown meal.
     *
     * @param user the user
     */
    public void breakdownMeal(User user) {

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery("select * from meals where person='" + user.getName() + "';");

            int currentID = 1;
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            int mealType = 0;
            LocalDate date = LocalDate.now();
            while (rs.next())  {
                if (rs.getInt("mealid") == (currentID)) {
                    ingredients.add(new Ingredient(rs.getInt("ingredient"), rs.getDouble("amount")));
                    System.out.println(ingredients.size());
                    date = rs.getDate("date").toLocalDate();
                    mealType = rs.getInt("mealtype");
                } else {
                    rs.previous();
                    user.getMeals().add(new Meal(currentID, rs.getDate("date").toLocalDate(), rs.getInt("mealtype"), ingredients));
                    ingredients = new ArrayList<>();
                    currentID++;
                }
            }
            user.getMeals().add(new Meal(currentID, date, mealType, ingredients));
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }

    }

    /**
     * Find nutrients.
     *
     * @param foodID the food id
     */
    public List findNutrients(int foodID) {
        List<Nutrient> nutrients = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT B.name, B.nutrientunit, A.nutrientvalue FROM nutrientname B JOIN nutrientamounts A ON B.NutrientID = A.NutrientID WHERE A.FOODID = '" + foodID + "';");

            while (rs.next()) {
                nutrients.add(new Nutrient(rs.getString("name"), rs.getString("nutrientvalue")));
            }
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return nutrients;
    }
}