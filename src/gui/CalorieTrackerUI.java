package gui;

import controller.UIController;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;


/**
 * Use case 4
 * The CalorieTrackerUI class represents a simple graphical user interface for
 * tracking and visualizing calorie-related data within a specified time period.
 * Users can input start and end dates, and the application fetches and displays
 * data for that time range.
 */
public class CalorieTrackerUI {
    int sum;
    private final JFrame frame;
    private final JPanel controlPanel;
    private final JButton visualizeButton;
    private final JTextField startDateField;
    private final JTextField endDateField;
    private final JTextArea dataTextArea;
    private final JLabel notificationLabel;
    private final JLabel result;

    UIController uic;
    /**
     * Constructs a new instance of the CalorieTrackerUI class.
     * Initializes the frame, control panel, and components.
     */
    public CalorieTrackerUI(UIController uic) {
    	this.uic = uic;

        frame = new JFrame("Calorie Tracker");
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(2,1));

        controlPanel = new JPanel();
        controlPanel.setSize(800, 600);
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateField = new JTextField(15);
        startDateField.setUI(new JTextFieldHintUI("Enter date (yyyy-MM-dd)", Color.gray)); // Add input hint
        JLabel endDateLabel = new JLabel("End Date:");
        endDateField = new JTextField(15);
        endDateField.setUI(new JTextFieldHintUI("Enter date (yyyy-MM-dd)", Color.gray)); // Add input hint

        visualizeButton = new JButton("Visualize");
        dataTextArea = new JTextArea(10, 40);
        dataTextArea.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font and style for dataTextArea
        dataTextArea.setEditable(false); // Make it non-editable
        JScrollPane scrollPane = new JScrollPane(dataTextArea);

        notificationLabel = new JLabel();

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

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER); // Use scrollPane for dataTextArea
        frame.add(notificationLabel, BorderLayout.SOUTH);

        visualizeButton.addActionListener(new VisualizeButtonListener());

        setButtonStyle(visualizeButton);

        result = new JLabel();


    }


    /**
     * Creates a PieDataset for the chart by retrieving nutrient information from the database.
     *
     * @return A PieDataset containing nutrient names and their corresponding amounts.
     */
    private PieDataset createDataset(LocalDate l1, LocalDate l2){

        DefaultPieDataset dataset=new DefaultPieDataset();

        sum = uic.getCaloriesConsumed(0, l1, l2);


        dataset.setValue("Burned", sum);
        return dataset;
    }

    /**
     * Displays the UI by setting the frame visibility to true.
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
    	
        String startDateStr = startDateField.getText().trim();
        String endDateStr = endDateField.getText().trim();

        // Check if start and end dates are not empty
        if (startDateStr.isEmpty() || endDateStr.isEmpty()) {
        	
            showError("Please enter both start and end dates.");
            return false;
        }

        // Check if start and end dates are in a valid format (add more validation if needed)
        try {
        	
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            // Check if start date is before or equal to end date
            if (startDate.isAfter(endDate)) {
            	
                showError("Start date must be before or equal to end date.");
                return false;
            }

        } catch (DateTimeParseException e) {
        	
            showError("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }

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
                String startDate = startDateField.getText();
                String endDate = endDateField.getText();
                // Fetch and display data in the dataTextArea.
                dataTextArea.setText("Data from " + startDate + " to " + endDate + ":\n");
                // Add visualization logic here.

                createDataset(LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText()));

                dataTextArea.append("Calorie Intake: " + sum);
                dataTextArea.append("\nExercise Calories Per Day: " + uic.getCalsBurned(LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText())));
                dataTextArea.append("\nCurrent avg daily calorie intake: " + uic.getCaloriesConsumed(1, LocalDate.parse(startDateField.getText()), LocalDate.parse(endDateField.getText())));


            }
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

}
