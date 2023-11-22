package gui;

import controller.DBMeal;
import controller.UIController;

import javax.swing.*;

public class FoodGuideUI extends JFrame {
    private JPanel panel;
    private JTextArea textArea1;
    private JTextArea textArea2;

    private UIController uic;
    public FoodGuideUI(UIController uic) {
        this.uic = uic;
        DBMeal dbm = new DBMeal();
        dbm.getTotals("J");



    }

    public static void main(String[] args) {
        FoodGuideUI fgUI = new FoodGuideUI(new UIController());
        fgUI.setContentPane(fgUI.panel);
        fgUI.setVisible(true);
        fgUI.setSize(900,900);
    }
}
