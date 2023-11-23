package view;

import model.DBAccess;
import model.DBUser;
import controller.UIController;
import model.dataObjects.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The Launcher class represents the main entry point of the application, providing a user interface
 * for launching the application and selecting a user profile.
 */
public class Launcher extends JFrame{
    private JPanel mainpanel;
    private JLabel label1;
    private JComboBox comboBox1;
    private JButton button1;
    private JButton button2;

    /**
     * Instantiates a new Launcher.
     */
    public Launcher() {
        DBAccess dba = new DBAccess();
        dba.getUsers();
        button1.addActionListener(new ActionListener() { // ActionListener for selecting an existing user profile
            @Override
            public void actionPerformed(ActionEvent e) {
                DBUser dbu = new DBUser();
                MainMenu menu = new MainMenu(new UIController(dbu.loadUser(comboBox1.getSelectedItem().toString())));
                menu.setContentPane(menu.panel);
                menu.setVisible(true);
                menu.setSize(300,300);
                Launcher.super.dispose();
            }
        });
        button2.addActionListener(new ActionListener() {  // ActionListener for creating a new user profile
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = new MainMenu(new UIController(new User(comboBox1.getSelectedItem().toString())));
                ProfileCreationWindow pcw = new ProfileCreationWindow(new UIController(new User(comboBox1.getSelectedItem().toString())));
                Launcher.super.dispose();
            }
        });
    }

    /**
     * The main method, the entry point of the Launcher application.
     *
     * @param args The input arguments.
     */
    public static void main(String[] args) {
        Launcher ps = new Launcher();
        DBAccess dba = new DBAccess();
        ArrayList<String> al =  dba.getUsers();
        // Populate the combo box with available user profiles
        for (String s : al) {
            ps.comboBox1.addItem(s);
        }
        ps.setContentPane(ps.mainpanel);
        ps.setSize(300,300);
        ps.setVisible(true);
    }

}
