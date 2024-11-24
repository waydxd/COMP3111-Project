package comp3111.examsystem.controller;

import comp3111.examsystem.Main;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.service.ExaminationService;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class StudentMainController implements Initializable {
    @FXML
    private ComboBox<Examination> examCombox;

    private final ExaminationService examinationService = new ExaminationServiceImpl();

    @FXML
    private VBox mainbox;

    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    private int studentid;

    public void setID(int studentid) {
        this.studentid = studentid;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPublishedExams();

        examCombox.setCellFactory(lv -> new ListCell<Examination>() {
            @Override
            protected void updateItem(Examination exam, boolean empty) {
                super.updateItem(exam, empty);
                if (empty || exam == null) {
                    setText(null);
                } else {
                    setText(exam.getCourseID() + " - " + exam.getExamName());
                }
            }
        });

        examCombox.setButtonCell(new ListCell<Examination>() {
            @Override
            protected void updateItem(Examination exam, boolean empty) {
                super.updateItem(exam, empty);
                if (empty || exam == null) {
                    setText(null);
                } else {
                    setText(exam.getCourseID() + " - " + exam.getExamName());
                }
            }
        });
    }

    @FXML
    private void loadPublishedExams() {
        List<Examination> exams = examinationService.getAllExaminations();
        List<Examination> publishedExams = exams.stream()
                .filter(Examination::getPublish)
                .collect(Collectors.toList());
        examCombox.getItems().addAll(publishedExams);
    }



    @FXML
    private void openExamUI() {
        Examination selectedExam = examCombox.getSelectionModel().getSelectedItem();
        if (selectedExam != null) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentExamStartUI.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Exam: " + selectedExam.getExamName());
                stage.setScene(new Scene(fxmlLoader.load()));

                // Get the controller and set the current exam
                StudentExamStartController controller = fxmlLoader.getController();
                controller.setExamDetails(selectedExam);
                controller.setUsername(username);
                controller.setID(studentid);

                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentGradeStatistics.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));

            System.out.println("SetStats Username: " + username);
            StudentGradeStatisticsController controller = fxmlLoader.getController();
            controller.setUsername(username);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout() {
        try {
            Stage currentStage = (Stage) mainbox.getScene().getWindow();
            currentStage.close();

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("LoginUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }
}