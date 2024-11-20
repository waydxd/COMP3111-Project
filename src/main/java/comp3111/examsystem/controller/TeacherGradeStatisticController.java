package comp3111.examsystem.controller;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.HashSet;
import java.util.Set;
import comp3111.examsystem.entity.Grade;

public class TeacherGradeStatisticController implements Initializable {

    @FXML
    protected ChoiceBox<String> courseCombox;
    @FXML
    protected TextField examTextField;
    @FXML
    protected TextField studentTextField;
    @FXML
    protected TableView<Grade> gradeTable;
    @FXML
    private TableColumn<Grade, String> studentColumn;
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
    @FXML
    LineChart<String, Number> lineChart;
    @FXML
    CategoryAxis categoryAxisLine;
    @FXML
    NumberAxis numberAxisLine;
    @FXML
    PieChart pieChart;

    protected final ObservableList<Grade> gradeList = FXCollections.observableArrayList();

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * <p>
     * This method performs the following tasks:
     * 1. Initializes the GradeService.
     * 2. Configures the bar chart, pie chart, and line chart.
     * 3. Populates the grade list with data from the GradeService.
     * 4. Sets up the table columns with the appropriate cell value factories.
     * 5. Initializes the choice boxes with unique values.
     * 6. Refreshes the table and loads the charts with the initial data.
     *                       <p/>
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GradeService gradeService = new GradeServiceImpl();

        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Student Scores");
        lineChart.setLegendVisible(false);
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Avg. Score");

        gradeList.addAll(gradeService.getAllGrades());
        gradeTable.setItems(gradeList);
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));

        initializeChoiceBoxes();

        refresh();
        loadChart(gradeList);
    }

    /**
     * Initializes the choice boxes with unique values.
     * This method performs the following tasks:
     * 1. Creates sets to store unique course names, exam names, and student names.
     * 2. Converts the sets to sorted lists.
     * 3. Adds an empty choice as the first item in each list.
     * 4. Sets the items of the choice boxes to the sorted lists.
     * 5. Sets the default values of the choice boxes to empty strings.
     */
    private void initializeChoiceBoxes() {
        // Get unique values
        Set<String> courses = new HashSet<>();


        gradeList.forEach(grade -> {
            courses.add(grade.getCourseName());
        });

        // Convert sets to sorted lists
        ObservableList<String> courseItems = FXCollections.observableArrayList(courses);

        // Sort the lists
        FXCollections.sort(courseItems);

        // Add an empty choice as first item
        courseItems.addFirst("");


        // Set items to choice boxes
        courseCombox.setItems(courseItems);
        examTextField.setText("Exam");
        studentTextField.setText("Student");

        // Set default values
        courseCombox.setValue("");
        examTextField.setText("");
        studentTextField.setText("");
    }

    /**
     * Refreshes the table and loads the charts with the latest data.
     */
    @FXML
    public void refresh() {
        gradeTable.refresh();
        loadChart(gradeList);
    }

    /**
     * Loads the bar chart, pie chart, and line chart with the latest data.
     * This method performs the following tasks:
     * 1. Calculates the average score per course.
     * 2. Populates the bar chart with the average scores.
     * 3. Populates the pie chart with the student scores.
     * 4. Populates the line chart with the score progression.
     */
    private void loadChart(List<Grade> grades) {
        // Bar Chart - Average score per course
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        Map<String, Double> courseAverages = calculateCourseAverages(grades);
        seriesBar.getData().clear();
        barChart.getData().clear();
        courseAverages.forEach((course, avg) ->
                seriesBar.getData().add(new XYChart.Data<>(course, avg))
        );
        barChart.getData().add(seriesBar);

        // Pie Chart - Student score distribution
        pieChart.getData().clear();
        grades.forEach(grade ->
                pieChart.getData().add(new PieChart.Data(
                        grade.getStudentName(),
                        grade.getScore()
                ))
        );

        // Line Chart - Score progression
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.getData().clear();
        lineChart.getData().clear();
        grades.forEach(grade ->
                seriesLine.getData().add(new XYChart.Data<>(
                        grade.getCourseName() + "-" + grade.getExamName(),
                        grade.getScore()
                ))
        );
        lineChart.getData().add(seriesLine);
    }

    /**
     * Calculates the average score per course.
     * This method performs the following tasks:
     * 1. Creates a map to store course names and their corresponding scores.
     * 2. Creates a map to store course names and their corresponding averages.
     * 3. Iterates through the grade list and populates the course scores map.
     * 4. Iterates through the course scores map and calculates the averages.
     *
     * @return A map of course names and their corresponding averages.
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
     * Resets the choice boxes and table to their initial state.
     */
    @FXML
    public void reset() {
        courseCombox.setValue(null);
        examTextField.setText("");
        studentTextField.setText("");
    }

  /**
     * Queries the grade list based on the selected course, exam, and student.
     * This method performs the following tasks:
     * 1. Gets the selected course, exam, and student from the choice boxes.
     * 2. Filters the grade list based on the selected values.
     * 3. Sets the filtered list to the table.
     * 4. Loads the charts with the filtered data.
     */
    @FXML
    public void query() {
        String selectedCourse = courseCombox.getValue();
        String selectedExam = examTextField.getText();
        String selectedStudent = studentTextField.getText();

        ObservableList<Grade> filteredList = FXCollections.observableArrayList();

        for (Grade grade : gradeList) {
            boolean matches = true;
            if (selectedCourse != null && !selectedCourse.isEmpty() && !grade.getCourseName().equals(selectedCourse)) {
                matches = false;
            }
            if (selectedExam != null && !selectedExam.isEmpty() && !grade.getExamName().contains(selectedExam)) {
                matches = false;
            }
            if (selectedStudent != null && !selectedStudent.isEmpty() && !grade.getStudentName().contains(selectedStudent)) {
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
