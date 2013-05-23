package mi.practice.java.base.operators;

import java.util.Random;

/**
 * 展示boolean操作符
 * 在Java中对非boolean类型的数进行&&,||,!是非法的
 */
public class Bool {
	public static void main(String[] args) {
		Random rand = new Random(7);
		int i = rand.nextInt(100);
		int j = rand.nextInt(100);
		System.out.println(" i = " + i);
		System.out.println(" j = " + j);
		System.out.println(" i > j is " + (i > j));
		System.out.println(" i < j is " + (i < j));
		System.out.println(" i >= j is " + (i >= j));
		System.out.println(" i <= j is " + (i <= j));
		System.out.println(" i == j is " + (i == j));
		System.out.println(" i != j is " + (i != j));
		//System.out.println(" i && j is "+(i&&j)); 
		//System.out.println(" i || j is "+(i||j));
		//System.out.println(" !i is "+(!i));
		System.out.println(" (i<10) && (j<10) is "+((i<10) && (j<10)));
		System.out.println(" (i<10) || (j<10) is "+((i<10) || (j<10)));
		System.out.println(" !(i<10) is "+!(i<10));
	}
}
