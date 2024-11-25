package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentService;
import comp3111.examsystem.service.internal.StudentServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static comp3111.examsystem.entity.Manager.getAccountManager;

public class StudentRegisterController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private PasswordField passwordConfirmTxt;
    @FXML
    private TextField nameTxt;
    @FXML
    private ChoiceBox<String> Gender;
    @FXML
    private TextField ageTxt;
    @FXML
    private TextField departmentTxt;

    private final StudentService studentService = new StudentServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gender.getItems().addAll("Male", "Female", "Other");
    }

    @FXML
    public void register() {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();
        String passwordConfirm = passwordConfirmTxt.getText();
        String name = nameTxt.getText();
        String gender = Gender.getValue();
        String age = ageTxt.getText();
        String department = departmentTxt.getText();

        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || gender == null || age.isEmpty() || department.isEmpty() || passwordConfirm.isEmpty()) {
            ErrorPopupController.Error_Popup("Please fill in all required fields.");
            return;
        }

        if (!passwordConfirmTxt.getText().equals(passwordTxt.getText())) {
            ErrorPopupController.Error_Popup("Passwords do not match.");
            return;
        }

        if (studentService.getAllStudents().stream().anyMatch(student -> student.getUsername().equals(username))) {
            ErrorPopupController.Error_Popup("Username already exists.");
            return;
        }

        try {
            int intage = Integer.parseInt(ageTxt.getText());
            if (intage < 0 || intage > 100) {
                ErrorPopupController.Error_Popup("Invalid age. Age must be between 1 and 100.");
                return;
            }
        } catch (NumberFormatException e) {
            ErrorPopupController.Error_Popup("Invalid age. Please enter a valid number.");
            return;
        }



        Student newStudent = new Student(username, password, name, gender, age, department);
        studentService.addStudent(newStudent);

        SuccessPopupController.Success_Popup("Registration successful!");

        close();
    }


    @FXML
    public void close() {
        Stage stage = (Stage) usernameTxt.getScene().getWindow();
        stage.close();
    }
}