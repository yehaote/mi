package mi.practice.java.base.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java中的数组跟一般的容器相比, 最大的区别是:
 * 1. 数组中可以存放基本类型, 容器只能存放Object
 * 2. 数组都是固定大小的
 * 3. 数组的效率要高
 * 
 * 容器类相比较数组要方便很多,
 * 不过数组更加具有性能上的优势.
 * @author waf
 */
class BerylliumSphere{
	private static long counter;
	private final long id = counter++;
	@Override
	public String toString(){
		return "Sphere "+id;
	}
}

public class ContainerComparison {
	public static void main(String[] args){
		BerylliumSphere[] spheres = new BerylliumSphere[10];
		for(int i=0;i<5;i++){
			spheres[i]=new BerylliumSphere();
		}
		System.out.println(Arrays.toString(spheres));
		System.out.println(spheres[4]);
		
		List<BerylliumSphere> sphereList = new ArrayList<BerylliumSphere>();
		for(int i = 0; i<5;i++){
			sphereList.add(new BerylliumSphere());
		}
		System.out.println(sphereList);
		System.out.println(sphereList.get(4));
		
		int[] integers= {0,1,2,3,4,5};
		System.out.println(Arrays.toString(integers));
		System.out.println(integers[4]);
		
		List<Integer> intList = new ArrayList<Integer>(Arrays.asList(0,1,2,3,4,5));
		intList.add(97);
		System.out.println(intList);
		System.out.println(intList.get(4));
		
	}
}
