package share;

import java.io.*;
import java.util.*;

public class SharedUtility {

	// generate a length*length float matrix
	public static float[][] getIdentityMatrix(int length) {
		float[][] matrix = new float[length][length];
		for (int i = 0; i < length; i++)
			for (int j = 0; j < length; j++)
				if (i == j)
					matrix[i][j] = 1.0f;
		return matrix;
	}

	// make all values of a row added up to 1
	public static float[][] normalizeMatrix(float[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			float sum = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				if (Math.abs(matrix[i][j]) > 0.01)
					sum += matrix[i][j];
			}
			if(sum-0<0.1)System.err.println("the row is all zero!");
			for (int j = 0; j < matrix[0].length; j++) {
				if (Math.abs(matrix[i][j]) > 0.0001)
					matrix[i][j] = matrix[i][j] / sum;
			}
		}

		return matrix;
	}

	// exchange the row and column, then normalize it
	public static float[][] exchangeMatrix(float[][] matrix) {
		float[][] exchange = new float[matrix[0].length][matrix.length];

		// get symmetrical matrix
		for (int i = 0; i < matrix[0].length; i++)
			for (int j = 0; j < matrix.length; j++)
				exchange[i][j] = matrix[j][i];

		normalizeMatrix(exchange);
		return exchange;
	}

	// write object to disk
	public static void writeObject(Object obj, String str) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(str));
			out.writeObject(obj);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// read object from disk
	public static Object readObject(String str) {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(str));
			Object o = in.readObject();
			in.close();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// return the first cluster of index
	// the left belongs to the other cluster
	public static ArrayList twoWayKMeans(double[] vector) {
		// check
		if (vector == null || vector.length < 3)
			System.out.println("twoWayKMeans: vector is null or too short to cluster.");

		double center1 = vector[MaxIndex(vector)];
		double center2 = vector[MinIndex(vector)];
		ArrayList<Integer> firstCluster;
		// difference result of every iteration
		double difference = 0;
		double differenceOld = 1;

		do {
			// clustering
			firstCluster = new ArrayList();
			for (int i = 0; i < vector.length; i++) {
				if (Math.abs(vector[i] - center1) <= Math.abs(vector[i] - center2))
					firstCluster.add(i);
			}

			// update center values
			double sum1 = 0, sum2 = 0;
			for (int i = 0; i < vector.length; i++) {
				if (firstCluster.contains(i))
					sum1 = +vector[i];
				else
					sum2 = +vector[i];
			}
			center1 = sum1 / firstCluster.size();
			center2 = sum2 / (vector.length - firstCluster.size());

			// obtain difference
			differenceOld = difference;
			for (int i = 0; i < vector.length; i++) {
				if (firstCluster.contains(i))
					difference = +(vector[i] - center1) * (vector[i] - center1);
				else
					difference = +(vector[i] - center2) * (vector[i] - center2);
			}

		} while (Math.abs(difference - differenceOld) < 0.1 && (Math.abs(differenceOld) > 0.01));

		return firstCluster;
	}

	// find max value of a array, return index
	public static int MaxIndex(double[] array) {
		if (array == null) {
			System.out.println("searched array is null!");
			return -1;
		}
		int destination = 0;
		double compare = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] > compare) {
				destination = i;
				compare = array[i];
			}
		}
		return destination;
	}

	// find min value of a array,return index
	public static int MinIndex(double[] array) {
		if (array == null) {
			System.out.println("searched array is null!");
			return -1;
		}
		int destination = 0;
		double compare = array[0];
		for (int i = 0; i < array.length; i++) {
			if (array[i] < compare) {
				destination = i;
				compare = array[i];
			}
		}
		return destination;
	}

	// get mean value of two matrix
	public static float[][] meanMatrix(float[][] temp, float[][] similarity) {
		int length = temp.length;
		if (length == similarity.length && length == similarity[0].length && length == temp[0].length) {
			for (int i = 0; i < length; i++)
				for (int j = 0; j < length; j++)
					similarity[i][j] = (temp[i][j] + similarity[i][j]) / 2.f;

			return similarity;
		} else {
			System.err.println("temp and similarity don't match!");
			return null;
		}

	}

	public static void printMatrix(double[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println();
		}
	}

	// write onto disk according to "METIS" format
	// 1 in matrix stands for a edge in corresponding graph
	// must be noted that: leftArray is first, then followed by rightArray
	public static void writeMetis(float[][] matrix, String name) {

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name)));
			// write vertices number and edges number
			out.print(matrix.length + matrix[0].length);
			out.print(" ");
			float sum = 0.f;
			for (int i = 0; i < matrix.length; i++)
				for (int j = 0; j < matrix[0].length; j++)
					if (Math.abs(matrix[i][j]) > 0.01)
						sum += matrix[i][j];
			out.println((int) sum);

			// write information of the ith vertex
			// the vertices are numbered starting from 1
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++)
					if (Math.abs(matrix[i][j] - 1) < 0.1) {
						out.print(matrix.length + j + 1);
						out.print(" ");
					}
				out.println();
			}

			for (int i = 0; i < matrix[0].length; i++) {
				for (int j = 0; j < matrix.length; j++)
					if (Math.abs(matrix[j][i] - 1) < 0.1) {
						out.print(j + 1);
						out.print(" ");
					}
				out.println();
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}



	public static void main(String[] args) {
		// float[][] f = new float[100][100];
		// for (int i = 0; i < f.length; i++)
		// for (int j = 0; j < f.length; j++)
		// f[i][j] = 1.0f;
		// writeObject(f, "simrank\\f.obj");
		// float[][] g = (float[][]) readObject("simrank\\f.obj");

		writeMetis(null, "lipei");
	}

}
