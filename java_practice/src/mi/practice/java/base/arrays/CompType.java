package mi.practice.java.base.arrays;

import java.util.Arrays;
import java.util.Random;

import mi.practice.java.base.util.Generated;
import mi.practice.java.base.util.Generator;
/**
 * 元素间的比较可以通过实现Comparable接口来实现, 
 * 一般来说大于返回正值, 0代表相等, 负数代表小于.
 * Arrays.sort()可以对数组里的元素进行排序,
 * 如果元素是对象类型而且没有实现Comparable接口,
 * 调用Arrays.sort()方法会抛出异常说类型不能转换.
 * @author waf
 */
public class CompType implements Comparable<CompType>{
	int i;
	int j;
	private static int count=1;
	public CompType(int n1, int n2){
		i=n1;
		j=n2;
	}
	@Override
	public String toString(){
		String result ="[ i= "+i+", j = "+j+"]";
		if(count++ %3 == 0){
			result+="\n";
		}
		return result;
	}
	@Override
	public int compareTo(CompType o) {
		return (i < o.i ? -1 :(i==o.i ? 0:1));
	}
	private static Random rand = new Random(47);
	public static Generator<CompType> generator(){
		return new Generator<CompType>() {
			@Override
			public CompType next() {
				return new CompType(rand.nextInt(100), rand.nextInt(100));
			}
		};
	}
	
	public static void main(String[] args){
		CompType[] a = Generated.array(new CompType[12], generator());
		System.out.println("before sorting:");
		System.out.println(Arrays.toString(a));
		Arrays.sort(a);
		System.out.println("after sorting:");
		System.out.println(Arrays.toString(a));
	}
}
