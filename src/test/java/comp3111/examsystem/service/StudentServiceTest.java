package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.internal.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private MemberDAO studentDAO;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        studentService = new StudentServiceImpl(studentDAO);
    }

    @Test
    void addStudent() {
        Student student = new Student("user1", "password1", "John Doe", "Male", "25", "CS");

        studentService.addStudent(student);

        verify(studentDAO).addMember(student);
    }

    @Test
    void getStudent() {
        Student mockStudent = new Student("user1", "password1", "John Doe", "Male", "25", "CS");
        when(studentDAO.getStudent(1)).thenReturn(mockStudent);

        Student result = studentService.getStudent(1);

        verify(studentDAO).getStudent(1);
        assertNotNull(result);
        assertEquals(mockStudent.getUsername(), result.getUsername());
        assertEquals(mockStudent.getName(), result.getName());
    }

    @Test
    void getAllStudents() {
        List<Student> mockStudents = Arrays.asList(new Student(), new Student());
        when(studentDAO.getAllStudents()).thenReturn(mockStudents);

        List<Student> result = studentService.getAllStudents();

        verify(studentDAO).getAllStudents();
        assertEquals(mockStudents, result);
    }

    @Test
    void updateStudent() {
        Student student = new Student("user1", "password1", "John Doe", "Male", "25", "CS");
        student.setId(1);

        studentService.updateStudent(1, student);

        verify(studentDAO).updateMember(student);
    }

    @Test
    void deleteStudent() {
        studentService.deleteStudent(1);

        verify(studentDAO).deleteMember(1);
    }

    @Test
    void login() {
        // Mock data
        Member mockMember = new Member("user1", "password1", "John Doe", "Male", "25", "CS");
        List<Member> mockMembers = Arrays.asList(mockMember);
        when(studentDAO.getAllMembers()).thenReturn(mockMembers);

        // Test for correct credentials
        boolean result = studentService.login("user1", "password1");
        verify(studentDAO).getAllMembers();
        assertTrue(result);

        // Test for incorrect credentials
        boolean falseResult = studentService.login("user1", "wrongpassword");
        verify(studentDAO, times(2)).getAllMembers();
        assertFalse(falseResult);

        // Test for non-existing user
        boolean nonExistingUserResult = studentService.login("nonuser", "password1");
        verify(studentDAO, times(3)).getAllMembers();
        assertFalse(nonExistingUserResult);
    }
}