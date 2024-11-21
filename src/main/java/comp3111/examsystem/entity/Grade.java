package comp3111.examsystem.entity;

public class Grade extends Entity {
    private String studentName;
    private String userName;
    private String courseName;
    private String examName;
    private float score;
    private float fullScore;
    private float timeSpent;

    /**
     * Default constructor with default values
     */
    public Grade() {
        this.id = 0;
        this.studentName = "Default Student";
        this.courseName = "Default Course";
        this.examName = "Default Exam";
        this.score = 0.0F;
        this.fullScore = 100.0F;
        this.timeSpent = 0;
        this.userName = "Default User";
    }

    /**
     * Constructor with parameters
     * @param studentName student name
     * @param courseName course name
     * @param examName exam name
     * @param score score
     * @param fullScore full score
     * @param timeSpent time spent
     */
    public Grade(String studentName, String courseName, String examName, float score, float fullScore, float timeSpent, String userName) {
        this.studentName = studentName;
        this.courseName = courseName;
        this.examName = examName;
        this.score = score;
        this.fullScore = fullScore;
        this.timeSpent = timeSpent;
        this.userName = userName;
    }

    /**
     * @return student name
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @return full score
     */
    public float getFullScore() {
        return fullScore;
    }

    /**
     * @return score
     */
    public float getScore() {
        return score;
    }

    /**
     * @return time spent
     */
    public float getTimeSpent() {
        return timeSpent;
    }

    /**
     * @return course name
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @return exam name
     */
    public String getExamName() {
        return examName;
    }

    /**
     * @param studentName student name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @param courseName course name
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * @param examName exam name
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * @param score score
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * @param fullScore full score
     */
    public void setFullScore(float fullScore) {
        this.fullScore = fullScore;
    }

    /**
     * @param timeSpent time spent
     */
    public void setTimeSpent(float timeSpent) {
        this.timeSpent = timeSpent;
    }

    /**
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}