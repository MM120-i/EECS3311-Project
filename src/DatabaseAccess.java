import java.sql.*;
import java.util.Date;

public class DatabaseAccess {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");


    public void addUser(User newUser) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            if (!findDup(newUser.getName())) {
                java.sql.Date sqlDate = new java.sql.Date(newUser.getDob().getTime());
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
                        statement.execute("update person set isMale='" + (String) newObject + "' where name='" + newUser.getName() + "';");
                        break;
                    case 3:
                        java.sql.Date sqlDate = new java.sql.Date(((Date) newObject).getTime());
                        statement.execute("update person set dob='" + sqlDate + "' where name='" + newUser.getName() + "';");
                        break;
                    case 4:
                        System.out.println((Integer) newObject);
                        statement.execute("update person set height='" + (Integer) newObject + "' where name='" + newUser.getName() + "';");
                        System.out.println("test");
                        break;
                    case 5:
                        statement.execute("update person set weight='" + (String) newObject + "' where name='" + newUser.getName() + "';");
                        break;
                    case 6:
                        statement.execute("update person set prefermetric='" + (String) newObject + "' where name='" + newUser.getName() + "';");
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
            System.out.println(name);
            while (rs.next()) {
                System.out.println(rs.getString("name"));
                if (rs.getString("name").equals(name)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void addMeal(User newUser, Meal meal) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                java.sql.Date sqlDate = java.sql.Date.valueOf(meal.getDate());
                Statement statement = connection.createStatement();

                for (Ingredient i : meal.getIngredients()) {
                    statement.execute("insert into meals (person," +
                            "mealid, date, ingredient, amount) values ('" + newUser.getName()
                            + "',' " + meal.getMealID() + "',' " + sqlDate + "',' "
                            + newUser.getHeight() + "',' " + newUser.getWeight() + "',' " + newUser.getPrefersMetric()
                            + "',' " + newUser.getBMR() + "');");
                }

        } catch (Exception e) {
            System.out.println("Duplicated");
            e.printStackTrace();
        }

    }
}