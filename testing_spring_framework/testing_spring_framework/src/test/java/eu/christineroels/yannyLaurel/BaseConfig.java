package eu.christineroels.yannyLaurel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("base-test")
@Configuration
public class BaseConfig {

    @Bean
    public WordLauncher getWordLauncher(WordPronounce wordPronounce){
        return new WordLauncher(wordPronounce);
    }
}
