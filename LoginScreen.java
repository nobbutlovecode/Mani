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

        // Load background and logo preserving existing assets pipeline
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
                logoImage = new BufferedImage(130, 130, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = logoImage.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING,     RenderingHints.VALUE_RENDER_QUALITY);
                g2d.drawImage(original, 0, 0, 130, 130, null);
                g2d.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        final Image         finalBg   = bgImage;
        final BufferedImage finalLogo = logoImage;
        final boolean       hasLogo   = (finalLogo != null);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Preserve background image
                if (finalBg != null) {
                    g2.drawImage(finalBg, 0, 0, getWidth(), getHeight(), null);
                } else {
                    // Refined fallback gradient
                    GradientPaint gp = new GradientPaint(0, 0, new Color(26, 26, 36), 0, getHeight(), new Color(15, 23, 42));
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                // Draw logo if exists
                if (hasLogo) {
                    int x = (getWidth() - 130) / 2;
                    int y = 70;
                    g2.drawImage(finalLogo, x, y, null);
                }
            }
        };

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        if (hasLogo) {
            mainPanel.add(Box.createVerticalStrut(70 + 130 + 15));
        } else {
            mainPanel.add(Box.createVerticalStrut(80));

            // Stylized brand badge
            JLabel badgeLabel = new JLabel("MANVI", SwingConstants.CENTER);
            badgeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
            badgeLabel.setForeground(Color.WHITE);
            badgeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(badgeLabel);

            JLabel subTitle = new JLabel("Hệ thống truyện tranh bản quyền đỉnh cao", SwingConstants.CENTER);
            subTitle.setFont(UIConfig.FONT_SMALL);
            subTitle.setForeground(new Color(226, 232, 240));
            subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(subTitle);
            mainPanel.add(Box.createVerticalStrut(25));
        }

        // Modern Card Container
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Glass-effect card with subtle border
                g2.setColor(new Color(255, 255, 255, 242));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(255, 255, 255, 120));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
            }
        };
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(new EmptyBorder(24, 20, 24, 20));
        cardPanel.setMaximumSize(new Dimension(340, 310));
        cardPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblLoginTitle = new JLabel("ĐĂNG NHẬP");
        lblLoginTitle.setFont(UIConfig.FONT_HEADER);
        lblLoginTitle.setForeground(UIConfig.PRIMARY);
        lblLoginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(lblLoginTitle);
        cardPanel.add(Box.createVerticalStrut(18));

        // Modern input fields
        JTextField txtUser = new JTextField();
        txtUser.setMaximumSize(new Dimension(300, 42));
        txtUser.setFont(UIConfig.FONT_BODY);
        txtUser.putClientProperty("JTextField.placeholderText", "Tên đăng nhập hoặc Email");
        txtUser.putClientProperty("JComponent.roundRect", true);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setMaximumSize(new Dimension(300, 42));
        txtPass.setFont(UIConfig.FONT_BODY);
        txtPass.putClientProperty("JTextField.placeholderText", "Mật khẩu");
        txtPass.putClientProperty("JComponent.roundRect", true);

        cardPanel.add(txtUser);
        cardPanel.add(Box.createVerticalStrut(12));
        cardPanel.add(txtPass);
        cardPanel.add(Box.createVerticalStrut(18));

        // Primary Red Action Button
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setMaximumSize(new Dimension(300, 44));
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setFont(UIConfig.FONT_SUBTITLE);
        btnLogin.setBackground(UIConfig.PRIMARY);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());
            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (DTBConnector.checkLogin(user, pass)) {
                DTBConnector.currentUser = user;
                JOptionPane.showMessageDialog(this, "Đăng nhập thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                new Dashboard().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardPanel.add(btnLogin);
        cardPanel.add(Box.createVerticalStrut(14));

        JLabel lblRegister = new JLabel("<html><u>Chưa có tài khoản? Đăng ký ngay</u></html>", SwingConstants.CENTER);
        lblRegister.setFont(UIConfig.FONT_SMALL);
        lblRegister.setForeground(UIConfig.PRIMARY);
        lblRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRegister.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new RegisterScreen().setVisible(true);
                LoginScreen.this.dispose();
            }
        });

        cardPanel.add(lblRegister);
        mainPanel.add(cardPanel);
        setContentPane(mainPanel);
    }
}
