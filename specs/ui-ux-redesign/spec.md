# Feature Specification: Manvi UI/UX Overhaul & Modernization

## 1. Overview & Objective
Redesign the entire user experience and visual interface for **Manvi** (a Java Swing + FlatLaf mobile-dimension Manga Reader app) according to **Hallmark** design principles ("look designed, not generated") and **UI-UX Pro Max** guidelines.
Maintain 100% backward compatibility with `DTBConnector` and keep the background image `assets/background_dacs.png` intact while honoring the dominant **RED** color identity.

---

## 2. Target Screens & Redesign Scope

| Screen | File | Current Issues | Proposed UI/UX Enhancements |
|---|---|---|---|
| **Login** | `LoginScreen.java` | Titled borders look dated; simple text fields; abrupt layout | Modern floating label / card container over `assets/background_dacs.png`; refined crimson primary button with hover shadow; clean sign-up footer link with generous tap target. |
| **Register** | `RegisterScreen.java` | Uses default blue button `#2980b9` which clashes with brand red; plain inputs | Standardize onto brand Red design tokens; consistent card layout, input grouping, inline validation spacing. |
| **Survey (Cold Start)** | `SurveyScreen.java` | Standard JComboBoxes stacked vertically without visual categorization | Polished pill/option selectors or sleek FlatLaf rounded combos; clear progress indicator (1 to 5); cohesive Red primary CTA button. |
| **Home (Dashboard)** | `Dashboard.java` | Plain header with raw emojis `🔄`, `👤`; standard list spacing | Modern app header with stylized SVG/vector icons or elegant typography; category chips (Action, Romance, Fantasy); smoother manga card feed. |
| **Manga Card** | `MangaCard.java` | Basic box with gray fallback box; plain text | Refined cover container with subtle border/drop shadow; typography hierarchy (bold title, muted metadata badge, genre tag pill); smooth click feedback. |
| **Story Detail** | `StoryDetail.java` | Massive text block; plain chapter list | Polished hero section with cover, metadata badges, synopsis accordion/card, clean chapter item rows with read-state badges and coins indicator. |
| **Reader** | `ReadScreen.java` | Stark pure black with harsh raw red exit button; no chapter progress bar | Immersive reading mode with subtle floating top bar (auto-hide or elegant dark glass), chapter title, page count counter, and smooth navigation. |
| **Profile & Wallet** | `ProfileScreen.java` | Raw emoji `🪙`, table borders default | Clean user avatar badge, styled coin balance card with Red/Gold accent, modern recharge dialog cards with clear pricing tiers, reading history list. |
| **Design Tokens** | `UIConfig.java` | Basic static colors | Comprehensive design token system: Primary Red tones (main, dark, light, subtle tint), Neutral surfaces, Typography scales, Spacing constants, Border radii. |

---

## 3. Invariants & Rules
1. **Zero SQL/Data Changes**: Only UI code in Swing and FlatLaf properties are modified.
2. **Color Palette Invariant**: Red is the undisputed primary color. Nuance palette proposals will be presented to the user for selection.
3. **Background Image Invariant**: `assets/background_dacs.png` is preserved without any alteration.
4. **Live Preview Requirement**: Each screen has an interactive, standalone HTML live preview that renders cleanly inside the chat interface.

---

## 4. Acceptance Criteria
- [ ] User confirms color palette direction.
- [ ] All 8 Swing screens updated with modern, cohesive, polished UI following Hallmark & UI-UX Pro Max rules.
- [ ] Zero compile/runtime breakage; existing business logic preserved.
- [ ] Standalone interactive live preview artifact generated and verified via `live-preview-fixing`.
