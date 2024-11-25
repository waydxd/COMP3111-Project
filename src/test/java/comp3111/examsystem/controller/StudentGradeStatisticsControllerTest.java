package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.service.GradeService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StudentGradeStatisticsControllerTest {

    private StudentGradeStatisticsController controller;

    private String username = "testUser";

    @Mock
    private GradeService gradeService;

    @BeforeAll
    public static void initToolkit() {
        Platform.startup(() -> {});
    }

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        controller = new StudentGradeStatisticsController();
        setPrivateField(controller, "gradeService", gradeService);

        // Initialize JavaFX components using reflection
        setPrivateField(controller, "courseCombox", new ChoiceBox<>());
        setPrivateField(controller, "gradeTable", new TableView<>());
        setPrivateField(controller, "courseColumn", new TableColumn<>());
        setPrivateField(controller, "examColumn", new TableColumn<>());
        setPrivateField(controller, "scoreColumn", new TableColumn<>());
        setPrivateField(controller, "fullScoreColumn", new TableColumn<>());
        setPrivateField(controller, "timeSpendColumn", new TableColumn<>());
        setPrivateField(controller, "barChart", new BarChart<>(new CategoryAxis(), new NumberAxis()));
        setPrivateField(controller, "categoryAxisBar", new CategoryAxis());
        setPrivateField(controller, "numberAxisBar", new NumberAxis());

        controller.initialize(null, null);
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
            if (arg instanceof List) {
                return List.class;
            } else {
                return arg.getClass();
            }
        }).toArray(Class<?>[]::new);
        Method method = object.getClass().getDeclaredMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(object, args);
    }

    @Test
    public void testSetUsername() throws Exception {
        controller.setUsername(username);
        assertEquals(username, getPrivateField(controller, "username"));
    }

    @Test
    public void testInitialize() throws Exception {
        assertEquals("Course", controller.categoryAxisBar.getLabel());
        assertEquals("Avg. Score", controller.numberAxisBar.getLabel());
    }

    @Test
    public void testLoadGrades() throws Exception {
        Grade grade1 = new Grade("testUser", "COMP3111", "Midterm", 90.0f, 100.0f, 60.0f, username);
        Grade grade2 = new Grade("testUser", "COMP3111", "Final", 85.0f, 100.0f, 60.0f, username);
        List<Grade> grades = Arrays.asList(grade1, grade2);

        when(gradeService.getGradesForUser("testUser")).thenReturn(grades);

        controller.setUsername("testUser");

        assertEquals(2, controller.gradeTable.getItems().size());
        assertEquals(2, controller.courseCombox.getItems().size());
    }

    @Test
    public void testInitializeChoiceBoxes() throws Exception {
        Grade grade1 = new Grade("testUser", "COMP3111", "Midterm", 90.0f, 100.0f, 60.0f, username);
        Grade grade2 = new Grade("testUser", "COMP3111", "Final", 85.0f, 100.0f, 60.0f, username);
        ObservableList<Grade> grades = FXCollections.observableArrayList(grade1, grade2);

        setPrivateField(controller, "gradeList", grades);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "initializeChoiceBoxes");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals(2, controller.courseCombox.getItems().size()); // includes empty string
    }

    @Test
    public void testRefresh() throws Exception {
        Grade grade1 = new Grade("testUser", "COMP3111", "Midterm", 90.0f, 100.0f, 60.0f, username);
        Grade grade2 = new Grade("testUser", "COMP3111", "Final", 85.0f, 100.0f, 60.0f, username);
        List<Grade> grades = Arrays.asList(grade1, grade2);

        when(gradeService.getGradesForUser("testUser")).thenReturn(grades);

        controller.setUsername("testUser");

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "refresh");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals(2, controller.gradeTable.getItems().size());
    }

    @Test
    public void testLoadChart() throws Exception {
        Grade grade1 = new Grade("testUser", "COMP3111", "Midterm", 90.0f, 100.0f, 60.0f, username);
        Grade grade2 = new Grade("testUser", "COMP3111", "Final", 85.0f, 100.0f, 60.0f, username);
        List<Grade> grades = Arrays.asList(grade1, grade2);

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "loadChart", grades);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals(1, controller.barChart.getData().size());
    }

    @Test
    public void testCalculateCourseAverages() throws Exception {
        Grade grade1 = new Grade("testUser", "COMP3111", "Midterm", 90.0f, 100.0f, 60.0f, username);
        Grade grade2 = new Grade("testUser", "COMP3111", "Final", 85.0f, 100.0f, 60.0f, username);
        List<Grade> grades = Arrays.asList(grade1, grade2);

        Map<String, Double> averages = controller.calculateCourseAverages(grades);

        assertEquals(1, averages.size());
        assertEquals(87.5, averages.get("COMP3111"));
    }

    @Test
    public void testReset() throws Exception {
        controller.reset();
        assertEquals(null, controller.courseCombox.getValue());
    }

    @Test
    public void testQuery() throws Exception {
        Grade grade1 = new Grade("testUser", "COMP3111", "Midterm", 90.0f, 100.0f, 60.0f, username);
        Grade grade2 = new Grade("testUser", "COMP3111", "Final", 85.0f, 100.0f, 60.0f, username);
        ObservableList<Grade> grades = FXCollections.observableArrayList(grade1, grade2);

        setPrivateField(controller, "gradeList", grades);
        controller.courseCombox.setValue("COMP3111");

        new FXBlock(() -> {
            try {
                invokePrivateMethod(controller, "query");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).run();

        assertEquals(2, controller.gradeTable.getItems().size());
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