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
    /**
     * Constructor
     * <p>
     *     This constructor initializes the DSLContext for interacting with the database
     *     using the SQLite dialect. It attempts to establish a connection to the database
     *     and sets up the DSLContext for executing SQL queries.
     *     If a SQLException occurs while attempting to establish the connection, the stack trace
     *     of the exception is printed.
     *     </p>
     * */
    public CourseDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Adds a course to the database.
     * @param course course to be added
     * @throws Exception when course cannot be added
     */
    public void addCourse(Course course) throws Exception {
        try{
            create.insertInto(COURSES, COURSES.COURSE_CODE, COURSES.NAME, COURSES.DEPARTMENT)
                    .values(course.getCourseCode() ,course.getName(), course.getDepartment())
                    .execute();
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * Updates a course in the database.
     * @param code course code
     * @param course course content to be updated
     */
    public void updateCourse(String code,Course course) {
        create.update(COURSES)
                .set(COURSES.NAME, course.getName())
                .set(COURSES.DEPARTMENT, course.getDepartment())
                .set(COURSES.COURSE_CODE, course.getCourseCode())
                .where(COURSES.COURSE_CODE.eq(code))
                .execute();
    }

    /**
     * Deletes a course from the database.
     * @param courseID course code
     */
    public void deleteCourse(String courseID) {
        create.deleteFrom(COURSES)
                .where(COURSES.COURSE_CODE.eq(courseID))
                .execute();
    }

    /**
     * Filters courses by department.
     * @param department department to be filtered
     * @return list of filtered courses
     */
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

    /**
     * Get all courses from the database.
     * @return list of all courses
     */
    public List<Course> getAllCourses() {
        return create.select(
                        COURSES.COURSE_CODE.as("course_code"),
                        COURSES.NAME.as("name"),
                        COURSES.DEPARTMENT.as("department")
                )
                .from(COURSES)
                .fetchInto(Course.class);
    }

    /**
     * Get all course codes (unique) from the database.
     * @return list of all course codes
     */
    public List<String> getAllCoursesID() {
        return create.select(
                        COURSES.COURSE_CODE.as("course_code")
                )
                .from(COURSES)
                .fetchInto(String.class);
    }
}
