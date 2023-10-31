package gui;

import controller.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;


/**
 * Use Case 3
 * A GUI window for logging exercise details and calculating calories burnt and BMR.
*/
public class ExerciseLogWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField dateField, timeField, durationField;
    private JComboBox<String> exerciseTypeComboBox, intensityComboBox;
    private JLabel caloriesBurntLabel, bmrLabel;
    
    /**
     * Constructs a new gui.ExerciseLogWindow.
    */
    public ExerciseLogWindow() {
    	
        setTitle("dataObjects.Exercise Log");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUIFont(new javax.swing.plaf.FontUIResource("Arial", Font.BOLD, 14));
        createUI();
    }

    /**
     * Constructs a new gui.ExerciseLogWindow.
    */
    private void createUI() {
    	
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.insets = new Insets(5, 5, 5, 5);

        cons.gridx = 0;
        cons.gridy = 0;
        JLabel dateLabel = new JLabel("Date:");
        mainPanel.add(dateLabel, cons);

        cons.gridx = 1;
        dateField = new JTextField(15);
        mainPanel.add(dateField, cons);

        cons.gridx = 0;
        cons.gridy = 1;
        JLabel timeLabel = new JLabel("Time:");
        mainPanel.add(timeLabel, cons);

        cons.gridx = 1;
        timeField = new JTextField(15);
        mainPanel.add(timeField, cons);

        cons.gridx = 0;
        cons.gridy = 2;
        JLabel exerciseTypeLabel = new JLabel("dataObjects.Exercise Type:");
        mainPanel.add(exerciseTypeLabel, cons);

        cons.gridx = 1;
        String[] exerciseTypes = {"Walking", "Running", "Biking", "Swimming", "Others"};
        exerciseTypeComboBox = new JComboBox<>(exerciseTypes);
        mainPanel.add(exerciseTypeComboBox, cons);

        cons.gridx = 0;
        cons.gridy = 3;
        JLabel durationLabel = new JLabel("Duration (minutes):");
        mainPanel.add(durationLabel, cons);

        cons.gridx = 1;
        durationField = new JTextField(15);
        mainPanel.add(durationField, cons);

        cons.gridx = 0;
        cons.gridy = 4;
        JLabel intensityLabel = new JLabel("Intensity:");
        mainPanel.add(intensityLabel, cons);

        cons.gridx = 1;
        String[] intensities = {"Low", "Medium", "High", "Very High"};
        intensityComboBox = new JComboBox<>(intensities);
        mainPanel.add(intensityComboBox, cons);

        cons.gridx = 0;
        cons.gridy = 5;
        JLabel caloriesBurntTitle = new JLabel("Calories Burnt:");
        mainPanel.add(caloriesBurntTitle, cons);

        cons.gridx = 1;
        caloriesBurntLabel = new JLabel("Calories will be calculated here");
        mainPanel.add(caloriesBurntLabel, cons);

        cons.gridx = 0;
        cons.gridy = 6;
        JLabel bmrTitle = new JLabel("BMR:");
        mainPanel.add(bmrTitle, cons);

        cons.gridx = 1;
        bmrLabel = new JLabel("BMR will be displayed here");
        mainPanel.add(bmrLabel, cons);

        cons.gridx = 0;
        cons.gridy = 7;
        cons.gridwidth = 2;
        cons.anchor = GridBagConstraints.CENTER;

        cons.weightx = 0;
        cons.weighty = 0;

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setPreferredSize(new Dimension(100, 30));
        calculateButton.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Handle calculations for calories burnt and BMR here
                // Update caloriesBurntLabel and bmrLabel accordingly

                UIController uic = new UIController();
            	
            }
        });

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        calculateButton.setBackground(new Color(0, 102, 204));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(calculateButton, cons);

        setContentPane(mainPanel);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Sets the font for the entire Swing UI.
     *
     * @param f The FontUIResource to set as the default font.
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
    
    /**
     * Main method to launch the gui.ExerciseLogWindow.
     *
     * @param args Command-line arguments (not used).
    */
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(() -> new ExerciseLogWindow());
    }
}
