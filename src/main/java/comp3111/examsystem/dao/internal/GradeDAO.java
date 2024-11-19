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

    /**
     * Constructor
     * <p>
     *     This constructor initializes the DSLContext for interacting with the database
     *     using the SQLite dialect. It attempts to establish a connection to the database
     *     and sets up the DSLContext for executing SQL queries.
     *     If a SQLException occurs while attempting to establish the connection, the stack trace
     *     of the exception is printed.
     *     </p>
     */
    public GradeDAO() {
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
     *     If a SQLException occurs while attempting to establish the connection, the stack trace
     *     of the exception is printed.
     *     </p>
     * @param conn connection to the database
     * */
    public GradeDAO(Connection conn) {
        try {
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Adds a grade to the database.
     * @param grade grade to be added
     */
    public void addGrade(Grade grade) {
        create.insertInto(GRADES, GRADES.STUDENT_NAME, GRADES.COURSE_NAME, GRADES.EXAM_NAME, GRADES.SCORE, GRADES.FULL_SCORE, GRADES.TIME_SPENT)
                .values(grade.getStudentName(), grade.getCourseName(), grade.getExamName(), grade.getScore(), grade.getFullScore(), grade.getTimeSpent())
                .execute();
    }

    /**
     * Retrieves a grade from the database using the grade ID.
     * <p>
     * This function fetches a grade from the database using the given grade ID.
     * </p>
     * @param id the ID of the grade to be retrieved
     * @return the grade with the given ID
     */
    public Grade getGrade(int id) {
        return create.selectFrom(GRADES)
                .where(GRADES.ID.eq(id))
                .fetchOneInto(Grade.class);
    }

    /**
     * Retrieves all grades from the database.
     * @return the list of all grades
     */
    public List<Grade> getAllGrades() {
        return create.selectFrom(GRADES)
                .fetchInto(Grade.class);
    }

    /**
     * Updates a grade in the database.
     * @param grade grade to be updated
     */
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

    /**
     * Deletes a grade from the database.
     * @param id the ID of the grade to be deleted
     */
    public void deleteGrade(int id) {
        create.deleteFrom(GRADES)
                .where(GRADES.ID.eq(id))
                .execute();
    }
}
