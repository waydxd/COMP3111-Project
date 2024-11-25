package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.internal.QuestionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;


public class QuestionBankManagementController implements Initializable {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private TableView<Question> questionTableView;

    @FXML
    private TableColumn<Question, String> QuestionColumn;
    @FXML
    private TableColumn<Question, String> OptionAColumn;
    @FXML
    private TableColumn<Question, String> OptionBColumn;
    @FXML
    private TableColumn<Question, String> OptionCColumn;
    @FXML
    private TableColumn<Question, String> OptionDColumn;
    @FXML
    private TableColumn<Question, String> AnswerColumn;
    @FXML
    private TableColumn<Question, String> TypeColumn;
    @FXML
    private TableColumn<Question, String> ScoreColumn;

    @FXML
    private TextField questionFilter;
    @FXML
    private ChoiceBox<String> typeFilterTextField;
    @FXML
    private TextField scoreFilterTextField;

    @FXML
    private TextField questionTextField;
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
    private ChoiceBox<String> typeTextField;
    @FXML
    private TextField scoreTextField;

    @FXML
    private Button resetButton;
    @FXML
    private Button filterButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button addButton;
    @FXML
    private Button updateButton;

//    private ObservableList<Question> questionList = FXCollections.observableArrayList();
//    private ObservableList<Question> fliter_questionList = FXCollections.observableArrayList();

    private final QuestionService questionService = new QuestionServiceImpl();

    /**
     * Initializes the `QuestionBankManagementController` with the necessary setup.
     *
     * @param url            the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Init_ALL();



        questionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));
    }

    /**
     * Binds the table columns to the properties of the `Question` class.
     * This method sets the cell value factories for each column in the `questionTableView`.
     */
    void Bind_All_Columns()
    {
        // Populate the question table view
        QuestionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        OptionAColumn.setCellValueFactory(new PropertyValueFactory<>("optionA"));
        OptionBColumn.setCellValueFactory(new PropertyValueFactory<>("optionB"));
        OptionCColumn.setCellValueFactory(new PropertyValueFactory<>("optionC"));
        OptionDColumn.setCellValueFactory(new PropertyValueFactory<>("optionD"));
        AnswerColumn.setCellValueFactory(new PropertyValueFactory<>("answer"));
        TypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        ScoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
    }

    /**
     * Initializes the choice boxes for the question type filter and the question type in the form.
     * This method adds the available options to the choice boxes and sets the default selection.
     */
    void init_ChoiceBox()
    {
        // Initialize question filter choice box
        typeTextField.getItems().addAll("Type","Single","Multiple");
        typeTextField.getSelectionModel().select(0);

        typeFilterTextField.getItems().addAll("Type","Single","Multiple");
        typeFilterTextField.getSelectionModel().select(0);
    }

    /**
     * Initializes the entire application by calling the `Bind_All_Columns()` and `init_ChoiceBox()` methods.
     */
    void Init_ALL()
    {
        // Populate the question table view
        Bind_All_Columns();

        // Initialize question filter choice box
        init_ChoiceBox();
    }

    /**
     * Handles the addition of a new question to the question bank.
     * This method retrieves the values from the form fields, validates the input, creates a new `Question` object,
     * and adds it to the question service.
     */
    @FXML
    public void handleAddButton() {
        // Add logic to handle adding a new question
        // Retrieve the values from the form fields
        String question = questionTextField.getText();
        String optionA = optionATextField.getText();
        String optionB = optionBTextField.getText();
        String optionC = optionCTextField.getText();
        String optionD = optionDTextField.getText();
        String answer = answerTextField.getText();
        String type = typeTextField.getValue();
        String score = scoreTextField.getText();
        // Validate the user input
        if (question.isEmpty() || optionA.isEmpty() || optionB.isEmpty() || optionC.isEmpty() || optionD.isEmpty() || answer.isEmpty() || type == null || type.equals("Type") || score.isEmpty()) {
            // Display an error message or show a dialog to the user
            ErrorPopupController.Error_Popup("Please fill in all the required fields.");
            return;
            // Throw a RuntimeException with an appropriate error message
//            throw new RuntimeException("Please fill in all the required fields.");
        }
        //Validate the option
        if(!answer.matches("[ABCD]*") && !answer.equals("ALL"))
        {
            ErrorPopupController.Error_Popup("Please fill in the answer with ABCD or ALL");
        }
        // Validate the score
        try {
            Float.parseFloat(score);
        } catch (NumberFormatException e) {
            ErrorPopupController.Error_Popup("Please enter a numeric score.");
            return;
//                throw new RuntimeException("Please enter a numeric score.");
        }
        // Create a new Question object with the entered data
        Question newQuestion = new Question(question, new String[] {optionA, optionB, optionC, optionD}, answer, type, score);

        // Add the new question to the questionList
        questionService.addQuestion(newQuestion);

        questionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));
    }

    /**
     * Handles the update of an existing question in the question bank.
     * This method retrieves the currently selected question from the table view, retrieves the updated values from the form fields,
     * validates the input, and updates the selected question in the question service.
     */
    @FXML
    void handleUpdateButton() {
        // Add logic to handle updating an existing question
        // Retrieve the currently selected question from the TableView
        Question selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();

        if (selectedQuestion != null) {
            // Retrieve the updated values from the form fields
            String updatedQuestion = questionTextField.getText().trim();
            String updatedOptionA = optionATextField.getText().trim();
            String updatedOptionB = optionBTextField.getText().trim();
            String updatedOptionC = optionCTextField.getText().trim();
            String updatedOptionD = optionDTextField.getText().trim();
            String updatedAnswer = answerTextField.getText().trim();
            String updatedType = typeTextField.getValue();
            String updatedScore = scoreTextField.getText().trim();

            // Validate the updated values
            if (updatedQuestion.isEmpty() || updatedOptionA.isEmpty() || updatedOptionB.isEmpty() || updatedOptionC.isEmpty() || updatedOptionD.isEmpty() || updatedAnswer.isEmpty() || updatedType == null || updatedType.equals("Type") || updatedScore.isEmpty()) {
                // Display an error message or show a dialog to the user
                ErrorPopupController.Error_Popup("Please fill in all the required fields.");
                return;
//                throw new RuntimeException("Please fill in all the required fields.");
            }

            // Validate the score
            try {
                Float.parseFloat(updatedScore);
            } catch (NumberFormatException e) {
                ErrorPopupController.Error_Popup("Please enter a numeric score.");
                return;
//                throw new RuntimeException("Please enter a numeric score.");
            }

            // Update the selected question with the new values
            questionService.updateQuestion(selectedQuestion.getId(), new Question(
                    updatedQuestion,
                    new String[] {updatedOptionA, updatedOptionB, updatedOptionC, updatedOptionD},
                    updatedAnswer,
                    updatedType,
                    updatedScore
            ));

            // Refresh the TableView to display the updated question
            questionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));
            questionTableView.refresh();

            return;
        }
        else {
            // Throw a RuntimeException if no question is selected
            ErrorPopupController.Error_Popup("No question selected for update.");
            return;
//            throw new RuntimeException("No question selected for update.");
        }
    }

    /**
     * Handles the deletion of a question from the question bank.
     * This method retrieves the currently selected question from the table view and removes it from the question service.
     */
    @FXML
    void handleDeleteButton() {
        // Add logic to handle deleting a question
        // Retrieve the currently selected question from the TableView
        Question selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();

        if (selectedQuestion != null) {
            // Remove the selected question from the questionList
            questionService.deleteQuestion(selectedQuestion.getId());

            // Refresh the TableView to display the updated list of questions
            questionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));
        }
        else {
            // Throw a RuntimeException if no question is selected
            ErrorPopupController.Error_Popup("No question selected for deletion.");
//            throw new RuntimeException("No question selected for deletion.");
        }
    }

    /**
     * Handles the refresh button click, resetting the filter values and the question form fields.
     * This method also updates the table view with the complete list of questions.
     */
    @FXML
    private void handleRefreshButton() {
        // Reset the filter values to their default state
        questionFilter.clear();
        typeTextField.getSelectionModel().select(0);
        scoreFilterTextField.clear();

        // Reset the question form fields
        questionTextField.clear();
        optionATextField.clear();
        optionBTextField.clear();
        optionCTextField.clear();
        optionDTextField.clear();
        answerTextField.clear();
        typeTextField.getSelectionModel().selectFirst();
        scoreTextField.clear();

        // Update the TableView with the complete list of questions
        questionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));
    }

    /**
     * Handles the filter button click, filtering the question table based on the selected filter values.
     * This method retrieves the filter values, validates the input, and updates the table view with the filtered questions.
     */
    @FXML
    private void handleFilterButton() {
        // Add logic to handle filtering the question table
        String selectedQuestion = questionFilter.getText();
        String selectedType =  typeFilterTextField.getValue();
        String selectedScore = scoreFilterTextField.getText();
        // Validate the filter inputs
//        if (selectedQuestion == null || selectedQuestion.isEmpty()) {
//            ErrorPopupController.Error_Popup("Please fill in all the required fields.");
//            throw new RuntimeException("Question filter cannot be empty.");
//        }

//        if (selectedType == null || selectedType.equals("Type")) {
//            ErrorPopupController.Error_Popup("Please fill in all the required fields.");
//            throw new RuntimeException("Type filter cannot be empty.");
//        }
//
//        if (selectedScore == null || selectedScore.isEmpty()) {
//            ErrorPopupController.Error_Popup("Please fill in all the required fields.");
//            throw new RuntimeException("Score filter cannot be empty.");
//        }
        // Validate the score
        try {
            Float.parseFloat(selectedScore);
        } catch (NumberFormatException e) {
            ErrorPopupController.Error_Popup("Please enter a numeric score.");
            return;
//                throw new RuntimeException("Please enter a numeric score.");
        }
        // Filter the questionList based on the selected values
        List<Question> filteredQuestions = questionService.getAllQuestions().stream()
                .filter(q -> {
                    boolean questionFilter = selectedQuestion == null || selectedQuestion.isEmpty() || q.getQuestion().contains(selectedQuestion);
                    boolean typeFilter = selectedType == null || selectedType.equals("Type") || q.getType().equals(selectedType);
                    boolean scoreFilter = selectedScore == null || selectedScore.isEmpty() || parseFloat(String.valueOf(q.getScore()))==parseFloat(selectedScore);
                    return questionFilter && typeFilter && scoreFilter;

                })

                .collect(Collectors.toList());

        // Update the TableView with the filtered questions
        questionTableView.setItems(FXCollections.observableArrayList(filteredQuestions));
    }

    /**
     * Handles the reset button click, resetting the filter values to their default state.
     */
    @FXML
    private void handleResetButton() {
        // Reset the filter values to their default state
        questionFilter.clear();
        typeFilterTextField.getSelectionModel().select(0);
        scoreFilterTextField.clear();
    }



}