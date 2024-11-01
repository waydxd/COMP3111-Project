package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Grade;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.examsystem.jooq.generated.tables.Grades.GRADES;

public class GradeDAO {
    private DSLContext create;

    public GradeDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGrade(Grade grade) {
        create.insertInto(GRADES, GRADES.STUDENT_NAME, GRADES.COURSE_NAME, GRADES.EXAM_NAME, GRADES.SCORE, GRADES.FULL_SCORE, GRADES.TIME_SPENT)
                .values(grade.getStudentName(), grade.getCourseName(), grade.getExamName(), grade.getScore(), grade.getFullScore(), grade.getTimeSpent())
                .execute();
    }

    public Grade getGrade(int id) {
        return create.selectFrom(GRADES)
                .where(GRADES.ID.eq(id))
                .fetchOneInto(Grade.class);
    }

    public List<Grade> getAllGrades() {
        return create.selectFrom(GRADES)
                .fetchInto(Grade.class);
    }

    public void updateGrade(Grade grade) {
        create.update(GRADES)
                .set(GRADES.STUDENT_NAME, grade.getStudentName())
                .set(GRADES.COURSE_NAME, grade.getCourseName())
                .set(GRADES.EXAM_NAME, grade.getExamName())
                .set(GRADES.SCORE, grade.getScore())
                .set(GRADES.FULL_SCORE, grade.getFullScore())
                .set(GRADES.TIME_SPENT, grade.getTimeSpent())
                .where(GRADES.ID.eq(grade.getId()))
                .execute();
    }

    public void deleteGrade(int id) {
        create.deleteFrom(GRADES)
                .where(GRADES.ID.eq(id))
                .execute();
    }
}
