package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * HTTP Client to access to Dictionary API
 * Mocked as asked in the assignment
 */
public class DictionaryClient {
    private static final Logger logger = LogManager.getLogger(DictionaryClient.class.getName());
    private final int THRESHOLD = 3;

    /**
     * Determines if the given string is a valid English word
     * @param word input string to check against English words
     * @return if valid English word return true, otherwise false
     * @throws DictionaryClientException when an error happens accessing to Dictionary API
     */
    public boolean isEnglishWord(String word) throws DictionaryClientException {
        try {
            logger.debug(String.format("Accessing Dictionary API to check if word " +
                    "'%s' is a valid English word...", word));
            return word.length() > THRESHOLD;
        } catch (Exception e) {
            String errorMessage = String.format("An error happened while accessing Dictionary API: %s", e.getMessage());
            logger.error(errorMessage);
            throw new DictionaryClientException(errorMessage);
        }
    }
}
