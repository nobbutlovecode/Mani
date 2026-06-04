package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class LoginScreen extends JFrame {
    public LoginScreen() {
        setTitle("Manvi - Đăng nhập");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load ảnh nền và logo TRƯỚC — lưu vào biến final để dùng trong paintComponent
        Image bgImage = null;
        File bgFile = new File("assets/background_dacs.png");
        if (bgFile.exists()) {
            bgImage = new ImageIcon(bgFile.getAbsolutePath()).getImage();
        }

        BufferedImage logoImage = null;
        File logoFile = new File("assets/logo.png");
        if (!logoFile.exists()) logoFile = new File("assets/logo.jpg");
        if (logoFile.exists()) {
            try {
                BufferedImage original = ImageIO.read(logoFile);
                logoImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = logoImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING,     RenderingHints.VALUE_RENDER_QUALITY);
                g2d.drawImage(original, 0, 0, 150, 150, null);
                g2d.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        final Image         finalBg   = bgImage;
        final BufferedImage finalLogo = logoImage;
        final boolean       hasLogo   = (finalLogo != null);

        // Vẽ cả background VÀ logo thẳng trong paintComponent
        // Vùng trong suốt của logo sẽ lộ đúng lớp background bên dưới — không còn caro
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                // Vẽ ảnh nền toàn màn hình
                if (finalBg != null) {
                    g2.drawImage(finalBg, 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(UIConfig.BG);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                // Vẽ logo PNG trực tiếp lên nền — KHÔNG dùng JLabel
                if (hasLogo) {
                    int x = (getWidth() - 150) / 2;
                    int y = 80;
                    g2.drawImage(finalLogo, x, y, null);
                }
            }
        };

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        if (hasLogo) {
            // Chừa khoảng trống đúng bằng vùng logo đã vẽ (y=80, h=150, padding=20)
            mainPanel.add(Box.createVerticalStrut(80 + 150 + 20));
        } else {
            mainPanel.add(Box.createVerticalStrut(80));
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
            mainPanel.add(Box.createVerticalStrut(20));
        }

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
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                return;
            }
            if (DTBConnector.checkLogin(user, pass)) {
                DTBConnector.currentUser = user;
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
