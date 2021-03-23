package eu.christineroels.controllers;

import guru.springframework.sfgpetclinic.controllers.IndexController;
import guru.springframework.sfgpetclinic.controllers.WrongNumberParameterException;
import org.junit.jupiter.api.*;

import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static org.junit.jupiter.api.Assertions.*;

class IndexControllerTest {
    private static IndexController indexController;
    @BeforeAll
    static void initialize(){
        indexController = new IndexController();
    }

    @DisplayName("Test Proper View name is returned for index page")
    @Test
    void index() {
        Assertions.assertEquals("index", indexController.index(),"does not return index page");
    }

    @Test
    void oupsHandler() {
        Assertions.assertThrows(WrongNumberParameterException.class, () -> indexController.oupsHandler());
    }

    @Disabled
    @Test
    void demoTimeOut(){
        Assertions.assertTimeout(Duration.ofSeconds(5), ()->{Thread.sleep(10000);
        System.out.println("I can always read that");},"Execution exceeded 5 seconds");
    }

    @Test
    void demoTimeOutPreemptively(){
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(5), ()->{Thread.sleep(2000);
            System.out.println("I cannot read that if this test fails by exceeding the expected time");},"Execution exceeded 5 seconds");
    }
}