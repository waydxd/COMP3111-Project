package comp3111.examsystem.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.QuestionServiceImpl;
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

import static comp3111.examsystem.entity.Manager.*;

public class TeacherLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    public static Stage registerstage;

    private final TeacherService teacherService = new TeacherServiceImpl();

    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean account_exist(String user)
    {
        for(Teacher member: teacherService.getAllTeachers())
        {
            if(member.getUsername().equals(user))
            {
                return true;
            }
        }
        return false;
    }
    public Teacher getTeacherbyUserName(String username) {
        for (Teacher teacher : teacherService.getAllTeachers()) {
            if (teacher.getUsername().equals(username)) {
                return teacher;
            }
        }
        return null; // 如果未找到匹配的教师,返回 null
    }


    public boolean Check_login()
    {

//        //检查账户是否存在
//        if(getAccountManager().account_exist(usernameTxt.getText()))
//        {
//            //检查账户密码是否正确
//            if(getAccountManager().getTeacherbyUserName(usernameTxt.getText()).Check_password(passwordTxt.getText()))
//            {
//
//                return true;
//            }
//
//
//        }
        //检查账户是否存在
        if(account_exist(usernameTxt.getText()))
        {
            //检查账户密码是否正确
            if(getTeacherbyUserName(usernameTxt.getText()).Check_password(passwordTxt.getText()))
            {

                return true;
            }


        }



        return false;
    }
    @FXML
    public void login(ActionEvent e) {
        try {
            //登录成功
            if(Check_login())
            {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("TeacherMainUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hi " + usernameTxt.getText() +", Welcome to HKUST Examination System");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();
                ((Stage) ((Button) e.getSource()).getScene().getWindow()).close();
            }
            //登录失败
            else
            {
                //错误弹窗
                ErrorPopupController.Error_Popup();

            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    public void register() {
        try {
            if(registerstage==null)
            {
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
    public Stage getRegisterStage()
    {
        return registerstage;
    }



}
