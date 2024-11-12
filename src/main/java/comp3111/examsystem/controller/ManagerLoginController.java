package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManagerLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        // Dummy authentication logic
        if (authenticate(username, password)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManagerMainUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hi " + username + ", Welcome to HKUST Examination System");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();

                // Close the current window
                Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                currentStage.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            // Handle authentication failure (e.g., show an error message)
            System.out.println("Authentication failed. Please check your username and password.");
        }
    }

    private boolean authenticate(String username, String password) {
        // Replace with actual authentication logic
        return "admin".equals(username) && "password".equals(password);
    }

}
