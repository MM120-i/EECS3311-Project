package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Use case 7
 * CanadaFoodGuideUI is a Java Swing application that allows users to input
 * percentages of different food groups on their plate and visualize how well
 * their diet aligns with the Canada Food Guide recommendations.
 *
 * The UI includes text fields for entering percentages of vegetables, fruits,
 * grains, proteins, and dairy. Users can click the "Visualize" button to see
 * the visualization of their plate composition and receive feedback on how close
 * it aligns with the Canada Food Guide recommendations.
 */
public class CanadaFoodGuideUI {

    private JFrame frame;
    private JPanel controlPanel;
    private JButton visualizeButton;
    private JTextField veggieField, fruitField, grainField, proteinField, dairyField;
    private JTextArea resultTextArea;
    private JLabel notificationLabel;

    /**
     * Constructs a new CanadaFoodGuideUI. Initializes the main frame, control
     * panel, and components such as labels, text fields, buttons, and result
     * text area. Sets the layout and styles for UI components.
     */
    public CanadaFoodGuideUI() {
    	
        // Initialize the main frame
        frame = new JFrame("Canada Food Guide Analyzer");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Initialize the control panel
        controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());

        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        // Labels and text fields for food group percentages
        JLabel veggieLabel = new JLabel("Vegetables (%):");
        veggieField = new JTextField(15);

        JLabel fruitLabel = new JLabel("Fruits (%):");
        fruitField = new JTextField(15);

        JLabel grainLabel = new JLabel("Grains (%):");
        grainField = new JTextField(15);

        JLabel proteinLabel = new JLabel("Proteins (%):");
        proteinField = new JTextField(15);

        JLabel dairyLabel = new JLabel("Dairy (%):");
        dairyField = new JTextField(15);

        // Button for visualizing food group alignment
        visualizeButton = new JButton("Visualize");
        resultTextArea = new JTextArea(10, 40);
        notificationLabel = new JLabel();

        // Add components to the control panel
        cons.gridx = 0;
        cons.gridy = 0;
        controlPanel.add(veggieLabel, cons);

        cons.gridx = 1;
        controlPanel.add(veggieField, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        controlPanel.add(fruitLabel, cons);

        cons.gridx = 1;
        controlPanel.add(fruitField, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        controlPanel.add(grainLabel, cons);

        cons.gridx = 1;
        controlPanel.add(grainField, cons);

        cons.gridx = 0;
        cons.gridy = 3;
        controlPanel.add(proteinLabel, cons);

        cons.gridx = 1;
        controlPanel.add(proteinField, cons);

        cons.gridx = 0;
        cons.gridy = 4;
        controlPanel.add(dairyLabel, cons);

        cons.gridx = 1;
        controlPanel.add(dairyField, cons);

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
     * Displays the CanadaFoodGuideUI by making the frame visible.
     */
    public void showUI() {
        frame.setVisible(true);
    }

    /**
     * ActionListener for the "Visualize" button. Validates input, and if valid,
     * triggers the visualization of food group alignment.
     */
    private class VisualizeButtonListener implements ActionListener {
    	
        @Override
        public void actionPerformed(ActionEvent e) {
        	
            if (validateInput()) {
                visualizeFoodGuideAlignment();
            }
        }
    }

    /**
     * Validates the input fields to ensure they are not empty or contain invalid values.
     *
     * @return true if input is valid, false otherwise.
     */
    private boolean validateInput() {
    	
        String veggiePercentage = veggieField.getText().trim();
        String fruitPercentage = fruitField.getText().trim();
        String grainPercentage = grainField.getText().trim();
        String proteinPercentage = proteinField.getText().trim();
        String dairyPercentage = dairyField.getText().trim();

        if (veggiePercentage.isEmpty() || fruitPercentage.isEmpty() || grainPercentage.isEmpty() || proteinPercentage.isEmpty() || dairyPercentage.isEmpty()) {
        	
            showError("Please enter values for all food group percentages.");
            return false;
        }

        try {
        	
            double veggie = Double.parseDouble(veggiePercentage);
            double fruit = Double.parseDouble(fruitPercentage);
            double grain = Double.parseDouble(grainPercentage);
            double protein = Double.parseDouble(proteinPercentage);
            double dairy = Double.parseDouble(dairyPercentage);

            if (veggie < 0 || fruit < 0 || grain < 0 || protein < 0 || dairy < 0) {
            	
                showError("Food group percentages cannot be negative.");
                return false;
            }

            double totalPercentage = veggie + fruit + grain + protein + dairy;

            if (totalPercentage != 100) {
            	
                showError("Total food group percentages must equal 100.");
                return false;
            }

        } catch (NumberFormatException ex) {
        	
            showError("Food group percentages must be numeric.");
            return false;
        }

        return true;
    }

    /**
     * Visualizes the alignment of the user's plate with the Canada Food Guide
     * recommendations based on the entered percentages. Displays the result in
     * the result text area.
     */
    private void visualizeFoodGuideAlignment() {
    	
        double veggie = Double.parseDouble(veggieField.getText().trim());
        double fruit = Double.parseDouble(fruitField.getText().trim());
        double grain = Double.parseDouble(grainField.getText().trim());
        double protein = Double.parseDouble(proteinField.getText().trim());
        double dairy = Double.parseDouble(dairyField.getText().trim());

        // You can implement the visualization logic here based on the CFG recommendations
        // For simplicity, this example just displays the percentages in the result text area

        resultTextArea.setText("Your Plate Composition:\n" +
                "Vegetables: " + veggie + "%\n" +
                "Fruits: " + fruit + "%\n" +
                "Grains: " + grain + "%\n" +
                "Proteins: " + protein + "%\n" +
                "Dairy: " + dairy + "%\n\n" +
                "Note: Visualization logic based on CFG recommendations can be implemented here.");
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
     * Entry point for the CanadaFoodGuideUI application. Creates an instance of
     * the CanadaFoodGuideUI class and displays the UI.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> {
            CanadaFoodGuideUI ui = new CanadaFoodGuideUI();
            ui.showUI();
        });
    }
}