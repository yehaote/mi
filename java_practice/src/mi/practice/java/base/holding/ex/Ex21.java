package mi.practice.java.base.holding.ex;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mi.practice.java.base.util.TextFile;

public class Ex21 {
	public static void main(String[] args) {
		List<String> list = new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+");
		System.out.println(list);
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		System.out.println(list);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : list) {
			map.put(s, map.get(s) == null ? 1 : map.get(s) + 1);
		}
		System.out.println(map);
	}
}
