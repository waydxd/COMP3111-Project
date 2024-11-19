package comp3111.examsystem.dao.internal;

import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExaminationDAOTest {

    private ExaminationDAO examinationDAO;
    private QuestionDAO questionDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        examinationDAO = new ExaminationDAO(connection);
        questionDAO = new QuestionDAO(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        String createExaminationsTableSQL = "CREATE TABLE IF NOT EXISTS examinations (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "courseID TEXT NOT NULL, " +
                "examTime REAL NOT NULL, " +
                "examName TEXT NOT NULL, " +
                "publish BOOLEAN NOT NULL)";
        connection.createStatement().execute(createExaminationsTableSQL);

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

        String createExaminationQuestionsTableSQL = "CREATE TABLE IF NOT EXISTS examination_questions (" +
                "examination_id INTEGER NOT NULL, " +
                "question_id INTEGER NOT NULL, " +
                "PRIMARY KEY (examination_id, question_id), " +
                "FOREIGN KEY (examination_id) REFERENCES examinations(id), " +
                "FOREIGN KEY (question_id) REFERENCES questions(id))";
        connection.createStatement().execute(createExaminationQuestionsTableSQL);
    }

    @Test
    void addExamination() {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);

        List<Examination> exams = examinationDAO.getAllExaminations();
        assertTrue(exams.stream().anyMatch(e -> e.getCourseID().equals("COMP3111") &&
                e.getExamTime().equals(2023.12f) &&
                e.getExamName().equals("Final Exam") &&
                e.getPublish()));
    }

    @Test
    void getExamination() {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);
        Examination retrievedExam = examinationDAO.getExamination(1);

        assertNotNull(retrievedExam);
        assertEquals("COMP3111", retrievedExam.getCourseID());
        assertEquals(2023.12f, retrievedExam.getExamTime());
        assertEquals("Final Exam", retrievedExam.getExamName());
        assertTrue(retrievedExam.getPublish());
    }

    @Test
    void getAllExaminations() {
        Examination exam1 = new Examination();
        exam1.setCourseID("COMP3111");
        exam1.setExamTime(2023.12f);
        exam1.setExamName("Final Exam");
        exam1.setPublish(true);

        Examination exam2 = new Examination();
        exam2.setCourseID("COMP3112");
        exam2.setExamTime(2023.12f);
        exam2.setExamName("Midterm Exam");
        exam2.setPublish(false);

        examinationDAO.addExamination(exam1);
        examinationDAO.addExamination(exam2);

        List<Examination> exams = examinationDAO.getAllExaminations();
        assertEquals(2, exams.size());
    }

    @Test
    void updateExamination() {

        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);

        exam = examinationDAO.getAllExaminations().getFirst();


        exam.setExamName("Updated Final Exam");
        examinationDAO.updateExamination(exam);


        assertEquals(examinationDAO.getExamination(exam.getId()).getExamName(), exam.getExamName());
    }
    @Test
    void deleteExamination() {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);
        examinationDAO.deleteExamination(1);

        Examination deletedExam = examinationDAO.getExamination(1);
        assertNull(deletedExam);
    }

    @Test
    void addQuestionToExamination() throws Exception {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);

        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A","MCQ", "10");
        // Assuming a method to add a question to the database exists
        questionDAO.addQuestion(question);

        examinationDAO.addQuestionToExamination(1, 1);

        List<Question> questions = examinationDAO.getQuestionsInExamination(1);
        assertTrue(questions.stream().anyMatch(q -> q.getQuestion().equals("What is Java?")));
    }

    @Test
    void removeQuestionFromExamination() throws Exception {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);

        Question question = new Question();
        question.setQuestion("What is Java?");
        // Assuming a method to add a question to the database exists
        // addQuestion(question);

        examinationDAO.addQuestionToExamination(1, 1);
        examinationDAO.removeQuestionFromExamination(1, 1);

        List<Question> questions = examinationDAO.getQuestionsInExamination(1);
        assertFalse(questions.stream().anyMatch(q -> q.getQuestion().equals("What is Java?")));
    }

    @Test
    void getQuestionsInExamination() throws Exception {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);

        Question question1 = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A","MCQ", "10");
        questionDAO.addQuestion(question1);

        Question question2 = new Question("What is JavaScript?", new String[]{"A", "B", "C", "D"}, "A","MCQ", "10");
        questionDAO.addQuestion(question2);

        examinationDAO.addQuestionToExamination(1, 1);
        examinationDAO.addQuestionToExamination(1, 2);

        List<Question> questions = examinationDAO.getQuestionsInExamination(1);
        assertEquals(2, questions.size());
    }

    @Test
    void addQuestionToExamination_ShouldThrowExceptionWhenQuestionIsRepeated() throws Exception {
        Examination exam = new Examination();
        exam.setCourseID("COMP3111");
        exam.setExamTime(2023.12f);
        exam.setExamName("Final Exam");
        exam.setPublish(true);

        examinationDAO.addExamination(exam);

        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        questionDAO.addQuestion(question);

        examinationDAO.addQuestionToExamination(1, 1);

        Exception exception = assertThrows(Exception.class, () -> {
            examinationDAO.addQuestionToExamination(1, 1);
        });

        assertEquals("repeated", exception.getMessage());
    }
}