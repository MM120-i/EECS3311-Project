package gui;

import controller.UIController;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

/**
 * Use case 6
 * The class represents a graphical user interface for estimating
 * fat loss based on exercise log, caloric intake, and a selected future date.
 * Users can input their exercise log, caloric intake, and choose a future date to calculate
 * the estimated fat loss based on the provided formula (1 kg of fat = 7,700 kcal).
 */
public class FatLossEstimatorUI {
    private UIController uic;
    private JFrame frame;
    private JPanel controlPanel;
    private JButton calculateButton;
    private JTextField exerciseLogField, calorieIntakeField;
    private JTextArea resultTextArea;
    private JLabel futureDateLabel, notificationLabel;
    private JTextField futureDateField;

    /**
     * Constructs a new instance of the class.
     * Initializes the main frame, control panel, and user interface components.
     */
    public FatLossEstimatorUI(UIController uic) {
    	this.uic = uic;
        // Initialize the main frame
        frame = new JFrame("Fat Loss Estimator");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Initialize the control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        // Labels and text fields for exercise log, calorie intake, and future date
        JLabel exerciseLogLabel = new JLabel("Exercise Log (kcal):");
        exerciseLogField = new JTextField(15);
        exerciseLogField.setUI(new JTextFieldHintUI(exerciseLogField, "Enter Exercise Log (kcal)", Color.gray));

        JLabel calorieIntakeLabel = new JLabel("Caloric Intake (kcal):");
        calorieIntakeField = new JTextField(15);
        calorieIntakeField.setUI(new JTextFieldHintUI(calorieIntakeField, "Enter Caloric Intake (kcal)", Color.gray));

        futureDateLabel = new JLabel("Select Future Date:");
        futureDateField = new JTextField(10);
        futureDateField.setUI(new JTextFieldHintUI(futureDateField, "yyyy-MM-dd", Color.gray));

        // Button for triggering fat loss calculation
        calculateButton = new JButton("Calculate Fat Loss");
        resultTextArea = new JTextArea(10, 40);
        notificationLabel = new JLabel();

        // Add components to the control panel
        cons.gridx = 0;
        cons.gridy = 0;
        controlPanel.add(exerciseLogLabel, cons);

        cons.gridx = 1;
        controlPanel.add(exerciseLogField, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        controlPanel.add(calorieIntakeLabel, cons);

        cons.gridx = 1;
        controlPanel.add(calorieIntakeField, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        controlPanel.add(futureDateLabel, cons);

        cons.gridx = 1;
        controlPanel.add(futureDateField, cons);

        cons.gridx = 2;
        controlPanel.add(calculateButton, cons);

        // Add components to the main frame
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(resultTextArea, BorderLayout.CENTER);
        frame.add(notificationLabel, BorderLayout.SOUTH);

        // Add action listener for the calculate button
        calculateButton.addActionListener(new CalculateButtonListener());

        // Set the style for the calculate button
        setButtonStyle(calculateButton);
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
     * Displays the FatLossEstimatorUI by making the frame visible.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * Action listener for the calculate button.
     */
    private class CalculateButtonListener implements ActionListener {
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            if (validateInput()) {
                // Input is valid, proceed with fat loss calculation
                calculateFatLoss();
            }
        }
    }

    /**
     * Validates the input fields to ensure they are not empty or contain invalid values.
     *
     * @return true if input is valid, false otherwise.
     */
    private boolean validateInput() {
    	
        String exerciseLog = exerciseLogField.getText().trim();
        String calorieIntake = calorieIntakeField.getText().trim();
        String futureDate = futureDateField.getText().trim();

        // Check if exercise log, calorie intake, and future date are not empty
        if (exerciseLog.isEmpty() || calorieIntake.isEmpty() || futureDate.isEmpty()) {
        	
            showError("Please enter values for all fields.");
            return false;
        }

        // Check if exercise log and calorie intake are numeric
        try {
        	
            double exerciseLogValue = Double.parseDouble(exerciseLog);
            double calorieIntakeValue = Double.parseDouble(calorieIntake);

            if(exerciseLogValue < 0|| calorieIntakeValue < 0) {
            	
            	showError("Exercise log and calorie intake must be non-negative values.");
            	return false;
            }

        } catch (NumberFormatException ex) {
        	
            showError("Exercise log and calorie intake must be numeric.");
            return false;
        }

        // Check if future date is in a valid format (add more validation if needed)
        if (!isValidDateFormat(futureDate)) {
        	
            showError("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }

        return true;
    }

    /**
     * Checks if a date string is in the valid format (yyyy-MM-dd).
     *
     * @param date The date string to check.
     * @return true if the date string is in the valid format, false otherwise.
     */
    private boolean isValidDateFormat(String date) {
    	
        try {
        	
            LocalDate.parse(date);
            return true;
        } catch (Exception e) {
        	
            return false;
        }
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
     * Calculates and displays the estimated fat loss based on the input values.
     */
    private void calculateFatLoss() {

        double exerciseLogValue = uic.getCalsBurned(LocalDate.now().minusMonths(1), LocalDate.now());
        double calorieIntakeValue = uic.getCaloriesConsumed(1, LocalDate.now().minusMonths(1), LocalDate.now());
        String futureDate = futureDateField.getText().trim();

        // Calculate fat loss based on the provided formula (1 kg of fat = 7,700 kcal)
        double fatLoss = (calorieIntakeValue - exerciseLogValue) / 7700;

        // Display the result
        resultTextArea.setText("Estimated Fat Loss by " + futureDate + ":\n" + String.format("%.2f", fatLoss) + " kg");
    }

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
        	
            FatLossEstimatorUI ui = new FatLossEstimatorUI(new UIController());
            ui.showUI();
        });
    }

    /**
     * JTextFieldHintUI is a UI class that provides a hint (placeholder text) for JTextField components.
     * This class extends BasicTextFieldUI and implements FocusListener to handle focus events.
     */
    class JTextFieldHintUI extends BasicTextFieldUI implements FocusListener {

        private JTextField textField; // The text field to which the hint is applied
        private String hint;           // The hint text to be displayed
        private Color hintColor;       // The color of the hint text

        /**
         * Constructs a new JTextFieldHintUI with the specified text field, hint text, and hint color.
         *
         * @param textField The text field to which the hint is applied.
         * @param hint      The hint text to be displayed.
         * @param hintColor The color of the hint text.
         */
        public JTextFieldHintUI(JTextField textField, String hint, Color hintColor) {
        	
            this.textField = textField;
            this.hint = hint;
            this.hintColor = hintColor;
            installListeners();
        }

        /**
         * Invoked when the component gains focus. If the current text is the hint, it is cleared.
         *
         * @param e The focus event.
         */
        @Override
        public void focusGained(FocusEvent e) {
        	
            if (textField.getText().equals(hint)) {
            	
                textField.setText("");
                textField.setForeground(Color.BLACK);
            }
        }

        /**
         * Invoked when the component loses focus. If the current text is empty, the hint is set and its color is applied.
         *
         * @param e The focus event.
         */
        @Override
        public void focusLost(FocusEvent e) {
        	
            if (textField.getText().isEmpty()) {
            	
                textField.setText(hint);
                textField.setForeground(hintColor);
            }
        }

        /**
         * Installs the necessary listeners, including the FocusListener for handling focus events.
         */
        @Override
        protected void installListeners() {
        	
            super.installListeners();
            textField.addFocusListener(this);
        }

        /**
         * Uninstalls the previously installed listeners, including the FocusListener.
         */
        @Override
        protected void uninstallListeners() {
        	
            super.uninstallListeners();
            textField.removeFocusListener(this);
        }

        /**
         * Gets the text field associated with this UI.
         *
         * @return The text field.
         */
        protected JTextField getTextField() {
            return textField;
        }
    }
}