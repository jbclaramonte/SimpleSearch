package com.simplesearch.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import org.junit.jupiter.api.Test;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class FileIndexerTest {

  @Test
  public void should_index_file() {
    // Given
    Index index = new Index();
    FileIndexer fileIndexer = new FileIndexer(new Indexer(index, new SimpleRegexTokenizer()));
    URL resource = FileIndexerTest.class.getClassLoader().getResource("sampleTextFile.txt");
    String filePath = Objects.requireNonNull(resource).getFile();
    File fileToIndex = new File(filePath);

    // When
    fileIndexer.index(fileToIndex);

    // Then
    assertThat(index.size()).isEqualTo(9);
    assertThat(index.get("search").get().contentIdList).containsExactlyInAnyOrder("sampleTextFile.txt");
  }

}