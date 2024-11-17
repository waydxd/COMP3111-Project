package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.ExaminationDAO;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.service.ExaminationService;

import java.util.List;

public class ExaminationServiceImpl implements ExaminationService {
    private final ExaminationDAO examinationDAO;

    public ExaminationServiceImpl() {
        this.examinationDAO = new ExaminationDAO();
    }

    /**
     * @param examination
     */
    @Override
    public void addExamination(Examination examination) {
        examinationDAO.addExamination(examination);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Examination getExamination(int id) {
        return examinationDAO.getExamination(id);
    }

    /**
     * @return
     */
    @Override
    public List<Examination> getAllExaminations() {
        return examinationDAO.getAllExaminations();
    }

    /**
     * @param examination
     */
    @Override
    public void updateExamination(Examination examination) {
        examinationDAO.updateExamination(examination);
    }

    /**
     * @param id
     */
    @Override
    public void deleteExamination(int id) {
        examinationDAO.deleteExamination(id);
    }

    /**
     * @param examinationId
     * @param questionId
     */
    @Override
    public void addQuestionToExamination(int examinationId, int questionId) {

    }

    /**
     * @param examinationId
     * @param questionId
     */
    @Override
    public void removeQuestionFromExamination(int examinationId, int questionId) {

    }
}
