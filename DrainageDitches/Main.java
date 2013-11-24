/*
Ford-Fulkerson Algorithm
LANG: JAVA
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
	static int MAX = 201;
	static int[][] capacity = new int[MAX][MAX];
	static int[][] flow = new int[MAX][MAX];
	static boolean[] used;
	static int[] father;
	static int edges;
	static int nodes;
	static int maxflow = 0;
	static Scanner scan = null;

	public static void main(String args[]) {
		run();
	}

	public static void run() {
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		while (scan.hasNext()) {
			init();
			read();
			System.out.println(FordFulkerson(1, nodes));
		}
	}

	public static void init() {
		for (int[] c : capacity)
			Arrays.fill(c, 0);
		for (int[] f : flow)
			Arrays.fill(f, 0);
		used = new boolean[MAX];
		father = new int[MAX];
		maxflow = 0;
	}

	public static void read() {
		edges = scan.nextInt();
		nodes = scan.nextInt();
		for (int i = 0; i < edges; i ++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			capacity[x][y] += scan.nextInt();
		}
	}

	public static void dfs(int src, int des) {
		used[src] = true;
		//father[src] = -1;
		for (int i = 1; i <= nodes; i ++) {
			if (!used[i] && capacity[src][i] > flow[src][i]) {
				used[i] = true;
				father[i] = src;
				dfs(i, des);
			}
		}
	}

	public static int FordFulkerson(int src, int des) {
		/*
		for (int[] f : flow)
			Arrays.fill(f, 0);*/
		while (true) {
			Arrays.fill(used, false);
			dfs(src, des);
			if (!used[des])
				break;
			int minflow = 99999;
			for (int i = des; father[i] >= src; i = father[i]) {
				minflow = (int)Math.min(minflow, capacity[father[i]][i] - flow[father[i]][i]);
			}
			for (int i = des; father[i] >= src; i = father[i]) {
				flow[father[i]][i] += minflow;
				flow[i][father[i]] -= minflow;
			}
			maxflow += minflow;
		}
		return maxflow;
	}


}