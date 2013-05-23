package mi.practice.java.base.holding.ex;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import mi.practice.java.base.util.TextFile;

public class Ex16 {
	static Set<Character> vowelSet = new HashSet<Character>();
	static {
		vowelSet.add('a');
		vowelSet.add('e');
		vowelSet.add('i');
		vowelSet.add('o');
		vowelSet.add('u');
		vowelSet.add('A');
		vowelSet.add('E');
		vowelSet.add('I');
		vowelSet.add('O');
		vowelSet.add('U');
	}

	public static void countVowel(Set<String> set) {
		int totalVowel = 0;
		for (String s : set) {
			int vowel = 0;
			for (Character c : s.toCharArray()) {
				if (vowelSet.contains(c)) {
					vowel++;
					totalVowel++;
				}
			}
			System.out.println(s+" "+vowel);
		}
		System.out.println("TotalVowel: "+totalVowel);
	}

	public static void main(String[] args) {
		Set<String> words = new TreeSet<String>(new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+"));
		countVowel(words);
	}
}
