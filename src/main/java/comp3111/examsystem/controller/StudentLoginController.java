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

    public void initialize(URL location, ResourceBundle resources) {
    }

    public boolean accountExists(String username) {
        for (Student student : studentService.getAllStudents()) {
            if (student.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Student getStudentByUsername(String username) {
        for (Student student : studentService.getAllStudents()) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    public boolean checkLogin() {
        if (accountExists(usernameTxt.getText())) {
            Student student = getStudentByUsername(usernameTxt.getText());
            if (student != null && student.Check_password(passwordTxt.getText())) {
                return true;
            }
        }
        return false;
    }

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

    @FXML
    public void backToMain() {
        try {
            Stage currentStage = (Stage) usernameTxt.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Select Login");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}