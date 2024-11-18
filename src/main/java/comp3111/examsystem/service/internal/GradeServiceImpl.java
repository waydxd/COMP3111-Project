package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.GradeDAO;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.service.GradeService;

import java.util.List;

public class GradeServiceImpl implements GradeService {
    private GradeDAO gradeDAO;

    public GradeServiceImpl() {
        this.gradeDAO = new GradeDAO();
    }

    public GradeServiceImpl(GradeDAO gradeDAO) {
        this.gradeDAO = gradeDAO;
    }

    @Override
    public void addGrade(Grade grade) {
        gradeDAO.addGrade(grade);
    }

    @Override
    public Grade getGrade(int id) {
        return gradeDAO.getGrade(id);
    }

    @Override
    public List<Grade> getAllGrades() {
        return gradeDAO.getAllGrades();
    }

    @Override
    public void updateGrade(Grade grade) {
        gradeDAO.updateGrade(grade);
    }

    @Override
    public void deleteGrade(int id) {
        gradeDAO.deleteGrade(id);
    }
}

