package comp3111.examsystem.dao.internal;

import comp3111.examsystem.entity.Member;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemberDAOTest {

    private MemberDAO memberDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        memberDAO = new MemberDAO(connection);
        initializeDatabase();
    }

    private void initializeDatabase() throws SQLException {
        String createMembersTableSQL = "CREATE TABLE IF NOT EXISTS members (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "name TEXT NOT NULL, " +
                "gender TEXT NOT NULL, " +
                "age TEXT NOT NULL, " +
                "department TEXT NOT NULL, " +
                "position TEXT, " +
                "type TEXT NOT NULL CHECK (type IN ('Teacher', 'Student')))";
        connection.createStatement().execute(createMembersTableSQL);
    }

    @Test
    void addMember() {
        Member member = new Member("user1", "pass1", "John Doe", "Male", "20", "CS");
        memberDAO.addMember(member);

        List<Member> members = memberDAO.getAllMembers();
        assertTrue(members.stream().anyMatch(m -> m.getUsername().equals("user1") && m.getPassword().equals("pass1")));
    }

    @Test
    void addTeacher() {
        Teacher teacher = new Teacher("teacher1", "pass1", "Jane Doe", "Female", "30", "CS", "Professor");
        memberDAO.addTeacher(teacher);

        List<Teacher> teachers = memberDAO.getAllTeachers();
        assertTrue(teachers.stream().anyMatch(t -> t.getUsername().equals("teacher1") && t.getPassword().equals("pass1")));
    }

    @Test
    void getMember() {
        Member member = new Member("user1", "pass1", "John Doe", "Male", "20", "CS");
        memberDAO.addMember(member);

        int memberId = memberDAO.getAllMembers().getFirst().getId();
        Member retrievedMember = memberDAO.getMember(memberId);

        assertNotNull(retrievedMember);
        assertEquals("user1", retrievedMember.getUsername());
        assertEquals("pass1", retrievedMember.getPassword());
    }

    @Test
    void getTeacher() {
        Teacher teacher = new Teacher("teacher1", "pass1", "Jane Doe", "Female", "30", "CS", "Professor");
        memberDAO.addTeacher(teacher);

        int teacherId = memberDAO.getAllTeachers().getFirst().getId();
        Teacher retrievedTeacher = memberDAO.getTeacher(teacherId);

        assertNotNull(retrievedTeacher);
        assertEquals("teacher1", retrievedTeacher.getUsername());
        assertEquals("pass1", retrievedTeacher.getPassword());
    }

    @Test
    void getStudent() {
        Member student = new Member("student1", "pass1", "John Doe", "Male", "20", "CS");
        memberDAO.addMember(student);

        int studentId = memberDAO.getAllStudents().getFirst().getId();
        Student retrievedStudent = memberDAO.getStudent(studentId);

        assertNotNull(retrievedStudent);
        assertEquals("student1", retrievedStudent.getUsername());
        assertEquals("pass1", retrievedStudent.getPassword());
    }

    @Test
    void updateMember() {
        Member member = new Member("user1", "pass1", "John Doe", "Male", "20", "CS");
        memberDAO.addMember(member);

        int memberId = memberDAO.getAllMembers().getFirst().getId();
        member.setId(memberId);
        member.setPassword("newpass");
        memberDAO.updateMember(member);

        Member updatedMember = memberDAO.getMember(memberId);
        assertEquals("newpass", updatedMember.getPassword());
    }

    @Test
    void deleteMember() {
        Member member = new Member("user1", "pass1", "John Doe", "Male", "20", "CS");
        memberDAO.addMember(member);

        int memberId = memberDAO.getAllMembers().getFirst().getId();
        memberDAO.deleteMember(memberId);

        Member deletedMember = memberDAO.getMember(memberId);
        assertNull(deletedMember);
    }

    @Test
    void getAllMembers() {
        Member member1 = new Member("user1", "pass1", "John Doe", "Male", "20", "CS");
        Member member2 = new Member("user2", "pass2", "Jane Doe", "Female", "22", "CS");

        memberDAO.addMember(member1);
        memberDAO.addMember(member2);

        List<Member> members = memberDAO.getAllMembers();
        assertEquals(2, members.size());
    }

    @Test
    void getAllTeachers() {
        Teacher teacher1 = new Teacher("teacher1", "pass1", "Jane Doe", "Female", "30", "CS", "Professor");
        Teacher teacher2 = new Teacher("teacher2", "pass2", "John Smith", "Male", "40", "CS", "Associate Professor");

        memberDAO.addTeacher(teacher1);
        memberDAO.addTeacher(teacher2);

        List<Teacher> teachers = memberDAO.getAllTeachers();
        assertEquals(2, teachers.size());
    }

    @Test
    void getAllStudents() {
        Member student1 = new Member("student1", "pass1", "John Doe", "Male", "20", "CS");
        Member student2 = new Member("student2", "pass2", "Jane Doe", "Female", "22", "CS");

        memberDAO.addMember(student1);
        memberDAO.addMember(student2);

        List<Student> students = memberDAO.getAllStudents();
        assertEquals(2, students.size());
    }
}