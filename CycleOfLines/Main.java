/*
dfs 
*/
import java.util.*;
import java.io.*;

public class Main {
	static int t;
	static int n;
	static int m;
	static List<ArrayList<Integer>> graph;
	static int[] nodes; //对于每一个点记录是当前path的第几个点
	static boolean[] used;
	static int max;

	public static void main(String args[]) {
		Scanner scan = null;
		try {
			scan = new Scanner(System.in);
		} catch(Exception e) {}
		t = scan.nextInt();
		while (t > 0) {
			max = 0;
			n = scan.nextInt();
			m = scan.nextInt();
			nodes = new int[n+1];
			used = new boolean[n+1];
			graph = new ArrayList<ArrayList<Integer>>();
			for (int i = 1; i <= n+1; i ++) { // 1 to 7
				graph.add(new ArrayList<Integer>());
			}
			for (int i = 0; i < m; i ++) {
				int x = scan.nextInt();
				int y = scan.nextInt();
				graph.get(x).add(y);
				graph.get(y).add(x);
			}
			for (int i = 1; i <= n; i ++)
				dfs(i, 1);
			if (max > 2)
				System.out.println(max);
			else
				System.out.println("0");
			t --;
		}
	}

	public static void dfs(int src, int num) {
		nodes[src] = num;
		used[src] = true;
		for (int i = 0; i < graph.get(src).size(); i ++) {
			int des = graph.get(src).get(i);
			if (!used[des]) { // didn't find a cycle
				dfs(des, num+1);
			}
			else { // found a cycle
				if (max < nodes[src] - nodes[des] + 1) {
					max = nodes[src] - nodes[des] + 1;
				}
			}
		}
	}
}