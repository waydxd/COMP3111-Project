package comp3111.examsystem.dao.internal;

import comp3111.examsystem.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {

    private CourseDAO courseDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        courseDAO = new CourseDAO(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS courses (
                                                       course_code TEXT PRIMARY KEY NOT NULL,
                                                       name TEXT NOT NULL,
                                                       department TEXT NOT NULL
                );""";
        connection.createStatement().execute(createTableSQL);
    }

    @Test
    void addCourse() throws Exception {
        Course course = new Course("COMP1111", "Introduction to Programming", "CS");

        courseDAO.addCourse(course);

        // Verify that the course was added (this would typically involve querying the database)
        List<Course> courses = courseDAO.getAllCourses();
        boolean courseExists = courses.stream()
                .anyMatch(c -> c.getCourseCode().equals(course.getCourseCode()) &&
                        c.getName().equals(course.getName()) &&
                        c.getDepartment().equals(course.getDepartment()));
        assertTrue(courseExists);
    }

    @Test
    void updateCourse() throws Exception {
        courseDAO.addCourse(new Course("COMP1111", "Introduction to Programming", "CS"));

        Course course = new Course("COMP1111", "Advanced Programming", "CS");

        courseDAO.updateCourse("COMP1111", course);

        // Verify that the course was updated (this would typically involve querying the database)
        Course updatedCourse = courseDAO.getAllCourses().stream()
                .filter(c -> c.getCourseCode().equals("COMP1111"))
                .findFirst()
                .orElse(null);
        assertNotNull(updatedCourse);
        assertEquals("Advanced Programming", updatedCourse.getName());
    }

    @Test
    void deleteCourse() {
        courseDAO.deleteCourse("COMP1111");

        // Verify that the course was deleted (this would typically involve querying the database)
        Course deletedCourse = courseDAO.getAllCourses().stream()
                .filter(c -> c.getCourseCode().equals("COMP1111"))
                .findFirst()
                .orElse(null);
        assertNull(deletedCourse);
    }
//
//    @Test
//    void filterCoursesByDepartment() throws Exception {
//        courseDAO.addCourse(new Course("COMP1111", "Introduction to Programming", "CS"));
//        courseDAO.addCourse(new Course("COMP2222", "Programming", "CS"));
//        List<Course> courses = courseDAO.filterCoursesByDepartment("CS");
//
//        // Verify that the courses were filtered correctly
//        assertNotNull(courses);
//        assertFalse(courses.isEmpty());
//        assertTrue(courses.stream().allMatch(c -> c.getDepartment().equals("CS")));
//    }

    @Test
    void getAllCourses() throws Exception {
        courseDAO.addCourse(new Course("COMP1111", "Introduction to Programming", "CS"));
        courseDAO.addCourse(new Course("COMP2222", "Programming", "CS"));
        List<Course> courses = courseDAO.getAllCourses();

        // Verify that all courses were retrieved
        assertNotNull(courses);
        assertFalse(courses.isEmpty());
    }

    @Test
    void getAllCoursesID() throws Exception {
        courseDAO.addCourse(new Course("COMP1111", "Introduction to Programming", "CS"));
        List<String> courseIDs = courseDAO.getAllCoursesID();

        // Verify that all course IDs were retrieved
        assertNotNull(courseIDs);
        assertFalse(courseIDs.isEmpty());
    }
}