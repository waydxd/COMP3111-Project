package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentService;
import comp3111.examsystem.service.internal.StudentServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * The `StudentLoginController` class manages the student login functionality within the Exam Management System.
 * It handles user authentication by verifying the student's credentials and managing the transition
 * to the main user interface upon successful login.
 */
public class StudentLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;
    @FXML
    private Button loginBtn; // Added missing field
    @FXML
    private Label errorLabel; // Added missing field

    private int studentid;

    private final StudentService studentService = new StudentServiceImpl();

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object is not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Checks if a student account exists based on the provided username.
     *
     * @param username The username to check.
     * @return True if the account exists, false otherwise.
     */
    public boolean accountExists(String username) {
        for (Student student : studentService.getAllStudents()) {
            if (student.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a Student object based on the provided username.
     *
     * @param username The username of the student to retrieve.
     * @return The Student object if found, or null if not found.
     */
    public Student getStudentByUsername(String username) {
        for (Student student : studentService.getAllStudents()) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    /**
     * Validates the login credentials of a student.
     *
     * @return True if the login is successful (account exists and password is correct), false otherwise.
     */
    public boolean checkLogin() {
        if (accountExists(usernameTxt.getText())) {
            Student student = getStudentByUsername(usernameTxt.getText());
            if (student != null && student.Check_password(passwordTxt.getText())) {
                return true;
            }
        }
        return false;
    }


    /**
     * Handles the login action triggered by the user.
     *
     * @param e The action event triggered by the login button.
     */
    @FXML
    public void login(ActionEvent e) {
        if (checkLogin()) {
            SuccessPopupController.Success_Popup("Login successful!");
            studentid = studentService.login(usernameTxt.getText(), passwordTxt.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentMainUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Hi " + usernameTxt.getText() + ", Welcome to HKUST Examination System");

            try {
                stage.setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            StudentMainController controller = fxmlLoader.getController();
            controller.setUsername(usernameTxt.getText());
            controller.setID(studentid);

            stage.show();
            ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
        } else {
            ErrorPopupController.Error_Popup("Username/Password incorrect.");
        }
    }

    /**
     * Opens the student registration window.
     */
    @FXML
    public void register() {
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentRegisterUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Student Register");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}