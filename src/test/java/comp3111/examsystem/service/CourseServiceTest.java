package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.CourseDAO;
import comp3111.examsystem.entity.Course;
import comp3111.examsystem.service.internal.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Mock
    private CourseDAO courseDAO;

    @InjectMocks
    private CourseServiceImpl courseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        courseService = new CourseServiceImpl(courseDAO);
    }

    @Test
    void addCourse() {
        Course course = new Course("COMP1111", "Introduction to Programming", "CS");

        courseService.addCourse(course);

        verify(courseDAO).addCourse(course);
    }

    @Test
    void updateCourse() {
        Course course = new Course("COMP1111", "Advanced Programming", "CS");

        courseService.updateCourse("COMP1111", course);

        verify(courseDAO).updateCourse("COMP1111", course);
    }

    @Test
    void deleteCourse() {
        courseService.deleteCourse("COMP1111");

        verify(courseDAO).deleteCourse("COMP1111");
    }

    @Test
    void filterCoursesByDepartment() {
        List<Course> mockCourses = Arrays.asList(
                new Course("COMP1111", "Introduction to Programming", "CS"),
                new Course("COMP2222", "Data Structures", "CS")
        );
        when(courseDAO.filterCoursesByDepartment("CS")).thenReturn(mockCourses);

        List<Course> result = courseService.filterCoursesByDepartment("CS");

        verify(courseDAO).filterCoursesByDepartment("CS");
        assertEquals(mockCourses, result);
    }

    @Test
    void getAllCourses() {
        List<Course> mockCourses = Arrays.asList(
                new Course("COMP1111", "Introduction to Programming", "CS"),
                new Course("COMP2222", "Data Structures", "CS")
        );
        when(courseDAO.getAllCourses()).thenReturn(mockCourses);

        List<Course> result = courseService.getAllCourses();

        verify(courseDAO).getAllCourses();
        assertEquals(mockCourses, result);
    }

    @Test
    void getAllCoursesID() {
        List<String> mockCourseIDs = Arrays.asList("COMP1111", "COMP2222");
        when(courseDAO.getAllCoursesID()).thenReturn(mockCourseIDs);

        List<String> result = courseService.getAllCoursesID();

        verify(courseDAO).getAllCoursesID();
        assertEquals(mockCourseIDs, result);
    }
}