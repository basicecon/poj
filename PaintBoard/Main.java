/*
dfs 有向图 对于每一个点记录上方矩形的个数 每次涂完上面的矩形更新个数 为0则可以涂
*/
import java.util.*;
import java.io.*;

public class Main {
	static int t;
	static int n_rect;
	static int[][] map; 
	static int[] n_above; //记录两两矩形之间的关系
	static rect[] board;
	static int res;
	static boolean used[];

	public static class rect {
		int x1;
		int x2;
		int y1;
		int y2;
		int c;
		public rect () {
			x1 = 0;
			x2 = 0;
			y1 = 0;
			y2 = 0;
			c = 0;
		}
	}


	public static void main(String args[]) {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		t = scan.nextInt();
		while (t > 0) {
			res = 99999;
			n_rect = scan.nextInt();
			map = new int[n_rect][n_rect];
			n_above = new int[n_rect];
			board = new rect[n_rect];
			used = new boolean[n_rect];
			Arrays.fill(used, false);

			for (int i = 0; i < n_rect; i ++) {
				board[i] = new rect();
				board[i].y1 = scan.nextInt();
				board[i].x1 = scan.nextInt();
				board[i].y2 = scan.nextInt();
				board[i].x2 = scan.nextInt();
				board[i].c = scan.nextInt();
			}
			buildGraph();
			dfs(0, 0, 0);
			System.out.println(res);
			t --;
		}
	}

	public static void buildGraph() {
		for (int i = 0; i < n_rect; i ++) {
			for (int j = 0; j < n_rect; j ++) {
				if (board[i].y1 == board[j].y2 
					&& (board[j].x2 > board[i].x1 && board[j].x1 < board[i].x2)) {
					n_above[i] ++;
					map[j][i] = 1;
				}
			}
		}
	}

	public static void dfs(int dep, int sum, int color) {
		if (sum > res)
			return;
		if (dep == n_rect) {
			res = sum;
			return;
		}
		for (int i = 0; i < n_rect; i ++) {
			if (!used[i] && n_above[i] == 0) {
				used[i] = true;
				for (int j = 0; j < n_rect; j ++) {
					if (map[i][j] == 1) {
						n_above[j] --;
					}
				}
				if (board[i].c == color)
					dfs(dep+1, sum, color);
				else
					dfs(dep+1, sum+1, board[i].c);

				used[i] = false;
				for (int j = 0; j < n_rect; j ++) {
					if (map[i][j] == 1) {
						n_above[j] ++;
					}
				}
			}
		}
	}
}