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

    public QuestionDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(Question question) {
    create.insertInto(QUESTIONS, QUESTIONS.QUESTION, QUESTIONS.OPTIONA, QUESTIONS.OPTIONB, QUESTIONS.OPTIONC, QUESTIONS.OPTIOND, QUESTIONS.ANSWER, QUESTIONS.TYPE, QUESTIONS.SCORE)
                .values(question.getQuestion(), question.getOptionA(), question.getOptionB(),question.getOptionC(), question.getOptionD(), question.getAnswer(), question.getType(), (float) question.getScore())
                .execute();
    }

    public Question getQuestion(int id) {
        return create.selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .fetchOneInto(Question.class);
    }

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

    public void deleteQuestion(int id) {
        create.deleteFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .execute();
    }

    public List<Question> getAllQuestions() {
        return create.selectFrom(QUESTIONS)
                .fetchInto(Question.class);
    }
}
