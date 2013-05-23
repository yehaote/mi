package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;
/**
 * 泛型没有办法实例化数组, 
 * 可以使用List来代替
 * @author waf
 * @param <T>
 */
public class ListOfGenerics<T> {
	private List<T> array = new ArrayList<T>();
	public void add(T item){
		array.add(item);
	}
	public T get(int index){
		return array.get(index);
	}
}
