package com.mycompany.dacs1.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.border.EmptyBorder;

public class MangaCard extends JPanel {
    private Story story;
    private boolean isHovered = false;

    public MangaCard(Story story) {
        this.story = story;
        setPreferredSize(new Dimension(352, 122));
        setMaximumSize(new Dimension(352, 122));
        setBackground(UIConfig.CARD_BG);
        setLayout(new BorderLayout(14, 0));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setOpaque(false);

        // Thumbnail Cover Label
        JLabel imgLabel = new JLabel("", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paintComponent(g);
            }
        };
        imgLabel.setPreferredSize(new Dimension(75, 100));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(new Color(241, 245, 249));

        File coverFile = new File("assets/covers/" + story.imageName);
        if (coverFile.exists()) {
            Image img = new ImageIcon(coverFile.getAbsolutePath())
                .getImage().getScaledInstance(75, 100, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            imgLabel.setText("<html><div style='text-align:center;'><b>MANGA</b><br><small>COVER</small></div></html>");
            imgLabel.setFont(UIConfig.FONT_BADGE);
            imgLabel.setForeground(UIConfig.TEXT_MUTED);
        }

        // Information Column
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("<html><b>" + story.title + "</b></html>");
        titleLabel.setFont(UIConfig.FONT_SUBTITLE);
        titleLabel.setForeground(UIConfig.TEXT_MAIN);

        JLabel authorLabel = new JLabel("Tác giả: " + story.author);
        authorLabel.setFont(UIConfig.FONT_SMALL);
        authorLabel.setForeground(UIConfig.TEXT_MUTED);

        JPanel tagPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tagPanel.setOpaque(false);
        JLabel categoryTag = new JLabel(" " + story.category + " ");
        categoryTag.setFont(UIConfig.FONT_BADGE);
        categoryTag.setOpaque(true);
        categoryTag.setBackground(UIConfig.PRIMARY_LIGHT);
        categoryTag.setForeground(UIConfig.PRIMARY);
        categoryTag.setBorder(BorderFactory.createLineBorder(UIConfig.PRIMARY_BORDER, 1));
        tagPanel.add(categoryTag);

        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(titleLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(6));
        infoPanel.add(tagPanel);
        infoPanel.add(Box.createVerticalGlue());

        add(imgLabel, BorderLayout.WEST);
        add(infoPanel, BorderLayout.CENTER);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }

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

        // Card background
        g2.setColor(UIConfig.CARD_BG);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        // Hover or normal border
        if (isHovered) {
            g2.setColor(UIConfig.PRIMARY);
            g2.setStroke(new BasicStroke(1.5f));
        } else {
            g2.setColor(UIConfig.CARD_BORDER);
            g2.setStroke(new BasicStroke(1.0f));
        }
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        super.paintComponent(g);
    }
}
