package comp3111.examsystem.service;

import comp3111.examsystem.entity.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course) throws Exception;
    void updateCourse(String code, Course course);
    void deleteCourse(String courseID);
    List<Course> filterCourses(String courseCode, String courseName ,String department);
    List<Course> getAllCourses();
    List<String> getAllCoursesID();
}

