package com.search.websearchengine.components;

import java.io.*;
import java.util.HashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
  private static final int MAX_DEPTH = 3;
  private static final int max_urls = 20;
  private HashSet<String> links;

  public Crawler() {
    links = new HashSet<>();
  }

  public void getPageLinks(String URL, int depth) {
    if ((!links.contains(URL) && (depth < MAX_DEPTH) && links.size() < max_urls)) {
      // System.out.println(">> Depth: " + depth + " [" + URL + "]");
      appendStrToFile("src\\main\\resources\\urlLinks\\urllist.txt", URL + "\n");
      try {
        links.add(URL);

        Connection con = Jsoup.connect(URL)
            .userAgent(
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36")
            .timeout(30000);

        Connection.Response response = con.execute();
        if (response.statusCode() == 200) {
          Document doc = con.get();
          Elements linksOnPage = doc.select("a[href]");

          depth++;
          for (Element page : linksOnPage) {
            getPageLinks(page.attr("abs:href"), depth);
          }
        } else {
          return;
        }

      } catch (IOException e) {
        System.err.println("For '" + URL + "': " + e.getMessage());
      }
    }
  }

  public static void appendStrToFile(String fileName, String str) {
    try {
      // Open given file in append mode.
      BufferedWriter out = new BufferedWriter(
          new FileWriter(fileName, true));
      out.write(str);
      out.close();
    } catch (IOException e) {
      System.out.println("exception occoured" + e);
    }
  }

  public static void main(String[] args) {
    String LocFile = "src\\main\\resources\\urlLinks\\urllist.txt";
    File fil = new File(LocFile);
    try {
      BufferedWriter out = new BufferedWriter(new FileWriter(fil));
      out.close();
    } catch (IOException e) {
      System.out.println("Exception Occurred" + e);
    }

    new Crawler().getPageLinks("https://www.w3.org/", 0);
  }

}
