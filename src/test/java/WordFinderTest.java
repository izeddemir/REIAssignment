import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.Invocation;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import utils.DictionaryClient;
import utils.DictionaryClientException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WordFinderTest {
    private WordFinder wordFinder;
    private DictionaryClient mockedDictionaryClient;

    @BeforeEach
    public void setUp() {
        // Initialize WorkFinder and Mocked DictionaryClient
        wordFinder = new WordFinder();
        mockedDictionaryClient = mock(DictionaryClient.class);

        // Inject mocked client to WordFinder instance
        wordFinder.setDictionaryClient(mockedDictionaryClient);
    }

    @Test
    public void shouldSuccessfullyReturnWordsValidatedFromDictionaryClient() throws DictionaryClientException {
        // mock
        when(mockedDictionaryClient.isEnglishWord(anyString())).thenAnswer(
                new Answer() {
                    @Override
                    public Object answer(InvocationOnMock invocationOnMock) {
                        Object[] args = invocationOnMock.getArguments();
                        HashSet<String> englishWords = new HashSet<>();
                        englishWords.add("apa");
                        englishWords.add("pap");
                        return args[0] instanceof String && englishWords.contains(args[0]);
                    }
                }
        );

        // given
        List<String> expected = new ArrayList<>();
        expected.add("apa");
        expected.add("pap");
        String input = "appa";

        // when
        List<String> result = wordFinder.find(input);

        // then
        assert expected.equals(result);
    }

    @Test
    public void shouldSuccessfullyReturnEmptyListWhenNoEnglishWordArePresent() throws DictionaryClientException {
        // mock
        when(mockedDictionaryClient.isEnglishWord(anyString())).thenReturn(false);

        // given
        List<String> expected = new ArrayList<>();
        String input = "test";

        // when
        List<String> result = wordFinder.find(input);

        // then
        assert result.equals(expected);
    }

    @Test
    public void shouldThrowRuntimeExceptionWhenDictionaryClientIsFailed() throws DictionaryClientException {
        // mock
        when(mockedDictionaryClient.isEnglishWord(anyString())).thenThrow(DictionaryClientException.class);

        // given
        String input = "test-input";

        // assert exception
        Assertions.assertThrows(RuntimeException.class, () -> { wordFinder.find(input); });
    }
}
