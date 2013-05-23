package mi.practice.java.base.generics;

import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("rawtypes")
public class UnboundedWildcards2 {
	static Map map1;
	static Map<?,?> map2;
	static Map<String, ?> map3;
	static void assign1(Map map){
		map1=map;
	}
	static void assign2(Map<?,?> map){
		map2=map;
	}
	static void assign3(Map<String,?> map){
		map3=map;
	}
	@SuppressWarnings("unchecked")
	public static void main(String[] args){
		assign1(new HashMap());
		assign2(new HashMap());
		assign3(new HashMap());// warning: unchecked conversion
		assign1(new HashMap<String,Integer>());
		assign2(new HashMap<String,Integer>());
		assign3(new HashMap<String,Integer>());
	}
}
