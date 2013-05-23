package mi.practice.java.base.generics;

import mi.practice.java.base.util.Generator;
import mi.practice.java.base.util.RandomGenerator;
/**
 * 基本类型数组还是不好返回
 * @author waf
 */

// fill a array use a generator
class FArray{
	public static <T> T[] fill(T[] a, Generator<T> gen){
		for(int i=0; i<a.length; i++){
			a[i]=gen.next();
		}
		return a;
	}
}

public class PrimitiveGenericTest {
	public static void main(String[] args){
		String[] strings = FArray.fill(new String[7], new RandomGenerator.String(10));
		for(String s: strings){
			System.out.println(s);
		}
		Integer[] integers = FArray.fill(new Integer[7], new RandomGenerator.Integer());
		for(Integer i: integers){
			System.out.println(i);
		}
//		int[] b=FArray.fill(new int[7], new RandomGenerator.Integer()); // can't compile
	}
}
