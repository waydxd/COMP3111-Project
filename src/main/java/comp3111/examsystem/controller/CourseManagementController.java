package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.CourseService;
import comp3111.examsystem.service.internal.CourseServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseManagementController implements Initializable {
    @FXML
    private TextField courseIdFilter;
    @FXML
    private TextField courseNameFilter;
    @FXML
    private TextField departmentFilter;
    @FXML
    private TextField courseId;
    @FXML
    private TextField courseName;
    @FXML
    private TextField department;
    @FXML
    private TableView<Course> courseTable;
    @FXML
    private TableColumn<Course, String> courseIdColumn;
    @FXML
    private TableColumn<Course, String> courseNameColumn;
    @FXML
    private TableColumn<Course, String> departmentColumn;

    private final CourseService courseService = new CourseServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        courseTable.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        courseTable.refresh();

        courseTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                courseId.setText(newValue.getCourseCode());
                courseName.setText(newValue.getName());
                department.setText(newValue.getDepartment());
            }
        });
    }

    @FXML
    private void handleRefresh() {
        courseTable.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        courseTable.refresh();
    }

    @FXML
    private void handleReset() {
        courseIdFilter.clear();
        courseNameFilter.clear();
        departmentFilter.clear();
        handleRefresh();
    }

    @FXML
    private void handleFilter() {
        String filterCourseId = courseIdFilter.getText() != null ? courseIdFilter.getText().toLowerCase() : "";
        String filterCourseName = courseNameFilter.getText() != null ? courseNameFilter.getText().toLowerCase() : "";
        String filterDepartment = departmentFilter.getText() != null ? departmentFilter.getText().toLowerCase() : "";

        courseTable.setItems(FXCollections.observableArrayList(
                courseService.getAllCourses().stream()
                        .filter(course -> filterCourseId.isEmpty() || course.getCourseCode().toLowerCase().contains(filterCourseId))
                        .filter(course -> filterCourseName.isEmpty() || course.getName().toLowerCase().contains(filterCourseName))
                        .filter(course -> filterDepartment.isEmpty() || course.getDepartment().toLowerCase().contains(filterDepartment))
                        .toList()
        ));
        courseTable.refresh();
    }

    @FXML
    private void handleAdd() {
        try {
            courseService.addCourse(new Course(courseId.getText(), courseName.getText(), department.getText()));
            handleRefresh();
        } catch (org.jooq.exception.DataAccessException e) {
            if (e.getCause() instanceof org.sqlite.SQLiteException && e.getCause().getMessage().contains("UNIQUE constraint failed")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Course ID already exists");
                alert.showAndWait();
            } else {
                throw e;
            }
        }
    }

    @FXML
    private void handleUpdate() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseService.updateCourse(selectedCourse.getCourseCode() ,new Course(courseId.getText(), courseName.getText(), department.getText()));
            handleRefresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to update");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleDelete() {
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            courseService.deleteCourse(selectedCourse.getCourseCode());
            handleRefresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to delete");
            alert.showAndWait();
        }
    }
}