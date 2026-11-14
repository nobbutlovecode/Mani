package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProfileScreen extends JFrame {
    public ProfileScreen() {
        setTitle("Manvi - Hồ sơ & Ví");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConfig.BG);
        setLayout(new BorderLayout());

        // Floating Rounded Header Bar
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.setBorder(new EmptyBorder(10, 14, 0, 14));

        JPanel topPanel = new JPanel(new BorderLayout()) {
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
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(8, 12, 8, 12));

        JButton btnBack = new JButton("⬅ Trang chủ");
        btnBack.setFont(UIConfig.FONT_SMALL);
        btnBack.setBackground(UIConfig.PRIMARY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });

        JButton btnReload = new JButton("Làm mới");
        btnReload.setFont(UIConfig.FONT_SMALL);
        btnReload.setBackground(UIConfig.PRIMARY_LIGHT);
        btnReload.setForeground(UIConfig.PRIMARY);
        btnReload.setBorder(BorderFactory.createLineBorder(UIConfig.PRIMARY_BORDER, 1));
        btnReload.setFocusPainted(false);
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.addActionListener(e -> {
            new ProfileScreen().setVisible(true);
            this.dispose();
        });

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(btnReload, BorderLayout.EAST);
        topWrapper.add(topPanel, BorderLayout.CENTER);

        // Main Profile Container
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(new EmptyBorder(14, 14, 16, 14));

        // User Avatar & Name Card
        JPanel userHeader = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConfig.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
            }
        };
        userHeader.setLayout(new BoxLayout(userHeader, BoxLayout.Y_AXIS));
        userHeader.setOpaque(false);
        userHeader.setBorder(new EmptyBorder(14, 16, 14, 16));
        userHeader.setMaximumSize(new Dimension(352, 95));
        userHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel(DTBConnector.currentUser != null ? DTBConnector.currentUser : "Khách", SwingConstants.CENTER);
        lblName.setFont(UIConfig.FONT_HEADER);
        lblName.setForeground(UIConfig.PRIMARY);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblRole = new JLabel("Thành viên chính thức", SwingConstants.CENTER);
        lblRole.setFont(UIConfig.FONT_SMALL);
        lblRole.setForeground(UIConfig.TEXT_MUTED);
        lblRole.setAlignmentX(Component.CENTER_ALIGNMENT);

        userHeader.add(lblName);
        userHeader.add(Box.createVerticalStrut(4));
        userHeader.add(lblRole);

        // Wallet Card (Rounded)
        JPanel walletCard = new JPanel(new BorderLayout(10, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConfig.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
            }
        };
        walletCard.setOpaque(false);
        walletCard.setBorder(new EmptyBorder(12, 16, 12, 16));
        walletCard.setMaximumSize(new Dimension(352, 68));
        walletCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        int currentCoins = DTBConnector.currentUser != null ? DTBConnector.getUserCoins(DTBConnector.currentUser) : 0;
        JLabel lblCoins = new JLabel("<html><small style='color:#64748B;'>SỐ DƯ VÍ</small><br><b style='font-size:17px; color:#D97706;'>" + String.format("%,d", currentCoins) + " Xu</b></html>");
        lblCoins.setFont(UIConfig.FONT_SUBTITLE);

        JButton btnRecharge = new JButton("Nạp xu ➕");
        btnRecharge.setFont(UIConfig.FONT_SMALL);
        btnRecharge.setBackground(UIConfig.PRIMARY);
        btnRecharge.setForeground(Color.WHITE);
        btnRecharge.setFocusPainted(false);
        btnRecharge.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRecharge.addActionListener(e -> {
            String[] packages = {
                "200 Xu = 10,000 VNĐ",
                "400 Xu = 20,000 VNĐ",
                "1,000 Xu = 50,000 VNĐ",
                "2,000 Xu = 100,000 VNĐ",
                "4,000 Xu = 200,000 VNĐ",
                "10,000 Xu = 500,000 VNĐ (Khuyên dùng)"
            };

            String selected = (String) JOptionPane.showInputDialog(this,
                "Chọn gói xu bạn muốn nạp:", "Cổng thanh toán MANVI Pay",
                JOptionPane.QUESTION_MESSAGE, null, packages, packages[0]);

            if (selected != null) {
                try {
                    String coinsPart = selected.split(" Xu")[0].replace(",", "").trim();
                    int coinsToAdd = Integer.parseInt(coinsPart);

                    if (DTBConnector.addCoins(DTBConnector.currentUser, coinsToAdd)) {
                        JOptionPane.showMessageDialog(this,
                            "Thanh toán thành công! Đã cộng " + String.format("%,d", coinsToAdd) + " xu vào tài khoản.");
                        new ProfileScreen().setVisible(true);
                        this.dispose();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Có lỗi xảy ra trong quá trình xử lý giao dịch.", "Lỗi nạp tiền", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        walletCard.add(lblCoins, BorderLayout.WEST);
        walletCard.add(btnRecharge, BorderLayout.EAST);

        // Tabbed Panel in Rounded Container
        JPanel tabContainer = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConfig.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
            }
        };
        tabContainer.setOpaque(false);
        tabContainer.setBorder(new EmptyBorder(8, 8, 8, 8));
        tabContainer.setMaximumSize(new Dimension(352, 280));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(UIConfig.FONT_BADGE);
        tabbedPane.setBackground(UIConfig.CARD_BG);

        String[] columnsRead = {"Tên truyện", "Chương", "Thời gian"};
        DefaultTableModel modelRead = new DefaultTableModel(columnsRead, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tableRead = new JTable(modelRead);
        tableRead.setRowHeight(26);
        tableRead.setFont(UIConfig.FONT_SMALL);
        tableRead.setShowGrid(true);
        tableRead.setGridColor(UIConfig.CARD_BORDER);

        if (DTBConnector.currentUser != null) {
            for (String[] row : DTBConnector.getReadingHistory(DTBConnector.currentUser)) {
                modelRead.addRow(row);
            }
        }

        String[] columnsCoins = {"Truyện", "Chương", "Chi phí", "Thời gian"};
        DefaultTableModel modelCoins = new DefaultTableModel(columnsCoins, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tableCoins = new JTable(modelCoins);
        tableCoins.setRowHeight(26);
        tableCoins.setFont(UIConfig.FONT_SMALL);
        tableCoins.setShowGrid(true);
        tableCoins.setGridColor(UIConfig.CARD_BORDER);

        if (DTBConnector.currentUser != null) {
            for (String[] row : DTBConnector.getCoinUsageHistory(DTBConnector.currentUser)) {
                modelCoins.addRow(row);
            }
        }

        JScrollPane scrollRead = new JScrollPane(tableRead);
        scrollRead.setBorder(BorderFactory.createLineBorder(UIConfig.CARD_BORDER, 1));
        JScrollPane scrollCoins = new JScrollPane(tableCoins);
        scrollCoins.setBorder(BorderFactory.createLineBorder(UIConfig.CARD_BORDER, 1));

        tabbedPane.addTab("Lịch sử đọc", scrollRead);
        tabbedPane.addTab("Lịch sử dùng xu", scrollCoins);
        tabContainer.add(tabbedPane, BorderLayout.CENTER);

        JButton btnLogout = new JButton("Đăng xuất tài khoản");
        btnLogout.setFont(UIConfig.FONT_SMALL);
        btnLogout.setMaximumSize(new Dimension(352, 42));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(UIConfig.CARD_BG);
        btnLogout.setForeground(UIConfig.DANGER_RED);
        btnLogout.setBorder(BorderFactory.createLineBorder(new Color(254, 202, 202), 1));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            DTBConnector.currentUser = null;
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        infoPanel.add(userHeader);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(walletCard);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(tabContainer);
        infoPanel.add(Box.createVerticalStrut(14));
        infoPanel.add(btnLogout);

        add(topWrapper, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }
}
