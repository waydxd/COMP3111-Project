package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ErrorPopupController implements Initializable  {


    static Stage errorWindow;

    @FXML
    public void close(ActionEvent event) {
        // Code to close the error popup window
        errorWindow.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }


    public static void Error_Popup() {
        try {
            if(errorWindow==null)
            {
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
    public static void Error_Popup(String text) {


        Platform.runLater(() -> {
            try {
                // 创建 Stage 和加载 FXML 的代码
                if(errorWindow==null)
                {
                    errorWindow = new Stage();
                }

                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ErrorUI.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
//            errorLabel.setText(text);
                errorWindow.setTitle("Error");
                errorWindow.setScene(scene);
                errorWindow.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    public void close(javafx.event.ActionEvent actionEvent) {
        errorWindow.close();
    }
}
