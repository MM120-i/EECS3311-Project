package controller;

import model.dataObjects.Exercise;
import model.dataObjects.Meal;
import model.dataObjects.Nutrient;
import model.dataObjects.User;
import view.MainMenu;
import model.DBAccess;
import model.DBExercise;
import model.DBUser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;


/**
 * The UIController class manages user interface-related operations, including profile creation, exercise logging,
 * and nutrient consumption analysis.
 */
public class UIController {

    /**
     * The User object associated with the UIController.
     */
    public User u;

    /**
     * Creates a user profile with the specified details.
     *
     * @param name   The name of the user.
     * @param maleRB The gender of the user.
     * @param height The height of the user.
     * @param weight The weight of the user.
     * @param dob    The date of birth of the user.
     * @return True if the profile creation is successful, false otherwise.
     */
    public boolean profileCreation(String name, Boolean maleRB, String height, String weight, String dob) {
        u = new User();
        u.setName(name);
        if (maleRB) {
            u.setMale(1);
        } else {
            u.setMale(0);
        }
        u.setDob(LocalDate.parse(dob));
        u.setHeight(Integer.parseInt(height));
        u.setWeight(Integer.parseInt(weight));
        u.calculateBMR();

        DBUser da = new DBUser();

        if (!da.add(u)) {

            MainMenu mm = new MainMenu(this);
            return true;
        }
        return false;
    }


    /**
     * Creates or updates a user profile with the specified details.
     *
     * @param override Flag indicating whether to override an existing profile.
     * @param name     The name of the user.
     * @param maleRB   The gender of the user.
     * @param height   The height of the user.
     * @param weight   The weight of the user.
     * @param dob      The date of birth of the user.
     * @return True if the profile creation/update is successful, false otherwise.
     */
    public boolean profileCreation(int override, String name, Boolean maleRB, String height, String weight, String dob) {

        u = new User();
        u.setName(name);
        if (maleRB) {
            u.setMale(1);
        } else {
            u.setMale(0);
        }

        u.setDob(LocalDate.parse(dob));
        u.setHeight(Integer.parseInt(height));
        u.setWeight(Integer.parseInt(weight));
        u.calculateBMR();

        DBUser da = new DBUser();
        da.deleteUser(u);

        if (!da.add(u)) {

            MainMenu mm = new MainMenu(this);
            return true;
        }
        return false;
    }


    /**
     * Logs an exercise activity for the user.
     *
     * @param date      The date of the exercise.
     * @param time      The time of the exercise.
     * @param intensity The intensity of the exercise.
     * @param duration  The duration of the exercise.
     * @param type      The type of exercise.
     */
    public void exerciseCreation(LocalDate date, LocalTime time, int intensity, int duration, String type) {

        u.calculateBMR();
        Exercise e = new Exercise(date, duration, type, intensity);

        e.calBurned(u.getBMR());

        DBExercise da = new DBExercise();
        da.add(u, e);
    }

    /**
     * Retrieves a list of exercises logged by the user.
     *
     * @return A list of Exercise objects.
     */
    public List getExercises() {
        DBAccess da = new DBAccess();
        return da.findBetween(u, LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20), new Exercise());
    }

    /**
     * Calculates the total regular burn rate over a specified time period.
     *
     * @param exercises The list of exercises.
     * @return The total regular burn rate.
     */
    public double getRegularBurnOverTime(List<Exercise> exercises) {
        long total = LocalDate.of(2021, 5, 28).until(LocalDate.of(2023, 11,20), DAYS);

        double totalCals = (total * 2000);

        return totalCals;
    }

    /**
     * Retrieves a list of nutrients consumed by the user within a specified time period.
     *
     * @param amount The number of nutrients to retrieve.
     * @param l1     The start date.
     * @param l2     The end date.
     * @return A list of Nutrient objects.
     */
    public List getXNutrients2(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.findBetween(u, l1, l2, new Meal());

        for (int i = 0; i < amount && i < nutrients.size(); i++) {
            if (nutrients.get(i).getName().equals("ENERGY (KILOCALORIES)")) {
                nutrients.remove(i);
            }
            result.add(nutrients.get(i));
        }
        return result;
    }

    /**
     * Calculates the total calories burned by the user within a specified time period.
     *
     * @param l1 The start date.
     * @param l2 The end date.
     * @return The total calories burned.
     */
    public int getCalsBurned(LocalDate l1, LocalDate l2) {
        DBAccess da = new DBAccess();
        List<Exercise> exercises = da.findBetween(u, l1, l2, new Exercise());

        int result = 0;
        for (Exercise e : exercises) {
            result += e.getCalBurned();
        }

        return amountPerDay(result, l1, l2);

    }

    /**
     * Retrieves a list of nutrients consumed by the user within a specified time period.
     *
     * @param amount The number of nutrients to retrieve.
     * @param l1     The start date.
     * @param l2     The end date.
     * @return A list of Nutrient objects.
     */
    public List getXNutrients(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.findBetween(u, l1, l2, new Meal());
        for (int i = 0; i < nutrients.size() && i < 20; i++) {

            result.add(nutrients.get(i));

        }
        return result;
    }

    /**
     * Calculates the total calories consumed by the user within a specified time period.
     *
     * @param amount The number of nutrients to retrieve.
     * @param l1     The start date.
     * @param l2     The end date.
     * @return The total calories consumed.
     */
    public int getCaloriesConsumed(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBAccess da = new DBAccess();
        int sum = 0;
        List<Nutrient> nutrients = da.findBetween(u, l1, l2, new Meal());

        for (int i = 0; i < nutrients.size(); i++) {

            if (nutrients.get(i).getName().contains("KILOJOULES")) {


                sum += (int) nutrients.get(i).getAmount();
            }
            result.add(nutrients.get(i));
        }

        if (amount == 1) {
            return amountPerDay(sum, l1, l2);
        } else {
            return sum;
        }
    }

    /**
     * Calculates the average amount per day given the total amount and time period.
     *
     * @param totalCals The total amount.
     * @param l1        The start date.
     * @param l2        The end date.
     * @return The average amount per day.
     */
    private int amountPerDay(int totalCals, LocalDate l1, LocalDate l2) {
        long days = DAYS.between(l1, l2) + 1;
        return (int) (totalCals/days);
    }

    /**
     * Instantiates a new UIController with the specified user.
     *
     * @param u The User object.
     */
    public UIController(User u) {
        this.u = u;
    }

    /**
     * Instantiates a new UIController.
     */
    public UIController() {
    }


    /**
     * Finds the top five nutrients from the given list.
     *
     * @param list The list of nutrients.
     * @return An ArrayList of the top five nutrients.
     */
    public ArrayList<Nutrient> findFiveTop(ArrayList<Nutrient> list) {
        ArrayList<Nutrient> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Nutrient max = new Nutrient("Null", 0, "");
            for (Nutrient n : list) {
                if (n.getAmount() > max.getAmount()) {
                    max = n;
                }
            }
            list.remove(max);
            result.add(max);
        }
        return result;
    }
}
