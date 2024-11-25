package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The `ErrorPopupController` class manages the error popup window.
 * It provides methods to display error messages and close the error popup.
 */
public class ErrorPopupController implements Initializable {

    static Stage errorWindow;



    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object is not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Displays an error popup window with a predefined layout.
     */
    public static void Error_Popup() {
        try {
            if (errorWindow == null) {
                errorWindow = new Stage();
            }

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ErrorUI.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            errorWindow.setTitle("Error");
            errorWindow.setScene(scene);
            errorWindow.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an error popup with a custom error message.
     *
     * @param message the error message to display
     */
    public static void Error_Popup(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the error popup window.
     *
     * @param actionEvent the action event triggered by the close button
     */
    public void close(javafx.event.ActionEvent actionEvent) {
        errorWindow.close();
    }
}