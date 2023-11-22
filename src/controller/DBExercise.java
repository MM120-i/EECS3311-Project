package controller;

import dataObjects.*;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

/**
 * The type Database access.
 */
public class DBExercise extends DBAccess{

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nutrientapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = System.getenv("SQLPASS");
    protected User user;

    public boolean add(User newUser, Object obj) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            Statement statement = connection.createStatement();
                Exercise exercise = (Exercise) obj;
                java.sql.Date sqlDate = java.sql.Date.valueOf(exercise.getDate());
                statement.execute("insert into exercise (person," +
                        "date, duration, type, intensity, calburned) values ('" + newUser.getName()
                        + "',' " + sqlDate + "',' "
                        + exercise.getDuration() + "',' " + exercise.getType() + "',' " + exercise.getIntensity() + "',' " + exercise.getCalBurned() + "');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
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
