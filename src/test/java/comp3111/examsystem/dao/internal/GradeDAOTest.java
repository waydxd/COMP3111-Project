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
        create.execute("CREATE TABLE IF NOT EXISTS members (" +
                "username TEXT PRIMARY KEY)");

        create.execute("CREATE TABLE IF NOT EXISTS grades (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "student_name TEXT NOT NULL, " +
                "course_name TEXT NOT NULL, " +
                "exam_name TEXT NOT NULL, " +
                "score REAL NOT NULL, " +
                "full_score REAL NOT NULL, " +
                "time_spent REAL NOT NULL, " +
                "username TEXT NOT NULL, " +
                "FOREIGN KEY (username) REFERENCES members(username))");

        // Insert a test user into the members table
        create.execute("INSERT INTO members (username) VALUES ('1')");

        gradeDAO = new GradeDAO(connection);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DSLContext create = DSL.using(connection, SQLDialect.SQLITE);
        create.execute("DROP TABLE IF EXISTS grades");
        create.execute("DROP TABLE IF EXISTS members");
        connection.close();
    }

    @Test
    public void testAddGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
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
    public void testAddGradeWithMissingField() {
        Grade grade = new Grade(null, "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
        assertThrows(Exception.class, () -> gradeDAO.addGrade(grade));
    }

    @Test
    public void testGetAllGrades() {
        Grade grade1 = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
        Grade grade2 = new Grade("Jane Smith", "COMP3111", "Final", 92.0F, 100.0F, 120, "1");
        gradeDAO.addGrade(grade1);
        gradeDAO.addGrade(grade2);

        List<Grade> grades = gradeDAO.getAllGrades();
        assertEquals(2, grades.size());

        Grade firstGrade = grades.get(0);
        assertEquals("John Doe", firstGrade.getStudentName());
        assertEquals("Midterm", firstGrade.getExamName());

        Grade secondGrade = grades.get(1);
        assertEquals("Jane Smith", secondGrade.getStudentName());
        assertEquals("Final", secondGrade.getExamName());
    }

    @Test
    public void testUpdateGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
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
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
        gradeDAO.addGrade(grade);

        List<Grade> gradesBeforeDelete = gradeDAO.getAllGrades();
        assertEquals(1, gradesBeforeDelete.size());

        gradeDAO.deleteGrade(1);

        Grade retrievedGrade = gradeDAO.getGrade(1);
        assertNull(retrievedGrade);
    }

    @Test
    public void testDeleteNonExistentGrade() {
        assertThrows(Exception.class, () -> gradeDAO.deleteGrade(999));
    }

    @Test
    public void testUpdateNonExistentGrade() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
        grade.setId(999); // Non-existent ID
        assertThrows(Exception.class, () -> gradeDAO.updateGrade(grade));
    }

    @Test
    public void testGetNonExistingGrade() {
        Grade retrievedGrade = gradeDAO.getGrade(999); // Non-existing ID
        assertNull(retrievedGrade);
    }

    @Test
    public void testGetGradesForUser() {
        Grade grade1 = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
        Grade grade2 = new Grade("John Doe", "COMP3111", "Final", 92.0F, 100.0F, 120, "1");
        gradeDAO.addGrade(grade1);
        gradeDAO.addGrade(grade2);

        List<Grade> grades = gradeDAO.getGradesForUser("John Doe");
        assertEquals(2, grades.size());

        Grade firstGrade = grades.get(0);
        assertEquals("John Doe", firstGrade.getStudentName());
        assertEquals("Midterm", firstGrade.getExamName());

        Grade secondGrade = grades.get(1);
        assertEquals("John Doe", secondGrade.getStudentName());
        assertEquals("Final", secondGrade.getExamName());
    }

    @Test
    public void testGetGradesForUserWithNoGrades() {
        List<Grade> grades = gradeDAO.getGradesForUser("NonExistentUser");
        assertTrue(grades.isEmpty());
    }

    @Test
    public void testAddGradeWithNonExistentUsername() {
        Grade grade = new Grade("NonExistentUser", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "NonExistentUser");
        assertThrows(Exception.class, () -> gradeDAO.addGrade(grade));
    }

    @Test
    public void testUpdateGradeWithInvalidData() {
        Grade grade = new Grade("John Doe", "COMP3111", "Midterm", 85.5F, 100.0F, 45, "1");
        gradeDAO.addGrade(grade);

        Grade updatedGrade = gradeDAO.getGrade(1);
        updatedGrade.setScore(-10.0F); // Invalid score
        assertThrows(Exception.class, () -> gradeDAO.updateGrade(updatedGrade));
    }

    @Test
    public void testDeleteGradeWithInvalidData() {
        assertThrows(Exception.class, () -> gradeDAO.deleteGrade(-1)); // Invalid ID
    }
}