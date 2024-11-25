package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.ExaminationDAO;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.ExaminationService;

import java.util.List;

public class ExaminationServiceImpl implements ExaminationService {
    private final ExaminationDAO examinationDAO;

    /**
     * Constructor
     */
    public ExaminationServiceImpl() {
        this.examinationDAO = new ExaminationDAO();
    }

    /**
     * Constructor
     * @param examinationDAO self-defined examinationDAO to be used (e.g. mocked class)
     */
    public ExaminationServiceImpl(ExaminationDAO examinationDAO) {
        this.examinationDAO = examinationDAO;
    }

    /**
     * @param examination examination to be added
     */
    @Override
    public void addExamination(Examination examination) {
        examinationDAO.addExamination(examination);
    }

    /**
     * @param id id of examination
     * @return Examination with the given id
     * <p>
     *     The returned examination object do not contain a list of questions linked to it.
     * </p>
     */
    @Override
    public Examination getExamination(int id) {
        return examinationDAO.getExamination(id);
    }

    /**
     * @return List of all examinations
     * <p>
     * the returned examination objects do not contain any questions linked to it.
     * <p/>
     */
    @Override
    public List<Examination> getAllExaminations() {
        return examinationDAO.getAllExaminations();
    }

    /**
     * @param examination examination to be updated
     */
    @Override
    public void updateExamination(Examination examination) {
        examinationDAO.updateExamination(examination);
    }

    /**
     * @param id id of examination
     */
    @Override
    public void deleteExamination(int id) {
        examinationDAO.deleteExamination(id);
    }

    /**
     * @param examinationId id of examination
     * @param questionId id of question to be added
     */
    @Override
    public void addQuestionToExamination(int examinationId, int questionId) throws Exception {
        try {
            examinationDAO.addQuestionToExamination(examinationId, questionId);
        } catch (Exception e) {
            throw e;
        }
    }
    /**
     * @param examinationId id of examination
     * @param questionId id of question to be removed
     */
    @Override
    public void removeQuestionFromExamination(int examinationId, int questionId) {
        examinationDAO.removeQuestionFromExamination(examinationId, questionId);
    }

    /**
     * @param examinationId id of examination
     * @return List of questions in the examination
     */
    @Override
    public List<Question> getQuestionsInExamination(int examinationId) {
        return examinationDAO.getQuestionsInExamination(examinationId);
    }

    @Override
    public float getTotalScoreOfExamination(int examinationId) {
        List<Question> questions = getQuestionsInExamination(examinationId);
        float totalScore = 0;
        for (Question question : questions) {
            totalScore += question.getScore();
        }
        return totalScore;
    }

}
