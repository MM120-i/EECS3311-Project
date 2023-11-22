package controller;

import PROJECT.ProfileCreationWindow;
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
import java.util.ArrayList;
import java.util.List;

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
        System.out.println(u.getName());
        DBUser da = new DBUser();
        System.out.println("Here");
        if (!da.add(u)) {
            System.out.println("Here2");
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
        System.out.println("formatted date 2 : " + dob);
        u.setDob(LocalDate.parse(dob));
        u.setHeight(Integer.parseInt(height));
        u.setWeight(Integer.parseInt(weight));
        u.calculateBMR();
        System.out.println(u.getName());
        DBUser da = new DBUser();
        da.deleteUser(u);
        System.out.println("Here");
        if (!da.add(u)) {
            System.out.println("Here2");
            MainMenu mm = new MainMenu(this);
            return true;
        }
        return false;
    }

    public void exerciseCreation(LocalDate date, LocalTime time, int intensity, int duration, String type) {
        System.out.println("name: " + u.getName());
        u.calculateBMR();
        Exercise e = new Exercise(date, duration, type, intensity);
        System.out.println("bmr: " + u.getBMR());
        e.calBurned(u.getBMR());
        System.out.println("Burned: " + e.getCalBurned());
        DBExercise da = new DBExercise();
        da.add(u, e);
    }

    public List getExercises() {
        DBAccess da = new DBAccess();
        return da.findBetween(u, LocalDate.of(2021, 05, 28), LocalDate.of(2023, 11,20), new Exercise());
    }

    public double getRegularBurnOverTime(List<Exercise> exercises) {
        long total = LocalDate.of(2021, 5, 28).until(LocalDate.of(2023, 11,20), DAYS);
        System.out.println(total);
        double totalCals = (total * 2000);
        System.out.println(totalCals);
        return totalCals;
    }

    public List getXNutrients2(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBAccess da = new DBAccess();
        List<Nutrient> nutrients = da.findBetween(u, l1, l2, new Meal());
        System.out.println("NAME: " + u.getName());
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
        for (int i = 0; i < nutrients.size(); i++) {
            System.out.println(nutrients.get(i).getName() + " " + nutrients.get(i).getAmount());
            result.add(nutrients.get(i));

        }
        return result;
    }

    public int getCaloriesConsumed(int amount, LocalDate l1, LocalDate l2) {
        List<Nutrient> result = new ArrayList<>();
        DBAccess da = new DBAccess();
        int sum = 0;
        List<Nutrient> nutrients = da.findBetween(u, l1, l2, new Meal());
        System.out.println("ALREADY CALLED.");
        for (int i = 0; i < nutrients.size(); i++) {

            if (nutrients.get(i).getName().contains("KILOJOULES")) {

                System.out.println("INSIDE" + sum);
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
}
