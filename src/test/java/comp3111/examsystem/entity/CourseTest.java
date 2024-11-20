package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CourseTest {

    @Test
    void testConstructor() {
        // Arrange
        String courseCode = "COMP3111";
        String name = "Introduction to Software Engineering";
        String department = "Computer Science";

        // Act
        Course course = new Course(courseCode, name, department);

        // Assert
        Assertions.assertEquals(courseCode, course.getCourseCode());
        Assertions.assertEquals(name, course.getName());
        Assertions.assertEquals(department, course.getDepartment());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Course course = new Course();

        // Act and Assert
        Assertions.assertNotNull(course);
        Assertions.assertNull(course.getCourseCode());
        Assertions.assertNull(course.getName());
        Assertions.assertNull(course.getDepartment());
    }

    @Test
    void testSetters() {
        // Arrange
        Course course = new Course();

        // Act
        course.setCourseCode("COMP3111");
        course.setName("Introduction to Software Engineering");
        course.setDepartment("Computer Science");

        // Assert
        Assertions.assertEquals("COMP3111", course.getCourseCode());
        Assertions.assertEquals("Introduction to Software Engineering", course.getName());
        Assertions.assertEquals("Computer Science", course.getDepartment());
    }

    @Test
    void testToString() {
        // Arrange
        Course course = new Course("COMP3111", "Introduction to Software Engineering", "Computer Science");

        // Act
        String toString = course.toString();

        // Assert
        Assertions.assertTrue(toString.contains("COMP3111"));
        Assertions.assertTrue(toString.contains("Introduction to Software Engineering"));
        Assertions.assertTrue(toString.contains("Computer Science"));
    }
}