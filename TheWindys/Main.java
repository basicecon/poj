/*
Minimum Cost flow Problem
LANG: JAVA
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
	static int MAX = 2552;
	static int[][] distance = new int[MAX][MAX];
	static boolean[][] capacity = new boolean[MAX][MAX];
	static boolean[][] flow = new boolean[MAX][MAX];
	static boolean[] used = new boolean[MAX];
	static boolean[] visited = new boolean[MAX];
	static int[] spath = new int[MAX];
	static int work = 0;
	static int machine = 0;
	static Scanner scan = null;
	static int[][] map = new int[52][52];
	static int maxflow;
	static int[] father = new int[MAX];
	static int start = 0;
	static int end = 0;
	static int cost = 0;

	public static void main(String args[]) {
		run();
	}

	public static void run() {
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		int tc = scan.nextInt();
		while (tc > 0) {
			tc --;
			init();
			read();
			graph();
			int max = ford(start, end);
			System.out.printf("%.6f\n" ,result());
		}
	}

	public static void init() {
		for (boolean[] c : capacity)
			Arrays.fill(c, false);
		for (boolean[] f : flow)
			Arrays.fill(f, false);
		for (int[] d : distance)
			Arrays.fill(d, 100000000);
		for (int[] m : map)
			Arrays.fill(m, 0);
		cost = 0;
		maxflow = 0;
	}

	public static void read() {
		work = scan.nextInt();
		machine = scan.nextInt();
		end = work+work*machine+1;
		for (int i = 1; i <= work; i ++) {
			for (int j = 1; j <= machine; j ++) {
				map[i][j] = scan.nextInt();
			}
		}
	}

	public static void graph() {
		for (int i = 1; i <= work; i ++) {
			capacity[0][i] = true;
			distance[0][i] = 0;
			for (int j = work+1; j <= work+machine; j ++) {
				for (int k = 0; k < work; k ++) {
					capacity[i][j+k*machine] = true;
					distance[i][j+k*machine] = (work-k)*map[i][j-work];
					capacity[j+k*machine][end] = true;
					distance[j+k*machine][end] = 0;
				}
			}
		}
	}

	public static void dfs(int src, int des) {
		used[src] = true;
		for (int i = 1; i <= end; i ++) {
			if (!used[i] && (capacity[src][i] ? 1 : 0) > (flow[src][i] ? 1 : 0)) {
				used[i] = true;
				father[i] = src;
				dfs(i, des);
			}
		}
	}

	public static void dijkstra() {
		for (int i = 0; i <= end; i ++) {
			spath[i] = 100000000;
		}
		spath[0] = 0; 

		while (true) {
			int min = 100000000;
			int next = -1;
			for (int j = 0; j <= end; j ++) {
				if (!visited[j]) {
					if (spath[j] < min) {
						min = spath[j];
						next = j;
					}
				}
			}
			if (min == 100000000)
				break;
			if (next == end) {
				visited[next] = true;
				break;
			}
			else
				visited[next] = true;
			for (int k = 0; k <= end; k ++) {
				if (!visited[k] && min + distance[next][k] < spath[k] 
					&& (capacity[next][k] ? 1 : 0) > (flow[next][k] ? 1 : 0)) {
					spath[k] = min + distance[next][k];
					father[k] = next;
				}
			}
		}
	}

	public static int ford(int src, int des) {
		father[src] = -1;
		while (true) {
			Arrays.fill(visited, false);
			dijkstra();
			if (!visited[des])
				break;
			int minflow = 100000000;
			for (int i = des; father[i] >= src; i = father[i]) {
				minflow = (int)Math.min(minflow, 
					(capacity[father[i]][i] ? 1 : 0) - (flow[father[i]][i] ? 1 : 0));
			}
			for (int i = des; father[i] >= src; i = father[i]) {
				flow[father[i]][i] = true;
				flow[i][father[i]] = false;
			}
			maxflow += minflow;
		}
		return maxflow;
	}

	public static double result() {
		double r = 0;
		for (int i = 0; i <= end; i ++) {
			for (int j = 0; j <= end; j ++) {
				if (flow[i][j])
					cost += distance[i][j];
			}
		}
		r = (double)cost / (double)work;
		return r;
	}
}