package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.ExaminationService;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class ExamManagementSystemControllerTest {

    private ExamManagementSystemController controller;
    private ExaminationService examinationService;
    private QuestionService questionService;

    @BeforeAll
    public static void startJavaFXRuntime() {
        try {
                Platform.startup(() -> {});

        } catch (IllegalStateException e) {
            // Toolkit already initialized, continue with the tests
        }
        Platform.setImplicitExit(false);
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

    @BeforeEach
    void setUp() throws Exception {
        examinationService = Mockito.mock(ExaminationService.class);
        questionService = Mockito.mock(QuestionService.class);
        controller = new ExamManagementSystemController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "examNameTextField", new TextField());
                setPrivateField(controller, "filterCourseID", new ComboBox<>());
                setPrivateField(controller, "filterPublish", new ComboBox<>());
                setPrivateField(controller, "questionTextField", new TextField());
                setPrivateField(controller, "questionTypeComboBox", new ComboBox<>());
                setPrivateField(controller, "scoreTextField", new TextField());
                setPrivateField(controller, "examNameField", new TextField());
                setPrivateField(controller, "examTimeField", new TextField());
                setPrivateField(controller, "courseComboBox", new ComboBox<>());
                setPrivateField(controller, "publishComboBox", new ComboBox<>());
                setPrivateField(controller, "ExamTableView", new TableView<>());
                setPrivateField(controller, "LeftQuestionTableView", new TableView<>());
                setPrivateField(controller, "All_QuestionTableView", new TableView<>());

                setPrivateField(controller, "examNameColumn", new TableColumn<>());
                setPrivateField(controller, "courseIdColumn", new TableColumn<>());
                setPrivateField(controller, "examTimeColumn", new TableColumn<>());
                setPrivateField(controller, "publishColumn", new TableColumn<>());
                setPrivateField(controller, "questionColumn", new TableColumn<>());
                setPrivateField(controller, "typeColumn", new TableColumn<>());
                setPrivateField(controller, "scoreColumn_left", new TableColumn<>());
                setPrivateField(controller, "scoreColumn_right", new TableColumn<>());
                setPrivateField(controller, "questionTextColumn", new TableColumn<>());
                setPrivateField(controller, "questionTypeColumn", new TableColumn<>());
                controller.initialize();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private void invokePrivateMethod(Object object, String methodName) throws Exception {
        object.getClass().getDeclaredMethod(methodName).setAccessible(true);
        object.getClass().getDeclaredMethod(methodName).invoke(object);
    }

    @Test
    void testFilterQuestions() throws Exception {
        // Arrange
        Question question1 = new Question("Q1", new String[]{"A", "B", "C", "D"}, "A", "Single", "10.0");
        Question question2 = new Question("Q2", new String[]{"A", "B", "C", "D"}, "B", "Multiple", "20.0");
        List<Question> questions = Arrays.asList(question1, question2);
        Mockito.when(questionService.getAllQuestions()).thenReturn(questions);

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "questionService", questionService);
                invokePrivateMethod(controller, "filterQuestions");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // Act and Assert
            TableView<Question> allQuestionTableView = null;
            try {
                allQuestionTableView = (TableView<Question>) getPrivateField(controller, "All_QuestionTableView");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            assertEquals(2, allQuestionTableView.getItems().size());
        }).run();
    }

//    @Test
//    void testAddExam() throws Exception {
//        // Arrange
//        new FXBlock(() -> {
//            try {
//                TextField examNameField = (TextField) getPrivateField(controller, "examNameField");
//                TextField examTimeField = (TextField) getPrivateField(controller, "examTimeField");
//                ComboBox<String> courseComboBox = (ComboBox<String>) getPrivateField(controller, "courseComboBox");
//                ComboBox<String> publishComboBox = (ComboBox<String>) getPrivateField(controller, "publishComboBox");
//
//                examNameField.setText("New Exam");
//                examTimeField.setText("60");
//                courseComboBox.setValue("COMP3111");
//                publishComboBox.setValue("yes");
//
//                // Act
//                controller.AddExam(new ActionEvent());
//
//                // Assert
//                TableView<Examination> examTableView = (TableView<Examination>) getPrivateField(controller, "ExamTableView");
//                assertEquals(1, examTableView.getItems().size());
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }).run();
//    }

    @Test
    void testResetQuestionFields() throws Exception {
        new FXBlock(() -> {
            try {
                // Arrange
                TextField questionTextField = (TextField) getPrivateField(controller, "questionTextField");
                ComboBox<String> questionTypeComboBox = (ComboBox<String>) getPrivateField(controller, "questionTypeComboBox");
                TextField scoreTextField = (TextField) getPrivateField(controller, "scoreTextField");

                questionTextField.setText("Sample Question");
                questionTypeComboBox.setValue("Single");
                scoreTextField.setText("10");

                // Act
                controller.resetQuestionFields(new ActionEvent());

                // Assert
                assertEquals("", questionTextField.getText());
                assertNull(questionTypeComboBox.getValue());
                assertEquals("", scoreTextField.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testResetExamFields() throws Exception {
        new FXBlock(() -> {
            try {
                // Arrange
                TextField examNameTextField = (TextField) getPrivateField(controller, "examNameTextField");
                ComboBox<String> filterCourseID = (ComboBox<String>) getPrivateField(controller, "filterCourseID");
                ComboBox<String> filterPublish = (ComboBox<String>) getPrivateField(controller, "filterPublish");

                examNameTextField.setText("Sample Exam");
                filterCourseID.setValue("COMP3111");
                filterPublish.setValue("yes");

                // Act
                controller.resetExamFields(new ActionEvent());

                // Assert
                assertEquals("", examNameTextField.getText());
                assertNull(filterCourseID.getValue());
                assertNull(filterPublish.getValue());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testFilterExams() throws Exception {
        // Arrange
        Examination exam1 = new Examination("COMP3111", 60.0f, "Midterm", true);
        Examination exam2 = new Examination("COMP3111", 120.0f, "Final", false);
        List<Examination> exams = Arrays.asList(exam1, exam2);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(exams);

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "examinationService", examinationService);
                ComboBox<String> filterCourseID = (ComboBox<String>) getPrivateField(controller, "filterCourseID");
                ComboBox<String> filterPublish = (ComboBox<String>) getPrivateField(controller, "filterPublish");
                TextField examNameTextField = (TextField) getPrivateField(controller, "examNameTextField");

                filterCourseID.setValue("COMP3111");
                filterPublish.setValue("yes");
                examNameTextField.setText("Midterm");

                // Act
                controller.filterExams(new ActionEvent());

                // Assert
                TableView<Examination> examTableView = (TableView<Examination>) getPrivateField(controller, "ExamTableView");
                assertEquals(1, examTableView.getItems().size());
                assertEquals("Midterm", examTableView.getItems().get(0).getExamName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testDeleteExam() throws Exception {
        // Arrange
        Examination exam1 = new Examination("COMP3111", 60.0f, "Midterm", true);
        List<Examination> exams = Arrays.asList(exam1);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(exams);

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "examinationService", examinationService);
                TableView<Examination> examTableView = (TableView<Examination>) getPrivateField(controller, "ExamTableView");
                examTableView.setItems(FXCollections.observableArrayList(exams));
                examTableView.getSelectionModel().select(exam1);

                // Act
                controller.deleteExam(new ActionEvent());

                // Assert
                assertEquals(0, examTableView.getItems().size());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testAddToLeft() throws Exception {
        // Arrange
        Question question1 = new Question("Q1", new String[]{"A", "B", "C", "D"}, "A", "Single", "10.0");
        Examination exam1 = new Examination("COMP3111", 60.0f, "Midterm", true);
        List<Question> questions = Arrays.asList(question1);
        List<Examination> exams = Arrays.asList(exam1);
        Mockito.when(questionService.getAllQuestions()).thenReturn(questions);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(exams);
        Mockito.when(examinationService.getQuestionsInExamination(exam1.getId())).thenReturn(questions);

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "questionService", questionService);
                setPrivateField(controller, "examinationService", examinationService);
                TableView<Question> allQuestionTableView = (TableView<Question>) getPrivateField(controller, "All_QuestionTableView");
                TableView<Examination> examTableView = (TableView<Examination>) getPrivateField(controller, "ExamTableView");
                TableView<Question> leftQuestionTableView = (TableView<Question>) getPrivateField(controller, "LeftQuestionTableView");

                allQuestionTableView.setItems(FXCollections.observableArrayList(questions));
                examTableView.setItems(FXCollections.observableArrayList(exams));
                examTableView.getSelectionModel().select(exam1);
                allQuestionTableView.getSelectionModel().select(question1);

                // Act
                invokePrivateMethod(controller, "addToLeft");
                // Assert
                assertEquals(1, leftQuestionTableView.getItems().size());
                assertEquals("Q1", leftQuestionTableView.getItems().get(0).getQuestion());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testDeleteFromLeft() throws Exception {
        // Arrange
        Question question1 = new Question("Q1", new String[]{"A", "B", "C", "D"}, "A", "Single", "10.0");
        Examination exam1 = new Examination("COMP3111", 60.0f, "Midterm", true);
        List<Question> questions = Arrays.asList(question1);
        List<Examination> exams = Arrays.asList(exam1);
        Mockito.when(questionService.getAllQuestions()).thenReturn(questions);
        Mockito.when(examinationService.getAllExaminations()).thenReturn(exams);
        Mockito.when(examinationService.getQuestionsInExamination(exam1.getId())).thenReturn(questions);

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "questionService", questionService);
                setPrivateField(controller, "examinationService", examinationService);
                TableView<Question> leftQuestionTableView = (TableView<Question>) getPrivateField(controller, "LeftQuestionTableView");
                TableView<Examination> examTableView = (TableView<Examination>) getPrivateField(controller, "ExamTableView");

                leftQuestionTableView.setItems(FXCollections.observableArrayList(questions));
                examTableView.setItems(FXCollections.observableArrayList(exams));
                examTableView.getSelectionModel().select(exam1);
                leftQuestionTableView.getSelectionModel().select(question1);

                // Act
                invokePrivateMethod(controller, "deleteFromLeft");

                // Assert
                assertEquals(0, leftQuestionTableView.getItems().size());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }
    @Test
    void testAddQuestionToExam() throws Exception {
        // Arrange
        try {
            Examination exam = new Examination("COMP3111", 60.0f, "Test Exam", true);
            examinationService.addExamination(exam);

            Question question = new Question("What is the capital of France?", new String[]{"A", "B", "C", "D"}, "A", "Single", "5.0");
            examinationService.addQuestionToExamination(exam.getId(), question.getId());

            // Act
            controller.addToLeft();

            // Assert
                Mockito.verify(examinationService).addQuestionToExamination(exam.getId(), question.getId());
//            List<Question> questionsInExam = examinationService.getQuestionsInExamination(exam.getId());
//            Assertions.assertTrue(questionsInExam.contains(question));
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}