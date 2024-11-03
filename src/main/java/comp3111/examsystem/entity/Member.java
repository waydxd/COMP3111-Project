package comp3111.examsystem.entity;

public class Member extends Entity{
    public long id;
    private String Username;
    private String Password;
    private String Name;
    private String Gender;
    private String Age;
    private String Department;

    public String getUsername() {
        return Username;
    }

    public boolean Check_password(String pwd)
    {
        if(Password.equals(pwd))
        {return true;}
        else
        {
            Manager.getAccountManager().account_exist("ss");
            return false;
        }
    }

    public Member(String Username, String Password, String Name, String Gender, String Age, String Department) {
        this.Username = Username;
        this.Password = Password;
        this.Name = Name;
        this.Gender = Gender;
        this.Age = Age;
        this.Department = Department;
    }
}

