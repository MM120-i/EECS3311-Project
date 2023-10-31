package controller;

import dataObjects.Ingredient;
import dataObjects.Meal;
import dataObjects.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The controller.DatabaseMain class is the entry point for the application that interacts with a database.
 * It demonstrates various database operations, such as adding users, meals, and exercises.
 */
public class DatabaseMain {
    /**
     * The main method of the application that demonstrates database operations.
     *
     * @param args Command-line arguments (not used).
     * @throws SQLException if a database-related error occurs.
     */
    public static void main(String[] args) throws SQLException {
        LocalDate d = LocalDate.of(2003, 5,28);
        DBAccess dba = new DBAccess();
        User user1 = new User("Bob Smith", 1, d, 150,  100, 0, 0);
        dba.add(user1, user1);
        //dba.addUser(user1);
        //dba.deleteUser(user1);
       //dba.updateUser(user1, 3, d);
        ArrayList<Ingredient> ing = new ArrayList<>();
        ing.add(new Ingredient(2, Math.random() * 5));
       ing.add(new Ingredient(5, 1));
       dba.add(user1, new Meal(1, LocalDate.of(2023, (int) (Math.random() * 10 + 1), (int) (Math.random() * 30)), 4, ing));
        //ing = new ArrayList<>();
      //  ing.add(new dataObjects.Ingredient(1, 2));
       // ing.add(new dataObjects.Ingredient(10, 2));
        //dba.addMeal(user1, new dataObjects.Meal(2, LocalDate.now(), 1, ing));
        //dba.add(user1, new Exercise( LocalDate.now(), 5, "Biking", 1, 150));
        //dba.breakdownMeal(user1, LocalDate.of(2023, 05, 28), LocalDate.of(2023, 9,20));
        //System.out.println(user1.getMeals().size());

        for (Meal m : user1.getMeals()) {
            System.out.println(m.getMealID());
            for (Ingredient i : m.getIngredients()) {
                System.out.println(i.getIngredientNum());
            }
        }

        //dba.findNutrients(2);
    }
}
