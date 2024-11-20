package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeacherServiceTest {

    @Mock
    private MemberDAO teacherDAO;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teacherService = new TeacherServiceImpl(teacherDAO);
    }

    @Test
    void addTeacher() {
        Teacher teacher = new Teacher("user1", "password1", "John Doe", "Male", "35", "Math", "Professor");

        teacherService.addTeacher(teacher);

        verify(teacherDAO).addTeacher(teacher);
    }

    @Test
    void getTeacher() {
        Teacher mockTeacher = new Teacher("user1", "password1", "John Doe", "Male", "35", "Math", "Professor");
        when(teacherDAO.getTeacher(1)).thenReturn(mockTeacher);

        Teacher result = teacherService.getTeacher(1);

        verify(teacherDAO).getTeacher(1);
        assertNotNull(result);
        assertEquals(mockTeacher.getUsername(), result.getUsername());
        assertEquals(mockTeacher.getName(), result.getName());
    }

    @Test
    void getAllTeachers() {
        List<Teacher> mockTeachers = Arrays.asList(new Teacher(), new Teacher());
        when(teacherDAO.getAllTeachers()).thenReturn(mockTeachers);

        List<Teacher> result = teacherService.getAllTeachers();

        verify(teacherDAO).getAllTeachers();
        assertEquals(mockTeachers, result);
    }

    @Test
    void updateTeacher() {
        Teacher teacher = new Teacher("user1", "password1", "John Doe", "Male", "35", "Math", "Professor");
        teacher.setId(1);

        teacherService.updateTeacher(1, teacher);

        verify(teacherDAO).updateMember(teacher);
    }

    @Test
    void deleteTeacher() {
        teacherService.deleteTeacher(1);

        verify(teacherDAO).deleteMember(1);
    }

    @Test
    void filterTeachers() {
        List<Teacher> sampleTeachers = Arrays.asList(
                new Teacher("user1", "password1", "John Doe", "Male", "25", "CS", "Professor"),
                new Teacher("user2", "password1", "Grimes", "Female", "25", "CS", "Lecturer I"),
                new Teacher("user3", "password1", "Alice Smith", "Female", "30", "Math", "Lecturer II")
        );

        when(teacherDAO.getAllTeachers()).thenReturn(sampleTeachers);

        // Test with all filters
        List<Teacher> result = teacherService.filterTeachers("user2", "grimes", "cs");
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0).getUsername());

        // Test with empty username
        result = teacherService.filterTeachers("", "grimes", "cs");
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0).getUsername());

        // Test with empty name
        result = teacherService.filterTeachers("user2", "", "cs");
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0).getUsername());

        // Test with empty department
        result = teacherService.filterTeachers("user2", "grimes", "");
        assertEquals(1, result.size());
        assertEquals("user2", result.get(0).getUsername());

        // Test with all empty filters
        result = teacherService.filterTeachers("", "", "");
        assertEquals(3, result.size());

        // Test with no matches
        result = teacherService.filterTeachers("nonexistent", "nonexistent", "nonexistent");
        assertTrue(result.isEmpty());

        verify(teacherDAO, times(6)).getAllTeachers();
    }
}