# Technical Architecture & Redesign Plan: Manvi (`specs/ui-ux-redesign/plan.md`)

## 1. Technical Framework & Constraints
- **GUI Framework**: Java Swing with FlatLaf (`com.formdev.flatlaf.FlatLightLaf`)
- **Dimensions**: Fixed 400×750 (Mobile form factor)
- **Zero-Touch Layers**: `DTBConnector.java`, database models, and SQL queries remain strictly unchanged.
- **Protected Assets**: `assets/background_dacs.png` is preserved in `LoginScreen`.

---

## 2. Component Design & Tokens Architecture

### `UIConfig.java` Expansion
Extend `UIConfig.java` into a centralized Design Token repository:
```java
public class UIConfig {
    // Primary Red Palette (To be confirmed with user)
    // Option 1: Crimson Wine (Default original #6D141A / #8B1824)
    public static Color PRIMARY          = new Color(139, 24, 36);
    public static Color PRIMARY_HOVER    = new Color(110, 18, 27);
    public static Color PRIMARY_LIGHT    = new Color(253, 242, 244);
    public static Color PRIMARY_BORDER   = new Color(247, 208, 213);

    // Neutrals & Surfaces
    public static Color BG               = new Color(248, 249, 250);
    public static Color CARD_BG          = Color.WHITE;
    public static Color CARD_BORDER      = new Color(226, 232, 240);
    public static Color TEXT_MAIN        = new Color(30, 41, 59);
    public static Color TEXT_MUTED       = new Color(100, 116, 139);
    public static Color SHADOW           = new Color(0, 0, 0, 15);

    // Accents
    public static Color ACCENT_GOLD      = new Color(217, 119, 6);
    public static Color SUCCESS_GREEN    = new Color(22, 101, 52);
    public static Color DANGER_RED       = new Color(220, 38, 38);

    // Typography
    public static Font FONT_TITLE        = new Font("Segoe UI", Font.BOLD, 22);
    public static Font FONT_SUBTITLE     = new Font("Segoe UI", Font.BOLD, 16);
    public static Font FONT_BODY         = new Font("Segoe UI", Font.PLAIN, 14);
    public static Font FONT_SMALL        = new Font("Segoe UI", Font.PLAIN, 12);
    public static Font FONT_BADGE        = new Font("Segoe UI", Font.BOLD, 10);

    // Arc & Layout Tokens
    public static int ARC_BUTTON         = 14;
    public static int ARC_CARD           = 16;
    public static int ARC_INPUT          = 12;
}
```

---

## 3. Screen-by-Screen Execution Plan

1. **`UIConfig.java` & `main.java`**:
   - Register global FlatLaf rounded arcs and font defaults.
2. **`LoginScreen.java`**:
   - Modernize form panel with glassmorphism or elevated card over `assets/background_dacs.png`.
   - Sleek text fields with placeholder text and focus rings.
   - Refined Red CTA button.
3. **`RegisterScreen.java`**:
   - Align color tokens from blue (`#2980b9`) to unified Red brand palette.
4. **`SurveyScreen.java`**:
   - Step indicator, styled combo boxes, clean spacing and Red CTA.
5. **`Dashboard.java`**:
   - Header with custom icon buttons instead of raw text emojis.
   - Category filter pills.
   - Polished manga feed with `MangaCard`.
6. **`MangaCard.java`**:
   - Rounded border, subtle shadow, tag pills, cover placeholder fallback.
7. **`StoryDetail.java`**:
   - Hero cover banner, synopsis card, styled chapter item cards.
8. **`ReadScreen.java`**:
   - Elegant dark reader interface with subtle top navigation bar.
9. **`ProfileScreen.java`**:
   - Clean user avatar header, coin wallet card, modern recharge packages.
