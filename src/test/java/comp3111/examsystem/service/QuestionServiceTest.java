package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.QuestionDAO;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.internal.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionDAO questionDAO;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionServiceImpl(questionDAO);
    }

    @Test
    void addQuestion() {
        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");

        questionService.addQuestion(question);

        verify(questionDAO).addQuestion(question);
    }

    @Test
    void getQuestion() {
        Question mockQuestion = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        when(questionDAO.getQuestion(1)).thenReturn(mockQuestion);

        Question result = questionService.getQuestion(1);

        verify(questionDAO).getQuestion(1);
        assertNotNull(result);
        assertEquals(mockQuestion.getQuestion(), result.getQuestion());
    }

    @Test
    void getAllQuestions() {
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());
        when(questionDAO.getAllQuestions()).thenReturn(mockQuestions);

        List<Question> result = questionService.getAllQuestions();

        verify(questionDAO).getAllQuestions();
        assertEquals(mockQuestions, result);
    }

    @Test
    void updateQuestion() {
        Question question = new Question("What is Java?", new String[]{"A", "B", "C", "D"}, "A", "MCQ", "10");
        question.setId(1);

        questionService.updateQuestion(1, question);

        verify(questionDAO).updateQuestion(question);
    }

    @Test
    void deleteQuestion() {
        questionService.deleteQuestion(1);

        verify(questionDAO).deleteQuestion(1);
    }
    @Test
    void getAllQuestionsEmpty() {
        when(questionDAO.getAllQuestions()).thenReturn(Collections.emptyList());

        List<Question> result = questionService.getAllQuestions();

        verify(questionDAO).getAllQuestions();
        assertTrue(result.isEmpty());
    }

    @Test
    void getQuestionNotFound() {
        when(questionDAO.getQuestion(1)).thenReturn(null);

        Question result = questionService.getQuestion(1);

        verify(questionDAO).getQuestion(1);
        assertNull(result);
    }



}