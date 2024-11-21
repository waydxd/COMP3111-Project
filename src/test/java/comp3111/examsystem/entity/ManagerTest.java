package comp3111.examsystem.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ManagerTest {

    private Manager.AccountManager accountManager;

    @BeforeEach
    void setUp() {
        accountManager = Manager.getAccountManager();
    }

    @Test
    void testAddAccount() {
        // Arrange
        Manager manager = new Manager("manager1", "password1", "John Doe", "Male", "30", "IT");
        Teacher teacher = new Teacher("teacher1", "password1", "Jane Smith", "Female", "35", "CS", "Associate Professor");
        Student student = new Student("student1", "password1", "Bob Johnson", "Male", "20", "EE");

        // Act
        accountManager.addAccount(manager);
        accountManager.addAccount(teacher);
        accountManager.addAccount(student);

        // Assert
        List<Member> allAccounts = accountManager.getAllAccounts();
        Assertions.assertEquals(3, allAccounts.size());

        Assertions.assertTrue(accountManager.account_exist("manager1"));
        Assertions.assertTrue(accountManager.account_exist("teacher1"));
        Assertions.assertTrue(accountManager.account_exist("student1"));

        Assertions.assertNotNull(accountManager.getManagerbyUserName("manager1"));
        Assertions.assertNotNull(accountManager.getTeacherbyUserName("teacher1"));
        Assertions.assertNotNull(accountManager.getStudentbyUserName("student1"));

        Assertions.assertNull(accountManager.getManagerbyUserName("manager2"));
        Assertions.assertNull(accountManager.getTeacherbyUserName("teacher2"));
        Assertions.assertNull(accountManager.getStudentbyUserName("student2"));
    }

    @Test
    void testGetAccountByUsername() {
        // Arrange
        Manager manager = new Manager("manager1", "password1", "John Doe", "Male", "30", "IT");
        accountManager.addAccount(manager);

        // Act
        Manager retrievedManager = accountManager.getManagerbyUserName("manager1");

        // Assert
        Assertions.assertNotNull(retrievedManager);
        Assertions.assertEquals("manager1", retrievedManager.getUsername());
        Assertions.assertEquals("John Doe", retrievedManager.getName());
    }

    @Test
    void testAccountExist() {
        // Arrange
        Manager manager = new Manager("manager1", "password1", "John Doe", "Male", "30", "IT");
        accountManager.addAccount(manager);

        // Act and Assert
        Assertions.assertTrue(accountManager.account_exist("manager1"));
        Assertions.assertFalse(accountManager.account_exist("non_existing_user"));
    }
}