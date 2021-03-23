package eu.christineroels.yannyLaurel.JUnit5;

import eu.christineroels.yannyLaurel.WordLauncher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("Yanny")
@SpringJUnitConfig(classes = WordLauncherActiveProfileTest.TestConfig2.class)
class WordLauncherActiveProfileTest {
    @Configuration
    @ComponentScan("eu.christineroels.yannyLaurel")
    static class TestConfig2{
        //LaurelWordPronouncer is the component marked as primary
    }
    @Autowired
    WordLauncher wordLauncher;

    @Test
    void sayWord() {
        String word = wordLauncher.sayWord();
        Assertions.assertEquals("Yanny",word);
    }
}