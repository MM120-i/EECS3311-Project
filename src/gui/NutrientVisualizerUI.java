package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.Locale;
import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

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

    /**
     * Constructs a new NutrientVisualizerUI.
     * Initializes and sets up the main components of the UI.
     */
    public NutrientVisualizerUI() {
    	
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
    	
        // Replace this with the backend logic to fetch nutrient data
        Map<String, Double> nutrientData = new HashMap<>();
        nutrientData.put("Protein", 50.0);
        nutrientData.put("Carbohydrates", 30.0);
        nutrientData.put("Fat", 15.0);
        nutrientData.put("Vitamins", 3.0);
        nutrientData.put("Minerals", 2.0);
        nutrientData.put("Fiber", 5.0);

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
        } else {
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
        	
            LocalDate.parse(startDate);
            LocalDate.parse(endDate);
            
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

    /**
     * Main method to launch the NutrientVisualizerUI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
        	
            NutrientVisualizerUI ui = new NutrientVisualizerUI();
            ui.showUI();
        });
    }
}

/**
 * JTextFieldHintUI is a UI class that provides a hint (placeholder text) for JTextField components.
 * This class extends BasicTextFieldUI and implements FocusListener to handle focus events.
 */
class JTextFieldHintUI extends BasicTextFieldUI implements FocusListener {

    private String hint; 	 // The hint text to be displayed
    private Color hintColor; // The color of the hint text

    /**
     * Constructs a new JTextFieldHintUI with the specified hint text and hint color.
     *
     * @param hint      The hint text to be displayed.
     * @param hintColor The color of the hint text.
     */
    public JTextFieldHintUI(String hint, Color hintColor) {
    	
        this.hint = hint;
        this.hintColor = hintColor;
    }

    /**
     * Invoked when the component gains focus. If the current text is the hint, it is cleared.
     *
     * @param e The focus event.
     */
    @Override
    public void focusGained(FocusEvent e) {
    	
        if (e.getSource() instanceof JTextComponent) {
        	
            JTextComponent textComponent = (JTextComponent) e.getSource();
            
            if (textComponent.getText().equals(hint)) {
            	
                textComponent.setText("");
                textComponent.setForeground(Color.BLACK);
            }
        }
    }

    /**
     * Invoked when the component loses focus. If the current text is empty, the hint is set and its color is applied.
     *
     * @param e The focus event.
     */
    @Override
    public void focusLost(FocusEvent e) {
    	
        if (e.getSource() instanceof JTextComponent) {
        	
            JTextComponent textComponent = (JTextComponent) e.getSource();
            
            if (textComponent.getText().isEmpty()) {
            	
                textComponent.setText(hint);
                textComponent.setForeground(hintColor);
            }
        }
    }

    /**
     * Installs the necessary listeners, including the FocusListener for handling focus events.
     */
    @Override
    protected void installListeners() {
    	
        super.installListeners();
        getComponent().addFocusListener(this);
    }

    /**
     * Uninstalls the previously installed listeners, including the FocusListener.
     */
    @Override
    protected void uninstallListeners() {
    	
        super.uninstallListeners();
        getComponent().removeFocusListener(this);
    }
}