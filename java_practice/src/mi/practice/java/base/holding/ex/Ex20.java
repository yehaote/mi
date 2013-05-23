package mi.practice.java.base.holding.ex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import mi.practice.java.base.util.TextFile;

public class Ex20 {
	static Map<Character,Integer> vowelMap = new HashMap<Character,Integer>();
	static {
		vowelMap.put('a',0);
		vowelMap.put('e',0);
		vowelMap.put('i',0);
		vowelMap.put('o',0);
		vowelMap.put('u',0);
		vowelMap.put('A',0);
		vowelMap.put('E',0);
		vowelMap.put('I',0);
		vowelMap.put('O',0);
		vowelMap.put('U',0);
	}

	public static void countVowel(Set<String> set) {
		for (String s : set) {
			for (Character c : s.toCharArray()) {
				if (vowelMap.containsKey(c)) {
					vowelMap.put(c, vowelMap.get(c)+1);
				}
			}
		}
		System.out.println(vowelMap);
	}

	public static void main(String[] args) {
		Set<String> words = new TreeSet<String>(new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+"));
		countVowel(words);
	}
}
