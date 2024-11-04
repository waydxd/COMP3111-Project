package comp3111.examsystem.entity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Question {
    private StringProperty question = new SimpleStringProperty();
    private StringProperty optionA = new SimpleStringProperty();
    private StringProperty optionB = new SimpleStringProperty();
    private StringProperty optionC = new SimpleStringProperty();
    private StringProperty optionD = new SimpleStringProperty();
    private StringProperty answer = new SimpleStringProperty();
    private StringProperty type = new SimpleStringProperty();
    private StringProperty score = new SimpleStringProperty();



    public Question(String question, String[] options, String answer, String type, String score) {
        setQuestion(question);
        setOptionA(options[0]);
        setOptionB(options[1]);
        setOptionC(options[2]);
        setOptionD(options[3]);
        setAnswer(answer);
        setType(type);
        setScore(score);
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
}