package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static comp3111.examsystem.entity.Manager.getAccountManager;


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
    public void register(ActionEvent e)
    {
        boolean register_success=true;
        //Case I:用户名已经存在
        if(getAccountManager().account_exist(usernameTxt.getText()))
        {
            register_success=false;

            //显示报错窗口
            ErrorPopupController.Error_Popup();
        }

        //Case II:俩个密码不同
        if(!passwordConfirmTxt.toString().equals(passwordTxt.toString()))
        {
            register_success=false;

            //显示报错窗口
            ErrorPopupController.Error_Popup();

        }

        //注册
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

        //加入到账户库中
        getAccountManager().addAccount(teacher);



    }
}
