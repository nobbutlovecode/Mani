package com.mycompany.dacs1.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterScreen extends JFrame {
    public RegisterScreen() {
        setTitle("Manvi - Đăng ký tài khoản");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(UIConfig.BG);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(UIConfig.BG);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 24, 30, 24));

        // Header branding
        JLabel lblTitle = new JLabel("TẠO TÀI KHOẢN");
        lblTitle.setFont(UIConfig.FONT_TITLE);
        lblTitle.setForeground(UIConfig.PRIMARY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Gia nhập cộng đồng người đọc Manvi", SwingConstants.CENTER);
        lblSub.setFont(UIConfig.FONT_SMALL);
        lblSub.setForeground(UIConfig.TEXT_MUTED);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form Card Panel
        JPanel formCard = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UIConfig.CARD_BG);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(UIConfig.CARD_BORDER);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setOpaque(false);
        formCard.setBorder(new EmptyBorder(22, 18, 22, 18));
        formCard.setMaximumSize(new Dimension(350, 420));
        formCard.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtFullname = createTextField("Họ và tên");
        JTextField txtUsername = createTextField("Tên đăng nhập");
        JPasswordField txtPassword = createPasswordField("Mật khẩu");
        JPasswordField txtConfirmPass = createPasswordField("Xác nhận mật khẩu");

        JButton btnRegister = new JButton("ĐĂNG KÝ NGAY");
        btnRegister.setFont(UIConfig.FONT_SUBTITLE);
        btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnRegister.setBackground(UIConfig.PRIMARY);
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegister.addActionListener(e -> {
            String fullname = txtFullname.getText();
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            String confirmPass = new String(txtConfirmPass.getPassword());

            if (user.isEmpty() || pass.isEmpty() || fullname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!pass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DTBConnector.registerUser(user, pass, fullname)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy làm bài khảo sát nhỏ nhé.");
                this.dispose();
                new SurveyScreen(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại hoặc lỗi kết nối!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel lblLogin = new JLabel("<html><u>Đã có tài khoản? Đăng nhập ngay</u></html>");
        lblLogin.setFont(UIConfig.FONT_SMALL);
        lblLogin.setForeground(UIConfig.PRIMARY);
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginScreen().setVisible(true);
            }
        });

        formCard.add(createInputLabel("Họ và tên"));
        formCard.add(txtFullname);
        formCard.add(Box.createVerticalStrut(10));
        formCard.add(createInputLabel("Tên đăng nhập"));
        formCard.add(txtUsername);
        formCard.add(Box.createVerticalStrut(10));
        formCard.add(createInputLabel("Mật khẩu"));
        formCard.add(txtPassword);
        formCard.add(Box.createVerticalStrut(10));
        formCard.add(createInputLabel("Xác nhận mật khẩu"));
        formCard.add(txtConfirmPass);
        formCard.add(Box.createVerticalStrut(20));
        formCard.add(btnRegister);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(lblSub);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formCard);
        mainPanel.add(Box.createVerticalStrut(16));
        mainPanel.add(lblLogin);

        add(mainPanel);
    }

    private JLabel createInputLabel(String labelText) {
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(UIConfig.FONT_BADGE);
        lbl.setForeground(UIConfig.TEXT_MAIN);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setBorder(new EmptyBorder(0, 0, 4, 0));
        return lbl;
    }

    private JTextField createTextField(String placeholder) {
        JTextField txt = new JTextField();
        txt.putClientProperty("JTextField.placeholderText", placeholder);
        txt.putClientProperty("JComponent.roundRect", true);
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txt.setFont(UIConfig.FONT_BODY);
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);
        return txt;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField txt = new JPasswordField();
        txt.putClientProperty("JTextField.placeholderText", placeholder);
        txt.putClientProperty("JComponent.roundRect", true);
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txt.setFont(UIConfig.FONT_BODY);
        txt.setAlignmentX(Component.LEFT_ALIGNMENT);
        return txt;
    }
}
