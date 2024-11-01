package comp3111.examsystem.entity;

public class Grade extends Entity {
    private String studentName;
    private String courseName;
    private String examName;
    private float score;
    private float fullScore;
    private float timeSpent;

    // Default constructor with default values
    public Grade() {
        this.id = 0;
        this.studentName = "Default Student";
        this.courseName = "Default Course";
        this.examName = "Default Exam";
        this.score = 0.0F;
        this.fullScore = 100.0F;
        this.timeSpent = 0;
    }
    public Grade(String studentName, String courseName, String examName, float score, float fullScore, float timeSpent) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.examName = examName;
        this.score = score;
        this.fullScore = fullScore;
        this.timeSpent = timeSpent;
    }
    public String getStudentName() {
        return studentName;
    }

    public float getFullScore() {
        return fullScore;
    }

    public float getScore() {
        return score;
    }

    public float getTimeSpent() {
        return timeSpent;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getExamName() {
        return examName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setFullScore(float fullScore) {
        this.fullScore = fullScore;
    }

    public void setTimeSpent(float timeSpent) {
        this.timeSpent = timeSpent;
    }

}