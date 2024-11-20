package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class ExaminationTest {

    @Test
    void testConstructorWithQuestionsList() {
        // Arrange
        String courseID = "COMP3111";
        float examTime = 120.0f;
        String examName = "Midterm Exam";
        List<Question> questionsList = new ArrayList<>();
        String[] options1 = {"Paris", "London", "Berlin", "Tokyo"};
        questionsList.add(new Question("What is the capital of France?", options1, "Paris", "Multiple Choice", "5"));
        String[] options2 = {"Mercury", "Venus", "Earth", "Jupiter"};
        questionsList.add(new Question("What is the largest planet in our solar system?", options2, "Jupiter", "Multiple Choice", "5"));
        boolean publish = true;

        // Act
        Examination examination = new Examination(courseID, examTime, examName, questionsList, examTime, publish);

        // Assert
        Assertions.assertEquals(courseID, examination.getCourseID());
        Assertions.assertEquals(examTime, examination.getExamTime());
        Assertions.assertEquals(examName, examination.getExamName());
        Assertions.assertEquals(questionsList, examination.getQuiz());
        Assertions.assertTrue(examination.getPublish());
    }

    @Test
    void testConstructorWithoutQuestionsList() {
        // Arrange
        String courseID = "COMP3111";
        float examTime = 120.0f;
        String examName = "Final Exam";
        boolean publish = false;

        // Act
        Examination examination = new Examination(courseID, examTime, examName, publish);

        // Assert
        Assertions.assertEquals(courseID, examination.getCourseID());
        Assertions.assertEquals(examTime, examination.getExamTime());
        Assertions.assertEquals(examName, examination.getExamName());
        Assertions.assertNotNull(examination.getQuiz());
        Assertions.assertTrue(examination.getQuiz().isEmpty());
        Assertions.assertFalse(examination.getPublish());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Examination examination = new Examination();

        // Assert
        Assertions.assertNull(examination.getCourseID());
        Assertions.assertEquals(0.0f, examination.getExamTime());
        Assertions.assertNull(examination.getExamName());
        Assertions.assertNotNull(examination.getQuiz());
        Assertions.assertTrue(examination.getQuiz().isEmpty());
        Assertions.assertFalse(examination.getPublish());
    }

    @Test
    void testSetters() {
        // Arrange
        Examination examination = new Examination();

        // Act
        examination.setCourseID("COMP3111");
        examination.setExamTime(120.0f);
        examination.setExamName("Final Exam");
        examination.setPublish(true);

        // Assert
        Assertions.assertEquals("COMP3111", examination.getCourseID());
        Assertions.assertEquals(120.0f, examination.getExamTime());
        Assertions.assertEquals("Final Exam", examination.getExamName());
        Assertions.assertTrue(examination.getPublish());
    }
}