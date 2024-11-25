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

/**
 * The `StudentRegisterController` class manages the student registration functionality within the Exam Management System.
 * It handles the registration process by collecting user input, validating it, and creating a new student account.
 */

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

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object is not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Gender.getItems().addAll("Male", "Female", "Other");
    }


    /**
     * Handles the registration process. This method collects user input, validates it, and creates a new student account.
     * If any validation fails, an error popup is displayed.
     */
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

    /**
     * Closes the registration window.
     */
    @FXML
    public void close() {
        Stage stage = (Stage) usernameTxt.getScene().getWindow();
        stage.close();
    }
}