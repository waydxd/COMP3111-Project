package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    void testConstructor() {
        // Arrange
        String question = "What is the capital of France?";
        String[] options = {"Paris", "London", "Berlin", "Rome"};
        String answer = "Paris";
        String type = "Multiple Choice";
        String score = "5.0";

        // Act
        Question q = new Question(question, options, answer, type, score);

        // Assert
        Assertions.assertEquals(question, q.getQuestion());
        Assertions.assertEquals(options[0], q.getOptionA());
        Assertions.assertEquals(options[1], q.getOptionB());
        Assertions.assertEquals(options[2], q.getOptionC());
        Assertions.assertEquals(options[3], q.getOptionD());
        Assertions.assertEquals(answer, q.getAnswer());
        Assertions.assertEquals(type, q.getType());
        Assertions.assertEquals(5.0f, q.getScore());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Question q = new Question();

        // Act and Assert
        Assertions.assertNull(q.getQuestion());
        Assertions.assertNull(q.getOptionA());
        Assertions.assertNull(q.getOptionB());
        Assertions.assertNull(q.getOptionC());
        Assertions.assertNull(q.getOptionD());
        Assertions.assertNull(q.getAnswer());
        Assertions.assertNull(q.getType());
        Assertions.assertEquals(0.0f, q.getScore());
    }

    @Test
    void testSetters() {
        // Arrange
        Question q = new Question();

        // Act
        q.setQuestion("What is the largest planet in our solar system?");
        q.setOptionA("Jupiter");
        q.setOptionB("Saturn");
        q.setOptionC("Earth");
        q.setOptionD("Mars");
        q.setAnswer("Jupiter");
        q.setType("Multiple Choice");
        q.setScore(10.0f);

        // Assert
        Assertions.assertEquals("What is the largest planet in our solar system?", q.getQuestion());
        Assertions.assertEquals("Jupiter", q.getOptionA());
        Assertions.assertEquals("Saturn", q.getOptionB());
        Assertions.assertEquals("Earth", q.getOptionC());
        Assertions.assertEquals("Mars", q.getOptionD());
        Assertions.assertEquals("Jupiter", q.getAnswer());
        Assertions.assertEquals("Multiple Choice", q.getType());
        Assertions.assertEquals(10.0f, q.getScore());
    }
}