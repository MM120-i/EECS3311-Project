package PROJECT;

import com.sun.tools.javac.Main;
import controller.UIController;
import gui.MainMenu;
import gui.ProfileSelector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.YES_NO_OPTION;

/**
 * This class represents a Profile Creation Window for a user's profile information.
 * It allows users to input their gender, date of birth, height, weight, and units of measurement.
 */
public class ProfileCreationWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField dateOfBirthField, heightField, weightField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private JRadioButton metricRadioButton, imperialRadioButton;
    private ButtonGroup unitsGroup;
    private UIController uic;
    private String date;
    private String name;
    private boolean male;
    private String heightA;
    private String weightA;
    private String metric;
    JTextField fullNameField;

    /**
     * Constructor for the ProfileCreationWindow.
     * Sets up the UI components for profile creation.
     */
    public ProfileCreationWindow(UIController uic) {

        this.uic = uic;
        // Initialize the window
        setTitle("Profile Creation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the main panel and set layout constraints
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.BOTH; // Allow components to stretch both vertically and horizontally
        cons.insets = new Insets(5, 5, 5, 5);

        // Add gender components
        cons.gridx = 0;
        cons.gridy = 0;
        JLabel gender = new JLabel("Gender:");
        mainPanel.add(gender, cons);

        cons.gridx = 1;
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);

        JPanel genderPanel = new JPanel();
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        mainPanel.add(genderPanel, cons);

        // Add Full Name input
        cons.gridx = 0;
        cons.gridy = 1;
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(fullNameLabel, cons);

        cons.gridx = 1;
        fullNameField = new JTextField(15);
        mainPanel.add(fullNameField, cons);

        // Add Date of Birth input
        cons.gridx = 0;
        cons.gridy = 2;
        JLabel DOB = new JLabel("Date of Birth (yyyy-mm-dd):");
        mainPanel.add(DOB, cons);

        cons.gridx = 1;
        dateOfBirthField = new JTextField(15);
        mainPanel.add(dateOfBirthField, cons);

        // Add Height input
        cons.gridx = 0;
        cons.gridy = 3;
        JLabel height = new JLabel("Height (cm):");
        mainPanel.add(height, cons);

        cons.gridx = 1;
        heightField = new JTextField(15);
        mainPanel.add(heightField, cons);

        // Add Weight input
        cons.gridx = 0;
        cons.gridy = 4;
        JLabel weight = new JLabel("Weight (kg):");
        mainPanel.add(weight, cons);

        cons.gridx = 1;
        weightField = new JTextField(15);
        mainPanel.add(weightField, cons);

        // Add Units of Measurement
        cons.gridx = 0;
        cons.gridy = 5;
        JLabel unit = new JLabel("Units of Measurement:");
        mainPanel.add(unit, cons);

        cons.gridx = 1;
        metricRadioButton = new JRadioButton("Metric");
        imperialRadioButton = new JRadioButton("Imperial");

        unitsGroup = new ButtonGroup();
        unitsGroup.add(metricRadioButton);
        unitsGroup.add(imperialRadioButton);

        JPanel unitsPanel = new JPanel();
        unitsPanel.add(metricRadioButton);
        unitsPanel.add(imperialRadioButton);
        mainPanel.add(unitsPanel, cons);

        // Create "Create Profile" button
        cons.gridx = 0;
        cons.gridy = 7;
        cons.gridwidth = 2;
        cons.anchor = GridBagConstraints.CENTER;
        cons.weightx = 0;
        cons.weighty = 0;

        JButton createProfileButton = new JButton("Create Profile");
        createProfileButton.setPreferredSize(new Dimension(100, 30));
        createProfileButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                date = dateOfBirthField.getText();
                name = fullNameField.getText();
                male = maleRadioButton.isSelected();
                heightA = heightField.getText();
                weightA = weightField.getText();
                metric = String.valueOf(metricRadioButton.isSelected());
                if (uic.profileCreation(name, male, heightA, weightA, date)) {
                    message();
                }
                // Handle user inputs here:
            }
        });

        // Set fonts and styles
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 14);

        gender.setFont(labelFont);
        maleRadioButton.setFont(buttonFont);
        femaleRadioButton.setFont(buttonFont);
        DOB.setFont(labelFont);
        height.setFont(labelFont);
        weight.setFont(labelFont);
        unit.setFont(labelFont);
        metricRadioButton.setFont(buttonFont);
        imperialRadioButton.setFont(buttonFont);
        createProfileButton.setFont(buttonFont);

        // Styling
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        genderPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        unitsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        createProfileButton.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));

        mainPanel.setBackground(new Color(240, 240, 240));
        createProfileButton.setForeground(Color.WHITE);
        createProfileButton.setBackground(new Color(0, 102, 204));

        mainPanel.add(createProfileButton, cons);

        // Set the mainPanel as the content pane
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void message() {
        int a = JOptionPane.showConfirmDialog(this, "This name is already taken. Do you want to override?", "Name Unavailable", YES_NO_OPTION);
        if (a == 0) {
            System.out.println(name);
            System.out.println(heightA);
            System.out.println(weightA);
            System.out.println("formatted date: " + date);
            uic.profileCreation(a, name, male, heightA, weightA, date);

            MainMenu mm = new MainMenu(uic);
            mm.setContentPane(mm.panel);
            mm.setVisible(true);
            mm.setSize(300,300);
            this.dispose();
        }

    }
}


