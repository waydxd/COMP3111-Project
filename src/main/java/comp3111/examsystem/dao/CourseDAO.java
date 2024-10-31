package comp3111.examsystem.dao;

import comp3111.examsystem.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void addCourse(String courseCode, String courseName, String instructorName) {
        String sql = "INSERT INTO courses (code, name, instructor) VALUES (?, ?, ?)";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseCode);
            pstmt.setString(2, courseName);
            pstmt.setString(3, instructorName);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error adding course", e);
        }
    }

    public void updateCourse(String courseID, String courseName, String department) {
        String sql = "UPDATE courses SET name = ?, department = ? WHERE code = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseName);
            pstmt.setString(2, department);
            pstmt.setString(3, courseID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating course", e);
        }
    }

    public void deleteCourse(String courseID) {
        String sql = "DELETE FROM courses WHERE code = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseID);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting course", e);
        }
    }

    public List<Course> filterCoursesByDepartment(String department) {
        String sql = "SELECT * FROM courses WHERE department = ?";
        List<Course> courses = new ArrayList<>();

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, department);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDepartment(rs.getString("department"));
                courses.add(course);
            }

            return courses;

        } catch (SQLException e) {
            throw new RuntimeException("Error filtering courses", e);
        }
    }

    // Additional helper methods if needed
    public Course findByCode(String courseCode) {
        String sql = "SELECT * FROM courses WHERE code = ?";

        try (Connection conn = SQLiteConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Course course = new Course();
                course.setId(rs.getInt("id"));
                course.setName(rs.getString("name"));
                course.setDepartment(rs.getString("department"));
                return course;
            }
            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error finding course", e);
        }
    }
}
