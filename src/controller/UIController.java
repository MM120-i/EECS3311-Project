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
     * @param name   the name
     * @param maleRB the male rb
     * @param height the height
     * @param weight the weight
     * @param dob    the dob
     * @return boolean
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
     * Exercise creation
     *
     * @param date      the date
     * @param time      the time
     * @param intensity the intensity
     * @param duration  the duration
     * @param type      the type
     */
    public Exercise exerciseCreation(Exercise exercise) {

        u.calculateBMR();

        exercise.calBurned((u.getBMR()/24) * ((exercise.getDuration())/60));

        DBExercise da = new DBExercise();
        da.add(u, exercise);
        return exercise;
    }

    /**
     * Get list of exercises
     *
     * @return exercises
     */
    public List getExercises() {
        DBExercise da = new DBExercise();
        return da.findBetween(u, LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20));
    }

    /**
     * Get regular burn rate.
     *
     * @param exercises the exercises
     * @return regular burn over time
     */
    public double getRegularBurnOverTime(List<Exercise> exercises) {
        long total = LocalDate.of(2021, 5, 28).until(LocalDate.of(2023, 11,20), DAYS);

        double totalCals = (total * 2000);

        return totalCals;
    }

    /**
     * Alternative Getting nutrients method
     *
     * @param amount the amount
     * @param l1     the l 1
     * @param l2     the l 2
     * @return x nutrients 2
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
     * Get total cals burned per day
     *
     * @param l1 the l 1
     * @param l2 the l 2
     * @return cals burned
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
     * Getting list of nutrients and values
     *
     * @param amount the amount
     * @param l1     the l 1
     * @param l2     the l 2
     * @return x nutrients
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
     * Getting calories consumed
     *
     * @param amount the amount
     * @param l1     the l 1
     * @param l2     the l 2
     * @return calories consumed
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


    private int amountPerDay(int totalCals, LocalDate l1, LocalDate l2) {
        long days = DAYS.between(l1, l2) + 1;
        return (int) (totalCals/days);
    }

    /**
     * Instantiates a new Ui controller.
     *
     * @param u the u
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
     * Find 5 top nutrients
     *
     * @param list the list
     * @return array list
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

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
}
