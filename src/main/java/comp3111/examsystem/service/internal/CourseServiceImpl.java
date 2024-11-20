package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.CourseDAO;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.CourseService;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    private final CourseDAO courseDAO;

    /**
     * Constructor
     */
    public CourseServiceImpl() {
        courseDAO = new CourseDAO();
    }
    /**
     * Constructor
     * @param courseDAO self-defined CourseDAO object for testing
     */
    public CourseServiceImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }
    /**
     * @param course course to be added
     */
    @Override
    public void addCourse(Course course) throws Exception {
        try {
            courseDAO.addCourse(course);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * @param code code of course
     * <p>
     *             Update the course with the given code and the new course object
     *             </p>
     */
    @Override
    public void updateCourse(String code, Course course) {
        courseDAO.updateCourse(code, course);
    }

    /**
     * @param courseID courseID of course
     * <p>
     *             delete the course with the given courseID
     *             </p>
     *
     */
    @Override
    public void deleteCourse(String courseID) {
        courseDAO.deleteCourse(courseID);
    }

    /**
     * @param courseCode
     * @param courseName
     * @param department
     * @return
     */
    @Override
    public List<Course> filterCourses(String courseCode, String courseName, String department) {
        List<Course> course = courseDAO.getAllCourses();
        return course.stream()
                .filter(Course -> (courseCode.isEmpty()|| Course.getCourseCode().toLowerCase().contains(courseCode)))
                .filter(Course -> (courseName.isEmpty() || Course.getName().toLowerCase().contains(courseName)))
                .filter(Course -> (department.isEmpty() || Course.getDepartment().toLowerCase().contains(department)))
                .toList();
    }

//    /**
//     * @param department filterCoursesByDepartment
//     * @return Course with the given courseID
//     */
//    @Override
//    public List<Course> filterCoursesByDepartment(String department) {
//        return courseDAO.filterCoursesByDepartment(department);
//    }

    /**
     *
     * @return List of Courses
     */
    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    /**
     * @return List of all courses' ID
     */
    @Override
    public List<String> getAllCoursesID() {
        return courseDAO.getAllCoursesID();
    }
}
