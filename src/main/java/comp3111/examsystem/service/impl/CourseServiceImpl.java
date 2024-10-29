package comp3111.examsystem.service.impl;

import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.CourseService;
import comp3111.examsystem.service.service;
import comp3111.examsystem.dao.CourseDAOImpl;

import java.util.List;

public class CourseServiceImpl extends service implements CourseService {
    private final CourseDAOImpl courseDAO = new CourseDAOImpl();

    public void addCourse(String courseCode, String courseName, String instructorName) {
        courseDAO.addCourse(courseCode, courseName, instructorName);
    }

    @Override
    public void updateCourse(String courseID, String courseName, String department) {
        courseDAO.updateCourse(courseID, courseName, department);
    }

    @Override
    public void deleteCourse(String courseID) {
        courseDAO.deleteCourse(courseID);
    }

    @Override
    public List<Course> filterCoursesByDepartment(String department) {
        return courseDAO.filterCoursesByDepartment(department);
    }
}
