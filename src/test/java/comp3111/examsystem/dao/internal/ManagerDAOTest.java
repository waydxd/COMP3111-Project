package comp3111.examsystem.dao.internal;

import comp3111.examsystem.entity.Manager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerDAOTest {

    private ManagerDAO managerDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        managerDAO = new ManagerDAO(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        String createManagersTableSQL = "CREATE TABLE IF NOT EXISTS managers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL)";
        connection.createStatement().execute(createManagersTableSQL);
    }

    @Test
    void addManager() {
        Manager manager = new Manager();
        manager.setUsername("admin");
        manager.setPassword("password");

        managerDAO.addManager(manager);

        List<Manager> managers = managerDAO.getAllManagers();
        assertTrue(managers.stream().anyMatch(m -> m.getUsername().equals("admin") && m.getPassword().equals("password")));
    }

    @Test
    void getManagerById() {
        Manager manager = new Manager();
        manager.setUsername("admin");
        manager.setPassword("password");

        managerDAO.addManager(manager);

        int managerId = managerDAO.getAllManagers().getFirst().getId();
        Manager retrievedManager = managerDAO.getManager(managerId);

        assertNotNull(retrievedManager);
        assertEquals("admin", retrievedManager.getUsername());
        assertEquals("password", retrievedManager.getPassword());
    }

    @Test
    void getManagerByUsername() {
        Manager manager = new Manager();
        manager.setUsername("admin");
        manager.setPassword("password");

        managerDAO.addManager(manager);

        Manager retrievedManager = managerDAO.getManager("admin");

        assertNotNull(retrievedManager);
        assertEquals("admin", retrievedManager.getUsername());
        assertEquals("password", retrievedManager.getPassword());
    }

    @Test
    void updateManager() {
        Manager manager = new Manager();
        manager.setUsername("admin");
        manager.setPassword("password");

        managerDAO.addManager(manager);

        int managerId = managerDAO.getAllManagers().getFirst().getId();
        manager.setId(managerId);
        manager.setPassword("newpassword");
        managerDAO.updateManager(manager);

        Manager updatedManager = managerDAO.getManager(managerId);
        assertEquals("newpassword", updatedManager.getPassword());
    }

    @Test
    void deleteManager() {
        Manager manager = new Manager();
        manager.setUsername("admin");
        manager.setPassword("password");

        managerDAO.addManager(manager);

        int managerId = managerDAO.getAllManagers().getFirst().getId();
        managerDAO.deleteManager(managerId);

        Manager deletedManager = managerDAO.getManager(managerId);
        assertNull(deletedManager);
    }

    @Test
    void getAllManagers() {
        Manager manager1 = new Manager();
        manager1.setUsername("admin1");
        manager1.setPassword("password1");

        Manager manager2 = new Manager();
        manager2.setUsername("admin2");
        manager2.setPassword("password2");

        managerDAO.addManager(manager1);
        managerDAO.addManager(manager2);

        List<Manager> managers = managerDAO.getAllManagers();
        assertEquals(2, managers.size());
    }
}