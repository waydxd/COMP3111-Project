package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class TeacherLoginControllerTest {

    private TeacherLoginController controller;
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
        controller = new TeacherLoginController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField());
                setPrivateField(controller, "passwordTxt", new PasswordField());
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

    private Object getPrivateField(Object object, String fieldName) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    @Test
    void testAccountExist() throws Exception {
        new FXBlock(() -> {
            try {
                // Arrange
                Teacher teacher1 = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
                Teacher teacher2 = new Teacher("teacher2", "password2", "John Smith", "Male", "40", "EE", "Professor");
                List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
                Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);
                controller.setTeacherList(teacherService.getAllTeachers());

                // Act and Assert
                assertTrue(controller.account_exist("teacher1"));
                assertTrue(controller.account_exist("teacher2"));
                assertFalse(controller.account_exist("non_existing_user"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testGetTeacherByUsername() throws Exception {
        new FXBlock(() -> {
            try {
                // Arrange
                Teacher teacher = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
                Mockito.when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher));
                controller.setTeacherList(teacherService.getAllTeachers());

                // Act
                Teacher retrievedTeacher = controller.getTeacherbyUserName("teacher1");

                // Assert
                assertNotNull(retrievedTeacher);
                assertEquals("teacher1", retrievedTeacher.getUsername());
                assertEquals("Jane Doe", retrievedTeacher.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testCheckLogin() throws Exception {
        new FXBlock(() -> {
            try {
                // Arrange
                Teacher teacher = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
                Mockito.when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher));
                controller.setTeacherList(teacherService.getAllTeachers());

                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");

                // Act and Assert
                Mockito.when(controller.getTeacherbyUserName("teacher1")).thenReturn(teacher);
                Mockito.when(teacher.Check_password("password1")).thenReturn(true);
                usernameTxt.setText("teacher1");
                passwordTxt.setText("password1");
                assertTrue(controller.Check_login());

                Mockito.when(teacher.Check_password("wrongPassword")).thenReturn(false);
                usernameTxt.setText("teacher1");
                passwordTxt.setText("wrongPassword");
                assertFalse(controller.Check_login());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testLogin() throws Exception {
        new FXBlock(() -> {
            try {
                // Arrange
                Teacher teacher = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
                Mockito.when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher));
                controller.setTeacherList(teacherService.getAllTeachers());

                TextField usernameTxt = (TextField) getPrivateField(controller, "usernameTxt");
                PasswordField passwordTxt = (PasswordField) getPrivateField(controller, "passwordTxt");

                // Act and Assert
                Mockito.when(controller.Check_login()).thenReturn(true);
                usernameTxt.setText("teacher1");
                passwordTxt.setText("password1");
                assertDoesNotThrow(() -> controller.login(null));

                Mockito.when(controller.Check_login()).thenReturn(false);
                usernameTxt.setText("teacher1");
                passwordTxt.setText("wrongPassword");
                assertThrows(NullPointerException.class, () -> controller.login(null));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testRegister() throws Exception {
        new FXBlock(() -> {
            try {
                // Act
                controller.register();

                // Assert
                assertNotNull(controller.getRegisterStage());
                assertTrue(controller.getRegisterStage().isShowing());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }
}