package mi.practice.java.base.arrays;

import java.util.Arrays;
/**
 * Java的多维数组
 * @author waf
 */
public class MultidimensionalPrimitiveArray {
	public static void main(String[] args){
		int[][] a={
				{1,2,3},
				{4,5,6}
		};
		// 打印多维数组需要用Arrays.deepToString()
		System.out.println(Arrays.deepToString(a));
	}
}
