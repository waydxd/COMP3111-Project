package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.ExaminationService;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.List;

public class StudentExamStartController {
    @FXML
    private Label courseLabel;
    @FXML
    private Label examNameLabel;
    @FXML
    private Label numberOfQuestionsLabel;
    @FXML
    private Label fullScoreLabel;
    @FXML
    private Label examTimeLabel;

    private Examination exam;
    private final ExaminationService examinationService = new ExaminationServiceImpl();

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private int studentid;

    public void setID(int studentid) {
        this.studentid = studentid;
    }

    public void setExamDetails(Examination exam) {
        List<Question> questions = examinationService.getQuestionsInExamination(exam.getId());
        float totalScore = examinationService.getTotalScoreOfExamination(exam.getId());

        this.exam = exam;
        courseLabel.setText(exam.getCourseID());
        examNameLabel.setText(exam.getExamName());
        numberOfQuestionsLabel.setText("Number of Questions: " + questions.size());
        fullScoreLabel.setText("Full Score: " + totalScore);
        examTimeLabel.setText("Time: " + exam.getExamTime() + " minutes");

    }

    @FXML
    private void startExam() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("ExamUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Exam: " + exam.getExamName());
            stage.setScene(new Scene(fxmlLoader.load()));

            // Get the controller and set the current exam
            StudentExamController controller = fxmlLoader.getController();
            controller.setUsername(username);
            controller.setID(studentid);
            controller.setCurrentExam(exam);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}