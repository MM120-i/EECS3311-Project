package controller;

import dataObjects.Exercise;
import dataObjects.User;
import gui.ExerciseLogWindow;

import java.time.LocalDate;
import java.time.LocalTime;

public class UIController {

    private User u;
    public void profileCreation(Boolean maleRB, String height, String weight) {
        u = new User();
        u.setName("Testing12223");
        if (maleRB) {
            u.setMale(1);
        } else {
            u.setMale(0);
        }
        u.setHeight(Integer.parseInt(height));
        u.setWeight(Integer.parseInt(weight));
        u.calculateBMR();
        System.out.println(u.getName());
        DBAccess da = new DBAccess();
        da.add(u, u);

        ExerciseLogWindow ex = new ExerciseLogWindow(this);
        ex.call(this);
    }

    public void exerciseCreation(LocalDate date, LocalTime time, int intensity, int duration, String type) {
        System.out.println(u.getName());
        Exercise e = new Exercise(date, duration, type, intensity);
        System.out.println("bmr: " + u.getBMR());
        e.calBurned(u.getBMR());
        System.out.println(e.getCalBurned());
        DBAccess da = new DBAccess();
        da.add(u, e);
    }


}
