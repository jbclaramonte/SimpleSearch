package com.simplesearch.core;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TokenizerTest {
    @Test
    public void should_get_each_word_as_a_token() {
        // Given
        SimpleRegexTokenizer simpleRegexTokenizer = new SimpleRegexTokenizer();
        var content = """
            This is a test for simple
            Regex-Tokenizer search
            """;

        // When
        String[] tokens = simpleRegexTokenizer.tokenize(content);

        // Then
        String[] expectedTokens = new String[]{"this", "is", "a", "test", "for", "simple", "regex", "tokenizer", "search"};
        Assertions.assertThat(tokens).containsExactlyInAnyOrder(expectedTokens);

    }
}