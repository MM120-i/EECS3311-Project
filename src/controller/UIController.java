package controller;

import dataObjects.Exercise;
import dataObjects.Meal;
import dataObjects.Nutrient;
import dataObjects.User;
import gui.ExerciseLogWindow;
import gui.ExercisePie;
import gui.MainMenu;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;


public class UIController {

    public User u;

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

    public void exerciseCreation(LocalDate date, LocalTime time, int intensity, int duration, String type) {

        u.calculateBMR();
        Exercise e = new Exercise(date, duration, type, intensity);

        e.calBurned(u.getBMR());

        DBExercise da = new DBExercise();
        da.add(u, e);
    }

    public List getExercises() {
        DBAccess da = new DBAccess();
        return da.findBetween(u, LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20), new Exercise());
    }

    public double getRegularBurnOverTime(List<Exercise> exercises) {
        long total = LocalDate.of(2021, 5, 28).until(LocalDate.of(2023, 11,20), DAYS);

        double totalCals = (total * 2000);

        return totalCals;
    }

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

    public double getRemainingNutrients(int amount) {
        double result = 0;
        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.findBetween(u, LocalDate.of(2023, 05, 28), LocalDate.of(2023, 9,20), new Meal());
        for (int i = amount; i < nutrients.size(); i++) {
            result += nutrients.get(i).getAmount();
        }
        return result;
    }

    public int getCalsBurned(LocalDate l1, LocalDate l2) {
        DBAccess da = new DBAccess();
        List<Exercise> exercises = da.findBetween(u, l1, l2, new Exercise());

        int result = 0;
        for (Exercise e : exercises) {
            result += e.getCalBurned();
        }

        return amountPerDay(result, l1, l2);

    }

    public List getXNutrients(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.findBetween(u, l1, l2, new Meal());
        for (int i = 0; i < nutrients.size() && i < 20; i++) {

            result.add(nutrients.get(i));

        }
        return result;
    }

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

    public UIController(User u) {
        this.u = u;
    }

    public UIController() {
    }


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
