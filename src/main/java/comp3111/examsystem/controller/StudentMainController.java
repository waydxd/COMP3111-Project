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

/**
 * The `StudentMainController` class manages the main UI for students.
 * It allows students to view and select published exams, open the exam UI, view grade statistics, and log out.
 */
public class StudentMainController implements Initializable {
    @FXML
    private ComboBox<Examination> examCombox;

    private final ExaminationService examinationService = new ExaminationServiceImpl();

    @FXML
    private VBox mainbox;

    private String username;

    /**
     * Sets the username of the student.
     *
     * @param username the username of the student
     */
    public void setUsername(String username) {
        this.username = username;
    }

    private int studentid;

    /**
     * Sets the ID of the student.
     *
     * @param studentid the ID of the student
     */
    public void setID(int studentid) {
        this.studentid = studentid;
    }

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param location  the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resources the resources used to localize the root object, or null if the root object is not localized
     */
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

    /**
     * Loads the published exams into the ComboBox.
     */
    @FXML
    private void loadPublishedExams() {
        List<Examination> exams = examinationService.getAllExaminations();
        List<Examination> publishedExams = exams.stream()
                .filter(Examination::getPublish)
                .collect(Collectors.toList());
        examCombox.getItems().addAll(publishedExams);
    }


    /**
     * Opens the exam UI for the selected exam.
     */
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

    /**
     * Opens the grade statistics UI.
     */
    @FXML
    public void openGradeStatistic() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentGradeStatistics.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Grade Statistics");
            stage.setScene(new Scene(fxmlLoader.load()));

            StudentGradeStatisticsController controller = fxmlLoader.getController();
            controller.setUsername(username);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Logs out the student and returns to the login UI.
     */
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

    /**
     * Exits the application.
     */
    @FXML
    public void exit() {
        System.exit(0);
    }
}