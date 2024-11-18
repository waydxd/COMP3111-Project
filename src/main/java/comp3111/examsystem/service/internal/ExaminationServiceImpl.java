package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.ExaminationDAO;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.entity.Question;
import comp3111.examsystem.service.ExaminationService;

import java.util.List;

public class ExaminationServiceImpl implements ExaminationService {
    private final ExaminationDAO examinationDAO;

    public ExaminationServiceImpl() {
        this.examinationDAO = new ExaminationDAO();
    }

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
     */
    @Override
    public Examination getExamination(int id) {
        return examinationDAO.getExamination(id);
    }

    /**
     * @return List of all examinations
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
}
