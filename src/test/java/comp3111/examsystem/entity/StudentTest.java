package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentTest {

    @Test
    void testConstructor() {
        // Arrange
        String username = "student1";
        String password = "password1";
        String name = "John Doe";
        String gender = "Male";
        String age = "20";
        String department = "Computer Science";

        // Act
        Student student = new Student(username, password, name, gender, age, department);

        // Assert
        Assertions.assertEquals(username, student.getUsername());
        Assertions.assertEquals(password, student.getPassword());
        Assertions.assertEquals(name, student.getName());
        Assertions.assertEquals(gender, student.getGender());
        Assertions.assertEquals(age, student.getAge());
        Assertions.assertEquals(department, student.getDepartment());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Student student = new Student();

        // Act and Assert
        Assertions.assertNull(student.getUsername());
        Assertions.assertNull(student.getPassword());
        Assertions.assertNull(student.getName());
        Assertions.assertNull(student.getGender());
        Assertions.assertNull(student.getAge());
        Assertions.assertNull(student.getDepartment());
    }

    @Test
    void testSetters() {
        // Arrange
        Student student = new Student();

        // Act
        student.setUsername("student2");
        student.setPassword("password2");
        student.setName("Jane Smith");
        student.setGender("Female");
        student.setAge("22");
        student.setDepartment("Mathematics");

        // Assert
        Assertions.assertEquals("student2", student.getUsername());
        Assertions.assertEquals("password2", student.getPassword());
        Assertions.assertEquals("Jane Smith", student.getName());
        Assertions.assertEquals("Female", student.getGender());
        Assertions.assertEquals("22", student.getAge());
        Assertions.assertEquals("Mathematics", student.getDepartment());
    }
}