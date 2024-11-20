package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TeacherTest {

    @Test
    void testConstructor() {
        // Arrange
        String username = "teacher1";
        String password = "password1";
        String name = "John Doe";
        String gender = "Male";
        String age = "35";
        String department = "Computer Science";
        String position = "Associate Professor";

        // Act
        Teacher teacher = new Teacher(username, password, name, gender, age, department, position);

        // Assert
        Assertions.assertEquals(username, teacher.getUsername());
        Assertions.assertEquals(password, teacher.getPassword());
        Assertions.assertEquals(name, teacher.getName());
        Assertions.assertEquals(gender, teacher.getGender());
        Assertions.assertEquals(age, teacher.getAge());
        Assertions.assertEquals(department, teacher.getDepartment());
        Assertions.assertEquals(position, teacher.getPosition());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Teacher teacher = new Teacher();

        // Act and Assert
        Assertions.assertNull(teacher.getUsername());
        Assertions.assertNull(teacher.getPassword());
        Assertions.assertNull(teacher.getName());
        Assertions.assertNull(teacher.getGender());
        Assertions.assertNull(teacher.getAge());
        Assertions.assertNull(teacher.getDepartment());
        Assertions.assertNull(teacher.getPosition());
    }

    @Test
    void testSetters() {
        // Arrange
        Teacher teacher = new Teacher();

        // Act
        teacher.setUsername("teacher2");
        teacher.setPassword("password2");
        teacher.setName("Jane Smith");
        teacher.setGender("Female");
        teacher.setAge("40");
        teacher.setDepartment("Mathematics");
        teacher.setPosition("Professor");

        // Assert
        Assertions.assertEquals("teacher2", teacher.getUsername());
        Assertions.assertEquals("password2", teacher.getPassword());
        Assertions.assertEquals("Jane Smith", teacher.getName());
        Assertions.assertEquals("Female", teacher.getGender());
        Assertions.assertEquals("40", teacher.getAge());
        Assertions.assertEquals("Mathematics", teacher.getDepartment());
        Assertions.assertEquals("Professor", teacher.getPosition());
    }
}