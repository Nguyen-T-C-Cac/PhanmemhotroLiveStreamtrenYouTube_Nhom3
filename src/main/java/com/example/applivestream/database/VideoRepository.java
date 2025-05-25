package com.example.applivestream.database;

import com.example.applivestream.model.Video;
import java.sql.*;

/**
 * VideoRepository chịu trách nhiệm lưu / xóa dữ liệu video,
 * sử dụng cấu hình DB giống UserRepository.
 */
public class VideoRepository {
    private static final String URL = "jdbc:mysql://sql8.freesqldatabase.com:3306/sql8780238";
    private static final String USER = "sql8780238";
    private static final String PASSWORD = "Pf7bLADBVu";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
//7.1.8	VideoRepository lưu thông tin video xuống Database.

    public static boolean saveVideo(Video video) {
        String sql = "INSERT INTO videos(id, title, filepath, recorded_at) VALUES(?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, video.getId());
            ps.setString(2, video.getTitle());
            ps.setString(3, video.getFilePath());
            ps.setTimestamp(4, Timestamp.valueOf(video.getRecordedAt()));

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
//7.2.3	PostStreamController gửi yêu cầu xóa đến VideoRepository
//    7.2.4	VideoRepository xóa video ở trong database.
    public static boolean deleteVideo(String videoId) {
        String sql = "DELETE FROM videos WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, videoId);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
//    7.3.3	PoststreamController yêu cầu VideoRepository đưa video trong database lên.
//    7.3.4	VideoRepository tìm video trong database.
//    7.3.5	Database trả về video.
    public static boolean findVideo(String title) {
        String sql = "SELECT * FROM videos WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}