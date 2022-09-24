package com.simplesearch.core;

public class SimpleRegexTokenizer {
  public String[] tokenize(String content) {
    return content.toLowerCase().split("\\W");
  }
}
