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
    @FXML
    private Button resetButton;
    @FXML
    private Button filterButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;

    private final TeacherService teacherService = new TeacherServiceImpl();

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

    @FXML
    private void handleRefresh() {
        teacherTable.setItems(FXCollections.observableArrayList(teacherService.getAllTeachers()));
        teacherTable.refresh();
    }

    @FXML
    private void handleReset() {
        filterUsernameField.clear();
        filterNameField.clear();
        filterDepartmentField.clear();
        handleRefresh();
    }

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

    @FXML
    private void handleAdd() {
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

    @FXML
    private void handleUpdate() {
        Teacher selectedTeacher = teacherTable.getSelectionModel().getSelectedItem();
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