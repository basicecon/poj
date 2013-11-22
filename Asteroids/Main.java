/*
Hungarian Algorithm
LANG: JAVA
*/

import java.util.*;
import java.io.*;

public class Main {
	static int MAX = 1024;
	static int n;
	static int aster;
	static int[][] adj_list = new int[MAX][MAX];
	static int[] match = new int[MAX];
	static boolean[] used = new boolean[MAX];
	static int matching = 0;

	public static void main(String args[]) {
		read();
		System.out.println(Hungarian());
	}

	public static void read() {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		n = scan.nextInt();
		aster = scan.nextInt();

		for (int i = 0; i < aster; i ++) {
			int row = scan.nextInt();
			int col = scan.nextInt();
			col += n;
			adj_list[row][ ++adj_list[row][0] ] = col;
		}
	}

	public static boolean bfs(int start) {
		int index = 0;
		ArrayList<Integer[]> track = new ArrayList<Integer[]>();
		Integer[] src = new Integer[2];
		src[0] = start;
		src[1] = -1;
		track.add(src);
		used[start] = true;

		while (index < track.size()) {
			int node = track.get(index)[0];
			if (node <= n) { // row
				for (int i = 1; i <= adj_list[node][0]; i ++) {
					int col = adj_list[node][i];
					if (used[col])
						continue;
					Integer[] tmp = new Integer[2];
					tmp[0] = col;
					tmp[1] = index;
					track.add(tmp);
					used[col] = true;
					if (match[col] == 0) {
						index = 99999;
						break;
					}
				}
			}
			else { // col
				int row = match[node];
				if (row == 0)
					break;
				else {
					if (!used[row]) {
						Integer[] tmp = new Integer[2];
						tmp[0] = row;
						tmp[1] = index;
						track.add(tmp);
						used[row] = true;
					}
				}
			}
			index ++;
		}
		Integer[] last_pair = track.get(track.size()-1);
		int last = last_pair[0];

		if (match[last] != 0 || last <= n)
			return false;
		
		ArrayList<Integer> path = new ArrayList<Integer>();
		path.add(last_pair[0]);
		int father = last_pair[1];
		while (father != -1) {
			last_pair = track.get(father);
			path.add(last_pair[0]);
			father = last_pair[1];
		}	

		for (int i = 0; i < path.size()-1; i += 2) {
			match[path.get(i)] = path.get(i+1);
		}
		return true;
	}

	public static boolean dfs(int start) {
		for (int i = 1; i <= adj_list[start][0]; i ++) {
			int col = adj_list[start][i];
			if (!used[col]) {
				used[col] = true;
				if (match[col] == 0 || dfs(match[col])) {
					match[col] = start;
					return true;
				}
			}
		}
		return false;
	}

	public static int Hungarian() {
		for (int i = 1; i <= n; i ++) {
			Arrays.fill(used, false);
		/*	if (bfs(i)) {
				matching ++;
			}*/
			if (dfs(i)) {
				matching ++;
			}
		}
		return matching;
	}
}