package comp3111.examsystem.entity;

public class Student extends Member {
    private Examination[] exams;

    public Student(String Username, String Password, String Name, String Gender, String Age, String Department) {
        super(Username, Password, Name, Gender, Age, Department);

    }
}

