package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.text.Position;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static comp3111.examsystem.entity.Manager.*;

/**
 * The TeacherRegisterController class is responsible for handling the registration of new teachers in the exam system.
 * It provides methods for validating user input, creating a new teacher account, and managing the registration process.
 */
public class TeacherRegisterController implements Initializable {

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private ChoiceBox<String> Gender;

    @FXML
    private TextField ageTxt;

    @FXML
    private ChoiceBox<String> Position;

    @FXML
    private TextField departmentTxt;

    @FXML
    private PasswordField passwordConfirmTxt;
    private TeacherLoginController teacherLoginController;
    private int testcount=0;
    private final TeacherService teacherService =new TeacherServiceImpl();
    /**
     * Initializes the controller by setting up the necessary components and services.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The resources used to localize the root object, or null if the root object was not localized.
     */

    public void initialize(URL location, ResourceBundle resources) {




        // Add options to the Gender ChoiceBox
        Gender.getItems().addAll("Male", "Female", "Other");
        // Add options to the Position ChoiceBox
        Position.getItems().addAll("Professor", "Associate Professor", "Assistant Professor", "Lecturer I", "Lecturer II", "Adjunct Professor", "Teaching Assistant", "Research Assistant", "Graduate Assistant Lecturer", "Instructional Assistant");


        teacherLoginController=new TeacherLoginController();

    }
    /**
     * Handles the login button click event, opening the teacher registration UI.
     *
     * @param e The ActionEvent object representing the button click.
     */
    @FXML
    public void login(ActionEvent e) {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherRegisterUI.fxml"));
        Stage stage = new Stage();

        stage.setTitle("Teacher Register");
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        stage.show();
        ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
    }
    /**
     * Handles the close button click event, closing the teacher registration stage.
     *
     * @param e The ActionEvent object representing the button click.
     */
    @FXML
    public void close(ActionEvent e)
    {
        // Get a reference to the current stage
        teacherLoginController.getRegisterStage().close();

    }
    /**
     * Handles the registration of a new teacher.
     * This method validates the user input, creates a new teacher account, and adds it to the teacher service.
     *
     * @param e The ActionEvent object representing the button click.
     */

    @FXML
    public void register(ActionEvent e) {
        boolean register_success = true;

        // Case III: At least one input is empty
        // Case IV: Invalid Age

        try
        {
            if (!ageTxt.getText().isEmpty()) {
                int age = Integer.parseInt(ageTxt.getText());
                if (age < 0 || age > 100) {
                    register_success = false;

                    // Error popup
                    ErrorPopupController.Error_Popup("Age must be between 0 and 100.");
                    return; }
            }
        }
        catch (NumberFormatException s) {
            ErrorPopupController.Error_Popup("Invalid exam time format. Please enter a valid number.");
            return;
        }



        if (usernameTxt.getText().isEmpty() || passwordTxt.getText().isEmpty() ||
                nameTxt.getText().isEmpty() || Gender.getValue() == null ||
                ageTxt.getText().isEmpty() || departmentTxt.getText().isEmpty() ||
                Position.getValue() == null) {
            register_success = false;

            // Error popup
            ErrorPopupController.Error_Popup("Please fill in all required fields.");
            return;
        }

        // Case I: Existing username
        if (teacherService.account_exist(usernameTxt.getText())) {
            register_success = false;

            // Error popup
            ErrorPopupController.Error_Popup("Username already exists.");
            return;
        }

        // Case II: Different passwords
        if (!passwordConfirmTxt.getText().equals(passwordTxt.getText())) {
            register_success = false;

            // Error popup
            ErrorPopupController.Error_Popup("Passwords do not match.");
            return;
        }

        // Registration
        if (register_success) {
            String Username = usernameTxt.getText();
            String Password = passwordTxt.getText();
            String Name = nameTxt.getText();
            String Gender_ = Gender.getValue();
            String Age = ageTxt.getText();
            String Department = departmentTxt.getText();
            String Position_ = Position.getValue();

            Teacher teacher = new Teacher(
                    Username,
                    Password,
                    Name,
                    Gender_,
                    Age,
                    Department,
                    Position_
            );

            // Add the account to the account manager
//            getAccountManager().addAccount(teacher);
            teacherService.addTeacher(teacher);
            SuccessPopupController.Success_Popup("Registration successful!");
            teacherLoginController.getRegisterStage().close();
        }


    }


}
