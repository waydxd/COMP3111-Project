package comp3111.examsystem.entity;

public class Teacher extends Member {
    private String Position;

    /**
     * Constructor using super class constructor
     * @param Username username
     * @param Password password
     * @param Name name
     * @param Gender gender
     * @param Age age
     * @param Department department
     * @param position position
     */
    public Teacher(String Username, String Password, String Name, String Gender, String Age, String Department,String position) {
        super(Username, Password, Name, Gender, Age, Department);
        Position=position;
    }

    /**
     * @return position
     */
    public String getPosition() {
        return Position;
    }

    /**
     * @param Position position
     */
    public void setPosition(String Position) {
        this.Position = Position;
    }

    /**
     * Default constructor with no values
     */
    public Teacher(){
        super();
    }
}
