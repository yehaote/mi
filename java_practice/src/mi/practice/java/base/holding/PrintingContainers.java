package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import static mi.practice.java.base.util.Print.*;

public class PrintingContainers {
	@SuppressWarnings("rawtypes")
	static Collection fill(Collection<String> collection) {
		System.out.println("collection");
		collection.add("rat");
		collection.add("cat");
		collection.add("dog");
		collection.add("dog");
		return collection;
	}
	@SuppressWarnings("rawtypes")
	static Map fill(Map<String, String> map) {
		System.out.println("map");
		map.put("rat", "Fuzzy");
		map.put("cat", "Rags");
		map.put("dog", "Bosco");
		map.put("dog", "Spot");
		return map;
	}

	public static void main(String[] args) {
		//list
		print(fill(new ArrayList<String>()));
		print(fill(new LinkedList<String>()));
		//set
		print(fill(new HashSet<String>()));
		print(fill(new TreeSet<String>()));
		print(fill(new LinkedHashSet<String>()));
		//map
		print(fill(new HashMap<String, String>()));
		print(fill(new TreeMap<String, String>()));
		print(fill(new LinkedHashMap<String, String>()));
	}
}
