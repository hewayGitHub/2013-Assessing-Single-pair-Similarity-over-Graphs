package icdm;

import java.io.File;
import java.util.Random;

import share.Matrix;

/**
 * Section 4.1
 * @author Administrator
 *
 */
public class Accuracy {
	float decay = 0.5f;
	static int K = 6;

	public static void main(String[] args) throws Exception {

		// transition matrix

		//		int[][] graph = new int[][] { { 0, 0, 1, 1, 0 }, { 1, 0, 0, 1, 0 },
		//				{ 0, 0, 0, 1, 0 }, { 0, 0, 1, 0, 1 }, { 0, 0, 1, 0, 0 } };
		int nodeNum = 1000;
		int repeat = 100;
		int[][] graph = ScaleFreeGraph
				.getAdjacent(
						new File(
								"C:\\Program Files\\Barabasi Graph Generator\\Barabasi\\Edges_random.txt"),
						nodeNum);
		float[][] T = Matrix.normalize(graph);
		graph = null;
		System.gc();

		// position matrix 
		float[][] positionThreshold = new float[T.length][T.length];
		float[][] position = new float[T.length][T.length];

		// similarity query
		float[][] resultsThreshold = new float[repeat][6];
		float[][] results = new float[repeat][6];

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

			positionThreshold[a][b] = 1;
			position[a][b] = 1;
			int k = 0;
			float simThreshold = 0;
			float sim = 0;
			do {
				positionThreshold = lightweight.positionMatrix(
						positionThreshold, T, 0.001f);
				position = lightweight.positionMatrix(position, T, 0.000001f);
				simThreshold += Matrix.diaSum(positionThreshold);
				sim += Matrix.diaSum(position);
				resultsThreshold[count][k] = simThreshold;
				results[count][k] = sim;
				//				System.out.println(k + "0: " + sim);
				//				System.out.println(k + "1: " + simThreshold);
				k++;
			} while (k < K);

		}

		// statistics
		for (int i = 0; i < 6; i++) {
			float sumThreshold = 0;
			float sum = 0;
			for (int j = 0; j < repeat; j++) {
				sumThreshold += resultsThreshold[j][i];
				sum += results[j][i];
//								System.out
//										.println(((results[j][i] - resultsThreshold[j][i]) / results[j][i])
//												+ " "
//												+ resultsThreshold[j][i]
//												+ " "
//												+ results[j][i]);
			}
			System.out.println(i + ":---------: " + 100*(1 - sumThreshold / sum));
			// System.out.println(sumThreshold+" "+ sum);
		}

	}
}
