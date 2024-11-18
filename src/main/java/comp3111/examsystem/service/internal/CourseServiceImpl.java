package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.CourseDAO;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;

    public CourseServiceImpl() {
        courseDAO = new CourseDAO();
    }
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
    /**
     * @param course
     */
    @Override
    public void addCourse(Course course) {
        try {
            courseDAO.addCourse(course);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateCourse(String code, Course course) {
        courseDAO.updateCourse(code, course);
    }

    @Override
    public void deleteCourse(String courseID) {
        courseDAO.deleteCourse(courseID);
    }

    @Override
    public List<Course> filterCoursesByDepartment(String department) {
        return courseDAO.filterCoursesByDepartment(department);
    }

    /**
     *
     * @return List<Course>
     */
    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    /**
     * @return
     */
    @Override
    public List<String> getAllCoursesID() {
        return courseDAO.getAllCoursesID();
    }
}
