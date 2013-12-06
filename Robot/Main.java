/*
bfs 有方向的
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
	static int MAX = 99999;
	static int m;
	static int n;
	public static class node {
		int x;
		int y;
		int dir;
		int step;
		public node() {
			x = 0;
			y = 0;
			dir = -1;
			step = 0;
		}
	}
	static int[][] map;
	static int[][][] used;
	static node start = new node();
	static node end = new node();
	static node curr;
	static node next;
	static Queue<node> queue = new LinkedList<node>();
	static int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
	static int res = 0;

	public static void main(String args[]) {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		m = scan.nextInt();
		n = scan.nextInt();

		while (m != 0 && n != 0) {
			map = new int[m+1][n+1];
			used = new int[m+1][n+1][4];
			queue.clear();
			for (int i = 0; i < m; i ++) {
				for (int j = 0; j < n; j ++) {
					int tmp = scan.nextInt();
					if (tmp == 1) {
						map[i][j] = 1;
						map[i+1][j] = 1;
						map[i][j+1] = 1;
						map[i+1][j+1] = 1;
					}
				}
			}
			for (int i = 0; i <= m; i ++) {
				for (int j = 0; j <= n; j ++) {
					//System.out.print(map[i][j] + " ");
					for (int k = 0; k < 4; k ++) {
						used[i][j][k] = MAX;
					}
				}
				//System.out.println();
			}
			start.x = scan.nextInt();
			start.y = scan.nextInt();
			end.x = scan.nextInt();
			end.y = scan.nextInt();
			String tmp = scan.next();
			if (tmp.equals("north"))
				start.dir = 0;
			if (tmp.equals("east"))
				start.dir = 1;
			if (tmp.equals("south"))
				start.dir = 2;
			if (tmp.equals("west"))
				start.dir = 3;

			start.step = 0;
			curr = start;

			res = bfs();
			System.out.println(res);

			m = scan.nextInt();
			n = scan.nextInt();
		}
	}

	public static int bfs() {
		queue.add(start);
		used[start.x][start.y][start.dir] = 0;

		while (!queue.isEmpty()) {
			node curr = queue.poll();
			//System.out.println("~~~~~~~~" + "x = " + curr.x + " y = " + curr.y + " dir = " + curr.dir + " step = " + curr.step);
			if (curr.x == end.x && curr.y == end.y) {
				return curr.step;
			}
			if (curr.step + 1 < used[curr.x][curr.y][(curr.dir+1)%4]) {
				next = new node();
				//next = curr;
				next.x = curr.x;
				next.y = curr.y;
				next.dir = (curr.dir+1)%4;
				next.step = curr.step+1;
				used[next.x][next.y][next.dir] = curr.step + 1;
				//System.out.println("turn right add : " + "x = " + next.x + " y = " + next.y + " dir = " + next.dir + " step = " + next.step);
				//System.out.println();
				queue.add(next);
			}
			if (curr.step + 1 < used[curr.x][curr.y][(curr.dir+3)%4]) {
				next = new node();
				//next = curr;
				next.x = curr.x;
				next.y = curr.y;
				next.dir = (curr.dir+3)%4;
				next.step = curr.step+1;
				used[next.x][next.y][next.dir] = curr.step + 1;
				//System.out.println("turn left add : " + "x = " + next.x + " y = " + next.y + " dir = " + next.dir + " step = " + next.step);
				//System.out.println();
				queue.add(next);
			}
			for (int i = 1; i <= 3; i ++) {
				int new_x = curr.x + i * directions[curr.dir][0];
				int new_y = curr.y + i * directions[curr.dir][1];
				next = new node();
				
				if (ok(new_x, new_y, curr) && map[new_x][new_y] == 0) {
					next.x = new_x;
					next.y = new_y;
					next.dir = curr.dir;
					next.step = curr.step + 1;
					used[new_x][new_y][next.dir] = curr.step + 1;
					//System.out.println("go straight " + i + " steps, add : " + "x = " + next.x + " y = " + next.y + " dir = " + next.dir + " step = " + next.step);
					//System.out.println();
					queue.add(next);
				}

				if (new_x < 0 || new_y < 0 || new_x > m || new_y > n ||  map[new_x][new_y] == 1)
					break;
				/*
				else
					break;*/
				// 如果走一步被挡或出界不可以，二三步不可能实现所以要break出来，
				// 但是如果是因为以前走过这一步走过的状态而不可以，则不需break出来，因为这并不代表二三步不可以走
			}
		}
		return -1;
	}

	public static boolean ok(int nx, int ny, node cur) {
		if (nx > 0 && nx < m && ny > 0 && ny < n
			&& cur.step + 1 < used[nx][ny][cur.dir]) {
			return true;
		}
		return false;
	}
}