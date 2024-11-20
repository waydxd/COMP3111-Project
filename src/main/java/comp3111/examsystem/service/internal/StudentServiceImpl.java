package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentService;

import java.util.List;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private final MemberDAO studentDAO;

    /**
     * default Constructor
     */
    public StudentServiceImpl() {
        this.studentDAO = new MemberDAO();
    }

    /**
     * Conversion Constructor
     * @param studentDAO self-defined MemberDAO object for testing
     */
    public StudentServiceImpl(MemberDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
    /**
     * @param student student to be added
     */
    @Override
    public void addStudent(Student student) {
        studentDAO.addMember(student);
        // exam is not implemented
    }

    /**
     * @param id id of student
     * @return student with the given id
     */
    @Override
    public Student getStudent(int id) {
        return  studentDAO.getStudent(id);
    }

    /**
     * @return List of all students
     */
    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    /**
     * @param student student with new content to be updated
     * @param id id of the student to be updated
     */
    @Override
    public void updateStudent(int id, Student student) {
        student.setId(id);
        studentDAO.updateMember(student);
        // Exam is not implemented
    }

    /**
     * @param id id of student to be deleted
     */
    @Override
    public void deleteStudent(int id) {
        studentDAO.deleteMember(id);
    }

    /**
     * @param username username of student from the filter field
     * @param name name of student from the filter field
     * @param department department of student from the filter field
     * @return List of students that meet the filter criteria
     * <p>
     *     This function filters the students based on the given filter criteria.
     *     If the filter criteria is null, it will not be used to filter the students.
     *     </p>
     */
    @Override
    public List<Student> filterStudents(String username, String name, String department) {
        return studentDAO.getAllStudents().stream()
                .filter(student -> username.isEmpty() || student.getUsername().toLowerCase().contains(username))
                .filter(student -> name.isEmpty() || student.getName().toLowerCase().contains(name))
                .filter(student -> department.isEmpty() || student.getDepartment().toLowerCase().contains(department))
                .toList();
    }

    /**
     * @param username username
     * @param password password
     * @return true if login is successful, false otherwise
     */
    @Override
    public boolean login(String username, String password) {
        for (Member member : studentDAO.getAllMembers()) {
            if (member.getUsername().equals(username) && member.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

}
