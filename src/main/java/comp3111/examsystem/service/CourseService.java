package comp3111.examsystem.service;

import comp3111.examsystem.entity.Course;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    void updateCourse(String code, Course course);
    void deleteCourse(String courseID);
    List<Course> filterCoursesByDepartment(String department);
    List<Course> getAllCourses();
    List<String> getAllCoursesID();
}

