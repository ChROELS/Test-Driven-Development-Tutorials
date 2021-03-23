package eu.christineroels.yannyLaurel.JUnit5;

import eu.christineroels.yannyLaurel.WordLauncher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("component-scan")
@SpringJUnitConfig(classes = WordLauncherComponentScanTest.TestConfig.class)
class WordLauncherComponentScanTest {

    @Profile("component-scan")
    @Configuration
    @ComponentScan(basePackages = {"eu.christineroels.yannyLaurel"})
    static class TestConfig{
        //LaurelWordPronouncer is the component marked as primary
    }
    @Autowired
    WordLauncher wordLauncher;

    @Test
    void sayWord() {
        String word = wordLauncher.sayWord();
        Assertions.assertEquals("Laurel",word);
    }
}