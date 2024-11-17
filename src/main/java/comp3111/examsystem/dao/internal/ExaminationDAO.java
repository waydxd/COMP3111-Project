package comp3111.examsystem.dao.internal;

import com.examsystem.jooq.generated.tables.Examinations;
import com.examsystem.jooq.generated.tables.ExaminationQuestions;
import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Question;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import comp3111.examsystem.entity.Examination;

import static com.examsystem.jooq.generated.tables.Examinations.EXAMINATIONS;
public class ExaminationDAO {
    private DSLContext create;
    public ExaminationDAO() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            this.create = DSL.using(conn, SQLDialect.SQLITE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addExamination(Examination exam) {
        create.insertInto(EXAMINATIONS, EXAMINATIONS.COURSEID, EXAMINATIONS.EXAMTIME, EXAMINATIONS.EXAMNAME, EXAMINATIONS.PUBLISH)
                .values(exam.getCourseID(), exam.getExamTime(), exam.getExamName(), exam.getPublish())
                .execute();
    }

    public Examination getExamination(int id) {
        return create.selectFrom(EXAMINATIONS)
                .where(EXAMINATIONS.ID.eq(id))
                .fetchOneInto(Examination.class);
    }
//    public Examination getExamination(String examName) {
//        return create.selectFrom(EXAMINATIONS)
//                .where(EXAMINATIONS.EXAMNAME.eq(examName))
//                .fetchOneInto(Examination.class);
//    }
    public List<Examination> getAllExaminations() {
        return create.selectFrom(EXAMINATIONS)
                .fetchInto(Examination.class);
    }
    public void updateExamination(Examination Examination){
        create.update(EXAMINATIONS)
                .set(EXAMINATIONS.COURSEID, Examination.getCourseID())
                .set(EXAMINATIONS.EXAMTIME, Examination.getExamTime())
                .set(EXAMINATIONS.EXAMNAME, Examination.getExamName())
                .set(EXAMINATIONS.PUBLISH, Examination.getPublish())
                .where(EXAMINATIONS.ID.eq(Examination.getId()))
                .execute();
    }
    public void deleteExamination(int id) {
        create.deleteFrom(EXAMINATIONS)
                .where(EXAMINATIONS.ID.eq(id))
                .execute();
    }

    public void addQuestionToExamination(int examinationId, int questionId) {
        create.insertInto(ExaminationQuestions.EXAMINATION_QUESTIONS, ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID, ExaminationQuestions.EXAMINATION_QUESTIONS.QUESTION_ID)
                .values(examinationId, questionId)
                .execute();
    }

    public void removeQuestionFromExamination(int examinationId, int questionId) {
        create.deleteFrom(ExaminationQuestions.EXAMINATION_QUESTIONS)
                .where(ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID.eq(examinationId))
                .and(ExaminationQuestions.EXAMINATION_QUESTIONS.QUESTION_ID.eq(questionId))
                .execute();
    }

    public List<Question> getQuestionsInExamination(int examinationId) {
        return create.select()
                .from(ExaminationQuestions.EXAMINATION_QUESTIONS)
                .join(Examinations.EXAMINATIONS)
                .on(ExaminationQuestions.EXAMINATION_QUESTIONS.EXAMINATION_ID.eq(Examinations.EXAMINATIONS.ID))
                .where(Examinations.EXAMINATIONS.ID.eq(examinationId))
                .fetchInto(Question.class);
    }
}
