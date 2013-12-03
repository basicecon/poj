/*
记忆化搜索 dfs
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
  static int[] x = {1, 0, -1, 0};
  static int[] y = {0, 1, 0, -1};
  static int r;
  static int c;
  static int[][] height;
  static int[][] path;
  static int max;
  
  public static void main(String args[]) {
    Scanner scan = null;
    try {
      scan = new Scanner(System.in);
    } catch(Exception e) {}
    r = scan.nextInt();
    c = scan.nextInt();
    height = new int[r][c];
    path = new int[r][c];
    for (int i = 0; i < r; i ++) {
      for (int j = 0; j < c; j ++) {
        height[i][j] = scan.nextInt();
      }
    }
    max = 0;
    for (int i = 0; i < r; i ++) {
      for (int j = 0; j < c; j ++) {
        int tmp = dfs(i, j);
        if (tmp > max)
          max = tmp;
      } 
    }
    System.out.println(max+1);
  }
  
  public static boolean withinRange(int xx, int yy) {
    if (xx >= 0 && xx < r && yy >= 0 && yy < c) {
      return true;
    }
    return false;
  }
  
  public static int dfs(int xx, int yy) {
    if (path[xx][yy] > 0)
      return path[xx][yy];
    for (int i = 0; i < 4; i ++) {
      int new_x = xx + x[i];
      int new_y = yy + y[i];
      if (withinRange(new_x, new_y) && height[new_x][new_y] > height[xx][yy]) {
        int tmp = dfs(new_x, new_y) + 1;
        if (tmp > path[xx][yy]) {
          path[xx][yy] = tmp;
        }
      }
    }
    return path[xx][yy];
  }
}
