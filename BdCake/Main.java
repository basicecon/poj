/*
dfs + 三个减枝条件
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
  static int n;
  static int m;
  static int[] minV = new int[21];
  static int[] minS = new int[21];
  static int sumV;
  static int sumS;
  static int bestS;

  public static void main(String args[]) {
    Scanner scan = null;
    try {
      scan = new Scanner(System.in);
    } catch(Exception e) {}
    n = scan.nextInt();
    m = scan.nextInt();
    //minV = new int[m+1];
    //minS = new int[m+1];
    bestS = 99999;
    minV[0] = minS[0] = 0;
    
    updatePossibleMin();
    dfs(m, 0, 0, n+1, n+1);
    if (bestS == 99999)
      bestS = 0;
    System.out.println(bestS);
  }
  
  public static void updatePossibleMin() {
    for (int i = 1; i <= m; i ++) {
      minV[i] = minV[i-1] + i*i*i;
      minS[i] = minS[i-1] + 2*i*i;
    }
  }
  
  public static void dfs(int dep, int sumV, int sumS, int r, int h) {
    if (dep == 0) {
      if (sumV == n && sumS < bestS) {
        bestS = sumS;
      }
      return;
    }
    if (sumV + minV[dep] > n 
    || sumS + minS[dep] > bestS 
    || 2*(n-sumV)/r + sumS >= bestS)
      return;
    
    for (int i = r-1; i >= dep; i --) {
      if (dep == m) {
        sumS = i*i;
      }
      int maxh = (int)Math.min((n-sumV-minV[dep-1])/(i*i), h-1);
      for (int j = maxh; j >= dep; j --) {
        dfs(dep-1, sumV+j*i*i, sumS+2*i*j, i, j);
      }
    }
  }
}
