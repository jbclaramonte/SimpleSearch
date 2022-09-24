package com.simplesearch.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileIndexer {

  private final Indexer indexer;

  public FileIndexer(Indexer indexer) {
    this.indexer = indexer;
  }

  public void index(File file) {
    if (file.isDirectory()) {
      return;
    }
    try(BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()))) {
      String line = reader.readLine();
      while (line != null) {
        // read next line
        indexer.index(line, file.getName());
        line = reader.readLine();
      }
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}
