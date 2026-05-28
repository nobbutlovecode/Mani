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
        // --- TÍNH NĂNG MỚI: GHI NHẬN LỊCH SỬ ĐỌC ---
        if (DTBConnector.currentUser != null) {
            // Truy vấn lấy tên truyện dựa vào storyId để lưu cho đẹp
            String storyTitle = "Truyện"; 
            for(Story s : DTBConnector.getStories()) {
                if(s.id.equals(chapter.storyId)) {
                    storyTitle = s.title;
                    break;
                }
            }
            DTBConnector.saveReadingHistory(DTBConnector.currentUser, storyTitle, chapter.chapterName);
        }

        // 1. THANH ĐIỀU HƯỚNG
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.DARK_GRAY);
        JButton btnBack = new JButton("⬅ Thoát");
        btnBack.setFocusPainted(false);
        btnBack.setBackground(Color.RED);
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(e -> {
            new Dashboard().setVisible(true);
            this.dispose();
        });
        
        JLabel lblTitle = new JLabel("  " + chapter.chapterName);
        lblTitle.setForeground(Color.WHITE);
        topPanel.add(btnBack);
        topPanel.add(lblTitle);

        // 2. KHU VỰC HIỂN THỊ ẢNH (CUỘN DỌC)
        JPanel pagePanel = new JPanel();
        pagePanel.setLayout(new BoxLayout(pagePanel, BoxLayout.Y_AXIS));
        pagePanel.setBackground(Color.BLACK);

        // --- ĐỌC ẢNH TỪ THƯ MỤC LOCAL ---
        String folderPath = "assets/pages/" + chapter.folderPath;
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            // Lấy tất cả file ảnh (.jpg, .png)
            File[] files = folder.listFiles((dir, name) -> 
                name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".png"));

            if (files != null && files.length > 0) {
                // THUẬT TOÁN QUAN TRỌNG: Sắp xếp file theo số (Tránh tình trạng 1.jpg, 10.jpg, 2.jpg)
                Arrays.sort(files, (f1, f2) -> {
                    String n1 = f1.getName().replaceAll("[^0-9]", ""); // Lấy ra phần số
                    String n2 = f2.getName().replaceAll("[^0-9]", "");
                    try {
                        return Integer.compare(Integer.parseInt(n1), Integer.parseInt(n2));
                    } catch (NumberFormatException e) {
                        return f1.getName().compareTo(f2.getName());
                    }
                });

                // Render từng ảnh lên màn hình
                for (File file : files) {
                    ImageIcon originIcon = new ImageIcon(file.getAbsolutePath());
                    
                    // Tính toán Scale ảnh sao cho vừa chiều rộng 380px, giữ nguyên tỷ lệ chiều cao
                    int imgWidth = 380;
                    int imgHeight = (originIcon.getIconHeight() * imgWidth) / originIcon.getIconWidth();
                    
                    Image scaledImg = originIcon.getImage().getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
                    
                    JLabel page = new JLabel(new ImageIcon(scaledImg));
                    page.setAlignmentX(Component.CENTER_ALIGNMENT);
                    pagePanel.add(page);
                }
            } else {
                pagePanel.add(createErrorLabel("Thư mục truyện rỗng!"));
            }
        } else {
            pagePanel.add(createErrorLabel("Không tìm thấy đường dẫn: " + folderPath));
        }

        JScrollPane scroll = new JScrollPane(pagePanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(30); // Tốc độ lướt truyện mượt mà
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        add(topPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // Hàm tạo Label thông báo lỗi nếu không có ảnh
    private JLabel createErrorLabel(String message) {
        JLabel lbl = new JLabel(message, SwingConstants.CENTER);
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        return lbl;
    }
}
