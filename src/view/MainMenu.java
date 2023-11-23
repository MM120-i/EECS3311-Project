package view;

import controller.UIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MainMenu class represents the main menu of the application, providing options for various features
 * such as logging meals and exercises, estimating fat loss, tracking calories versus exercise, visualizing nutrient intake,
 * and accessing Canada's Food Guide recommendations.
 */
public class MainMenu extends JFrame{
    private JButton mealsButton;
    private JButton exerciseButton;
    private JButton button3;
    private JButton estimationsButton;
    private JButton calorieVsExerciseButton;
    private JButton nutrientIntakeButton;
    /**
     * The Panel.
     */
    public JPanel panel;

    /**
     * Instantiates a new Main menu.
     *
     * @param uic The UIController instance for handling user interface actions and interactions.
     */
    public MainMenu(UIController uic) {
        mealsButton.addActionListener(new ActionListener() {  // ActionListener for meal logging
            @Override
            public void actionPerformed(ActionEvent e) {
                DietLogWindow dlw = new DietLogWindow(uic);

            }
        });
        exerciseButton.addActionListener(new ActionListener() { // ActionListener for exercise logging
            @Override
            public void actionPerformed(ActionEvent e) {
                ExerciseLogWindow elw = new ExerciseLogWindow(uic);
            }
        });
        estimationsButton.addActionListener(new ActionListener() { // ActionListener for fat loss estimation
            @Override
            public void actionPerformed(ActionEvent e) {
                FatLossEstimatorUI fleUI = new FatLossEstimatorUI(uic);
                fleUI.showUI();
            }
        });
        calorieVsExerciseButton.addActionListener(new ActionListener() { // ActionListener for calorie versus exercise tracking
            @Override
            public void actionPerformed(ActionEvent e) {
                CalorieTrackerUI ctUI = new CalorieTrackerUI(uic);
                ctUI.showUI();
            }
        });


        nutrientIntakeButton.addActionListener(new ActionListener() {  // ActionListener for nutrient intake visualization
            @Override
            public void actionPerformed(ActionEvent e) {
                NutrientVisualizerUI nvUI = new NutrientVisualizerUI(uic);
                nvUI.showUI();
            }
        });
        button3.addActionListener(new ActionListener() { // ActionListener for accessing Canada's Food Guide recommendations
            @Override
            public void actionPerformed(ActionEvent e) {
                CanadaFoodGuideUI cfgUI = new CanadaFoodGuideUI(uic);
                cfgUI.showUI();
            }
        });
    }
}
