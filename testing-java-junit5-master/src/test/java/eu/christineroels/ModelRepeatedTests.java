package eu.christineroels;

import org.junit.jupiter.api.*;

@Tag("ModelRepeated")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface ModelRepeatedTests {
    @BeforeAll
    static void setUpDefault(){
        System.out.println("This will be display once for each test implementing ModelRepeatedTests");
    }
    @BeforeEach
    default void displayTestInfos(TestInfo testInfo, RepetitionInfo repetitionInfo){
        System.out.println("Test infos\nDisplay name:\n" + testInfo.getDisplayName()
        + "\nIterations: " + repetitionInfo.getTotalRepetitions() + " times");
    }
}
