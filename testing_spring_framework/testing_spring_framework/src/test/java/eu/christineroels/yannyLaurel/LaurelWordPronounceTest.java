package eu.christineroels.yannyLaurel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
@ActiveProfiles("base-test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class,LaurelWordConfig.class})
public class LaurelWordPronounceTest {
    @Autowired
    WordLauncher wordLauncher;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getWord() {
        Assert.assertEquals("Laurel",wordLauncher.sayWord());
    }
}