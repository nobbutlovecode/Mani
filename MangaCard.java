package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class MangaCard extends JPanel {
    private Story story;

    public MangaCard(Story story) {
        this.story = story;
        setPreferredSize(new Dimension(340, 120)); // Chỉnh lại size cho dạng list dọc
        setMaximumSize(new Dimension(340, 120)); // Ép cứng size tránh bị vỡ layout
        setBackground(UIConfig.CARD_BG);
        setLayout(new BorderLayout(15, 0));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        // 1. Khu vực ảnh bìa (Tạm thời để 1 ô màu xám, ngày 8 ta sẽ load ảnh thật)
        // 1. Khu vực ảnh bìa (Có cơ chế Fallback chống Crash)
        JLabel imgLabel = new JLabel("", SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(75, 100));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(UIConfig.SHADOW);

        String coverPath = "assets/covers/" + story.imageName;
        java.io.File coverFile = new java.io.File(coverPath);
        
        if (coverFile.exists()) {
            // Load và scale ảnh vừa khung 75x100
            ImageIcon icon = new ImageIcon(coverPath);
            Image img = icon.getImage().getScaledInstance(75, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            // Fallback nếu không có ảnh
            imgLabel.setText("NO IMG");
            imgLabel.setForeground(Color.GRAY);
        }
        // 2. Khu vực chứa Tên và Tác giả
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false); // Trong suốt để ăn theo nền Card_BG

        JLabel titleLabel = new JLabel("<html><b>" + story.title + "</b></html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(UIConfig.PRIMARY);

        JLabel authorLabel = new JLabel("Tác giả: " + story.author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.setForeground(UIConfig.TEXT_MAIN);
        
        JLabel categoryLabel = new JLabel("Thể loại: " + story.category);
        categoryLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        categoryLabel.setForeground(Color.GRAY);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5)); // Khoảng cách
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(categoryLabel);

        // Thêm vào MangaCard
        add(imgLabel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);
        
        // Thay đổi con trỏ chuột khi chỉ vào Card
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // Mở trang chi tiết và truyền object story qua
                StoryDetail detailScreen = new StoryDetail(story);
                detailScreen.setVisible(true);
                
                // Ẩn (đóng) cái Dashboard hiện tại đi
                Window window = SwingUtilities.getWindowAncestor(MangaCard.this);
                if (window != null) {
                    window.dispose();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Bo góc 20px
    }
}
