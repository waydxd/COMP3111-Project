package comp3111.examsystem.service;

import comp3111.examsystem.entity.Question;

import java.util.List;

public interface QuestionService {
    void addQuestion(Question question);
    Question getQuestion(int id);
    List<Question> getAllQuestions();
    void updateQuestion(int id, Question question);
    void deleteQuestion(int id);
}