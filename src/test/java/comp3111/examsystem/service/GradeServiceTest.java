package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.GradeDAO;
import comp3111.examsystem.entity.Grade;
import comp3111.examsystem.service.internal.GradeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    @Mock
    private GradeDAO gradeDAO;

    @InjectMocks
    private GradeServiceImpl gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gradeService = new GradeServiceImpl(gradeDAO);
    }

    @Test
    void addGrade() {
        Grade grade = new Grade();
        grade.setId(1);
        grade.setScore(95.0F);

        gradeService.addGrade(grade);

        verify(gradeDAO).addGrade(grade);
    }

    @Test
    void getGrade() {
        Grade mockGrade = new Grade();
        mockGrade.setId(1);
        mockGrade.setScore(95.0F);
        when(gradeDAO.getGrade(1)).thenReturn(mockGrade);

        Grade result = gradeService.getGrade(1);

        verify(gradeDAO).getGrade(1);
        assertNotNull(result);
        assertEquals(mockGrade.getId(), result.getId());
        assertEquals(mockGrade.getScore(), result.getScore());
    }

    @Test
    void getAllGrades() {
        List<Grade> mockGrades = Arrays.asList(new Grade(), new Grade());
        when(gradeDAO.getAllGrades()).thenReturn(mockGrades);

        List<Grade> result = gradeService.getAllGrades();

        verify(gradeDAO).getAllGrades();
        assertEquals(mockGrades, result);
    }

    @Test
    void updateGrade() {
        Grade grade = new Grade();
        grade.setId(1);
        grade.setScore(98.0F);

        gradeService.updateGrade(grade);

        verify(gradeDAO).updateGrade(grade);
    }

    @Test
    void deleteGrade() {
        gradeService.deleteGrade(1);

        verify(gradeDAO).deleteGrade(1);
    }
}