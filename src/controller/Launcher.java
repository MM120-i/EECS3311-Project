package controller;

import gui.WelcomeScreen;

public class Launcher {
    public static void main(String[] args) {
        // Create and launch first screen
        WelcomeScreen.CreateWelcome();
        WelcomeScreen.GUI.setVisible(true);
    }

}
