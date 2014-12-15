package share;

public class ArrayTools {

	// return the index of max value
	public static int indexOfMax(int[] array) {
		int maxValue = Integer.MIN_VALUE;
		int maxIndex = -1;
		for (int i = 0; i < array.length; i++)
			if (array[i] > maxValue) {
				maxValue = array[i];
				maxIndex = i;
			}
		return maxIndex;
	}
}
