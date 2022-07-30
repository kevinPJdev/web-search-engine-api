package com.search.websearchengine.components;

import java.io.*;
import org.jsoup.Jsoup;

import com.search.websearchengine.utilities.In;

public class GenTextFilefrmUrl {
  public static void main(String[] args) {
    GenTextFilefrmUrl fr = new GenTextFilefrmUrl();
    In in = new In("src\\main\\resources\\urlLinks\\urllist.txt");
    int i = 1;
    while (!in.isEmpty()) {

      String line = in.readLine();
      try {
        fr.HtmlConversion(line, i);
      } catch (IOException e) {
        System.out.println("Exception Occurred" + e);
        continue;
      }
      i++;
    }
  }

  public void HtmlConversion(String link, int i) throws IOException {
    org.jsoup.nodes.Document doc1 = Jsoup.connect(link).get();
    String Txt = doc1.text();
    PrintWriter out = new PrintWriter("src\\main\\resources\\data\\" + i + ".txt");
    out.println(Txt);
    out.close();

  }
}
