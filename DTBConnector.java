package com.mycompany.dacs1.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DTBConnector {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=Manvi_local;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa"; // User DB của bạn
    private static final String PASS = "123456"; // Pass DB của bạn

    // QUAN TRỌNG: Biến toàn cục để lưu trữ user đang đăng nhập (Session)
    public static String currentUser = null;

    // Hàm hỗ trợ kết nối DB dùng chung
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // 1. Hàm đăng ký người dùng mới
    public static boolean registerUser(String username, String password, String fullname) {
        String checkSql = "SELECT username FROM Users WHERE username = ?";
        String insertSql = "INSERT INTO Users (username, password, fullname) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) return false; // Username đã tồn tại
            
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); 
            insertStmt.setString(3, fullname);
            return insertStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Hàm lưu kết quả khảo sát Cold Start
    public static boolean saveUserSurvey(String username, String favCategory, String readTime, String storyLength, String readingStyle, String goal) {
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

    // 3. Hàm lấy danh sách truyện
    public static List<Story> getStories() {
        List<Story> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Stories");
            while (rs.next()) {
                Story s = new Story(
                    rs.getString("id"), rs.getString("title"),
                    rs.getString("author"), rs.getString("category"),
                    rs.getString("image_name"), rs.getString("description")
                );
                list.add(s);
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }

    // 4. Hàm lấy Chapters
    public static List<Chapter> getChaptersByStoryId(String storyId) {
        List<Chapter> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Chapters WHERE story_id = ? ORDER BY chapter_id ASC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
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

    // 5. Hàm kiểm tra đăng nhập
    public static boolean checkLogin(String username, String password) {
        boolean isValid = false;
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isValid = true;
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return isValid;
    }
    // 6. Hàm lưu lịch sử khi người dùng ấn vào đọc truyện
    public static void saveReadingHistory(String username, String storyName, String chapterName) {
        // Kiểm tra xem đã đọc chap này chưa, nếu rồi thì cập nhật thời gian, chưa thì insert mới
        String checkSql = "SELECT id FROM ReadingHistory WHERE username=? AND story_name=? AND chapter_name=?";
        String updateSql = "UPDATE ReadingHistory SET read_at = GETDATE() WHERE id=?";
        String insertSql = "INSERT INTO ReadingHistory (username, story_name, chapter_name) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection()) {
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, username);
            checkStmt.setString(2, storyName);
            checkStmt.setString(3, chapterName);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                // Đã tồn tại -> Cập nhật thời gian đọc mới nhất
                PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setInt(1, rs.getInt("id"));
                updateStmt.executeUpdate();
            } else {
                // Chưa tồn tại -> Thêm mới
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, username);
                insertStmt.setString(2, storyName);
                insertStmt.setString(3, chapterName);
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 7. Hàm lấy danh sách lịch sử đọc của User
    public static List<String[]> getReadingHistory(String username) {
        List<String[]> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            // Lấy ra lịch sử, sắp xếp mới nhất lên đầu
            String sql = "SELECT story_name, chapter_name, FORMAT(read_at, 'dd/MM/yyyy HH:mm') as time_read " +
                         "FROM ReadingHistory WHERE username=? ORDER BY read_at DESC";
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
}
