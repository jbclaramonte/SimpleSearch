package com.simplesearch.client;

import com.simplesearch.core.IndexSearcher.SearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchResultPrinter {

  private final List<ResultLine> resultLines = new ArrayList<>();

  public SearchResultPrinter(SearchResult searchResult) {
    for (var entry : searchResult.getContentIdFoundTokensMap().entrySet()) {
      int score = computeScore(entry.getValue().size(), searchResult.getSearchedTokens().size());
      resultLines.add(new ResultLine(entry.getKey(), score));
    }
  }

  private int computeScore(int tokenFoundSize, int searchedTokenSize) {
    return (int)Math.ceil(100*(tokenFoundSize / (float) searchedTokenSize));
  }

  @Override
  public String toString() {
    if (resultLines.size() == 0) {
      return "no matches found";
    }
    StringBuilder sb = new StringBuilder();
    for (var line : getFirstLines(10)) {
      sb.append(line);
      sb.append("\n");
    }
    return sb.toString();
  }

  public List<ResultLine> getFirstLines(int max) {
    if (resultLines.size() <= max) {
      return resultLines;
    }
    List<ResultLine> firstLines = new ArrayList<>();
    resultLines.sort(Collections.reverseOrder());
    int i = 0;
    var it = resultLines.iterator();
    while (it.hasNext() && i++ < max) {
      firstLines.add(it.next());
    }
    return firstLines;
  }

  public record ResultLine(String filename, int score) implements Comparable<ResultLine> {

    @Override
    public int compareTo(ResultLine o) {
      if (this.score - o.score < 0 ) {
        return -1;
      } else if (this.score - o.score > 0) {
        return  1;
      }
      return 0;
    }

    @Override
    public String toString() {
      return filename + ":" + score + "%";
    }
  }
}
