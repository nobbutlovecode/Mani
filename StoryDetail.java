package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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

        JPanel topPanel = new JPanel(new BorderLayout());
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

        JButton btnReload = new JButton("🔄");
        btnReload.setFocusPainted(false);
        btnReload.setBackground(UIConfig.PRIMARY);
        btnReload.setForeground(Color.WHITE);
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.addActionListener(e -> {
            new StoryDetail(story).setVisible(true);
            this.dispose();
        });

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(btnReload, BorderLayout.EAST);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JLabel imgLabel = new JLabel("", SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(200, 280));
        imgLabel.setMaximumSize(new Dimension(200, 280));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(UIConfig.SHADOW);
        imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        File coverFile = new File("assets/covers/" + story.imageName);
        if (coverFile.exists()) {
            Image img = new ImageIcon("assets/covers/" + story.imageName)
                .getImage().getScaledInstance(200, 280, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("NO IMG");
            imgLabel.setForeground(Color.GRAY);
        }

        JLabel titleLabel = new JLabel(
            "<html><div style='text-align: center;'>" + story.title + "</div></html>", SwingConstants.CENTER);
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

        contentPanel.add(imgLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(5));
        contentPanel.add(authorLabel);
        contentPanel.add(categoryLabel);
        contentPanel.add(descArea);
        contentPanel.add(Box.createVerticalStrut(10));

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
                boolean isUnlocked = DTBConnector.isChapterUnlocked(DTBConnector.currentUser, chap.chapterId)
                    || chap.chapterName.contains("Chương 1") || chap.chapterName.contains("Chap 1");

                JButton btnChap = new JButton(chap.chapterName + (isUnlocked ? " (Đã mở khóa)" : " (🔒 2 Coin)"));
                btnChap.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                btnChap.setMaximumSize(new Dimension(300, 40));
                btnChap.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnChap.setCursor(new Cursor(Cursor.HAND_CURSOR));

                final boolean finalUnlocked = isUnlocked;
                btnChap.addActionListener(e -> {
                    if (finalUnlocked) {
                        new ReadScreen(chap).setVisible(true);
                        this.dispose();
                    } else {
                        int userCoins = DTBConnector.getUserCoins(DTBConnector.currentUser);
                        int option = JOptionPane.showConfirmDialog(this,
                            "Chương này bị khóa.\nBạn có muốn dùng 2 xu để mở khóa vĩnh viễn không?\nSố dư hiện tại: " + userCoins + " xu",
                            "Mở khóa chương truyện", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (option == JOptionPane.YES_OPTION) {
                            if (DTBConnector.unlockChapter(DTBConnector.currentUser, chap.chapterId, 2)) {
                                JOptionPane.showMessageDialog(this, "Mở khóa thành công! Chúc bạn đọc truyện vui vẻ.");
                                new StoryDetail(story).setVisible(true);
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "Số dư không đủ! Vui lòng nạp thêm xu.",
                                    "Mở khóa thất bại", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                });

                contentPanel.add(btnChap);
                contentPanel.add(Box.createVerticalStrut(5));
            }
        }

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
