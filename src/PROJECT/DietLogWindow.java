package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


//Use Case 2:
public class DietLogWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField dateField;
    private JButton addIngredientsButton;
    private List<JTextField> ingredientFields;
    private List<JTextField> quantityFields;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private GridBagConstraints cons;
    private List<JTextField> snackTypeFields;

    public DietLogWindow() {

        setTitle("Diet Log");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 14)); // Set the font for the entire UI
        createUI();
        
        ingredientFields = new ArrayList<>();
        quantityFields = new ArrayList<>();
        snackTypeFields = new ArrayList<JTextField>();
    }

    private void createUI() {
    	
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        // Create a JTabbedPane to organize tabs for different meal types
        tabbedPane = new JTabbedPane();

        // Add tabs for Breakfast, Lunch, Dinner, and Snack
        for (String mealType : new String[]{"Breakfast", "Lunch", "Dinner", "Snack"}) {

            JPanel mealPanel = createMealPanel(mealType);
            tabbedPane.addTab(mealType, mealPanel);
        }

        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 2;
        cons.weightx = 1.0;
        cons.weighty = 1.0;

        mainPanel.add(tabbedPane, cons);

        // Set up the main panel and display
        add(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showErrorDialog(String message) {

        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private JPanel createMealPanel(String mealType) {

        JPanel mealPanel = new JPanel(new GridBagLayout());
        GridBagConstraints mealCons = new GridBagConstraints();
        mealCons.fill = GridBagConstraints.HORIZONTAL;
        mealCons.insets = new Insets(5, 5, 5, 5);

        // Date Input
        mealCons.gridx = 0;
        mealCons.gridy = 0;
        JLabel dateLabel = new JLabel("Date:");
        mealPanel.add(dateLabel, mealCons);

        mealCons.gridx = 1;
        dateField = new JTextField(15);
        mealPanel.add(dateField, mealCons);

        if (!mealType.equals("Snack")) {
        	
            // "Add Ingredients" Button
            mealCons.gridx = 0;
            mealCons.gridy = 2;
            addIngredientsButton = new JButton("Add Ingredients");
            mealPanel.add(addIngredientsButton, mealCons);
            addIngredientsButton.setBackground(new Color(0, 102, 204)); // Button color
            addIngredientsButton.setForeground(Color.WHITE); // Button text color
            addIngredientsButton.setFont(new Font("Arial", Font.BOLD, 14)); // Button font
            
            addIngredientsButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                	
                    try {
                    	
                        int numIngredients = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of ingredients:"));
                        
                        if (numIngredients <= 0) {
                            showErrorDialog("Number of ingredients must be a positive integer.");
                        } 
                        else {
                            createIngredientFields(mealPanel, numIngredients);
                        }
                    } catch (NumberFormatException ex) {
                    	
                        showErrorDialog("Invalid input. Please enter a valid number.");
                    }
                }
            });
        }

        if (mealType.equals("Snack")) {

            JButton addSnackButton = new JButton("Add Snack Entries");
            mealCons.gridx = 0;
            mealCons.gridy = 3;
            mealPanel.add(addSnackButton, mealCons);
            addSnackButton.setBackground(new Color(0, 102, 204)); // Button color
            addSnackButton.setForeground(Color.WHITE); // Button text color
            addSnackButton.setFont(new Font("Arial", Font.BOLD, 14)); // Button font

            addSnackButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                	
                    try {
                    	
                        int numSnacks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of snacks"));
                        
                        if (numSnacks <= 0) {
                            showErrorDialog("Number of snacks must be a positive number.");
                        } 
                        else {
                            createSnackFields(numSnacks);
                        }
                    } catch (NumberFormatException exception) {
                    	
                        showErrorDialog("Invalid input. Please enter a valid number.");
                    }
                }
            });
        }

        return mealPanel;
    }

    private void createIngredientFields(JPanel mealPanel, int numIngredients) {

        for (int i = 0; i < numIngredients; i++) {

            GridBagConstraints mealCons = new GridBagConstraints();
            mealCons.fill = GridBagConstraints.HORIZONTAL;
            mealCons.insets = new Insets(5, 5, 5, 5);
            mealCons.gridx = 0;
            mealCons.gridy = 4 + i;

            // Label for the ingredient field
            JLabel ingredientLabel = new JLabel("Ingredient #" + (i + 1) + ":");
            mealPanel.add(ingredientLabel, mealCons);

            mealCons.gridx = 1;
            JTextField ingredientField = new JTextField(15);
            ingredientFields.add(ingredientField);
            mealPanel.add(ingredientField, mealCons);

            // Label for the quantity field
            JLabel quantityLabel = new JLabel("Quantity:");
            mealCons.gridx = 2;
            mealPanel.add(quantityLabel, mealCons);

            // Quantity field
            mealCons.gridx = 3;
            JTextField quantityField = new JTextField(5);
            quantityFields.add(quantityField);
            mealPanel.add(quantityField, mealCons);
        }

        mealPanel.revalidate();
        mealPanel.repaint();
    }

    private void createSnackFields(int numSnacks) {

        for (int i = 0; i < numSnacks; i++) {

            cons.gridx = 0;
            cons.gridy = 4 + i;

            // Label for the snack type
            JLabel snackTypeLabel = new JLabel("Snack Type #" + (i + 1) + ":");
            mainPanel.add(snackTypeLabel, cons);

            cons.gridx = 1;
            JTextField snackTypeField = new JTextField(15);
            snackTypeFields.add(snackTypeField); // Add to the list
            mainPanel.add(snackTypeField, cons);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
    	
        // Set the font for the entire UI
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        
        while (keys.hasMoreElements()) {
        	
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new DietLogWindow());
    }
}
