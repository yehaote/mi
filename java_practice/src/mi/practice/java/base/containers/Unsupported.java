package mi.practice.java.base.containers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在java中会包含一些可选的实现方法,
 * 这代表着当你实现一个接口的时候可以不实现这些方法, 
 * 一般的做法是抛出一个UnsupportedOperationException.
 * 在Collection中所有"读"的方法都不是可选的.
 * 传入一个数组来构造Collection的时候,
 * 这个Collection相当于一个fix-size的数组.
 * 而当使用unmodifiableList来实例化的时候, 
 * 它是一个不可更改的数组, 不仅仅包括它的size,
 * 还有它的元素.
 */
public class Unsupported {
	static void test(String msg, List<String> list){
		System.out.println("--- "+msg+" ---");
		Collection<String> c = list;
		Collection<String> subList = list.subList(1, 8);
		// 拷贝subList
		Collection<String> c2 = new ArrayList<String>(subList);
		try{
			// 会删除元素, 从而改变数组大小
			c.retainAll(c2);
		}catch (Exception e) {
			System.out.println("retainAll() "+e);
		}
		try{
			// 会删除元素, 从而改变数组大小
			c.removeAll(c2);
		}catch (Exception e) {
			System.out.println("removeAll() "+e);
		}
		try{
			// 会删除元素, 从而改变数组大小
			c.clear();
		}catch (Exception e) {
			System.out.println("clear() "+e);
		}
		try{
			// 会增加元素, 从而改变数组大小
			c.add("X");
		}catch (Exception e) {
			System.out.println("add() "+e);
		}
		try{
			// 会增加元素, 从而改变数组大小
			c.addAll(c2);
		}catch (Exception e) {
			System.out.println("addAll() "+e);
		}
		try{
			// 会删除元素, 从而改变数组大小
			c.remove("C");
		}catch (Exception e) {
			System.out.println("remove() "+e);
		}
		try{
			// 修改地一个元素的值, 而不改变大小
			// 不改变数组大小
			list.set(0, "X");
		}catch (Exception e) {
			System.out.println("set() "+e);
		}
	}
	
	public static void main(String[] args){
		List<String> list = Arrays.asList("A B C D E F G H I J K L".split(" "));
		test("Modifiable Copy", new ArrayList<String>(list));
		test("Array.asList()", list);
		test("Unmodifiable List", Collections.unmodifiableList(list));
	}
}
