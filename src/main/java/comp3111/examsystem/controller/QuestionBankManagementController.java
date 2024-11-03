package comp3111.examsystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionBankManagementController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableView<?> questionTableView;
    @FXML
    private ChoiceBox<?> questionChoiceBox;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField scoreTextField;

    @FXML
    private TextField optionATextField;

    @FXML
    private TextField optionBTextField;

    @FXML
    private TextField optionCTextField;

    @FXML
    private TextField optionDTextField;

    @FXML
    private TextField answerTextField;

    @FXML
    private Button resetButton;

    @FXML
    private Button filterButton;

    @FXML
    private ChoiceBox<String> questionFilterChoiceBox;

    @FXML
    private TextField typeFilterTextField;

    @FXML
    private TextField scoreFilterTextField;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize question filter choice box
        questionFilterChoiceBox.getItems().addAll("All", "Question", "Type", "Score");
        questionFilterChoiceBox.getSelectionModel().selectFirst();

        // Populate the question table view
//        questionTableView.setItems(questionList);

    }
}
