package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import static mi.practice.java.base.util.Print.*;

public class Utilities {
	static List<String> list = Arrays.asList("one Two three Four five Six one".split(" "));
	public static void main(String[] args){
		print(list);
		print("'list' disjoint (Four)?: "+Collections.disjoint(list, Collections.singletonList("Four")));
		print("max: "+Collections.max(list));
		print("min: "+Collections.min(list));
		print("max w/ comparator: "+Collections.max(list, String.CASE_INSENSITIVE_ORDER));
		print("min w/ comparator: "+Collections.min(list, String.CASE_INSENSITIVE_ORDER));
		List<String> subList= Arrays.asList("Four five Six".split(" "));
		print("indexOfSubList: "+Collections.indexOfSubList(list, subList));
		print("lastIndexOfSubList: "+Collections.lastIndexOfSubList(list, subList));
		Collections.replaceAll(list, "one", "Yo");
		print("replaceAll: "+list);
		Collections.reverse(list);
		print("reverse: "+list);
		Collections.rotate(list, 3);
		print("rotate: "+list);
		List<String> source = Arrays.asList("in the matrix".split(" "));
		Collections.copy(list, source);//拷贝不是放到最后, 而是跟数组拷贝一样, 直接替换
		print("copy: "+list);
		Collections.swap(list, 0, list.size()-1);
		print("swap: "+list);
		Collections.shuffle(list);
		print("shuffled: "+list);
		Collections.fill(list, "pop");//全部填充为一个值
		print("fill: "+list);
		print("frequency of 'pop': "+Collections.frequency(list, "pop"));
		List<String> dups = Collections.nCopies(3, "snap");// 一个值复制多次产生一个List
		print("dups: "+dups);
		print("'list' disjoint 'dups'?: "+Collections.disjoint(list, dups));
		// 获得一个旧式的Enumeration
		Enumeration<String> e = Collections.enumeration(dups);
		Vector<String> v = new Vector<String>();
		while(e.hasMoreElements()){
			v.add(e.nextElement());
		}
		ArrayList<String> arrayList = Collections.list(v.elements());
		print("arrayList: "+arrayList);
	}
}
