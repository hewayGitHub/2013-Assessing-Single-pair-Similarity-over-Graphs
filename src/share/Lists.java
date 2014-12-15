package share;

import java.util.*;

public class Lists {
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
	
	/**
	 * get mean value<br>
	 */
	public static float meanValue(ArrayList<Float> list) {
		float sum = 0.f;
		for (int i = 0; i < list.size(); i++)
			sum += list.get(i);
		return sum / list.size();
	}
}
