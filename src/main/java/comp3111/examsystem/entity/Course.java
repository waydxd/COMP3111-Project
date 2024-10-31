package comp3111.examsystem.entity;

public class Course extends Entity {
    private String name;
    private String department;

    // Default constructor
    public Course() {
    }

    // Constructor with parameters
    public Course(String name, String department) {
        this.name = name;
        this.department = department;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    // Optional: Override toString() for better object representation
    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}