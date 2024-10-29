package comp3111.examsystem.controller;

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
import comp3111.examsystem.entity.Grade;

public class TeacherGradeStatisticController implements Initializable {
    Grade Grade = new Grade();

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private ChoiceBox<String> examCombox;
    @FXML
    private ChoiceBox<String> studentCombox;
    @FXML
    private TableView<Grade> gradeTable;
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

    private final ObservableList<Grade> gradeList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        barChart.setLegendVisible(false);
        categoryAxisBar.setLabel("Course");
        numberAxisBar.setLabel("Avg. Score");
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Student Scores");
        lineChart.setLegendVisible(false);
        categoryAxisLine.setLabel("Exam");
        numberAxisLine.setLabel("Avg. Score");

        addDummyData();

        gradeList.add(new Grade());
        gradeTable.setItems(gradeList);
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpent"));

        initializeChoiceBoxes();

        refresh();
        loadChart();
    }
    private void addDummyData() {
        Grade grade1 = new Grade();
        grade1.setStudentName("John Doe");
        grade1.setCourseName("COMP3111");
        grade1.setExamName("Midterm");
        grade1.setScore(85.5);
        grade1.setFullScore(100.0);
        grade1.setTimeSpent(45);

        Grade grade2 = new Grade();
        grade2.setStudentName("Jane Smith");
        grade2.setCourseName("COMP3111");
        grade2.setExamName("Final");
        grade2.setScore(92.0);
        grade2.setFullScore(100.0);
        grade2.setTimeSpent(120);

        Grade grade3 = new Grade();
        grade3.setStudentName("Alice Johnson");
        grade3.setCourseName("COMP2012");
        grade3.setExamName("Quiz 1");
        grade3.setScore(78.5);
        grade3.setFullScore(100.0);
        grade3.setTimeSpent(30);

        Grade grade4 = new Grade();
        grade4.setStudentName("Bob Wilson");
        grade4.setCourseName("COMP2012");
        grade4.setExamName("Midterm");
        grade4.setScore(88.0);
        grade4.setFullScore(100.0);
        grade4.setTimeSpent(60);

        Grade grade5 = new Grade();
        grade5.setStudentName("Charlie Brown");
        grade5.setCourseName("COMP1022");
        grade5.setExamName("Final");
        grade5.setScore(95.5);
        grade5.setFullScore(100.0);
        grade5.setTimeSpent(90);

        gradeList.addAll(grade1, grade2, grade3, grade4, grade5);
    }

    private void initializeChoiceBoxes() {
        // Get unique values
        Set<String> courses = new HashSet<>();
        Set<String> exams = new HashSet<>();
        Set<String> students = new HashSet<>();

        gradeList.forEach(grade -> {
            courses.add(grade.getCourseName());
            exams.add(grade.getExamName());
            students.add(grade.getStudentName());
        });

        // Convert sets to sorted lists
        ObservableList<String> courseItems = FXCollections.observableArrayList(courses);
        ObservableList<String> examItems = FXCollections.observableArrayList(exams);
        ObservableList<String> studentItems = FXCollections.observableArrayList(students);

        // Sort the lists
        FXCollections.sort(courseItems);
        FXCollections.sort(examItems);
        FXCollections.sort(studentItems);

        // Add an empty choice as first item
        courseItems.add(0, "");
        examItems.add(0, "");
        studentItems.add(0, "");

        // Set items to choice boxes
        courseCombox.setItems(courseItems);
        examCombox.setItems(examItems);
        studentCombox.setItems(studentItems);

        // Set default values
        courseCombox.setValue("");
        examCombox.setValue("");
        studentCombox.setValue("");
    }

    @FXML
    public void refresh() {
        gradeTable.refresh();
        loadChart();
    }

    private void loadChart() {
        // Bar Chart - Average score per course
        XYChart.Series<String, Number> seriesBar = new XYChart.Series<>();
        Map<String, Double> courseAverages = calculateCourseAverages();
        seriesBar.getData().clear();
        barChart.getData().clear();
        courseAverages.forEach((course, avg) ->
                seriesBar.getData().add(new XYChart.Data<>(course, avg))
        );
        barChart.getData().add(seriesBar);

        // Pie Chart - Student score distribution
        pieChart.getData().clear();
        gradeList.forEach(grade ->
                pieChart.getData().add(new PieChart.Data(
                        grade.getStudentName(),
                        grade.getScore()
                ))
        );

        // Line Chart - Score progression
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.getData().clear();
        lineChart.getData().clear();
        gradeList.forEach(grade ->
                seriesLine.getData().add(new XYChart.Data<>(
                        grade.getCourseName() + "-" + grade.getExamName(),
                        grade.getScore()
                ))
        );
        lineChart.getData().add(seriesLine);
    }

    private Map<String, Double> calculateCourseAverages() {
        Map<String, List<Double>> courseScores = new HashMap<>();
        Map<String, Double> averages = new HashMap<>();

        gradeList.forEach(grade -> {
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
        examCombox.setValue(null);
        studentCombox.setValue(null);
        gradeTable.setItems(gradeList);
        loadChart();
    }

    @FXML
    public void query() {
        String selectedCourse = courseCombox.getValue();
        String selectedExam = examCombox.getValue();
        String selectedStudent = studentCombox.getValue();

        ObservableList<Grade> filteredList = FXCollections.observableArrayList();

        for (Grade grade : gradeList) {
            boolean matches = true;
            if (selectedCourse != null && !selectedCourse.isEmpty() && !grade.getCourseName().equals(selectedCourse)) {
                matches = false;
            }
            if (selectedExam != null && !selectedExam.isEmpty() && !grade.getExamName().equals(selectedExam)) {
                matches = false;
            }
            if (selectedStudent != null && !selectedStudent.isEmpty() && !grade.getStudentName().equals(selectedStudent)) {
                matches = false;
            }
            if (matches) {
                filteredList.add(grade);
            }
        }

        gradeTable.setItems(filteredList);
        loadChart();
    }

}
