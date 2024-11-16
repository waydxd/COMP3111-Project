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
    @FXML
    private Button resetButton;
    @FXML
    private Button filterButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;

    private final StudentService studentService = new StudentServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderComboBox.setItems(FXCollections.observableArrayList("Male", "Female", "Other"));

        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

//        studentTable.getColumns().addAll(usernameColumn, nameColumn, ageColumn, genderColumn, departmentColumn, passwordColumn);

//
//        // Example data
//     studentTable.setItems(FXCollections.observableArrayList(
//             new Student("user1", "John Doe", "s", "Male", "20", "password1"),
//            new Student("user2", "Jane Smith", "s", "Female", "10", "password2")
//        ));

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

    @FXML
    private void handleReset() {
        filterUsernameField.clear();
        filterNameField.clear();
        filterDepartmentField.clear();
        studentTable.setItems(FXCollections.observableArrayList(
                studentService.getAllStudents()
        ));
        studentTable.refresh();
    }

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

    @FXML
    private void handleAdd() {
        // Implement add logic here
        try{
            Integer.parseInt(ageField.getText());
            studentService.addStudent(new Student(usernameField.getText(), passwordField.getText(), nameField.getText(), genderComboBox.getValue(), (ageField.getText()), departmentField.getText()));
            studentTable.setItems(FXCollections.observableArrayList(
                    studentService.getAllStudents()
            ));
            studentTable.refresh();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Age must be an integer");
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void handleUpdate() {
        // Implement update logic here
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            int id = selectedStudent.getId();
            // Implement update logic here using the id
            try{
                Integer.parseInt(ageField.getText());
                studentService.updateStudent(id,new Student(usernameField.getText(), passwordField.getText(), nameField.getText(), genderComboBox.getValue(), (ageField.getText()), departmentField.getText()));
                studentTable.setItems(FXCollections.observableArrayList(
                        studentService.getAllStudents()
                ));
                studentTable.refresh();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Age must be an integer");
                alert.showAndWait();
                return;
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a student to update");
            alert.showAndWait();
        }
    }
}