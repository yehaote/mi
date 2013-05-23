package mi.practice.java.base.containers;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import mi.practice.java.base.util.Countries;

public class SimpleHashMap<K,V> extends AbstractMap<K, V>{
	// hash表大小
	static final int SIZE = 997;
	
	// 用链表来作为hash表的元素,
	// 一个链表的数组就是一个hash表
	@SuppressWarnings("unchecked")
	LinkedList<MapEntry<K,V>>[] buckets = new LinkedList[SIZE];
	
	
	@Override
	public V put(K key, V value){
		V oldValue = null;
		// 计算索引位置
		int index = Math.abs(key.hashCode())%SIZE;
		if(buckets[index] == null){
			buckets[index] = new LinkedList<MapEntry<K,V>>();
		}
		LinkedList<MapEntry<K,V>> bucket = buckets[index];
		MapEntry<K,V> pair = new MapEntry<K,V>(key, value);
		boolean found = false;
		ListIterator<MapEntry<K,V>> it = bucket.listIterator();
		while(it.hasNext()){
			MapEntry<K, V> iPair =it.next();
			if(iPair.getKey().equals(key)){
				oldValue=iPair.getValue();
				// 如果找到的话, 用新的替换老的
				it.set(pair);
				found=true;
				break;
			}
		}
		if(!found){
			buckets[index].add(pair);
		}
		return oldValue;
	}
	
	@Override
	public V get(Object key){
		int index = Math.abs(key.hashCode())%SIZE;
		if(buckets[index] == null){
			return null;
		}
		for(MapEntry<K,V> iPair:buckets[index]){
			if(iPair.getKey().equals(key)){
				return iPair.getValue();
			}
		}
		return null;
	}
		
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		Set<Map.Entry<K, V>> set  = new HashSet<Map.Entry<K,V>>();
		for(LinkedList<MapEntry<K,V>> bucket : buckets){
			if(bucket == null){
				continue;
			}
			for(MapEntry<K,V> mpair : bucket){
				set.add(mpair);
			}
		}
		return set;
	}
	
	public static void main(String[] args){
		SimpleHashMap<String, String> m = new SimpleHashMap<String, String>();
		m.putAll(Countries.capitals(25));
		System.out.println(m);
		System.out.println(m.get("ERITREA"));
		System.out.println(m.keySet());
	}
}
