package view;

import controller.UIController;
import model.DBAccess;
import model.DBUser;
import model.dataObjects.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The type Launcher.
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
        button1.addActionListener(new ActionListener() {
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
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = new MainMenu(new UIController(new User("")));
                ProfileCreationWindow pcw = new ProfileCreationWindow(new UIController(new User("")));
                Launcher.super.dispose();
            }
        });
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Launcher ps = new Launcher();
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
