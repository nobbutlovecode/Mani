package com.mycompany.dacs1.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DTBConnector {
    private static final String URL  = "jdbc:sqlserver://localhost:1433;databaseName=Manvi_local;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASS = "123456";

    public static String currentUser = null;

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static boolean registerUser(String username, String password, String fullname) {
        try (Connection conn = getConnection()) {
            PreparedStatement check = conn.prepareStatement("SELECT username FROM Users WHERE username = ?");
            check.setString(1, username);
            if (check.executeQuery().next()) return false;

            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO Users (username, password, fullname) VALUES (?, ?, ?)");
            insert.setString(1, username);
            insert.setString(2, password);
            insert.setString(3, fullname);
            return insert.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveUserSurvey(String username, String favCategory, String readTime,
                                          String storyLength, String readingStyle, String goal) {
        String sql = "INSERT INTO UserSurvey (username, fav_category, read_time, story_length, reading_style, goal) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, favCategory);
            pstmt.setString(3, readTime);
            pstmt.setString(4, storyLength);
            pstmt.setString(5, readingStyle);
            pstmt.setString(6, goal);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Story> getStories() {
        List<Story> list = new ArrayList<>();
        try (Connection conn = getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Stories")) {
            while (rs.next()) {
                list.add(new Story(
                    rs.getString("id"), rs.getString("title"),
                    rs.getString("author"), rs.getString("category"),
                    rs.getString("image_name"), rs.getString("description")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Chapter> getChaptersByStoryId(String storyId) {
        List<Chapter> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM Chapters WHERE story_id = ? ORDER BY chapter_id ASC");
            pstmt.setString(1, storyId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new Chapter(
                    rs.getString("chapter_id"), rs.getString("story_id"),
                    rs.getString("chapter_name"), rs.getString("folder_path")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean checkLogin(String username, String password) {
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM Users WHERE LTRIM(RTRIM(username)) = ? AND LTRIM(RTRIM(password)) = ?");
            pstmt.setString(1, username.trim());
            pstmt.setString(2, password.trim());
            if (pstmt.executeQuery().next()) return true;

            StringBuilder debug = new StringBuilder("Không tìm thấy tài khoản trùng khớp!\n\nDanh sách Users trong CSDL:\n");
            ResultSet rsAll = conn.createStatement().executeQuery("SELECT username, password FROM Users");
            int count = 0;
            while (rsAll.next()) {
                debug.append("- [").append(rsAll.getString("username")).append("] / [")
                     .append(rsAll.getString("password")).append("]\n");
                count++;
            }
            if (count == 0) debug.append("(Bảng Users đang rỗng!)");

            javax.swing.JOptionPane.showMessageDialog(null, debug.toString(),
                "Chẩn đoán đăng nhập", javax.swing.JOptionPane.WARNING_MESSAGE);
        } catch (SQLException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Lỗi kết nối CSDL:\n" + e.getMessage(),
                "Lỗi Hệ Thống", javax.swing.JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }

    public static void saveReadingHistory(String username, String storyName, String chapterName) {
        try (Connection conn = getConnection()) {
            PreparedStatement check = conn.prepareStatement(
                "SELECT id FROM ReadingHistory WHERE username=? AND story_name=? AND chapter_name=?");
            check.setString(1, username);
            check.setString(2, storyName);
            check.setString(3, chapterName);
            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                PreparedStatement update = conn.prepareStatement(
                    "UPDATE ReadingHistory SET read_at = GETDATE() WHERE id=?");
                update.setInt(1, rs.getInt("id"));
                update.executeUpdate();
            } else {
                PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO ReadingHistory (username, story_name, chapter_name) VALUES (?, ?, ?)");
                insert.setString(1, username);
                insert.setString(2, storyName);
                insert.setString(3, chapterName);
                insert.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String[]> getReadingHistory(String username) {
        List<String[]> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT story_name, chapter_name, FORMAT(read_at, 'dd/MM/yyyy HH:mm') AS time_read " +
                "FROM ReadingHistory WHERE username=? ORDER BY read_at DESC");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("story_name"),
                    rs.getString("chapter_name"),
                    rs.getString("time_read")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int getUserCoins(String username) {
        try (Connection conn = getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                "SELECT coins FROM Users WHERE username = ?");
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("coins");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean addCoins(String username, int amount) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "UPDATE Users SET coins = coins + ? WHERE username = ?")) {
            pstmt.setInt(1, amount);
            pstmt.setString(2, username);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isChapterUnlocked(String username, String chapterId) {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                "SELECT id FROM UnlockedChapters WHERE username = ? AND chapter_id = ?")) {
            pstmt.setString(1, username);
            pstmt.setString(2, chapterId);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean unlockChapter(String username, String chapterId, int cost) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            PreparedStatement check = conn.prepareStatement(
                "SELECT ISNULL(coins, 0) AS coins FROM Users WHERE username = ?");
            check.setString(1, username);
            ResultSet rs = check.executeQuery();
            if (!rs.next() || rs.getInt("coins") < cost) {
                conn.rollback();
                return false;
            }

            PreparedStatement deduct = conn.prepareStatement(
                "UPDATE Users SET coins = ISNULL(coins, 0) - ? WHERE username = ?");
            deduct.setInt(1, cost);
            deduct.setString(2, username);
            deduct.executeUpdate();

            PreparedStatement insert = conn.prepareStatement(
                "INSERT INTO UnlockedChapters (username, chapter_id) VALUES (?, ?)");
            insert.setString(1, username);
            insert.setString(2, chapterId);
            insert.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static List<String[]> getCoinUsageHistory(String username) {
        List<String[]> list = new ArrayList<>();
        String sql = "SELECT s.title, c.chapter_name, FORMAT(uc.unlocked_at, 'dd/MM/yyyy HH:mm') AS time_unlocked " +
                     "FROM UnlockedChapters uc " +
                     "JOIN Chapters c ON uc.chapter_id = c.chapter_id " +
                     "JOIN Stories s ON c.story_id = s.id " +
                     "WHERE uc.username = ? " +
                     "ORDER BY uc.unlocked_at DESC";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                list.add(new String[]{
                    rs.getString("title"),
                    rs.getString("chapter_name"),
                    "2 Xu",
                    rs.getString("time_unlocked")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
