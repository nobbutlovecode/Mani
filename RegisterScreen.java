package com.mycompany.dacs1.Config; // <--- Cực kỳ quan trọng, bắt buộc phải có

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterScreen extends JFrame {
    public RegisterScreen() {
        setTitle("Manvi - Đăng ký");
        setSize(400, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 30, 50, 30));

        JLabel lblTitle = new JLabel("TẠO TÀI KHOẢN");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("Khám phá thế giới truyện tranh Manvi");
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField txtFullname = createTextField("Họ và tên");
        JTextField txtUsername = createTextField("Tên đăng nhập");
        JPasswordField txtPassword = createPasswordField("Mật khẩu");
        JPasswordField txtConfirmPass = createPasswordField("Xác nhận mật khẩu");

        JButton btnRegister = new JButton("Đăng ký ngay");
        btnRegister.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnRegister.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btnRegister.setBackground(new Color(41, 128, 185));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegister.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnRegister.addActionListener(e -> {
            String fullname = txtFullname.getText();
            String user = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());
            String confirmPass = new String(txtConfirmPass.getPassword());

            if (user.isEmpty() || pass.isEmpty() || fullname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
                return;
            }
            if (!pass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
                return;
            }

            if (DTBConnector.registerUser(user, pass, fullname)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công! Hãy làm bài khảo sát nhỏ nhé.");
                this.dispose();
                new SurveyScreen(user).setVisible(true); 
            } else {
                JOptionPane.showMessageDialog(this, "Tên đăng nhập đã tồn tại hoặc lỗi kết nối!");
            }
        });

        JLabel lblLogin = new JLabel("<html><u>Đã có tài khoản? Đăng nhập ngay</u></html>");
        lblLogin.setForeground(new Color(41, 128, 185));
        lblLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginScreen().setVisible(true);
            }
        });

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblSub);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(txtFullname);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(txtUsername);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(txtPassword);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(txtConfirmPass);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(btnRegister);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblLogin);

        add(mainPanel);
    }

    private JTextField createTextField(String placeholder) {
        JTextField txt = new JTextField();
        txt.putClientProperty("JTextField.placeholderText", placeholder);
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return txt;
    }

    private JPasswordField createPasswordField(String placeholder) {
        JPasswordField txt = new JPasswordField();
        txt.putClientProperty("JTextField.placeholderText", placeholder);
        txt.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        txt.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return txt;
    }
}
