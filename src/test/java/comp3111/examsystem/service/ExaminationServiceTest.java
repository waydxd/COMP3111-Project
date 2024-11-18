package comp3111.examsystem.service;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.dao.internal.ExaminationDAO;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExaminationServiceTest {

    @Mock
    private ExaminationDAO examinationDAO;

    @InjectMocks
    private ExaminationServiceImpl examinationService;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        MockitoAnnotations.openMocks(this);
        examinationService = new ExaminationServiceImpl(examinationDAO);
        DatabaseConnection.setUrl("jdbc:sqlite:./test.db");
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException, IOException {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            String sql = new String(Files.readAllBytes(Paths.get("scripts/migrations/000001.sql")));
            stmt.executeUpdate(sql);
        }
    }

    @Test
    void testAddExamination() {
        Examination examination = new Examination();
        examination.setCourseID("COMP1111");
        examination.setExamTime(120.0F);
        examination.setExamName("Sample Exam");
        examination.setPublish(false);

        examinationService.addExamination(examination);

        verify(examinationDAO).addExamination(examination);
    }

    @Test
    void testGetExamination() {
        Examination mockExam = new Examination();
        mockExam.setId(1);
        mockExam.setExamName("Test Exam");
        when(examinationDAO.getExamination(1)).thenReturn(mockExam);

        Examination result = examinationService.getExamination(1);

        verify(examinationDAO).getExamination(1);
        assertNotNull(result);
        assertEquals(mockExam.getId(), result.getId());
        assertEquals(mockExam.getExamName(), result.getExamName());
    }

    @Test
    void testGetAllExaminations() {
        List<Examination> mockExams = Arrays.asList(new Examination(), new Examination());
        when(examinationDAO.getAllExaminations()).thenReturn(mockExams);

        List<Examination> result = examinationService.getAllExaminations();

        verify(examinationDAO).getAllExaminations();
        assertEquals(mockExams, result);
    }

    @Test
    void testUpdateExamination() {
        Examination examination = new Examination();
        examination.setId(1);
        examination.setExamName("Updated Exam");

        examinationService.updateExamination(examination);

        verify(examinationDAO).updateExamination(examination);
    }

    @Test
    void testDeleteExamination() {
        examinationService.deleteExamination(1);

        verify(examinationDAO).deleteExamination(1);
    }

    @Test
    void testAddQuestionToExamination() throws Exception {
        examinationService.addQuestionToExamination(1, 1);

        verify(examinationDAO).addQuestionToExamination(1, 1);
    }

    @Test
    void testRemoveQuestionFromExamination() {
        examinationService.removeQuestionFromExamination(1, 1);

        verify(examinationDAO).removeQuestionFromExamination(1, 1);
    }

    @Test
    void testGetQuestionsInExamination() {
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());
        when(examinationDAO.getQuestionsInExamination(1)).thenReturn(mockQuestions);

        List<Question> result = examinationService.getQuestionsInExamination(1);

        verify(examinationDAO).getQuestionsInExamination(1);
        assertEquals(mockQuestions, result);
    }
}