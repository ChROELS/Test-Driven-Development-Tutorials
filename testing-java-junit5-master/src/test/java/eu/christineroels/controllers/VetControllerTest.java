package eu.christineroels.controllers;

import guru.springframework.sfgpetclinic.controllers.VetController;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.fauxspring.ModelImpl;
import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.model.Vet;
import guru.springframework.sfgpetclinic.services.VetService;
import guru.springframework.sfgpetclinic.services.map.SpecialityMapService;
import guru.springframework.sfgpetclinic.services.map.VetMapService;
import org.junit.jupiter.api.*;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class VetControllerTest {
    public static final String VETS_INDEX = "vets/index";
    private VetController vetController;
    private static VetService vetMapService;
    private Model model;
    private static Vet veterinary1;
    private static Vet veterinary2;
    @BeforeAll
    static void setUpService(){
        //VetMapService
        vetMapService = new VetMapService(new SpecialityMapService());
        //Specialties to create 2 vets
        Speciality specialityFurry = new Speciality();
        specialityFurry.setDescription("Dogs and cats");
        Speciality specialityNAC = new Speciality();
        specialityNAC.setDescription("Snakes, lizards, spiders");
        HashSet<Speciality> specialties = new HashSet<>();
        specialties.add(specialityFurry);
        specialties.add(specialityNAC);
        HashSet<Speciality> specialtiesRestricted = new HashSet<>();
        specialtiesRestricted.add(specialityFurry);
        //2 Vets objects to add to the vetMapService
        veterinary1 = new Vet(1L,"Jean","Poilu", specialtiesRestricted);
        veterinary2 = new Vet(2L,"Bob","Cooleman", specialties);
        vetMapService.save(veterinary1);
        vetMapService.save(veterinary2);
    }


    @BeforeEach
    void setUp() {
        //VetController
       vetController = new VetController(vetMapService);
       //Model
       model = new ModelImpl();
    }

    @Test
    @DisplayName("VetController --> listVets page")
    void listVets() {
        Assertions.assertEquals("vets/index", vetController.listVets(model),"does not return the good page");
    }
    @Test
    void VetMapServiceUpdated(){
        Assertions.assertAll("2 Vets saved",
                () ->Assertions.assertTrue(()-> vetMapService.findAll().contains(veterinary1),
                "VetMapService does not contain veterinary1 object"),
                ()-> Assertions.assertTrue(()-> vetMapService.findAll().contains(veterinary2),
                        "VetMapService does not contain veterinary2 object"));
    }

    @Test
    void MockTestListVetModel(){
        //Act
        model.addAttribute("vets",vetMapService.findAll());
        //Assert
        Assertions.assertIterableEquals(vetMapService.findAll(),
                (HashSet<Vet>)((ModelImpl) model).getAttribute("vets"),
                "Model does not contain list of Vets");

    }
    @Test
    @DisplayName("VetController --> Model Map is updated with list of Vets")
    void listVetModel(){
        //Act (testing the actual method implementation written in the class)
        String viewName = vetController.listVets(model);
        //Assert
        Assertions.assertIterableEquals(vetMapService.findAll(),
                (HashSet<Vet>)((ModelImpl) model).getAttribute("vets"),
                "Model does not contain list of Vets");
        assertThat(viewName).isEqualTo(VETS_INDEX);
    }
}