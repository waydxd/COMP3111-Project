package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.service.GradeService;
import comp3111.examsystem.service.internal.GradeServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.HashSet;
import java.util.Set;

public class StudentGradeStatisticsController implements Initializable {

    @FXML
    protected ChoiceBox<String> courseCombox;
    @FXML
    protected TableView<Grade> gradeTable;
    @FXML
    private TableColumn<Grade, String> courseColumn;
    @FXML
    private TableColumn<Grade, String> examColumn;
    @FXML
    private TableColumn<Grade, String> scoreColumn;
    @FXML
    private TableColumn<Grade, String> fullScoreColumn;
    @FXML
    private TableColumn<Grade, String> timeSpendColumn;
    @FXML
    BarChart<String, Number> barChart;
    @FXML
    CategoryAxis categoryAxisBar;
    @FXML
    NumberAxis numberAxisBar;

    protected final ObservableList<Grade> gradeList = FXCollections.observableArrayList();

    private String username;

    private GradeService gradeService = new GradeServiceImpl();

    public void setUsername(String username) {
        this.username = username;
        System.out.println("GetGrades Username: " + username);
        loadGrades();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");

        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));


    }

    private void loadGrades() {
        gradeList.clear();
        gradeList.addAll(gradeService.getGradesForUser(username));
        gradeTable.setItems(gradeList);
        initializeChoiceBoxes();
        loadChart(gradeList);
    }

    private void initializeChoiceBoxes() {
        Set<String> courses = new HashSet<>();
        gradeList.forEach(grade -> courses.add(grade.getCourseName()));

        ObservableList<String> courseItems = FXCollections.observableArrayList(courses);
        FXCollections.sort(courseItems);
        courseItems.add(0, "");

        courseCombox.setItems(courseItems);
        courseCombox.setValue("");
    }

    @FXML
    public void refresh() {
        loadGrades();
        query();
    }

    private void loadChart(List<Grade> grades) {
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        Map<String, Double> courseAverages = calculateCourseAverages(grades);
        seriesBar.getData().clear();
        barChart.getData().clear();
        courseAverages.forEach((course, avg) ->
                seriesBar.getData().add(new XYChart.Data<>(course, avg))
        );
        barChart.getData().add(seriesBar);
    }

    protected Map<String, Double> calculateCourseAverages(List<Grade> grades) {
        Map<String, List<Double>> courseScores = new HashMap<>();
        Map<String, Double> averages = new HashMap<>();

        grades.forEach(grade -> {
            String course = grade.getCourseName();
            double score = grade.getScore();
            courseScores.computeIfAbsent(course, k -> FXCollections.observableArrayList())
                    .add(score);
        });

        courseScores.forEach((course, scores) -> {
            double avg = scores.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0.0);
            averages.put(course, avg);
        });

        return averages;
    }

    @FXML
    public void reset() {
        courseCombox.setValue(null);
    }

    @FXML
    public void query() {
        String selectedCourse = courseCombox.getValue();

        ObservableList<Grade> filteredList = FXCollections.observableArrayList();

        for (Grade grade : gradeList) {
            boolean matches = true;
            if (selectedCourse != null && !selectedCourse.isEmpty() && !grade.getCourseName().equals(selectedCourse)) {
                matches = false;
            }
            if (matches) {
                filteredList.add(grade);
            }
        }

        gradeTable.setItems(filteredList);
        loadChart(filteredList);
    }
}