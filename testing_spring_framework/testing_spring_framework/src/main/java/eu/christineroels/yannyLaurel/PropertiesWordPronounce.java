package eu.christineroels.yannyLaurel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"externalized","laurel-properties"})
@Primary
@Component
public class PropertiesWordPronounce implements WordPronounce{

    private String word;

    @Value("${say.word}")
    public void setWord(String word){
        this.word=word;
    }
    @Override
    public String getWord() {
        return word;
    }
}
