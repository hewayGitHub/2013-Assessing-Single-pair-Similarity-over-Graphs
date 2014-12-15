package icdm;

import java.io.*;

public class ScaleFreeGraph {

	public static void main(String[] args) throws IOException {
		File edges = new File(
				"C:\\Program Files\\Barabasi Graph Generator\\Barabasi\\Edges.txt");
		BufferedReader in = new BufferedReader(new FileReader(edges));
		String s;
		int i = 0;
		while ((s = in.readLine()) != null) {
			i++;
		}
		in.close();
		System.out.println(i); 
		getAdjacent(edges, 5000);
	}

	public static int[][] getAdjacent(File edges, int nodeNum)
			throws IOException {
		int[][] adjacent = new int[nodeNum][nodeNum];

		BufferedReader in = new BufferedReader(new FileReader(edges));
		String s;
		while ((s = in.readLine()) != null) {
			int index = s.indexOf(" ");
			int beg = (new Integer(s.substring(0, index)));
			int end = (new Integer(s.substring(index + 3)));
			adjacent[beg][end] = 1;//reversed already
		}
		in.close();
		return adjacent;
	}
}
