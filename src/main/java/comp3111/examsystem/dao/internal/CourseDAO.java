package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Course;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import static com.examsystem.jooq.generated.tables.Courses.COURSES;


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
    public void addCourse(Course course) {
        create.insertInto(com.examsystem.jooq.generated.tables.Courses.COURSES, com.examsystem.jooq.generated.tables.Courses.COURSES.NAME, COURSES.DEPARTMENT)
                .values(course.getName(), course.getDepartment())
                .execute();
    }

    public void updateCourse(int courseID, String courseName, String department) {
        create.update(COURSES)
                .set(COURSES.NAME, courseName)
                .set(COURSES.DEPARTMENT, department)
                .where(COURSES.ID.eq(courseID))
                .execute();
    }

    public void deleteCourse(int courseID) {
        create.deleteFrom(COURSES)
                .where(COURSES.ID.eq(courseID))
                .execute();
    }

    public List<Course> filterCoursesByDepartment(String department) {

        return create.selectFrom(COURSES)
                .where(COURSES.DEPARTMENT.eq(department))
                .fetchInto(Course.class);

    }

    // Additional helper methods if needed
    public Course findByCode(String courseCode) {
        return null;
    }
}
