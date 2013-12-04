/*
dfs
*/
import java.util.*;
import java.io.*;

public class Main {
	static int t;
	static int p;
	static int q;
	static char[][] path;
	static boolean[][] used;
	static boolean flag;
	static int[][] move = {{-1,-2},{1,-2},{-2,-1},{2,-1},{-2,1},{2,1},{-1,2},{1,2}};

	public static void main(String args[]) {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		t = scan.nextInt();
		for (int i = 1; i <= t; i ++) {
			p = scan.nextInt();
			q = scan.nextInt();
			path = new char[p*q][2];
			used = new boolean[p][q];
			for (boolean[] u : used)
				Arrays.fill(u, false);
			path[0][0] = '1';
			path[0][1] = 'A';
			used[0][0] = true;
			System.out.println("Scenario #" + i + ":");
			if (dfs(0, 0, 1)) {
				for (int j = 0; j < p*q; j ++) {
					System.out.print(path[j][1] + "" + path[j][0]);
				}
				System.out.println();
			}
			else {
				System.out.println("impossible");
			}
			//if (i != t)
				System.out.println();
		}
	}

	public static boolean dfs(int x, int y, int step) {
		if (step == p*q) {
			return true;
		}
		for (int i = 0; i < 8; i ++) {
			int new_x = x + move[i][0];
			int new_y = y + move[i][1];
			//System.out.println("x = " + x + " y = " + y + " new_x = " + new_x + " new_y = " + new_y);
			if (withinRange(new_x, new_y) && !used[new_x][new_y]) {
				path[step][0] = (char)(new_x + '1');
				path[step][1] = (char)(new_y + 'A');
				used[new_x][new_y] = true;
				boolean tmp = dfs(new_x, new_y, step+1);
				if (tmp)
					return true;
				used[new_x][new_y] = false;
			}
		}
		return false;
	}

	public static boolean withinRange(int x, int y) {
		if (x >= 0 && x < p && y >= 0 && y < q) {
			return true;
		}
		return false;
	}
}