package mi.practice.java.base.control;

import java.util.Random;

/**
 * for each 循环为你迭代出集合中的所有元素
 * for each 可以对数组进行操作, 或者实现Iterable的对象
 */
public class ForEachFloat {

	public static void main(String[] args) {
		Random rand = new Random(47);
		float f[] = new float[10];
		for (int i = 0; i < 10; i++) {
			f[i] = rand.nextFloat();
		}
		for (float x : f) {
			System.out.println(x);
		}
	}

}
