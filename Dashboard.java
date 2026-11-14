package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Manvi - Kho Truyện Tranh");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(UIConfig.BG);

        // Top Wrapper with padding to create a floating rounded header
        JPanel topWrapper = new JPanel();
        topWrapper.setLayout(new BoxLayout(topWrapper, BoxLayout.Y_AXIS));
        topWrapper.setOpaque(false);
        topWrapper.setBorder(new EmptyBorder(10, 14, 4, 14));

        // Floating Rounded Header Bar
        JPanel headerPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConfig.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_PANEL, UIConfig.ARC_PANEL);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_PANEL, UIConfig.ARC_PANEL);
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
        headerPanel.setMaximumSize(new Dimension(360, 56));

        // Brand logo
        JLabel lblLogo = new JLabel("<html><span style='color:#8B1824; font-size:18px; font-weight:bold;'>📖 MANVI</span></html>");

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        actionPanel.setOpaque(false);

        JButton btnReload = new JButton("Làm mới");
        btnReload.setFont(UIConfig.FONT_SMALL);
        btnReload.setFocusPainted(false);
        btnReload.setBackground(UIConfig.PRIMARY_LIGHT);
        btnReload.setForeground(UIConfig.PRIMARY);
        btnReload.setBorder(BorderFactory.createLineBorder(UIConfig.PRIMARY_BORDER, 1));
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });

        JButton btnProfile = new JButton("Cá nhân");
        btnProfile.setFont(UIConfig.FONT_SMALL);
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
        topWrapper.add(headerPanel);
        topWrapper.add(Box.createVerticalStrut(10));

        // Rounded Category Pills Bar
        JPanel catPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConfig.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_PANEL, UIConfig.ARC_PANEL);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_PANEL, UIConfig.ARC_PANEL);
            }
        };
        catPanel.setOpaque(false);
        catPanel.setBorder(new EmptyBorder(8, 12, 8, 12));
        catPanel.setMaximumSize(new Dimension(360, 44));

        String[] categories = {"Tất cả", "Hành động", "Kỳ ảo", "Tình cảm", "Hài hước"};
        for (int i = 0; i < categories.length; i++) {
            JLabel pill = new JLabel(" " + categories[i] + " ");
            pill.setFont(UIConfig.FONT_BADGE);
            pill.setOpaque(true);
            if (i == 0) {
                pill.setBackground(UIConfig.PRIMARY);
                pill.setForeground(Color.WHITE);
            } else {
                pill.setBackground(new Color(241, 245, 249));
                pill.setForeground(UIConfig.TEXT_MUTED);
            }
            pill.setBorder(new EmptyBorder(4, 8, 4, 8));
            catPanel.add(pill);
        }
        topWrapper.add(catPanel);

        mainPanel.add(topWrapper, BorderLayout.NORTH);

        // Content Area with Manga Cards
        JPanel contentGrid = new JPanel();
        contentGrid.setLayout(new BoxLayout(contentGrid, BoxLayout.Y_AXIS));
        contentGrid.setOpaque(false);
        contentGrid.setBorder(new EmptyBorder(10, 14, 16, 14));

        List<Story> stories = DTBConnector.getStories();
        if (stories.isEmpty()) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setBorder(new EmptyBorder(60, 20, 20, 20));
            JLabel lblEmpty = new JLabel("Chưa có truyện nào trong cơ sở dữ liệu...", SwingConstants.CENTER);
            lblEmpty.setFont(UIConfig.FONT_BODY);
            lblEmpty.setForeground(UIConfig.TEXT_MUTED);
            emptyPanel.add(lblEmpty);
            contentGrid.add(emptyPanel);
        } else {
            for (Story s : stories) {
                contentGrid.add(new MangaCard(s));
                contentGrid.add(Box.createVerticalStrut(12));
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
