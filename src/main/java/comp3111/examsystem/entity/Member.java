package comp3111.examsystem.entity;

public class Member extends Entity{
    public long id;
    private String Username;
    private String Password;
    private String Name;
    private String Gender;
    private String Age;
    private String Department;

    /**
     * Default constructor with no values
     */
    public Member() {
    }

    /**
     * get the username of the member
     * @return the username of the member
     */
    public String getUsername() {
        return Username;
    }

    /**
     * check if the password is correct
     * @param pwd the password to be checked
     */
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

    /**
     * Conversion constructor
     * @param Username username
     * @param Password password
     * @param Name name
     */
    public Member(String Username, String Password, String Name, String Gender, String Age, String Department) {
        this.Username = Username;
        this.Password = Password;
        this.Name = Name;
        this.Gender = Gender;
        this.Age = Age;
        this.Department = Department;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return Password;
    }

    /**
     * @return name
     */
    public String getName() {
        return Name;
    }

    /**
     * @return Gender
     */
    public String getGender() {
        return Gender;
    }

    /**
     * @return Age
     */
    public String getAge() {
        return Age;
    }

    /**
     * @return Department
     */
    public String getDepartment() {
        return Department;
    }

    /**
     * Set the username
     * @param Username username
     */
    public void setUsername(String Username) {
        this.Username = Username;
    }

    /**
     * Set the password
     * @param Password password
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * Set the name
     * @param Name name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     * Set the gender
     * @param gender gender
     */
    public void setGender(String gender){
        this.Gender = gender;
    }

    /**
     * Set the age
     * @param age age
     */
    public void setAge(String age){
        this.Age = age;
    }

    /**
     * Set the department
     * @param department department
     */
    public void setDepartment(String department){
        this.Department = department;
    }

}

