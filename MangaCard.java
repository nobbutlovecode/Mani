package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class MangaCard extends JPanel {
    private Story story;

    public MangaCard(Story story) {
        this.story = story;
        setPreferredSize(new Dimension(340, 120));
        setMaximumSize(new Dimension(340, 120));
        setBackground(UIConfig.CARD_BG);
        setLayout(new BorderLayout(15, 0));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel imgLabel = new JLabel("", SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(75, 100));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(UIConfig.SHADOW);

        File coverFile = new File("assets/covers/" + story.imageName);
        if (coverFile.exists()) {
            Image img = new ImageIcon("assets/covers/" + story.imageName)
                .getImage().getScaledInstance(75, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("NO IMG");
            imgLabel.setForeground(Color.GRAY);
        }

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("<html><b>" + story.title + "</b></html>");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(UIConfig.PRIMARY);

        JLabel authorLabel = new JLabel("Tác giả: " + story.author);
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        authorLabel.setForeground(UIConfig.TEXT_MAIN);

        JLabel categoryLabel = new JLabel("Thể loại: " + story.category);
        categoryLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        categoryLabel.setForeground(Color.GRAY);

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(categoryLabel);

        add(imgLabel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new StoryDetail(story).setVisible(true);
                Window window = SwingUtilities.getWindowAncestor(MangaCard.this);
                if (window != null) window.dispose();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }
}
