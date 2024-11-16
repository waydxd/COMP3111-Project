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

    public MemberDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addMember(Member member) {
        create.insertInto(MEMBERS, MEMBERS.USERNAME, MEMBERS.PASSWORD, MEMBERS.AGE, MEMBERS.DEPARTMENT, MEMBERS.GENDER, MEMBERS.NAME, MEMBERS.TYPE)
                .values(member.getUsername(), member.getPassword(), member.getAge(), member.getDepartment(), member.getGender(), member.getName(), "Student")
                .execute();
    }
    public void addTeacher(Teacher teacher) {
        create.insertInto(MEMBERS, MEMBERS.USERNAME, MEMBERS.PASSWORD, MEMBERS.AGE, MEMBERS.DEPARTMENT, MEMBERS.GENDER, MEMBERS.NAME, MEMBERS.TYPE, MEMBERS.POSITION)
                .values(teacher.getUsername(), teacher.getPassword(), teacher.getAge(), teacher.getDepartment(), teacher.getGender(), teacher.getName(), "Teacher", teacher.getPosition())
                .execute();
    }
    public Member getMember(int id) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id))
                .fetchOneInto(Member.class);
    }
    public Member getMember(String username) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.USERNAME.eq(username))
                .fetchOneInto(Member.class);
    }
    public Teacher getTeacher(int id) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id), MEMBERS.TYPE.eq("Teacher"))
                .fetchOneInto(Teacher.class);
    }
    public Student getStudent(int id) {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id), MEMBERS.TYPE.eq("Student"))
                .fetchOneInto(Student.class);
    }
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
    public void deleteMember(int id) {
        create.deleteFrom(MEMBERS)
                .where(MEMBERS.ID.eq(id))
                .execute();
    }
    public List<Member> getAllMembers() {
        return create.selectFrom(MEMBERS)
                .fetchInto(Member.class);
    }
    public List<Teacher> getAllTeachers() {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.TYPE.eq("Teacher"))
                .fetchInto(Teacher.class);
    }
    public List<Student> getAllStudents() {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.TYPE.eq("Student"))
                .fetchInto(Student.class);
    }

}
