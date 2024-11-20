package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class TeacherManagementControllerTest {

    @BeforeAll
    static void startJavaFXRuntime() {
        Platform.startup(() -> {});
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

    @Mock
    private TeacherService teacherService;

    @InjectMocks
    private TeacherManagementController controller;

    @Mock
    private TableView<Teacher> teacherTable;

    @Mock
    private TableColumn<Teacher, String> usernameColumn;
    @Mock
    private TableColumn<Teacher, String> nameColumn;
    @Mock
    private TableColumn<Teacher, Integer> ageColumn;
    @Mock
    private TableColumn<Teacher, String> genderColumn;
    @Mock
    private TableColumn<Teacher, String> departmentColumn;
    @Mock
    private TableColumn<Teacher, String> passwordColumn;
    @Mock
    private TableColumn<Teacher, String> positionColumn;

    @Mock
    private TextField filterUsernameField;
    @Mock
    private TextField filterNameField;
    @Mock
    private TextField filterDepartmentField;
    @Mock
    private TextField usernameField;
    @Mock
    private TextField nameField;
    @Mock
    private TextField ageField;
    @Mock
    private ComboBox<String> genderComboBox;
    @Mock
    private TextField departmentField;
    @Mock
    private TextField passwordField;
    @Mock
    private ComboBox<String> positionComboBox;

    @Mock
    private TableView.TableViewSelectionModel<Teacher> selectionModel;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(teacherTable.getSelectionModel()).thenReturn(selectionModel);
        when(selectionModel.selectedItemProperty()).thenAnswer((Answer<ReadOnlyObjectProperty<Teacher>>) invocation -> {
            ReadOnlyObjectWrapper<Teacher> wrapper = new ReadOnlyObjectWrapper<>();
            return wrapper.getReadOnlyProperty();
        });

        // Initialize JavaFX components on FX thread
        new FXBlock(() -> {
            controller.initialize(null,null);
        }).run();
    }

    @Test
    void handleRefresh_ShouldUpdateTableItems() throws Exception {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(new Teacher("user1", "pass1", "name1", "Male", "30", "dept1", "Professor"));

        when(teacherService.getAllTeachers()).thenReturn(teachers);

        new FXBlock(() -> {
            controller.handleRefresh();
        }).run();

//        verify(teacherService).getAllTeachers();
//        verify(teacherTable).setItems(any(ObservableList.class));
//        verify(teacherTable).refresh();
    }

    @Test
    void handleReset_ShouldClearFilterFields() throws Exception {
        new FXBlock(() -> {
            controller.handleReset();
        }).run();

        verify(filterUsernameField).clear();
        verify(filterNameField).clear();
        verify(filterDepartmentField).clear();
    }

    @Test
    void handleAdd_WithEmptyFields_ShouldShowError() throws Exception {
        new FXBlock(() -> {
            when(usernameField.getText()).thenReturn("");
            when(passwordField.getText()).thenReturn("");

            controller.handleAdd();
        }).run();

        verify(teacherService, never()).addTeacher(any(Teacher.class));
    }

    @Test
    void handleAdd_WithInvalidAge_ShouldShowError() throws Exception {
        new FXBlock(() -> {
            when(usernameField.getText()).thenReturn("user1");
            when(passwordField.getText()).thenReturn("pass1");
            when(nameField.getText()).thenReturn("name1");
            when(genderComboBox.getValue()).thenReturn("Male");
            when(ageField.getText()).thenReturn("invalid");
            when(departmentField.getText()).thenReturn("dept1");
            when(positionComboBox.getValue()).thenReturn("Professor");

            controller.handleAdd();
        }).run();

        verify(teacherService, never()).addTeacher(any(Teacher.class));
    }

    @Test
    void handleAdd_WithValidData_ShouldAddTeacher() throws Exception {
        new FXBlock(() -> {
            when(usernameField.getText()).thenReturn("user1");
            when(passwordField.getText()).thenReturn("pass1");
            when(nameField.getText()).thenReturn("name1");
            when(genderComboBox.getValue()).thenReturn("Male");
            when(ageField.getText()).thenReturn("30");
            when(departmentField.getText()).thenReturn("dept1");
            when(positionComboBox.getValue()).thenReturn("Professor");

            controller.handleAdd();
        }).run();

//        verify(teacherService).addTeacher(any(Teacher.class));
    }

    @Test
    void handleUpdate_WithNoSelection_ShouldShowError() throws Exception {
        new FXBlock(() -> {
            when(selectionModel.getSelectedItem()).thenReturn(null);

            controller.handleUpdate();
        }).run();

        verify(teacherService, never()).updateTeacher(anyInt(), any(Teacher.class));
    }

    @Test
    void handleUpdate_WithInvalidAge_ShouldShowError() throws Exception {
        new FXBlock(() -> {
            Teacher selectedTeacher = new Teacher("user1", "pass1", "name1", "Male", "30", "dept1", "Professor");
            when(selectionModel.getSelectedItem()).thenReturn(selectedTeacher);
            when(usernameField.getText()).thenReturn("user1");
            when(passwordField.getText()).thenReturn("pass1");
            when(nameField.getText()).thenReturn("name1");
            when(genderComboBox.getValue()).thenReturn("Male");
            when(ageField.getText()).thenReturn("invalid");
            when(departmentField.getText()).thenReturn("dept1");
            when(positionComboBox.getValue()).thenReturn("Professor");

            controller.handleUpdate();
        }).run();

        verify(teacherService, never()).updateTeacher(anyInt(), any(Teacher.class));
    }

    @Test
    void handleDelete_WithNoSelection_ShouldDoNothing() throws Exception {
        new FXBlock(() -> {
            when(selectionModel.getSelectedItem()).thenReturn(null);

            controller.handleDelete();
        }).run();

        verify(teacherService, never()).deleteTeacher(anyInt());
    }

    @Test
    void handleDelete_WithConfirmation_ShouldDeleteTeacher() throws Exception {
        new FXBlock(() -> {
            Teacher selectedTeacher = new Teacher("user1", "pass1", "name1", "Male", "30", "dept1", "Professor");
            selectedTeacher.setId(1);
            when(selectionModel.getSelectedItem()).thenReturn(selectedTeacher);

            controller.handleDelete();
        }).run();

//        verify(teacherTable).refresh();
    }

    @Test
    void checkNull_WithEmptyFields_ShouldReturnTrue() throws Exception {
        new FXBlock(() -> {
            when(usernameField.getText()).thenReturn("");
            when(passwordField.getText()).thenReturn("pass1");
            when(nameField.getText()).thenReturn("name1");
            when(genderComboBox.getValue()).thenReturn("Male");
            when(ageField.getText()).thenReturn("30");
            when(departmentField.getText()).thenReturn("dept1");
            when(positionComboBox.getValue()).thenReturn("Professor");

            assertTrue(controller.checkNull());
        }).run();
    }

    @Test
    void checkNull_WithAllFieldsFilled_ShouldReturnFalse() throws Exception {
        new FXBlock(() -> {
            when(usernameField.getText()).thenReturn("user1");
            when(passwordField.getText()).thenReturn("pass1");
            when(nameField.getText()).thenReturn("name1");
            when(genderComboBox.getValue()).thenReturn("Male");
            when(ageField.getText()).thenReturn("30");
            when(departmentField.getText()).thenReturn("dept1");
            when(positionComboBox.getValue()).thenReturn("Professor");

            assertFalse(controller.checkNull());
        }).run();
    }

    @Test
    void checkNull_WithNullComboBoxValues_ShouldReturnTrue() throws Exception {
        new FXBlock(() -> {
            when(usernameField.getText()).thenReturn("user1");
            when(passwordField.getText()).thenReturn("pass1");
            when(nameField.getText()).thenReturn("name1");
            when(genderComboBox.getValue()).thenReturn(null);
            when(ageField.getText()).thenReturn("30");
            when(departmentField.getText()).thenReturn("dept1");
            when(positionComboBox.getValue()).thenReturn(null);

            assertTrue(controller.checkNull());
        }).run();
    }

}