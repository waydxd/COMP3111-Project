package comp3111.examsystem.service;

import comp3111.examsystem.dao.internal.ExaminationDAO;
import comp3111.examsystem.entity.Examination;
import comp3111.examsystem.service.internal.ExaminationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ExaminationServiceTest {

    @Mock
    private ExaminationDAO examinationDAO;

    @InjectMocks
    private ExaminationServiceImpl examinationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddExamination() {
        Examination examination = new Examination();
        doNothing().when(examinationDAO).addExamination(examination);

        examinationService.addExamination(examination);

        verify(examinationDAO, times(1)).addExamination(examination);
    }

    @Test
    void testGetExamination() {
        Examination examination = new Examination();
        when(examinationDAO.getExamination(1)).thenReturn(examination);

        Examination result = examinationService.getExamination(1);

        assertEquals(examination, result);
        verify(examinationDAO, times(1)).getExamination(1);
    }

    @Test
    void testGetAllExaminations() {
        List<Examination> examinations = Arrays.asList(new Examination(), new Examination());
        when(examinationDAO.getAllExaminations()).thenReturn(examinations);

        List<Examination> result = examinationService.getAllExaminations();

        assertEquals(examinations, result);
        verify(examinationDAO, times(1)).getAllExaminations();
    }

    @Test
    void testUpdateExamination() {
        Examination examination = new Examination();
        doNothing().when(examinationDAO).updateExamination(examination);

        examinationService.updateExamination(examination);

        verify(examinationDAO, times(1)).updateExamination(examination);
    }

    @Test
    void testDeleteExamination() {
        doNothing().when(examinationDAO).deleteExamination(1);

        examinationService.deleteExamination(1);

        verify(examinationDAO, times(1)).deleteExamination(1);
    }
}