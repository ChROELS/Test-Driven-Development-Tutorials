package eu.christineroels.yannyLaurel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("base-test")
@Configuration
public class YannyWordConfig {
    @Bean
    public YannyWordPronounce getWordPronounce(){
        return new YannyWordPronounce();
    }
}
