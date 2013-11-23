/*
Ford-Fulkerson Algorithm
LANG: JAVA
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
	static int MAX = 400;
	static int n;
	static int movie;
	static int[] week;
	static int[][] capacity = new int[MAX][MAX];
	static int[][] flow = new int[MAX][MAX];
	static boolean[] used;
	static int[] father;
	static int maxFlow;
	static Scanner scan = null;
	static int start = 0;
	static int end = 0;

	public static void main(String args[]) {
		run();
	}

	public static void init() {
		maxFlow = 0;
		week = new int[51];
		for (int[] i : capacity)
			Arrays.fill(i, 0);
		for (int[] i : flow)
			Arrays.fill(i, 0);
		used = new boolean[MAX];
		father = new int[MAX];
	}

	public static void read() {
		movie = scan.nextInt();
		for (int i = 1; i <= movie; i ++) {
			for (int j = 1; j <= 7; j ++) {
				capacity[i][movie+j] = scan.nextInt();
			}
			capacity[0][i] = scan.nextInt();
			week[i] = scan.nextInt();
			int w = week[i];
			while (w > 0) {
				for (int j = 1; j <= 7; j ++)
					capacity[i][movie+(w-1)*7+j] = capacity[i][movie+j];
				w --;
			}
		}
		int max_week = -1;
		for (int i = 1; i <= 50; i ++) {
			max_week = (int)Math.max(max_week, week[i]);
		}
		end = movie+7*max_week+1;
		for (int i = movie+1; i <= movie+7*max_week; i ++) {
			capacity[i][end] = 1;
		}
	}

	public static void run() {
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		n = scan.nextInt();
		while (n > 0) {
			init();
			read();
			output();
			n --;
		}
	}

	public static void dfs(int src, int des) {
		used[src] = true;
		for (int i = 1; i <= end; i ++) {
			if (!used[i] && capacity[src][i] > flow[src][i]) {
				used[i] = true;
				father[i] = src;
				dfs(i, des);
			}
		}
	}

	public static int FordFulkerson(int src, int des) {
		for (int[] f : flow)
			Arrays.fill(f, 0);
		while (true) {
			Arrays.fill(used, false);
			father[src] = -1;
			dfs(src, des);
			if (!used[des])
				break;
			int minFlow = 99999;
			for (int i = des; father[i] >= src; i = father[i]) {
				minFlow = (int)Math.min(minFlow, capacity[father[i]][i] - flow[father[i]][i]);
			}
			for (int i = des; father[i] >= src; i = father[i]) {
				flow[father[i]][i] += minFlow;
				flow[i][father[i]] -= minFlow;
			}
			maxFlow += minFlow;
		}
		return maxFlow;
	}

	public static void output() {
		int work = 0;
		int ability = FordFulkerson(start, end);
		for (int i = 1; i <= movie; i ++) {
			work += capacity[0][i];
		}
		if (work == ability) {
			System.out.println("Yes");
		}
		else {
			System.out.println("No");
		}
	}
}