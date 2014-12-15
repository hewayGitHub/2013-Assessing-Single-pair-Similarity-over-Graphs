package icdm;

import java.util.*;

public class GraphArray {
	public int[][] neighbors;
	public float[][] weight;

	public GraphArray(float[][] adjacent) {
		float threshold = 0.01f;
		neighbors = new int[adjacent.length][];
		weight = new float[adjacent.length][];
		for (int i = 0; i < adjacent.length; i++) {
			int num = 0;
			@SuppressWarnings("unused")
			float sum = 0;
			for (int j = 0; j < adjacent[i].length; j++) {
				if (adjacent[i][j] > threshold) {
					num++;
					sum += adjacent[i][j];
				}
			}
			neighbors[i] = new int[num];
			weight[i] = new float[num];
			for (int j = 0, d = 0; j < adjacent[i].length; j++) {
				if (adjacent[i][j] > threshold) {
					neighbors[i][d] = j;
					weight[i][d] = adjacent[i][j];
					d++;
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public GraphArray(int nodes, int edges) {
		neighbors = new int[nodes][];
		weight = new float[nodes][];

		ArrayList[] nei = new ArrayList[nodes];
		ArrayList[] wei = new ArrayList[nodes];
		for (int i = 0; i < nodes; i++) {
			nei[i] = new ArrayList<Integer>();
			wei[i] = new ArrayList<Float>();
		}

		Random ran = new Random();
		int beg, end;
		for (int i = 0; i < edges; i++) {
			// add one edge
			do {
				beg = ran.nextInt(nodes);
				end = ran.nextInt(nodes);
			} while (nei[beg].contains(new Integer(end)));
			nei[beg].add(new Integer(end));
			wei[beg].add(new Float(1));
		}

		// trim
		for (int i = 0; i < nodes; i++) {
			int s = nei[i].size();
			int[] ne = new int[s];
			float[] we = new float[s];
			if (s > 0) {
				float sum = 0;
				for (int j = 0; j < s; j++) {
					ne[j] = (Integer) nei[i].get(j);
					sum += (Float) wei[i].get(j);
				}
				for (int j = 0; j < s; j++)
					we[j] = (Float) wei[i].get(j) / sum;
			}
			neighbors[i] = ne;
			weight[i] = we;
		}
	}
}
