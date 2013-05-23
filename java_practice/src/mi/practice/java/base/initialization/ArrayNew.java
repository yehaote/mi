package mi.practice.java.base.initialization;

import java.util.Arrays;
import java.util.Random;

public class ArrayNew {
	public static void main(String[] args){
		int a[];
		Random random = new Random(47);
		a = new int[random.nextInt(20)];
		System.out.println("length of a = "+a.length);
		System.out.println(Arrays.toString(a));
	}
}
