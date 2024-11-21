package comp3111.examsystem.service;

import comp3111.examsystem.entity.Teacher;

import java.util.List;

public interface TeacherService {
    void addTeacher(Teacher teacher);
    Teacher getTeacher(int id);
    List<Teacher> getAllTeachers();
    void updateTeacher(int id, Teacher teacher);
    void deleteTeacher(int id);
    List<Teacher> filterTeachers(String username, String name, String department);
    public boolean account_exist(String user);
    public Teacher getTeacherbyUserName(String username);

}