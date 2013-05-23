package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;
/**
 * 用<?>代替什么都不使用, 
 * 更加清楚的说明在这里就是要使用rawtype
 * @author waf
 */
public class UnboundedWildcards1 {
	@SuppressWarnings("rawtypes")
	static List list1;
	static List<?> list2;
	static List<? extends Object> list3;
	@SuppressWarnings("rawtypes")
	static void assign(List list){
		list1 = list;
		list2 = list;
//		list3 = list; // Warning: unchecked conversion
	}
	static void assign2(List<?> list){
		list1 = list;
		list2 = list;
		list3 = list;
	}
	static void assign3(List<? extends Object> list){
		list1 = list;
		list2 = list;
		list3 = list;
	}
	@SuppressWarnings("rawtypes")
	public static void main(String[] args){
		assign(new ArrayList());
		assign2(new ArrayList());
//		assign3(new ArrayList()); // Warning: unchecked conversion
		assign(new ArrayList<String>());
		assign2(new ArrayList<String>());
		assign3(new ArrayList<String>());
		List<?> wildList =new ArrayList();
		wildList= new ArrayList<String>();
		assign(wildList);
		assign2(wildList);
		assign3(wildList);
	}
}
