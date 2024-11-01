package comp3111.examsystem.dao.internal;

import comp3111.examsystem.DatabaseConnection;
import comp3111.examsystem.entity.Grade;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class GradeDAOTest {
    private GradeDAO gradeDAO;
    private Connection connection;

    @BeforeEach
    public void setUp() throws SQLException {
        DatabaseConnection.setUrl("jdbc:sqlite:./test.db");
        connection = DriverManager.getConnection("jdbc:sqlite:./test.db");
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
        create.execute("CREATE TABLE IF NOT EXISTS grades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_name TEXT NOT NULL, " +
                "course_name TEXT NOT NULL, " +
                "exam_name TEXT NOT NULL, " +
                "score REAL NOT NULL, " +
                "full_score REAL NOT NULL, " +
                "time_spent REAL NOT NULL)");

        gradeDAO = new GradeDAO();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
        create.execute("DROP TABLE IF EXISTS grades");
        connection.close();
    }

    @Test
    public void testAddGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45);
        gradeDAO.addGrade(grade);

        Grade retrievedGrade = gradeDAO.getGrade(1);
        assertNotNull(retrievedGrade);
        assertEquals("John Doe", retrievedGrade.getStudentName());
        assertEquals("COMP3111", retrievedGrade.getCourseName());
        assertEquals("Midterm", retrievedGrade.getExamName());
        assertEquals(85.5, retrievedGrade.getScore());
        assertEquals(100.0, retrievedGrade.getFullScore());
        assertEquals(45, retrievedGrade.getTimeSpent());
    }

    @Test
    public void testGetAllGrades() {
        Grade grade1 = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45);
        Grade grade2 = new Grade("Jane Smith", "COMP3111", "Final", 92.0F, 100.0F, 120);
        gradeDAO.addGrade(grade1);
        gradeDAO.addGrade(grade2);

        List<Grade> grades = gradeDAO.getAllGrades();
        assertEquals(2, grades.size());
    }

    @Test
    public void testUpdateGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45);
        gradeDAO.addGrade(grade);

        Grade updatedGrade = gradeDAO.getGrade(1);
        updatedGrade.setScore(90.0F);
        gradeDAO.updateGrade(updatedGrade);

        Grade retrievedGrade = gradeDAO.getGrade(1);
        assertEquals(90.0F, retrievedGrade.getScore());
    }

    @Test
    public void testDeleteGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45);
        gradeDAO.addGrade(grade);

        gradeDAO.deleteGrade(1);
        Grade retrievedGrade = gradeDAO.getGrade(1);
        assertNull(retrievedGrade);
    }
}
