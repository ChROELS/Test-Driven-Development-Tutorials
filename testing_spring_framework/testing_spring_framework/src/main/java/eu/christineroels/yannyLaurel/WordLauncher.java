package eu.christineroels.yannyLaurel;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class WordLauncher {

    private final WordPronounce wordPronounce;

    public WordLauncher(WordPronounce wordPronounce) {
        this.wordPronounce = wordPronounce;
    }

    public String sayWord(){
        return wordPronounce.getWord();
    }
}
