package comp3111.examsystem.entity;

import Interface.HandleDatabase;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Question implements HandleDatabase {

    private StringProperty question = new SimpleStringProperty();
    private StringProperty optionA = new SimpleStringProperty();
    private StringProperty optionB = new SimpleStringProperty();
    private StringProperty optionC = new SimpleStringProperty();
    private StringProperty optionD = new SimpleStringProperty();
    private StringProperty answer = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty score = new SimpleStringProperty();

    private int id;
    private String question_;
    private String optionA_;
    private String optionB_;
    private String optionC_;
    private String optionD_;
    private String answer_;
    private String type_;
    private double score_;

    private static ObservableList<Question> questionList = FXCollections.observableArrayList(                        new Question("Which of the following is a programming language?", new String[] {"Apple", "Java", "Banana", "Orange"}, "B", "Single", "10"),
            new Question("What does CPU stand for?", new String[] {"Computer Processor", "Central Processing Unit", "Computer Program", "Centralized Programming"}, "B", "Single", "10"),
            new Question("Which of the following is a programming language?", new String[] {"Excel", "Photoshop", "Java", "HTML"}, "C", "Single", "10"),
            new Question("What does RAM stand for in computing?", new String[] {"Random Access Memory", "Read-Only Memory", "Running Applications", "Remote Access Memory"}, "A", "Single", "10"),
            new Question("Which of the following is NOT a programming language?", new String[] {"Java", "Python", "HTML", "Photoshop"}, "D", "Single", "10"),
            new Question("What is the main function of a GPU?", new String[] {"Data Storage", "Graphics Rendering", "Network Connection", "Power Supply"}, "B", "Single", "10"),
            new Question("What does HTML stand for in web development?", new String[] {"Hyper Text Markup", "High Tech Modern", "How to Make Lists", "Home Tool Management"}, "A", "Single", "10"),
            new Question("Which component of a computer is often referred to as the 'brain'?", new String[] {"Hard Drive", "CPU", "Monitor", "Keyboard"}, "B", "Single", "10"),
            new Question("Which of the following are object-oriented programming languages?", new String[] {"Java", "Python", "C", "HTML"}, "AB", "Multiple", "20"),
            new Question("Which of the following are types of computer memory?", new String[] {"RAM", "ROM", "CPU", "HDD"}, "AB", "Multiple", "20"),
            new Question("Which of the following are essential components of a computer?", new String[] {"Motherboard", "Keyboard", "Monitor", "Mouse"}, "ABC", "Multiple", "20"),
            new Question("Which of the following are commonly used web browsers?", new String[] {"Google Chrome", "Photoshop", "Safari", "Microsoft Word"}, "AC", "Multiple", "20"),
            new Question("Which of the following are programming paradigms?", new String[] {"Imperative", "Declarative", "Procedural", "Visual Studio"}, "ABC", "Multiple", "20"));

    public static ObservableList<Question> getQuestionList() {
        return questionList;
    }

    public Question(String question, String[] options, String answer, String type, String score) {
        setQuestion(question);
        setOptionA(options[0]);
        setOptionB(options[1]);
        setOptionC(options[2]);
        setOptionD(options[3]);
        setAnswer(answer);
        setType(type);
        setScore(score);

        this.question_ = question;
        this.optionA_ = options[0];
        this.optionB_ = options[1];
        this.optionC_ = options[2];
        this.optionD_ = options[3];
        this.answer_ = answer;
        this.type_ = type;
        this.score_ = Double.parseDouble(score);
    }

    public String getQuestion() {
        return questionProperty().get();
    }

    public void setQuestion(String question) {
        questionProperty().set(question);
    }

    public StringProperty questionProperty() {
        return question;
    }

    public String getOptionA() {
        return optionAProperty().get();
    }

    public void setOptionA(String optionA) {
        optionAProperty().set(optionA);
    }

    public StringProperty optionAProperty() {
        return optionA;
    }

    public String getOptionB() {
        return optionBProperty().get();
    }

    public void setOptionB(String optionB) {
        optionBProperty().set(optionB);
    }

    public StringProperty optionBProperty() {
        return optionB;
    }

    public String getOptionC() {
        return optionCProperty().get();
    }

    public void setOptionC(String optionC) {
        optionCProperty().set(optionC);
    }

    public StringProperty optionCProperty() {
        return optionC;
    }

    public String getOptionD() {
        return optionDProperty().get();
    }

    public void setOptionD(String optionD) {
        optionDProperty().set(optionD);
    }

    public StringProperty optionDProperty() {
        return optionD;
    }

    public String getAnswer() {
        return answerProperty().get();
    }

    public void setAnswer(String answer) {
        answerProperty().set(answer);
    }

    public StringProperty answerProperty() {
        return answer;
    }

    public String getType() {
        return typeProperty().get();
    }

    public void setType(String type) {
        typeProperty().set(type);
    }

    public StringProperty typeProperty() {
        return type;
    }


    public void setScore(String score) {
        scoreProperty().set(score);
    }

    public StringProperty scoreProperty() {
        return score;
    }

    public String getScore() {
        return scoreProperty().get();
    }


    @Override
    public void Read() {
       //Change null to database's all questions
        questionList=null;
    }

    @Override
    public void Write() {
        //Change null to database's all questions
        questionList=null;

    }
}