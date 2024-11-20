package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.CourseService;
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

class CourseManagementControllerTest {

    private CourseManagementController controller;
    private CourseService courseService;

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
        courseService = Mockito.mock(CourseService.class);
        controller = new CourseManagementController();

        new FXBlock(() -> {
            try {
                setPrivateField(controller, "courseIdFilter", new TextField());
                setPrivateField(controller, "courseNameFilter", new TextField());
                setPrivateField(controller, "departmentFilter", new TextField());
                setPrivateField(controller, "courseId", new TextField());
                setPrivateField(controller, "courseName", new TextField());
                setPrivateField(controller, "department", new TextField());
                setPrivateField(controller, "courseTable", new TableView<>());
                setPrivateField(controller, "courseIdColumn", new TableColumn<>());
                setPrivateField(controller, "courseNameColumn", new TableColumn<>());
                setPrivateField(controller, "departmentColumn", new TableColumn<>());
                setPrivateField(controller, "courseService", courseService);
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
                TextField courseId = (TextField) getPrivateField(controller, "courseId");
                TextField courseName = (TextField) getPrivateField(controller, "courseName");
                TextField department = (TextField) getPrivateField(controller, "department");

                courseId.setText("CS101");
                courseName.setText("Introduction to Computer Science");
                department.setText("CS");

                // Act
                controller.handleAdd();

                // Assert
                Mockito.verify(courseService).addCourse(Mockito.any(Course.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleUpdate() throws Exception {
        new FXBlock(() -> {
            try {
                TableView<Course> courseTable = (TableView<Course>) getPrivateField(controller, "courseTable");
                TextField courseId = (TextField) getPrivateField(controller, "courseId");
                TextField courseName = (TextField) getPrivateField(controller, "courseName");
                TextField department = (TextField) getPrivateField(controller, "department");

                Course course = new Course("CS101", "Introduction to Computer Science", "CS");
                courseTable.setItems(FXCollections.observableArrayList(course));
                courseTable.getSelectionModel().select(course);

                courseId.setText("CS102");
                courseName.setText("Data Structures");
                department.setText("CS");

                // Act
                controller.handleUpdate();

                // Assert
                Mockito.verify(courseService).updateCourse(Mockito.eq(course.getCourseCode()), Mockito.any(Course.class));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).run();
    }

    @Test
    void testHandleDelete() throws Exception {
        new FXBlock(() -> {
            try {
                TableView<Course> courseTable = (TableView<Course>) getPrivateField(controller, "courseTable");

                Course course = new Course("CS101", "Introduction to Computer Science", "CS");
                courseTable.setItems(FXCollections.observableArrayList(course));
                courseTable.getSelectionModel().select(course);

                // Act
                controller.handleDelete();

                // Assert
                Mockito.verify(courseService).deleteCourse(Mockito.eq(course.getCourseCode()));
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