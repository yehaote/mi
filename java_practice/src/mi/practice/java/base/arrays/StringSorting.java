package mi.practice.java.base.arrays;

import java.util.Arrays;
import java.util.Collections;

import mi.practice.java.base.util.Generated;
import mi.practice.java.base.util.RandomGenerator;

/**
 * 使用内置的排序方法可以对所有的基本类型进行排序,
 * 或者是实现了Comparable接口的对象, 或者是提供一个Comparator.
 * 对基本类型, java内置方法使用Quicksort,
 * 对于对象, 使用stable merge sort
 * @author waf
 */
public class StringSorting {
	public static void main(String[] args){
		String[] sa = Generated.array(new String[20], new RandomGenerator.String(5));
		System.out.println("Before sortring:");
		System.out.println(Arrays.toString(sa));
		Arrays.sort(sa); // 一般排序, 字典顺序排序, 大写字母比小写字母在前A-Z, a-z
		System.out.println("After sorting:");
		System.out.println(Arrays.toString(sa));
		Arrays.sort(sa, Collections.reverseOrder()); // 反转排序 z-a,Z-A
		System.out.println("Reverse sorting:");
		System.out.println(Arrays.toString(sa));
		Arrays.sort(sa, String.CASE_INSENSITIVE_ORDER); // 大小写不敏感, 排序 aA-zZ
		System.out.println("Reverse sorting:");
		System.out.println(Arrays.toString(sa));
	}
}
