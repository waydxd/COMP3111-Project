package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Member;
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
        create.insertInto(MEMBERS, MEMBERS.USERNAME, MEMBERS.PASSWORD, MEMBERS.AGE, MEMBERS.DEPARTMENT, MEMBERS.GENDER, MEMBERS.NAME)
                .values(member.getUsername(), member.getPassword(), member.getAge(), member.getDepartment(), member.getGender(), member.getName())
                .execute();
    }
    public void addTeacher(Teacher teacher) {
        create.insertInto(MEMBERS, MEMBERS.USERNAME, MEMBERS.PASSWORD, MEMBERS.AGE, MEMBERS.DEPARTMENT, MEMBERS.GENDER, MEMBERS.NAME, MEMBERS.TYPE, MEMBERS.POSITION)
                .values(teacher.getUsername(), teacher.getPassword(), teacher.getAge(), teacher.getDepartment(), teacher.getGender(), teacher.getName(), "teacher", teacher.getPosition())
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
                .where(MEMBERS.ID.eq(id))
                .fetchOneInto(Teacher.class);
    }
    public void updateMember(Member member) {
        create.update(MEMBERS)
                .set(MEMBERS.USERNAME, member.getUsername())
                .set(MEMBERS.PASSWORD, member.getPassword())
                .where(MEMBERS.ID.eq(member.getId()))
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
                .where(MEMBERS.TYPE.eq("teacher"))
                .fetchInto(Teacher.class);
    }
    public List<Member> getAllStudents() {
        return create.selectFrom(MEMBERS)
                .where(MEMBERS.TYPE.eq("student"))
                .fetchInto(Member.class);
    }

}
