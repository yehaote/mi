package mi.practice.java.base.holding.ex;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;


public class Ex24 {
	public static void main(String[] args){
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("hello", 1);
		map.put("so so ", 2);
		map.put("Jack", 3);
		map.put("about", 4);
		map.put("Lucene", 5);
		System.out.println(map);
		TreeMap<String, Integer> sortedMap = new TreeMap<String, Integer>(map);
		System.out.println(sortedMap);
		map.clear();
		for(Entry<String, Integer> entry:sortedMap.entrySet()){
			map.put(entry.getKey(), entry.getValue());
		}
		System.out.println(map);
	}
}
