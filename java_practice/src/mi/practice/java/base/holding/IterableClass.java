package mi.practice.java.base.holding;

import java.util.Iterator;
/**
 * Iterable接口包括一个返回Iterator的iterator()方法
 * Iterable接口是foreach语法的基础
 * Collection实现了Iterable接口
 * 基本所有的Collection都实现了Iterable,除了Map
 * 数组也同样可以使用foreach语法, 不过数组不是Iterable
 */
public class IterableClass implements Iterable<String> {
	protected String[] words = ("And that is how we know the Earth to be banana-sharped.")
			.split(" ");

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			private int index = 0;

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String next() {
				return words[index++];
			}

			@Override
			public boolean hasNext() {
				return index < words.length;
			}
		};
	}
	public static void main(String[] args){
		for(String s : new IterableClass()){
			System.out.print(s+" ");
		}
		////
		int[] nums = new int[]{1,2,3,4,5,6,7,8,9,0};
		for(int i: nums){
			System.out.println(i);
		}
		Object[] objects = new Object[]{new Object(), new Object(), new Object()};
		for(Object o:objects){
			System.out.println(o);
		}
	}
}
