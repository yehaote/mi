package mi.practice.java.base.arrays;

import java.util.Arrays;
import java.util.Comparator;

import mi.practice.java.base.util.Generated;

/**
 * 这个Comparator使用CompType的j来进行排序
 * @author waf
 */
class CompTypeComparator implements Comparator<CompType>{
	@Override
	public int compare(CompType o1, CompType o2) {
		return (o1.j < o2.j ? -1 : (o1.j == o2.j ? 0 : 1));
	}
}
public class ComparatorTest {
	public static void main(String[] args){
		CompType[] a = Generated.array(new CompType[15], CompType.generator());
		System.out.println("Before Sorting:");
		System.out.println(Arrays.toString(a));
		Arrays.sort(a, new CompTypeComparator());
		System.out.println("After Sorting:");
		System.out.println(Arrays.toString(a));
	}
}
