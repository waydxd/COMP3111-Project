package comp3111.examsystem.service.internal;

import comp3111.examsystem.dao.internal.MemberDAO;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final MemberDAO studentDAO;

    public StudentServiceImpl() {
        this.studentDAO = new MemberDAO();
    }

    public StudentServiceImpl(MemberDAO studentDAO) {
        this.studentDAO = studentDAO;
    }
    /**
     * @param student
     */
    @Override
    public void addStudent(Student student) {
        studentDAO.addMember(student);
        // exam is not implemented
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Student getStudent(int id) {
        return  studentDAO.getStudent(id);
    }

    /**
     * @return
     */
    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    /**
     * @param student
     */
    @Override
    public void updateStudent(int id, Student student) {
        student.setId(id);
        studentDAO.updateMember(student);
        // Exam is not implemented
    }

    /**
     * @param id
     */
    @Override
    public void deleteStudent(int id) {
        studentDAO.deleteMember(id);
    }

    /**
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean login(String username, String password) {
        // Not implemented
        return false;
    }

}
