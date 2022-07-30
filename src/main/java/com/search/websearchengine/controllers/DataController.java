package com.search.websearchengine.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.search.websearchengine.beans.SearchResult;
import com.search.websearchengine.components.SearchEngine;
import com.search.websearchengine.services.SearchService;

@RestController
@RequestMapping("/search")
public class DataController {

  SearchService service = new SearchService();
  SearchEngine engine = new SearchEngine();

  // returns array of json objects
  @GetMapping
  public List<SearchResult> getWebsitesSearchParams(@RequestParam(value = "searchString") String searchString) {
    return service.getSearchResults(searchString);
  }

  // returns array of 3 similar words
  @GetMapping(path = "/wordsuggest")
  public List<String> getWebsite(@RequestParam(value = "suggestString") String suggestString) {
    return service.getSuggestedWords(suggestString);
  }
}
