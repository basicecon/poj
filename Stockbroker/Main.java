/*
Floyd 最短路 对于每一个源点求出到达其他人的最长时间 对于每一个最长时间求出min
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
  static Scanner scan = null;
  static int n_sb;
  static int[][] map = new int[102][102];

  public static void main(String args[]) {
    run();
  }
  
  public static void floyd() {
    for (int k = 1; k <= n_sb; k ++) {
      for (int i = 1; i <= n_sb; i ++) {
        for (int j = 1; j <= n_sb; j ++) {
          if (map[i][k] + map[k][j] < map[i][j]) {
            map[i][j] = map[i][k] + map[k][j];
          }
        }
      }
    }
  }
  
  public static void run() {
    try {
      scan = new Scanner(System.in);    
    } catch(Exception e) {}
    n_sb = scan.nextInt();
    while (n_sb != 0) {
      init();
      read();
      floyd();
      int max;
      int min = 99999;
      int source = -1;
      for (int i = 1; i <= n_sb; i ++) {
        max = 0;
        for (int j = 1; j <= n_sb; j ++) {
          if (i == j)
            map[i][j] = 0;
          if (map[i][j] > max) {
            max = map[i][j];
          }
        }
        if (max < min) {
          min = max;
          source = i;
        }
      }
      if (min > 99999)
        System.out.println("disjoint");
      else
        System.out.println(source + " " + min);
      n_sb = scan.nextInt();
    }
  }
  
  public static void read() {
    for (int i = 1; i <= n_sb; i ++) {
      int num = scan.nextInt();
      for (int j = 1; j <= num; j ++) {
        int tmp = scan.nextInt();
        map[i][tmp] = scan.nextInt();
      }
    }
  }
  
  public static void init() {
    for (int[] m : map)
      Arrays.fill(m, 99999);
  }
}
