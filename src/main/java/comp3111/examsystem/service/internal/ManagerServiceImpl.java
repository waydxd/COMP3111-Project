package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.ManagerDAO;
import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.service.ManagerService;

import java.util.List;

public class ManagerServiceImpl implements ManagerService {
    private final ManagerDAO managerDAO ;
    private final MemberDAO memberDAO;

    /**
     * Constructor
     */
    public ManagerServiceImpl() {
        this.managerDAO = new ManagerDAO();
        this.memberDAO = new MemberDAO();
    }

    /**
     * Constructor
     * @param managerDAO self-defined ManagerDAO object for testing
     * @param memberDAO  self-defined MemberDAO object for testing
     */
    public ManagerServiceImpl(ManagerDAO managerDAO, MemberDAO memberDAO) {
        this.managerDAO = managerDAO;
        this.memberDAO = memberDAO;
    }

    /**
     * @param manager manager to be added
     */
    @Override
    public void addManager(Manager manager) {
        managerDAO.addManager(manager);
    }

    /**
     * @param id id of manager
     * @return Manager with the given id
     */
    @Override
    public Manager getManager(int id) {
        return managerDAO.getManager(id);
    }

    /**
     * @return all the managers
     */
    @Override
    public List<Manager> getAllManagers() {
        return managerDAO.getAllManagers();
    }

    /**
     * @return all the accounts (members) excluding managers
     */
    @Override
    public List<Member> getAllAccounts() {
        return memberDAO.getAllMembers();
    }

    /**
     * @param manager manager to be updated
     */
    @Override
    public void updateManager(Manager manager) {
        managerDAO.updateManager(manager);
    }

    /**
     * @param id id of manager to be deleted
     */
    @Override
    public void deleteManager(int id) {
        managerDAO.deleteManager(id);
    }

    /**
     * @param username username
     * @param password password
     * @return true if the login is successful, false otherwise
     */
    @Override
    public boolean login(String username, String password) {
        Manager manager = managerDAO.getManager(username);
        return manager != null && manager.getPassword().equals(password);
    }
}
