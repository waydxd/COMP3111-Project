package comp3111.examsystem;

import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Member;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;


public class Main extends Application {
	/**
	 * Start the application
	 * @param primaryStage the primary stage	
	 */
	public void start(Stage primaryStage) {
		try {
			String cssPath = getClass().getResource("cupertino-light.css").toExternalForm();
			Application.setUserAgentStylesheet(cssPath);
			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
			Scene scene = new Scene(fxmlLoader.load(), 640, 480);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Main function
	 * @param args arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
