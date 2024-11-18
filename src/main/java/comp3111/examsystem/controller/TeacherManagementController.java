package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * The controller for the teacher management UI.
 */
public class TeacherManagementController implements Initializable {
    @FXML
    private TextField filterUsernameField;
    @FXML
    private TextField filterNameField;
    @FXML
    private TextField filterDepartmentField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField ageField;
    @FXML
    private ComboBox<String> genderComboBox;
    @FXML
    private TextField departmentField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> positionComboBox;
    @FXML
    private TableView<Teacher> teacherTable;
    @FXML
    private TableColumn<Teacher, String> usernameColumn;
    @FXML
    private TableColumn<Teacher, String> nameColumn;
    @FXML
    private TableColumn<Teacher, Integer> ageColumn;
    @FXML
    private TableColumn<Teacher, String> genderColumn;
    @FXML
    private TableColumn<Teacher, String> departmentColumn;
    @FXML
    private TableColumn<Teacher, String> passwordColumn;
    @FXML
    private TableColumn<Teacher, String> positionColumn;

    /**
     * The TeacherService instance used to interact with the teacher data.
     */
    private final TeacherService teacherService = new TeacherServiceImpl();

    /**
     * Initializes the teacher management UI.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     * <p>
     * This method performs the following functions:
     * 1. Initializes the genderComboBox and positionComboBox with the appropriate values.
     * 2. Sets the cell value factories for the columns in the teacherTable.
     * 3. Populates the teacherTable with the list of all teachers.
     * 4. Adds a listener to the teacherTable to populate the text fields with the selected teacher's details.
     * </p>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));
        positionComboBox.setItems(FXCollections.observableArrayList("Professor", "Associate Professor", "Assistant Professor", "Lecturer I", "Lecturer II", "Adjunct Professor", "Teaching Assistant", "Research Assistant", "Graduate Assistant Lecturer", "Instructional Assistant"));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));

        teacherTable.setItems(FXCollections.observableArrayList(teacherService.getAllTeachers()));
        teacherTable.refresh();

        // Event listener for teacherTable
        teacherTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                usernameField.setText(newValue.getUsername());
                nameField.setText(newValue.getName());
                ageField.setText(newValue.getAge());
                genderComboBox.setValue(newValue.getGender());
                departmentField.setText(newValue.getDepartment());
                passwordField.setText(newValue.getPassword());
                positionComboBox.setValue(newValue.getPosition());
            }
        });
    }

    /**
     * Handles the refresh button click event.
     * <p>
     * This method refreshes the teacherTable with the list of all teachers.
     * </p>
     */
    @FXML
    private void handleRefresh() {

        teacherTable.setItems(FXCollections.observableArrayList(teacherService.getAllTeachers()));
        teacherTable.refresh();

    }

    /**
     * Handles the reset button click event.
     * <p>
     * This method clears the filter fields and refreshes the teacherTable with the list of all teachers.
     * </p>
     */
    @FXML
    private void handleReset() {
        filterUsernameField.clear();
        filterNameField.clear();
        filterDepartmentField.clear();
    }

    /**
     * Handles the filter button click event.
     * <p>
     * This method filters the teacherTable based on the values in the filter fields.
     * </p>
     */
    @FXML
    private void handleFilter() {
        String filterUsername = filterUsernameField.getText() != null ? filterUsernameField.getText().toLowerCase() : "";
        String filterName = filterNameField.getText() != null ? filterNameField.getText().toLowerCase() : "";
        String filterDepartment = filterDepartmentField.getText() != null ? filterDepartmentField.getText().toLowerCase() : "";

        teacherTable.setItems(FXCollections.observableArrayList(
                teacherService.getAllTeachers().stream()
                        .filter(teacher -> filterUsername.isEmpty() || teacher.getUsername().toLowerCase().contains(filterUsername))
                        .filter(teacher -> filterName.isEmpty() || teacher.getName().toLowerCase().contains(filterName))
                        .filter(teacher -> filterDepartment.isEmpty() || teacher.getDepartment().toLowerCase().contains(filterDepartment))
                        .toList()
        ));
        teacherTable.refresh();
    }

    /**
     * Handles the add button click event.
     * <p>
     * This method adds a new teacher to the teacherTable based on the values in the text fields.
     * </p>
     */
    @FXML
    private void handleAdd() {
        if (checkNull()) return;
        try {
            Integer.parseInt(ageField.getText());
            teacherService.addTeacher(new Teacher(usernameField.getText(), passwordField.getText(), nameField.getText(), genderComboBox.getValue(), ageField.getText(), departmentField.getText(), positionComboBox.getValue()));
            handleRefresh();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Age must be an integer");
            alert.showAndWait();
        }
    }

    /**
     * Handles the update button click event.
     * <p>
     * This method updates the selected teacher in the teacherTable based on the values in the text fields.
     * </p>
     */
    @FXML
    private void handleUpdate() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (checkNull()) return;
        if (selectedTeacher != null) {
            int id = selectedTeacher.getId();
            try {
                Integer.parseInt(ageField.getText());
                teacherService.updateTeacher(id, new Teacher(usernameField.getText(), passwordField.getText(), nameField.getText(), genderComboBox.getValue(), ageField.getText(), departmentField.getText(), positionComboBox.getValue()));
                handleRefresh();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Age must be an integer");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a teacher to update");
            alert.showAndWait();
        }
    }

    /**
     * Checks if any of the text fields are empty.
     *
     * @return true if any of the text fields are empty, false otherwise.
     */
    private boolean checkNull() {
        if(usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nameField.getText().isEmpty() || genderComboBox.getValue() == null || ageField.getText().isEmpty() || departmentField.getText().isEmpty() || positionComboBox.getValue() == null) {
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
     * This method deletes the selected teacher from the teacherTable.
     * </p>
     */
    @FXML
    private void handleDelete() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            int id = selectedTeacher.getId();
            teacherService.deleteTeacher(id);
            handleRefresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a teacher to delete");
            alert.showAndWait();
        }
    }
}