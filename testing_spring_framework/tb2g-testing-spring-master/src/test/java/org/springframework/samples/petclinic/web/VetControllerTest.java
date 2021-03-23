package org.springframework.samples.petclinic.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ModelMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    public static final String VET_LIST = "vets/vetList";
    @Mock
    ClinicService clinicService;

    @InjectMocks
    VetController vetController;

    List<Vet> vetsList = new ArrayList<>();
    //Mocking servlets
    MockMvc mockMvc;
    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(vetController).build();
    }
    @Test
    void testControllerShow() throws Exception {
        //request msg goes through the dispatcher servlet to find the page
        mockMvc.perform(get("/vets.html"))
        //response, expecting a status Ok
                .andExpect(status().isOk())
        //model takes on an attribute
        .andExpect(model().attributeExists("vets"))
        //response returns the good view name
        .andExpect(view().name(VET_LIST));
    }
    @Test
    void showVetList() {
        //given
        ModelMap modelMap = mock(ModelMap.class);
        given(clinicService.findVets()).willReturn(vetsList);
        //when
        String viewName = vetController.showVetList(modelMap);
        //then
        Assertions.assertEquals(VET_LIST, viewName);
        verify(clinicService).findVets();
        verify(modelMap).put(anyString(),any(Vets.class));
        then(clinicService).shouldHaveNoMoreInteractions();
    }

    @Test
    void showResourcesVetList() {
        //given
        given(clinicService.findVets()).willReturn(vetsList);
        //when
        Vets vetsList = vetController.showResourcesVetList();
        //then
        Assertions.assertNotNull(vetsList);
        verify(clinicService).findVets();
        then(clinicService).shouldHaveNoMoreInteractions();

    }
}