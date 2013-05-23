package mi.practice.java.base.containers;

import static mi.practice.java.base.util.Print.*;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import mi.practice.java.base.util.CountingMapData;
/**
 * 对map的key_Set和value_Collections进行操作的时候会改变map
 */
public class Maps {
	public static void printKeys(Map<Integer, String> map){
		printnb("Size ="+map.size()+",");
		printnb("Key: ");
		print(map.keySet()); // 获取所有的key
	}
	
	public static void test(Map<Integer, String> map){
		print(map.getClass().getSimpleName());
		map.putAll(new CountingMapData(25));
		// Map has 'Set' behavior for keys:
		map.putAll(new CountingMapData(25));
		printKeys(map);
		// Producing a collection of the values:
		printnb("Values: ");
		print(map.values());
		print(map);
		print("map.containsKey(11): "+map.containsKey(11));
		print("map.get(11): "+map.get(11));
		print("map.ContainsValue(\"F0\"): "+map.containsValue("F0"));
		Integer key = map.keySet().iterator().next();
		print("First key in map: "+key);
		map.remove(key);
		printKeys(map);
		map.clear();
		print("map.isEmpty(): "+map.isEmpty());
		map.putAll(new CountingMapData(25));
		// Operations on the Set change the map:
		// 在set进行的操作会改变map
		map.keySet().removeAll(map.keySet());
		print("map.isEmpty(): "+map.isEmpty());
	}
	
	public static void main(String[] args){
		test(new HashMap<Integer, String>());
		test(new TreeMap<Integer, String>());
		test(new LinkedHashMap<Integer, String>());
		test(new IdentityHashMap<Integer, String>());
		test(new ConcurrentHashMap<Integer, String>());
		test(new WeakHashMap<Integer, String>());
	}
}
