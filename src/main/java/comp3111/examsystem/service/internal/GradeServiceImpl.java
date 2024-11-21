package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.GradeDAO;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.service.GradeService;

import java.util.List;

public class GradeServiceImpl implements GradeService {
    private final GradeDAO gradeDAO;
    /**
     * Constructor
     */
    public GradeServiceImpl() {
        this.gradeDAO = new GradeDAO();
    }
    /**
     * Constructor
     * @param gradeDAO self-defined GradeDAO object for testing
     */
    public GradeServiceImpl(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }
    /**
     * @param grade grade to be added
     */
    @Override
    public void addGrade(Grade grade) {
        gradeDAO.addGrade(grade);
    }

    /**
     * @param id id of grade
     * @return Grade with the given id
     */
    @Override
    public Grade getGrade(int id) {
        return gradeDAO.getGrade(id);
    }

    /**
     * @return List of all grades
     */
    @Override
    public List<Grade> getAllGrades() {
        return gradeDAO.getAllGrades();
    }
    /**
     * @param grade grade to be updated
     */
    @Override
    public void updateGrade(Grade grade) {
        gradeDAO.updateGrade(grade);
    }
    /**
     * @param id id of grade to be deleted
     */
    @Override
    public void deleteGrade(int id) {
        gradeDAO.deleteGrade(id);
    }

    /**
     * @param username username of the user
     * @return List of grades with the given username
     */
    @Override
    public List<Grade> getGradesForUser(String username) {
        return gradeDAO.getGradesForUser(username);
    }
}

