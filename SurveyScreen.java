package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SurveyScreen extends JFrame {
    private String username;

    public SurveyScreen(String username) {
        this.username = username;
        setTitle("Khảo sát sở thích (Cold Start)");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 25, 30, 25));

        JLabel lblTitle = new JLabel("TÌM HIỂU SỞ THÍCH");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("<html><div style='text-align: center;'>Giúp Manvi gợi ý truyện cực chuẩn<br>dành riêng cho bạn!</div></html>");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JComboBox<String> cbCategory = createComboBox(new String[]{
            "Action (Hành động)", "Romance (Tình cảm)", "Fantasy (Kỳ ảo)", "Comedy (Hài hước)", "Slice of Life"});
        JComboBox<String> cbReadTime = createComboBox(new String[]{
            "Sáng sớm", "Giờ nghỉ trưa", "Buổi tối", "Đêm khuya"});
        JComboBox<String> cbStoryLength = createComboBox(new String[]{
            "Truyện ngắn (Dưới 50 chap)", "Trung bình (50 - 200 chap)", "Dài (Trên 200 chap)"});
        JComboBox<String> cbReadingStyle = createComboBox(new String[]{
            "Đọc dồn 1 lần (Binge-read)", "Đọc mỗi ngày 1 ít", "Chỉ đọc khi rảnh rỗi"});
        JComboBox<String> cbGoal = createComboBox(new String[]{
            "Giải trí, xả stress", "Khám phá cốt truyện sâu sắc", "Giết thời gian", "Học hỏi, tìm cảm hứng"});

        JButton btnSubmit = new JButton("Bắt đầu đọc truyện");
        btnSubmit.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnSubmit.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnSubmit.setBackground(new Color(46, 204, 113));
        btnSubmit.setForeground(Color.WHITE);
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
                JOptionPane.showMessageDialog(this, "Thiết lập thành công! Manvi đã phân tích xong sở thích của bạn.");
                this.dispose();
                new Dashboard().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi lưu khảo sát.");
            }
        });

        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblSub);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(createLabel("1. Thể loại bạn yêu thích nhất?"));
        mainPanel.add(cbCategory);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createLabel("2. Thường đọc truyện vào lúc nào?"));
        mainPanel.add(cbReadTime);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createLabel("3. Độ dài truyện mong muốn?"));
        mainPanel.add(cbStoryLength);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createLabel("4. Phong cách đọc của bạn?"));
        mainPanel.add(cbReadingStyle);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createLabel("5. Mục tiêu khi đọc truyện?"));
        mainPanel.add(cbGoal);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(btnSubmit);
        mainPanel.add(Box.createVerticalGlue());
        add(mainPanel);
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cb.setFont(new Font("SansSerif", Font.PLAIN, 14));
        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        return cb;
    }
}
