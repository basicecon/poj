/*
  Hungarian
  LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
  static int MAX = 510;
  static int cow;
  static int stall;
  static int[] match = new int[MAX];
  static int[][] adj_list = new int[MAX][MAX];
  static boolean[] used = new boolean[MAX];
  static ArrayList<Integer[]> track = new ArrayList<Integer[]>();
  static int matching = 0;
  static Scanner scan = null;
    
  public static void main(String args[]) {
    run();
  }
  
  public static void init() {
    for (int[] a : adj_list) 
      Arrays.fill(a, 0);
    Arrays.fill(used, false);
    Arrays.fill(match, 0);
    matching = 0;  
  }
  
  public static void run() {
    try {
      scan = new Scanner(System.in);
    } catch(Exception e) {}
    while (scan.hasNext()) {
      init();
      read();
      int max = hungarian();
      System.out.println(max);
    }        
  }
  
  public static void read() {
    cow = scan.nextInt();
    stall = scan.nextInt();
    for (int i = 1; i <= cow; i ++) {
      int n = scan.nextInt();
      for (int j = 1; j <= n; j ++) {
        int st = scan.nextInt();
        adj_list[i][ ++adj_list[i][0] ] = st + cow;     
      }
    }
  }
  
  public static boolean bfs(int src) {
    int index = 0;
    track.clear();
    Integer[] start = new Integer[2];
    start[0] = src;
    start[1] = -1;
    track.add(start);
    used[src] = true;
    
    while (index < track.size()) {
      int node = track.get(index)[0];
      if (node <= cow) {
        for (int i = 1; i <= adj_list[node][0]; i ++) {
          int y = adj_list[node][i];
          if (used[y])
            continue;
          Integer[] tmp = new Integer[2];
          tmp[0] = y;
          tmp[1] = index;
          track.add(tmp);
          used[y] = true;
          if (match[y] == 0) {
            index = MAX;
            break;
          }
        }
      }
      else {
        int x = match[node];
        if (x == 0)
          break;
        else {
          if (!used[x]) {
            Integer[] tmp = new Integer[2];
            tmp[0] = x;
            tmp[1] = index;
            track.add(tmp);
            used[x] = true;
          }        
        }
      }
      index ++;
    }
    
    Integer[] last_pair = track.get(track.size()-1);
    int last = last_pair[0];
    
    if (match[last] != 0 || last <= cow)
      return false;
    
    ArrayList<Integer> path = new ArrayList<Integer>();
    path.add(last);
    int father = last_pair[1];
    while (father != -1) {
      last_pair = track.get(father);
      path.add(last_pair[0]);
      father = last_pair[1];
    }
    
    for (int i = 0; i < path.size(); i += 2) {
      match[path.get(i)] = path.get(i+1);
    }
    
    return true;
  }
  
  public static boolean dfs(int src) {
    for (int i = 1; i <= adj_list[src][0]; i ++) {
      int y = adj_list[src][i];
      if (!used[y]) {
        used[y] = true;
        if (match[y] == 0 || dfs(match[y])) {
          match[y] = src;
          return true;
        }
      }
    }
    return false;
  }
  
  public static int hungarian() {
    for (int i = 1; i <= cow; i ++) {
      Arrays.fill(used, false);
      if (dfs(i))
        matching ++;
      //if (bfs(i))
      //  matching ++; 
    }
    return matching;
  }
}
