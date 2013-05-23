package mi.practice.java.base.containers;

import java.util.Map;
/**
 * 
 */
public class MapEntry<K,V> implements Map.Entry<K, V>{
	private K key;
	private V value;
	
	public MapEntry(K key, V value){
		this.key=key;
		this.value=value;
	}
	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V result=this.value;
		this.value= value;
		return result;
	}
	
	@Override
	public int hashCode(){
		return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
	}
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof MapEntry)){
			return false;
		}
		@SuppressWarnings("rawtypes")
		MapEntry me = (MapEntry)o;
		return (key == null ? (me.getKey() == null) : (key.equals(me.getKey())))
				&& (value == null ?
						(me.getValue() == null) : (value.equals(me.getValue())));
	}
	
	@Override
	public String toString(){
		return key+"="+value;
	}
}
