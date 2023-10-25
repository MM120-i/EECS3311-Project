package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalorieTrackerUI {
	
    private JFrame frame;
    private JPanel controlPanel;
    private JTextArea dataTextArea;
    private JButton visualizeButton;

    public CalorieTrackerUI() {
    	
        frame = new JFrame("Calorie Tracker");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        JLabel startDateLabel = new JLabel("Start Date: ");
        JTextField startDateField = new JTextField(10);
        JLabel endDateLabel = new JLabel("End Date: ");
        JTextField endDateField = new JTextField(10);

        visualizeButton = new JButton("Visualize");
        dataTextArea = new JTextArea(10, 40);

        controlPanel.add(startDateLabel);
        controlPanel.add(startDateField);
        controlPanel.add(endDateLabel);
        controlPanel.add(endDateField);
        controlPanel.add(visualizeButton);

        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(dataTextArea, BorderLayout.CENTER);

        visualizeButton.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                // Add code to fetch and visualize data based on the selected time period.
                String startDate = startDateField.getText();
                
                String endDate = endDateField.getText();
                // Fetch and display data in the dataTextArea.
                dataTextArea.setText("Data from " + startDate + " to " + endDate + ":\n");
                // Add visualization logic here.
            }
        });
    }

    public void showUI() {
    	
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
        	
            public void run() {
            	
                CalorieTrackerUI ui = new CalorieTrackerUI();
                ui.showUI();
            }
        });
    }
}
