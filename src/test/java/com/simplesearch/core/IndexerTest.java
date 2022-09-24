package com.simplesearch.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class IndexerTest {
  @Test
  public void should_index_a_text_content() {
    // Given
    var content = """
            This is a test for simple
            Regex-Tokenizer search
            """;
    Index index = new Index();
    Indexer indexer = new Indexer(index, new SimpleRegexTokenizer());

    // When
    indexer.index(content, "file1");

    // Then
    assertThat(index.size()).isEqualTo(9);
    assertThat(index.get("simple").get().contentIdList).containsExactlyInAnyOrder("file1");
    assertThat(index.get("simple").get().frequency).isEqualTo(1);

  }
}