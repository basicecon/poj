/*
Max Net Work Flow Algorithm
LANG: JAVA
*/

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Main {
	static int MAX = 1024;
	static int cow;
	static int food;
	static int drink;
	static int[][] capacity = new int[MAX][MAX];
	static int[][] flow = new int[MAX][MAX];
	static boolean[] used = new boolean[MAX];
	static int[] father = new int[MAX];
	static int maxFlow = 0;
	static int start = 0;
	static int end = 0;

	public static void main(String args[]) {
		read();
		//System.out.println("start = " + start + " end = " + end);
		int max = FordFulkerson(start, end);
		System.out.println(max);
	}

	public static void read() {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		cow = scan.nextInt();
		food = scan.nextInt();
		drink = scan.nextInt();
		start = 0;
		end = food+2*cow+drink+1;
		int food_cnt = 0;
		int drink_cnt = 0;

		for (int i = 1; i <= food; i ++)
			capacity[0][i] = 1;

		for (int i = 1; i <= cow; ++ i) {
			food_cnt = scan.nextInt();
			drink_cnt = scan.nextInt();
			while (food_cnt > 0) {
				int ff = scan.nextInt();
				capacity[ff][i+food] = 1;
				food_cnt --;
			}
			capacity[i+food][i+food+cow] = 1;

			while (drink_cnt > 0) {
				int dd = scan.nextInt();
				capacity[i+food+cow][dd+food+cow+cow] = 1;
				capacity[dd+food+cow+cow][end] = 1;
				drink_cnt --;
			}
		}
		for (int i = 1; i <= cow; ++ i)
			capacity[i+food][i+food+cow] = 1;
		/*
		for (int i = start; i <= end; i ++) {
			for (int j = start; j <= end; j ++) {
				System.out.print(capacity[i][j] + " ");
			}
			System.out.println();
		}*/
	}

	public static void dfs(int src, int des) {
		used[src] = true;
		for (int i = 0; i <= end; i ++) {
			if (!used[i] && (capacity[src][i] > flow[src][i])) {
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
			int min = 99999;
			for (int i = des; father[i] >= src; i = father[i]) {
				min = (int)Math.min(min, capacity[father[i]][i] - flow[father[i]][i]);
			}
			for (int i = des; father[i] >= src; i = father[i]) {
				flow[father[i]][i] += min;
				flow[i][father[i]] -= min;
 			}
 			maxFlow += min;
		} 	
		return maxFlow;
	}
}