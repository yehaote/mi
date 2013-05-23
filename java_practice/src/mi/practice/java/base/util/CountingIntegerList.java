package mi.practice.java.base.util;

import java.util.AbstractList;
import java.util.List;
/**
 * 为了构造一个只读的List, 可以继承AbstractList然后实现size()和get()方法
 * 值并不是CountingIntegerList初始化之后就生产完毕的,
 * 而是在get的时候生成的.
 */
public class CountingIntegerList extends 
	AbstractList<Integer>{
	private int size;
	public CountingIntegerList(int size){
		this.size = size < 0 ? 0 : size;
	}
	@Override
	public Integer get(int index) {
		return Integer.valueOf(index);
	}
	@Override
	public int size() {
		return size;
	}
	
	public static void main(String[] args){
		List<Integer> list=new CountingIntegerList(30);
//		list.add(100); // 不支持添加元素
		System.out.println(list);
	}
}
