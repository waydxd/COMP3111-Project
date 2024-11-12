package comp3111.examsystem.service;

import comp3111.examsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {
    void addTeacher(Teacher teacher);
    Teacher getTeacher(int id);
    List<Teacher> getAllTeachers();
}