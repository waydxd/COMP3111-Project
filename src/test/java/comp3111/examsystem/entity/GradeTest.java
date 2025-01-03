package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GradeTest {

    @Test
    void testConstructor() {
        // Arrange
        String studentName = "John Doe";
        String courseName = "COMP3111";
        String examName = "Midterm Exam";
        float score = 85.0f;
        float fullScore = 100.0f;
        float timeSpent = 60.0f;
        String userName = "1";

        // Act
        Grade grade = new Grade(studentName, courseName, examName, score, fullScore, timeSpent, userName);

        // Assert
        Assertions.assertEquals(studentName, grade.getStudentName());
        Assertions.assertEquals(courseName, grade.getCourseName());
        Assertions.assertEquals(examName, grade.getExamName());
        Assertions.assertEquals(score, grade.getScore());
        Assertions.assertEquals(fullScore, grade.getFullScore());
        Assertions.assertEquals(timeSpent, grade.getTimeSpent());
        Assertions.assertEquals(userName, grade.getUserName());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Grade grade = new Grade();

        // Act and Assert
        Assertions.assertEquals(0, grade.getId());
        Assertions.assertEquals("Default Student", grade.getStudentName());
        Assertions.assertEquals("Default Course", grade.getCourseName());
        Assertions.assertEquals("Default Exam", grade.getExamName());
        Assertions.assertEquals(0.0f, grade.getScore());
        Assertions.assertEquals(100.0f, grade.getFullScore());
        Assertions.assertEquals(0.0f, grade.getTimeSpent());
        Assertions.assertEquals("Default User", grade.getUserName());
    }

    @Test
    void testSetters() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setStudentName("Jane Doe");
        grade.setCourseName("COMP5111");
        grade.setExamName("Final Exam");
        grade.setScore(90.0f);
        grade.setFullScore(100.0f);
        grade.setTimeSpent(90.0f);
        grade.setUserName("2");

        // Assert
        Assertions.assertEquals("Jane Doe", grade.getStudentName());
        Assertions.assertEquals("COMP5111", grade.getCourseName());
        Assertions.assertEquals("Final Exam", grade.getExamName());
        Assertions.assertEquals(90.0f, grade.getScore());
        Assertions.assertEquals(100.0f, grade.getFullScore());
        Assertions.assertEquals(90.0f, grade.getTimeSpent());
        Assertions.assertEquals("2", grade.getUserName());
    }

    @Test
    void testGetId() {
        // Arrange
        Grade grade = new Grade();
        grade.setId(1);

        // Act and Assert
        Assertions.assertEquals(1, grade.getId());
    }

    @Test
    void testSetId() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setId(1);

        // Assert
        Assertions.assertEquals(1, grade.getId());
    }

    @Test
    void testGetUserName() {
        // Arrange
        Grade grade = new Grade();
        grade.setUserName("1");

        // Act and Assert
        Assertions.assertEquals("1", grade.getUserName());
    }

    @Test
    void testSetUserName() {
        // Arrange
        Grade grade = new Grade();

        // Act
        grade.setUserName("1");

        // Assert
        Assertions.assertEquals("1", grade.getUserName());
    }
}