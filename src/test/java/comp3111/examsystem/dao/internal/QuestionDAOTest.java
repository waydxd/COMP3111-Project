package comp3111.examsystem.dao.internal;

import comp3111.examsystem.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDAOTest {

    private QuestionDAO questionDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        questionDAO = new QuestionDAO(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        String createQuestionsTableSQL = "CREATE TABLE IF NOT EXISTS questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "question TEXT NOT NULL, " +
                "optionA TEXT NOT NULL, " +
                "optionB TEXT NOT NULL, " +
                "optionC TEXT NOT NULL, " +
                "optionD TEXT NOT NULL, " +
                "answer TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "score REAL NOT NULL)";
        connection.createStatement().execute(createQuestionsTableSQL);
    }

    @Test
    void addQuestion() {
        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        questionDAO.addQuestion(question);

        List<Question> questions = questionDAO.getAllQuestions();
        assertTrue(questions.stream().anyMatch(q -> q.getQuestion().equals("What is Java?")));
    }

    @Test
    void getQuestion() {
        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        questionDAO.addQuestion(question);

        int questionId = questionDAO.getAllQuestions().getFirst().getId();
        Question retrievedQuestion = questionDAO.getQuestion(questionId);

        assertNotNull(retrievedQuestion);
        assertEquals("What is Java?", retrievedQuestion.getQuestion());
    }

    @Test
    void updateQuestion() {
        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        questionDAO.addQuestion(question);

        int questionId = questionDAO.getAllQuestions().getFirst().getId();
        question.setId(questionId);
        question.setQuestion("What is JavaScript?");
        questionDAO.updateQuestion(question);

        Question updatedQuestion = questionDAO.getQuestion(questionId);
        assertEquals("What is JavaScript?", updatedQuestion.getQuestion());
    }

    @Test
    void deleteQuestion() {
        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        questionDAO.addQuestion(question);

        int questionId = questionDAO.getAllQuestions().getFirst().getId();
        questionDAO.deleteQuestion(questionId);

        Question deletedQuestion = questionDAO.getQuestion(questionId);
        assertNull(deletedQuestion);
    }

    @Test
    void getAllQuestions() {
        Question question1 = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        Question question2 = new Question("What is JavaScript?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");

        questionDAO.addQuestion(question1);
        questionDAO.addQuestion(question2);

        List<Question> questions = questionDAO.getAllQuestions();
        assertEquals(2, questions.size());
    }
}