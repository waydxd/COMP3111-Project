package comp3111.examsystem.entity;
import java.util.ArrayList;
import java.util.List;
public class Manager extends Member {
    public Manager() {
        super();
    }
    public long id;
    private String Username;
    private String Password;


    /**
     * The AccountManager class is responsible for managing user accounts
     * in the system, which include teachers, students, and managers.
     * It provides methods to add accounts, check for account existence,
     * and retrieve specific account types by username.
     */
    public static class AccountManager
    {
        private List<Member> all_accounts;
        private List<Teacher> teacher_accounts;
        private List<Student> student_accounts;
        private List<Manager> manager_accounts;
        public void addAccount(Member account) {
            if(account==null)
            {
                throw new NullPointerException("Account cannot be null");
            }
            all_accounts.add(account);

            if (account instanceof Teacher) {
                teacher_accounts.add((Teacher) account);
            } else if (account instanceof Student) {
                student_accounts.add((Student) account);
            } else if (account instanceof Manager) {
                manager_accounts.add((Manager) account);
            }
        }
        /**
         * Retrieves a list of all accounts managed by this AccountManager.
         *
         * @return a List of all Member accounts
         */
        public List<Member> getAllAccounts() {
            return all_accounts;
        }


        /**
         * Constructs an AccountManager instance, initializing the lists for
         * all accounts, teachers, students, and managers.
         */
        public AccountManager()
        {
            all_accounts =new ArrayList<>();
            teacher_accounts=new ArrayList<>();
            student_accounts=new ArrayList<>();
            manager_accounts=new ArrayList<>();
        }
        /**
         * Checks if an account with the specified username exists in the system.
         *
         * @param user the username to check for
         * @return true if the account exists, false otherwise
         */
        public boolean account_exist(String user)
        {
            for(Member member: all_accounts)
            {
                if(member.getUsername().equals(user))
                {
                    return true;
                }
            }
            return false;
        }
        /**
         * Retrieves a Teacher account by username if it exists.
         *
         * @param username the username of the Teacher to retrieve
         * @return the Teacher account if found, null otherwise
         */
        public Teacher getTeacherbyUserName(String username) {
            for (Teacher teacher : teacher_accounts) {
                if (teacher.getUsername().equals(username)) {
                    return teacher;
                }
            }
            return null;
        }
        /**
         * Retrieves a Student account by username if it exists.
         *
         * @param username the username of the Student to retrieve
         * @return the Student account if found, null otherwise
         */
        public Student getStudentbyUserName(String username) {
            for (Student student : student_accounts) {
                if (student.getUsername().equals(username)) {
                    return student;
                }
            }
            return null;
        }
        /**
         * Retrieves a Manager account by username if it exists.
         *
         * @param username the username of the Manager to retrieve
         * @return the Manager account if found, null otherwise
         */
        public Manager getManagerbyUserName(String username) {
            for (Manager manager : manager_accounts) {
                if (manager.getUsername().equals(username)) {
                    return manager;
                }
            }
            return null; // If no matching manager is found, return null
        }
    }
    private static final AccountManager accountManager=new AccountManager();
    public  static AccountManager getAccountManager() {
        return accountManager;
    }
    public Manager(String Username, String Password, String Name, String Gender, String Age, String Department) {
        super(Username, Password, Name, Gender, Age, Department);
    }
}

