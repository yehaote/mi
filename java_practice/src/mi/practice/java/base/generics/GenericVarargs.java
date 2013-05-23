package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;
/**
 * 泛型函数和可变参数可以很好地结合在一起
 * @author waf
 */
public class GenericVarargs {
	public static <T> List<T> makeList(T... args){
		List<T> result = new ArrayList<T>();
		for(T item:args){
			result.add(item);
		}
		return result;
	}
	
	public static void main(String[] args){
		List<String> nullList = makeList();
		System.out.println(nullList);
		List<String> ls = makeList("A");
		System.out.println(ls);
		ls=makeList("A","B","C");
		System.out.println(ls);
		//分割
		ls=makeList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
		System.out.println(ls);
	}
}
