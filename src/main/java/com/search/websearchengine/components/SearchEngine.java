package com.search.websearchengine.components;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

import com.search.websearchengine.utilities.TrieST;
import com.search.websearchengine.beans.*;

public class SearchEngine {

  public static PriorityQueue<WebPageRanking> pQueue = new PriorityQueue<WebPageRanking>();

  public List<SearchResult> keywordSearch(String searchString) {
    HashMap<Integer, SearchResult> resultMap = new HashMap<Integer, SearchResult>();

    var map = LoadHashMap();

    ArrayList<String> list = new ArrayList<String>();

    StringTokenizer st = new StringTokenizer(searchString);
    while (st.hasMoreTokens()) {
      list.add(st.nextToken());
    }

    RankWebPages(list, map);

    if (pQueue.size() != 0) {
      int i = 0;
      for (var links : pQueue) {
        resultMap.put(i, new SearchResult(i, links.getKey()));
        i++;
      }
    }

    List<SearchResult> resultArr = new ArrayList<SearchResult>(resultMap.values());
    pQueue.clear();
    resultMap.clear();
    return resultArr;
  }

  public static Map<String, TrieST<Integer>> LoadHashMap() {

    Map<String, TrieST<Integer>> map = new HashMap<String, TrieST<Integer>>();
    BufferedReader br = null;

    try {
      File file = new File("src\\main\\resources\\keywords\\keywordMap.dat");
      br = new BufferedReader(new FileReader(file));
      String line = null;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split("::");
        String link = parts[0].trim();
        String[] words = parts[1].trim().split("[{},]");

        TrieST<Integer> st = new TrieST<Integer>();

        for (int i = 0; i < words.length; i++) {
          st.put(words[i], i);
        }
        map.put(link, st);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return map;
  }

  public static void RankWebPages(ArrayList<String> str, Map<String, TrieST<Integer>> map) {

    int freq_score = 0;
    for (var entry : map.entrySet()) {
      for (var word : str) {

        if (entry.getValue().contains(word))
          freq_score++;

      }
      if (freq_score == str.size()) {
        WebPageRanking rank = new WebPageRanking(entry.getKey(), freq_score);
        pQueue.add(rank);
      } else if (freq_score > 0) {
        WebPageRanking rank = new WebPageRanking(entry.getKey(), freq_score);
        pQueue.add(rank);
      }
      freq_score = 0;
    }
  }

  public List<String> SimilarWords(String str) {
    ArrayList<String> dictionary = new ArrayList<String>();
    try (BufferedReader br = new BufferedReader(new FileReader("src\\main\\resources\\keywords\\dictionary.dat"))) {
      while (br.ready()) {
        dictionary.add(br.readLine());
      }
      br.close();
    } catch (IOException e) {
      System.out.println("Exception Occurred" + e);
    }
    List<Integer> percentage_match = new ArrayList<Integer>();
    List<String> out = new ArrayList<String>();
    for (int i = 0; i < dictionary.size(); i++) {
      double p_similar = WordSuggestor.getDistanceDiff(str, dictionary.get(i));
      int pp_similar = (int) (p_similar * 100);
      percentage_match.add((int) pp_similar);
    }
    // 1st match
    int max = Collections.max(percentage_match);
    int index = percentage_match.indexOf(max);
    out.add(dictionary.get(index));

    percentage_match.set(index, 0);
    // 2nd match
    max = Collections.max(percentage_match);
    index = percentage_match.indexOf(max);
    out.add(dictionary.get(index));

    percentage_match.set(index, 0);
    // 3rd match
    max = Collections.max(percentage_match);
    index = percentage_match.indexOf(max);
    out.add(dictionary.get(index));

    return out;

  }

}
