package mi.practice.java.base.arrays;

import java.util.Arrays;
/**
 * 对象的多维数组
 * @author waf
 */
public class MultidimensionalObjectArrays {
	public static void main(String[] args){
		BerylliumSphere[][] spheres ={
				{ new BerylliumSphere(), new BerylliumSphere() },
				{ new BerylliumSphere(), new BerylliumSphere(),
				  new BerylliumSphere(), new BerylliumSphere() },
				{ new BerylliumSphere(), new BerylliumSphere(),
				  new BerylliumSphere(), new BerylliumSphere(),
				  new BerylliumSphere(), new BerylliumSphere(),
				  new BerylliumSphere(), new BerylliumSphere() }
				
		};
		System.out.println(Arrays.deepToString(spheres));
	}
}
