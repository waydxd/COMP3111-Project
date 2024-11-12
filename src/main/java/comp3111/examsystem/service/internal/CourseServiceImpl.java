package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.CourseDAO;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.CourseService;
import comp3111.examsystem.service.service;

import java.util.List;

public class CourseServiceImpl extends service implements CourseService {
    private final CourseDAO courseDAO = new CourseDAO();

    /**
     * @param course
     */
    @Override
    public void addCourse(Course course) {
        courseDAO.addCourse(course);
    }

    @Override
    public void updateCourse(int courseID, String courseName, String department) {
        courseDAO.updateCourse(courseID, courseName, department);
    }

    @Override
    public void deleteCourse(int courseID) {
        courseDAO.deleteCourse(courseID);
    }

    @Override
    public List<Course> filterCoursesByDepartment(String department) {
        return courseDAO.filterCoursesByDepartment(department);
    }
}
