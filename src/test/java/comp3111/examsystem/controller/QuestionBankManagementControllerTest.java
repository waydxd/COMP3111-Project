package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.QuestionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class QuestionBankManagementControllerTest {

    private QuestionBankManagementController controller;
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
        questionService = Mockito.mock(QuestionService.class);
        controller = new QuestionBankManagementController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "questionTextField", new TextField());
                setPrivateField(controller, "optionATextField", new TextField());
                setPrivateField(controller, "optionBTextField", new TextField());
                setPrivateField(controller, "optionCTextField", new TextField());
                setPrivateField(controller, "optionDTextField", new TextField());
                setPrivateField(controller, "answerTextField", new TextField());
                setPrivateField(controller, "typeTextField", new ChoiceBox<>());
                setPrivateField(controller, "scoreTextField", new TextField());
                setPrivateField(controller, "questionTableView", new TableView<>());
                setPrivateField(controller, "questionService", questionService);
                controller.initialize(null, null);
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

    @Test
    void testHandleAddButton() throws Exception {
        new FXBlock(() -> {
            try {
                TextField questionTextField = (TextField) getPrivateField(controller, "questionTextField");
                TextField optionATextField = (TextField) getPrivateField(controller, "optionATextField");
                TextField optionBTextField = (TextField) getPrivateField(controller, "optionBTextField");
                TextField optionCTextField = (TextField) getPrivateField(controller, "optionCTextField");
                TextField optionDTextField = (TextField) getPrivateField(controller, "optionDTextField");
                TextField answerTextField = (TextField) getPrivateField(controller, "answerTextField");
                ChoiceBox<String> typeTextField = (ChoiceBox<String>) getPrivateField(controller, "typeTextField");
                TextField scoreTextField = (TextField) getPrivateField(controller, "scoreTextField");

                questionTextField.setText("Sample Question");
                optionATextField.setText("Option A");
                optionBTextField.setText("Option B");
                optionCTextField.setText("Option C");
                optionDTextField.setText("Option D");
                answerTextField.setText("A");
                typeTextField.setValue("Single");
                scoreTextField.setText("10");

                // Act

                controller.handleAddButton();

                // Assert
                Mockito.verify(questionService).addQuestion(Mockito.any(Question.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleUpdateButton() throws Exception {
        new FXBlock(() -> {
            try {
                TableView<Question> questionTableView = (TableView<Question>) getPrivateField(controller, "questionTableView");
                TextField questionTextField = (TextField) getPrivateField(controller, "questionTextField");
                TextField optionATextField = (TextField) getPrivateField(controller, "optionATextField");
                TextField optionBTextField = (TextField) getPrivateField(controller, "optionBTextField");
                TextField optionCTextField = (TextField) getPrivateField(controller, "optionCTextField");
                TextField optionDTextField = (TextField) getPrivateField(controller, "optionDTextField");
                TextField answerTextField = (TextField) getPrivateField(controller, "answerTextField");
                ChoiceBox<String> typeTextField = (ChoiceBox<String>) getPrivateField(controller, "typeTextField");
                TextField scoreTextField = (TextField) getPrivateField(controller, "scoreTextField");

                Question question = new Question("Sample Question", new String[]{"Option A", "Option B", "Option C", "Option D"}, "A", "Single", "10");
                questionTableView.setItems(FXCollections.observableArrayList(question));
                questionTableView.getSelectionModel().select(question);

                questionTextField.setText("Updated Question");
                optionATextField.setText("Updated Option A");
                optionBTextField.setText("Updated Option B");
                optionCTextField.setText("Updated Option C");
                optionDTextField.setText("Updated Option D");
                answerTextField.setText("B");
                typeTextField.setValue("Multiple");
                scoreTextField.setText("20");

                // Act
                controller.handleUpdateButton();

                // Assert
                Mockito.verify(questionService).updateQuestion(Mockito.eq(question.getId()), Mockito.any(Question.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleDeleteButton() throws Exception {
        new FXBlock(() -> {
            try {
                TableView<Question> questionTableView = (TableView<Question>) getPrivateField(controller, "questionTableView");

                Question question = new Question("Sample Question", new String[]{"Option A", "Option B", "Option C", "Option D"}, "A", "Single", "10");
                questionTableView.setItems(FXCollections.observableArrayList(question));
                questionTableView.getSelectionModel().select(question);

                // Act
                controller.handleDeleteButton();

                // Assert
                Mockito.verify(questionService).deleteQuestion(Mockito.eq(question.getId()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }
}