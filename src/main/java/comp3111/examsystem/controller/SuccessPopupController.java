package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * The `SuccessPopupController` class manages the success popup functionality within the Exam Management System.
 * It provides methods to display success messages to the user.
 */
public class SuccessPopupController {

    static Stage successWindow;

    /**
     * Closes the success popup window.
     */
    @FXML
    public void close() {
        // Code to close the success popup window
        successWindow.close();
    }

    /**
     * Displays a success popup window with a predefined UI.
     * If the success window is not already created, it initializes and shows it.
     */
    public static void Success_Popup() {
        try {
            if(successWindow == null) {
                successWindow = new Stage();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("SuccessUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            successWindow.setTitle("Success");
            successWindow.setScene(scene);
            successWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays a success popup window with a custom message.
     *
     * @param message The success message to be displayed in the popup.
     */
    public static void Success_Popup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}