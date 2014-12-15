package icdm;

import share.*;

public class SimAB {

	public static void main(String[] args) {
		float[][] transition = new float[][] { { 0, 1.f / 2, 0, 0, 1.f / 2 },
				{ 1.f / 3, 0, 1.f / 3, 0, 1.f / 3 },
				{ 0, 1.f / 2, 0, 1.f / 2, 0 }, { 0, 0, 1.f / 2, 0, 0.5f },
				{ 1.f / 3, 1.f / 3, 0, 1.f / 3, 0 } };

		float[] A=transition[0], B=transition[2];
		for (int k = 1;; k++) {
			// statistics
			float sum = 0.f;
			for (int i = 0; i < A.length; i++) {
				sum += A[i] * B[i];
			}
			sum=sum*(float)Math.pow(SimRank.C, k);
			System.out.println(k + ": " + sum);
			
			// A
			A = Matrix.getProduct(A, transition);
			//B
			B = Matrix.getProduct(B, transition);
		}
	}

}
