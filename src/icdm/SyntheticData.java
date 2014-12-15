package icdm;

import java.util.*;

public class SyntheticData {

	public int[][] generateData(int nodeNum, int edgeNum) {
		int[][] graph = new int[nodeNum][nodeNum];
		Random ran = new Random();
		while (edgeNum > 0) {
			int beg = ran.nextInt(nodeNum);
			int end = ran.nextInt(nodeNum);
			if (graph[beg][end] == 0) {
				graph[beg][end] = 1;
				edgeNum--;
			}
		}
		return graph;
	}

	// a test case in Section 4.2
	public static void main(String[] args) {
		

	}

}
