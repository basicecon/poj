/*
枚举 和 贪心
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
	static int MAX = 110;
	static int t = 0;
	static int n = 0;
	static int[] mf = new int[MAX];
	static int[][] bandwidth = new int[MAX][MAX];
	static int[][] price = new int[MAX][MAX];
	static Scanner scan = null;
	static int bmin = 99999;
	static int[] bmaxs = new int[MAX];
	static int bmax = 99999;
	static double ratio = 0;

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
		Arrays.fill(bmaxs, 0);
		n = 0;
		bmin = 99999;
		bmax = 99999;
	}

	public static void read() {
		n = scan.nextInt();
		for (int i = 0; i < n; i ++) {
			mf[i] = scan.nextInt();
			for (int j = 0; j < mf[i]; j ++) {
				bandwidth[i][j] = scan.nextInt();
				price[i][j] = scan.nextInt();
				if (bandwidth[i][j] > bmaxs[i]) {
					bmaxs[i] = bandwidth[i][j];
				}
				if (bandwidth[i][j] < bmin) {
					bmin = bandwidth[i][j];
				}
			}
		}
		for (int i = 0; i < n; i ++) {
			if (bmaxs[i] < bmax) {
				bmax = bmaxs[i];
			}
		}
		/*
		for (int i = 0; i < n; i ++) {
			System.out.print(mf[i] + " ");
			for (int j = 0; j < mf[i]; j ++) {
				System.out.print(bandwidth[i][j] + " " + price[i][j] + " ");
			}
			System.out.println();
		}*/
	}

	public static void output() {
		int sum = 0;
		ratio = 0;
		for (int i = bmin; i <= bmax; i ++) {
			sum = 0;
			for (int j = 0; j < n; j ++) {
				int min = 99999;
				for (int k = 0; k < mf[j]; k ++) {
					if (bandwidth[j][k] >= i && price[j][k] < min) {
						min = price[j][k];
					}
				}

				sum += min;
			}
			if ((double)i / sum > ratio) {

			System.out.println("bw: " + i + " price: " + sum + "  = " + 
				(double)((double)(i) / (double)sum));
				ratio = (double)i / sum;
			}
		}
		System.out.printf("%.3f\n", ratio);
	}
}