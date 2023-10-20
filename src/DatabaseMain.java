import com.mysql.cj.protocol.a.LocalDateTimeValueEncoder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseMain {
    public static void main(String[] args) throws SQLException {
        LocalDate d = LocalDate.of(2003,05,28);
        DatabaseAccess dba = new DatabaseAccess();
        User user1 = new User("Bob Smith", 1, d, 150,  100, 0, 0);
        dba.addUser(user1);
        //dba.deleteUser(user1);
       //dba.updateUser(user1, 3, d);
        ArrayList<Ingredient> ing = new ArrayList<>();
        ing.add(new Ingredient(2, 5));
        ing.add(new Ingredient(5, 1));
        //dba.addMeal(user1, new Meal(1, LocalDate.now(), 1, ing));
        ing = new ArrayList<>();
        ing.add(new Ingredient(1, 2));
        ing.add(new Ingredient(10, 2));
        //dba.addMeal(user1, new Meal(2, LocalDate.now(), 1, ing));
        //dba.addExercise(user1, new Exercise( LocalDate.now(), 5, "Biking", 1, 150));
        //dba.breakdownMeal(user1);
        //System.out.println(user1.getMeals().size());

        for (Meal m : user1.getMeals()) {
            System.out.println(m.getMealID());
            for (Ingredient i : m.getIngredients()) {
                System.out.println(i.getIngredientNum());
            }
        }

        dba.findNutrients(2);
    }
}