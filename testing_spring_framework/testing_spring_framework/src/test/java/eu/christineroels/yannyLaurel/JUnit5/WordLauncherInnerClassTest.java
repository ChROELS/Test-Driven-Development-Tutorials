package eu.christineroels.yannyLaurel.JUnit5;

import eu.christineroels.yannyLaurel.LaurelWordPronounce;
import eu.christineroels.yannyLaurel.WordLauncher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("inner-class")
@SpringJUnitConfig(classes = WordLauncherInnerClassTest.TestConfig.class)
class WordLauncherInnerClassTest {
    @Profile("inner-class")
    @Configuration
    static class TestConfig{
        @Bean
        WordLauncher wordLauncher(){
            return new WordLauncher(new LaurelWordPronounce());
        }
    }
    @Autowired
    WordLauncher wordLauncher;

    @Test
    void sayWord() {
        String word = wordLauncher.sayWord();
        Assertions.assertEquals("Laurel",word);
    }
}