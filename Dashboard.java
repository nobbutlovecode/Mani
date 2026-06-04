package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Manvi - Home");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(UIConfig.BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblLogo = new JLabel("<html><b style='color:#6d141a; font-size:20px'>MANVI</b></html>");

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        actionPanel.setOpaque(false);

        JButton btnReload = new JButton("🔄");
        btnReload.setFocusPainted(false);
        btnReload.setBackground(UIConfig.PRIMARY);
        btnReload.setForeground(Color.WHITE);
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });

        JButton btnProfile = new JButton("👤 Cá nhân");
        btnProfile.setFocusPainted(false);
        btnProfile.setBackground(UIConfig.PRIMARY);
        btnProfile.setForeground(Color.WHITE);
        btnProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProfile.addActionListener(e -> {
            new ProfileScreen().setVisible(true);
            this.dispose();
        });

        actionPanel.add(btnReload);
        actionPanel.add(btnProfile);

        headerPanel.add(lblLogo, BorderLayout.WEST);
        headerPanel.add(actionPanel, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel contentGrid = new JPanel();
        contentGrid.setLayout(new BoxLayout(contentGrid, BoxLayout.Y_AXIS));
        contentGrid.setOpaque(false);

        List<Story> stories = DTBConnector.getStories();
        if (stories.isEmpty()) {
            contentGrid.add(new JLabel("Chưa có truyện nào trong CSDL..."));
        } else {
            for (Story s : stories) {
                contentGrid.add(new MangaCard(s));
                contentGrid.add(Box.createVerticalStrut(15));
            }
        }

        JScrollPane scroll = new JScrollPane(contentGrid);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(scroll, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
}
