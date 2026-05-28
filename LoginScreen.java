package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Manvi - Đăng nhập");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Đã sửa lại đúng tên background_dacs.png của bạn
                File bgFile = new File("assets/background_dacs.png");
                if (bgFile.exists()) {
                    Image bg = new ImageIcon("assets/background_dacs.png").getImage();
                    g.drawImage(bg, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g.setColor(UIConfig.BG);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(Box.createVerticalStrut(80)); 

        // --- 2. CODE HIỂN THỊ LOGO ---
        // Đã đồng bộ đuôi .jpg cho cả 2 dòng
        File logoFile = new File("assets/logo.jpg");        
        if (logoFile.exists()) {
            try {
                // 1. Dùng ImageIO để đọc ảnh (giữ nguyên kênh Alpha trong suốt)
                BufferedImage originalImg = ImageIO.read(logoFile);

                // 2. Tạo một bức ảnh mới kích thước 150x150, ÉP kiểu ARGB (A = Alpha = Trong suốt)
                BufferedImage scaledImg = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = scaledImg.createGraphics();
                
                // 3. Khử răng cưa và vẽ ảnh con rồng lên
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.drawImage(originalImg, 0, 0, 150, 150, null);
                g2d.dispose();

                // 4. Gắn vào Label và bắt buộc Label phải trong suốt (setOpaque = false)
                JLabel logoLabel = new JLabel(new ImageIcon(scaledImg));
                logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                logoLabel.setOpaque(false); // Lệnh sống còn để không bị nền xám/caro
                mainPanel.add(logoLabel);
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            // (Phần else hiện chữ giữ nguyên như cũ)
            JLabel titleLabel = new JLabel("MANVI", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
            titleLabel.setForeground(UIConfig.PRIMARY);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(titleLabel);
            
            JLabel subTitle = new JLabel("Hệ thống gợi ý truyện tranh", SwingConstants.CENTER);
            subTitle.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            subTitle.setForeground(Color.GRAY);
            subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(subTitle);
        }
        
        mainPanel.add(Box.createVerticalStrut(50));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false); 
        formPanel.setBorder(new EmptyBorder(0, 40, 0, 40));
        formPanel.setMaximumSize(new Dimension(400, 200));

        JTextField txtUser = new JTextField();
        txtUser.setMaximumSize(new Dimension(300, 40));
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUser.setBorder(BorderFactory.createTitledBorder("Tài khoản"));
        
        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(300, 40));
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPass.setBorder(BorderFactory.createTitledBorder("Mật khẩu"));

        formPanel.add(txtUser);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(txtPass);
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(30));

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setMaximumSize(new Dimension(200, 45));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(UIConfig.PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- CẬP NHẬT: Gán giá trị cho currentUser khi login thành công ---
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                return;
            }

            if (DTBConnector.checkLogin(user, pass)) {
                DTBConnector.currentUser = user; // Lưu Session đăng nhập
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
                new Dashboard().setVisible(true);
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(btnLogin);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel registerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        registerPanel.setOpaque(false);
        JLabel lblRegister = new JLabel("<html><u>Chưa có tài khoản? Đăng ký ngay</u></html>");
        lblRegister.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblRegister.setForeground(UIConfig.TEXT_MAIN);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // --- CẬP NHẬT: Gọi trang Đăng ký khi Click ---
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterScreen().setVisible(true);
                LoginScreen.this.dispose();
            }
        });
        
        registerPanel.add(lblRegister);
        mainPanel.add(registerPanel);
        setContentPane(mainPanel);
    }
}
