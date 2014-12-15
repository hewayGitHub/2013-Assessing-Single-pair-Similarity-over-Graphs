package icdm;

import share.*;
import java.util.*;

public class Test1 {
	public static void main(String[] args) {
		float[][] T = new float[][] { { 0, 1.f / 2, 0, 0, 1.f / 2 },
				{ 1.f / 3, 0, 1.f / 3, 0, 1.f / 3 },
				{ 0, 1.f / 2, 0, 1.f / 2, 0 }, { 0, 0, 1.f / 2, 0, 0.5f },
				{ 1.f / 3, 1.f / 3, 0, 1.f / 3, 0 } };
		int n = T.length;
//		float[][] AC = new float[][] { { 0, 0, 0, 0, 0 },
//				{ 0, 0.25f, 0, 0.25f, 0 }, { 0, 0, 0, 0, 0 },
//				{ 0, 0, 0, 0, 0 }, { 0, 0.25f, 0, 0.25f, 0 } };
		float[][] AC  = new float[n][n];
		for (int p = 0; p < n; p++)
			for (int q = 0; q < n; q++)
				AC[p][q] =T[1][p] * T[3][q];

		float sim=1.f/6;
		
		for (int count = 2;; count++) {
			// set diagonal to be 0
			for (int i = 0; i < AC.length; i++)
				AC[i][i] = 0;

			ArrayList<float[][]> values = new ArrayList<float[][]>();
			
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					float[][] v = new float[n][n];
					for (int p = 0; p < n; p++)
						for (int q = 0; q < n; q++)
							v[p][q] = AC[i][j] * T[i][p] * T[j][q];
					values.add(v);
				}

			// update AC
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					float sum = 0;
					for (int k = 0; k < values.size(); k++)
						sum += values.get(k)[i][j];
					AC[i][j] = sum;
				}
			float sum = 0;
			for (int i = 0; i < n; i++)
				sum += AC[i][i];
			sim +=sum * Math.pow(SimRank.C, count);
			System.out.println(count + ": " + sim);

		}
	}
}
