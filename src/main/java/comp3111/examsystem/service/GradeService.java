package comp3111.examsystem.service;

import comp3111.examsystem.entity.Grade;

import java.util.List;

public interface GradeService {
    void addGrade(Grade grade);
    Grade getGrade(int id);
    List<Grade> getAllGrades();
    void updateGrade(Grade grade);
    void deleteGrade(int id);
    List<Grade> getGradesForUser(String username);
}
