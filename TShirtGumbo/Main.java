/*
  maximum flow 
  dfs && bfs
*/
import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
  static int MAX = 120;
  static int contestant;
  static int[][] capacity = new int[MAX][MAX];
  static int[][] flow = new int[MAX][MAX];
  static boolean[] used = new boolean[MAX];
  static int[] father = new int[MAX];
  static int end = 0;
  static int maxflow = 0;
  static Scanner scan = null;
  static String message = null;
  
  public static void main(String args[]) {
    run();
  }
  
  public static void run() {
    try {
      scan = new Scanner(System.in);
    } catch(Exception e) {}
    while (true) {
      init();
      message = scan.nextLine();
      if (message.equals("ENDOFINPUT"))
        break;
      read();
      output();
    }
  }
  
  public static void init() {
    for (int[] c : capacity)
      Arrays.fill(c, 0);
    for (int[] f : flow)
      Arrays.fill(f, 0);
    Arrays.fill(used, false);
    Arrays.fill(father, -1);
    maxflow = 0;
  }
  
  public static void read() {
    String[] m = message.split(" ");
    contestant = Integer.parseInt(m[1]);
    end = contestant + 6;
    
    for (int i = 1; i <= contestant; i ++) {
      capacity[0][i] = 1;
      String s = scan.next();
      int left = contestant + convert(s.charAt(0));
      int right = contestant + convert(s.charAt(1));
      for (int j = left; j <= right; j ++) {
        capacity[i][j] = 1;
      }
    }
    for (int i = 1; i <= 5; i ++) {
      capacity[contestant+i][end] = scan.nextInt();
    }
    
    String blank = scan.nextLine();
    String ee = scan.nextLine();
  }
  
  public static int convert(char size) {
    int num = 0;
    switch (size) {
      case 'S': num = 1; break;
      case 'M': num = 2; break;
      case 'L': num = 3; break;
      case 'X': num = 4; break;
      case 'T': num = 5; break;
      default: num = -1; break; 
    }  
    return num;
  }
  
  public static void output() {
    int max = ff(0, end);
    if (max == contestant)
      System.out.println("T-shirts rock!");
    else
      System.out.println("I'd rather not wear a shirt anyway...");
  }
  
  public static boolean bfs(int src, int des) {
    used[src] = true;
    ArrayList<Integer> path = new ArrayList<Integer>();
    path.add(src);
    int index = 0;
    
    while (index < path.size()) {
      int node = path.get(index);
      for (int i = 0; i <= end; i ++) {
        if (!used[i] && capacity[src][i] > flow[src][i]) {
          used[i] = true;
          path.add(i);
          father[i] = node;
        }
      }
    }
    return used[des];
  }
  
  public static void dfs(int src, int des) {
    used[src] = true;
    for (int i = 0; i <= end; i ++) {
      if (!used[i] && capacity[src][i] > flow[src][i]) {
        used[i] = true;
        father[i] = src;
        dfs(i, des);
      }
    }
  }
  
  public static int ff(int src, int des) {
    while (true) {
      Arrays.fill(used, false);
      dfs(src, des);
      if (!used[des])
        break;
      //if (!bfs(src, des))
      //  break;
      int min = 99999;
      for (int i = des; father[i] >= src; i = father[i])
        min = (int)Math.min(min, capacity[father[i]][i] - flow[father[i]][i]);
      for (int i = des; father[i] >= src; i = father[i]) {
        flow[father[i]][i] += min;
        flow[i][father[i]] -= min;
      }
      maxflow += min;
    }
    return maxflow;
  }
}
