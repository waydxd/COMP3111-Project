package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void testConstructor() {
        // Arrange
        String username = "johndoe";
        String password = "password123";
        String name = "John Doe";
        String gender = "Male";
        String age = "30";
        String department = "Computer Science";

        // Act
        Member member = new Member(username, password, name, gender, age, department);

        // Assert
        Assertions.assertEquals(username, member.getUsername());
        Assertions.assertEquals(password, member.getPassword());
        Assertions.assertEquals(name, member.getName());
        Assertions.assertEquals(gender, member.getGender());
        Assertions.assertEquals(age, member.getAge());
        Assertions.assertEquals(department, member.getDepartment());
    }

    @Test
    void testDefaultConstructor() {
        // Arrange
        Member member = new Member();

        // Act and Assert
        Assertions.assertNull(member.getUsername());
        Assertions.assertNull(member.getPassword());
        Assertions.assertNull(member.getName());
        Assertions.assertNull(member.getGender());
        Assertions.assertNull(member.getAge());
        Assertions.assertNull(member.getDepartment());
    }

    @Test
    void testSetters() {
        // Arrange
        Member member = new Member();

        // Act
        member.setUsername("janedoe");
        member.setPassword("password456");
        member.setName("Jane Doe");
        member.setGender("Female");
        member.setAge("25");
        member.setDepartment("Marketing");

        // Assert
        Assertions.assertEquals("janedoe", member.getUsername());
        Assertions.assertEquals("password456", member.getPassword());
        Assertions.assertEquals("Jane Doe", member.getName());
        Assertions.assertEquals("Female", member.getGender());
        Assertions.assertEquals("25", member.getAge());
        Assertions.assertEquals("Marketing", member.getDepartment());
    }

    @Test
    void testCheckPassword() {
        // Arrange
        Member member = new Member("johndoe", "password123", "John Doe", "Male", "30", "Computer Science");

        // Act and Assert
        Assertions.assertTrue(member.Check_password("password123"));
        Assertions.assertFalse(member.Check_password("wrongpassword"));
    }
}