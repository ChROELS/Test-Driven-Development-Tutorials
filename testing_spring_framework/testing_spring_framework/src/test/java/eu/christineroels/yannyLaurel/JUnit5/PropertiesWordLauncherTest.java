package eu.christineroels.yannyLaurel.JUnit5;

import eu.christineroels.yannyLaurel.WordLauncher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@TestPropertySource("classpath:yanny.properties")
@ActiveProfiles("externalized")
@SpringJUnitConfig(PropertiesWordLauncherTest.TestConfig.class)
class PropertiesWordLauncherTest {
    @Configuration
    @ComponentScan("eu.christineroels.yannyLaurel")
    static class TestConfig{
    }
    @Autowired
    WordLauncher wordLauncher;

    @Test
    void sayWord() {
        String word = wordLauncher.sayWord();
        Assertions.assertEquals("yanny.com",word);
    }


}
