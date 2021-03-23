package eu.christineroels;

import org.junit.jupiter.api.*;

@Tag("Model")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface ModelTests {
    @BeforeAll
    static void setUpDefault(){
        System.out.println("This will be display once for each test implementing ModelTests");
    }
    @BeforeEach
    default void displayTestInfos(TestInfo testInfo){
        System.out.println("Test infos\nDisplay name:\n" + testInfo.getDisplayName());
    }
}
