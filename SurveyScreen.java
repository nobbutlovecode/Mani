package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SurveyScreen extends JFrame {
    private String username;

    public SurveyScreen(String username) {
        this.username = username;
        setTitle("Khảo sát sở thích - Manvi");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConfig.BG);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(24, 20, 24, 20));
        mainPanel.setBackground(UIConfig.BG);

        JLabel lblTitle = new JLabel("TÌM HIỂU SỞ THÍCH", SwingConstants.CENTER);
        lblTitle.setFont(UIConfig.FONT_TITLE);
        lblTitle.setForeground(UIConfig.PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("<html><div style='text-align: center;'>Giúp Manvi cá nhân hóa kho truyện dành riêng cho bạn!</div></html>", SwingConstants.CENTER);
        lblSub.setFont(UIConfig.FONT_SMALL);
        lblSub.setForeground(UIConfig.TEXT_MUTED);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> cbCategory = createComboBox(new String[]{
            "Action (Hành động)", "Romance (Tình cảm)", "Fantasy (Kỳ ảo)", "Comedy (Hài hước)", "Slice of Life"});
        JComboBox<String> cbReadTime = createComboBox(new String[]{
            "Sáng sớm", "Giờ nghỉ trưa", "Buổi tối", "Đêm khuya"});
        JComboBox<String> cbStoryLength = createComboBox(new String[]{
            "Truyện ngắn (< 50 chap)", "Trung bình (50 - 200 chap)", "Dài (> 200 chap)"});
        JComboBox<String> cbReadingStyle = createComboBox(new String[]{
            "Đọc dồn 1 lần (Binge-read)", "Đọc mỗi ngày 1 ít", "Chỉ đọc khi rảnh rỗi"});
        JComboBox<String> cbGoal = createComboBox(new String[]{
            "Giải trí, xả stress", "Khám phá cốt truyện sâu sắc", "Giết thời gian", "Học hỏi, tìm cảm hứng"});

        JButton btnSubmit = new JButton("BẮT ĐẦU ĐỌC TRUYỆN ➔");
        btnSubmit.setFont(UIConfig.FONT_SUBTITLE);
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 46));
        btnSubmit.setBackground(UIConfig.PRIMARY);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setFocusPainted(false);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSubmit.addActionListener(e -> {
            boolean success = DTBConnector.saveUserSurvey(username,
                (String) cbCategory.getSelectedItem(),
                (String) cbReadTime.getSelectedItem(),
                (String) cbStoryLength.getSelectedItem(),
                (String) cbReadingStyle.getSelectedItem(),
                (String) cbGoal.getSelectedItem());

            if (success) {
                DTBConnector.currentUser = username;
                JOptionPane.showMessageDialog(this, "Thiết lập thành công! Manvi đã lưu sở thích của bạn.");
                this.dispose();
                new Dashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lưu khảo sát.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(lblSub);
        mainPanel.add(Box.createVerticalStrut(20));

        mainPanel.add(createQuestionRow("1", "Thể loại truyện bạn yêu thích nhất?"));
        mainPanel.add(cbCategory);
        mainPanel.add(Box.createVerticalStrut(12));

        mainPanel.add(createQuestionRow("2", "Thời điểm thường đọc truyện trong ngày?"));
        mainPanel.add(cbReadTime);
        mainPanel.add(Box.createVerticalStrut(12));

        mainPanel.add(createQuestionRow("3", "Độ dài bộ truyện mong muốn?"));
        mainPanel.add(cbStoryLength);
        mainPanel.add(Box.createVerticalStrut(12));

        mainPanel.add(createQuestionRow("4", "Phong cách đọc truyện của bạn?"));
        mainPanel.add(cbReadingStyle);
        mainPanel.add(Box.createVerticalStrut(12));

        mainPanel.add(createQuestionRow("5", "Mục tiêu chính khi đọc truyện?"));
        mainPanel.add(cbGoal);
        mainPanel.add(Box.createVerticalStrut(24));

        mainPanel.add(btnSubmit);
        mainPanel.add(Box.createVerticalGlue());

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(UIConfig.BG);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setContentPane(scroll);
    }

    private JPanel createQuestionRow(String step, String question) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel badge = new JLabel(" " + step + " ");
        badge.setFont(UIConfig.FONT_BADGE);
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(UIConfig.PRIMARY);

        JLabel text = new JLabel(question);
        text.setFont(UIConfig.FONT_BODY);
        text.setForeground(UIConfig.TEXT_MAIN);

        row.add(badge);
        row.add(text);
        row.setBorder(new EmptyBorder(0, 0, 4, 0));
        return row;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cb.setFont(UIConfig.FONT_BODY);
        cb.setBackground(UIConfig.CARD_BG);
        cb.setForeground(UIConfig.TEXT_MAIN);
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.putClientProperty("JComponent.roundRect", true);
        return cb;
    }
}
