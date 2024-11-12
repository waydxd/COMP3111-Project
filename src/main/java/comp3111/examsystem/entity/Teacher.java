package comp3111.examsystem.entity;

public class Teacher extends Member {
    private String Position;

    public Teacher(String Username, String Password, String Name, String Gender, String Age, String Department,String position) {
        super(Username, Password, Name, Gender, Age, Department);
        Position=position;
    }

    public String getPosition() {
        return Position;
    }

    public void setPosition(String Position) {
        this.Position = Position;
    }
}
