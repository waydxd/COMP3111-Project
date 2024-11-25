package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.ExaminationService;
import comp3111.examsystem.service.GradeService;
import comp3111.examsystem.service.QuestionService;
import comp3111.examsystem.service.StudentService;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import comp3111.examsystem.service.internal.GradeServiceImpl;
import comp3111.examsystem.service.internal.QuestionServiceImpl;
import comp3111.examsystem.service.internal.StudentServiceImpl;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentExamController {

    @FXML
    private Label quizNameLabel;
    @FXML
    private Label totalQuestionsLabel;
    @FXML
    private Label timerLabel;
    @FXML
    private ListView<String> questionListView;
    @FXML
    private VBox questionVBox;
    @FXML
    private Label questionNumberLabel;
    @FXML
    private ToggleGroup optionsToggleGroup;
    @FXML
    private RadioButton optionARadioButton;
    @FXML
    private RadioButton optionBRadioButton;
    @FXML
    private RadioButton optionCRadioButton;
    @FXML
    private RadioButton optionDRadioButton;
    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button submitButton;
    @FXML
    private SplitPane splitPane;

    private ExaminationService examinationService;
    private QuestionService questionService;
    private GradeService gradeService;
    private StudentService studentService;
    private Examination currentExam;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private Map<Integer, RadioButton> selectedChoices = new HashMap<>();

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private int studentid;

    public void setID(int studentid) {
        this.studentid = studentid;
    }

    /*public void setCurrentExam(Examination exam) {
        this.currentExam = exam;
        loadExamData();
    }*/

    public StudentExamController() {
        this.examinationService = new ExaminationServiceImpl();
        this.questionService = new QuestionServiceImpl();
        this.gradeService = new GradeServiceImpl();
        this.studentService = new StudentServiceImpl();
    }

    @FXML
    private void initialize() {
        optionsToggleGroup = new ToggleGroup();
        optionARadioButton.setToggleGroup(optionsToggleGroup);
        optionBRadioButton.setToggleGroup(optionsToggleGroup);
        optionCRadioButton.setToggleGroup(optionsToggleGroup);
        optionDRadioButton.setToggleGroup(optionsToggleGroup);

        optionsToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            saveSelectedChoice();
        });

        questionListView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                saveSelectedChoice();
                displayQuestion(newValue.intValue());
            }
        });

        Platform.runLater(() -> {
            splitPane.setDividerPositions(0.25);
        });

        splitPane.getDividers().get(0).positionProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() > 0.5) {
                splitPane.setDividerPositions(0.5);
            }
        });
    }

    private void loadExamData() {
        if (currentExam != null) {
            quizNameLabel.setText(currentExam.getCourseID() + " - " + currentExam.getExamName());
            questions = examinationService.getQuestionsInExamination(currentExam.getId());
            totalQuestionsLabel.setText("Total Questions: " + questions.size());
            startTimer(currentExam.getExamTime());
        }

        ObservableList<String> questionTitles = FXCollections.observableArrayList();
        for (Question question : questions) {
            questionTitles.add(question.getQuestion());
        }
        questionListView.setItems(questionTitles);

        displayQuestion(0);
    }

    private void startTimer(float initialDuration) {
        final float[] duration = {initialDuration * 60};
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            int minutes = (int) Math.ceil(duration[0] / 60.0);
            timerLabel.setText("Time: " + minutes + " minutes");
            duration[0]--;
            if (duration[0] < 0) {
                timeline.stop();
                handleSubmitButton();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    void displayQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            currentQuestionIndex = index;
            Question question = questions.get(index);
            questionNumberLabel.setText("Question " + (index + 1));
            questionVBox.getChildren().clear();
            questionVBox.getChildren().add(new Text(question.getQuestion()));
            optionARadioButton.setText(question.getOptionA());
            optionBRadioButton.setText(question.getOptionB());
            optionCRadioButton.setText(question.getOptionC());
            optionDRadioButton.setText(question.getOptionD());

            RadioButton selectedRadioButton = selectedChoices.get(index);
            if (selectedRadioButton != null) {
                selectedRadioButton.setSelected(true);
            } else {
                optionsToggleGroup.selectToggle(null);
            }
        }
    }

    @FXML
    void handlePreviousButton() {
        if (currentQuestionIndex > 0) {
            saveSelectedChoice();
            displayQuestion(currentQuestionIndex - 1);
        }
    }

    @FXML
    private void handleNextButton() {
        if (currentQuestionIndex < questions.size() - 1) {
            saveSelectedChoice();
            displayQuestion(currentQuestionIndex + 1);
        }
    }

    @FXML
    private void handleSubmitButton() {
        saveSelectedChoice();
        AtomicInteger correctAnswers = new AtomicInteger(0);
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String correctAnswer = question.getAnswer();
            RadioButton selectedRadioButton = selectedChoices.get(i);
            if (selectedRadioButton != null) {
                if ((selectedRadioButton == optionARadioButton && correctAnswer.equals("A")) ||
                        (selectedRadioButton == optionBRadioButton && correctAnswer.equals("B")) ||
                        (selectedRadioButton == optionCRadioButton && correctAnswer.equals("C")) ||
                        (selectedRadioButton == optionDRadioButton && correctAnswer.equals("D"))) {
                    correctAnswers.incrementAndGet();
                }
            }
        }

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Result");
            alert.setHeaderText("Your Result");
            alert.setContentText("You answered " + correctAnswers.get() + " out of " + questions.size() + " questions correctly.");
            alert.showAndWait();

            Stage stage = (Stage) submitButton.getScene().getWindow();
            stage.close();
        });

        saveGrade(correctAnswers.get(), questions.size());
    }

    private void saveSelectedChoice() {
        RadioButton selectedRadioButton = (RadioButton) optionsToggleGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            selectedChoices.put(currentQuestionIndex, selectedRadioButton);
        }
    }

    private void saveGrade(int correctAnswers, int totalQuestions) {
        float score = (float) correctAnswers;
        float fullScore = (float) totalQuestions;
        String[] timerParts = timerLabel.getText().split(" ");
        float timeSpent = Float.parseFloat(timerParts[1]);

        Student student = studentService.getStudent(studentid);
        String studentName = student.getName();

        //System.out.println("SaveGrade Username: " + username);
        Grade grade = new Grade();
        grade.setStudentName(studentName); // Replace with actual student name
        grade.setCourseName(currentExam.getCourseID());
        grade.setExamName(currentExam.getExamName());
        grade.setScore(score);
        grade.setFullScore(fullScore);
        grade.setTimeSpent(timeSpent);
        grade.setUserName(username); // Replace with actual username

        gradeService.addGrade(grade);
    }

    private boolean isExamCompleted() {
        List<Grade> grades = gradeService.getGradesForUser(username);
        for (Grade grade : grades) {
            if (grade.getCourseName().equals(currentExam.getCourseID()) && grade.getExamName().equals(currentExam.getExamName())) {
                return true;
            }
        }
        return false;
    }

    public void setCurrentExam(Examination exam) {
        this.currentExam = exam;
        if (isExamCompleted()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exam Completed");
                alert.setHeaderText(null);
                alert.setContentText("You have already completed this exam.");
                alert.showAndWait();

                Stage stage = (Stage) quizNameLabel.getScene().getWindow();
                stage.close();
            });
        } else {
            //System.out.println("Exam is not completed. Loading exam data.");
            loadExamData();
        }
    }
}