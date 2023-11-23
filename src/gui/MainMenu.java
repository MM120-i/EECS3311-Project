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
    public JPanel panel;

    public MainMenu(UIController uic) {
        mealsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DietLogWindow dlw = new DietLogWindow(uic);

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
        calorieVsExerciseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CalorieTrackerUI ctUI = new CalorieTrackerUI(uic);
                ctUI.showUI();
            }
        });


        nutrientIntakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NutrientVisualizerUI nvUI = new NutrientVisualizerUI(uic);
                nvUI.showUI();
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CanadaFoodGuideUI cfgUI = new CanadaFoodGuideUI(uic);
                cfgUI.showUI();
            }
        });
    }
}
