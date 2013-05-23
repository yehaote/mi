package mi.practice.java.base.arrays;

import java.util.Arrays;
/**
 * 慢慢进行初始化, 
 * 一点点分配
 * @author waf
 */
public class AssemblingMultidimensionArrays {
	public static void main(String[] args){
		Integer[][] a;
		a = new Integer[3][];
		for(int i=0;i<a.length;i++){
			a[i] = new Integer[3];
			for(int j=0;j<a[i].length;j++){
				a[i][j] = i*j; //自动装箱
			}
		}
		System.out.println(Arrays.deepToString(a));
	}
}
