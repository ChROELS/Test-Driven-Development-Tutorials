package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations = {"classpath:spring/mvc-test-config.xml",
"classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {
    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    @Autowired
    OwnerController ownerController;

    @Autowired
    ClinicService clinicService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;
    @Test
    void initCreationFormTest() throws Exception {
        //Action
        mockMvc.perform(get("/owners/new"))
                //Assertions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("owner"))
                .andExpect(MockMvcResultMatchers.view().name(OWNERS_CREATE_OR_UPDATE_OWNER_FORM));
    }
    @Test
    void processFindFormNotFound() throws Exception {
        mockMvc.perform(get("/owners").param("lastName","not  found"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/findOwners"));
    }
    @Test
    void processFindFormOne() throws Exception {
        Owner owner = new Owner();
        final String lastName = "Holt";
        owner.setLastName(lastName);
        owner.setId(1);
        given(clinicService.findOwnerByLastName(lastName)).willReturn(Lists.newArrayList(owner));
        mockMvc.perform(get("/owners").param("lastName",lastName))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/1"));
        then(clinicService).should().findOwnerByLastName(anyString());
    }
    @Test
    void processFindFormList() throws Exception {
        given(clinicService.findOwnerByLastName("")).willReturn(Lists.newArrayList(new Owner(),new Owner()));
        mockMvc.perform(get("/owners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("owners/ownersList"));
        then(clinicService).should().findOwnerByLastName(stringArgumentCaptor.capture());
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
    }
    @Test
    void processCreationFormValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName","John")
                .param("lastName","Doe")
                .param("address","12 Magic Street")
                .param("city","NowhereVille")
                .param("telephone","123456789"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }
    @Test
    void processCreationFormNotValid() throws Exception {
        mockMvc.perform(post("/owners/new")
                .param("firstName","John")
                .param("lastName","Doe")
                .param("address","12 Magic Street"))
                //Two fields are empty => error object
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner"))
        .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner","city"))
        .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner","telephone"))
        .andExpect(MockMvcResultMatchers.view().name(OWNERS_CREATE_OR_UPDATE_OWNER_FORM));
    }
    @Test
    void processUpdateOwnerFormValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                .param("firstName", "John")
                .param("lastName","Doe")
                .param("address","12 Magic Street")
                .param("city","NowhereVille")
                .param("telephone","123456789"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/owners/{ownerId}"));
    }
    @Test
    void processUpdateFormNotValid() throws Exception {
        mockMvc.perform(post("/owners/{ownerId}/edit", 22)
                .param("firstName","John")
                .param("lastName","Doe")
                .param("address","12 Magic Street"))
                //Two fields are empty => error object
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("owner"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner","city"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("owner","telephone"))
                .andExpect(MockMvcResultMatchers.view().name(OWNERS_CREATE_OR_UPDATE_OWNER_FORM));
    }

}