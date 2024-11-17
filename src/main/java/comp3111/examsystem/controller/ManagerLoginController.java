package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.service.ManagerService;
import comp3111.examsystem.service.internal.ManagerServiceImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * The controller for the manager login UI.
 */
public class ManagerLoginController implements Initializable {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;

    /**
     * The ManagerService instance used to interact with the manager data.
     */
    private final ManagerService managerService = new ManagerServiceImpl();

    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Handles the login button click event.
     * @param e The action event.
     */
    @FXML
    public void login(ActionEvent e) {
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        // authentication logic
        if (authenticate(username, password)) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ManagerMainUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Hi " + username + ", Welcome to HKUST Examination System");
                stage.setScene(new Scene(fxmlLoader.load()));
                stage.show();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Login Success");
                alert.setHeaderText(null);
                alert.setContentText("You have successfully logged in as a manager.");
                alert.showAndWait();
                // Close the current window
                Stage currentStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
                currentStage.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else {
            // Show an error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Authentication failed. Please check your username and password.");
            alert.showAndWait();
        }
    }

    /**
     * Authenticates the user with the given username and password.
     * @param username The username.
     * @param password The password.
     * @return True if the user is authenticated, false otherwise.
     */
    private boolean authenticate(String username, String password) {
        return managerService.login(username, password);
    }

}
