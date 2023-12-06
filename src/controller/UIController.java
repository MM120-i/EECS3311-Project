package controller;

import model.DBExercise;
import model.DBMeal;
import model.DBUser;
import model.dataObjects.Exercise;
import model.dataObjects.Nutrient;
import model.dataObjects.User;
import view.MainMenu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


/**
 * The type Ui controller.
 */
public class UIController {

    /**
     * The U.
     */
    private User u;

    /**
     * Create a profile.
     *
     * @param u The user object containing profile information.
     * @return True if the profile creation is successful; false otherwise.
     */
    public boolean profileCreation(User u) {
        u.calculateBMR();
        DBUser da = new DBUser();
        if (!da.add(u)) {
            new MainMenu(this);
            return true;
        }
        return false;
    }


    /**
     * Exercise creation.
     *
     * @param exercise The Exercise object containing exercise details.
     * @return The created Exercise object.
     */
    public Exercise exerciseCreation(Exercise exercise) {

        u.calculateBMR();

        exercise.calBurned((u.getBMR()/24) * ((exercise.getDuration())/60));

        DBExercise da = new DBExercise();
        da.add(u, exercise);
        return exercise;
    }

    /**
     * Get list of exercises between specified dates.
     *
     * @return List of Exercise objects.
     */
    public List getExercises() {
        DBExercise da = new DBExercise();
        return da.findBetween(u, LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20));
    }

    /**
     * Get regular burn rate over time.
     *
     * @param exercises List of Exercise objects.
     * @return Regular burn over time.
     */
    public double getRegularBurnOverTime(List<Exercise> exercises) {
        long total = LocalDate.of(2021, 5, 28).until(LocalDate.of(2023, 11,20), DAYS);

        double totalCals = (total * 2000);

        return totalCals;
    }

    /**
     * Alternative method for getting nutrients.
     *
     * @param amount The number of nutrients to retrieve.
     * @param l1     The starting date.
     * @param l2     The ending date.
     * @return List of Nutrient objects.
     */
    public List getXNutrients2(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBMeal da = new DBMeal();
        List<Nutrient> nutrients = da.findBetween(u, l1, l2);

        for (int i = 0; i < amount && i < nutrients.size(); i++) {
            if (nutrients.get(i).getName().equals("ENERGY (KILOCALORIES)")) {
                nutrients.remove(i);
            }
            result.add(nutrients.get(i));
        }
        return result;
    }

    /**
     * Get total calories burned per day between specified dates.
     *
     * @param l1 The starting date.
     * @param l2 The ending date.
     * @return Total calories burned per day.
     */
    public int getCalsBurned(LocalDate l1, LocalDate l2) {
        DBExercise da = new DBExercise();
        List<Exercise> exercises = da.findBetween(u, l1, l2);

        int result = 0;
        for (Exercise e : exercises) {
            result += e.getCalBurned();
        }

        return amountPerDay(result, l1, l2);

    }

    /**
     * Get list of nutrients and values between specified dates.
     *
     * @param amount The number of nutrients to retrieve.
     * @param l1     The starting date.
     * @param l2     The ending date.
     * @return List of Nutrient objects.
     */
    public List getXNutrients(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBMeal da = new DBMeal();
        List<Nutrient> nutrients = da.findBetween(u, l1, l2);
        for (int i = 0; i < nutrients.size() && i < 20; i++) {

            result.add(nutrients.get(i));

        }
        return result;
    }

    /**
     * Get calories consumed between specified dates.
     *
     * @param amount The number of calories to retrieve.
     * @param l1     The starting date.
     * @param l2     The ending date.
     * @return Calories consumed.
     */
    public int getCaloriesConsumed(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBMeal da = new DBMeal();
        int sum = 0;
        List<Nutrient> nutrients = da.findBetween(u, l1, l2);

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
     * Calculate amount per day.
     *
     * @param totalCals The total calories.
     * @param l1        The starting date.
     * @param l2        The ending date.
     * @return Amount per day.
     */
    private int amountPerDay(int totalCals, LocalDate l1, LocalDate l2) {
        long days = DAYS.between(l1, l2) + 1;
        return (int) (totalCals/days);
    }

    /**
     * Instantiates a new Ui controller with the specified user.
     *
     * @param u The User object.
     */
    public UIController(User u) {
        this.u = u;
    }

    /**
     * Instantiates a new Ui controller.
     */
    public UIController() {
    }


    /**
     * Find 5 top nutrients from the given list.
     *
     * @param list The list of Nutrient objects.
     * @return An ArrayList containing the top 5 nutrients.
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

    /**
     * Get the current user associated with this UIController.
     *
     * @return The User object.
     */
    public User getU() {
        return u;
    }
    
    /**
     * Set the user associated with this UIController.
     *
     * @param u The User object to set.
     */
    public void setU(User u) {
        this.u = u;
    }
}
