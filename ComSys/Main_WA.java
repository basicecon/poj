/*
枚举 和 贪心
LANG: JAVA
*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class Main_WA {
	static int MAX = 102;
	static int t = 0;
	static int n = 0;
	static ArrayList<Integer> bw = new ArrayList<Integer>();
	static int[] mf = new int[MAX];
	static int[][] bandwidth = new int[MAX][MAX];
	static int[][] price = new int[MAX][MAX];
	static Scanner scan = null;

	public static void main(String args[]) {
		run();
	}

	public static void run() {
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		t = scan.nextInt();
		while (t > 0) {
			init();
			read();
			output();
			t --;
		}
	}

	public static void init() {
		for (int[] b : bandwidth)
			Arrays.fill(b, 0);
		for (int[] p : price)
			Arrays.fill(p, 0);
		n = 0;
	}

	public static void read() {
		n = scan.nextInt();
		for (int i = 1; i <= n; i ++) {
			mf[i] = scan.nextInt();
			for (int j = 1; j <= mf[i]; j ++) {
				bandwidth[i][j] = scan.nextInt();
				price[i][j] = scan.nextInt();
				bw.add(bandwidth[i][j]);
			}
		}
		Collections.sort(bw);
	}

	public static void output() {
		int sum = 0;
		double res = 0;

		for (int i = 0; i < bw.size(); i ++) { // mei ju
			sum = 0;
			for (int j = 1; j <= n; j ++) { // greedy
				int min = 99999;
				for (int k = 1; k <= mf[j]; k ++) {
					if (bandwidth[j][k] >= bw.get(i) && price[j][k] < min) {
						min = price[j][k];
					}
				}
				sum += min;
			}
			if ((double)bw.get(i) / (double)sum > res) {
				res = (double)bw.get(i) / (double)sum;
			}
		}
		System.out.printf("%.3f\n", res);
	}
}