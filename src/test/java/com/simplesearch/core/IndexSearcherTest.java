package com.simplesearch.core;

import com.simplesearch.core.IndexSearcher.SearchResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class IndexSearcherTest {
  @Test
  public void should_return_a_searchresult() {
    // Given
    Index index = new Index();
    SimpleRegexTokenizer simpleRegexTokenizer = new SimpleRegexTokenizer();
    Indexer indexer = new Indexer(index, simpleRegexTokenizer);
    IndexSearcher indexSearcher = new IndexSearcher(index, simpleRegexTokenizer);

    var contentFile1 = """
            This is a test, for simple
            Regex-Tokenizer search.
            """;

    var contentFile2 = """
            We can see search systems
            in almost every second of software.
            """;

    String file1 = "file1";
    indexer.index(contentFile1, file1);
    String file2 = "file2";
    indexer.index(contentFile2, file2);

    // When
    SearchResult searchResult = indexSearcher.search("this simple search");

    // Then
    Assertions.assertThat(searchResult.getContentIdFoundTokensMap().get(file1)).containsExactlyInAnyOrder("this", "simple", "search");
    Assertions.assertThat(searchResult.getContentIdFoundTokensMap().get(file2)).containsExactlyInAnyOrder("search");

  }
}