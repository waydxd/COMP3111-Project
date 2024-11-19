package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.internal.StudentServiceImpl;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

import comp3111.examsystem.service.StudentService;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * The controller for the student management UI.
 */
public class StudentManagementController implements Initializable {
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
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> usernameColumn;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, Integer> ageColumn;
    @FXML
    private TableColumn<Student, String> genderColumn;
    @FXML
    private TableColumn<Student, String> departmentColumn;
    @FXML
    private TableColumn<Student, String> passwordColumn;

    /**
     * The StudentService instance used to interact with the student data.
     */
    private final StudentService studentService = new StudentServiceImpl();

    /**
     * Initializes the student management UI.
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     *                  <p>
     *                  The method does the following:
     *                  1. Initializes the student table with the student data.
     *                  2. Adds a listener to the student table to update the student details when a student is selected.
     *                  </p>
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        studentTable.setItems(FXCollections.observableArrayList(
                studentService.getAllStudents()
        ));
        studentTable.refresh();

        studentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                usernameField.setText(newValue.getUsername());
                nameField.setText(newValue.getName());
                ageField.setText(newValue.getAge());
                genderComboBox.setValue(newValue.getGender());
                departmentField.setText(newValue.getDepartment());
                passwordField.setText(newValue.getPassword());
            }
        });
    }

    /**
     * Handles the refresh button click event.
     * <p>
     *     This method refreshes the studentTable with the list of all students.
     * </p>
     */
    @FXML
    private void handleRefresh(){
        studentTable.setItems(FXCollections.observableArrayList(
                studentService.getAllStudents()
        ));
        studentTable.refresh();
    }

    /**
     * Handles the reset button click event.
     * <p>
     *     This method clears the filter fields and refreshes the studentTable with the list of all students.
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
     *     This method filters the studentTable based on the values in the filter fields.
     *     The studentTable is then refreshed.
     *     The filter is case-insensitive.
     *     The filter is applied to the username, name, and department fields.
     * </p>
     */
    @FXML
    private void handleFilter() {
        String filterUsername = filterUsernameField.getText() != null ? filterUsernameField.getText().toLowerCase() : "";
        String filterName = filterNameField.getText() != null ? filterNameField.getText().toLowerCase() : "";
        String filterDepartment = filterDepartmentField.getText() != null ? filterDepartmentField.getText().toLowerCase() : "";

        studentTable.setItems(FXCollections.observableArrayList(
                studentService.getAllStudents().stream()
                        .filter(student -> filterUsername.isEmpty() || student.getUsername().toLowerCase().contains(filterUsername))
                        .filter(student -> filterName.isEmpty() || student.getName().toLowerCase().contains(filterName))
                        .filter(student -> filterDepartment.isEmpty() || student.getDepartment().toLowerCase().contains(filterDepartment))
                        .toList()
        ));
        studentTable.refresh();
    }

    /**
     * Handles the add button click event.
     * <p>
     *     This method adds a new student to the studentTable based on the values in the text fields.
     *     ageField must be an integer but then stored as a string in the database.
     *     If any field is empty, an error alert is shown.
     *     If ageField is not an integer, an error alert is shown.
     *     If the student is successfully added, the studentTable is refreshed.
     * </p>
     */
    @FXML
    private void handleAdd() {
        if (checkNull()) return;
        try {
            Integer.parseInt(ageField.getText());
            studentService.addStudent(new Student(usernameField.getText(), passwordField.getText(), nameField.getText(), genderComboBox.getValue(), (ageField.getText()), departmentField.getText()));
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
     * Checks if any of the fields are empty.
     * <p>
     *     If any of the fields are empty, an error alert is shown.
     * </p>
     * @return true if any of the fields are empty, false otherwise.
     */
    private boolean checkNull() {
        if (usernameField.getText() == null || usernameField.getText().isEmpty() ||
                passwordField.getText() == null || passwordField.getText().isEmpty() ||
                nameField.getText() == null || nameField.getText().isEmpty() ||
                genderComboBox.getValue() == null ||
                ageField.getText() == null || ageField.getText().isEmpty() ||
                departmentField.getText() == null || departmentField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    /**
     * Handles the update button click event.
     * <p>
     *     This method updates the selected student in the studentTable based on the values in the text fields.
     *     ageField must be an integer but then stored as a string in the database.
     *     If any field is empty, an error alert is shown.
     *     If ageField is not an integer, an error alert is shown.
     *     If no student is selected, an error alert is shown.
     *     If the student is successfully updated, the studentTable is refreshed.
     * </p>
     */
    @FXML
    private void handleUpdate() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (checkNull()) return;
        if (selectedStudent != null) {
            int id = selectedStudent.getId();
            try {
                Integer.parseInt(ageField.getText());
                studentService.updateStudent(id,new Student(usernameField.getText(), passwordField.getText(), nameField.getText(), genderComboBox.getValue(), (ageField.getText()), departmentField.getText()));
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
            alert.setContentText("Please select a student to update");
            alert.showAndWait();
        }
    }
    /**
     * Handles the delete button click event.
     * <p>
     *     This method deletes the selected student from the studentTable.
     *     If no student is selected, an error alert is shown.
     *     If the student is successfully deleted, the studentTable is refreshed.
     * </p>
     */
    @FXML
    private void handleDelete(){
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            int id = selectedStudent.getId();
            studentService.deleteStudent(id);
            handleRefresh();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to delete");
            alert.showAndWait();
        }
    }

}