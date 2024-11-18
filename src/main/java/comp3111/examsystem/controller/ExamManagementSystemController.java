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

    @FXML
    private void initialize() {
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

//        if(init_flag++==0) {
//
//
//            Examination exam1 = new Examination(
//                    "COMP3111",
//                    30.0F,
//                    "quiz1",
//                    true
//            );
//
//            Examination exam2 = new Examination(
//                    "COMP3111",
//                    30.0F,
//                    "quiz2",
//                    true
//            );
//
//            Examination exam3 = new Examination(
//                    "COMP3111",
//                    40.0F,
//                    "quiz3",
//                    false
//            );
//
//            Examination exam4 = new Examination(
//                    "COMP5111",
//                    30.0F,
//                    "quiz1",
//                    true
//            );
//
//            Examination exam5 = new Examination(
//                    "COMP5111",
//                    30.0F,
//                    "quiz2",
//                    true
//            );
//
//            Examination exam6 = new Examination(
//                    "COMP5111",
//                    60.0F,
//                    "final",
//                    false
//            );
//
//            Examination exam7 = new Examination(
//                    "COMP3111",
//                    200.0F,
//                    "quiz4",
//                    true
//            );
//
//            // Add the exams to the static examination list
//            examinationService.getAllExaminations().addAll(Arrays.asList(exam1, exam2, exam3, exam4, exam5, exam6, exam7));
//
//        }




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


    @FXML
    private void filterQuestions() {

        // Get the selected values from the filter controls
        String selectedScore = scoreTextField.getText();
        String selectedQuestion = questionTextField.getText();
        String selectedType = questionTypeComboBox.getValue();

        // Filter the questions based on the selected criteria
        ObservableList<Question> filteredQuestions = FXCollections.observableArrayList();
        for (Question question : questionService.getAllQuestions()) {
            if (
                    (selectedScore== null || selectedScore.isEmpty() ||String.valueOf(question.getScore()).equals(selectedScore)) &&
                            (selectedQuestion == null || selectedQuestion.isEmpty() ||question.getQuestion().contains(selectedQuestion)) &&
                            (selectedType == null || question.getType().equals(selectedType))) {
                filteredQuestions.add(question);
            }
        }


        All_QuestionTableView.setItems(filteredQuestions);
    }

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

    @FXML
    private void addToLeft() {
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


    @FXML
    public void UpdateExam(ActionEvent actionEvent) {
        // Get the selected exam from the ExamTableView
        Examination selectedExam = ExamTableView.getSelectionModel().getSelectedItem();
        if (selectedExam != null) {
            // Update the selected exam with the new values

            selectedExam.setExamName(examNameField.getText());
            selectedExam.setExamTime(Float.parseFloat(examTimeField.getText()));
            selectedExam.setCourseID(courseComboBox.getValue());
            selectedExam.setPublish(publishComboBox.getValue().equals("Published"));
            examinationService.updateExamination(selectedExam);

            // Refresh the ExamTableView
            ExamTableView.refresh();
        }

    }
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
    @FXML
    public void resetQuestionFields(ActionEvent actionEvent) {
        questionTextField.clear();
        questionTypeComboBox.getSelectionModel().clearSelection();
        scoreTextField.clear();

        All_QuestionTableView.setItems(FXCollections.observableArrayList(questionService.getAllQuestions()));

    }
    @FXML
    public void resetExamFields(ActionEvent actionEvent) {
        // Clear the input fields
        examNameTextField.clear();
        filterCourseID.getSelectionModel().clearSelection();
        filterPublish.getSelectionModel().clearSelection();
        ExamTableView.setItems(FXCollections.observableArrayList(examinationService.getAllExaminations()));
    }
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