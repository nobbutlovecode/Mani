# Quality Checklist: Manvi UI/UX Overhaul (`specs/ui-ux-redesign/checklist.md`)

- [x] **Governance & Integrity**
  - [x] `DTBConnector.java` has 0 changes to SQL, connection URL, or database logic.
  - [x] `Story.java` and `Chapter.java` models remain unmodified.
  - [x] `assets/background_dacs.png` is retained in `LoginScreen.java`.
- [x] **Brand & Color Standards**
  - [x] Red remains the dominant brand color across all screens.
  - [x] User was consulted regarding the red palette selection (Confirmed: Crimson Wine #8B1824).
  - [x] RegisterScreen blue button (#2980b9) eliminated and unified to Red.
- [x] **Hallmark Floor & Polish**
  - [x] All colors and fonts route through `UIConfig.java`.
  - [x] No raw emoji buttons without structured button styling.
  - [x] Contrast ratio >= 4.5:1 for body and >= 3:1 for large display elements.
  - [x] Clickable elements provide visual feedback (cursor, hover effect).
- [x] **Live Preview Verification**
  - [x] Standalone interactive HTML simulator available in artifact directory.
  - [x] 0 external blocking CDN dependencies (CSP-safe).
