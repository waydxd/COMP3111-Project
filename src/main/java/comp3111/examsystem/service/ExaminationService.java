package comp3111.examsystem.service;

import comp3111.examsystem.entity.Examination;

import java.util.List;

public interface ExaminationService {
    void addExamination(Examination examination);
    Examination getExamination(int id);
    List<Examination> getAllExaminations();
    void updateExamination(Examination examination);
    void deleteExamination(int id);
}