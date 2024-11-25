package comp3111.examsystem.controller;

import comp3111.examsystem.service.StudentService;
import comp3111.examsystem.entity.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentLoginControllerTest {

    private StudentLoginController controller;
    private StudentService studentService;
    private MockedStatic<ErrorPopupController> mockedErrorPopupController;
    private MockedStatic<SuccessPopupController> mockedSuccessPopupController;

    @BeforeAll
    public static void initToolkit() {
        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized, continue with the tests
        }
        Platform.setImplicitExit(false);
    }

    @BeforeEach
    public void setUp() throws Exception {
        studentService = mock(StudentService.class);
        controller = new StudentLoginController();

        // Use reflection to set private fields
        setPrivateField(controller, "usernameTxt", new TextField());
        setPrivateField(controller, "passwordTxt", new PasswordField());
        setPrivateField(controller, "errorLabel", new Label());

        // Mock the ErrorPopupController and SuccessPopupController to avoid showing popups
        mockedErrorPopupController = mockStatic(ErrorPopupController.class);
        mockedSuccessPopupController = mockStatic(SuccessPopupController.class);
    }

    @AfterEach
    public void tearDown() {
        if (mockedErrorPopupController != null) {
            mockedErrorPopupController.close();
        }
        if (mockedSuccessPopupController != null) {
            mockedSuccessPopupController.close();
        }
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

    @Test
    public void testAccountExists() {
        String username = "testUser";
        Student student = new Student(username, "testPass", "Test Name", "Male", "20", "CS");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        assertTrue(controller.accountExists(username));
        assertFalse(controller.accountExists("nonExistentUser"));
    }

    @Test
    public void testGetStudentByUsername() {
        String username = "testUser";
        Student student = new Student(username, "testPass", "Test Name", "Male", "20", "CS");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        assertEquals(student, controller.getStudentByUsername(username));
        assertNull(controller.getStudentByUsername("nonExistentUser"));
    }

    @Test
    public void testCheckLogin() {
        String username = "testUser";
        String password = "testPass";
        Student student = new Student(username, password, "Test Name", "Male", "20", "CS");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        try {
            ((TextField) getPrivateField(controller, "usernameTxt")).setText(username);
            ((PasswordField) getPrivateField(controller, "passwordTxt")).setText(password);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to set private fields");
        }

        assertTrue(controller.checkLogin());

        try {
            ((PasswordField) getPrivateField(controller, "passwordTxt")).setText("wrongPass");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to set private fields");
        }

        assertFalse(controller.checkLogin());
    }

    @Test
    public void testLogin() {
        String username = "testUser";
        String password = "testPass";
        Student student = new Student(username, password, "Test Name", "Male", "20", "CS");

        when(studentService.getAllStudents()).thenReturn(List.of(student));
        when(studentService.login(username, password)).thenReturn(1);

        try {
            ((TextField) getPrivateField(controller, "usernameTxt")).setText(username);
            ((PasswordField) getPrivateField(controller, "passwordTxt")).setText(password);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to set private fields");
        }

        controller.login(new ActionEvent());

        mockedSuccessPopupController.verify(() -> SuccessPopupController.Success_Popup("Registration successful!"));
        try {
            assertNull(((Label) getPrivateField(controller, "errorLabel")).getText());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to get private field");
        }
    }

    @Test
    public void testLoginWithIncorrectId() {
        String username = "wrongUser";
        String password = "testPass";

        when(studentService.getAllStudents()).thenReturn(List.of());

        try {
            ((TextField) getPrivateField(controller, "usernameTxt")).setText(username);
            ((PasswordField) getPrivateField(controller, "passwordTxt")).setText(password);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to set private fields");
        }

        controller.login(new ActionEvent());

        mockedErrorPopupController.verify(() -> ErrorPopupController.Error_Popup("Username/Password incorrect."));
    }

    @Test
    public void testLoginWithIncorrectPassword() {
        String username = "testUser";
        String password = "wrongPass";
        Student student = new Student(username, "testPass", "Test Name", "Male", "20", "CS");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        try {
            ((TextField) getPrivateField(controller, "usernameTxt")).setText(username);
            ((PasswordField) getPrivateField(controller, "passwordTxt")).setText(password);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to set private fields");
        }

        controller.login(new ActionEvent());

        mockedErrorPopupController.verify(() -> ErrorPopupController.Error_Popup("Username/Password incorrect."));
    }

    @Test
    public void testLoginWithEmptyFields() {
        try {
            ((TextField) getPrivateField(controller, "usernameTxt")).setText("");
            ((PasswordField) getPrivateField(controller, "passwordTxt")).setText("");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to set private fields");
        }

        controller.login(new ActionEvent());

        mockedErrorPopupController.verify(() -> ErrorPopupController.Error_Popup("Please fill in all required fields."));
    }
}