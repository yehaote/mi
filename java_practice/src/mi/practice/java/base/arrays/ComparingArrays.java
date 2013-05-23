package mi.practice.java.base.arrays;

import java.util.Arrays;
/**
 * Arrays里提供方法用于判断相等, 
 * 当数组相等的时候需要的大小相等而且每一位元素也必须相等,
 * 元素之间的相等对象是使用.equals()来实现的,
 * 基本类型是值判断
 * 如果是多维数组需要使用deepEquals
 * @author waf
 */
public class ComparingArrays {
	public static void main(String[] args){
		int[] a1 = new int[10];
		int[] a2 = new int[10];
		Arrays.fill(a1, 47);
		Arrays.fill(a2, 47);
		System.out.println(Arrays.equals(a1, a2));
		a2[3]=11;
		System.out.println(Arrays.equals(a1, a2));
		String[] s1 = new String[4];
		Arrays.fill(s1, "Hi");
		String[] s2 = {new String("Hi"), new String("Hi"), new String("Hi"), new String("Hi")};
		System.out.println(Arrays.equals(s1, s2));
	}
}
