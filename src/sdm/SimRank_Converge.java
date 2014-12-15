package sdm;

/**
 * 
 * save memory, two similarity in one matrix
 * 
 */
import share.Utility;
import icdm.*;

public class SimRank_Converge {
	static float c = 0.5f;

	public static void main(String[] args) {
		for (int g = 1; g <= 10; g++) {
			GraphArray graph = new GraphArray(500, 500 * g);
			int[][] edges = graph.neighbors;
			float[][] weight = graph.weight;

			// example
//			 int[][] edges = new int[][] { { 2, 3 }, { 0, 3 }, { 3 }, { 2, 4 }, {
//			 2 } };
//			 float[][] weight = new float[][] { { 0.5f, 0.5f }, { 0.5f, 0.5f }, {
//			 1.0f },
//			 { 0.5f, 0.5f }, { 1.0f } };

			int size = edges.length;

			// two sim in one matrix
			float[][] sim = new float[size][size];
			for (int i = 0; i < size; i++)
				sim[i][i] = 1.f;

			// iteration
			int K = 10;
			long t1 = System.currentTimeMillis();
			for (int k = 0; k < K; k++) {
				for (int x = 0; x < size; x++) {
					for (int y = 0; y < x; y++) {
						int xSize = edges[x].length;
						int ySize = edges[y].length;
						float sum = 0;
						for (int i = 0; i < xSize; i++)
							for (int j = 0; j < ySize; j++) {
								int xi = edges[x][i];
								int yj = edges[y][j];
								if (xi < yj)
									sum += weight[x][i] * weight[y][j] * sim[xi][yj];
								else
									sum += weight[x][i] * weight[y][j] * sim[yj][xi];
							}
						sim[x][y] = sum * c;
					}
					//					if ((x % 1000) == 0)
					//						System.out.println(x);
				}

				// update
				for (int x = 0; x < size; x++)
					for (int y = x + 1; y < size; y++)
						sim[x][y] = sim[y][x];
				System.out.println(k + " time: " + (System.currentTimeMillis() - t1));

				Utility.writeObj(sim, "SimRank_" + (k + 1));
			}
			long t2 = System.currentTimeMillis();
			System.out.println("Sum time: " + (t2 - t1));
			System.out.println("Avg time: " + (t2 - t1) / K);

			int len = size;
			for (int k = 1; k <= 6; k++) {
				float[][] s = (float[][]) Utility.readObj("SimRank_" + k);
				float sum = 0;
				for (int i = 0; i < len; i++)
					for (int j = 0; j < len; j++) {
//						if (sim[i][j] > 0.001)
//							System.out.println("i=" + i + " j=" + j + " " + sim[i][j] + " " + s[i][j]);
						if (sim[i][j] != 0)
							sum += s[i][j] / sim[i][j];
						else
							sum += 1;
					}
				System.out.println((sum / (len * len)));
			}
		}
	}
}
