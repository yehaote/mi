package mi.practice.java.base.arrays;

import java.util.Arrays;
import java.util.Collections;

import mi.practice.java.base.util.Generated;
/**
 * 可以使用Arrays.sort(Object[], Collections.reverseOrder())
 * 来对对象数组进行反转排序, 
 * 这个方法只支持对象数组.
 * Collections.reverseOrder()是一个Comparator,
 * 也可以写自己的Comparator,
 * 包含两个方法, compare和equals,
 * 如果不是具体要使用到equals的时候, 可以不再重新实现这个方法, 
 * 因为对象本身就已经包含这个方法.
 * @author waf
 */
public class Reverse {
	public static void main(String[] args){
		CompType[] a = Generated.array(new CompType[12], CompType.generator());
		System.out.println("Before sorting:");
		System.out.println(Arrays.toString(a));
//		Arrays.sort(a);
		Arrays.sort(a, Collections.reverseOrder());
		System.out.println("After sorting:");
		System.out.println(Arrays.toString(a));
		
		int[] b = new int[12];
		System.out.println("Before sorting:");
		System.out.println(Arrays.toString(b));
		Arrays.sort(b);
//		Arrays.sort(b, Collections.reverseOrder()); // 反转排序这个方法只支持对对象的排序
		System.out.println("After sorting:");
		System.out.println(Arrays.toString(a));
	}
}
