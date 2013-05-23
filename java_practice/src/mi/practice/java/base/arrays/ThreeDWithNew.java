package mi.practice.java.base.arrays;

import java.util.Arrays;
/**
 * 三维数组
 * @author waf
 */
public class ThreeDWithNew {
	public static void main(String[] args){
		int[][][] a = new int[2][2][4];
		System.out.println(Arrays.deepToString(a));
	}
}
