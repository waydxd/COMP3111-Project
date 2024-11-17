package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.QuestionDAO;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.QuestionService;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDAO questionDAO = new QuestionDAO();

    @Override
    public void addQuestion(Question question) {
        questionDAO.addQuestion(question);
    }

    @Override
    public Question getQuestion(int id) {
        return questionDAO.getQuestion(id);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    @Override
    public void updateQuestion(int id, Question question) {
        question.setId(id);
        questionDAO.updateQuestion(question);
    }

    @Override
    public void deleteQuestion(int id) {
        questionDAO.deleteQuestion(id);
    }
}
