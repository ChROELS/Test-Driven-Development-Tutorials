package eu.christineroels.yannyLaurel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("base-test")
@Configuration
public class LaurelWordConfig {
    @Bean
    public LaurelWordPronounce getWordPronounce(){
        return new LaurelWordPronounce();
    }
}
