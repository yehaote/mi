package mi.practice.java.base.util;
/**
 * 产生int数组, 用于控制循环数
 */
public class Range {
	
	public static int[] range(int n) {
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = i;
		}
		return result;
	}

	public static int[] range(int start, int end) {
		int sz = end - start;
		int[] result = new int[sz];
		for (int i = 0; i < sz; i++) {
			result[i] = start + i;
		}
		return result;
	}

	public static int[] range(int start, int end, int step) {
		int sz = (end - start) / step;
		int[] result = new int[sz];
		for (int i = 0; i < sz; i++) {
			result[i] = start + (step * i);
		}
		return result;
	}
}
