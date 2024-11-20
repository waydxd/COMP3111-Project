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
    void addCourse() throws Exception {
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

    @Test
    void filterCourses() {
        List<Course> sampleCourses = Arrays.asList(
                new Course("COMP1111", "Introduction to Programming", "CS"),
                new Course("CS102", "Data Structures", "Computer Science"),
                new Course("MATH101", "Calculus I", "Math")
        );

        when(courseDAO.getAllCourses()).thenReturn(sampleCourses);

        // Test with all filters
        List<Course> result = courseService.filterCourses("comp1111", "introduction to programming", "cs");
        assertEquals(1, result.size());
        assertEquals("COMP1111", result.get(0).getCourseCode());

        // Test with empty course code
        result = courseService.filterCourses("", "introduction to programming", "cs");
        assertEquals(1, result.size());
        assertEquals("COMP1111", result.get(0).getCourseCode());

        // Test with empty course name
        result = courseService.filterCourses("comp1111", "", "cs");
        assertEquals(1, result.size());
        assertEquals("COMP1111", result.get(0).getCourseCode());

        // Test with empty department
        result = courseService.filterCourses("comp1111", "introduction to programming", "");
        assertEquals(1, result.size());
        assertEquals("COMP1111", result.get(0).getCourseCode());

        // Test with all empty filters
        result = courseService.filterCourses("", "", "");
        assertEquals(3, result.size());

        // Test with no matches
        result = courseService.filterCourses("nonexistent", "nonexistent", "nonexistent");
        assertTrue(result.isEmpty());

        verify(courseDAO, times(6)).getAllCourses();
    }
}