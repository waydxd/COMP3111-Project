package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Teacher;
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


    public void initialize(URL location, ResourceBundle resources) {




        // Add options to the Gender ChoiceBox
        Gender.getItems().addAll("Male", "Female", "Other");
        // Add options to the Position ChoiceBox
        Position.getItems().addAll("Teacher", "Administrator", "Manager");


        teacherLoginController=new TeacherLoginController();

    }

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
    @FXML
    public void close(ActionEvent e)
    {
        // Get a reference to the current stage
        teacherLoginController.getRegisterStage().close();

    }
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
                    ErrorPopupController.Error_Popup("Age must be between 18 and 100.");
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
        if (getAccountManager().account_exist(usernameTxt.getText())) {
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
            getAccountManager().addAccount(teacher);

            teacherLoginController.getRegisterStage().close();
        }

        if (testcount == 0) {
            test();
            testcount++;
        }
    }

    public static void test()
    {
        String Username = "";
        String Password = "";
        String Name =     "";
        String Gender_ =  "";
        String Age =      "";
        String Department="";
        String Position_ ="";

        Teacher teacher = new Teacher(
                Username,
                Password,
                Name,
                Gender_,
                Age,
                Department,
                Position_
        );
        getAccountManager().addAccount(teacher);
    }

}
