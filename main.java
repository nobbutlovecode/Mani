package com.mycompany.dacs1.Config;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class main {
    public static void main(String[] args) {
        FlatLightLaf.setup();
        // Fully unified rounded arcs across all components
        UIManager.put("Button.arc", UIConfig.ARC_BUTTON);
        UIManager.put("Component.arc", UIConfig.ARC_CARD);
        UIManager.put("TextComponent.arc", UIConfig.ARC_INPUT);
        UIManager.put("ScrollBar.thumbArc", UIConfig.ARC_PILL);
        UIManager.put("TabbedPane.tabArc", UIConfig.ARC_INPUT);
        UIManager.put("Component.focusWidth", 1);
        UIManager.put("Button.innerFocusWidth", 0);

        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}
