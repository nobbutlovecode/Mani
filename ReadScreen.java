package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class ReadScreen extends JFrame {
    public ReadScreen(Chapter chapter) {
        setTitle("Đang đọc: " + chapter.chapterName);
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(15, 15, 18));
        setLayout(new BorderLayout());

        if (DTBConnector.currentUser != null) {
            String storyTitle = DTBConnector.getStories().stream()
                .filter(s -> s.id.equals(chapter.storyId))
                .map(s -> s.title)
                .findFirst()
                .orElse("Truyện");
            DTBConnector.saveReadingHistory(DTBConnector.currentUser, storyTitle, chapter.chapterName);
        }

        // Floating Rounded Header Bar
        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setOpaque(false);
        topWrapper.setBorder(new EmptyBorder(8, 12, 0, 12));

        JPanel topPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(26, 26, 34));
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_PANEL, UIConfig.ARC_PANEL);
                g2.setColor(new Color(45, 45, 58));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, UIConfig.ARC_PANEL, UIConfig.ARC_PANEL);
            }
        };
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(8, 12, 8, 12));

        JButton btnBack = new JButton("⬅ Thoát");
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
        btnReload.setBackground(new Color(42, 42, 54));
        btnReload.setForeground(Color.WHITE);
        btnReload.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReload.addActionListener(e -> {
            new ReadScreen(chapter).setVisible(true);
            this.dispose();
        });

        JLabel lblTitle = new JLabel(chapter.chapterName, SwingConstants.CENTER);
        lblTitle.setFont(UIConfig.FONT_SUBTITLE);
        lblTitle.setForeground(Color.WHITE);

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(btnReload, BorderLayout.EAST);
        topWrapper.add(topPanel, BorderLayout.CENTER);

        // Page scroll container
        JPanel pagePanel = new JPanel();
        pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
        pagePanel.setBackground(new Color(15, 15, 18));
        pagePanel.setBorder(new EmptyBorder(8, 0, 16, 0));

        File folder = new File("assets/pages/" + chapter.folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

            if (files != null && files.length > 0) {
                Arrays.sort(files, (f1, f2) -> {
                    String n1 = f1.getName().replaceAll("[^0-9]", "");
                    String n2 = f2.getName().replaceAll("[^0-9]", "");
                    try {
                        return Integer.compare(Integer.parseInt(n1), Integer.parseInt(n2));
                    } catch (NumberFormatException e) {
                        return f1.getName().compareTo(f2.getName());
                    }
                });

                for (File file : files) {
                    ImageIcon originIcon = new ImageIcon(file.getAbsolutePath());
                    int imgWidth = 380;
                    int imgHeight = (originIcon.getIconHeight() * imgWidth) / originIcon.getIconWidth();
                    JLabel page = new JLabel(new ImageIcon(
                        originIcon.getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH)));
                    page.setAlignmentX(Component.CENTER_ALIGNMENT);
                    pagePanel.add(page);
                }
            } else {
                JLabel emptyPage = new JLabel("Không tìm thấy trang truyện nào trong thư mục.");
                emptyPage.setFont(UIConfig.FONT_SMALL);
                emptyPage.setForeground(Color.LIGHT_GRAY);
                emptyPage.setAlignmentX(Component.CENTER_ALIGNMENT);
                emptyPage.setBorder(new EmptyBorder(50, 0, 0, 0));
                pagePanel.add(emptyPage);
            }
        } else {
            JLabel emptyFolder = new JLabel("Chưa có dữ liệu hình ảnh cho chương này.");
            emptyFolder.setFont(UIConfig.FONT_SMALL);
            emptyFolder.setForeground(Color.LIGHT_GRAY);
            emptyFolder.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyFolder.setBorder(new EmptyBorder(50, 0, 0, 0));
            pagePanel.add(emptyFolder);
        }

        JScrollPane scroll = new JScrollPane(pagePanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(24);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(topWrapper, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }
}
