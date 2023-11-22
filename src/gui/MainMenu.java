package gui;

import controller.UIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame{
    private JButton mealsButton;
    private JButton exerciseButton;
    private JButton button3;
    private JButton estimationsButton;
    private JButton calorieVsExerciseButton;
    private JButton nutrientIntakeButton;
    JPanel panel;

    public MainMenu(UIController uic) {
        mealsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DietLogWindow dlw = new DietLogWindow();

            }
        });
        exerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExerciseLogWindow elw = new ExerciseLogWindow(uic);
            }
        });
        estimationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FatLossEstimatorUI fleUI = new FatLossEstimatorUI(uic);
                fleUI.showUI();
            }
        });
    }
}