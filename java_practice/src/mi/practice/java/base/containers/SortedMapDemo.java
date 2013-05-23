package mi.practice.java.base.containers;

import java.util.Iterator;
import java.util.TreeMap;

import mi.practice.java.base.util.CountingMapData;
/**
 * SortedMap在java内置中好像只有TreeMap,
 * TreeMap是基于红黑树实现的.
 * SortedMap, 可以获取第一个最后一个key,
 * 还可以用key进行sub
 */
public class SortedMapDemo {
	public static void main(String[] args) {
		TreeMap<Integer, String> sortedMap = new TreeMap<Integer, String>(
				new CountingMapData(25));
		System.out.println(sortedMap);
		Integer low = sortedMap.firstKey();
		Integer high = sortedMap.lastKey();
		System.out.println(low);
		System.out.println(high);
		Iterator<Integer> it = sortedMap.keySet().iterator();
		for(int i=0;i<=6; i++){
			if(i==3){
				low = it.next();
			}
			if(i==6){
				high=it.next();
			}else{
				it.next();
			}
		}
		System.out.println(low);
		System.out.println(high);
		System.out.println(sortedMap.subMap(low, high));
		System.out.println(sortedMap.headMap(high));
		System.out.println(sortedMap.tailMap(low));
	}
}
