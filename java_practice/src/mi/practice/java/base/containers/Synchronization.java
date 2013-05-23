package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
/**
 * Collections.synchronized*还可以返回同步的Container,
 * 语法和用法跟Collections.unmodifiable*差不多.
 * 见ReadOnly.java
 */
public class Synchronization {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		Collection<String> c =Collections.synchronizedCollection(new ArrayList<String>());
		List<String> list = Collections.synchronizedList(new ArrayList<String>());
		Set<String> set = Collections.synchronizedSet(new HashSet<String>());
		Set<String> sset = Collections.synchronizedSortedSet(new TreeSet<String>());
		Map<String, String> m = Collections.synchronizedMap(new HashMap<String, String>());
		Map<String, String> sm = Collections.synchronizedSortedMap(new TreeMap<String, String>());
	}
}
