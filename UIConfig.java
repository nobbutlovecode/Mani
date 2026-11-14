package com.mycompany.dacs1.Config;

import java.awt.Color;
import java.awt.Font;

public class UIConfig {
    // Primary Red Identity (Option 1: Crimson Wine - Approved)
    public static final Color PRIMARY         = new Color(139, 24, 36);   // #8B1824
    public static final Color PRIMARY_HOVER   = new Color(110, 18, 27);   // #6E121B
    public static final Color PRIMARY_LIGHT   = new Color(253, 242, 244); // #FDF2F4
    public static final Color PRIMARY_BORDER  = new Color(247, 208, 213); // #F7D0D5

    // Neutral Surfaces & Texts
    public static final Color BG              = new Color(248, 249, 250); // Clean off-white
    public static final Color CARD_BG         = Color.WHITE;
    public static final Color CARD_BORDER     = new Color(226, 232, 240); // #E2E8F0
    public static final Color TEXT_MAIN       = new Color(30, 41, 59);    // #1E293B
    public static final Color TEXT_MUTED      = new Color(100, 116, 139); // #64748B
    public static final Color SHADOW          = new Color(220, 224, 230);

    // Accents
    public static final Color ACCENT_GOLD     = new Color(217, 119, 6);   // #D97706
    public static final Color SUCCESS_GREEN   = new Color(22, 101, 52);   // #166534
    public static final Color DANGER_RED      = new Color(220, 38, 38);   // #DC2626

    // Typography Tokens
    public static final Font FONT_TITLE       = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font FONT_HEADER      = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FONT_SUBTITLE    = new Font("Segoe UI", Font.BOLD, 15);
    public static final Font FONT_BODY        = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL       = new Font("Segoe UI", Font.PLAIN, 12);
    public static final Font FONT_BADGE       = new Font("Segoe UI", Font.BOLD, 11);

    // Harmonized Rounded Arcs (Phương án 1: Bo tròn mềm mại đồng bộ)
    public static final int ARC_PANEL         = 20;
    public static final int ARC_CARD          = 18;
    public static final int ARC_BUTTON        = 18;
    public static final int ARC_INPUT         = 16;
    public static final int ARC_PILL          = 999;
}
