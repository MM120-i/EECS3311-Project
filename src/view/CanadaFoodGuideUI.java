package view;

import controller.UIController;
import model.DBMeal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Use case 7
 * CanadaFoodGuideUI is a Java Swing application that allows users to input
 * percentages of different food groups on their plate and visualize how well
 * their diet aligns with the Canada Food Guide recommendations.
 * <p>
 * The UI includes a button for users to click and visualize the plate composition,
 * as well as two columns ("You" and "CFG") displayed below the button.
 */
public class CanadaFoodGuideUI {

    private  JFrame frame;
    private  JPanel controlPanel;
    private  JButton visualizeButton;
    private  JTextArea resultTextArea;

    private  UIController uic;

    /**
     * Constructs a new CanadaFoodGuideUI. Initializes the main frame, control
     * panel, and components such as labels, text fields, buttons, and result
     * text area. Sets the layout and styles for UI components.
     *
     * @param uic the uic
     */
    public CanadaFoodGuideUI(UIController uic) {
        this.uic = uic;
        initializeUIComponents();
        setComponentStyles();
        addEventListeners();
    }

    private void initializeUIComponents() {
        // Initialize the main frame
        frame = new JFrame("Canada Food Guide Analyzer");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Initialize the control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        // Initialize components
        visualizeButton = new JButton("Visualize");
        resultTextArea = new JTextArea(10, 40);

        // Add components to the control panel
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);
        cons.gridx = 0;
        cons.gridy = 0;
        controlPanel.add(visualizeButton, cons);

        cons.gridx = 0;
        cons.gridy = 1;

        cons.gridx = 1;
        cons.gridy = 1;

        // Add components to the main frame
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(resultTextArea, BorderLayout.CENTER);
    }

    private void setComponentStyles() {
        setButtonStyle(visualizeButton);
    }

    private void addEventListeners() {
        visualizeButton.addActionListener(new VisualizeButtonListener());
    }

    /**
     * Sets the visual style for a button.
     *
     * @param button The button to set the style for.
     */
    private void setButtonStyle(JButton button) {
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
    }

    /**
     * Displays the CanadaFoodGuideUI by making the frame visible.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * ActionListener for the "Visualize" button. Triggers the visualization
     * of food group alignment.
     */
    private class VisualizeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            visualizeFoodGuideAlignment();
        }
    }

    /**
     * Visualizes the alignment of the user's plate with the Canada Food Guide
     * recommendations. Displays the result in the result text area.
     */
    private void visualizeFoodGuideAlignment() {
        int val1 = (int) (8.0 / 22 * 100);
        int val2 = (int) (7.0 / 22 * 100);
        int val3 = (int) (4.0 / 22 * 100);
        int val4 = (int) (3.0 / 22 * 100);
        resultTextArea.setText("CFG Recommends: \n" + val1 + "% Vegetables and Fruits \n" + val2
                + "% Grain Products, \n" + val3 + "% Milk and Alternatives, and \n" + val4 + "% Meat and Alternatives\n");

        DBMeal dbm = new DBMeal(uic);
        ArrayList<Double> list = dbm.getTotals(uic.getU().getName());

        resultTextArea.append("\nYour Diet: \n" + String.format("%.2f", list.get(0)) + "% Vegetables and Fruits \n"
                + String.format("%.2f", list.get(1)) + "% Grain Products, \n" + String.format("%.2f", list.get(2))
                + "% Milk and Alternatives, and \n" + String.format("%.2f", list.get(3)) + "% Meat and Alternatives\n");
    }
}
