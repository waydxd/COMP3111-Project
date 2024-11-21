package comp3111.examsystem.controller;


import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class TeacherRegisterControllerTest {
    private TeacherRegisterController controller;
    private TeacherService teacherService;

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
        teacherService = Mockito.mock(TeacherServiceImpl.class);
        controller = new TeacherRegisterController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField());
                setPrivateField(controller, "passwordTxt", new PasswordField());
                setPrivateField(controller, "nameTxt", new TextField());
                setPrivateField(controller, "Gender", new ChoiceBox<>());
                setPrivateField(controller, "ageTxt", new TextField());
                setPrivateField(controller, "Position", new ChoiceBox<>());
                setPrivateField(controller, "departmentTxt", new TextField());
                setPrivateField(controller, "passwordConfirmTxt", new PasswordField());
                setPrivateField(controller, "teacherLoginController", new TeacherLoginController());
                setPrivateField(controller, "teacherService", teacherService);
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
    void testRegisterWithValidInput() throws Exception {
        new FXBlock(() -> {
            try {
                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");
                TextField nameTxt = (TextField) getPrivateField(controller, "nameTxt");
                ChoiceBox<String> Gender = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                TextField ageTxt = (TextField) getPrivateField(controller, "ageTxt");
                ChoiceBox<String> Position = (ChoiceBox<String>) getPrivateField(controller, "Position");
                TextField departmentTxt = (TextField) getPrivateField(controller, "departmentTxt");
                PasswordField passwordConfirmTxt = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");

                usernameTxt.setText("testuser");
                passwordTxt.setText("testpassword");
                nameTxt.setText("Test User");
                Gender.setValue("Male");
                ageTxt.setText("30");
                Position.setValue("Professor");
                departmentTxt.setText("Computer Science");
                passwordConfirmTxt.setText("testpassword");

                doNothing().when(teacherService).addTeacher(Mockito.any(Teacher.class));

                // Act
                controller.register(new ActionEvent());

                // Assert
                Mockito.verify(teacherService).addTeacher(Mockito.any(Teacher.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testRegisterWithInvalidAge() throws Exception {
        new FXBlock(() -> {
            try {
                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");
                TextField nameTxt = (TextField) getPrivateField(controller, "nameTxt");
                ChoiceBox<String> Gender = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                TextField ageTxt = (TextField) getPrivateField(controller, "ageTxt");
                ChoiceBox<String> Position = (ChoiceBox<String>) getPrivateField(controller, "Position");
                TextField departmentTxt = (TextField) getPrivateField(controller, "departmentTxt");
                PasswordField passwordConfirmTxt = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");

                usernameTxt.setText("testuser");
                passwordTxt.setText("testpassword");
                nameTxt.setText("Test User");
                Gender.setValue("Male");
                ageTxt.setText("110");
                Position.setValue("Professor");
                departmentTxt.setText("Computer Science");
                passwordConfirmTxt.setText("testpassword");

                // Act
                controller.register(new ActionEvent());

                // Assert
                Mockito.verify(teacherService, Mockito.never()).addTeacher(Mockito.any(Teacher.class));
                // Verify that the error popup is displayed
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }
    @Test
    void testRegisterWithEmptyFields() throws Exception {
        new FXBlock(() -> {
            try {
                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");
                TextField nameTxt = (TextField) getPrivateField(controller, "nameTxt");
                ChoiceBox<String> Gender = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                TextField ageTxt = (TextField) getPrivateField(controller, "ageTxt");
                ChoiceBox<String> Position = (ChoiceBox<String>) getPrivateField(controller, "Position");
                TextField departmentTxt = (TextField) getPrivateField(controller, "departmentTxt");
                PasswordField passwordConfirmTxt = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");

                usernameTxt.setText("");
                passwordTxt.setText("testpassword");
                nameTxt.setText("");
                Gender.setValue("");
                ageTxt.setText("30");
                Position.setValue("");
                departmentTxt.setText("");
                passwordConfirmTxt.setText("testpassword");

                // Act
                controller.register(new ActionEvent());

                // Assert
                Mockito.verify(teacherService, Mockito.never()).addTeacher(Mockito.any(Teacher.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }
    @Test
    void testRegisterWithDifferentPasswords() throws Exception {
        new FXBlock(() -> {
            try {
                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");
                TextField nameTxt = (TextField) getPrivateField(controller, "nameTxt");
                ChoiceBox<String> Gender = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                TextField ageTxt = (TextField) getPrivateField(controller, "ageTxt");
                ChoiceBox<String> Position = (ChoiceBox<String>) getPrivateField(controller, "Position");
                TextField departmentTxt = (TextField) getPrivateField(controller, "departmentTxt");
                PasswordField passwordConfirmTxt = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");

                usernameTxt.setText("testuser");
                passwordTxt.setText("testpassword");
                nameTxt.setText("Test User");
                Gender.setValue("Male");
                ageTxt.setText("30");
                Position.setValue("Professor");
                departmentTxt.setText("Computer Science");
                passwordConfirmTxt.setText("differentpassword");

                // Act
                controller.register(new ActionEvent());

                // Assert
                Mockito.verify(teacherService, Mockito.never()).addTeacher(Mockito.any(Teacher.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }
    @Test
    void testRegisterWithExistingUsername() throws Exception {
        new FXBlock(() -> {
            try {
                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");
                TextField nameTxt = (TextField) getPrivateField(controller, "nameTxt");
                ChoiceBox<String> Gender = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                TextField ageTxt = (TextField) getPrivateField(controller, "ageTxt");
                ChoiceBox<String> Position = (ChoiceBox<String>) getPrivateField(controller, "Position");
                TextField departmentTxt = (TextField) getPrivateField(controller, "departmentTxt");
                PasswordField passwordConfirmTxt = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");

                usernameTxt.setText("existinguser");
                passwordTxt.setText("testpassword");
                nameTxt.setText("Test User");
                Gender.setValue("Male");
                ageTxt.setText("30");
                Position.setValue("Professor");
                departmentTxt.setText("Computer Science");
                passwordConfirmTxt.setText("testpassword");

                when(teacherService.account_exist(Mockito.anyString())).thenReturn(true);

                // Act
                controller.register(new ActionEvent());

                // Assert
                Mockito.verify(teacherService, Mockito.never()).addTeacher(Mockito.any(Teacher.class));

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