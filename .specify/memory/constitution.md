# Manvi Project Constitution (`.specify/memory/constitution.md`)

## 1. Core Principles & Governance
- **Role**: Master Architecture & Quality Contract for Manvi Manga Reader.
- **Mastermind**: `speckit-orchestrator` controlling the 9 SDD lifecycle stages.
- **Design Intelligence**: `ui-ux-pro-max` + `hallmark` design standards.
- **Preview System**: `live-preview-fixing` standalone CSP-compliant previews embedded in chat.

---

## 2. Non-Negotiable Invariants (Thiết Quân Luật)

### Invariant 1: UI/UX Scope Only (CHỈ CÓ UI/UX ĐƯỢC CHỈNH SỬA)
- **STRICT PROHIBITION**: Zero modification to backend logic, SQL queries, database schema, database tables, or connection configurations in `DTBConnector.java`.
- Data model classes (`Story.java`, `Chapter.java`) and their fields/signatures must remain 100% intact.
- Modifications are strictly limited to UI presentation layer: `UIConfig.java`, `LoginScreen.java`, `RegisterScreen.java`, `SurveyScreen.java`, `Dashboard.java`, `MangaCard.java`, `StoryDetail.java`, `ProfileScreen.java`, `ReadScreen.java`, and `main.java`.

### Invariant 2: Red Dominant Color Law (MÀU CHỦ ĐẠO LUÔN LÀ MÀU ĐỎ)
- The primary brand color MUST ALWAYS BE RED.
- Any variation, accent tweak, or tonal shift must be explicitly proposed to and confirmed by the user before code persistence.
- Secondary, surface, and semantic colors must complement the red anchor while maintaining WCAG 2.1 AA contrast (>= 4.5:1 for body text, >= 3:1 for large display elements).

### Invariant 3: Background Image Protection Law (ẢNH NỀN CẤM ĐỔI)
- The background image reference (`assets/background_dacs.png`) and its scaling/drawing pipeline in `LoginScreen` (and any other screens utilizing it) is strictly forbidden to be replaced, renamed, or modified.
- All overlay cards, glassmorphic panels, and input fields must be layered cleanly above the background image.

### Invariant 4: Hallmark & UI-UX Pro Max Craftsmanship
- **Design Token Discipline**: All colors, fonts, margins, and border radii must pass through `UIConfig.java` tokens.
- **Touch & Accessibility**: Minimum click target 44×44px, proper focus rings (`:focus-visible` / FlatLaf focus outline), visible labels, clear hierarchy.
- **Anti-patterns to avoid**: No raw emoji buttons (e.g. replacing `🔄` and `👤` with styled icon-label components), no card-inside-card clutter, no purple-blue gradients, no unaligned inputs.

### Invariant 5: Live Preview Synchronization (`live-preview-fixing`)
- Every UI modification must have a synchronized, self-contained HTML/CSS/Tailwind mobile simulator preview (400×750px mobile viewport) viewable directly in chat.
- Previews must be CSP-safe: 0 external CDN scripts, inline styles, self-contained SVG icons, and base64 assets where applicable.
