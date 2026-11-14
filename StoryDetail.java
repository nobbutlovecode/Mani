package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

        // Floating Rounded Header Container
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

        JButton btnBack = new JButton("⬅ Quay lại");
        btnBack.setFont(UIConfig.FONT_SMALL);
        btnBack.setFocusPainted(false);
        btnBack.setBackground(UIConfig.PRIMARY);
        btnBack.setForeground(Color.WHITE);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });

        JButton btnReload = new JButton("Làm mới");
        btnReload.setFont(UIConfig.FONT_SMALL);
        btnReload.setFocusPainted(false);
        btnReload.setBackground(UIConfig.PRIMARY_LIGHT);
        btnReload.setForeground(UIConfig.PRIMARY);
        btnReload.setBorder(BorderFactory.createLineBorder(UIConfig.PRIMARY_BORDER, 1));
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.addActionListener(e -> {
            new StoryDetail(story).setVisible(true);
            this.dispose();
        });

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(btnReload, BorderLayout.EAST);
        topWrapper.add(topPanel, BorderLayout.CENTER);

        // Content Area
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(new EmptyBorder(14, 14, 20, 14));

        // Cover Thumbnail with rounded container
        JPanel coverCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(241, 245, 249));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_CARD, UIConfig.ARC_CARD);
            }
        };
        coverCard.setLayout(new BorderLayout());
        coverCard.setOpaque(false);
        coverCard.setPreferredSize(new Dimension(180, 250));
        coverCard.setMaximumSize(new Dimension(180, 250));
        coverCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imgLabel = new JLabel("", SwingConstants.CENTER);
        File coverFile = new File("assets/covers/" + story.imageName);
        if (coverFile.exists()) {
            Image img = new ImageIcon(coverFile.getAbsolutePath())
                .getImage().getScaledInstance(180, 250, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("<html><div style='text-align:center;'><b>ẢNH BÌA</b><br><small>MANVI</small></div></html>");
            imgLabel.setFont(UIConfig.FONT_BADGE);
            imgLabel.setForeground(UIConfig.TEXT_MUTED);
        }
        coverCard.add(imgLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel(
            "<html><div style='text-align: center;'>" + story.title + "</div></html>", SwingConstants.CENTER);
        titleLabel.setFont(UIConfig.FONT_HEADER);
        titleLabel.setForeground(UIConfig.TEXT_MAIN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel authorLabel = new JLabel("Tác giả: " + story.author, SwingConstants.CENTER);
        authorLabel.setFont(UIConfig.FONT_SMALL);
        authorLabel.setForeground(UIConfig.TEXT_MUTED);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        tagPanel.setOpaque(false);
        JLabel categoryTag = new JLabel(" " + story.category + " ");
        categoryTag.setFont(UIConfig.FONT_BADGE);
        categoryTag.setOpaque(true);
        categoryTag.setBackground(UIConfig.PRIMARY_LIGHT);
        categoryTag.setForeground(UIConfig.PRIMARY);
        categoryTag.setBorder(BorderFactory.createLineBorder(UIConfig.PRIMARY_BORDER, 1));
        tagPanel.add(categoryTag);

        // Synopsis Card (Fully Rounded)
        JPanel synopsisCard = new JPanel(new BorderLayout()) {
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
        synopsisCard.setOpaque(false);
        synopsisCard.setBorder(new EmptyBorder(12, 14, 12, 14));
        synopsisCard.setMaximumSize(new Dimension(352, 160));

        JTextArea descArea = new JTextArea(story.description);
        descArea.setFont(UIConfig.FONT_SMALL);
        descArea.setForeground(UIConfig.TEXT_MAIN);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        synopsisCard.add(descArea, BorderLayout.CENTER);

        contentPanel.add(coverCard);
        contentPanel.add(Box.createVerticalStrut(14));
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(4));
        contentPanel.add(authorLabel);
        contentPanel.add(Box.createVerticalStrut(6));
        contentPanel.add(tagPanel);
        contentPanel.add(Box.createVerticalStrut(14));
        contentPanel.add(synopsisCard);
        contentPanel.add(Box.createVerticalStrut(16));

        // Chapter Header
        JPanel chapHeader = new JPanel(new BorderLayout());
        chapHeader.setOpaque(false);
        chapHeader.setMaximumSize(new Dimension(352, 28));

        JLabel chapTitle = new JLabel("DANH SÁCH CHƯƠNG");
        chapTitle.setFont(UIConfig.FONT_SUBTITLE);
        chapTitle.setForeground(UIConfig.PRIMARY);
        chapHeader.add(chapTitle, BorderLayout.WEST);

        contentPanel.add(chapHeader);
        contentPanel.add(Box.createVerticalStrut(8));

        List<Chapter> chapters = DTBConnector.getChaptersByStoryId(story.id);
        if (chapters.isEmpty()) {
            JLabel emptyChap = new JLabel("Truyện đang cập nhật, chưa có chương mới.");
            emptyChap.setFont(UIConfig.FONT_SMALL);
            emptyChap.setForeground(UIConfig.TEXT_MUTED);
            emptyChap.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(emptyChap);
        } else {
            for (Chapter chap : chapters) {
                boolean isUnlocked = DTBConnector.isChapterUnlocked(DTBConnector.currentUser, chap.chapterId)
                    || chap.chapterName.contains("Chương 1") || chap.chapterName.contains("Chap 1");

                String statusText = isUnlocked ? "Đã mở" : "🔒 2 Xu";
                JButton btnChap = new JButton("<html><table width='270'><tr><td align='left'><b>" + chap.chapterName + "</b></td><td align='right' style='color:" + (isUnlocked ? "#166534" : "#D97706") + ";'>" + statusText + "</td></tr></table></html>");
                btnChap.setFont(UIConfig.FONT_BODY);
                btnChap.setMaximumSize(new Dimension(352, 42));
                btnChap.setBackground(UIConfig.CARD_BG);
                btnChap.setForeground(UIConfig.TEXT_MAIN);
                btnChap.setBorder(BorderFactory.createLineBorder(UIConfig.CARD_BORDER, 1));
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
                contentPanel.add(Box.createVerticalStrut(6));
            }
        }

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(topWrapper, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}
