package eu.christineroels.yannyLaurel.JUnit5;

import eu.christineroels.yannyLaurel.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("base-test")
@SpringJUnitConfig(classes = {BaseConfig.class, YannyWordConfig.class})
class WordLauncherTest {
    @Autowired
    WordLauncher wordLauncher;


    @Test
    void sayWordYanny() {
        String yanny = wordLauncher.sayWord();
        Assertions.assertEquals("Yanny",yanny);
    }
}