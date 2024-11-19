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
/**
 * The controller for the course management UI.
 */
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

    /**
     * The CourseService instance used to interact with the course data.
     */
    private final CourseService courseService = new CourseServiceImpl();

    /**
     * Initializes the course management UI.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     *                  <p>
     *                  The method does the following:
     *                  1. Initializes the course table with the course data.
     *                  2. Adds a listener to the course table to update the course details when a course is selected.
     *                  </p>
     */
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

    /**
     * Handles the refresh button click event.
     * <p>
     *     This method refreshes the course table with the latest course data fetched from database.
     * <p/>
     */
    @FXML
    private void handleRefresh() {
        courseTable.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        courseTable.refresh();
    }

    /**
     * Handles the reset button click event.
     * <p>
     *     This method clears the filter text fields and refreshes the course table.
     *     The course table is then refreshed.
     * </p>
     */
    @FXML
    private void handleReset() {
        courseIdFilter.clear();
        courseNameFilter.clear();
        departmentFilter.clear();
    }

    /**
     * Handles the filter button click event.
     * <p>
     *     This method filters the course table based on the values in the filter text fields.
     *     The course table is then refreshed.
     *     The filter is case-insensitive.
     * </p>
     */
    @FXML
    private void handleFilter() {
        // Get the filter text fields and check if they are null
        String filterCourseId = courseIdFilter.getText() != null ? courseIdFilter.getText().toLowerCase() : "";
        String filterCourseName = courseNameFilter.getText() != null ? courseNameFilter.getText().toLowerCase() : "";
        String filterDepartment = departmentFilter.getText() != null ? departmentFilter.getText().toLowerCase() : "";

        // Filter the fetched courses based on the filter text fields
        courseTable.setItems(FXCollections.observableArrayList(
                courseService.getAllCourses().stream()
                        .filter(course -> filterCourseId.isEmpty() || course.getCourseCode().toLowerCase().contains(filterCourseId))
                        .filter(course -> filterCourseName.isEmpty() || course.getName().toLowerCase().contains(filterCourseName))
                        .filter(course -> filterDepartment.isEmpty() || course.getDepartment().toLowerCase().contains(filterDepartment))
                        .toList()
        ));
        courseTable.refresh();
    }

    /**
     * Handles the add button click event.
     * <p>
     *     This method adds a new course to the course table with the values in the text fields.
     *     If the course ID already exists, an error message is displayed.
     *     Otherwise, the course is added to the course table.
     *     The course table is then refreshed.
     * </p>
     */
    @FXML
    private void handleAdd() {
        // Check if any of the text fields are empty
        if (nullCheck()) return;
        try {
            // Add the course
            courseService.addCourse(new Course(courseId.getText(), courseName.getText(), department.getText()));
            // Refresh the course table
            handleRefresh();
        } catch (org.jooq.exception.DataAccessException e) {
            // catch the exception if the course ID already exists
            if (e.getCause() instanceof org.sqlite.SQLiteException && e.getCause().getMessage().contains("UNIQUE constraint failed")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Course ID already exists");
                alert.showAndWait();
            } else {
                throw e;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Handles the update button click event.
     * <p>
     *     This method updates the selected course with the values in the text fields.
     *     If no course is selected, an error message is displayed.
     *     Otherwise, the course is updated in the course table.
     *     The course table is then refreshed.
     * </p>
     */
    @FXML
    private void handleUpdate() {
        // Get the selected course from listener
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        // Check if any of the text fields are empty
        if (nullCheck()) return;
        if (selectedCourse != null) {
            // Update the course
            courseService.updateCourse(selectedCourse.getCourseCode() ,new Course(courseId.getText(), courseName.getText(), department.getText()));
            handleRefresh();
        } else {
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to update");
            alert.showAndWait();
        }
    }

    /**
     * Checks if any of the text fields are empty.
     * @return true if any of the text fields are empty, false otherwise.
     */
    private boolean nullCheck() {
        if (courseId.getText().isEmpty() || courseName.getText().isEmpty() || department.getText().isEmpty() ||
                courseId == null || courseName == null || department == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Handles the delete button click event.
     * <p>
     *     This method deletes the selected course from the course table.
     *     If no course is selected, an error message is displayed.
     *     Otherwise, the course is deleted from the course table.
     *     The course table is then refreshed.
     * </p>
     */
    @FXML
    private void handleDelete() {
        // Get the selected course from listener
        Course selectedCourse = courseTable.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            // Delete the course
            courseService.deleteCourse(selectedCourse.getCourseCode());
            handleRefresh();
        } else {
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a course to delete");
            alert.showAndWait();
        }
    }
}