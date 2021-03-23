package eu.christineroels.controllers;

import guru.springframework.sfgpetclinic.controllers.VisitController;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    private VisitService visitService;

    //We will use the implementation fo the interface: to avoid
    // call abstract real method on java object!
    //Because calling real methods is only possible when mocking non abstract method.
    // correct example:
    //  when(mockOfConcreteClass.nonAbstractMethod()).thenCallRealMethod();
    @Spy
    private PetMapService petService;

    @InjectMocks
    VisitController visitController;

    @Test
    void loadPetWithVisit() {
        //given
        Map<String,Object> modelMap = new HashMap<>();
        Pet pet = new Pet(1L);
        Pet pet1 = new Pet(2L);
        //saving because first method called inside the tested method is a find type method
        petService.save(pet);
        petService.save(pet1);
        //method call with real method because it is a spy
        given(petService.findById(anyLong())).willCallRealMethod();
        //when
        Visit visit = visitController.loadPetWithVisit(1L,modelMap);
        //then
        Assertions.assertEquals(1L,visit.getPet().getId());
        verify(petService,times(1)).findById(anyLong());
        //the tested method performs a findById() to create a pet object based on the given id
        //So even if we have multiple pet saved, it will run one time and 'choose' the right object
    }

}