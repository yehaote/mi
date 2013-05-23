package mi.practice.java.base.arrays;

import java.util.Arrays;

import mi.practice.java.base.util.ConvertTo;
import mi.practice.java.base.util.Generated;
import mi.practice.java.base.util.Generator;
import mi.practice.java.base.util.RandomGenerator;
/**
 * 使用Arrays可以对值进行查询, 
 * 但是一定要是sort以后的数组,
 * 不然的话结果将是不可预期的.
 * @author waf
 */
public class ArraySearching {
	public static void main(String[] args) {
		Generator<Integer> gen = new RandomGenerator.Integer(1000);
		int[] a = ConvertTo.prmitive(Generated.array(new Integer[25], gen));
		Arrays.sort(a); // must be sorted
		System.out.println("Sorted array:");
		System.out.println(Arrays.toString(a));
		while (true) {
			int r = gen.next();
			int location = Arrays.binarySearch(a, r); // find by value, 折半查询
			if (location >= 0) { // 如果不到的话, location会返回赋值且<=-1
				System.out.println("location of " + r + " is " + location
						+ ", a[" + location + "] = " + a[location]);
				break;
			}
		}
	}
}
