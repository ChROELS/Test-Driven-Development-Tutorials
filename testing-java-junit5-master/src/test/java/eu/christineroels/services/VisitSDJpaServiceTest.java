package eu.christineroels.services;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import guru.springframework.sfgpetclinic.services.springdatajpa.VisitSDJpaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {
    @Mock
    VisitRepository mockVisitRepository;
    @InjectMocks
    VisitSDJpaService mockService;
    @Test
    void findAll() {
        Set<Visit> foundResult = mockService.findAll();
        verify(mockVisitRepository).findAll();
        Assertions.assertEquals(new HashSet<Visit>().size(),foundResult.size(),
                "findAll does not return a set of Visit objects");
    }

    @Test
    void findById() {
        Visit visit = new Visit();
        //Ongoing Stubbing: When().thenReturn() from Mockito
         when(mockVisitRepository.findById(1L)).thenReturn(Optional.of(visit));
        Visit mockResult = mockService.findById(1L);
        //Using arguments matcher anyLong()
        verify(mockVisitRepository).findById(anyLong());
        assertThat(mockResult).isNotNull();
    }

    @Test
    void saveInteractions() {
        //inline creation of a mock object
        Visit mockVisit = mock(Visit.class);
        Assertions.assertNotNull(mockVisit);
        mockService.save(mockVisit);
        verify(mockVisitRepository).save(mockVisit);
    }
    @Test
    void saveReturnValue() {
        Visit visit = new Visit();
        when(mockVisitRepository.save(any(Visit.class))).thenReturn(visit);
        Visit savedVisit = mockService.save(new Visit());
        Assertions.assertNotNull(savedVisit);

    }

    @Test
    void delete() {
        //Using any(class constant)
        mockService.delete(new Visit());
        verify(mockVisitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteById() {
        mockService.deleteById(2L);
        //Using Arguments Matcher general any()
        verify(mockVisitRepository).deleteById(any());
    }
}