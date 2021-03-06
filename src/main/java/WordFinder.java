import org.apache.commons.collections4.iterators.PermutationIterator;
import org.apache.commons.math3.util.Combinations;
import com.google.common.base.Joiner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import utils.DictionaryClient;
import utils.DictionaryClientException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to find all the possible English words
 * that can be generated by the given word's letters
 */
public class WordFinder {
    private static final Logger logger = LogManager.getLogger(WordFinder.class.getName());
    private DictionaryClient dictionaryClient;

    /**
     * Constructor for WordFinder class
     */
    public WordFinder() {
        this.dictionaryClient = new DictionaryClient();

    }

    /**
     * Returns a list of English words that generated by the given word's letters
     * @param word Seed word to use to generate English words
     * @return List of strings that are generated from the given word and are valid English words
     */
    public List<String> find(String word) {
        logger.info(String.format("WordFinder api is called with; '%s'", word));
        HashSet<String> englishWords = new HashSet<>(); // Results set

        List<Character> wordCharList = getCharListFromWord(word);
        logger.debug(String.format("Word '%s' converted to char list; '%s'.", word, wordCharList.toString()));

        // Check all the permutations of all the combinations of the given word
        for (int combLenghth = 1; combLenghth <= wordCharList.size(); combLenghth++) { // foreach word length
            logger.debug(String.format("Looking for combinations of size %s", combLenghth));

            Combinations combinations = new Combinations(wordCharList.size(), combLenghth);
            for (int[] combination : combinations) { // foreach combination of indices
                logger.debug(String.format("Combinations for size %s: [%s]", combLenghth, Arrays.toString(combination)));

                List<Character> subset = maskWordWithIndices(wordCharList, combination);
                logger.debug(String.format("Combined indices: [%s] are masked to original char list: [%s]", Arrays.toString(combination), subset.toString()));

                PermutationIterator<Character> permutationIterator = new PermutationIterator<>(subset);
                while (permutationIterator.hasNext()) { // foreach permutation
                    List<Character> permutation = permutationIterator.next();
                    String permutationString = Joiner.on("").join(permutation);
                    try {
                        if (dictionaryClient.isEnglishWord(permutationString)) { // if english
                            englishWords.add(permutationString); // add
                        }
                    } catch (DictionaryClientException e) {
                        String errorMessage = String.format("Error while determining if word `%s` is a valid English word: %s", permutationString, e);
                        logger.error(errorMessage);
                        throw new RuntimeException(errorMessage);
                    }
                }
            }
        }

        return new ArrayList<>(englishWords);
    }

    private List<Character> getCharListFromWord(String word) {
        return word.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
    }

    private List<Character> maskWordWithIndices(List<Character> wordCharList, int[] indices) {
        return  Arrays.stream(indices) // Generate the subset from indices
                .mapToObj(wordCharList::get)
                .collect(Collectors.toList());
    }

    /**
     * Sets the internal Dictionary client. This class is intended for helping with mocking in unit tests
     * @param dictionaryClient HTTP client to access to Dictionary API
     */
    public void setDictionaryClient(DictionaryClient dictionaryClient) {
        this.dictionaryClient = dictionaryClient;
    }
}
