package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileCreationWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField dateOfBirthField, heightField, weightField;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private JRadioButton metricRadioButton, imperialRadioButton;
    private ButtonGroup unitsGroup;

    public ProfileCreationWindow() {
    	
        setTitle("Profile Creation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
      //  Color backgroundColor = new Color(30, 30, 30);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
      //  mainPanel.setBackground(backgroundColor);
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.BOTH; // Allow components to stretch both vertically and horizontally
        cons.insets = new Insets(5, 5, 5, 5);

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

        cons.gridx = 0;
        cons.gridy = 1;
        JLabel DOB = new JLabel("Date of Birth (yyyy-mm-dd):");
        mainPanel.add(DOB, cons);

        cons.gridx = 1;
        dateOfBirthField = new JTextField(15);
        mainPanel.add(dateOfBirthField, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        JLabel height = new JLabel("Height (cm):");
        mainPanel.add(height, cons);

        cons.gridx = 1;
        heightField = new JTextField(15);
        mainPanel.add(heightField, cons);

        cons.gridx = 0;
        cons.gridy = 3;
        JLabel weight = new JLabel("Weight (kg):");
        mainPanel.add(weight, cons);

        cons.gridx = 1;
        weightField = new JTextField(15);
        mainPanel.add(weightField, cons);

        cons.gridx = 0;
        cons.gridy = 4;
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

        cons.gridx = 0;
        cons.gridy = 5;
        cons.gridwidth = 2;
        cons.anchor = GridBagConstraints.CENTER;

        // Define weights for the "Create Profile" button to make it expand both vertically and horizontally.
        cons.weightx = 1.0;
        cons.weighty = 1.0;
        JButton createProfileButton = new JButton("Create Profile");

        createProfileButton.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle user inputs here:
            }
        });

        mainPanel.add(createProfileButton, cons);

        // Set the mainPanel as the content pane
        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> new ProfileCreationWindow());
    }
}






























