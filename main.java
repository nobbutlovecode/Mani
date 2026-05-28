package com.mycompany.dacs1.Config;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

public class main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        SwingUtilities.invokeLater(() -> {
            LoginScreen login = new LoginScreen();
            login.setVisible(true);
        });
    }
}
