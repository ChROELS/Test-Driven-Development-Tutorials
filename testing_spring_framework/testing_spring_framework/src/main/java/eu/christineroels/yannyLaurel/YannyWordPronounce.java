package eu.christineroels.yannyLaurel;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("Yanny")
@Primary
@Component
public class YannyWordPronounce implements WordPronounce {
    @Override
    public String getWord() {
        return "Yanny";
    }
}
