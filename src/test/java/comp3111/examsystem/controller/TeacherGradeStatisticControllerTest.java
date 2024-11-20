package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Grade;
import javafx.application.Platform;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;


public class TeacherGradeStatisticControllerTest {

    private final TeacherGradeStatisticController controller = new TeacherGradeStatisticController();


    @BeforeAll
    public static void startJavaFXRuntime() {
        try {
                Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized, continue with the tests
        }
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

    private TeacherGradeStatisticController createController() throws Exception {
        TeacherGradeStatisticController controller = new TeacherGradeStatisticController();

        new FXBlock(() -> {
            // Initialize FXML components
            controller.courseCombox = new ChoiceBox<>();
            controller.examTextField = new TextField();
            controller.studentTextField = new TextField();
            controller.gradeTable = new TableView<>();
            controller.barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
            controller.lineChart = new LineChart<>(new CategoryAxis(), new NumberAxis());
            controller.pieChart = new PieChart();
            controller.categoryAxisBar = new CategoryAxis();
            controller.numberAxisBar = new NumberAxis();
            controller.categoryAxisLine = new CategoryAxis();
            controller.numberAxisLine = new NumberAxis();
        }).run();

        return controller;
    }

    @Test
    void calculateCourseAverages_WithValidGrades_ShouldReturnCorrectAverages() {
        List<Grade> grades = Arrays.asList(
                new Grade("Student1", "Math", "Exam1", 90.0F, 100.0F, 60),
                new Grade("Student2", "Math", "Exam2", 80.0F, 100.0F, 70),
                new Grade("Student3", "Physics", "Exam1", 85.0F, 100.0F, 60)
        );

        Map<String, Double> averages = controller.calculateCourseAverages(grades);

        assertEquals(85.0, averages.get("Math"), 0.01);
        assertEquals(85.0, averages.get("Physics"), 0.01);
    }

    @Test
    void calculateCourseAverages_WithEmptyGrades_ShouldReturnEmptyMap() {
        List<Grade> grades = Collections.emptyList();

        Map<String, Double> averages = controller.calculateCourseAverages(grades);

        assertTrue(averages.isEmpty());
    }

    @Test
    void calculateCourseAverages_WithSingleGrade_ShouldReturnSingleAverage() {
        List<Grade> grades = Collections.singletonList(
                new Grade("Student1", "Math", "Exam1", 90.0F, 100.0F, 60)
        );

        Map<String, Double> averages = controller.calculateCourseAverages(grades);

        assertEquals(90.0, averages.get("Math"), 0.01);
    }

    @Test
    void calculateCourseAverages_WithMultipleGradesSameCourse_ShouldReturnCorrectAverage() {
        List<Grade> grades = Arrays.asList(
                new Grade("Student1", "Math", "Exam1", 70.0F, 100.0F, 60),
                new Grade("Student2", "Math", "Exam2", 80.0F, 100.0F, 70),
                new Grade("Student3", "Math", "Exam3", 90.0F, 100.0F, 60)
        );

        Map<String, Double> averages = controller.calculateCourseAverages(grades);

        assertEquals(80.0, averages.get("Math"), 0.01);
    }
    @Test
    public void testQueryFunction() throws Exception {
        TeacherGradeStatisticController controller = createController();

        new FXBlock(() -> {
            // Setup test data
            Grade grade1 = new Grade();
            grade1.setCourseName("Math");
            grade1.setExamName("Midterm");
            grade1.setStudentName("John");
            grade1.setScore(90.0F);

            Grade grade2 = new Grade();
            grade2.setCourseName("Physics");
            grade2.setExamName("Final");
            grade2.setStudentName("Jane");
            grade2.setScore(85.0F);

            controller.gradeList.addAll(grade1, grade2);

            // Test course filter
            controller.courseCombox.setValue("Math");
            controller.examTextField.setText("");
            controller.studentTextField.setText("");

            controller.query();

            assertEquals(1, controller.gradeTable.getItems().size());
            assertEquals("Math", controller.gradeTable.getItems().get(0).getCourseName());
        }).run();
    }

    @Test
    public void testResetFunction() throws Exception {
        TeacherGradeStatisticController controller = createController();

        new FXBlock(() -> {
            // Set some values
            controller.courseCombox.setValue("Math");
            controller.examTextField.setText("Midterm");
            controller.studentTextField.setText("John");

            // Test reset
            controller.reset();

            assertNull(controller.courseCombox.getValue());
            assertEquals("", controller.examTextField.getText());
            assertEquals("", controller.studentTextField.getText());
        }).run();
    }

    @Test
    public void testQueryFunction_AllBranches() throws Exception {
        TeacherGradeStatisticController controller = createController();

        new FXBlock(() -> {
            // Setup test data
            Grade grade1 = new Grade("Student1", "Math", "Midterm", 90.0F, 100.0F, 60);
            Grade grade2 = new Grade("Student2", "Physics", "Final", 85.0F, 100.0F, 60);
            Grade grade3 = new Grade("Student3", "Math", "Final", 75.0F, 100.0F, 60);
            controller.gradeList.addAll(grade1, grade2, grade3);

            // Test course filter
            controller.courseCombox.setValue("Math");
            controller.examTextField.setText("");
            controller.studentTextField.setText("");
            controller.query();
            assertEquals(2, controller.gradeTable.getItems().size());

            // Test exam filter
            controller.courseCombox.setValue("");
            controller.examTextField.setText("Final");
            controller.studentTextField.setText("");
            controller.query();
            assertEquals(2, controller.gradeTable.getItems().size());

            // Test student filter
            controller.courseCombox.setValue("");
            controller.examTextField.setText("");
            controller.studentTextField.setText("Student1");
            controller.query();
            assertEquals(1, controller.gradeTable.getItems().size());

            // Test all filters
            controller.courseCombox.setValue("Math");
            controller.examTextField.setText("Final");
            controller.studentTextField.setText("Student3");
            controller.query();
            assertEquals(1, controller.gradeTable.getItems().size());

            // Test no matches
            controller.courseCombox.setValue("NonExistentCourse");
            controller.examTextField.setText("NonExistentExam");
            controller.studentTextField.setText("NonExistentStudent");
            controller.query();
            assertEquals(0, controller.gradeTable.getItems().size());
        }).run();
    }

}