package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    public Dashboard() {
        setTitle("Manvi - Home");
        setSize(400, 750); // Giữ đúng size Mobile
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); 

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Tạm thời tô màu nền hoặc load ảnh an toàn
                g.setColor(UIConfig.BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Căn lề 4 bên
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel lblLogo = new JLabel("<html><b style='color:#6d141a; font-size:20px'>MANVI</b></html>");
        JButton btnProfile = new JButton("👤 Cá nhân");
        btnProfile.setFocusPainted(false);
        btnProfile.setBackground(UIConfig.PRIMARY);
        btnProfile.setForeground(Color.WHITE);
        btnProfile.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProfile.addActionListener(e -> {
            new ProfileScreen().setVisible(true);
            this.dispose();
        });
        
         headerPanel.add(lblLogo, BorderLayout.WEST);
        headerPanel.add(btnProfile, BorderLayout.EAST);
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Panel chứa danh sách truyện
        JPanel contentGrid = new JPanel();
        contentGrid.setLayout(new BoxLayout(contentGrid, BoxLayout.Y_AXIS));
        contentGrid.setOpaque(false);

        // ĐỔ DỮ LIỆU TỪ SQL VÀO ĐÂY
        List<Story> stories = DTBConnector.getStories();
        if(stories.isEmpty()) {
            contentGrid.add(new JLabel("Chưa có truyện nào trong CSDL..."));
        } else {
            for(Story s : stories) {
                contentGrid.add(new MangaCard(s));
                contentGrid.add(Box.createVerticalStrut(15)); // Khoảng cách giữa các Card
            }
        }

        // Thiết lập thanh cuộn (Scroll)
        JScrollPane scroll = new JScrollPane(contentGrid);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        // Tăng tốc độ cuộn chuột
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        // Ẩn thanh cuộn ngang
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(scroll, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }
}
