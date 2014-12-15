package share;

public class Matrix {

	// get transpose matrix
	public static float[][] getTranspose(float[][] matrix) {
		float[][] transpose = new float[matrix[0].length][matrix.length];
		for (int i = 0; i < transpose.length; i++)
			for (int j = 0; j < transpose[0].length; j++)
				transpose[i][j] = matrix[j][i];
		return transpose;
	}

	public static int[][] getTranspose(int[][] matrix) {
		int[][] transpose = new int[matrix[0].length][matrix.length];
		for (int i = 0; i < transpose.length; i++)
			for (int j = 0; j < transpose[0].length; j++)
				transpose[i][j] = matrix[j][i];
		return transpose;
	}

	// get transition matrix
	public static float[][] getTransition(int[][] matrix) {
		float[][] transition = new float[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			float sum = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				sum += matrix[i][j];
			}
			for (int j = 0; j < matrix[0].length; j++) {
				if (sum > 0)
					transition[i][j] = ((float) matrix[i][j]) / sum;
			}
		}
		return transition;
	}

	// get product: matrix-by-matrix
	public static float[][] getProduct(float[][] A, float[][] B) {
		if (A[0].length != B.length) {
			System.out.println("invalid matrices product. exit.");
			return null;
		}
		float[][] product = new float[A.length][B[0].length];
		for (int i = 0; i < product.length; i++) {
			for (int j = 0; j < product[0].length; j++) {
				float sum = 0.f;
				for (int k = 0; k < A[i].length; k++)
					if ((A[i][k] != 0) && (B[k][j] != 0))
						sum += A[i][k] * B[k][j];

				product[i][j] = sum;
			}
		}
		return product;
	}

	// get product: vector-by-matrix
	public static float[] getProduct(float[] A, float[][] B) {
		if (A.length != B.length) {
			System.out.println("invalid matrices product. exit.");
			return null;
		}
		float[] product = new float[A.length];
		for (int i = 0; i < product.length; i++) {
			float sum = 0.f;
			for (int k = 0; k < B[i].length; k++)
				if ((A[k] != 0) && (B[k][i] != 0))
					sum += A[k] * B[k][i];

			product[i] = sum;
		}
		return product;
	}

	// get product: constant-by-matrix
	public static float[][] getProduct(float f, float[][] A) {

		float[][] product = new float[A.length][A[0].length];
		for (int i = 0; i < product.length; i++) {
			for (int j = 0; j < product[0].length; j++) {
				product[i][j] = A[i][j] * f;
			}
		}
		return product;
	}

	// get product: constant-by-vector
	public static float[] getProduct(float f, float[] A) {

		float[] product = new float[A.length];
		for (int i = 0; i < product.length; i++) {
			product[i] = A[i] * f;
		}
		return product;
	}

	// get product: A'-by-B
	public static float[][] getProduct(float[] A, float[] B) {

		float[][] product = new float[A.length][B.length];
		for (int i = 0; i < A.length && A[i] > 0.0001; i++)
			for (int j = 0; j < B.length && B[j] > 0.0001; j++) {
				product[i][j] = A[i] * B[j];
			}
		return product;
	}

	/**
	 * get L*S*(transpose of L)
	 */
	public static float[][] getMultiply3(float[][] L, float[][] S) {
		// left matrix's column number should be equal to
		// right matrix's row number
		if (L[0].length != S.length || L.length != L[0].length) {
			System.out.println("invalid matrices multiply. exit.");
			return null;
		}
		float[][] temp = new float[L.length][S[0].length];
		for (int i = 0; i < temp.length; i++)
			for (int j = 0; j < temp[0].length; j++) {
				float sum = 0.f;
				for (int k = 0; k < L[i].length; k++) {
					if ((L[i][k] != 0) && (S[k][j] != 0))
						sum += L[i][k] * S[k][j];
				}

				temp[i][j] = sum;
			}

		System.out.println("half.");

		for (int i = 0; i < S.length; i++)
			for (int j = 0; j < S[0].length; j++) {
				float sum = 0.f;
				for (int k = 0; k < temp[i].length; k++) {
					if ((temp[i][k] != 0) && (L[j][k] != 0))
						sum += temp[i][k] * L[j][k];
				}

				S[i][j] = sum;
			}

		return S;
	}

	public static float[][] getDirectMultiply3(float[][] L, float[][] S) {
		// left matrix's column number should be equal to
		// right matrix's row number
		if (L[0].length != S.length || L.length != L[0].length) {
			System.out.println("invalid matrices multiply. exit.");
			return null;
		}

		float[][] result = Matrix.identityMatrix(S.length);
		for (int i = 0; i < S.length; i++)
			for (int j = i + 1; j < S[0].length; j++) {
				float sum = 0.f;
				for (int k = 0; k < S.length; k++) {
					if (L[j][k] != 0) {
						float tmp = 0.f;
						for (int t = 0; t < S.length; t++) {
							if ((L[i][t] != 0) && (S[k][t] != 0))
								tmp += L[i][t] * S[t][k];
						}

						sum += tmp * L[j][k];
					}
				}
				result[i][j] = sum;
				result[j][i] = sum;
			}

		return result;
	}

	/**
	 * make all values of a row added up to 1
	 */
	public static void normalize(float[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			float sum = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				sum += matrix[i][j];
			}
			for (int j = 0; j < matrix[0].length; j++) {
				if (sum > 0.001)
					matrix[i][j] = matrix[i][j] / sum;
			}
		}
	}

	public static float[][] normalize(int[][] matrix) {
		float[][] m = new float[matrix.length][matrix[0].length];
		for (int i = 0; i < matrix.length; i++) {
			int sum = 0;
			for (int j = 0; j < matrix[0].length; j++)
				sum += matrix[i][j];

			for (int j = 0; j < matrix[0].length; j++) {
				if (sum > 0.01)
					m[i][j] = ((float) matrix[i][j]) / sum;
			}
		}
		return m;
	}

	public static float diaSum(float[][] matrix) {
		float sum = 0;
		for (int i = 0; i < matrix.length; i++)
			sum += matrix[i][i];
		return sum;
	}

	// generate a length*length float matrix
	public static float[][] identityMatrix(int length) {
		float[][] matrix = new float[length][length];
		for (int i = 0; i < length; i++)
			matrix[i][i] = 1.0f;
		return matrix;
	}

	// get the maximum absolute difference of every element
	public static float getMaxAbsDifference(float[][] a, float[][] b) {
		float diff = 0.f;
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++)
				if (Math.abs(a[i][j] - b[i][j]) > diff)
					diff = Math.abs(a[i][j] - b[i][j]);
		return diff;
	}

	public static void main(String[] args) {
		
	}

}
