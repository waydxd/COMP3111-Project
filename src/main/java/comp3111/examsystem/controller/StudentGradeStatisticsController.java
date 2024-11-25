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

/**
 * The `StudentGradeStatisticsController` class manages the grade statistics view for students.
 * It displays the grades in a table and a bar chart, and allows filtering by course.
 */
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

    /**
     * Sets the username of the student and loads the grades.
     *
     * @param username the username of the student
     */
    public void setUsername(String username) {
        this.username = username;
        System.out.println("GetGrades Username: " + username);
        loadGrades();
    }

    /**
     * Initializes the controller. This method is called after the FXML file has been loaded.
     *
     * @param url the location used to resolve relative paths for the root object, or null if the location is not known
     * @param resourceBundle the resources used to localize the root object, or null if the root object is not localized
     */
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

    /**
     * Loads the grades for the current user and updates the table and chart.
     */
    private void loadGrades() {
        gradeList.clear();
        gradeList.addAll(gradeService.getGradesForUser(username));
        gradeTable.setItems(gradeList);
        initializeChoiceBoxes();
        loadChart(gradeList);
    }

    /**
     * Initializes the course choice box with the list of courses.
     */
    private void initializeChoiceBoxes() {
        Set<String> courses = new HashSet<>();
        gradeList.forEach(grade -> courses.add(grade.getCourseName()));

        ObservableList<String> courseItems = FXCollections.observableArrayList(courses);
        FXCollections.sort(courseItems);
        courseItems.add(0, "");

        courseCombox.setItems(courseItems);
        courseCombox.setValue("");
    }

    /**
     * Refreshes the grade data and updates the table and chart.
     */
    @FXML
    public void refresh() {
        loadGrades();
        query();
    }

    /**
     * Loads the chart with the average scores for each course.
     *
     * @param grades the list of grades to be displayed in the chart
     */
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

    /**
     * Calculates the average scores for each course.
     *
     * @param grades the list of grades
     * @return a map of course names to average scores
     */
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

    /**
     * Resets the course choice box to its default value.
     */
    @FXML
    public void reset() {
        courseCombox.setValue(null);
    }

    /**
     * Filters the grades based on the selected course and updates the table and chart.
     */
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