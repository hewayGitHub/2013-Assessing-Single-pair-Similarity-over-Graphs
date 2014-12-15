package sdm;

import java.io.BufferedReader;
import java.io.FileReader;
import share.Utility;

public class SimRank {
	static float c = 0.5f;

	public static void main(String[] args) throws Exception {
		for (int g = 1; g <= 1; g++) {
			/*GraphArray graph = new GraphArray(1000 * g, 5000 * g);
			int[][] edges = graph.neighbors;
			float[][] weight = graph.weight;*/
			//		int[][] edges = (int[][]) Utility.readObj("DM_Sparse_Edges");
			//		float[][] weight = (float[][]) Utility.readObj("DM_Sparse_Weight");

			// example
			// int[][] edges = new int[][] { { 2, 3 }, { 0, 3 }, { 3 }, { 2, 4 }, {
			// 2 } };
			// float[][] weight = new float[][] { { 0.5f, 0.5f }, { 0.5f, 0.5f }, {
			// 1.0f },
			// { 0.5f, 0.5f }, { 1.0f } };

			int[][] edges = (int[][]) Utility.readObj(".\\workdir\\DM_Sparse_Edges");
			float[][] weight = (float[][]) Utility.readObj(".\\workdir\\DM_Sparse_Weight");
			//String[] author= (String[])Utility.readObj(".\\workdir\\DM_Author");
			
			int COUNT = 100;
			int[][] nodePair = new int[COUNT][2];
			BufferedReader in = new BufferedReader(new FileReader("workdir\\randomPair"));
			for (int count = 0; count < COUNT; count++) {
				String s;
				s = in.readLine();
				int index = s.indexOf(" ");
	     		nodePair[count][0] = (new Integer(s.substring(0, index)));
				nodePair[count][1] = (new Integer(s.substring(index+1)));
			}
			in.close();
			
			
			int size = edges.length;

			// two sim in one matrix
			float[][] sim = new float[size][size];
			for (int i = 0; i < size; i++)
				sim[i][i] = 1.f;

			// iteration
			int K = 6;
			int countIterate = 0;
			
			long t1 = System.currentTimeMillis();
			for (int count = 0; count < COUNT; count++) {
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
								
								countIterate++;
							}
						sim[x][y] = sum * c;
					}
					/*if ((x % 1000) == 0)
						System.out.println(x);*/
				}

				// update
				for (int x = 0; x < size; x++)
					for (int y = x + 1; y < size; y++)
						sim[x][y] = sim[y][x];
				//System.out.println(k + " time: " + (System.currentTimeMillis() - t1));
			}
			}
			long t2 = System.currentTimeMillis();
			System.out.println(g+ " Sum time: " + (t2 - t1));
			System.out.println(g+ " Avg time: " + (t2 - t1) / 100);
			
		}
	}
}
