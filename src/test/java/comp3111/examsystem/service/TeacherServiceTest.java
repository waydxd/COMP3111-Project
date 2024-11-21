package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
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

        // Test with null teacher
        assertThrows(NullPointerException.class, () -> teacherService.addTeacher(null));
    }

    @Test
    void getAllTeachers() {
        List<Teacher> mockTeachers = Arrays.asList(new Teacher(), new Teacher());
        when(teacherDAO.getAllTeachers()).thenReturn(mockTeachers);

        List<Teacher> result = teacherService.getAllTeachers();

        verify(teacherDAO).getAllTeachers();
        assertEquals(mockTeachers, result);

        // Test with no teachers
        when(teacherDAO.getAllTeachers()).thenReturn(Arrays.asList());
        result = teacherService.getAllTeachers();
        assertTrue(result.isEmpty());
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

        // Test with non-empty username
        result = teacherService.filterTeachers("user1", "john", "cs");
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUsername());

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

        // Test with null username
        assertThrows(NullPointerException.class, () -> teacherService.filterTeachers(null, "grimes", "cs"));

        // Test with null name
        assertThrows(NullPointerException.class, () -> teacherService.filterTeachers("user2", null, "cs"));

        // Test with null department
        assertThrows(NullPointerException.class, () -> teacherService.filterTeachers("user2", "grimes", null));

        verify(teacherDAO, times(7)).getAllTeachers();
    }
//
//    @Test
//    void getTeacher() {
//        Teacher mockTeacher = new Teacher("user1", "password1", "John Doe", "Male", "35", "Math", "Professor");
//        when(teacherDAO.getTeacher(1)).thenReturn(mockTeacher);
//
//        // Test with valid ID
//        Teacher result = teacherService.getTeacher(1);
//        verify(teacherDAO).getTeacher(1);
//        assertNotNull(result);
//        assertEquals(mockTeacher.getUsername(), result.getUsername());
//        assertEquals(mockTeacher.getName(), result.getName());
//
//        // Test with non-existent teacher
//        when(teacherDAO.getTeacher(2)).thenReturn(null);
//        assertNull(teacherService.getTeacher(2));
//
//        // Test with invalid ID
//        assertThrows(IllegalArgumentException.class, () -> teacherService.getTeacher(-1));
//
//        // Test with zero ID
//        assertThrows(IllegalArgumentException.class, () -> teacherService.getTeacher(0));
//    }

//    @Test
//    void updateTeacher() {
//        Teacher teacher = new Teacher("user1", "password1", "John Doe", "Male", "35", "Math", "Professor");
//        teacher.setId(1);
//
//        // Test with valid ID and teacher
//        teacherService.updateTeacher(1, teacher);
//        verify(teacherDAO).updateMember(teacher);
//
//        // Test with null teacher
//        assertThrows(NullPointerException.class, () -> teacherService.updateTeacher(1, null));
//
//        // Test with invalid ID
//        assertThrows(IllegalArgumentException.class, () -> teacherService.updateTeacher(-1, teacher));
//
//        // Test with zero ID
//        assertThrows(IllegalArgumentException.class, () -> teacherService.updateTeacher(0, teacher));
//    }

//    @Test
//    void deleteTeacher() {
//        // Test with valid ID
//        teacherService.deleteTeacher(1);
//        verify(teacherDAO).deleteMember(1);
//
//        // Test with non-existent teacher
//        doThrow(new IllegalArgumentException("Teacher not found")).when(teacherDAO).deleteMember(2);
//        assertThrows(IllegalArgumentException.class, () -> teacherService.deleteTeacher(2));
//
//        // Test with invalid ID
//        assertThrows(IllegalArgumentException.class, () -> teacherService.deleteTeacher(-1));
//
//        // Test with zero ID
//        assertThrows(IllegalArgumentException.class, () -> teacherService.deleteTeacher(0));
//    }

    @Test
    public void testGetTeacher_ValidId_ReturnsTeacher() {
        // Arrange
        int id = 1;
        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setId(id);
        when(teacherDAO.getTeacher(id)).thenReturn(expectedTeacher);

        // Act
        Teacher result = teacherService.getTeacher(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(teacherDAO).getTeacher(id);
    }

    @Test
    public void testGetTeacher_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> teacherService.getTeacher(0));
    }

    @Test
    public void testGetTeacher_NonexistentId_ThrowsException() {
        when(teacherDAO.getTeacher(anyInt())).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> teacherService.getTeacher(1));
    }

    // Tests for updateTeacher
    @Test
    public void testUpdateTeacher_ValidTeacher_UpdatesSuccessfully() {
        // Arrange
        int id = 1;
        Teacher teacher = new Teacher();
        when(teacherDAO.getTeacher(id)).thenReturn(teacher);

        // Act
        teacherService.updateTeacher(id, teacher);

        // Assert
        verify(teacherDAO).updateMember(teacher);
        assertEquals(id, teacher.getId());
    }

    @Test
    public void testUpdateTeacher_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> teacherService.updateTeacher(0, new Teacher()));
    }

    @Test
    public void testUpdateTeacher_NullTeacher_ThrowsException() {
        assertThrows(NullPointerException.class, () -> teacherService.updateTeacher(1, null));
    }

    @Test
    public void testUpdateTeacher_NonexistentTeacher_ThrowsException() {
        when(teacherDAO.getTeacher(anyInt())).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> teacherService.updateTeacher(1, new Teacher()));
    }

    // Tests for deleteTeacher
    @Test
    public void testDeleteTeacher_ValidId_DeletesSuccessfully() {
        // Arrange
        int id = 1;
        when(teacherDAO.getTeacher(id)).thenReturn(new Teacher());

        // Act
        teacherService.deleteTeacher(id);

        // Assert
        verify(teacherDAO).deleteMember(id);
    }

    @Test
    public void testDeleteTeacher_InvalidId_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> teacherService.deleteTeacher(0));
    }

    @Test
    public void testDeleteTeacher_NonexistentTeacher_ThrowsException() {
        when(teacherDAO.getTeacher(anyInt())).thenReturn(null);
        assertThrows(IllegalStateException.class, () -> teacherService.deleteTeacher(1));
    }
    @Test
    void testAccountExist() {
        List<Teacher> allTeachers = new ArrayList<>();
        allTeachers.add(new Teacher("existingUsername", "testPassword", "Existing Teacher", "Male", "30", "Professor", "Computer Science"));
        allTeachers.add(new Teacher("testUsername", "testPassword", "Test Teacher", "Female", "35", "Associate Professor", "Mathematics"));

        when(teacherService.getAllTeachers()).thenReturn(allTeachers);

        boolean accountExists = teacherService.account_exist("existingUsername");
        assertTrue(accountExists);

        accountExists = teacherService.account_exist("nonExistingUsername");
        assertFalse(accountExists);
    }

    @Test
    void testGetTeacherByUserName() {
        List<Teacher> allTeachers = new ArrayList<>();
        Teacher existingTeacher = new Teacher("existingUsername", "testPassword", "Existing Teacher", "Male", "30", "Professor", "Computer Science");
        existingTeacher.setId(1);
        allTeachers.add(existingTeacher);
        allTeachers.add(new Teacher("testUsername", "testPassword", "Test Teacher", "Female", "35", "Associate Professor", "Mathematics"));

        when(teacherService.getAllTeachers()).thenReturn(allTeachers);

        Teacher foundTeacher = teacherService.getTeacherbyUserName("existingUsername");
        assertNotNull(foundTeacher);
        assertEquals("existingUsername", foundTeacher.getUsername());

        foundTeacher = teacherService.getTeacherbyUserName("nonExistingUsername");
        assertNull(foundTeacher);
    }
}