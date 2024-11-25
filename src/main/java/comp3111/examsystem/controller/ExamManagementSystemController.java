package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.CourseService;
import comp3111.examsystem.service.ExaminationService;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.internal.CourseServiceImpl;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import comp3111.examsystem.service.internal.QuestionServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleFloatProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Float.parseFloat;

/**
 * The `ExamManagementSystemController` class is responsible for managing the user interface of the Exam Management System.
 * It handles user interactions, such as creating and updating examinations, adding and removing questions from examinations,
 * and filtering examinations and questions based on various criteria.
 */
public class ExamManagementSystemController {
        @FXML private TextField examNameTextField;
        @FXML private ComboBox<String> filterCourseID;
        @FXML private ComboBox<String> filterPublish;
        @FXML private TextField questionTextField;
        @FXML private ComboBox<String> questionTypeComboBox;
        @FXML private TextField scoreTextField;
        @FXML
        private TextField examNameField;
        @FXML
        private TextField examTimeField;
        @FXML
        private  ComboBox<String> courseComboBox;
        @FXML
        private  ComboBox<String> publishComboBox;
        @FXML
        private TableView<Examination> ExamTableView;
        @FXML
        private TableView<Question> LeftQuestionTableView;
        @FXML
        private TableView<Question> All_QuestionTableView;
        @FXML
        private TableColumn<Examination, String> examNameColumn;
        @FXML
        private TableColumn<Examination, String> courseIdColumn;
        @FXML
        private TableColumn<Examination, Float> examTimeColumn;
        @FXML
        private TableColumn<Examination, String> publishColumn;

        @FXML
        private TableColumn<Question, String> questionColumn;
        @FXML
        private TableColumn<Question, String> typeColumn;
        @FXML
        private TableColumn<Question, Float> scoreColumn_left;
        @FXML
        private TableColumn<Question, Float> scoreColumn_right;
        @FXML
        private TableColumn<Question, String> questionTextColumn;
        @FXML
        private TableColumn<Question, String> questionTypeColumn;

    private static int init_flag=0;

    private final ExaminationService examinationService = new ExaminationServiceImpl();

    private final CourseService courseService = new CourseServiceImpl();

    private final QuestionService questionService = new QuestionServiceImpl();
    /**
     * Initializes the controller by setting up the table views, populating the combo boxes, and adding a listener to the ExamTableView selection.
     */
    @FXML
    void initialize() {
        init_All();
        // Ensure the list is not null
        ObservableList<Examination> examinations = FXCollections.observableArrayList(examinationService.getAllExaminations());
        if (examinations == null) {
            examinations = FXCollections.observableArrayList();
        }
        ExamTableView.setItems(examinations);
        All_QuestionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));

        // Add a listener to the ExamTableView selection model
        ExamTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Get the quiz questions from the selected Examination object
                // Update the QuizTableView with the quiz questions
//                LeftQuestionTableView.setItems(FXCollections.observableArrayList(newValue.getQuiz()));
                LeftQuestionTableView.setItems(FXCollections.observableArrayList(examinationService.getQuestionsInExamination(newValue.getId())));

            } else {
                // Clear the QuizTableView if no exam is selected
                LeftQuestionTableView.setItems(null);
            }
        });
    }

    private void init_All()
    {
        bind_all_columns();

        init_Exam();
    }

    private void init_Exam()
    {





        filterCourseID.getItems().addAll(courseService.getAllCoursesID());
        filterPublish.getItems().addAll("yes","no");


        questionTypeComboBox.getItems().addAll("Single","Multiple");

        courseComboBox.getItems().addAll(courseService.getAllCoursesID());
        publishComboBox.getItems().addAll("yes","no");

    }

    private void bind_all_columns()
    {
        // Populate the question table view
        // Initialize the TableView columns
        examNameColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        examTimeColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getExamTime()).asObject());
        publishColumn.setCellValueFactory(new PropertyValueFactory<>("publish"));

        //Question in exam
        questionColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scoreColumn_left.setCellValueFactory(new PropertyValueFactory<>("score"));

        //All questions
        questionTextColumn.setCellValueFactory(new PropertyValueFactory<>("question"));
        questionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scoreColumn_right.setCellValueFactory(new PropertyValueFactory<>("score"));

    }

    /**
     * Filters the questions displayed in the All_QuestionTableView based on the input criteria from the user.
     */
    @FXML
    private void filterQuestions() {

        // Get the selected values from the filter controls
        String selectedScore = scoreTextField.getText();
        String selectedQuestion = questionTextField.getText();
        String selectedType = questionTypeComboBox.getValue();


        Float parsedScore = null;
        if (!selectedScore.isEmpty()) {
            try {
                parsedScore = Float.parseFloat(selectedScore);
            } catch (NumberFormatException e) {
                ErrorPopupController.Error_Popup("Please enter a valid numeric score.");
                return; // Exit the method if score is invalid
            }
        }
        // Filter the questions based on the selected criteria
//        ObservableList<Question> filteredQuestions = FXCollections.observableArrayList();
//        for (Question question : questionService.getAllQuestions()) {
//            if (
//                    (parseFloat(String.valueOf(question.getScore()))==parseFloat(selectedScore)||selectedScore== null || selectedScore.isEmpty() ||String.valueOf(question.getScore()).equals(selectedScore)) &&
//                            (selectedQuestion == null || selectedQuestion.isEmpty() ||question.getQuestion().contains(selectedQuestion)) &&
//                            (selectedType == null || question.getType().equals(selectedType))) {
//                filteredQuestions.add(question);
//            }
//        }
        //
        // Filter the questionList based on the selected values
        List<Question> filteredQuestions = questionService.getAllQuestions().stream()
                .filter(q -> {
                    boolean questionFilter = selectedQuestion == null || selectedQuestion.isEmpty() || q.getQuestion().contains(selectedQuestion);
                    boolean typeFilter = selectedType == null || selectedType.equals("Type") || q.getType().equals(selectedType);
                    boolean scoreFilter = selectedScore == null || selectedScore.isEmpty() || parseFloat(String.valueOf(q.getScore()))==parseFloat(selectedScore);
                    return questionFilter && typeFilter && scoreFilter;

                })

                .collect(Collectors.toList());




        All_QuestionTableView.setItems(FXCollections.observableArrayList(filteredQuestions));
    }
    /**
     * Deletes the selected question from the LeftQuestionTableView and the associated examination.
     */
    @FXML
    private void deleteFromLeft() {
        Question selectedQuestion =  LeftQuestionTableView.getSelectionModel().getSelectedItem();
        Examination selectedExam = ExamTableView.getSelectionModel().getSelectedItem();

        if (selectedExam != null && selectedQuestion != null) {
                examinationService.removeQuestionFromExamination(selectedExam.getId(), selectedQuestion.getId());
                LeftQuestionTableView.setItems(FXCollections.observableArrayList(examinationService.getQuestionsInExamination(selectedExam.getId())));
                LeftQuestionTableView.refresh();
        } else {
            String message = "Selected Exam: " + (selectedExam != null ? selectedExam.getExamName() : "None") + "\n" +
                    "Selected Question: " + (selectedQuestion != null ? selectedQuestion.getQuestion() : "None") + "\n";

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Selection Details");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
    /**
     * Adds the selected question from All_QuestionTableView to the selected examination in LeftQuestionTableView.
     */
    @FXML
    void addToLeft() {
        // Add a new question to the TableView
        Question selectedQuestion =All_QuestionTableView.getSelectionModel().getSelectedItem();
        Examination selectedExam =ExamTableView.getSelectionModel().getSelectedItem();
        if (selectedExam!=null) {

            //Show existing quiz in exam
            LeftQuestionTableView.setItems(FXCollections.observableArrayList(
                    examinationService.getQuestionsInExamination(selectedExam.getId()))
            );

            if(selectedQuestion != null)
            {
                // Validate the updated values
                //if (!selectedExam.getQuiz().contains(selectedQuestion))
                if (!examinationService.getQuestionsInExamination(selectedExam.getId()).contains(selectedQuestion))
                {
                    try {
                        examinationService.addQuestionToExamination(selectedExam.getId(),selectedQuestion.getId());
                        LeftQuestionTableView.setItems(FXCollections.observableArrayList(examinationService.getQuestionsInExamination(selectedExam.getId())));
                    } catch (Exception e) {
                        if("repeated".equals(e.getMessage())) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("The question is already in the exam.");
                            alert.showAndWait();
                        } else {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    /**
     * Refreshes all table views and clears all input fields.
     */
    @FXML
    private void refreshTable() {
        // Refresh the TableView with the updated data
        ExamTableView.setItems(FXCollections.observableArrayList(examinationService.getAllExaminations()));
        All_QuestionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));

        // Clear the input fields and comboboxes
        examNameTextField.clear();
        examNameField.clear();
        examTimeField.clear();
        courseComboBox.getSelectionModel().clearSelection();
        publishComboBox.getSelectionModel().clearSelection();

        questionTextField.clear();
        questionTypeComboBox.getSelectionModel().clearSelection();
        scoreTextField.clear();

        filterCourseID.getSelectionModel().clearSelection();
        filterPublish.getSelectionModel().clearSelection();

    }
    /**
     * Updates the selected examination with new values from the input fields.
     *
     * @param actionEvent The action event triggered by the user.
     */

    @FXML
    public void UpdateExam(ActionEvent actionEvent) {
        // Get the selected exam from the ExamTableView
        Examination selectedExam = ExamTableView.getSelectionModel().getSelectedItem();
        if (selectedExam != null) {
            //Check the invalid condition
            if (selectedExam != null) {
                // Retrieve input values
                String examName = examNameField.getText().trim();
                String examTimeText = examTimeField.getText().trim();
                String courseID = courseComboBox.getValue();
                String publishStatus = publishComboBox.getValue();

                // Validate inputs
                if (examName.isEmpty() || examTimeText.isEmpty() || courseID == null || publishStatus == null) {
                    // Show an error message for incomplete input
                    ErrorPopupController.Error_Popup("Please fill in all fields.");
                    return;
                }

                float examTime;
                try {
                    examTime = Float.parseFloat(examTimeText);
                    if (examTime < 0) {
                        ErrorPopupController.Error_Popup("Exam time must be a non-negative number.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    ErrorPopupController.Error_Popup("Invalid exam time. Please enter a valid number.");
                    return;
                }


            // Update the selected exam with the new values

            selectedExam.setExamName(examNameField.getText());
            selectedExam.setExamTime(Float.parseFloat(examTimeField.getText()));
            selectedExam.setCourseID(courseComboBox.getValue());
            selectedExam.setPublish(publishComboBox.getValue().equals("yes"));
            examinationService.updateExamination(selectedExam);

            // Refresh the ExamTableView
            ExamTableView.refresh();
        }
        }

    }
    /**
     * Adds a new examination based on the input values from the user.
     *
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void AddExam(ActionEvent actionEvent) {

        // Get the values from the input fields

        String examName = examNameField.getText();
        String courseID = courseComboBox.getValue();



        // Validate the input fields
        if (examName.isEmpty() ||examTimeField.getText().isEmpty() || courseID == null || publishComboBox.getValue() == null) {
            ErrorPopupController.Error_Popup("Please fill in all the required fields.");
            return;
        }

        //Validate the double conversion not work
        try {
            float examTime = Float.parseFloat(examTimeField.getText());
        } catch (NumberFormatException e) {
            ErrorPopupController.Error_Popup("Invalid exam time format. Please enter a valid number.");
            return;
        }
        boolean publish = publishComboBox.getValue().equals("yes");
        float examTime = Float.parseFloat(examTimeField.getText());

        // Create a new Examination object
        Examination newExam = new Examination(
                courseID,
                examTime,
                examName,
                publish
        );

        // Add the new exam to the ExamTableView
        examinationService.addExamination(newExam);
        ExamTableView.setItems(FXCollections.observableArrayList(examinationService.getAllExaminations()));

        // Clear the input fields
        examNameField.clear();
        examTimeField.clear();
        courseComboBox.getSelectionModel().clearSelection();
        publishComboBox.getSelectionModel().clearSelection();

    }
    /**
     * Resets the fields related to questions to their default state.
     *
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void resetQuestionFields(ActionEvent actionEvent) {
        questionTextField.clear();
        questionTypeComboBox.getSelectionModel().clearSelection();
        scoreTextField.clear();

//        All_QuestionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));

    }
    /**
     * Resets the fields related to exams to their default state.
     *
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void resetExamFields(ActionEvent actionEvent) {
        // Clear the input fields
        examNameTextField.clear();
        filterCourseID.getSelectionModel().clearSelection();
        filterPublish.getSelectionModel().clearSelection();
        ExamTableView.setItems(FXCollections.observableArrayList(examinationService.getAllExaminations()));
    }

    /**
     * Filters the examinations displayed in the ExamTableView based on the input criteria from the user.
     *
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void filterExams(ActionEvent actionEvent) {
        // Get the selected values from the filter controls
        String selectedCourseId = filterCourseID.getValue();
        String selectedPublish = filterPublish.getValue();
        String selectedExamName = examNameTextField.getText();
        // Filter the exams based on the selected criteria
        ObservableList<Examination> filteredExams = FXCollections.observableArrayList();
        for (Examination exam : examinationService.getAllExaminations()) {
            if ((selectedCourseId == null || selectedCourseId.isEmpty() || exam.getCourseID().equals(selectedCourseId)) &&
                    (selectedPublish == null || selectedPublish.isEmpty() ||
                    // selected value is "yes" or "no" while exam.getPublish() returns true or false
                    ((selectedPublish.equals("yes") && exam.getPublish()) || (selectedPublish.equals("no") && !exam.getPublish()))) &&
                    (selectedExamName == null || selectedExamName.isEmpty() || exam.getExamName().contains(selectedExamName))) {
                filteredExams.add(exam);
            }
        }

        // Update the ExamTableView with the filtered exams
        ExamTableView.setItems(filteredExams);
    }
    /**
     * Deletes the selected examination from the ExamTableView.
     *
     * @param actionEvent The action event triggered by the user.
     */
    public void deleteExam(ActionEvent actionEvent) {
        // Get the selected exam from the ExamTableView
        Examination selectedExam = ExamTableView.getSelectionModel().getSelectedItem();

        if (selectedExam != null) {
            // Remove the selected exam from the static exam list
            examinationService.deleteExamination(selectedExam.getId());

            // Refresh the ExamTableView
            ExamTableView.setItems(FXCollections.observableArrayList(examinationService.getAllExaminations()));
        } else {
            // Display an error message or alert if no exam is selected
            ErrorPopupController.Error_Popup("No exam selected for deletion.");
        }
    }
}