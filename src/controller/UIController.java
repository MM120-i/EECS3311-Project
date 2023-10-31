package controller;

import dataObjects.Exercise;
import dataObjects.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class UIController {

    User u;
    public void profileCreation(Boolean maleRB, String height, String weight) {
        u = new User();
        u.setName("no Name");
        if (maleRB) {
            u.setMale(1);
        } else {
            u.setMale(0);
        }
        u.setHeight(Integer.parseInt(height));
        u.setWeight(Integer.parseInt(weight));

        DBAccess da = new DBAccess();
        da.add(u, u);
    }

    public void exerciseCreation(LocalDate date, LocalTime time, int intensity, int duration, String type) {
        Exercise e = new Exercise(date, duration, type, intensity, 0);
        DBAccess da = new DBAccess();
        da.add(u, e);
    }
}
