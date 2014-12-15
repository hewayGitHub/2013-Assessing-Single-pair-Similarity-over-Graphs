package icdm;

import java.util.*;

import share.Utility;

public class LocalSimRank {

	static float C = 0.5f;
	static int K = 7;

	public static void main(String[] args) {

		//GraphArray graph = new GraphArray(4000, 4000 * 5);
		//int[][] edges = graph.neighbors;
		//float[][] weight = graph.weight;

		int[][] edges = (int[][]) Utility.readObj("DM_Sparse_Edges");
		float[][] weight = (float[][]) Utility.readObj("DM_Sparse_Weight");
		String[] author= (String[])Utility.readObj("DM_Author");

		// example
		// int[][] edges = new int[][] { { 2, 3 }, { 0, 3 }, { 3 }, { 2, 4
		// }, {
		// 2 } };
		// float[][] weight = new float[][] { { 0.5f, 0.5f }, { 0.5f, 0.5f
		// }, {
		// 1.0f },
		// { 0.5f, 0.5f }, { 1.0f } };

		int size = edges.length;
		Random ran = new Random();
		int COUNT = 5;

		long t1 = System.currentTimeMillis();
		for (int count = 0; count < COUNT; count++) {

			int a = Arrays.binarySearch(author, "Jiawei Han");
			int b = Arrays.binarySearch(author, "Philip S. Yu");
			float sim = 0.f;
			// int a = 0, b = 1;

			// initialize P
			float[][] P = new float[size][size];
			P[a][b] = 1.f;
			float[][] newP;

			for (int k = 0; k < K; k++) {
				newP = new float[size][size];
				for (int i = 0; i < size; i++) {
					for (int j = 0; j < size; j++) {
						if (P[i][j] > 0.001 && i != j) {
							for (int ii = 0; ii < edges[i].length; ii++)
								for (int jj = 0; jj < edges[j].length; jj++)
									newP[edges[i][ii]][edges[j][jj]] += P[i][j] * weight[i][ii] * weight[j][jj];
						}
					}
				}

				// Diasum
				float sum = 0;
				for (int i = 0; i < size; i++)
					sum += newP[i][i];

				sim += sum * Math.pow(C, k + 1);
				P = newP;

			}
			System.out.println(count + ": " + sim);
		}
		long t2 = System.currentTimeMillis();
		System.out.println(" Avg time: " + (t2 - t1) / COUNT);
	}

}
