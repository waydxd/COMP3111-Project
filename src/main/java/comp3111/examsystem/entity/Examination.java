package comp3111.examsystem.entity;


import java.util.List;

public class Examination extends Entity{

    private Boolean publish;
    private String courseID;
    private float examTime;
    private String examName;


//    private static List<Examination> exam_list;
    private List<Question> quiz;

    public Examination(String courseID, float examTime, String examName, List<Question> questionsList, float time, boolean publish) {
        setCourseID(courseID);
        setExamTime(examTime);
        setExamName(examName);

        this.courseID = courseID;
        this.examTime = examTime;
        this.examName = examName;
        quiz = questionsList;
        this.publish = publish;

    }

    public Examination(String courseID, float examTime, String examName, boolean publish) {
        setCourseID(courseID);
        setExamTime(examTime);
        setExamName(examName);
        setPublish(publish);

        this.courseID = courseID;
        this.examTime = examTime;
        this.examName = examName;

        this.publish = publish;
    }

    public Examination() {
        this.publish = false; // Initialize publish to false
    }

    public String getCourseID() {
        return this.courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Float getExamTime() {
        return this.examTime;
    }

    public void setExamTime(float examTime) {
        this.examTime = examTime;
    }

    public Float examTimeProperty() {
        return examTime;
    }

    public String getExamName() {
        return this.examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public List<Question> getQuiz() {
        return quiz;
    }

//    public static List<Examination> get_examlist() {
//        return exam_list;
//    }

    public boolean getPublish() {
        return this.publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

}