package mi.practice.java.base.arrays;

import java.util.ArrayList;
import java.util.List;
/**
 * 初始化数组的时候是没有办法跟泛型一起使用的, 
 * 只有在实例化rawtype以后再进行强转
 * @author waf
 */
public class ArrayOfGenerics {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args){
		List<String>[] ls;
		List[] la = new List[10];
		ls = (List<String>[]) la; // Unchecked
		ls[0] = new ArrayList<String>();
//		ls[1] = new ArrayList<Integer>(); // error
		
		// List<String> is a subtype of object
		Object[] objects = ls;
		// Compiles and run without complaint
		objects[1] = new ArrayList<Integer>();
		
		List<BerylliumSphere>[] spheres =(List<BerylliumSphere>[])new List[10];
		for(int i = 0; i < spheres.length; i++){
			spheres[i] = new ArrayList<BerylliumSphere>();
		}
	}
}
