package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.MemberDAO;
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
}