package comp3111.examsystem.controller;

import comp3111.examsystem.service.ManagerService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManagerLoginControllerTest {

    @Mock
    private TextField usernameTxt;

    @Mock
    private PasswordField passwordTxt;

    @Mock
    private ManagerService managerService;

    @InjectMocks
    private ManagerLoginController managerLoginController;

    @BeforeAll
    public static void initJFX() {

        try {
            Platform.startup(() -> {});
        } catch (IllegalStateException e) {
            // Toolkit already initialized, continue with the tests
        }
    }

    @Test
    void testLoginSuccess() {
        Platform.runLater(() -> {
            // Arrange
            when(usernameTxt.getText()).thenReturn("validUsername");
            when(passwordTxt.getText()).thenReturn("validPassword");
            when(managerService.login("validUsername", "validPassword")).thenReturn(true);

            Button mockButton = mock(Button.class);
            Scene mockScene = mock(Scene.class);
            Stage mockStage = mock(Stage.class);
            ActionEvent mockEvent = mock(ActionEvent.class);

            when(mockEvent.getSource()).thenReturn(mockButton);
            when(mockButton.getScene()).thenReturn(mockScene);
            when(mockScene.getWindow()).thenReturn(mockStage);

            // Act
            managerLoginController.login(mockEvent);

            // Assert
            verify(managerService).login("validUsername", "validPassword");
            verify(mockStage).close();
        });
    }

    @Test
    void testLoginFailure() {
        Platform.runLater(() -> {
            // Arrange
            when(usernameTxt.getText()).thenReturn("invalidUsername");
            when(passwordTxt.getText()).thenReturn("invalidPassword");
            when(managerService.login("invalidUsername", "invalidPassword")).thenReturn(false);

            ActionEvent mockEvent = mock(ActionEvent.class);

            // Act
            managerLoginController.login(mockEvent);

            // Assert
            verify(managerService).login("invalidUsername", "invalidPassword");
        });
    }
}