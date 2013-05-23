package mi.practice.java.base.util;

import java.util.HashSet;
import java.util.Set;

public class Sets {
	/**
	 * 并集
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> union(Set<T> a, Set<T> b){
		Set<T> result =new HashSet<T>(a);//拷贝, 原数据不做修改
		a.addAll(b);
		return result;
	}
	/**
	 *  交集
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> intersection(Set<T> a, Set<T> b){
		Set<T> result=new HashSet<T>(a);
		result.retainAll(b);
		return result;
	}
	
	/**
	 * 集合删去另一个集合
	 * @param superset
	 * @param subSet
	 * @return
	 */
	public static <T> Set<T> difference(Set<T> superset, Set<T> subset){
		Set<T> result = new HashSet<T>(superset);
		result.removeAll(subset);
		return result;
	}
	
	/**
	 * 两个集合除去交集的部分
	 * @param a
	 * @param b
	 * @return
	 */
	public static <T> Set<T> complement(Set<T> a, Set<T> b){
		return difference(union(a,b), intersection(a, b));
	}
}
