package mi.practice.java.base.holding;

import java.util.Set;
import java.util.TreeSet;

import mi.practice.java.base.util.TextFile;
/**
 * 如果想不区分大小写字母排序, 可以在构造treeSet的时候
 * 传入String.CASE_INSENSITIVE_ORDER
 * TreeSet接受Comparator接口
 */
public class UniqueWordsAlphabetic {
	public static void main(String[] args) {
		Set<String> words = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
		words.addAll(new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+"));
		System.out.println(words);
	}
}
