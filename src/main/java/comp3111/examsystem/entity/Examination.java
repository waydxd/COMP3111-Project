package comp3111.examsystem.entity;


import java.util.ArrayList;
import java.util.List;

public class Examination extends Entity{

    private Boolean publish;
    private String courseID;
    private float examTime;
    private String examName;
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

    /**
     * Constructor with parameters
     * @param courseID course ID
     * @param examTime exam time
     * @param examName exam name
     * @param publish publish status
     */
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

    /**
     * Default constructor
     */
    public Examination() {
        this.publish = false; // Initialize publish to false
    }

    /**
     *  Get course ID
     */
    public String getCourseID() {
        return this.courseID;
    }

    /**
     * Set course ID
     * @param courseID course ID
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * Get exam time
     * @return exam time
     */
    public Float getExamTime() {
        return this.examTime;
    }

    /**
     * Set exam time
     * @param examTime exam time
     */
    public void setExamTime(float examTime) {
        this.examTime = examTime;
    }

    /**
     * Get exam name
     * @return exam name
     */
    public String getExamName() {
        return this.examName;
    }

    /**
     * Set exam name
     * @param examName exam name
     */
    public void setExamName(String examName) {
        this.examName = examName;
    }

    /**
     * Get list of questions in the examination
     * @return list of questions
     */
    public List<Question> getQuiz() {
        return quiz;
    }

    /**
     * Get publish status
     * @return publish status
     */
    public boolean getPublish() {
        return this.publish;
    }

    /**
     * Set publish status
     * @param publish publish status
     */
    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    /**
     * Set list of questions in the examination
     * @param quiz list of questions
     */
    public void setQuiz(List<Question> quiz) {
        this.quiz = quiz;
    }

    /**
     * @return total score of the examination
     * <p>
     *     This function calculates the total score of the examination by summing the scores of all questions in the examination.
     *     You should specify the quiz array before calling this function. Otherwise, it will always return 0.
     * </p>
     */
    public float getTotalScore() {
        if(quiz == null) return 0;
        float totalScore = 0;
        for (Question q : quiz) {
            totalScore += q.getScore();
        }
        return totalScore;
    }
}