package eu.christineroels.models;

import eu.christineroels.CustomArgsProvider;
import eu.christineroels.ModelTests;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.model.OwnerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest implements ModelTests {

    //Owner is a subclass of Person. Some of its fields depend of Person class
    //With grouped and dependent testcases, and proper headings and messages, we can easily
    //know where is the failure in the code while reading the test console
    @DisplayName("testing setters")
    @Test()
    void dependentAssertions(){
        Owner owner = new Owner(1L,"Jim","Raff");
        owner.setCity("Brussels");
        owner.setAddress("12 avenue des Fleurs");
        owner.setTelephone("-");
        //AssertAll groups the test under a heading
        Assertions.assertAll("All Properties",
                () -> assertAll("Person properties",
                        ()->assertEquals("Jim",owner.getFirstName()),
                        ()->assertEquals("Raff",owner.getLastName())),
                () -> assertAll("Owner properties",
                        ()->assertEquals("Brussels",owner.getCity()),
                        ()->assertEquals("12 avenue des Fleurs",owner.getAddress()),
                        ()->assertEquals("-",owner.getTelephone(),"Tel does not match")));
    }
    //Parameterized Test
    //With value source of a type
    @DisplayName("Owner with parameters")
    @ParameterizedTest(name = "{displayName}" + ParameterizedTest.DEFAULT_DISPLAY_NAME)
    @ValueSource(strings = {"Julia"
    , "Irima", "Lindsey"})
    void settingNames(String firstname){
        Owner owner = new Owner(1L,"Jim","Raff");
        owner.setFirstName(firstname);
    }
    //With Csv source hardcoded
    @DisplayName("Owner with parameters")
    @ParameterizedTest(name = "{displayName}" + ParameterizedTest.DEFAULT_DISPLAY_NAME)
    @CsvSource(value = {
            "Ronda, Kyle",
            "Marie, Martine",
            "Jean, Jacques"
    })
    void settingFirstNames(String firstname1, String firstname2){
        Owner owner = new Owner(1L,"Jim","Raff");
        owner.setFirstName(firstname1.concat("-"+firstname2));
        Assertions.assertEquals(firstname1+"-"+firstname2, owner.getFirstName());
        System.out.println(owner.getFirstName());
    }
    //With Csv source from file
    @DisplayName("Owner with parameters")
    @ParameterizedTest(name = "{displayName}" + ParameterizedTest.DEFAULT_DISPLAY_NAME)
    @CsvFileSource(resources = {"/owners.csv"},numLinesToSkip = 1)
    void settingNames(String firstname, String lastName){
        Owner owner = new Owner(1L,"Jim","Raff");
        owner.setFirstName(firstname);
        owner.setLastName(lastName);
        Assertions.assertEquals(firstname, owner.getFirstName());
        System.out.println(owner.getFirstName() +" "+ owner.getLastName());
    }
    //With Enums
    @DisplayName("Owner Type with parameters")
    @ParameterizedTest(name = "{displayName}" + ParameterizedTest.DEFAULT_DISPLAY_NAME)
    @EnumSource(OwnerType.class)
    void testingOwnerTypes(OwnerType ownerType){
        Assertions.assertFalse(()-> ownerType == null);
        System.out.println(ownerType);
    }
    //With Method Provider
    @DisplayName("Owner id with parameters")
    @ParameterizedTest(name = "{displayName}" + ParameterizedTest.DEFAULT_DISPLAY_NAME)
    @MethodSource(value = "valuesProvider")
    void testingOwnerTypes(IntStream value){
        //value is an object address when called in the ParameterizedTest.DEFAULT_DISPLAY_NAME
        value.forEach(System.out::println);
    }
    private Stream<Arguments> valuesProvider(){
        return Stream.of(Arguments.of(IntStream.range(1,11)),
                Arguments.of(IntStream.range(1,3)));
    }
    //With Custom Arguments Provider
    @DisplayName("Custom Argus Provider Test ")
    @ParameterizedTest(name = "{displayName}" + ParameterizedTest.DEFAULT_DISPLAY_NAME)
    @ArgumentsSource(CustomArgsProvider.class)
    void testingCustomArguments(Long id, String name, String key){
        Owner owner = new Owner(id, name, key);
        Assertions.assertAll("Testing getters",
                () -> Assertions.assertEquals(name, owner.getFirstName()),
                () -> Assertions.assertEquals(id, owner.getId()));
    }
}