package eu.christineroels.yannyLaurel;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class LaurelWordPronounce implements WordPronounce {
    @Override
    public String getWord() {
        return "Laurel";
    }
}
