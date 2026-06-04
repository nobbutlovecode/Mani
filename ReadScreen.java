package com.mycompany.dacs1.Config;

import javax.swing.*;
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
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        if (DTBConnector.currentUser != null) {
            String storyTitle = DTBConnector.getStories().stream()
                .filter(s -> s.id.equals(chapter.storyId))
                .map(s -> s.title)
                .findFirst()
                .orElse("Truyện");
            DTBConnector.saveReadingHistory(DTBConnector.currentUser, storyTitle, chapter.chapterName);
        }

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.DARK_GRAY);

        JButton btnBack = new JButton("⬅ Thoát");
        btnBack.setFocusPainted(false);
        btnBack.setBackground(Color.RED);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });

        JButton btnReload = new JButton("🔄");
        btnReload.setFocusPainted(false);
        btnReload.setBackground(Color.DARK_GRAY);
        btnReload.setForeground(Color.WHITE);
        btnReload.addActionListener(e -> {
            new ReadScreen(chapter).setVisible(true);
            this.dispose();
        });

        JLabel lblTitle = new JLabel("  " + chapter.chapterName, SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);

        topPanel.add(btnBack, BorderLayout.WEST);
        topPanel.add(lblTitle, BorderLayout.CENTER);
        topPanel.add(btnReload, BorderLayout.EAST);

        JPanel pagePanel = new JPanel();
        pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
        pagePanel.setBackground(Color.BLACK);

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
                pagePanel.add(createErrorLabel("Thư mục truyện rỗng!"));
            }
        } else {
            pagePanel.add(createErrorLabel("Không tìm thấy đường dẫn: assets/pages/" + chapter.folderPath));
        }

        JScrollPane scroll = new JScrollPane(pagePanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(30);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    private JLabel createErrorLabel(String message) {
        JLabel lbl = new JLabel(message, SwingConstants.CENTER);
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        return lbl;
    }
}
