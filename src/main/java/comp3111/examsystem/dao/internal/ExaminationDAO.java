package comp3111.examsystem.dao.internal;

import com.examsystem.jooq.generated.tables.ExaminationQuestions;
import com.examsystem.jooq.generated.tables.Questions;
import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Question;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import comp3111.examsystem.entity.Examination;

import static com.examsystem.jooq.generated.Tables.QUESTIONS;
import static com.examsystem.jooq.generated.tables.Examinations.EXAMINATIONS;

public class ExaminationDAO {
    private DSLContext create;

    /**
     * Constructor for ExaminationDAO.
     * <p>
     * This constructor initializes the DSLContext for interacting with the database
     * using the SQLite dialect. It attempts to establish a connection to the database
     * and sets up the DSLContext for executing SQL queries.
     * </p>
     * <p>
     * If a SQLException occurs while attempting to establish the connection, the stack trace
     * of the exception is printed.
     * </p>
     */
    public ExaminationDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Constructor for ExaminationDAO.
     * <p>
     * This constructor initializes the DSLContext for interacting with the database
     * using the SQLite dialect. It attempts to establish a connection to the database
     * and sets up the DSLContext for executing SQL queries.
     * </p>
     * <p>
     * If a SQLException occurs while attempting to establish the connection, the stack trace
     * of the exception is printed.
     * </p>
     * @param conn connection to the database
     * */
    public ExaminationDAO(Connection conn) {
        try {
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an examination to the database.
     * <p>
     * This function inserts a new examination into the database using the given Examination object.
     * </p>
     * @param exam the examination to be added
     */
    public void addExamination(Examination exam) {
        create.insertInto(EXAMINATIONS, EXAMINATIONS.COURSEID, EXAMINATIONS.EXAMTIME, EXAMINATIONS.EXAMNAME, EXAMINATIONS.PUBLISH)
                .values(exam.getCourseID(), exam.getExamTime(), exam.getExamName(), exam.getPublish())
                .execute();
    }

    /**
     * Retrieves an examination from the database using the examination ID.
     * <p>
     * This function fetches an examination from the database using the given examination ID.
     * </p>
     * @param id the ID of the examination to be retrieved
     * @return the examination with the given ID
     */
    public Examination getExamination(int id) {
        return create.selectFrom(EXAMINATIONS)
                .where(EXAMINATIONS.ID.eq(id))
                .fetchOneInto(Examination.class);
    }

    /**
     * Retrieves all examinations from the database.
     * @return a list of all examinations in the database
     */
    public List<Examination> getAllExaminations() {
        return create.selectFrom(EXAMINATIONS)
                .fetchInto(Examination.class);
    }

    /**
     * Updates an examination in the database.
     * @param Examination the examination with new content to be updated
     * <p>
     * This function updates an examination in the database using the given Examination object.
     * </p>
     */
    public void updateExamination(Examination Examination){
        create.update(EXAMINATIONS)
                .set(EXAMINATIONS.COURSEID, Examination.getCourseID())
                .set(EXAMINATIONS.EXAMTIME, Examination.getExamTime())
                .set(EXAMINATIONS.EXAMNAME, Examination.getExamName())
                .set(EXAMINATIONS.PUBLISH, Examination.getPublish())
                .where(EXAMINATIONS.ID.eq(Examination.getId()))
                .execute();
    }

    /**
     * Deletes an examination from the database.
     * <p>
     * This function deletes an examination from the database using the given examination ID.
     * </p>
     * @param id the ID of the examination to be deleted
     */
    public void deleteExamination(int id) {
        create.deleteFrom(EXAMINATIONS)
                .where(EXAMINATIONS.ID.eq(id))
                .execute();
    }

    /**
     * @param examinationId id of the examination to be altered
     * @param questionId question ID to be added
     * @throws Exception
     * Add a question to an examination
     * If the question is already in the examination, throw an exception
     * If the question is not in the examination, add the question to the examination
     * If the question is not in the database, throw an exception
     * If the examination is not in the database, throw an exception
     * If the question is added to the examination, return
     */
    public void addQuestionToExamination(int examinationId, int questionId) throws Exception {

            Integer count = create.selectCount()
                    .from(ExaminationQuestions.EXAMINATION_QUESTIONS)
                    .where(ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID.eq(examinationId))
                    .and(ExaminationQuestions.EXAMINATION_QUESTIONS.QUESTION_ID.eq(questionId))
                    .fetchOne(0, int.class);

            if (count != null && count == 0) {
                create.insertInto(ExaminationQuestions.EXAMINATION_QUESTIONS, ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID, ExaminationQuestions.EXAMINATION_QUESTIONS.QUESTION_ID)
                        .values(examinationId, questionId)
                        .execute();
            } else {
                throw new Exception("repeated");
            }

    }

    /**
     * Remove a question from an examination
     * @param examinationId id of the examination to be altered
     * @param questionId question ID to be removed
     */
    public void removeQuestionFromExamination(int examinationId, int questionId) {
        create.deleteFrom(ExaminationQuestions.EXAMINATION_QUESTIONS)
                .where(ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID.eq(examinationId))
                .and(ExaminationQuestions.EXAMINATION_QUESTIONS.QUESTION_ID.eq(questionId))
                .execute();
    }

    /**
     * Get all questions in an examination
     * @param examinationId id of the examination
     * @return a list of questions in the examination
     */
    public List<Question> getQuestionsInExamination(int examinationId) {
        // Step 1: Fetch question IDs
        List<Integer> questionIds = create.select(ExaminationQuestions.EXAMINATION_QUESTIONS.QUESTION_ID)
                .from(ExaminationQuestions.EXAMINATION_QUESTIONS)
                .where(ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID.eq(examinationId))
                .fetchInto(Integer.class);

        // Step 2: Fetch questions using the question IDs
        return create.selectFrom(Questions.QUESTIONS)
                .where(QUESTIONS.ID.in(questionIds))
                .fetchInto(Question.class);
    }
}
