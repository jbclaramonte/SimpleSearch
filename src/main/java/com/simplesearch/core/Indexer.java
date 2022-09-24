package com.simplesearch.core;

import java.util.Arrays;

public class Indexer {

  private final Index index;
  private final SimpleRegexTokenizer tokenizer;

  public Indexer(Index index, SimpleRegexTokenizer tokenizer) {
    this.index = index;
    this.tokenizer = tokenizer;
  }

  public void index(String content, String contentId) {
    String[] tokens = tokenizer.tokenize(content);
    Arrays.stream(tokens).forEach(t -> index.addToken(t, contentId));
  }

}
