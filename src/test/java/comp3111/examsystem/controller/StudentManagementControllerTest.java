package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentService;
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

class StudentManagementControllerTest {

    private StudentManagementController controller;
    private StudentService studentService;

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
        studentService = Mockito.mock(StudentService.class);
        controller = new StudentManagementController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "filterUsernameField", new TextField());
                setPrivateField(controller, "filterNameField", new TextField());
                setPrivateField(controller, "filterDepartmentField", new TextField());
                setPrivateField(controller, "usernameField", new TextField());
                setPrivateField(controller, "nameField", new TextField());
                setPrivateField(controller, "ageField", new TextField());
                setPrivateField(controller, "genderComboBox", new ComboBox<>());
                setPrivateField(controller, "departmentField", new TextField());
                setPrivateField(controller, "passwordField", new TextField());
                setPrivateField(controller, "studentTable", new TableView<>());
                setPrivateField(controller, "usernameColumn", new TableColumn<>());
                setPrivateField(controller, "nameColumn", new TableColumn<>());
                setPrivateField(controller, "ageColumn", new TableColumn<>());
                setPrivateField(controller, "genderColumn", new TableColumn<>());
                setPrivateField(controller, "departmentColumn", new TableColumn<>());
                setPrivateField(controller, "passwordColumn", new TableColumn<>());
                setPrivateField(controller, "studentService", studentService);
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
    void testHandleAdd() throws Exception {
        new FXBlock(() -> {
            try {
                TextField usernameField = (TextField) getPrivateField(controller, "usernameField");
                TextField nameField = (TextField) getPrivateField(controller, "nameField");
                TextField ageField = (TextField) getPrivateField(controller, "ageField");
                ComboBox<String> genderComboBox = (ComboBox<String>) getPrivateField(controller, "genderComboBox");
                TextField departmentField = (TextField) getPrivateField(controller, "departmentField");
                TextField passwordField = (TextField) getPrivateField(controller, "passwordField");

                usernameField.setText("student1");
                nameField.setText("Student One");
                ageField.setText("20");
                genderComboBox.setValue("Male");
                departmentField.setText("CS");
                passwordField.setText("password");

                // Act
                controller.handleAdd();

                // Assert
                Mockito.verify(studentService).addStudent(Mockito.any(Student.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleUpdate() throws Exception {
        new FXBlock(() -> {
            try {
                TableView<Student> studentTable = (TableView<Student>) getPrivateField(controller, "studentTable");
                TextField usernameField = (TextField) getPrivateField(controller, "usernameField");
                TextField nameField = (TextField) getPrivateField(controller, "nameField");
                TextField ageField = (TextField) getPrivateField(controller, "ageField");
                ComboBox<String> genderComboBox = (ComboBox<String>) getPrivateField(controller, "genderComboBox");
                TextField departmentField = (TextField) getPrivateField(controller, "departmentField");
                TextField passwordField = (TextField) getPrivateField(controller, "passwordField");

                Student student = new Student("student1", "password", "Student One", "Male", "20", "CS");
                studentTable.setItems(FXCollections.observableArrayList(student));
                studentTable.getSelectionModel().select(student);

                usernameField.setText("updatedStudent");
                nameField.setText("Updated Student");
                ageField.setText("21");
                genderComboBox.setValue("Female");
                departmentField.setText("IT");
                passwordField.setText("newpassword");

                // Act
                controller.handleUpdate();

                // Assert
                Mockito.verify(studentService).updateStudent(Mockito.eq(student.getId()), Mockito.any(Student.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleDelete() throws Exception {
        new FXBlock(() -> {
            try {
                TableView<Student> studentTable = (TableView<Student>) getPrivateField(controller, "studentTable");

                Student student = new Student("student1", "password", "Student One", "Male", "20", "CS");
                studentTable.setItems(FXCollections.observableArrayList(student));
                studentTable.getSelectionModel().select(student);

                // Act
                controller.handleDelete();

                // Assert
                Mockito.verify(studentService).deleteStudent(Mockito.eq(student.getId()));
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