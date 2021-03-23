package eu.christineroels.controllers;

import guru.springframework.sfgpetclinic.controllers.OwnerController;
import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelAndView;
import guru.springframework.sfgpetclinic.fauxspring.ModelImpl;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String OWNERS_FIND_OWNERS = "owners/findOwners";
    @Mock
    OwnerService ownerService;
    @Mock
    Owner owner;
    @Mock
    BindingResult bindingResult;
    @Mock
    Model model;
    @InjectMocks
    OwnerController ownerController;
    //to test conditions or special features inside methods
    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    private static final String REDIRECT_OWNERS = "redirect:/owners/";
    private static final String expectedError_VIEW = "owners/createOrUpdateOwnerForm";
    @Test
    @DisplayName("find owners - testing view name")
    void findOwners(){
        String viewName = ownerController.findOwners(model);
        assertThat(viewName).isEqualTo(OWNERS_FIND_OWNERS);
    }
    @Test
    @DisplayName("process Find Form - testing setting to empty string if lastName is null")
    void processFindFormLastNameToEmpty(){
        //given
        Owner dummy = new Owner(1L,null,null);
        //when
        ownerController.processFindForm(dummy,bindingResult,null);
        //then
        Assertions.assertEquals("",dummy.getLastName());
        //deprecated
        //verifyZeroInteractions(model);
    }

    @Test
    @DisplayName("process Find Form - testing list of owners is empty")
    void processFindFormListEmpty(){
        //given - mock owner
        //when
        //We don't interact with the model to test the implementation so it can stay null
        String viewName = ownerController.processFindForm(owner,bindingResult,null);
        //then
        Assertions.assertEquals("owners/findOwners",viewName);
        verifyNoInteractions(model);
    }
    @Test
    @DisplayName("process Find Form - testing list of owners is =1")
    void processFindFormListSize1(){
        //given
        Owner owner1 = new Owner(1L,"Rick", "Hastley");
        List<Owner> resultList = new ArrayList<>();
        resultList.add(owner1);
        //using mockito when syntax for a change
        when(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .thenReturn(resultList);
        //when
        //We don't interact with the model to test the implementation so it can stay null
        String viewName = ownerController.processFindForm(owner1,bindingResult,null);
        //then
        Assertions.assertEquals("redirect:/owners/" + owner1.getId(),viewName);
        verifyNoMoreInteractions(bindingResult);
    }
    //Alternatively (same test coverage but without checking viewname)
    @Test
    @DisplayName("process Find Form - testing the usage of wildcards in the query by last names")
    void processFindFormWildCardString(){
        List<Owner> resultList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(resultList);
        //Alternatively to the annotation for 'stringArgumentCaptor':
        // //final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        //when
        //We don't interact with the model to test the implementation so it can stay null
        String viewName = ownerController.processFindForm(owner,bindingResult,null);
        //then
        //the mock owner has a default null value for the field
        Assertions.assertEquals("%null%",stringArgumentCaptor.getValue());
    }
    @Test
    @DisplayName("process Find Form - testing list of owners is multiple")
    void processFindFormListMultiple(){
        //given
        Owner owner1 = new Owner(1L,"Rick", "Hastley");
        Owner owner2 = new Owner(1L,"Rita", "Hastley");
        //using BDD mockito willAnswer syntax for a change
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocationOnMock -> {
                    //by returning a list of owners with in this case multiple owners with the same lastnames
                    List<Owner> resultList2 = new ArrayList<>();
                    resultList2.add(owner1);
                    resultList2.add(owner2);
                return  resultList2;});
        //I will need a mock model and It is called after the service : order to respect
        InOrder inOrder = inOrder(ownerService,model);
        //when
        //We interact with the model to test the implementation so it has to be mocked
        String viewName = ownerController.processFindForm(owner1,bindingResult,model);
        //then
        //the dummies, owner1 has a value for the field
        assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("%Hastley%");
        //return view name
        Assertions.assertEquals("owners/ownersList",viewName);
        // method called inside the tested method
        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        //idem but interacting with model
        inOrder.verify(model).addAttribute(anyString(),anyList());
    }
    @Test
    @DisplayName("show owners - testing return value and interaction")
    void showOwner(){
        //given
        given(ownerController.showOwner(1L)).willReturn(any(ModelAndView.class));
        //when
        ModelAndView view = ownerController.showOwner(1L);
        //then
        verify(ownerService).findById(1L);
        assertThat(view).isNotNull();
    }

    @Test
    @DisplayName("testing saving a owner with binding result - error")
    void processCreationFormHasError() {
        //given
        //hasErrors returns a boolean
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        String viewName = ownerController.processCreationForm(owner,bindingResult);
        //then
        Assertions.assertEquals(expectedError_VIEW,viewName);
    }

    @Test
    @DisplayName("testing saving a owner with binding result- ok")
    void processCreationFormHasNoError() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(any(Owner.class))).willReturn(owner);
        //when
        String viewName = ownerController.processCreationForm(owner,bindingResult);
        //then
        Assertions.assertEquals(REDIRECT_OWNERS +owner.getId(),viewName);
    }
    @Test
    @DisplayName("testing updating a owner with binding result - error")
    void processUpdateFormHasError() {
        //given
        //hasErrors returns a boolean
        given(bindingResult.hasErrors()).willReturn(true);
        //when
        String viewName = ownerController.processUpdateOwnerForm(owner,bindingResult,11L);
        //then
        Assertions.assertEquals(expectedError_VIEW,viewName);
    }

    @Test
    @DisplayName("testing updating a owner with binding result- ok")
    void processUpdateFormHasNoError() {
        //given
        given(bindingResult.hasErrors()).willReturn(false);
        //id is being updated inside the tested method and returns as part of the URI
        when(owner.getId()).thenReturn(11L);
        given(ownerService.save(owner)).willReturn(owner);
        //when
        String viewName = ownerController.processUpdateOwnerForm(owner,bindingResult,11L);
        //then
        then(ownerService).should().save(owner);
        Assertions.assertEquals(REDIRECT_OWNERS +11L,viewName);
    }
}