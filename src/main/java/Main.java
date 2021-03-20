import com.google.common.base.Joiner;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        WordFinder wordFinder = new WordFinder();
        List<String> words = wordFinder.find("appa");
        System.out.println(Joiner.on("\n").join(words));
    }
}
