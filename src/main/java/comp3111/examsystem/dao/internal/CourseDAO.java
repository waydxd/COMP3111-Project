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
        try{
            create.insertInto(COURSES, COURSES.COURSE_CODE, COURSES.NAME, COURSES.DEPARTMENT)
                    .values(course.getCourseCode() ,course.getName(), course.getDepartment())
                    .execute();
        } catch (Exception e) {
            throw e;
        }
    }

    public void updateCourse(String code,Course course) {
        create.update(COURSES)
                .set(COURSES.NAME, course.getName())
                .set(COURSES.DEPARTMENT, course.getDepartment())
                .set(COURSES.COURSE_CODE, course.getCourseCode())
                .where(COURSES.COURSE_CODE.eq(code))
                .execute();
    }

    public void deleteCourse(String courseID) {
        create.deleteFrom(COURSES)
                .where(COURSES.COURSE_CODE.eq(courseID))
                .execute();
    }

    public List<Course> filterCoursesByDepartment(String department) {

        return create.select(
                        COURSES.COURSE_CODE.as("courseId"),
                        COURSES.NAME.as("courseName"),
                        COURSES.DEPARTMENT.as("department")
                )
                .from(COURSES)
                .where(COURSES.DEPARTMENT.eq(department))
                .fetchInto(Course.class);

    }

    public List<Course> getAllCourses() {
        return create.select(
                        COURSES.COURSE_CODE.as("course_code"),
                        COURSES.NAME.as("name"),
                        COURSES.DEPARTMENT.as("department")
                )
                .from(COURSES)
                .fetchInto(Course.class);
    }

    // Additional helper methods if needed
    public Course findByCode(String courseCode) {
        return null;
    }

    public List<String> getAllCoursesID() {
        return create.select(
                        COURSES.COURSE_CODE.as("course_code")
                )
                .from(COURSES)
                .fetchInto(String.class);
    }
}
