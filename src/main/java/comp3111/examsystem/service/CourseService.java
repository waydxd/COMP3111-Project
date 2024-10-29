package comp3111.examsystem.service;

import comp3111.examsystem.entity.Course;

import java.util.List;

public interface CourseService {
    void addCourse(String courseCode, String courseName, String instructorName);
    void updateCourse(String courseID, String courseName, String department);
    void deleteCourse(String courseID);
    List<Course> filterCoursesByDepartment(String department);
}

