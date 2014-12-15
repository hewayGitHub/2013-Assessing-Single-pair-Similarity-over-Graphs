package icdm;

import java.util.Arrays;
import java.util.Random;

import share.Matrix;
import share.SharedUtility;

public class CitationGraph {

	static float[][] T = (float[][]) SharedUtility
			.readObject("E:\\Papers\\BlockSimRank\\data\\transitions.obj");
	static float decay = 0.5f;
	static int K = 6;

	public static void main(String[] args) {		
		

				int[] edgeStats = new int[T.length];
				int all=0;
				for (int i = 0; i < T.length; i++) {
					int sum = 0;
					for (int j = 0; j < T.length; j++)
						if (T[i][j] > 0){
							sum += 1;
							all+=1;
						}
					edgeStats[i] = sum;
				}
				Arrays.sort(edgeStats);
				
		
				int stats = 0;
				for (int i = 0; i < T.length * 0.3; i++)
					stats += edgeStats[T.length-1-i];
				System.out.println("all = "+all);
				System.out.println(((float) stats) / all);

		Matrix.normalize(T);
		int repeat = 100;

		// position matrix 
		float[][] position = new float[T.length][T.length];

		// similarity and time cost
		float[] sims = new float[repeat];
		float[] times = new float[repeat];

		
		System.out.println("begin.");
		long t1 = System.currentTimeMillis();
		for (int count = 0; count < repeat; count++) {

			long t2 = System.currentTimeMillis();
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
			float sim = 0;
			do {
				position = lightweight.positionMatrix(position, T, 0.00001f);
				k++;
				sim += Matrix.diaSum(position);

			} while (k < K);
			System.out.println(count);
			sims[count] = sim;
			times[count] = ((float) (System.currentTimeMillis() - t2)) / 1000;

		}
		System.out
				.println(("average time cost: " + (System.currentTimeMillis() - t1) / 100));

		for (int i = 0; i < repeat; i++) 
			System.out.println(sims[i]);
		System.out.println("----------------------------------");
		for (int i = 0; i < repeat; i++) 
			System.out.println(times[i]);

	}
}
