package comp3111.examsystem.entity;

public class Student extends Member {
    private Examination[] exams;

    /**
     * Constructor using super class constructor
     * @param Username username
     * @param Password password
     * @param Name name
     * @param Gender gender
     * @param Age age
     * @param Department department
     */
    public Student(String Username, String Password, String Name, String Gender, String Age, String Department) {
        super(Username, Password, Name, Gender, Age, Department);

    }

    /**
     * Default constructor with no values
     */
    public Student(){
        super();
    }
}

