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

    private ObservableList<Question> questionList = FXCollections.observableArrayList();
//    private ObservableList<Question> fliter_questionList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Init_ALL();



        questionTableView.setItems(questionList);
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

        // Add  sample questions
        init_questionlist();

        // Initialize question filter choice box
        init_ChoiceBox();


    }

    private void init_questionlist()
    {

        questionList.addAll(
                new Question("Which of the following is a programming language?", new String[] {"Apple", "Java", "Banana", "Orange"}, "B", "Single", "10"),
                new Question("What does CPU stand for?", new String[] {"Computer Processor", "Central Processing Unit", "Computer Program", "Centralized Programming"}, "B", "Single", "10"),
                new Question("Which of the following is a programming language?", new String[] {"Excel", "Photoshop", "Java", "HTML"}, "C", "Single", "10"),
                new Question("What does RAM stand for in computing?", new String[] {"Random Access Memory", "Read-Only Memory", "Running Applications", "Remote Access Memory"}, "A", "Single", "10"),
                new Question("Which of the following is NOT a programming language?", new String[] {"Java", "Python", "HTML", "Photoshop"}, "D", "Single", "10"),
                new Question("What is the main function of a GPU?", new String[] {"Data Storage", "Graphics Rendering", "Network Connection", "Power Supply"}, "B", "Single", "10"),
                new Question("What does HTML stand for in web development?", new String[] {"Hyper Text Markup", "High Tech Modern", "How to Make Lists", "Home Tool Management"}, "A", "Single", "10"),
                new Question("Which component of a computer is often referred to as the 'brain'?", new String[] {"Hard Drive", "CPU", "Monitor", "Keyboard"}, "B", "Single", "10"),
                new Question("Which of the following are object-oriented programming languages?", new String[] {"Java", "Python", "C", "HTML"}, "AB", "Multiple", "20"),
                new Question("Which of the following are types of computer memory?", new String[] {"RAM", "ROM", "CPU", "HDD"}, "AB", "Multiple", "20"),
                new Question("Which of the following are essential components of a computer?", new String[] {"Motherboard", "Keyboard", "Monitor", "Mouse"}, "ABC", "Multiple", "20"),
                new Question("Which of the following are commonly used web browsers?", new String[] {"Google Chrome", "Photoshop", "Safari", "Microsoft Word"}, "AC", "Multiple", "20"),
                new Question("Which of the following are programming paradigms?", new String[] {"Imperative", "Declarative", "Procedural", "Visual Studio"}, "ABC", "Multiple", "20")
        );

    }

    @FXML
    private void handleAddButton() {
        // Add logic to handle adding a new question
    }

    @FXML
    private void handleUpdateButton() {
        // Add logic to handle updating an existing question
    }

    @FXML
    private void handleDeleteButton() {
        // Add logic to handle deleting a question
    }

    @FXML
    private void handleRefreshButton() {
        // Add logic to handle refreshing the question table
    }

    @FXML
    private void handleFilterButton() {
        // Add logic to handle filtering the question table
        applyFilters();
    }

    @FXML
    private void handleResetButton() {
        // Add logic to handle resetting the form
    }


    // Method to apply the filters
    private void applyFilters() {
        String selectedQuestion = questionFilter.getText();
        String selectedType = typeFilterTextField.getValue();
        String selectedScore = scoreFilterTextField.getText();

        // Filter the questionList based on the selected values
        List<Question> filteredQuestions = questionList.stream()
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
}