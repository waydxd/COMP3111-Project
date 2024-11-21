package comp3111.examsystem.dao.internal;

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
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
        create.execute("CREATE TABLE IF NOT EXISTS grades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_name TEXT NOT NULL, " +
                "course_name TEXT NOT NULL, " +
                "exam_name TEXT NOT NULL, " +
                "score REAL NOT NULL, " +
                "full_score REAL NOT NULL, " +
                "time_spent REAL NOT NULL)");

        gradeDAO = new GradeDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
        create.execute("DROP TABLE IF EXISTS grades");
        connection.close();
    }

    @Test
    public void testAddGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45,"1");
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
        Grade grade1 = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45,"1");
        Grade grade2 = new Grade("Jane Smith", "COMP3111", "Final", 92.0F, 100.0F, 120,"1");
        gradeDAO.addGrade(grade1);
        gradeDAO.addGrade(grade2);

        List<Grade> grades = gradeDAO.getAllGrades();
        assertEquals(2, grades.size());
        
        // Test specific properties of retrieved grades
        Grade firstGrade = grades.get(0);
        assertEquals("John Doe", firstGrade.getStudentName());
        assertEquals("Midterm", firstGrade.getExamName());
        
        Grade secondGrade = grades.get(1);
        assertEquals("Jane Smith", secondGrade.getStudentName());
        assertEquals("Final", secondGrade.getExamName());
    }

    @Test
    public void testUpdateGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45,"1");
        gradeDAO.addGrade(grade);

        Grade updatedGrade = gradeDAO.getGrade(1);
        updatedGrade.setScore(90.0F);
        updatedGrade.setTimeSpent(50);
        updatedGrade.setStudentName("John Smith");
        gradeDAO.updateGrade(updatedGrade);

        Grade retrievedGrade = gradeDAO.getGrade(1);
        assertEquals(90.0F, retrievedGrade.getScore());
        assertEquals(50, retrievedGrade.getTimeSpent());
        assertEquals("John Smith", retrievedGrade.getStudentName());
    }

    @Test
    public void testDeleteGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45,"1");
        gradeDAO.addGrade(grade);

        List<Grade> gradesBeforeDelete = gradeDAO.getAllGrades();
        assertEquals(1, gradesBeforeDelete.size());

        gradeDAO.deleteGrade(1);
        
        Grade retrievedGrade = gradeDAO.getGrade(1);
        assertNull(retrievedGrade);
    }

    @Test
    public void testUpdateNonExistentGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45,"1");
        grade.setId(999); // Non-existent ID
        assertThrows(Exception.class, () -> gradeDAO.updateGrade(grade));
    }

    @Test
    public void testGetNonExistingGrade() {
        Grade retrievedGrade = gradeDAO.getGrade(999); // Non-existing ID
        assertNull(retrievedGrade);
    }
}