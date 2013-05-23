package mi.practice.java.base.arrays;

import java.util.Arrays;

import mi.practice.java.base.util.Generated;
import mi.practice.java.base.util.RandomGenerator;
/**
 * 当对对象数组进行binarySearch的时候,一定要采用一样的Comparator,
 * 不然也会导致不可预期的结果.
 * @author waf
 */
public class AlphabeticSearch {
	public static void main(String[] args) {
		String[] sa = Generated.array(new String[30],
				new RandomGenerator.String());
		Arrays.sort(sa, String.CASE_INSENSITIVE_ORDER);
		System.out.println(Arrays.toString(sa));
		// int index = Arrays.binarySearch(sa, sa[10]);
		// 注意: 一定要跟Sort的时候采用一样的Comparator
		int index = Arrays.binarySearch(sa, sa[10], 
				String.CASE_INSENSITIVE_ORDER);
		System.out.println("Index: " + index + "\n" + sa[index]);
	}
}
