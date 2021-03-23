package eu.christineroels.services;

import guru.springframework.sfgpetclinic.repositories.VetRepository;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.springdatajpa.VetSDJpaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(value = MockitoExtension.class)
class VetSDJpaServiceTest {
    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSDJpaService vetService;

    @Test
    void deleteById() {
        //these 2 values '1L' must match
        vetService.deleteById(1L);
        //verify the behavior of the repository once the service has been called
        //default : verify one time
        verify(vetRepository).deleteById(1L);
    }
}