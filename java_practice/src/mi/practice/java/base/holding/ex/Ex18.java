package mi.practice.java.base.holding.ex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Ex18 {
	public static void main(String[] args){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(12, "one");
		map.put(23, "two");
		map.put(34, "three");
		map.put(45, "four");
		map.put(56, "five");
		map.put(67, "six");
		map.put(78, "seven");
		System.out.println(map);
		
//		List<Integer> keys = new ArrayList<Integer>(map.keySet());
//		Collections.sort(keys);
//		Iterator<Integer> it = keys.iterator();
		
		Set<Integer> sortedSet = new TreeSet<Integer>(map.keySet());
		Iterator<Integer> it = sortedSet.iterator();
		
		LinkedHashMap<Integer, String> linkedMap= new LinkedHashMap<Integer, String>();
		while(it.hasNext()){
			Integer key =it.next();
			linkedMap.put(key, map.get(key));
		}
		System.out.println(linkedMap);
		
	}
}
