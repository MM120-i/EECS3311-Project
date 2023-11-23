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
 * The type Ui controller.
 */
public class UIController {

    /**
     * The U.
     */
    public User u;

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
     * Create a profile with existing name already (update)
     *
     * @param override the override
     * @param name     the name
     * @param maleRB   the male rb
     * @param height   the height
     * @param weight   the weight
     * @param dob      the dob
     * @return boolean
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
     * Exercise creation
     *
     * @param date      the date
     * @param time      the time
     * @param intensity the intensity
     * @param duration  the duration
     * @param type      the type
     */
    public void exerciseCreation(LocalDate date, LocalTime time, int intensity, int duration, String type) {

        u.calculateBMR();
        Exercise e = new Exercise(date, duration, type, intensity);

        e.calBurned(u.getBMR());

        DBExercise da = new DBExercise();
        da.add(u, e);
    }

    /**
     * Get list of exercises
     *
     * @return exercises
     */
    public List getExercises() {
        DBAccess da = new DBAccess();
        return da.findBetween(u, LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20), new Exercise());
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
     * Get total cals burned per day
     *
     * @param l1 the l 1
     * @param l2 the l 2
     * @return cals burned
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
     * Getting list of nutrients and values
     *
     * @param amount the amount
     * @param l1     the l 1
     * @param l2     the l 2
     * @return x nutrients
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
     * Getting calories consumed
     *
     * @param amount the amount
     * @param l1     the l 1
     * @param l2     the l 2
     * @return calories consumed
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
}
