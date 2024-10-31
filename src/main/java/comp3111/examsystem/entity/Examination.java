package comp3111.examsystem.entity;

public class Examination extends Entity {
    private int CourseID;
    private double examTime;
    private String examName;
    private Question[] questions_list;
    private int totalScore;
}
