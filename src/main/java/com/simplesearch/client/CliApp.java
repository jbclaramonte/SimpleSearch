package com.simplesearch.client;

import com.simplesearch.core.FileIndexer;
import com.simplesearch.core.Index;
import com.simplesearch.core.IndexSearcher;
import com.simplesearch.core.IndexSearcher.SearchResult;
import com.simplesearch.core.Indexer;
import com.simplesearch.core.SimpleRegexTokenizer;
import java.io.File;
import java.util.Scanner;

public class CliApp {

  public static void main(String[] args) {
    if (args.length == 0) {
      throw new IllegalArgumentException("No directory given to index.");
    }
    final File indexableDirectory = new File(args[0]);
    Index index = new Index();
    SimpleRegexTokenizer tokenizer = new SimpleRegexTokenizer();
    FileIndexer fileIndexer = new FileIndexer(new Indexer(index, tokenizer));

    File[] files = indexableDirectory.listFiles();
    int indexedFilesCount = 0;
    for (int i=0; i < files.length; i++) {
      if (files[i].isFile()) {
        fileIndexer.index(files[i]);
        indexedFilesCount++;
      }
      System.out.printf("\r%s%%", 100*i/files.length);
    }
    System.out.printf("\r");

    System.out.println(indexedFilesCount + " files read in directory " + indexableDirectory.getPath());

    IndexSearcher indexSearcher = new IndexSearcher(index, tokenizer);

    try (Scanner keyboard = new Scanner(System.in)) {
      while (true) {
        System.out.print("search> ");
        final String line = keyboard.nextLine();
        if (line.equalsIgnoreCase(":quit")) {
          System.exit(0);
        }
        SearchResult searchResult = indexSearcher.search(line);
        SearchResultPrinter searchResultPrinter = new SearchResultPrinter(searchResult);
        System.out.println(searchResultPrinter);
      }
    }
  }

}
