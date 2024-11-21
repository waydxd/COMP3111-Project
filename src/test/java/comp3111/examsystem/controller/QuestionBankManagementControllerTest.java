package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.internal.QuestionServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        questionService = Mockito.mock(QuestionServiceImpl.class);
        controller = new QuestionBankManagementController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "mainBorderPane", new BorderPane());
                setPrivateField(controller, "questionTableView", new TableView<>());
                setPrivateField(controller, "QuestionColumn", new TableColumn<>());
                setPrivateField(controller, "OptionAColumn", new TableColumn<>());
                setPrivateField(controller, "OptionBColumn", new TableColumn<>());
                setPrivateField(controller, "OptionCColumn", new TableColumn<>());
                setPrivateField(controller, "OptionDColumn", new TableColumn<>());
                setPrivateField(controller, "AnswerColumn", new TableColumn<>());
                setPrivateField(controller, "TypeColumn", new TableColumn<>());
                setPrivateField(controller, "ScoreColumn", new TableColumn<>());
                setPrivateField(controller, "questionFilter", new TextField());
                setPrivateField(controller, "typeFilterTextField", new ChoiceBox<>());
                setPrivateField(controller, "scoreFilterTextField", new TextField());
                setPrivateField(controller, "questionTextField", new TextField());
                setPrivateField(controller, "optionATextField", new TextField());
                setPrivateField(controller, "optionBTextField", new TextField());
                setPrivateField(controller, "optionCTextField", new TextField());
                setPrivateField(controller, "optionDTextField", new TextField());
                setPrivateField(controller, "answerTextField", new TextField());
                setPrivateField(controller, "typeTextField", new ChoiceBox<>());
                setPrivateField(controller, "scoreTextField", new TextField());
                setPrivateField(controller, "questionService", new QuestionServiceImpl());
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
    void testHandleAddButton_validInput() throws Exception {
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

                questionTextField.setText("What is the capital of France?");
                optionATextField.setText("Paris");
                optionBTextField.setText("London");
                optionCTextField.setText("Berlin");
                optionDTextField.setText("Rome");
                answerTextField.setText("A");
                typeTextField.getSelectionModel().select("Single");
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
    void testHandleAddButton_invalidInput() throws Exception {
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

                questionTextField.clear();
                optionATextField.clear();
                optionBTextField.clear();
                optionCTextField.clear();
                optionDTextField.clear();
                answerTextField.clear();
                typeTextField.getSelectionModel().select("Type");
                scoreTextField.clear();

                // Act and Assert
                assertThrows(RuntimeException.class, () -> controller.handleAddButton());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleUpdateButton_validInput() throws Exception {
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

                Question selectedQuestion = new Question(
                        "What is the capital of France?",
                        new String[]{"Paris", "London", "Berlin", "Rome"},
                        "A",
                        "Single",
                        "10"
                );
                questionTableView.setItems(FXCollections.observableArrayList(selectedQuestion));
                questionTableView.getSelectionModel().select(selectedQuestion);

                questionTextField.setText("What is the capital of Italy?");
                optionATextField.setText("Paris");
                optionBTextField.setText("London");
                optionCTextField.setText("Rome");
                optionDTextField.setText("Berlin");
                answerTextField.setText("C");
                typeTextField.getSelectionModel().select("Single");
                scoreTextField.setText("15");

                // Act
                controller.handleUpdateButton();

                // Assert
                Mockito.verify(questionService).updateQuestion(Mockito.eq(selectedQuestion.getId()), Mockito.any(Question.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleUpdateButton_invalidInput() throws Exception {
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

                Question selectedQuestion = new Question(
                        "What is the capital of France?",
                        new String[]{"Paris", "London", "Berlin", "Rome"},
                        "A",
                        "Single",
                        "10"
                );
                questionTableView.setItems(FXCollections.observableArrayList(selectedQuestion));
                questionTableView.getSelectionModel().select(selectedQuestion);

                questionTextField.clear();
                optionATextField.clear();
                optionBTextField.clear();
                optionCTextField.clear();
                optionDTextField.clear();
                answerTextField.clear();
                typeTextField.getSelectionModel().select("Type");
                scoreTextField.clear();

                // Act and Assert
                assertThrows(RuntimeException.class, () -> controller.handleUpdateButton());
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

                Question selectedQuestion = new Question(
                        "What is the capital of France?",
                        new String[]{"Paris", "London", "Berlin", "Rome"},
                        "A",
                        "Single",
                        "10"
                );
                questionTableView.setItems(FXCollections.observableArrayList(selectedQuestion));
                questionTableView.getSelectionModel().select(selectedQuestion);

                // Act
                controller.handleDeleteButton();

                // Assert
                Mockito.verify(questionService).deleteQuestion(Mockito.eq(selectedQuestion.getId()));
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