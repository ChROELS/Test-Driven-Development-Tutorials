package eu.christineroels.models;

import eu.christineroels.ModelRepeatedTests;
import guru.springframework.sfgpetclinic.model.Person;
import org.junit.jupiter.api.*;


class PersonTest implements ModelRepeatedTests {
    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person(1L,"Jack","Dungeon");
    }

    @RepeatedTest(value = 5, name = RepeatedTest.LONG_DISPLAY_NAME)
    @DisplayName("Demo repeated test")
    void testPersonDisplay(){
        System.out.println(person.getFirstName());
    }


}