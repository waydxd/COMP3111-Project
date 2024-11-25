package comp3111.examsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The `TeacherLoginController` class manages the teacher login functionality within the Exam Management System.
 * It handles user authentication by verifying the teacher's credentials and managing the transition
 * to the main user interface upon successful login.
 */
public class TeacherLoginController implements Initializable {

    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    public static Stage registerstage;

    private final TeacherService teacherService = new TeacherServiceImpl();
    private List<Teacher> teacherList = teacherService.getAllTeachers();

    /**
     * Sets the list of teachers.
     *
     * @param teacherList A list of Teacher objects to set.
     */
    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param location The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object is not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {
        // Initialization logic can go here if needed
    }

    /**
     * Checks if a teacher account exists based on the provided username.
     *
     * @param user The username to check.
     * @return True if the account exists, false otherwise.
     */
    public boolean account_exist(String user) {
        for (Teacher member : teacherList) {
            if (member.getUsername().equals(user)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a Teacher object based on the provided username.
     *
     * @param username The username of the teacher to retrieve.
     * @return The Teacher object if found, or null if not found.
     */
    public Teacher getTeacherbyUserName(String username) {
        teacherList = teacherService.getAllTeachers();
        for (Teacher teacher : teacherList) {
            if (teacher.getUsername().equals(username)) {
                return teacher;
            }
        }
        return null; // Return null if no matching teacher is found
    }

    /**
     * Validates the login credentials of a teacher.
     *
     * @return True if the login is successful (account exists and password is correct), false otherwise.
     */
    public boolean Check_login() {
        if (teacherService.account_exist(usernameTxt.getText())) {
            if (getTeacherbyUserName(usernameTxt.getText()).Check_password(passwordTxt.getText())) {
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
        try {
            // Proceed if login is successful
            if (Check_login()) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherMainUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hi " + usernameTxt.getText() + ", Welcome to HKUST Examination System");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
            } else {
                // Show error popup on login failure
                ErrorPopupController.Error_Popup();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Opens the teacher registration window.
     */
    @FXML
    public void register() {
        try {
            if (registerstage == null) {
                registerstage = new Stage();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherRegisterUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            registerstage.setTitle("Teacher Register");
            registerstage.setScene(scene);
            registerstage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the registration stage.
     *
     * @return The stage for teacher registration.
     */
    public Stage getRegisterStage() {
        return registerstage;
    }
}