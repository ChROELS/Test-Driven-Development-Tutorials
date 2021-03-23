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

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("laurel-properties")
@SpringJUnitConfig(value = {LaurelPropertiesWordPronounceTest.TestConfig.class})
@TestPropertySource({"classpath:laurel.properties"})
class LaurelPropertiesWordPronounceTest {

    @Configuration
    @ComponentScan(basePackages = "eu.christineroels.yannyLaurel")
    static class TestConfig{

    }

    @Autowired
    WordLauncher wordLauncher;

    @Test
    public void sayWordLaurel(){
        Assertions.assertEquals("laurel.com",wordLauncher.sayWord());
    }

}