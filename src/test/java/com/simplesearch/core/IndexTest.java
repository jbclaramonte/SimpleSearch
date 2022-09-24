package com.simplesearch.core;

import static org.assertj.core.api.Assertions.assertThat;

import com.simplesearch.core.Index.TokenInfo;
import org.junit.jupiter.api.Test;

@SuppressWarnings("OptionalGetWithoutIsPresent")
class IndexTest {

  @Test
  public void should_find_indexed_token() {
    // Given
    Index index = new Index();
    index.addToken("this", "file1");
    index.addToken("is", "file1");
    index.addToken("a", "file1");
    index.addToken("test", "file1");

    // When
    TokenInfo tokenInfo = index.get("this").get();

    // then
    assertThat(tokenInfo).isNotNull();

    assertThat(tokenInfo.frequency).isEqualTo(1);
    assertThat(tokenInfo.contentIdList).containsExactlyInAnyOrder("file1");
  }

  @Test
  public void should_index_token_from_differents_contentId() {
    // Given
    Index index = new Index();
    index.addToken("this", "file1");
    index.addToken("this", "file2");

    // When
    TokenInfo tokenInfo = index.get("this").get();

    // then
    assertThat(tokenInfo).isNotNull();

    assertThat(tokenInfo.frequency).isEqualTo(2);
    assertThat(tokenInfo.contentIdList).containsExactlyInAnyOrder("file1", "file2");
  }
}