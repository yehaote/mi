package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;
/**
 * In java generic,
 * can't use a primitives as type parameters.
 * If performance is a problem, 
 * you can use some other open source version.
 * for example : apache.collections
 * @author waf
 */
public class ListOfInt {
	public static void main(String[] args){
		List<Integer> li = new ArrayList<Integer>();
		for(int i=0;i<5;i++){
			li.add(i);
		}
		for(int i: li){
			System.out.println(i);
		}
	}
}
