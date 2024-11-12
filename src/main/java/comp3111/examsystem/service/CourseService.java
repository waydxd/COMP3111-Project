package comp3111.examsystem.service;

import comp3111.examsystem.entity.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    void updateCourse(int courseID, String courseName, String department);
    void deleteCourse(int courseID);
    List<Course> filterCoursesByDepartment(String department);
}

