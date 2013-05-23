package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static mi.practice.java.base.util.Print.*;

public class ListSortSearch {
	public static void main(String[] args){
		List<String> list = new ArrayList<String>(Utilities.list);
		list.addAll(Utilities.list);
		print(list);
		Collections.shuffle(list);
		print("Shuffled: "+list);
		// 使用ListIterator进行截断
		ListIterator<String> it = list.listIterator(10);
		while(it.hasNext()){
			it.next();
			it.remove();
		}
		print("Trimmed: "+list);
		Collections.sort(list);
		print("Sorted: "+list);
		String key = list.get(7);
		int index = Collections.binarySearch(list, key);
		print("Location of "+key+" is "+index+", list.get(" +index+") = "+list.get(index));
		Collections.sort(list,String.CASE_INSENSITIVE_ORDER);
		print("Case-insensitive sorted: "+list);
		// 当数组根据一个Comparator进行Sort的时候, binarySearch的时候也应该使用相同的Comparator
		key = list.get(7);
		index = Collections.binarySearch(list, key, String.CASE_INSENSITIVE_ORDER);
		print("Location of "+key+" is "+index+", list.get(" +index+") = "+list.get(index));
	}
}
