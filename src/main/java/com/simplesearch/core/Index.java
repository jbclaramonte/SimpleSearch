package com.simplesearch.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Index {
  private final Map<String, TokenInfo> tokenInfoMap = new HashMap<>();

  public void addToken(String token, String contentId) {
    TokenInfo tokenInfo = tokenInfoMap.getOrDefault(token, new TokenInfo());
    tokenInfo.add(contentId);
    tokenInfoMap.putIfAbsent(token, tokenInfo);
  }

  public Optional<TokenInfo> get(String token) {
    return Optional.ofNullable(tokenInfoMap.get(token));
  }

  public int size() {
    return tokenInfoMap.size();
  }

  public static class TokenInfo {
    int frequency;
    List<String> contentIdList = new ArrayList<>();

    public void add(String contentId) {
      frequency++;
      if (!contentIdList.contains(contentId)) {
        contentIdList.add(contentId);
      }
    }
  }
}
