package icdm;

import java.io.File;

import share.Matrix;
import share.SharedUtility;

public class SimRank {
	final static float C = 0.5f;

	final static float tolerate = 0.0000000001f;

	public static void main(String[] args) throws Exception {

		//				float[][] transition = new float[][] { { 0, 1.f / 2, 0, 0, 1.f / 2 },
		//						{ 1.f / 3, 0, 1.f / 3, 0, 1.f / 3 },
		//						{ 0, 1.f / 2, 0, 1.f / 2, 0 }, { 0, 0, 1.f / 2, 0, 0.5f },
		//						{ 1.f / 3, 1.f / 3, 0, 1.f / 3, 0 } };
		//		float[][] transition = new float[][] { { 0, 1, 1, 0, 0 },
		//				{ 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 1.f }, { 1.f, 0, 0, 0, 0 },
		//				{ 0, 0, 1.f, 0, 0 } };
		float[][] transition = new float[][] { { 0, 1, 0, 0, 0 },
				{ 0, 0, 0, 0, 0 }, { 1, 0, 0, 1, 1 }, { 1, 1, 1, 0, 0 },
				{ 0, 0, 0, 1, 0 } };

		//		transition = Matrix.getTranspose(transition);
		//		Matrix.normalize(transition);

		transition = Matrix
				.getTransition(ScaleFreeGraph
						.getAdjacent(
								new File(
										"C:\\Program Files\\Barabasi Graph Generator\\Barabasi\\Edges.txt"),
								5000));

		float[][] sim;
		float[][] simNew = Matrix.identityMatrix(transition.length);
		float diff;
		int count = 5;
		do {
			if (count <= 0)
				break;
			sim = simNew;
			long t1 = System.currentTimeMillis();
			simNew = iterateOnceReturnNew(transition, sim);
			System.out.println(count + ":  "
					+ (System.currentTimeMillis() - t1));
			//diff = Matrix.getMaxAbsDifference(sim, simNew);
			//			System.out.println(diff);
			count--;

			// temp
			// System.out.println(count + ": " + simNew[0][1]);
		} while (true);
	}

	// return the original similarity matrix
	public static float[][] iterateOnce(float[][] transfer, float[][] similarity) {

		int length = transfer.length;
		for (int row = 0; row < length; row++) {
			for (int column = row + 1; column < length; column++) {
				float value = 0.f;
				for (int a = 0; a < length; a++) {
					if ((transfer[row][a] - 0) < 0.0001)
						continue;
					for (int b = 0; b < length; b++) {
						if ((transfer[column][b] - 0) < 0.0001)
							continue;
						value += similarity[a][b] * transfer[row][a]
								* transfer[column][b];
					}
				}
				similarity[row][column] = value * C;
				similarity[column][row] = value * C;
			}
		}

		return similarity;
	}

	// return a new similarity matrix
	public static float[][] iterateOnceReturnNew(float[][] transfer,
			float[][] similarity) {

		int length = transfer.length;
		float[][] sim = SharedUtility.getIdentityMatrix(length);
		for (int row = 0; row < length; row++) {
			for (int column = row + 1; column < length; column++) {
				float value = 0.f;
				for (int a = 0; a < length; a++) {
					if ((transfer[row][a] - 0) < 0.0001)
						continue;
					for (int b = 0; b < length; b++) {
						if ((transfer[column][b] - 0) < 0.0001)
							continue;
						value += similarity[a][b] * transfer[row][a]
								* transfer[column][b];
					}
				}
				sim[row][column] = value * C;
				sim[column][row] = sim[row][column];
			}
		}

		return sim;
	}

}
