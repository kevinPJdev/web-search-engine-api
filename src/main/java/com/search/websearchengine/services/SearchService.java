package com.search.websearchengine.services;

import java.util.List;

import com.search.*;
import com.search.websearchengine.beans.SearchResult;
import com.search.websearchengine.components.SearchEngine;

public class SearchService {

  SearchEngine search = new SearchEngine();

  public List<SearchResult> getSearchResults(String searchString) {
    return search.keywordSearch(searchString);
  }

  public List<String> getSuggestedWords(String suggesString) {
    return search.SimilarWords(suggesString);
  }
}
