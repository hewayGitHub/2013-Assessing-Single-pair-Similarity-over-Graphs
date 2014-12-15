package share;

import java.io.*;
import java.util.*;

public class Utility {

	/**
	 * write object
	 */
	public static void writeObj(Object obj, String objPath) {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(objPath)));
			out.writeObject(obj);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * read object
	 */
	public static Object readObj(String objPath) {
		Object obj = null;
		try {
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(objPath)));
			obj = in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * make all values of a row added up to 1
	 */
	public static void normalizeMatrix(float[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			float sum = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				sum += matrix[i][j];
			}
			for (int j = 0; j < matrix[0].length; j++) {
				if (Math.abs(matrix[i][j]) > 0.0001)
					matrix[i][j] = matrix[i][j] / sum;
			}
		}
	}

	/**
	 * read file into a String by line
	 */
	public static String readFile(File file, String lineTerminator) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));
			String fileStr = "";
			String s;
			while ((s = in.readLine()) != null)
				fileStr += s + lineTerminator;
			in.close();
			return fileStr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * get String in middle
	 */
	public static String getMiddleStr(String str, String match1, String match2) {
		int beg = str.indexOf(match1);
		if (beg == -1) {
			//System.out.println("can't find match1 in str!");
			return null;
		}
		beg += match1.length();
		int end = str.indexOf(match2, beg);
		if (end == -1) {
			//System.out.println("can't find match2 in str!");
			return str.substring(beg);
		}
		return str.substring(beg, end);
	}

	/**
	 * transfer Integer[] to int[]
	 */
	public static int[] ToIntArray(Integer[] integers) {
		int[] ints = new int[integers.length];
		for (int i = 0; i < integers.length; i++)
			ints[i] = integers[i].intValue();
		return ints;
	}

	/**
	 * print array
	 */
	public static void printArray(int[] ints) {
		for (int i : ints)
			System.out.print(i + "  ");
		System.out.println();
	}

	/**
	 * ArrayList to HashMap
	 */
	public static HashMap<String, Integer> listToMap(ArrayList<String> list) {
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {
			String item = list.get(i);
			Integer value = map.get(item);
			if (value == null)
				map.put(item, 1);
			else
				map.put(item, value + 1);
		}
		return map;
	}

	/**
	 * get max float
	 */
	public static int maxIndex(ArrayList<Float> list) {
		if (list == null) {
			System.out.println("searched list is null!");
			return -1;
		}
		int destination = 0;
		float compare = list.get(0);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > compare) {
				destination = i;
				compare = list.get(i);
			}
		}
		return destination;
	}
}
