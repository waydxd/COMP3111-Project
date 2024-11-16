package comp3111.examsystem.entity;

public class Member extends Entity{
    public long id;
    private String Username;
    private String Password;
    private String Name;
    private String Gender;
    private String Age;
    private String Department;

    public Member() {

    }

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

    public String getPassword() {
        return Password;
    }

    public String getName() {
        return Name;
    }

    public String getGender() {
        return Gender;
    }

    public String getAge() {
        return Age;
    }

    public String getDepartment() {
        return Department;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setGender(String gender){
        this.Gender = gender;
    }

    public void setAge(String age){
        this.Age = age;
    }

    public void setDepartment(String department){
        this.Department = department;
    }

}

