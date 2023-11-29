package view;

import controller.UIController;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.Period;

/**
 * Use case 6
 * The class represents a graphical user interface for estimating
 * fat loss based on exercise log, caloric intake, and a selected future date.
 * Users can input their exercise log, caloric intake, and choose a future date to calculate
 * the estimated fat loss based on the provided formula (1 kg of fat = 7,700 kcal).
 */
public class FatLossEstimatorUI {
    private final UIController uic;
    private final JFrame frame;
    private final JPanel controlPanel;
    private final JButton calculateButton;
    private final JTextArea resultTextArea;
    private final JLabel futureDateLabel;
    private final JLabel notificationLabel;
    private final JTextField futureDateField;

    /**
     * Constructs a new instance of the class.
     * Initializes the main frame, control panel, and user interface components.
     *
     * @param uic the uic
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

        cons.gridx = 1;

        cons.gridx = 0;
        cons.gridy = 1;


        cons.gridx = 1;


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

        String futureDate = futureDateField.getText().trim();

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
        if (exerciseLogValue == 0 || calorieIntakeValue == 0) {
            JOptionPane.showMessageDialog(frame, "Insufficient Information. Enter some more information first.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String futureDate = futureDateField.getText().trim();

            int days = Period.between(LocalDate.now(), LocalDate.parse(futureDate)).getDays();

            if (days < 1) {
                JOptionPane.showMessageDialog(frame, "Set a date in the future", "Error", JOptionPane.ERROR_MESSAGE);

            } else {

                // Calculate fat loss based on the provided formula (1 kg of fat = 7,700 kcal)
                double fatLoss = (calorieIntakeValue - exerciseLogValue) / 7700;

                // Display the result
                resultTextArea.setText("Estimated Fat Loss by " + futureDate + ":\n" + String.format("%.2f", fatLoss * days) + " kg");
            }
        }

    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
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

        private final JTextField textField; // The text field to which the hint is applied
        private final String hint;           // The hint text to be displayed
        private final Color hintColor;       // The color of the hint text

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