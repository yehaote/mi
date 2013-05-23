package mi.practice.java.base.arrays;

import java.util.Arrays;
/**
 * 在java中提供了一个native方法用于拷贝数组,
 * 无论是对象数组还是基本类型数组.
 * 这个方法拷贝的效率要比用for循环自己去拷贝要快.
 * System.arraycopy()
 * 在对对象数组进行拷贝的时候, 拷贝的仅仅是对象的引用, 
 * 这是一种浅拷贝.
 * System.arraycopy()不会自动装箱, 解箱,
 * 两个数组的类型必须相同
 * 比如拷贝一个int[]到一个Integer数组是不可以的.
 * @author waf
 */
public class CopyingArrays {
	public static void main(String[] args){
		//基本类型
		int[] i = new int[7];
		int[] j = new int[10];
		Arrays.fill(i, 47);
		Arrays.fill(j, 99);
		System.out.println("i = "+Arrays.toString(i));
		System.out.println("j = "+Arrays.toString(j));
		System.arraycopy(i, 0, j, 0, i.length);
		System.out.println("j = "+Arrays.toString(j));
		int[] k = new int[5];
		Arrays.fill(k, 103);
		System.arraycopy(i, 0, k, 0, k.length);
		System.out.println("k = "+Arrays.toString(k));
		Arrays.fill(k, 103);
		System.arraycopy(k, 0, i, 0, k.length);
		System.out.println("i = "+Arrays.toString(i));
		
		//对象
		Integer[] u = new Integer[10];
		Integer[] v = new Integer[5];
		Arrays.fill(u, new Integer(47));
		Arrays.fill(v, new Integer(99));
		System.out.println("u = "+Arrays.toString(u));
		System.out.println("v = "+Arrays.toString(v));
		System.arraycopy(v, 0, u, u.length/2, v.length);
		System.out.println("u = "+Arrays.toString(u));
	}
}
