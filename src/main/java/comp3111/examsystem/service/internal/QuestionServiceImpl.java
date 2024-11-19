package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.QuestionDAO;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.QuestionService;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDAO questionDAO;

    /**
     * Constructor
     */
    public QuestionServiceImpl() {
        this.questionDAO = new QuestionDAO();
    }

    /**
     * Constructor
     * @param questionDAO self-defined QuestionDAO object for testing
     */
    public QuestionServiceImpl(QuestionDAO questionDAO) {
        this.questionDAO = questionDAO;
    }

    /**
     * @param question question to be added
     */
    @Override
    public void addQuestion(Question question) {
        questionDAO.addQuestion(question);
    }

    /**
     * @param id id of question
     * @return Question with the given id
     */
    @Override
    public Question getQuestion(int id) {
        return questionDAO.getQuestion(id);
    }

    /**
     * @return List of all questions
     */
    @Override
    public List<Question> getAllQuestions() {
        return questionDAO.getAllQuestions();
    }

    /**
     * @param question question to be updated
     * @param id id of question
     */
    @Override
    public void updateQuestion(int id, Question question) {
        question.setId(id);
        questionDAO.updateQuestion(question);
    }

    /**
     * @param id id of question to be deleted
     */
    @Override
    public void deleteQuestion(int id) {
        questionDAO.deleteQuestion(id);
    }
}
