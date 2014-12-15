package icdm;

import java.io.File;
import java.util.Random;
import java.math.*;
import share.Matrix;

public class NonZero {

	static float decay = 0.5f;
	static int K = 6;

	public static void main(String[] args) throws Exception {
		int nodeNum = 4000;
		int repeat = 20;
		int[][] graph = ScaleFreeGraph
				.getAdjacent(
						new File(
								"C:\\Program Files\\Barabasi Graph Generator\\Barabasi\\Edges.txt"),
						nodeNum);
		float[][] T = Matrix.normalize(graph);
		graph = null;
		System.gc();

		// position matrix 
		float[][] position = new float[T.length][T.length];

		BigInteger[][] results = new BigInteger[repeat][K];

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

			position[a][b] = 1;
			int k = 0;

			do {

				position = lightweight.positionMatrix(position, T, 0.00000001f);

				BigInteger num = BigInteger.valueOf(0);
				for (int i = 0; i < position.length; i++)
					for (int j = 0; j < position[0].length; j++) {
						if (position[i][j] > 0)
							num = num.add(BigInteger.valueOf(1));
					}
				results[count][k] = num;

				k++;
			} while (k < K);
			System.out.println(count);

		}
		// stats
		for (int i = 0; i < K; i++) {
			BigInteger sum = BigInteger.valueOf(0);
			for (int n = 0; n < repeat; n++)
				sum = sum.add(results[n][i]);
			System.out.println(sum.divide(BigInteger.valueOf(repeat)).intValue());
		}
	}

}
