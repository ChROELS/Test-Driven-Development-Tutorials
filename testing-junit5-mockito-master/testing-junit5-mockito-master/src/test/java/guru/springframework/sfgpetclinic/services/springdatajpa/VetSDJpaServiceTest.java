package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.repositories.VetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VetSDJpaServiceTest {
    @Mock
    private VetRepository mockVetRepository;
    @InjectMocks
    VetSDJpaService mockVetSDJpaService;
    @Test
    void deleteById() {
        mockVetSDJpaService.deleteById(1L);
        verify(mockVetRepository).deleteById(1L);
    }
}