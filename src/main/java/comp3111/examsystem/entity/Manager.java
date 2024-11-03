package comp3111.examsystem.entity;

import java.util.ArrayList;
import java.util.List;

public class Manager extends Member {



    public static class AccountManager
    {
        private List<Member> all_accounts;//储存所有账户
        private List<Teacher> teacher_accounts;
        private List<Student> student_accounts;
        private List<Manager> manager_accounts;

        public void addAccount(Member account) {
            all_accounts.add(account);
            // 判断账户类型,并分别添加到相应的列表中
            if (account instanceof Teacher) {
                teacher_accounts.add((Teacher) account);
            } else if (account instanceof Student) {
                student_accounts.add((Student) account);
            } else if (account instanceof Manager) {
                manager_accounts.add((Manager) account);
            }
        }
        public List<Member> getAllAccounts() {
            return all_accounts;
        }

        public AccountManager()
        {
            all_accounts =new ArrayList<>();
            teacher_accounts=new ArrayList<>();
            student_accounts=new ArrayList<>();
            manager_accounts=new ArrayList<>();
        }

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

        public Teacher getTeacherbyUserName(String username) {
            for (Teacher teacher : teacher_accounts) {
                if (teacher.getUsername().equals(username)) {
                    return teacher;
                }
            }
            return null; // 如果未找到匹配的教师,返回 null
        }

        public Student getStudentbyUserName(String username) {
            for (Student student : student_accounts) {
                if (student.getUsername().equals(username)) {
                    return student;
                }
            }
            return null; // 如果未找到匹配的学生,返回 null
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
