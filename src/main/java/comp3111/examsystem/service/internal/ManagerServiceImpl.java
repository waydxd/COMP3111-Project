package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.ManagerDAO;
import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.service.ManagerService;

import java.util.List;

public class ManagerServiceImpl implements ManagerService {
    private final ManagerDAO managerDAO = new ManagerDAO();
    private final MemberDAO memberDAO = new MemberDAO();

    @Override
    public void addManager(Manager manager) {
        managerDAO.addManager(manager);
    }

    @Override
    public Manager getManager(int id) {
        return managerDAO.getManager(id);
    }

    @Override
    public List<Manager> getAllManagers() {
        return managerDAO.getAllManagers();
    }

    /**
     * @return
     */
    @Override
    public List<Member> getAllAccounts() {
        return memberDAO.getAllMembers();
    }

    @Override
    public void updateManager(Manager manager) {
        managerDAO.updateManager(manager);
    }

    @Override
    public void deleteManager(int id) {
        managerDAO.deleteManager(id);
    }

    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean login(String username, String password) {
        Manager manager = managerDAO.getManager(username);
        return manager != null && manager.getPassword().equals(password);
    }
}
