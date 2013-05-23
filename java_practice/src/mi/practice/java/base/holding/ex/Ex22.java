package mi.practice.java.base.holding.ex;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mi.practice.java.base.util.TextFile;
/**
 * Hash集合在比较的时候会先比较对象的hashcode(),
 * 在用equals()判断
 */
class WordCount{
	private String word;
	private int count;
	public WordCount(String word, int count){
		this.word=word;
		this.count=count;
	}
	public WordCount(String word){
		this(word, 1);
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	@Override
	public int hashCode(){
		return 1;
	}
	@Override
	public boolean equals(Object anObject){
		if (this == anObject) {
		    return true;
		}
		if (anObject instanceof WordCount) {
			boolean result =((WordCount) anObject).getWord().equals(getWord());
			if(result){
				count++;
				((WordCount) anObject).setCount(((WordCount)anObject).count+1);
			}
			return result;
		}
		return false;
	}
	@Override
	public String toString(){
		return word+":"+count;
	}
}
public class Ex22 {
	public static void main(String[] args){
		List<String> list = new TextFile(
				"src/mi/practice/java/base/holding/SetOperations.java", "\\W+");
		System.out.println(list);
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		System.out.println(list);
		
		Set<WordCount> set = new HashSet<WordCount>();
		for(String s:list){
			WordCount wc = new WordCount(s);
			set.add(wc);
		}
		System.out.println(set);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String s : list) {
			map.put(s, map.get(s) == null ? 1 : map.get(s) + 1);
		}
		System.out.println(map);
	}
}