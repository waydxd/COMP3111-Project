package comp3111.examsystem.entity;

public class Course {
    private String name;
    private String department;
    private String courseCode;

    // Default constructor
    public Course() {
    }

    // Constructor with parameters
    public Course(String courseCode ,String name, String department) {
        this.courseCode = courseCode;
        this.name = name;
        this.department = department;
    }

    // Getters and Setters
    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

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
                "id=" + courseCode +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}