package mi.practice.java.base.holding;

import java.util.Arrays;
/**
 * foreach循环可以对所有实现Iterable接口的使用,
 * 也可以对数组进行使用, 但是这不表明数组实现了Iterable接口,
 * 在对数组进行foreach的时候也没有自动装箱
 */
public class ArrayIsNotIterable {
	static <T> void test(Iterable<T> ib){
		for(T t:ib){
			System.out.print(t+" ");
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		test(Arrays.asList(1,2,3));
		String[] strings={"A","B","C"};
//		test(strings);
		test(Arrays.asList(strings));
	}
}
