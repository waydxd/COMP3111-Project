package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.ExaminationService;
import comp3111.examsystem.service.GradeService;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.StudentService;
import javafx.application.Platform;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentExamControllerTest {

    private StudentExamController controller;

    @Mock
    private ExaminationService examinationService;
    @Mock
    private QuestionService questionService;
    @Mock
    private GradeService gradeService;
    @Mock
    private StudentService studentService;

    @BeforeAll
    public static void initToolkit() {
        // Initialize JavaFX toolkit
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new StudentExamController();
        setPrivateField(controller, "examinationService", examinationService);
        setPrivateField(controller, "questionService", questionService);
        setPrivateField(controller, "gradeService", gradeService);
        setPrivateField(controller, "studentService", studentService);

        // Initialize the controller
        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "initialize");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    private Object invokePrivateMethod(Object object, String methodName, Object... args) throws Exception {
        Class<?>[] parameterTypes = Arrays.stream(args).map(arg -> {
            if (arg instanceof Integer) {
                return int.class;
            } else if (arg instanceof Float) {
                return float.class;
            } else {
                return arg.getClass();
            }
        }).toArray(Class<?>[]::new);
        Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    @Test
    public void testInitialize() throws Exception {
        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "initialize");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertNotNull(getPrivateField(controller, "optionsToggleGroup"));
        assertEquals(((RadioButton) getPrivateField(controller, "optionARadioButton")).getToggleGroup(), getPrivateField(controller, "optionsToggleGroup"));
        assertEquals(((RadioButton) getPrivateField(controller, "optionBRadioButton")).getToggleGroup(), getPrivateField(controller, "optionsToggleGroup"));
        assertEquals(((RadioButton) getPrivateField(controller, "optionCRadioButton")).getToggleGroup(), getPrivateField(controller, "optionsToggleGroup"));
        assertEquals(((RadioButton) getPrivateField(controller, "optionDRadioButton")).getToggleGroup(), getPrivateField(controller, "optionsToggleGroup"));
    }

    @Test
    public void testLoadExamData() throws Exception {
        Examination exam = new Examination();
        exam.setId(1);
        exam.setCourseID("COMP3111");
        exam.setExamName("Midterm");
        exam.setExamTime(1.0f);

        List<Question> questions = Arrays.asList(
                new Question("Question 1", new String[]{"A", "B", "C", "D"}, "A", "type1", 1.0f),
                new Question("Question 2", new String[]{"A", "B", "C", "D"}, "B", "type2", 1.0f)
        );

        when(examinationService.getQuestionsInExamination(exam.getId())).thenReturn(questions);
        setPrivateField(controller, "questions", questions);

        new FXBlock(() -> {
            try {
                controller.setCurrentExam(exam);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals("COMP3111 - Midterm", ((Label) getPrivateField(controller, "quizNameLabel")).getText());
        assertEquals("Total Questions: 2", ((Label) getPrivateField(controller, "totalQuestionsLabel")).getText());
        assertEquals(2, ((ListView<?>) getPrivateField(controller, "questionListView")).getItems().size());
    }

    @Test
    public void testDisplayQuestion() throws Exception {
        List<Question> questions = Arrays.asList(
                new Question("Question 1", new String[]{"A", "B", "C", "D"}, "A", "type1", 1.0f),
                new Question("Question 2", new String[]{"A", "B", "C", "D"}, "B", "type2", 1.0f)
        );
        setPrivateField(controller, "questions", questions);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "displayQuestion", 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals("Question 1", ((Label) getPrivateField(controller, "questionNumberLabel")).getText());
        assertEquals("A", ((RadioButton) getPrivateField(controller, "optionARadioButton")).getText());
        assertEquals("B", ((RadioButton) getPrivateField(controller, "optionBRadioButton")).getText());
        assertEquals("C", ((RadioButton) getPrivateField(controller, "optionCRadioButton")).getText());
        assertEquals("D", ((RadioButton) getPrivateField(controller, "optionDRadioButton")).getText());
    }

    @Test
    public void testHandlePreviousButton() throws Exception {
        List<Question> questions = Arrays.asList(
                new Question("Question 1", new String[]{"A", "B", "C", "D"}, "A", "type1", 1.0f),
                new Question("Question 2", new String[]{"A", "B", "C", "D"}, "B", "type2", 1.0f)
        );
        setPrivateField(controller, "questions", questions);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "handlePreviousButton");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals("Question 1", ((Label) getPrivateField(controller, "questionNumberLabel")).getText());
    }

    @Test
    public void testHandleNextButton() throws Exception {
        List<Question> questions = Arrays.asList(
                new Question("Question 1", new String[]{"A", "B", "C", "D"}, "A", "type1", 1.0f),
                new Question("Question 2", new String[]{"A", "B", "C", "D"}, "B", "type2", 1.0f)
        );
        setPrivateField(controller, "questions", questions);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "handleNextButton");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals("Question 2", ((Label) getPrivateField(controller, "questionNumberLabel")).getText());
    }

    @Test
    public void testHandleSubmitButton() throws Exception {
        List<Question> questions = Arrays.asList(
                new Question("Question 1", new String[]{"A", "B", "C", "D"}, "A", "type1", 1.0f),
                new Question("Question 2", new String[]{"A", "B", "C", "D"}, "B", "type2", 1.0f)
        );
        setPrivateField(controller, "questions", questions);

        Student student = new Student("testUser", "testPass", "Test Name", "Male", "20", "CS");
        when(studentService.getStudent(anyInt())).thenReturn(student);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "handleSubmitButton");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        ArgumentCaptor<Grade> captor = ArgumentCaptor.forClass(Grade.class);
        verify(gradeService).addGrade(captor.capture());

        Grade grade = captor.getValue();
        assertEquals("COMP3111", grade.getCourseName());
        assertEquals("Midterm", grade.getExamName());
        assertEquals(2.0f, grade.getScore());
        assertEquals(2.0f, grade.getFullScore());
    }

    @Test
    public void testIsExamCompleted() throws Exception {
        Grade grade = new Grade();
        grade.setCourseName("COMP3111");
        grade.setExamName("Midterm");
        grade.setUserName("testUser");

        when(gradeService.getGradesForUser("testUser")).thenReturn(Arrays.asList(grade));

        new FXBlock(() -> {
            try {
                Boolean result = (Boolean) invokePrivateMethod(controller, "isExamCompleted");
                assertTrue(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();
    }

    @Test
    public void testSaveGrade() throws Exception {
        Student student = new Student("testUser", "testPass", "Test Name", "Male", "20", "CS");
        when(studentService.getStudent(anyInt())).thenReturn(student);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "saveGrade", 2, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        ArgumentCaptor<Grade> captor = ArgumentCaptor.forClass(Grade.class);
        verify(gradeService).addGrade(captor.capture());

        Grade grade = captor.getValue();
        assertEquals("COMP3111", grade.getCourseName());
        assertEquals("Midterm", grade.getExamName());
        assertEquals(2.0f, grade.getScore());
        assertEquals(2.0f, grade.getFullScore());
    }

    // Helper class to run code on JavaFX thread
    static class FXBlock {
        private final Runnable runnable;

        FXBlock(Runnable runnable) {
            this.runnable = runnable;
        }

        void run() throws Exception {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.runLater(() -> {
                try {
                    runnable.run();
                } finally {
                    latch.countDown();
                }
            });
            latch.await();
        }
    }
}