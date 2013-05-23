package mi.practice.java.base.holding;

import java.util.ArrayList;
import java.util.Collection;
/**
 * 一般定义java提供集合类的时候会使用接口引用,
 * 但是这种方式有不好的地方, 尤其是有一些实现类有接口中没有的方法
 * 比如:LinkedList有一些方法在List中是没有, TreeMap有一些方法在Map中是没有的
 */
public class SimpleCollection {
	public static void main(String[] args){
		Collection<Integer> c = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			c.add(i); //自动装箱
		}
		for(Integer i :c){
			System.out.print(i+" ");
		}
	}
}
