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

public class TeacherGradeStatisticController implements Initializable {
    public static class GradeExampleClass {
        public String getStudentName() {
            return "student";
        }
        public String getCourseNum() {
            return "comp3111";
        }
        public String getExamName() {
            return "final";
        }
        public String getScore() {
            return "100";
        }
        public String getFullScore() {
            return "100";
        }
        public String getTimeSpend() {
            return "60";
        }
    }

    @FXML
    private ChoiceBox<String> courseCombox;
    @FXML
    private ChoiceBox<String> examCombox;
    @FXML
    private ChoiceBox<String> studentCombox;
    @FXML
    private TableView<GradeExampleClass> gradeTable;
    @FXML
    private TableColumn<GradeExampleClass, String> studentColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> courseColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> examColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> scoreColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> fullScoreColumn;
    @FXML
    private TableColumn<GradeExampleClass, String> timeSpendColumn;
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

    private final ObservableList<GradeExampleClass> gradeList = FXCollections.observableArrayList();

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

        gradeList.add(new GradeExampleClass());
        gradeTable.setItems(gradeList);
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseNum"));
        examColumn.setCellValueFactory(new PropertyValueFactory<>("examName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        fullScoreColumn.setCellValueFactory(new PropertyValueFactory<>("fullScore"));
        timeSpendColumn.setCellValueFactory(new PropertyValueFactory<>("timeSpend"));

        refresh();
        loadChart();
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
                        Double.parseDouble(grade.getScore())
                ))
        );

        // Line Chart - Score progression
        XYChart.Series<String, Number> seriesLine = new XYChart.Series<>();
        seriesLine.getData().clear();
        lineChart.getData().clear();
        gradeList.forEach(grade ->
                seriesLine.getData().add(new XYChart.Data<>(
                        grade.getCourseNum() + "-" + grade.getExamName(),
                        Double.parseDouble(grade.getScore())
                ))
        );
        lineChart.getData().add(seriesLine);
    }

    private Map<String, Double> calculateCourseAverages() {
        Map<String, List<Double>> courseScores = new HashMap<>();
        Map<String, Double> averages = new HashMap<>();

        gradeList.forEach(grade -> {
            String course = grade.getCourseNum();
            double score = Double.parseDouble(grade.getScore());
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
    }

    @FXML
    public void query() {
    }
}
