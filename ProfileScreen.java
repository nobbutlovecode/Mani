package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProfileScreen extends JFrame {
    public ProfileScreen() {
        setTitle("Hồ sơ cá nhân");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConfig.BG);
        setLayout(new BorderLayout());

        // 1. THANH ĐIỀU HƯỚNG
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton btnBack = new JButton("⬅ Trang chủ");
        btnBack.setBackground(UIConfig.PRIMARY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });
        topPanel.add(btnBack);

        // 2. KHU VỰC THÔNG TIN USER
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

        JButton btnLogout = new JButton("Đăng xuất");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(Color.DARK_GRAY);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(e -> {
            DTBConnector.currentUser = null; // Xóa session
            new LoginScreen().setVisible(true);
            this.dispose();
        });

        infoPanel.add(avatar);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(lblName);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(btnLogout);
        infoPanel.add(Box.createVerticalStrut(30));

        JLabel lblHistory = new JLabel("Lịch sử đọc truyện:");
        lblHistory.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHistory.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(lblHistory);
        infoPanel.add(Box.createVerticalStrut(10));

        // 3. KHU VỰC BẢNG LỊCH SỬ (JTABLE)
        String[] columns = {"Tên truyện", "Chương", "Thời gian"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép sửa dữ liệu trên bảng
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(UIConfig.SHADOW);
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);
        
        // Điều chỉnh kích thước cột
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Đổ dữ liệu từ DB vào bảng
        if (DTBConnector.currentUser != null) {
            List<String[]> historyList = DTBConnector.getReadingHistory(DTBConnector.currentUser);
            for (String[] row : historyList) {
                model.addRow(row);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        infoPanel.add(scrollPane);

        add(topPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
    }
}
