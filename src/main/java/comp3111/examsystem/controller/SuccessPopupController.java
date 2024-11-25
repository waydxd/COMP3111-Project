package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class SuccessPopupController {

    static Stage successWindow;

    @FXML
    public void close() {
        // Code to close the success popup window
        successWindow.close();
    }

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

    public static void Success_Popup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}