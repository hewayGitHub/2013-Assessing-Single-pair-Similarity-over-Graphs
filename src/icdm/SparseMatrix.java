package icdm;

import java.util.*;

public class SparseMatrix {

	int[] rows;
	int[] columns;
	float[] values;
	int index; // next space
	int size;

	SparseMatrix(int size) {
		rows = new int[size];
		columns = new int[size];
		values = new float[size];
		index = 0;
		this.size = size;
	}

	public void add(int row, int column, float value) {
		rows[index] = row;
		columns[index] = column;
		values[index] = value;
		index++;
		if (index >= size) {

			int[] rows1 = new int[size * 2];
			int[] columns1 = new int[size * 2];
			float[] values1 = new float[size * 2];

			for (int i = 0; i < size; i++) {
				rows1[i] = rows[i];
				columns1[i] = columns[i];
				values1[i] = values[i];
			}
			size *= 2;

		}
	}
}
