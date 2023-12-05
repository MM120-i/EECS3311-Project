package model;

import controller.UIController;
import model.dataObjects.Ingredient;
import model.dataObjects.Meal;
import model.dataObjects.Nutrient;
import model.dataObjects.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The type Database access.
 */
public class DBMeal extends DBAccess{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");
    /**
     * The User.
     */
    protected User user;

    /**
     * Instantiates a new Db meal.
     *
     * @param uic the uic
     */
    public DBMeal(UIController uic) {
        super();
        user = uic.getU();
    }

    public DBMeal() {

    }

    /**
     * Add meal
     *
     * @param newUser the new user
     * @param obj     meal object
     */
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

    private static boolean safeToAdd(Meal obj, User u) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            Statement statement = connection.createStatement();

                if (( obj).getMealType() == 4) {
                    return true;
                }

                //CHANGE to delete based on ID NOT name
                ResultSet rs = statement.executeQuery("select mealType,date from meals where person = '" +  u.getName() + "';");
                while (rs.next()) {

                    if (rs.getInt("mealtype") == (obj.getMealType()) && rs.getString("date").equals
                            (String.valueOf(java.sql.Date.valueOf(obj.getDate())))) {
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
    public List findBetween(User user, LocalDate d1, LocalDate d2) {
        Date date1 = Date.valueOf(d1);
        Date date2 = Date.valueOf(d2);
        String mealCall = "select * from meals where date between '" + date1 + "' AND '" + date2 + "' AND person='" + user.getName() + "';";
        List<Nutrient> nutrients = null;
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

    public List findAll() {
        System.out.println( "select date from meals where person='" + user.getName() +"';");
        String mealCall = "select date from meals where person='" + user.getName() +"';";
        boolean flag = false;
        ArrayList<LocalDate> dates = new ArrayList<>();
        ArrayList<Meal> meals = new ArrayList<>();
        double calSum = 0;
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(mealCall);

            while (rs.next()) { //for each foodID

                for (LocalDate ld : dates) {

                    if (LocalDate.parse(rs.getString("date")).equals(ld)) {
                        flag = true;
                    }
                }

                if (!flag) {
                    dates.add(LocalDate.parse(rs.getString(1)));
                }
                flag = false;
            }

            for (LocalDate ld : dates) {
                for (int i = 1; i < 5; i++) {
                    if (findMeal(ld, i).getMealID() != -1) {
                        meals.add(findMeal(ld, i));
                    }
                }

            }
            return meals;
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return meals;

    }

    public Meal findMeal(LocalDate date, int type) {
        System.out.println("select * from meals where person='" + user.getName() +"' and date='"  + java.sql.Date.valueOf(date) + "' and mealType='" + type + "';");
        String mealCall = "select * from meals where person='" + user.getName() +"' and date='"  + java.sql.Date.valueOf(date) + "' and mealType='" + type + "';";
        Meal result = new Meal();
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = statement.executeQuery(mealCall);

            while (rs.next()) { //for each foodID
                System.out.println(rs.getString("ingredient"));
                ingredients.add(new Ingredient(rs.getInt("ingredient"), rs.getInt("amount")));

                result = new Meal(date, rs.getInt("mealType"), ingredients);
            }
            return result;
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }
        return result;

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

    /**
     * CFG. Get food group breakdown
     *
     * @param name the name
     * @return List of percentages
     */
    public ArrayList<Double> getTotals(String name) {
        int currentIndex = 0;
        ArrayList<Double> list = new ArrayList<>();
        list.add((double) 0);
        list.add((double) 0);
        list.add((double) 0);
        list.add((double) 0);

        Map<Double, Integer> groupIndexMap = Map.of(
                9.0, 0, 16.0, 0, 11.0, 0, // veg and fruit
                8.0, 1, 18.0, 1, 19.0, 1, 20.0, 1, 12.0, 1, // grain
                1.0, 2 // milk and alternatives
        );

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
                double foodGroupID = r.getDouble(1);

                // Get the corresponding list index from the map, defaulting to 3 if not found
                int listIndex = groupIndexMap.getOrDefault(foodGroupID, 3);

                // Update the list based on the list index
                list.set(listIndex, list.get(listIndex) + Double.valueOf(r.getString(3)));
            }
        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }

        return list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
