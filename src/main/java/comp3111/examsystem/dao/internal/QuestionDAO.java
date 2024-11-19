package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Question;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.examsystem.jooq.generated.Tables.QUESTIONS;

public class QuestionDAO {
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
     *
     */
    public QuestionDAO() {
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
    public QuestionDAO(Connection conn) {
        try {
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a question to the database.
     * @param question question to be added
     */
    public void addQuestion(Question question) {
    create.insertInto(QUESTIONS, QUESTIONS.QUESTION, QUESTIONS.OPTIONA, QUESTIONS.OPTIONB, QUESTIONS.OPTIONC, QUESTIONS.OPTIOND, QUESTIONS.ANSWER, QUESTIONS.TYPE, QUESTIONS.SCORE)
                .values(question.getQuestion(), question.getOptionA(), question.getOptionB(),question.getOptionC(), question.getOptionD(), question.getAnswer(), question.getType(), (float) question.getScore())
                .execute();
    }

    /**
     * Retrieves a question from the database using the question ID.
     * <p>
     * This function fetches a question from the database using the given question ID.
     * </p>
     * @param id the ID of the question to be retrieved
     * @return the question with the given ID
     */
    public Question getQuestion(int id) {
        return create.selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .fetchOneInto(Question.class);
    }

    /**
     * Updates a question in the database.
     * @param question question to be updated
     */
    public void updateQuestion(Question question) {
        create.update(QUESTIONS)
                .set(QUESTIONS.QUESTION, question.getQuestion())
                .set(QUESTIONS.OPTIONA, question.getOptionA())
                .set(QUESTIONS.OPTIONB, question.getOptionB())
                .set(QUESTIONS.OPTIONC, question.getOptionC())
                .set(QUESTIONS.OPTIOND, question.getOptionD())
                .set(QUESTIONS.ANSWER, question.getAnswer())
                .set(QUESTIONS.TYPE, question.getType())
                .set(QUESTIONS.SCORE, (float) question.getScore())
                .where(QUESTIONS.ID.eq(question.getId()))
                .execute();
    }

    /**
     * Deletes a question from the database.
     * @param id the ID of the question to be deleted
     */
    public void deleteQuestion(int id) {
        create.deleteFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .execute();
    }

    /**
     * Get all questions from the database.
     * @return list of all questions.
     */
    public List<Question> getAllQuestions() {
        return create.selectFrom(QUESTIONS)
                .fetchInto(Question.class);
    }
}
