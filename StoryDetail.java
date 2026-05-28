package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StoryDetail extends JFrame {
    private Story story;

    public StoryDetail(Story story) {
        this.story = story;
        setTitle("Chi tiết truyện - " + story.title);
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConfig.BG);
        setLayout(new BorderLayout());

        // 1. THANH ĐIỀU HƯỚNG (Nút Back)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton btnBack = new JButton("⬅ Quay lại");
        btnBack.setFocusPainted(false);
        btnBack.setBackground(UIConfig.PRIMARY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose(); 
        });
        topPanel.add(btnBack);

        // 2. KHỞI TẠO NỘI DUNG CHI TIẾT
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Tạo các thành phần (Chưa add)
        JLabel imgLabel = new JLabel("", SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(200, 280));
        imgLabel.setMaximumSize(new Dimension(200, 280));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(UIConfig.SHADOW);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String coverPath = "assets/covers/" + story.imageName;
        java.io.File coverFile = new java.io.File(coverPath);
        
        if (coverFile.exists()) {
            ImageIcon icon = new ImageIcon(coverPath);
            Image img = icon.getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("NO IMG");
            imgLabel.setForeground(Color.GRAY);
        }

        JLabel titleLabel = new JLabel("<html><div style='text-align: center;'>" + story.title + "</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(UIConfig.PRIMARY);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel authorLabel = new JLabel("Tác giả: " + story.author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel categoryLabel = new JLabel("Thể loại: " + story.category);
        categoryLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        categoryLabel.setForeground(Color.GRAY);
        categoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea descArea = new JTextArea(story.description);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        descArea.setEditable(false); 
        descArea.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        // --- LẮP RÁP PHẦN THÔNG TIN TRUYỆN VÀO TRƯỚC ---
        contentPanel.add(imgLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(authorLabel);
        contentPanel.add(categoryLabel);
        contentPanel.add(descArea);
        contentPanel.add(Box.createVerticalStrut(10));

        // --- SAU ĐÓ MỚI LẮP RÁP DANH SÁCH CHAPTER XUỐNG DƯỚI CÙNG ---
        JLabel chapTitle = new JLabel("Danh sách Chương");
        chapTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        chapTitle.setForeground(UIConfig.PRIMARY);
        chapTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(chapTitle);
        contentPanel.add(Box.createVerticalStrut(10));

        List<Chapter> chapters = DTBConnector.getChaptersByStoryId(story.id);
        
        if (chapters.isEmpty()) {
            JLabel emptyChap = new JLabel("Truyện đang cập nhật, chưa có chương mới.");
            emptyChap.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(emptyChap);
        } else {
            for (Chapter chap : chapters) {
                JButton btnChap = new JButton(chap.chapterName);
                btnChap.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                btnChap.setMaximumSize(new Dimension(300, 40));
                btnChap.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnChap.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // Mở màn hình đọc truyện
                btnChap.addActionListener(e -> {
                    new ReadScreen(chap).setVisible(true);
                    this.dispose(); 
                });
                
                contentPanel.add(btnChap);
                contentPanel.add(Box.createVerticalStrut(5));
            }
        }

        // 3. TẠO THANH CUỘN CHO TOÀN BỘ TRANG
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}
