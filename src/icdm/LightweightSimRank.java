package icdm;

import java.io.*;
import java.util.*;

import share.Matrix;

public class LightweightSimRank {
	static float decay = 0.5f;
	static int K = 6;

	public static void main(String[] args) throws Exception {

		// transition matrix

		//		int[][] graph = new int[][] { { 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 0 },
		//				{ 0, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1 }, { 0, 0, 1, 0, 0 } };
		int nodeNum = 4000;
		int repeat = 100;
		int[][] graph = ScaleFreeGraph
				.getAdjacent(
						new File(
								"C:\\Program Files\\Barabasi Graph Generator\\Barabasi\\Edges.txt"),
						nodeNum);
		float[][] T = Matrix.normalize(graph);
		graph = null;
		System.gc();

		// position matrix 
		float[][] positionThreshold = new float[T.length][T.length];
		float[][] position = new float[T.length][T.length];

		// similarity query
//		float[][] resultsThreshold = new float[nodeNum][6];
//		float[][] results = new float[nodeNum][6];

		long t1=System.currentTimeMillis();
		for (int count = 0; count < repeat; count++) {
			LightweightSimRank lightweight = new LightweightSimRank();
			Random random = new Random();
			int a = random.nextInt(position.length);
			int b;
			do {
				b = random.nextInt(position.length);
				if (a != b)
					break;
			} while (true);

//			positionThreshold[a][b] = 1;
			position[a][b] = 1;
			int k = 0;
//			float simThreshold = 0;
			float sim = 0;
			do {
//				positionThreshold = lightweight.positionMatrix(
//						positionThreshold, T, 0.001f);
				position = lightweight.positionMatrix(position, T, 0.0001f);
				k++;
				sim += Matrix.diaSum(position);
//				simThreshold += Matrix.diaSum(positionThreshold);
//				resultsThreshold[count][k] = simThreshold;
//				results[count][k] = sim;
//				System.out.println(k + "0: " + sim);
//				System.out.println(k + "1: " + simThreshold);
				
			} while (k < K);
			System.out.println(count);

		}
		System.out.println(("average time cost: "+(System.currentTimeMillis()-t1)/100));

	}

	public float[][] positionMatrix(float[][] position, float[][] T,
			float threshold) {
		int n = position.length;
		float[][] newPosition = new float[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if ((position[i][j] > threshold) && (j != i))
					for (int p = 0; p < n; p++)
						if (T[i][p] > threshold)
							for (int q = 0; q < n; q++) {
								if (T[j][q] > threshold)
									newPosition[p][q] +=decay * position[i][j]
											* T[i][p] * T[j][q];
							}
			}
		return newPosition;
	}
}
