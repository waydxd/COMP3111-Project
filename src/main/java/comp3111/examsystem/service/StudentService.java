package comp3111.examsystem.service;

import comp3111.examsystem.entity.Student;

import java.util.List;

public interface StudentService{
    void addStudent(Student student);
    Student getStudent(int id);
    List<Student> getAllStudents();
    void updateStudent(int id ,Student student);
    void deleteStudent(int id);
    List<Student> filterStudents(String username, String name, String department);
    boolean login(String username, String password);
}
