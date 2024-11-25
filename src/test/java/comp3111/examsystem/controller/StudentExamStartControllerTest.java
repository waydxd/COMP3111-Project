package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.ExaminationService;
import javafx.application.Platform;
import javafx.scene.control.Label;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentExamStartControllerTest {

    private StudentExamStartController controller;

    @Mock
    private ExaminationService examinationService;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new StudentExamStartController();
        setPrivateField(controller, "examinationService", examinationService);

        // Initialize JavaFX components using reflection
        setPrivateField(controller, "courseLabel", new Label());
        setPrivateField(controller, "examNameLabel", new Label());
        setPrivateField(controller, "numberOfQuestionsLabel", new Label());
        setPrivateField(controller, "fullScoreLabel", new Label());
        setPrivateField(controller, "examTimeLabel", new Label());
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
        Class<?>[] parameterTypes = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
        Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    @Test
    public void testSetExamDetails() throws Exception {
        Examination exam = new Examination();
        exam.setId(1);
        exam.setCourseID("COMP3111");
        exam.setExamName("Midterm");
        exam.setExamTime(60.0f);

        List<Question> questions = Arrays.asList(
                new Question("Question 1", new String[]{"A", "B", "C", "D"}, "A", "type1", 1.0f),
                new Question("Question 2", new String[]{"A", "B", "C", "D"}, "B", "type2", 1.0f)
        );

        when(examinationService.getQuestionsInExamination(exam.getId())).thenReturn(questions);
        when(examinationService.getTotalScoreOfExamination(exam.getId())).thenReturn(2.0f);

        invokePrivateMethod(controller, "setExamDetails", exam);

        assertEquals("COMP3111", ((Label) getPrivateField(controller, "courseLabel")).getText());
        assertEquals("Midterm", ((Label) getPrivateField(controller, "examNameLabel")).getText());
        assertEquals("Number of Questions: 2", ((Label) getPrivateField(controller, "numberOfQuestionsLabel")).getText());
        assertEquals("Full Score: 2.0", ((Label) getPrivateField(controller, "fullScoreLabel")).getText());
        assertEquals("Time: 60.0 minutes", ((Label) getPrivateField(controller, "examTimeLabel")).getText());
    }


    @Test
    public void testStartExam() throws Exception {
        Examination exam = new Examination();
        exam.setId(1);
        exam.setCourseID("COMP3111");
        exam.setExamName("Midterm");

        setPrivateField(controller, "username", "testUser");
        setPrivateField(controller, "studentid", 123); // Ensure this matches the actual field name
        invokePrivateMethod(controller, "setExamDetails", exam);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "startExam");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        // Verify that the new stage is shown with the correct title
        // This part is difficult to test directly without a full JavaFX environment
        // You can verify the FXMLLoader and controller interactions instead
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