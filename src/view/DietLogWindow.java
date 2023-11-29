package view;

import model.DBMeal;
import controller.UIController;
import model.dataObjects.Ingredient;
import model.dataObjects.Meal;
import model.dataObjects.Nutrient;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Use Case 2: Diet Log Window
 */
public class DietLogWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    /**
     * The Date fields.
     */
    public ArrayList<JTextField> dateFields;
    /**
     * The Tf 2.
     */
    public JTextField tf2;
    private final List<JTextField> ingredientFields;
    private final List<JTextField> quantityFields;
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private GridBagConstraints cons;
    private final List<Meal> meals;
    private final UIController uic;

    /**
     * Constructor to create the Diet Log Window
     *
     * @param uic the uic
     */
    public DietLogWindow(UIController uic) {
        System.out.println(uic.u);
        this.uic = uic;

        setTitle("Diet Log");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 14)); // Set the font for the entire UI
        createUI();

        ingredientFields = new ArrayList<>();
        quantityFields = new ArrayList<>();
        meals = new ArrayList<>();
    }

    /**
     * Create the user interface elements and layout
     */
    private void createUI() {

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        // Create a JTabbedPane to organize tabs for different meal types
        tabbedPane = new JTabbedPane();
        int counter = 0;
        // Add tabs for Breakfast, Lunch, Dinner, and Snack
        dateFields = new ArrayList<>();
        for (String mealType : new String[]{"Breakfast", "Lunch", "Dinner", "Snack"}) {
            JPanel mealPanel = createMealPanel(mealType, counter);
            tabbedPane.addTab(mealType, mealPanel);
            counter++;
        }

        // Add change listener to clear snack fields when switching to the "Snack" tab
        tabbedPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {

                // Get the currently selected tab index
                int selectedIndex = tabbedPane.getSelectedIndex();

                // Clear snack fields when switching to a new tab
                if (selectedIndex != -1 && tabbedPane.getTitleAt(selectedIndex).equals("Snack")) {
                    clearSnackFields();
                }
            }
        });

        // "View Meals" Button
        JButton viewMealsButton = new JButton("View Meals");
        viewMealsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                viewMealsInformation();
            }
        });

        cons.gridx = 1;  // Set the column to 1 to place it beside the "Save" button
        cons.gridy = 1;  // Set the row to 1 to place it beside the "Save" button
        mainPanel.add(viewMealsButton, cons);

        viewMealsButton.setBackground(new Color(0, 102, 204)); // Button color
        viewMealsButton.setForeground(Color.WHITE); // Button text color
        viewMealsButton.setFont(new Font("Arial", Font.BOLD, 14)); // Button font

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


    /**
     * Display a dialog showing the saved meal information.
     */
    private void viewMealsInformation() {
        StringBuilder message = new StringBuilder("Saved Meals:\n\n");

        LocalDate d1 = LocalDate.now();
        DBMeal dbm = new DBMeal(uic);
        ArrayList<Meal> meals = (ArrayList<Meal>) dbm.findAll();


        for (Meal meal : meals) {
            message.append("Date: ").append(meal.getDate()).append("\n");
            switch (meal.getMealType()) {
                case 1:
                    message.append("Meal Type: ").append("Breakfast").append("\n");
                    break;
                case 2:
                    message.append("Meal Type: ").append("Lunch").append("\n");
                    break;
                case 3:
                    message.append("Meal Type: ").append("Dinner").append("\n");
                    break;
                default:
                    message.append("Meal Type: ").append("Snack").append("\n");
                    break;
            }


            List<Ingredient> ingredients = meal.getIngredients();

            for (Ingredient ingredient : ingredients) {
                message.append("Ingredient: ").append(ingredient.getIngredientNum()).append(", Quantity: ").append(ingredient.getAmount()).append("\n");
            }

            message.append("\n");
        }

        JTextArea textArea = new JTextArea(message.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "View Meals", JOptionPane.PLAIN_MESSAGE);
    }



    /**
     * Save the meal information entered by the user
     */
    private void saveMealInformation() {

        int mealType = tabbedPane.getSelectedIndex() + 1;
        String date = dateFields.get(mealType - 1).getText();



        List<model.dataObjects.Ingredient> ingredients = new ArrayList<>();
        // Get ingredient and quantity information
        for (int i = 0; i < ingredientFields.size(); i++) {
            int ingredient = Integer.parseInt(ingredientFields.get(i).getText());
            double quantity = Double.parseDouble(quantityFields.get(i).getText());
            ingredients.add(new model.dataObjects.Ingredient(ingredient, quantity));
        }


        // Save meal information with the correct date
        model.dataObjects.Meal meal = new model.dataObjects.Meal(LocalDate.parse(dateFields.get(mealType-1).getText()), mealType, (ArrayList<model.dataObjects.Ingredient>) ingredients);
        meals.add(meal);

        DBMeal dbm = new DBMeal(uic);
        dbm.add(uic.u, meal);

        // Optionally, you can display a message to confirm that the data is saved
        JOptionPane.showMessageDialog(this, "Meal information saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);


        int counter = 0;
        mainPanel.remove(tabbedPane);
        tabbedPane = new JTabbedPane();
        dateFields = new ArrayList<>();
        for (String mealt : new String[]{"Breakfast", "Lunch", "Dinner", "Snack"}) {
            JPanel mealPanel = createMealPanel(mealt, counter);
            tabbedPane.addTab(mealt, mealPanel);
            counter++;
        };
        mainPanel.add(tabbedPane, cons);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    /**
     * Show an error dialog with a given message.
     *
     * @param message The error message to display.
     */
    private void showErrorDialog(String message) {

        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Create a panel for a specific meal type.
     *
     * @param mealType The type of the meal (e.g., Breakfast, Lunch, Dinner, Snack).
     * @return The created panel for the meal type.
     */
    private JPanel createMealPanel(String mealType, int mealtype) {

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
        dateFields.add(new JTextField(15));
        dateFields.get(mealtype).setText("");

        mealPanel.add(dateFields.get(mealtype), mealCons);

        JButton addIngredientsButton;
        JButton saveButton = null; // Declare outside the if block

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

            // "Save" Button
            mealCons.gridx = 1;
            mealCons.gridy = 2;
            saveButton = new JButton("Save");
            mealPanel.add(saveButton, mealCons);
            saveButton.setBackground(new Color(0, 102, 204)); // Button color
            saveButton.setForeground(Color.WHITE); // Button text color
            saveButton.setFont(new Font("Arial", Font.BOLD, 14)); // Button font
        }

        else {

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

            // "Save" Button for Snack
            saveButton = new JButton("Save");
            mealCons.gridx = 1;
            mealCons.gridy = 3;
            mealPanel.add(saveButton, mealCons);
            saveButton.setBackground(new Color(0, 102, 204)); // Button color
            saveButton.setForeground(Color.WHITE); // Button text color
            saveButton.setFont(new Font("Arial", Font.BOLD, 14)); // Button font
        }


        // "Save" Button Action for Snack
        if (saveButton != null) {

            saveButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int mealType = tabbedPane.getSelectedIndex() + 1;

                    saveMealInformation();
                }
            });
        }

        return mealPanel;
    }


    /**
     * Create fields for inputting ingredients based on the number of ingredients.
     *
     * @param mealPanel     The panel for the meal type.
     * @param numIngredients The number of ingredient fields to create.
     */
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

    /**
     * Create fields for inputting snack details based on the number of snacks.
     *
     * @param numSnacks The number of snack fields to create.
     */
    private void createSnackFields(int numSnacks) {

        List<JLabel> snackLabels = new ArrayList<>();
        List<JTextField> snackTypeFields = new ArrayList<>();

        for (int i = 0; i < numSnacks; i++) {
            cons.gridx = 0;
            cons.gridy = 4 + i;

            // Label for the snack type
            JLabel snackTypeLabel = new JLabel("Snack Type #" + (i + 1) + ":");
            snackTypeLabel.setName("SnackLabel" + i); // Set a unique name for identification
            mainPanel.add(snackTypeLabel, cons);
            snackLabels.add(snackTypeLabel);

            cons.gridx = 1;
            JTextField snackTypeField = new JTextField(15);
            snackTypeFields.add(snackTypeField); // Add to the list
            mainPanel.add(snackTypeField, cons);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Clear snack fields when switching to a different tab
     */
    private void clearSnackFields() {

        Component[] components = mainPanel.getComponents();

        for (Component component : components) {

            if (component instanceof JLabel && component.getName() != null && component.getName().startsWith("SnackLabel")) {
                mainPanel.remove(component);
            } else if (component instanceof JTextField) {
                mainPanel.remove(component);
            }
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Set the font for the entire Swing UI.
     *
     * @param f The font to set for the entire UI.
     */
    public static void setUIFont(javax.swing.plaf.FontUIResource f) {

        Enumeration<Object> keys = UIManager.getDefaults().keys();

        while (keys.hasMoreElements()) {

            Object key = keys.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof javax.swing.plaf.FontUIResource) {

                UIManager.put(key, f);
            }
        }
    }

}

