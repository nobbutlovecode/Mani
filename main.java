package com.mycompany.dacs1.Config;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        UIManager.put("Button.arc", 20);
        UIManager.put("Component.arc", 20);
        UIManager.put("TextComponent.arc", 20);
        UIManager.put("ScrollBar.thumbArc", 999);

        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
