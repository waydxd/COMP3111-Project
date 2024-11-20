package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import javafx.application.Platform;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
        teacherService = Mockito.mock(TeacherService.class); // Properly mock the TeacherService
        controller = new TeacherLoginController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "usernameTxt", new TextField());
                setPrivateField(controller, "passwordTxt", new PasswordField());
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
    void testAccountExist() throws Exception {
        // Arrange
        Teacher teacher1 = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
        Teacher teacher2 = new Teacher("teacher2", "password2", "John Smith", "Male", "40", "EE", "Professor");
        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
        Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);

        // Act and Assert
        new FXBlock(() -> {
            controller.setTeacherList(teacherService.getAllTeachers());
            Assertions.assertTrue(controller.account_exist("teacher1"));
            Assertions.assertTrue(controller.account_exist("teacher2"));
            Assertions.assertFalse(controller.account_exist("non_existing_user"));
        }).run();
    }

    @Test
    void testGetTeacherByUsername() throws Exception {
        // Arrange
        Teacher teacher = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
        Mockito.when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher));

        new FXBlock(() -> {
            controller.setTeacherList(teacherService.getAllTeachers());
            // Act
            Teacher retrievedTeacher = controller.getTeacherbyUserName("teacher1");

            // Assert
            Assertions.assertNotNull(retrievedTeacher);
            Assertions.assertEquals("teacher1", retrievedTeacher.getUsername());
            Assertions.assertEquals("Jane Doe", retrievedTeacher.getName());
        }).run();
    }

    @Test
    void testCheckLogin() throws Exception {
        // Arrange
        Teacher teacher = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
        Mockito.when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher));

        new FXBlock(() -> {
            controller.setTeacherList(teacherService.getAllTeachers());
            try {
                setPrivateField(controller, "usernameTxt", new TextField("teacher1"));
                setPrivateField(controller, "passwordTxt", new PasswordField());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Act and Assert
            Assertions.assertTrue(controller.Check_login());

            // Test with incorrect password
            try {
                setPrivateField(controller, "passwordTxt", new PasswordField());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Assertions.assertFalse(controller.Check_login());

            // Test with non-existing user
            try {
                setPrivateField(controller, "usernameTxt", new TextField("non_existing_user"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Assertions.assertFalse(controller.Check_login());
        }).run();
    }
}