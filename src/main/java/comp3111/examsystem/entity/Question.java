package comp3111.examsystem.entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Question extends Entity{

//    private StringProperty question = new SimpleStringProperty();
//    private StringProperty optionA = new SimpleStringProperty();
//    private StringProperty optionB = new SimpleStringProperty();
//    private StringProperty optionC = new SimpleStringProperty();
//    private StringProperty optionD = new SimpleStringProperty();
//    private StringProperty answer = new SimpleStringProperty();
//    private StringProperty type = new SimpleStringProperty();
//    private StringProperty score = new SimpleStringProperty();

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String type;
    private double score;

//    private static ObservableList<Question> questionList = FXCollections.observableArrayList(                        new Question("Which of the following is a programming language?", new String[] {"Apple", "Java", "Banana", "Orange"}, "B", "Single", "10"),
//            new Question("What does CPU stand for?", new String[] {"Computer Processor", "Central Processing Unit", "Computer Program", "Centralized Programming"}, "B", "Single", "10"),
//            new Question("Which of the following is a programming language?", new String[] {"Excel", "Photoshop", "Java", "HTML"}, "C", "Single", "10"),
//            new Question("What does RAM stand for in computing?", new String[] {"Random Access Memory", "Read-Only Memory", "Running Applications", "Remote Access Memory"}, "A", "Single", "10"),
//            new Question("Which of the following is NOT a programming language?", new String[] {"Java", "Python", "HTML", "Photoshop"}, "D", "Single", "10"),
//            new Question("What is the main function of a GPU?", new String[] {"Data Storage", "Graphics Rendering", "Network Connection", "Power Supply"}, "B", "Single", "10"),
//            new Question("What does HTML stand for in web development?", new String[] {"Hyper Text Markup", "High Tech Modern", "How to Make Lists", "Home Tool Management"}, "A", "Single", "10"),
//            new Question("Which component of a computer is often referred to as the 'brain'?", new String[] {"Hard Drive", "CPU", "Monitor", "Keyboard"}, "B", "Single", "10"),
//            new Question("Which of the following are object-oriented programming languages?", new String[] {"Java", "Python", "C", "HTML"}, "AB", "Multiple", "20"),
//            new Question("Which of the following are types of computer memory?", new String[] {"RAM", "ROM", "CPU", "HDD"}, "AB", "Multiple", "20"),
//            new Question("Which of the following are essential components of a computer?", new String[] {"Motherboard", "Keyboard", "Monitor", "Mouse"}, "ABC", "Multiple", "20"),
//            new Question("Which of the following are commonly used web browsers?", new String[] {"Google Chrome", "Photoshop", "Safari", "Microsoft Word"}, "AC", "Multiple", "20"),
//            new Question("Which of the following are programming paradigms?", new String[] {"Imperative", "Declarative", "Procedural", "Visual Studio"}, "ABC", "Multiple", "20"));

//    public static ObservableList<Question> getQuestionList() {
//        return questionList;
//    }

    public Question(String question, String[] options, String answer, String type, String score) {

        this.question = question;
        this.optionA = options[0];
        this.optionB = options[1];
        this.optionC = options[2];
        this.optionD = options[3];
        this.answer = answer;
        this.type = type;
        this.score = Double.parseDouble(score);
    }
    public Question() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}