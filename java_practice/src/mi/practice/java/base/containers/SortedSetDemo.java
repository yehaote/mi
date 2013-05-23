package mi.practice.java.base.containers;

import java.util.Collections;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
/**
 * SortedSet接口比Set方法多了以下方法
 * comparator()
 * subSet(E, E)
 * headSet(E) // 从头取到指定元素位置
 * tailSet(E) // 从指定元素位置取到尾
 * first() // 第一个
 * last() // 最后一个
 * 在
 */
public class SortedSetDemo {
	public static void main(String[] args){
		SortedSet<String> sortedSet = new TreeSet<String>();
		Collections.addAll(sortedSet, "one two three four five six seven eight".split(" "));
		System.out.println(sortedSet);
		String low = sortedSet.first();
		String high = sortedSet.last();
		System.out.println(low);
		System.out.println(high);
		Iterator<String> it = sortedSet.iterator();
		for(int i=0; i<=6; i++){
			if(i==3){
				low=it.next();
			}
			if(i==6){
				high=it.next();
			}else{
				it.next();
			}
		}
		System.out.println(low);
		System.out.println(high);
		System.out.println(sortedSet.subSet(low, high)); //包含前一个, 不包含后一个
		System.out.println(sortedSet.headSet(high)); // 不包含后一个
		System.out.println(sortedSet.tailSet(low));	//包含前一个
	}
}
