package gui;

import PROJECT.ProfileCreationWindow;
import controller.DBAccess;
import controller.UIController;
import dataObjects.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProfileSelector extends JFrame{
    private JPanel mainpanel;
    private JLabel label1;
    private JComboBox comboBox1;
    private JButton button1;
    private JButton button2;

    public ProfileSelector() {
        DBAccess dba = new DBAccess();
        dba.getUsers();
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = new MainMenu(new UIController(new User(comboBox1.getSelectedItem().toString())));
                menu.setContentPane(menu.panel);
                menu.setVisible(true);
                menu.setSize(300,300);
                ProfileSelector.super.dispose();
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = new MainMenu(new UIController(new User(comboBox1.getSelectedItem().toString())));
                PROJECT.ProfileCreationWindow pcw = new ProfileCreationWindow();
                ProfileSelector.super.dispose();
            }
        });
    }

    public static void main(String[] args) {
        ProfileSelector ps = new ProfileSelector();
        DBAccess dba = new DBAccess();
        ArrayList<String> al =  dba.getUsers();
        for (String s : al) {
            ps.comboBox1.addItem(s);
        }
        ps.setContentPane(ps.mainpanel);
        ps.setSize(300,300);
        ps.setVisible(true);
    }

}
