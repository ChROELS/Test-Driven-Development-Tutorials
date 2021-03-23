package eu.christineroels.models;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.PetType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {
    Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Pet();
    }

    @Test
    void getBirthDate() {
        //LocalDate is a constant object
        LocalDate dateOfBirth = LocalDate.of(2018,3,15);
        pet.setBirthDate(dateOfBirth);
        //assertSame
        Assertions.assertSame(dateOfBirth, pet.getBirthDate());
    }
    @Nested
    class PetTestType{
        PetType petType;
        @BeforeEach
        void setUpPetType(){
            petType = new PetType();
            petType.setName("Dog");
        }
        @Test
        void getPetType(){
            pet.setPetType(petType);
            Assertions.assertEquals("Dog",petType.getName());
        }
    }
}