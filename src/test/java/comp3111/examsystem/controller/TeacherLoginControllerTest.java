package comp3111.examsystem.controller;

import comp3111.examsystem.entity.Teacher;
import comp3111.examsystem.service.TeacherService;
import comp3111.examsystem.service.internal.TeacherServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

class TeacherLoginControllerTest {

    private TeacherLoginController controller;
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        teacherService = Mockito.mock(TeacherServiceImpl.class);
        controller = new TeacherLoginController();
    }

    @Test
    void testAccountExist() {
        // Arrange
        Teacher teacher1 = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
        Teacher teacher2 = new Teacher("teacher2", "password2", "John Smith", "Male", "40", "EE", "Professor");
        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);
        Mockito.when(teacherService.getAllTeachers()).thenReturn(teachers);

        // Act and Assert
        controller.setTeacherList(teacherService.getAllTeachers());
        Assertions.assertTrue(controller.account_exist("teacher1"));
        Assertions.assertTrue(controller.account_exist("teacher2"));
        Assertions.assertFalse(controller.account_exist("non_existing_user"));
    }

    @Test
    void testGetTeacherByUsername() {
        // Arrange
        Teacher teacher = new Teacher("teacher1", "password1", "Jane Doe", "Female", "35", "CS", "Associate Professor");
        Mockito.when(teacherService.getAllTeachers()).thenReturn(Arrays.asList(teacher));

        controller.setTeacherList(teacherService.getAllTeachers());
        // Act
        Teacher retrievedTeacher = controller.getTeacherbyUserName("teacher1");

        // Assert
        Assertions.assertNotNull(retrievedTeacher);
        Assertions.assertEquals("teacher1", retrievedTeacher.getUsername());
        Assertions.assertEquals("Jane Doe", retrievedTeacher.getName());
    }}
