package gui;

import controller.UIController;
import dataObjects.Nutrient;
import gui.JTextFieldHintUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.List;
import java.util.Locale;
import java.awt.Color;
import javax.swing.JTextField;

/**
 * Use Case 5:
 * A GUI window for visualizing daily nutrient intake over a specified time period.
 */
public class NutrientVisualizerUI {

    private JFrame frame;
    private JPanel controlPanel;
    private JButton visualizeButton;
    private JTextField startDateField, endDateField;
    private JTextArea resultTextArea;
    private JLabel notificationLabel;

    UIController uic;
    /**
     * Constructs a new NutrientVisualizerUI.
     * Initializes and sets up the main components of the UI.
     */
    public NutrientVisualizerUI(UIController uic) {
    	this.uic = uic;

        // Initialize the main frame
        frame = new JFrame("Nutrient Visualizer");
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Initialize the control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        // Labels and text fields for start and end dates
        JLabel startDateLabel = new JLabel("Start Date:");
        startDateField = new JTextField(15);
        JLabel endDateLabel = new JLabel("End Date:");
        endDateField = new JTextField(15);

        // Set placeholder text for date fields
        startDateField.setUI(new JTextFieldHintUI("Enter date (yyyy-MM-dd)", Color.gray));
        endDateField.setUI(new JTextFieldHintUI("Enter date (yyyy-MM-dd)", Color.gray));

        // Button for triggering nutrient visualization
        visualizeButton = new JButton("Visualize");
        resultTextArea = new JTextArea(10, 40);
        notificationLabel = new JLabel();

        // Add components to the control panel
        cons.gridx = 0;
        cons.gridy = 0;
        controlPanel.add(startDateLabel, cons);

        cons.gridx = 1;
        controlPanel.add(startDateField, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        controlPanel.add(endDateLabel, cons);

        cons.gridx = 1;
        controlPanel.add(endDateField, cons);

        cons.gridx = 2;
        controlPanel.add(visualizeButton, cons);

        // Add components to the main frame
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(resultTextArea, BorderLayout.CENTER);
        frame.add(notificationLabel, BorderLayout.SOUTH);

        // Add action listener for the visualize button
        visualizeButton.addActionListener(new VisualizeButtonListener());

        // Set the style for the visualize button
        setButtonStyle(visualizeButton);

        JButton chart = new JButton();
        chart.setText("Chart");
        setButtonStyle(chart);
        controlPanel.add(chart);
        chart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new CaloriePie(uic, startDateField.getText(), startDateField.getText()).run(uic, startDateField.getText(), startDateField.getText());
            }
        });

    }

    /**
     * Visualizes the nutrient data on the UI.
     *
     * @param nutrientData A map containing nutrient names and their corresponding percentages.
     */
    private void visualizeNutrientData(Map<String, Double> nutrientData) {
    	
        int count = 0;
        // Calculate percentages and display the result
        double total = nutrientData.values().stream().mapToDouble(Double::doubleValue).sum();
        StringBuilder resultText = new StringBuilder("Nutrient Intake Percentages:\n");

        // Sort the nutrients by value (percentage) in descending order
        nutrientData.entrySet().stream()
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                .forEach(entry -> {
                	
                    double percentage = (entry.getValue() / total) * 100;
                    resultText.append(entry.getKey()).append(": ")
                            .append(String.format(Locale.US, "%.2f", percentage)).append("%\n");

                });

        // Display the top 5 nutrients (can be changed to top 10).
        resultText.append("\nTop 5 Nutrients:\n");

        for (Map.Entry<String, Double> entry : nutrientData.entrySet()) {
        	
            if (count >= 5) {
                break;
            }

            double percentage = (entry.getValue() / total) * 100;
            resultText.append(entry.getKey()).append(": ")
                    .append(String.format(Locale.US, "%.2f", percentage)).append("%\n");

            count++;
        }

        // Display notification based on total percentage
        String notification = getNotification(total);
        notificationLabel.setText(notification);

        // Update the result text area
        resultTextArea.setText(resultText.toString());
    }

    /**
     * Retrieves a sample nutrient data map for demonstration purposes.
     *
     * @return A map containing nutrient names and their corresponding percentages.
     */
    private Map<String, Double> getSampleNutrientData() {
        Map<String, Double> nutrientData = new HashMap<>();


        List<Nutrient> nutrients = uic.getXNutrients(10, LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText()));
        for (Nutrient n : nutrients) {
            nutrientData.put(n.getName(), n.getAmount());
        }
        // Replace this with the backend logic to fetch nutrient data


        return nutrientData;
    }

    /**
     * Generates a notification based on the total percentage of nutrient intake.
     *
     * @param totalPercentage The total percentage of nutrient intake.
     * @return A notification message.
     */
    private String getNotification(double totalPercentage) {
    	
        // Replace this with the logic to determine the notification
        if (totalPercentage >= 80) {
            return "You have reached or exceeded the recommended daily nutrient intake.";
        } 
        else {
            return "You are below the recommended daily nutrient intake.";
        }
    }

    /**
     * Sets the visual style for a button.
     *
     * @param button The button to set the style for.
     */
    private void setButtonStyle(JButton button) {
    	
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    /**
     * Displays the NutrientVisualizerUI by making the frame visible.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * Validates the input fields to ensure they are not empty or contain invalid values.
     *
     * @return true if input is valid, false otherwise.
     */
    private boolean validateInput() {
    	
        String startDate = startDateField.getText().trim();
        String endDate = endDateField.getText().trim();

        // Check if start and end dates are not empty
        if (startDate.isEmpty() || endDate.isEmpty()) {
        	
            showError("Please enter both start and end dates.");
            return false;
        }

        // Check if start and end dates are in a valid format (add more validation if needed)
        try {
        	
            LocalDate start = LocalDate.parse(startDate);
            LocalDate end = LocalDate.parse(endDate);

            // Check if start date is before or equal to end date
            if (start.isAfter(end)) {
            	
                showError("Start date must be before or equal to end date.");
                return false;
            }

        } catch (DateTimeParseException e) {
        	
            showError("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }

        // Add more validation for other input fields (duration, etc.) if needed

        return true;
    }

    /**
     * Displays an error message dialog with the given message.
     *
     * @param message The error message to display.
     */
    private void showError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Action listener for the visualize button.
     */
    private class VisualizeButtonListener implements ActionListener {
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            if (validateInput()) {
            	
                // Input is valid, proceed with visualization
                Map<String, Double> nutrientData = getSampleNutrientData();
                visualizeNutrientData(nutrientData);
            }
        }
    }


}

