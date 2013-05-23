package mi.practice.java.base.containers;

import java.util.LinkedHashMap;

import mi.practice.java.base.util.CountingMapData;
/**
 * LRU顺序, 最近使用的元素会被放到队列的最末端.
 * 最前面的是上次使用时间间隔最大的.
 * 每次使用过的元素会被移动到末端.
 */
public class LinkedHashMapDemo {
	public static void main(String[] args){
		LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<Integer, String>(new CountingMapData(25));
		System.out.println(linkedHashMap);
		// 第三个参数, true代表LRU顺序, false代表新增顺序
		linkedHashMap = new LinkedHashMap<Integer, String>(16, 0.75f, true);
		linkedHashMap.putAll(new CountingMapData(9));
		System.out.println(linkedHashMap);
		for(int i=0 ;i<6;i++){
			linkedHashMap.get(i);
		}
		System.out.println(linkedHashMap);
		linkedHashMap.get(0);
		System.out.println(linkedHashMap);
		linkedHashMap.get(6);
		System.out.println(linkedHashMap);
	}
}