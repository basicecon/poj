/*
最长子段问题 动态规划
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
  static Scanner scan = null;
  static int n;
  static int[][] rect;
  
  public static void main(String args[]) {
    read();
    run();
  }
  
  public static void read() {
    try {
      scan = new Scanner(System.in);
    } catch(Exception e) {}
    n = scan.nextInt();
    rect = new int[n][n];
    for (int i = 0; i < n; i ++) {
      for (int j = 0; j < n; j ++) {
        rect[i][j] = scan.nextInt();
      }
    }
  }
  
  public static void run() {
    int max = -2000;
    int tmp = 0;
    
    for (int i = 0; i < n; i ++) { // for each row
      tmp = dp(rect[i], n);
      if (tmp > max)
        max = tmp;
      for (int j = i + 1; j < n; j ++) {
        for (int k = 0; k < n; k ++) {
          rect[i][k] += rect[j][k];
        }
        tmp = dp(rect[i], n);
        if (tmp > max)
          max = tmp;
      }
    }
    System.out.println(max);
  }
  
  public static int dp(int[] arr, int len) {
    int[] dp = new int[len];
    dp[0] = arr[0];
    int max = dp[0];
    
    for (int i = 1; i < len; i ++) {
      if (dp[i-1] > 0)
        dp[i] = dp[i-1] + arr[i];
      else
        dp[i] = arr[i];
      if (dp[i] > max)
        max = dp[i];
    }
    return max;
  }
}

