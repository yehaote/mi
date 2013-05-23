package mi.practice.java.base.holding;

import java.util.Set;
import java.util.TreeSet;

import mi.practice.java.base.util.TextFile;

public class UniqueWords {
	public static void main(String[] args) {
		Set<String> words = new TreeSet<String>(new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+"));
		System.out.println(words);
	}
}