package com.example.practicelab.controller;

import com.example.practicelab.model.InvestmentRecord;
import com.example.practicelab.model.Projection;
import com.example.practicelab.repository.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;


public class RecordControllerTest {
    @Mock
    private RecordRepository recordRepository;

    RecordController recordController;

    @BeforeEach
    void init() { //init for initial
        MockitoAnnotations.openMocks(this);
        recordController = new RecordController(recordRepository);
    }

    //Test 1
    @Test
    void testOpenProjection() {
        InvestmentRecord investmentRecord=
                new InvestmentRecord(123, "Test", 100.0,2,"Savings-Regular");

        Mockito.when(recordRepository.findById(1L)).thenReturn(Optional.of(investmentRecord));

        Model model = new ConcurrentModel();

        String view = recordController.openProjection(1L, model);
        assertEquals(view, "projectiontable");

        List<Projection> projections = (List<Projection>) model.getAttribute("projections");

        assertNotNull(projections); //the list should not be empty
        assertEquals(projections.size(), 2); //in this test, year is 2 so the list should have 2 years

        //we will check every record of the object for the first year
        Projection year1 = projections.get(0);
        assertEquals(1, year1.getYear());
        assertEquals(100.0, year1.getStartingAmount());
        assertEquals(10.0, year1.getInterest());
        assertEquals(110.0, year1.getEndingBalance());

        //for the second year
        Projection year2 = projections.get(1);
        assertEquals(2, year2.getYear());
        assertEquals(110.0, year2.getStartingAmount());
        assertEquals(11.0, year2.getInterest());
        assertEquals(121.0, year2.getEndingBalance());
    }

    //second test
    @Test
    void testOpenProjectionThrowsWhenMissing() {

        Mockito.when(recordRepository.findById(5L))
                .thenReturn(Optional.empty());

        Model model = new ConcurrentModel();

        assertThrows(IllegalArgumentException.class,
                () -> recordController.openProjection(5L, model));
    }

    //third test
    @Test
    void testSubmitRedirects() {
        String view = recordController.submit();
        assertEquals("redirect:/", view);
    }
    @Test
    void testDeleteRecord() {
        String view = recordController.deleteRecord(1L);
        assertEquals("redirect:/", view);
        Mockito.verify(recordRepository).deleteById(1L);
    }

    @Test
    void testAddRecord() {
        InvestmentRecord investmentRecord=
                new InvestmentRecord(123, "Test", 100.0,2,"Savings-Regular");
        //Repo says record does NOT exist
        Mockito.when(recordRepository.existsByCustomerNumber(123))
                .thenReturn(false);

        // Mock RedirectAttributes (needed but not used in success)
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);
    String view = recordController.addRecord(investmentRecord, redirectAttributes );
    assertEquals("redirect:/", view);
    Mockito.verify(recordRepository).save(investmentRecord);

    }

    @Test
    void testAddRecordThrowsWhenRecordExists() {
        InvestmentRecord investmentRecord=
                new InvestmentRecord(123, "Test", 100.0,2,"Savings-Regular");
        //Repo says record does NOT exist
        Mockito.when(recordRepository.existsByCustomerNumber(123))
                .thenReturn(true);
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        String view = recordController.addRecord(investmentRecord, redirectAttributes );
        assertEquals("redirect:/", view);
        Mockito.verify(recordRepository,Mockito.never()).save(investmentRecord); //that it was not called

        Mockito.verify(redirectAttributes).addFlashAttribute("errorMessage",
                "The record you are trying to add already exists. Choose a different customer number");

//        Mockito.verify(redirectAttributes)
//                .addFlashAttribute(Mockito.eq("errorMessage"), Mockito.anyString()); //if the exact string does not matter
    }
}
