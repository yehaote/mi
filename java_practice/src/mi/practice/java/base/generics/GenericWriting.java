package mi.practice.java.base.generics;

import java.util.ArrayList;
import java.util.List;
/**
 * 好像没有问题了......
 * Thinking in java 落伍了?
 * 还是我没看清楚
 * @author waf
 */
public class GenericWriting {
	static <T> void writeExact(List<T> list, T item){
		list.add(item);
	}
	static <T> void writeWithWildcard(List<? super T> list, T item){
		list.add(item);
	}
	static List<Apple> apples=new ArrayList<Apple>();
	static List<Fruit> fruits = new ArrayList<Fruit>();
	static void f1(){
		writeExact(fruits, new Fruit());
		writeExact(fruits, new Apple());
//		writeExact(apples, new Fruit());
		writeExact(apples, new Apple());
	}
	static void f2(){
		writeWithWildcard(fruits, new Fruit());
		writeWithWildcard(fruits, new Apple());
//		writeWithWildcard(apples, new Fruit());
		writeWithWildcard(apples, new Apple());
	}
	
	public static void main(String[] args){
		f1();
		f2();
	}
}
