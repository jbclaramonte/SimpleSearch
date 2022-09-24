package com.simplesearch.client;

import com.simplesearch.core.IndexSearcher.SearchResult;
import java.util.List;
import java.util.stream.IntStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SearchResultPrinterTest {

  @Test
  public void should_print_the_result_in_socre_descending_order() {
    // Given
    SearchResult searchResult = new SearchResult(List.of("simple", "test"));
    searchResult.addFoundToken("file1", "simple");
    searchResult.addFoundToken("file2", "simple");
    searchResult.addFoundToken("file2", "test");
    SearchResultPrinter searchResultPrinter = new SearchResultPrinter(searchResult);

    // When
    String result = searchResultPrinter.toString();

    // Then
    String expected = """
        file2:100.0%
        file1:50.0%
        """;
    Assertions.assertThat(result).isEqualTo(expected);
  }

  @Test
  public void should_print_max_first_10_results() {
    // Given
    SearchResult searchResult = new SearchResult(List.of("simple", "test"));
    IntStream.range(1, 15).forEach(value -> searchResult.addFoundToken("file" + value, "text"));

    // When
    SearchResultPrinter searchResultPrinter = new SearchResultPrinter(searchResult);

    // Then
    Assertions.assertThat(searchResultPrinter.getFirstLines(10).size()).isEqualTo(10);
  }
}