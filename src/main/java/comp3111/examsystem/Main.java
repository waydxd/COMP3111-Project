package comp3111.examsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
