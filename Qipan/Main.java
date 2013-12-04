/*
dfs 要注意分成当前行放与不放两种情况
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
	static int n;
	static int k;
	static int[][] map;
	static boolean[] used; // 记录某一列是否放过棋子
	static int done;
	static int res;

	public static void main(String args[]) {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		n = scan.nextInt();
		k = scan.nextInt();

		while (n != -1 && k != -1) {
			done = 0;
			res = 0;
			map = new int[n][n];
			used = new boolean[n];
			Arrays.fill(used, false);

			for (int i = 0; i < n; i ++) {
				String line = scan.next();
				for (int j = 0; j < n; j ++) {
					if (line.charAt(j) == '#')
						map[i][j] = 1;
					else
						map[i][j] = 0;
				}
			}		
			dfs(0);
			System.out.println(res);
			n = scan.nextInt();
			k = scan.nextInt();
		}
	}

	public static void dfs(int row) {
		if (done == k) {
			res ++;
			return;
		}
		if (row >= n)
			return;
		// put in "row" row
		for (int col = 0; col < n; col ++) {
			if (map[row][col] == 1 && !used[col]) {
				used[col] = true;
				done ++;
				dfs(row + 1);
				used[col] = false;
				done --;
			}
		}
		// wont put in this row
		dfs(row + 1); //注意不要漏了不放在此行的情况
	}
}