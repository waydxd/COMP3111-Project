package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.ManagerDAO;
import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.service.internal.ManagerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerServiceTest {

    @Mock
    private ManagerDAO managerDAO;

    @Mock
    private MemberDAO memberDAO;

    @InjectMocks
    private ManagerServiceImpl managerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        managerService = new ManagerServiceImpl(managerDAO, memberDAO);
    }

    @Test
    void addManager() {
        Manager manager = new Manager();
        manager.setUsername("admin");
        manager.setPassword("password");

        managerService.addManager(manager);

        verify(managerDAO).addManager(manager);
    }

    @Test
    void getManager() {
        Manager mockManager = new Manager();
        mockManager.setId(1);
        mockManager.setUsername("admin");
        when(managerDAO.getManager(1)).thenReturn(mockManager);

        Manager result = managerService.getManager(1);

        verify(managerDAO).getManager(1);
        assertNotNull(result);
        assertEquals(mockManager.getId(), result.getId());
        assertEquals(mockManager.getUsername(), result.getUsername());
    }

    @Test
    void getAllManagers() {
        List<Manager> mockManagers = Arrays.asList(new Manager(), new Manager());
        when(managerDAO.getAllManagers()).thenReturn(mockManagers);

        List<Manager> result = managerService.getAllManagers();

        verify(managerDAO).getAllManagers();
        assertEquals(mockManagers, result);
    }

    @Test
    void getAllAccounts() {
        List<Member> mockMembers = Arrays.asList(new Member(), new Member());
        when(memberDAO.getAllMembers()).thenReturn(mockMembers);

        List<Member> result = managerService.getAllAccounts();

        verify(memberDAO).getAllMembers();
        assertEquals(mockMembers, result);
    }

    @Test
    void updateManager() {
        Manager manager = new Manager();
        manager.setId(1);
        manager.setUsername("admin");
        manager.setPassword("newpassword");

        managerService.updateManager(manager);

        verify(managerDAO).updateManager(manager);
    }

    @Test
    void deleteManager() {
        managerService.deleteManager(1);

        verify(managerDAO).deleteManager(1);
    }

    @Test
    void login() {
        Manager mockManager = new Manager();
        mockManager.setUsername("admin");
        mockManager.setPassword("password");
        when(managerDAO.getManager("admin")).thenReturn(mockManager);

        boolean result = managerService.login("admin", "password");

        verify(managerDAO).getManager("admin");
        assertTrue(result);
    }
}