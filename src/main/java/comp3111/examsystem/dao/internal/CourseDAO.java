package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Course;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CourseDAO {
    private DSLContext create;

    public CourseDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addCourse(String courseCode, String courseName, String instructorName) {

    }

    public void updateCourse(String courseID, String courseName, String department) {

    }

    public void deleteCourse(String courseID) {

    }

    public List<Course> filterCoursesByDepartment(String department) {
        return null;
    }

    // Additional helper methods if needed
    public Course findByCode(String courseCode) {
        return null;
    }
}
