package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;

class StudentRegisterControllerTest {

    private StudentRegisterController controller;
    private StudentService studentService;

    @BeforeAll
    static void initToolkit() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @BeforeEach
    void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                studentService = Mockito.mock(StudentService.class);
                controller = new StudentRegisterController();
                setPrivateField(controller, "usernameTxt", new TextField());
                setPrivateField(controller, "passwordTxt", new PasswordField());
                setPrivateField(controller, "passwordConfirmTxt", new PasswordField());
                setPrivateField(controller, "nameTxt", new TextField());
                setPrivateField(controller, "Gender", new ChoiceBox<>(FXCollections.observableArrayList("Male", "Female", "Other")));
                setPrivateField(controller, "ageTxt", new TextField());
                setPrivateField(controller, "departmentTxt", new TextField());
                setPrivateField(controller, "studentService", studentService);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
        });
        latch.await();
    }

    @Test
    void testRegister_AllFieldsEmpty() throws Exception {
        new FXBlock(() -> {
            try (MockedStatic<ErrorPopupController> mockedStatic = mockStatic(ErrorPopupController.class)) {
                controller.register();
                mockedStatic.verify(() -> ErrorPopupController.Error_Popup("Please fill in all required fields."));
            }
        }).run();
    }

    @Test
    void testRegister_PasswordsDoNotMatch() throws Exception {
        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField("testUser"));
                PasswordField passwordField = (PasswordField) getPrivateField(controller, "passwordTxt");
                passwordField.setText("password");
                PasswordField passwordConfirmField = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");
                passwordConfirmField.setText("differentPassword");
                setPrivateField(controller, "nameTxt", new TextField("Test User"));
                ChoiceBox<String> genderChoiceBox = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                genderChoiceBox.setValue("Male");
                setPrivateField(controller, "ageTxt", new TextField("20"));
                setPrivateField(controller, "departmentTxt", new TextField("CS"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try (MockedStatic<ErrorPopupController> mockedStatic = mockStatic(ErrorPopupController.class)) {
                controller.register();
                mockedStatic.verify(() -> ErrorPopupController.Error_Popup("Passwords do not match."));
            }
        }).run();
    }

    @Test
    void testRegister_UsernameAlreadyExists() throws Exception {
        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField("testUser"));
                PasswordField passwordField = (PasswordField) getPrivateField(controller, "passwordTxt");
                passwordField.setText("password");
                PasswordField passwordConfirmField = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");
                passwordConfirmField.setText("password");
                setPrivateField(controller, "nameTxt", new TextField("Test User"));
                ChoiceBox<String> genderChoiceBox = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                genderChoiceBox.setValue("Male");
                setPrivateField(controller, "ageTxt", new TextField("20"));
                setPrivateField(controller, "departmentTxt", new TextField("CS"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            when(studentService.getAllStudents()).thenReturn(Collections.singletonList(new Student("testUser", "password", "Test User", "Male", "20", "CS")));
            try (MockedStatic<ErrorPopupController> mockedStatic = mockStatic(ErrorPopupController.class)) {
                controller.register();
                mockedStatic.verify(() -> ErrorPopupController.Error_Popup("Username already exists."));
            }
        }).run();
    }

    @Test
    void testRegister_Success() throws Exception {
        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField("newUser"));
                PasswordField passwordField = (PasswordField) getPrivateField(controller, "passwordTxt");
                passwordField.setText("password");
                PasswordField passwordConfirmField = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");
                passwordConfirmField.setText("password");
                setPrivateField(controller, "nameTxt", new TextField("New User"));
                ChoiceBox<String> genderChoiceBox = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                genderChoiceBox.setValue("Male");
                setPrivateField(controller, "ageTxt", new TextField("20"));
                setPrivateField(controller, "departmentTxt", new TextField("CS"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            when(studentService.getAllStudents()).thenReturn(Collections.emptyList());
            try (MockedStatic<SuccessPopupController> mockedStatic = mockStatic(SuccessPopupController.class)) {
                controller.register();
                verify(studentService).addStudent(any(Student.class));
                mockedStatic.verify(() -> SuccessPopupController.Success_Popup("Registration successful!"));
            }
        }).run();
    }

    @Test
    void testRegister_InvalidAge() throws Exception {
        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField("testUser"));
                PasswordField passwordField = (PasswordField) getPrivateField(controller, "passwordTxt");
                passwordField.setText("password");
                PasswordField passwordConfirmField = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");
                passwordConfirmField.setText("password");
                setPrivateField(controller, "nameTxt", new TextField("Test User"));
                ChoiceBox<String> genderChoiceBox = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                genderChoiceBox.setValue("Male");
                setPrivateField(controller, "ageTxt", new TextField("invalidAge"));
                setPrivateField(controller, "departmentTxt", new TextField("CS"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try (MockedStatic<ErrorPopupController> mockedStatic = mockStatic(ErrorPopupController.class)) {
                controller.register();
                mockedStatic.verify(() -> ErrorPopupController.Error_Popup("Invalid age. Please enter a valid number."));
            }
        }).run();
    }

    @Test
    void testRegister_InvalidAgeNumber() throws Exception {
        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField("testUser"));
                PasswordField passwordField = (PasswordField) getPrivateField(controller, "passwordTxt");
                passwordField.setText("password");
                PasswordField passwordConfirmField = (PasswordField) getPrivateField(controller, "passwordConfirmTxt");
                passwordConfirmField.setText("password");
                setPrivateField(controller, "nameTxt", new TextField("Test User"));
                ChoiceBox<String> genderChoiceBox = (ChoiceBox<String>) getPrivateField(controller, "Gender");
                genderChoiceBox.setValue("Male");
                setPrivateField(controller, "ageTxt", new TextField("-1"));
                setPrivateField(controller, "departmentTxt", new TextField("CS"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try (MockedStatic<ErrorPopupController> mockedStatic = mockStatic(ErrorPopupController.class)) {
                controller.register();
                mockedStatic.verify(() -> ErrorPopupController.Error_Popup("Invalid age. Age must be between 1 and 100."));
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