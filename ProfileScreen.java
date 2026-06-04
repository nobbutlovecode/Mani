package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProfileScreen extends JFrame {
    public ProfileScreen() {
        setTitle("Hồ sơ cá nhân");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConfig.BG);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        JButton btnBack = new JButton("⬅ Trang chủ");
        btnBack.setBackground(UIConfig.PRIMARY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });

        JButton btnReload = new JButton("🔄");
        btnReload.setBackground(UIConfig.PRIMARY);
        btnReload.setForeground(Color.WHITE);
        btnReload.setFocusPainted(false);
        btnReload.addActionListener(e -> {
            new ProfileScreen().setVisible(true);
            this.dispose();
        });

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(btnReload, BorderLayout.EAST);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel avatar = new JLabel("👤", SwingConstants.CENTER);
        avatar.setFont(new Font("Segoe UI", Font.PLAIN, 60));
        avatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblName = new JLabel("Xin chào, " + DTBConnector.currentUser);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblName.setForeground(UIConfig.PRIMARY);
        lblName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblCoins = new JLabel("Số dư ví: " + String.format("%,d", DTBConnector.getUserCoins(DTBConnector.currentUser)) + " Xu 🪙");
        lblCoins.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCoins.setForeground(new Color(230, 126, 34));
        lblCoins.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(Color.DARK_GRAY);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> {
            DTBConnector.currentUser = null;
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        JButton btnRecharge = new JButton("Nạp xu 🪙");
        btnRecharge.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRecharge.setBackground(new Color(46, 204, 113));
        btnRecharge.setForeground(Color.WHITE);
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

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 12));

        String[] columnsRead = {"Tên truyện", "Chương", "Thời gian"};
        DefaultTableModel modelRead = new DefaultTableModel(columnsRead, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tableRead = new JTable(modelRead);
        tableRead.setRowHeight(25);
        tableRead.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tableRead.setShowGrid(true);
        tableRead.setGridColor(Color.LIGHT_GRAY);
        
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
        tableCoins.setRowHeight(25);
        tableCoins.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tableCoins.setShowGrid(true);
        tableCoins.setGridColor(Color.LIGHT_GRAY);

        if (DTBConnector.currentUser != null) {
            for (String[] row : DTBConnector.getCoinUsageHistory(DTBConnector.currentUser)) {
                modelCoins.addRow(row);
            }
        }

        tabbedPane.addTab("Lịch sử đọc", new JScrollPane(tableRead));
        tabbedPane.addTab("Lịch sử dùng xu", new JScrollPane(tableCoins));

        infoPanel.add(avatar);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblName);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblCoins);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(btnRecharge);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(btnLogout);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(tabbedPane);

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }
}
