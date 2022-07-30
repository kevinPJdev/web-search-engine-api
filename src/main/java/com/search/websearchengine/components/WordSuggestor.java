package com.search.websearchengine.components;

public class WordSuggestor {

  // Levenshtein distance algorithm given 2 strings finds their difference
  public static int editDistance(String word1, String word2) {
    int len1 = word1.length();
    int len2 = word2.length();

    // len1+1, len2+1, because finally return dp[len1][len2]
    int[][] dp = new int[len1 + 1][len2 + 1];

    for (int i = 0; i <= len1; i++) {
      dp[i][0] = i;
    }

    for (int j = 0; j <= len2; j++) {
      dp[0][j] = j;
    }

    // iterate though, and check last char
    for (int i = 0; i < len1; i++) {
      char c1 = word1.charAt(i);
      for (int j = 0; j < len2; j++) {
        char c2 = word2.charAt(j);

        // if last two chars equal
        if (c1 == c2) {
          // update dp value for +1 length
          dp[i + 1][j + 1] = dp[i][j];
        } else {
          int replace = dp[i][j] + 1;
          int insert = dp[i][j + 1] + 1;
          int delete = dp[i + 1][j] + 1;

          int min = replace > insert ? insert : replace;
          min = delete > min ? min : delete;
          dp[i + 1][j + 1] = min;
        }
      }
    }

    return dp[len1][len2];
  }

  // This method is to assign a longer string as word1 so that always a positive
  // percentage for difference can be calculated
  public static double getDistanceDiff(String s, String t) {
    String longer = s, shorter = t;
    if (s.length() < t.length()) { // longer should always have greater length
      longer = t;
      shorter = s;
    }
    int longerLength = longer.length();
    if (longerLength == 0) {
      return 1.0;
      /* both strings are zero length */ }

    return (longerLength - editDistance(longer, shorter)) / (double) longerLength;
  }
}
