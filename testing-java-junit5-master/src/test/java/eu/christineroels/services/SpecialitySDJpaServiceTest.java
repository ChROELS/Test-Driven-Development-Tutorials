package eu.christineroels.services;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import guru.springframework.sfgpetclinic.services.springdatajpa.SpecialitySDJpaService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {
    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService specialitySDJpaService;

    @Test
    void findAll() {
        //given
        Set<Speciality> specialities = new HashSet<>();
        given(specialtyRepository.findAll()).willReturn(specialities);
        //when
        Set<Speciality> resultSet = specialitySDJpaService.findAll();
        //then
        then(specialtyRepository).should().findAll();
        Assertions.assertNotNull(resultSet);
    }

    @Test
    void findById() {
        //given
        Optional<Speciality> speciality = Optional.of(new Speciality());
        given(specialtyRepository.findById(anyLong())).willReturn(speciality);
        //when
        Speciality foundSpecialty = specialitySDJpaService.findById(anyLong());
        //then
        //timeout if we have performance requirements
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
        Assertions.assertNotNull(foundSpecialty);
    }

    @Test
    void save() {
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.save(speciality)).willReturn(speciality);
        //when
        specialitySDJpaService.save(speciality);
        //then
        then(specialtyRepository).should().save(speciality);
    }

    @Test
    void delete() {
        //given
        Speciality mockSpecialty = mock(Speciality.class);
        //when
        specialitySDJpaService.delete(mockSpecialty);
        //then
        then(specialtyRepository).should().delete(mockSpecialty);
    }

    @Test
    void deleteById() {
        //given - none
        //when
        specialitySDJpaService.deleteById(anyLong());
        //then
        then(specialtyRepository).should().deleteById(anyLong());
    }
    @Test
    void deleteByIdMultipleTimeOut() {
        //given - none

        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        then(specialtyRepository).should(timeout(100).times(2)).deleteById(1L);
    }

    @Test
    void deleteByIdAtLeast() {
        //given

        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        then(specialtyRepository).should(timeout(10).atLeastOnce()).deleteById(1L);
    }

    @Test
    void deleteByIdAtMost() {
        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(1l);

        //then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {

        //when
        specialitySDJpaService.deleteById(1l);
        specialitySDJpaService.deleteById(2l);

        //then
        then(specialtyRepository).should(never()).deleteById(5L);

    }
}