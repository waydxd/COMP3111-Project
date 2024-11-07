package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Question;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Init_ALL();



        questionTableView.setItems(Question.getQuestionList());
    }

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

    void init_ChoiceBox()
    {
        // Initialize question filter choice box
        typeTextField.getItems().addAll("Type","Single","Multiple");
        typeTextField.getSelectionModel().select(0);

        typeFilterTextField.getItems().addAll("Type","Single","Multiple");
        typeFilterTextField.getSelectionModel().select(0);
    }
    void Init_ALL()
    {
        // Populate the question table view
        Bind_All_Columns();

//        // Add  sample questions
//        init_questionlist();

        // Initialize question filter choice box
        init_ChoiceBox();


    }

//    private void init_questionlist()
//    {
//
//                questionList.addAll(
//                        new Question("Which of the following is a programming language?", new String[] {"Apple", "Java", "Banana", "Orange"}, "B", "Single", "10"),
//                        new Question("What does CPU stand for?", new String[] {"Computer Processor", "Central Processing Unit", "Computer Program", "Centralized Programming"}, "B", "Single", "10"),
//                        new Question("Which of the following is a programming language?", new String[] {"Excel", "Photoshop", "Java", "HTML"}, "C", "Single", "10"),
//                        new Question("What does RAM stand for in computing?", new String[] {"Random Access Memory", "Read-Only Memory", "Running Applications", "Remote Access Memory"}, "A", "Single", "10"),
//                        new Question("Which of the following is NOT a programming language?", new String[] {"Java", "Python", "HTML", "Photoshop"}, "D", "Single", "10"),
//                        new Question("What is the main function of a GPU?", new String[] {"Data Storage", "Graphics Rendering", "Network Connection", "Power Supply"}, "B", "Single", "10"),
//                        new Question("What does HTML stand for in web development?", new String[] {"Hyper Text Markup", "High Tech Modern", "How to Make Lists", "Home Tool Management"}, "A", "Single", "10"),
//                        new Question("Which component of a computer is often referred to as the 'brain'?", new String[] {"Hard Drive", "CPU", "Monitor", "Keyboard"}, "B", "Single", "10"),
//                        new Question("Which of the following are object-oriented programming languages?", new String[] {"Java", "Python", "C", "HTML"}, "AB", "Multiple", "20"),
//                        new Question("Which of the following are types of computer memory?", new String[] {"RAM", "ROM", "CPU", "HDD"}, "AB", "Multiple", "20"),
//                        new Question("Which of the following are essential components of a computer?", new String[] {"Motherboard", "Keyboard", "Monitor", "Mouse"}, "ABC", "Multiple", "20"),
//                        new Question("Which of the following are commonly used web browsers?", new String[] {"Google Chrome", "Photoshop", "Safari", "Microsoft Word"}, "AC", "Multiple", "20"),
//                        new Question("Which of the following are programming paradigms?", new String[] {"Imperative", "Declarative", "Procedural", "Visual Studio"}, "ABC", "Multiple", "20")
//                );
//
//    }

    @FXML
    private void handleAddButton() {
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
            ErrorPopupController.Error_Popup();
            return;
        }
        // Create a new Question object with the entered data
        Question newQuestion = new Question(question, new String[] {optionA, optionB, optionC, optionD}, answer, type, score);

        // Add the new question to the questionList
        Question.getQuestionList().add(newQuestion);

        questionTableView.setItems(Question.getQuestionList());


    }

    @FXML
    private void handleUpdateButton() {
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
                ErrorPopupController.Error_Popup();
                return;
            }


            // Refresh the TableView to display the updated question
            questionTableView.refresh();
            return;

        }
        ErrorPopupController.Error_Popup();
    }

    @FXML
    private void handleDeleteButton() {
        // Add logic to handle deleting a question
        // Retrieve the currently selected question from the TableView
        Question selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();

        if (selectedQuestion != null) {
            // Remove the selected question from the questionList
            Question.getQuestionList().remove(selectedQuestion);

            // Refresh the TableView to display the updated list of questions
            questionTableView.setItems(Question.getQuestionList());
        }

    }

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

//        questionTableView.setItems(FXCollections.observableArrayList(questionList));

        questionTableView.setItems(FXCollections.observableArrayList(Question.getQuestionList()));
    }

    @FXML
    private void handleFilterButton() {
        // Add logic to handle filtering the question table
        String selectedQuestion = questionFilter.getText();
        String selectedType =  typeFilterTextField.getValue();
        String selectedScore = scoreFilterTextField.getText();

        // Filter the questionList based on the selected values
        List<Question> filteredQuestions = Question.getQuestionList().stream()
                .filter(q -> {
                    boolean questionFilter = selectedQuestion == null || selectedQuestion.isEmpty() || q.getQuestion().contains(selectedQuestion);
                    boolean typeFilter = selectedType == null || selectedType.equals("Type") || q.getType().equals(selectedType);
                    boolean scoreFilter = selectedScore == null || selectedScore.isEmpty() || q.getScore().equals(selectedScore);
                    return questionFilter && typeFilter && scoreFilter;
                })
                .collect(Collectors.toList());

        // Update the TableView with the filtered questions
        questionTableView.setItems(FXCollections.observableArrayList(filteredQuestions));
    }

    @FXML
    private void handleResetButton() {

        // Reset the filter values to their default state
        questionFilter.clear();
        typeFilterTextField.getSelectionModel().select(0);
        scoreFilterTextField.clear();
    }



}