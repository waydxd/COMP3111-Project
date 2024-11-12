package comp3111.examsystem.service;

import comp3111.examsystem.entity.Manager;
import comp3111.examsystem.entity.Member;

import java.util.List;

public interface ManagerService {
    void addManager(Manager manager);
    Manager getManager(int id);
    List<Manager> getAllManagers();
    List<Member> getAllAccounts();
    void updateManager(Manager manager);
    void deleteManager(int id);
}