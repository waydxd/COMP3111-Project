package comp3111.examsystem.entity;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;



public class Examination {
    private StringProperty courseID = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private DoubleProperty examTime = new SimpleDoubleProperty();
    private StringProperty examName = new SimpleStringProperty();

    private StringProperty publish = new SimpleStringProperty();

    private Boolean publish_;
    private String courseID_;
    private int id_;
    private double examTime_;
    private String examName_;

    private Label publishLabel = new Label();

    private static ObservableList<Examination> exam_list = FXCollections.observableArrayList();
    private ObservableList<Question> quiz;

    public Examination(String courseID, int id, double examTime, String examName, ObservableList<Question> questionsList, double time, boolean publish) {
        setCourseID(courseID);
        setId(id);
        setExamTime(examTime);
        setExamName(examName);

        this.courseID_ = courseID;
        this.id_ = id;
        this.examTime_ = examTime;
        this.examName_ = examName;
        quiz = questionsList;
    }

    public Examination(String courseID, int id, double examTime, String examName, boolean publish) {
        setCourseID(courseID);
        setId(id);
        setExamTime(examTime);
        setExamName(examName);
        setPublish(publish);

        this.courseID_ = courseID;
        this.id_ = id;
        this.examTime_ = examTime;
        this.examName_ = examName;

        this.publish_ = publish;
        quiz = FXCollections.observableArrayList();
    }

    public String getCourseID() {
        return courseIDProperty().get();
    }

    public void setCourseID(String courseID) {
        courseIDProperty().set(courseID);
    }

    public StringProperty courseIDProperty() {
        return courseID;
    }

    public int getId() {
        return idProperty().get();
    }

    public void setId(int id) {
        idProperty().set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public double getExamTime() {
        return examTimeProperty().get();
    }

    public void setExamTime(double examTime) {
        examTimeProperty().set(examTime);
    }

    public DoubleProperty examTimeProperty() {
        return examTime;
    }

    public String getExamName() {
        return examNameProperty().get();
    }

    public void setExamName(String examName) {
        examNameProperty().set(examName);
    }

    public StringProperty examNameProperty() {
        return examName;
    }

    public ObservableList<Question> getQuiz() {
        return quiz;
    }

    public static ObservableList<Examination> get_examlist() {
        return exam_list;
    }

    public String getPublish() {
        return publishProperty().get();
    }

    public void setPublish(boolean publish) {
        if (publish) {
            publishProperty().set("yes");
        } else {
            publishProperty().set("no");
        }
    }

    public StringProperty publishProperty() {
        return publish;
    }
}