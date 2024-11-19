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
    Grade Grade = new Grade();

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
//    private void addDummyData() {
//
//        Grade grade1 = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45);
//        Grade grade2 = new Grade("Jane Smith", "COMP3111", "Final", 92.0F, 100.0F, 120);
//        Grade grade3 = new Grade("Alice Johnson", "COMP2012", "Quiz 1", 78.5F, 100.0F, 30);
//        Grade grade4 = new Grade("Bob Wilson", "COMP2012", "Midterm", 88.0F, 100.0F, 60);
//        Grade grade5 = new Grade("Charlie Brown", "COMP1022", "Final", 95.5F, 100.0F, 90);
//
//        gradeService.addGrade(grade1);
//        gradeService.addGrade(grade2);
//        gradeService.addGrade(grade3);
//        gradeService.addGrade(grade4);
//        gradeService.addGrade(grade5);
//
//    }

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
//        ObservableList<String> examItems = FXCollections.observableArrayList(exams);
//        ObservableList<String> studentItems = FXCollections.observableArrayList(students);

        // Sort the lists
        FXCollections.sort(courseItems);
//        FXCollections.sort(examItems);
//        FXCollections.sort(studentItems);

        // Add an empty choice as first item
        courseItems.add(0, "");
//        examItems.add(0, "");
//        studentItems.add(0, "");

        // Set items to choice boxes
        courseCombox.setItems(courseItems);
        examTextField.setText("Exam");
        studentTextField.setText("Student");

        // Set default values
        courseCombox.setValue("");
        examTextField.setText("");
        studentTextField.setText("");
    }

    @FXML
    public void refresh() {
        gradeTable.refresh();
        loadChart(gradeList);
    }

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
        examTextField.setText("");
        studentTextField.setText("");
//        gradeTable.setItems(gradeList);
//        loadChart(gradeList);
    }
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
