package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Member;
import comp3111.examsystem.entity.Student;
import comp3111.examsystem.entity.Teacher;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.examsystem.jooq.generated.tables.Members.MEMBERS;
public class MemberDAO {
    private DSLContext create;

    /** Constructor
     * This constructor initializes the DSLContext for interacting with the database
     * using the SQLite dialect. It attempts to establish a connection to the database
     * and sets up the DSLContext for executing SQL queries.
     * If a SQLException occurs while attempting to establish the connection, the stack trace
     * of the exception is printed.
     */
    public MemberDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor
     * <p>
     *     This constructor initializes the DSLContext for interacting with the database
     *     using the SQLite dialect. It attempts to establish a connection to the database
     *     and sets up the DSLContext for executing SQL queries.
     *     </p>
     * @param conn connection to the database
     */
    public MemberDAO(Connection conn) {
        try {
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Adds a member to the database.
     * @param member member to be added
     */
    public void addMember(Member member) {
        create.insertInto(MEMBERS, MEMBERS.USERNAME, MEMBERS.PASSWORD, MEMBERS.AGE, MEMBERS.DEPARTMENT, MEMBERS.GENDER, MEMBERS.NAME, MEMBERS.TYPE)
                .values(member.getUsername(), member.getPassword(), member.getAge(), member.getDepartment(), member.getGender(), member.getName(), "Student")
                .execute();
    }

    /**
     * Adds a teacher to the database.
     * @param teacher teacher to be added
     */
    public void addTeacher(Teacher teacher) {
        create.insertInto(MEMBERS, MEMBERS.USERNAME, MEMBERS.PASSWORD, MEMBERS.AGE, MEMBERS.DEPARTMENT, MEMBERS.GENDER, MEMBERS.NAME, MEMBERS.TYPE, MEMBERS.POSITION)
                .values(teacher.getUsername(), teacher.getPassword(), teacher.getAge(), teacher.getDepartment(), teacher.getGender(), teacher.getName(), "Teacher", teacher.getPosition())
                .execute();
    }

    /**
     * Retrieves a member from the database using the member ID.
     * @param id the ID of the member to be retrieved
     * @return the member with the given ID
     */
    public Member getMember(int id) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id))
                .fetchOneInto(Member.class);
    }

//    public Member getMember(String username) {
//        return create.selectFrom(MEMBERS)
//                .where(MEMBERS.USERNAME.eq(username))
//                .fetchOneInto(Member.class);
//    }

    /**
     * Retrieves a teacher from the database using the teacher ID.
     * @param id the ID of the teacher to be retrieved
     * @return the teacher with the given ID
     */
    public Teacher getTeacher(int id) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id), MEMBERS.TYPE.eq("Teacher"))
                .fetchOneInto(Teacher.class);
    }

    /**
     * Retrieves a student from the database using the student ID.
     * @param id the ID of the student to be retrieved
     * @return the student with the given ID
     */
    public Student getStudent(int id) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id), MEMBERS.TYPE.eq("Student"))
                .fetchOneInto(Student.class);
    }

    /**
     * Updates a member in the database.
     * @param member member to be updated
     *               <p>
     *               This function updates a member in the database using the given Member object.
     *               If the member is a teacher, the additional data members will also be added.
     *               </p>
     */
    public void updateMember(Member member) {
        var updateQuery = create.update(MEMBERS)
                .set(MEMBERS.USERNAME, member.getUsername())
                .set(MEMBERS.PASSWORD, member.getPassword())
                .set(MEMBERS.AGE, member.getAge())
                .set(MEMBERS.DEPARTMENT, member.getDepartment())
                .set(MEMBERS.GENDER, member.getGender())
                .set(MEMBERS.NAME, member.getName());
        if (member instanceof Teacher) {
            updateQuery = updateQuery.set(MEMBERS.POSITION, ((Teacher) member).getPosition());
        }

        updateQuery.where(MEMBERS.ID.eq(member.getId()))
                .execute();
    }

    /**
     * Deletes a member from the database.
     * @param id the ID of the member to be deleted
     */
    public void deleteMember(int id) {
        create.deleteFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id))
                .execute();
    }

    /**
     * Retrieves all members from the database.
     * @return list of all members
     */
    public List<Member> getAllMembers() {
        return create.selectFrom(MEMBERS)
                .fetchInto(Member.class);
    }

    /**
     * Retrieves all teachers from the database.
     * @return list of all teachers
     */
    public List<Teacher> getAllTeachers() {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.TYPE.eq("Teacher"))
                .fetchInto(Teacher.class);
    }

    /**
     * Retrieves all students from the database.
     * @return list of all students
     */
    public List<Student> getAllStudents() {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.TYPE.eq("Student"))
                .fetchInto(Student.class);
    }

}
