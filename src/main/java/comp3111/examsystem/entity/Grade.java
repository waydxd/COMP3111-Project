package comp3111.examsystem.entity;

public class Grade extends Entity {
    private String studentName;
    private String courseName;
    private String examName;
    private double score;
    private double fullScore;
    private double timeSpent;

    // Default constructor with default values
    public Grade() {
        this.id = 0L;
        this.studentName = "Default Student";
        this.courseName = "Default Course";
        this.examName = "Default Exam";
        this.score = 0.0;
        this.fullScore = 100.0;
        this.timeSpent = 0;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getFullScore() {
        return fullScore;
    }

    public double getScore() {
        return score;
    }

    public double getTimeSpent() {
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

    public void setScore(double score) {
        this.score = score;
    }

    public void setFullScore(double fullScore) {
        this.fullScore = fullScore;
    }

    public void setTimeSpent(double timeSpent) {
        this.timeSpent = timeSpent;
    }

}