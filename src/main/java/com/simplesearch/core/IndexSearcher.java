package com.simplesearch.core;

import com.simplesearch.core.Index.TokenInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class IndexSearcher {

  private final Index index;
  private final SimpleRegexTokenizer simpleRegexTokenizer;

  public IndexSearcher(Index index, SimpleRegexTokenizer simpleRegexTokenizer) {
    this.index = index;
    this.simpleRegexTokenizer = simpleRegexTokenizer;
  }

  public SearchResult search(String content) {
    List<String> searchedTokens = Arrays.asList(simpleRegexTokenizer.tokenize(content));
    SearchResult searchResult = new SearchResult(searchedTokens);
    for (var token: searchedTokens) {
      Optional<TokenInfo> tokenInfo = index.get(token);
      if (tokenInfo.isPresent()) {
        for (var contentId : tokenInfo.get().contentIdList) {
          searchResult.addFoundToken(contentId, token);
        }
      }
    }
    return  searchResult;
  }

  public static class SearchResult {
    private final List<String> searchedTokens;
    private final Map<String, List<String>> contentIdFoundTokensMap = new HashMap<>();

    public SearchResult(List<String> searchedTokens) {
      this.searchedTokens = searchedTokens;
    }

    public void addFoundToken(String contentId, String token) {
      List<String> tokens = contentIdFoundTokensMap.getOrDefault(contentId, new ArrayList<>());
      tokens.add(token);
      contentIdFoundTokensMap.putIfAbsent(contentId, tokens);
    }

    public List<String> getSearchedTokens() {
      return searchedTokens;
    }

    public Map<String, List<String>> getContentIdFoundTokensMap() {
      return contentIdFoundTokensMap;
    }

  }
}
